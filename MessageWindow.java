/*
 * Created on 8/20/2018
 *
 */
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * @author nyatadecocoa
 */
public class MessageWindow {
    // 白枠の幅
    private static final int EDGE_WIDTH = 2;

    // 行間の大きさ
    protected static final int LINE_HEIGHT = 8;
    // 1行の最大文字数
    private static final int MAX_CHAR_IN_LINE = 20;
    // 1ページに表示できる最大行数
    private static final int MAX_LINES = 3;
    // 1ページに表示できる最大文字数
    private static final int MAX_CHAR_IN_PAGE = MAX_CHAR_IN_LINE * MAX_LINES;

    // 一番外側の枠
    private Rectangle rect;
    // 一つ内側の枠（白い枠線ができるように）
    private Rectangle innerRect;
    // 実際にテキストを描画する枠
    private Rectangle textRect;

    // メッセージウィンドウを表示中か
    private boolean isVisible = false;

    // カーソルのアニメーションGIF
    private Image cursorImage;

    // メッセージを格納した配列
    private char[] text = new char[128 * MAX_CHAR_IN_LINE];
    // 最大ページ
    private int maxPage;
    // 現在表示しているページ
    private int curPage = 0;
    // 表示した文字数
    private int curPos;
    // 次のページへいけるか（▼が表示されてればtrue）
    private boolean nextFlag = false;

    // メッセージエンジン
    private MessageEngine messageEngine;

    // テキストを流すタイムライン
    private AnimationTimer task;

    public MessageWindow(Rectangle rect) {
        this.rect = rect;

        innerRect = new Rectangle(
                rect.getX() + EDGE_WIDTH,
                rect.getY() + EDGE_WIDTH,
                rect.getWidth() - EDGE_WIDTH * 2,
                rect.getHeight() - EDGE_WIDTH * 2);

        textRect = new Rectangle(
                innerRect.getX() + 16,
                innerRect.getY() + 16,
                320,
                120);

        // メッセージエンジンを作成
        messageEngine = new MessageEngine();

        cursorImage = new Image("image/cursor.gif");
    }

    public void draw(GraphicsContext gc) {
        if (!isVisible) return;

        // 枠を描く
        gc.setFill(Color.WHITE);
        gc.fillRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());

        // 内側の枠を描く
        gc.setFill(Color.BLACK);
        gc.fillRect(innerRect.getX(), innerRect.getY(), innerRect.getWidth(), innerRect.getHeight());

        // 現在のページ（curPage）のcurPosまでの内容を表示
        for (int i = 0; i <curPos; i++) {
            char c = text[curPage * MAX_CHAR_IN_PAGE + i];
            int dx = (int) textRect.getX() + MessageEngine.FONT_WIDTH * (i % MAX_CHAR_IN_LINE);
            int dy = (int) textRect.getY() + (LINE_HEIGHT + MessageEngine.FONT_HEIGHT) * (i / MAX_CHAR_IN_LINE);
            messageEngine.drawCharacter(dx, dy, c, gc);
        }

        // 最後のページでない場合は矢印を表示する
        if (curPage < maxPage && nextFlag) {
            int dx = (int) textRect.getX() + (MAX_CHAR_IN_LINE / 2) * MessageEngine.FONT_WIDTH - 8;
            int dy = (int) textRect.getY() + (LINE_HEIGHT + MessageEngine.FONT_HEIGHT) * 3;
            gc.drawImage(cursorImage, dx, dy);
        }
    }

    /**
     * メッセージをセットする
     *
     * @param msg メッセージ
     */
    public void setMessage(String msg) {
        curPos = 0;
        curPage = 0;
        nextFlag = false;

        // 全角スペースで初期化
        for (int i = 0; i < text.length; i++) {
            text[i] = '　';
        }

        int p = 0;  // 処理中の文字位置
        for (int i = 0; i < msg.length(); i++) {
            char c = msg.charAt(i);
            if (c == '\\') {
                i++;
                if (msg.charAt(i) == 'n') {  // 改行
                    p += MAX_CHAR_IN_LINE;
                    p = (p / MAX_CHAR_IN_LINE) * MAX_CHAR_IN_LINE;
                } else if (msg.charAt(i) == 'f') {  // 改ページ
                    p += MAX_CHAR_IN_PAGE;
                    p = (p / MAX_CHAR_IN_PAGE) * MAX_CHAR_IN_PAGE;
                }
            } else {
                text[p++] = c;
            }
        }
        // デバッグ用
        System.out.println(text);

        maxPage = p / MAX_CHAR_IN_PAGE;

        task = new DrawingWindowTask();
        task.start();

    }

    /**
     * メッセージを先に進める
     *
     * @return メッセージが終了したらtrueを返す
     */
    public boolean nextMessage() {
        // 現在ページが最後のページだったらメッセージを終了する
        if (curPage == maxPage) {
            task.stop();
            return true;
        }
        // ▼が表示されてなければ次ページへいけない
        if (nextFlag) {
            curPage++;
            curPos = 0;
            nextFlag = false;
        }
        return false;
    }

    /**
     * ウィンドウを表示
     */
    public void show() {
        isVisible = true;
    }

    /**
     * ウィンドウを隠す
     */
    public void hide() {
        isVisible = false;
    }

    /**
     * ウィンドウを表示中か
     */
    public boolean isVisible() {
        return isVisible;
    }

    class DrawingWindowTask extends AnimationTimer {

        @Override
        public void handle(long now) {
            if (!nextFlag) {
                curPos++;  // 1文字増やす
                // 1ページの文字数になったら▼を表示
                if (curPos % MAX_CHAR_IN_PAGE == 0) {
                    nextFlag = true;
                }
            }
        }
    }
}

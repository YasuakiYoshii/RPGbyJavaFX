import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import javafx.scene.canvas.Canvas;
import javafx.stage.StageStyle;

import java.util.Random;
import java.util.Vector;


/*
 * Created on 8/18/2018
 *
 */

/**
 * @author nyatadecocoa
 */
class MainPanel extends Pane implements Common {
    // パネルサイズ
    public static final int WIDTH = 480;
    public static final int HEIGHT = 480;

    // マップ
    private Map map;
    // 勇者
    private Chara hero;


    private final GraphicsContext gc;

    // アクションキー
    private ActionKey leftKey;
    private ActionKey rightKey;
    private ActionKey upKey;
    private ActionKey downKey;
    private ActionKey spaceKey;

    // 乱数生成器
    private Random rand = new Random();

    // ウィンドウ
    private MessageWindow messageWindow;
    // ウィンドウを表示する領域
    private static Rectangle WND_RECT =
            new Rectangle(62, 324, 356, 140);

    public MainPanel(Stage stage) {
        // パネルの推奨サイズを設定
        setPrefSize(WIDTH, HEIGHT);
        Group root = new Group();
        Scene theScene = new Scene(root);
        stage.setScene(theScene);
        Canvas canvas = new Canvas(WIDTH, HEIGHT);

        gc = canvas.getGraphicsContext2D();

        // アクションキーを作成
        leftKey = new ActionKey();
        rightKey = new ActionKey();
        upKey = new ActionKey();
        downKey = new ActionKey();
        spaceKey = new ActionKey(ActionKey.DETECT_INITIAL_PRESS_ONLY);

        // マップを作成
        map = new Map("map/map.dat", "event/event.dat", this);

        // 勇者を作成
        hero = new Chara(4, 4, 0, DOWN, 0, map);

        // マップにキャラクターを登録
        // キャラクターはマップに属す
        map.addChara(hero);

        // ウィンドウを追加
        messageWindow = new MessageWindow(WND_RECT);

        draw(gc);

        root.getChildren().add(canvas);

        stage.show();

        theScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case LEFT:
                        // 左キーだったら勇者を1歩左へ
                        leftKey.press();
                        break;
                    case RIGHT:
                        // 右キーだったら勇者を1歩右へ
                        rightKey.press();
                        break;
                    case UP:
                        // 上キーだったら勇者を1歩上へ
                        upKey.press();
                        break;
                    case DOWN:
                        // 下キーだったら勇者を1歩下へ
                        downKey.press();
                        break;
                    case SPACE:
                        spaceKey.press();
                        break;
                }
            }
        });

        theScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case LEFT:
                        // 左キーだったら勇者を1歩左へ
                        leftKey.release();
                        break;
                    case RIGHT:
                        // 右キーだったら勇者を1歩右へ
                        rightKey.release();
                        break;
                    case UP:
                        // 上キーだったら勇者を1歩上へ
                        upKey.release();
                        break;
                    case DOWN:
                        // 下キーだったら勇者を1歩下へ
                        downKey.release();
                        break;
                    case SPACE:
                        spaceKey.release();
                        break;
                }
            }
        });

        // キャラクターの移動(操作)
        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                gameLoop();
            }
        }.start();

        // x(閉じる)ボタンを押してもthread(CharacterAnimation)が終わらないので、無理矢理止める。(要改善)
        stage.showingProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue == true && newValue == false) {
                // マップにいるキャラクターを取得
                /*Vector charas = map.getCharas();
                for (int i=0; i<charas.size(); i++) {
                    Chara chara = (Chara)charas.get(i);
                    chara.thread.stop();
                }*/
                map.finished = true;
            }
        });
    }

    //ゲームループ（通常60fpsで呼び出される）
    public void gameLoop() {
        // キー入力をチェックする
        if (messageWindow.isVisible()) {  // メッセージウィンドウ表示中
            messageWindowCheckInput();
        } else {  // メイン画面
            mainWindowCheckInput();
        }

        if (!messageWindow.isVisible()) {
            // 勇者の移動処理
            heroMove();
            // キャラクターの移動処理
            charaMove();
        }

        repaint();

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void repaint(){

        gc.clearRect(0, 0, WIDTH, HEIGHT);

        draw(gc);
    }

    public void draw(GraphicsContext gc){
        // X方向のオフセットを計算
        int offsetX = MainPanel.WIDTH / 2 - hero.getX() * CS;
        // マップの端ではスクロールしないようにする
        offsetX = Math.min(offsetX, 0);
        offsetX = Math.max(offsetX, MainPanel.WIDTH - map.getWidth());

        // Y方向のオフセットを計算
        int offsetY = MainPanel.HEIGHT / 2 - hero.getY() * CS;
        // マップの端ではスクロールしないようにする
        offsetY = Math.min(offsetY, 0);
        offsetY = Math.max(offsetY, MainPanel.HEIGHT - map.getHeight());

        // マップを描く
        map.draw(gc, offsetX, offsetY);

        // 勇者を描く
        hero.draw(gc, offsetX, offsetY);

        // メッセージウィンドウを描画
        messageWindow.draw(gc);
    }

    /**
     * メインウィンドウでのキー入力をチェックする
     */
    private void mainWindowCheckInput() {
        if (leftKey.isPressed()) { // 左
            if (!hero.isMoving()) {       // 移動中でなければ
                hero.setDirection(LEFT);  // 方向をセットして
                hero.setMoving(true);     // 移動（スクロール）開始
            }
        }
        if (rightKey.isPressed()) { // 右
            if (!hero.isMoving()) {
                hero.setDirection(RIGHT);
                hero.setMoving(true);
            }
        }
        if (upKey.isPressed()) { // 上
            if (!hero.isMoving()) {
                hero.setDirection(UP);
                hero.setMoving(true);
            }
        }
        if (downKey.isPressed()) { // 下
            if (!hero.isMoving()) {
                hero.setDirection(DOWN);
                hero.setMoving(true);
            }
        }
        if (spaceKey.isPressed()) {  // スペース
            // 移動中は表示できない
            if (hero.isMoving()) return;

            // しらべる
            TreasureEvent treasure = hero.search();
            if (treasure != null) {
                // メッセージをセットする
                messageWindow.setMessage(treasure.getItemName() + "を　てにいれた。\\fと、おもっただけだった");
                // メッセージウィンドウを表示
                messageWindow.show();
                // ここにアイテム入手処理を入れる
                // 宝箱を削除
                map.removeEvent(treasure);
                return;  // しらべた場合ははなさない
            }

            // とびら
            DoorEvent door = hero.open();
            if (door != null) {
                // ドアを削除
                map.removeEvent(door);

                return;
            }

            // はなす
            if (!messageWindow.isVisible()) {  // メッセージウィンドウを表示
                Chara chara = hero.talkWith();
                if (chara != null) {
                    // メッセージをセットする
                    messageWindow.setMessage(chara.getMessage());
                    // メッセージウィンドウを表示
                    messageWindow.show();
                } else {
                    messageWindow.setMessage("そのほうこうには　だれもいない。");
                    messageWindow.show();
                }
            }
        }
    }

    /**
     * メッセージウィンドウでのキー入力をチェックする
     */
    private void messageWindowCheckInput() {
        if (spaceKey.isPressed()) {
            if (messageWindow.nextMessage()) {  // 次のメッセージへ
                messageWindow.hide();  // 終了したら隠す
            }
        }
    }

    /**
     * 勇者の移動処理
     */
    private void heroMove() {
        // 移動（スクロール）中なら移動する
        if (hero.isMoving()) {
            if (hero.move()) {  // 移動（スクロール）
                // 移動が完了した後の処理はここに書く
            }
        }
    }

    /**
     * 勇者以外のキャラクターの移動処理
     */
    private void charaMove() {
        // マップにいるキャラクターを取得
        Vector charas = map.getCharas();
        for (int i=0; i<charas.size(); i++) {
            Chara chara = (Chara)charas.get(i);
            // キャラクターの移動タイプを調べる
            if (chara.getMoveType() == 1) {  // 移動するタイプなら
                if (chara.isMoving()) {  // 移動中なら
                    chara.move();  // 移動する
                } else if (rand.nextDouble() < Chara.PROB_MOVE) {
                    // 移動してない場合はChara.PROB_MOVEの確率で再移動する
                    // 方向はランダムに決める
                    chara.setDirection(rand.nextInt(4));
                    chara.setMoving(true);
                }
            }
        }
    }
}
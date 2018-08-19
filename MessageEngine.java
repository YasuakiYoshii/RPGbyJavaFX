/*
 * Created on 8/18/2018
 *
 */

import javafx.scene.canvas.GraphicsContext;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

import java.util.HashMap;

/**
 * @author nyatadecocoa
 */
public class MessageEngine {
    // フォントの大きさ
    public static final int FONT_WIDTH = 16;
    public static final int FONT_HEIGHT = 22;

    // フォントの色
    public static final int WHITE = 0;
    public static final int RED = 160;
    public static final int GREEN = 320;
    public static final int BLUE = 480;

    // フォントイメージ
    private Image fontImage;
    // かな→座標のハッシュ
    private HashMap<Character, Point2D> kana2Pos;

    // 色
    private int color;

    public MessageEngine() {
        // フォントイメージをロード
        fontImage = new Image("image/font.gif");

        color = WHITE;

        // かな→イメージ座標へのハッシュを作成
        kana2Pos = new HashMap<>();
        createHash();
    }

    public void setColor(int c) {
        this.color = c;

        // 変な値だったらWHITEにする
        if (color != WHITE && color != RED && color != GREEN && color != BLUE) {
            this.color = WHITE;
        }
    }

    /**
     * メッセージを描画する
     * @param x X座標
     * @param y Y座標
     * @param message メッセージ
     * @param gc 描画オブジェクト
     */
    public void drawMessage(int x, int y, String message, GraphicsContext gc) {
        for (int i=0; i<message.length(); i++) {
            char c = message.charAt(i);
            int dx = x + FONT_WIDTH * i;
            drawCharacter(dx, y, c, gc);
        }
    }

    /**
     * 文字を描画する
     * @param x X座標
     * @param y Y座標
     * @param c 文字
     * @param gc 描画オブジェクト
     */
    public void drawCharacter(int x, int y, char c, GraphicsContext gc) {
        Point2D pos = kana2Pos.get(c);
        gc.drawImage(fontImage, pos.getX() + color, pos.getY(), FONT_WIDTH, FONT_HEIGHT,
                x, y, FONT_WIDTH, FONT_HEIGHT);
    }

    /**
     * 文字から座標へのハッシュを作成する
     */
    private void createHash() {
        kana2Pos.put('あ', new Point2D(0, 0));
        kana2Pos.put('い', new Point2D(16, 0));
        kana2Pos.put('う', new Point2D(32, 0));
        kana2Pos.put('え', new Point2D(48, 0));
        kana2Pos.put('お', new Point2D(64, 0));

        kana2Pos.put('か', new Point2D(0, 22));
        kana2Pos.put('き', new Point2D(16, 22));
        kana2Pos.put('く', new Point2D(32, 22));
        kana2Pos.put('け', new Point2D(48, 22));
        kana2Pos.put('こ', new Point2D(64, 22));

        kana2Pos.put('さ', new Point2D(0, 44));
        kana2Pos.put('し', new Point2D(16, 44));
        kana2Pos.put('す', new Point2D(32, 44));
        kana2Pos.put('せ', new Point2D(48, 44));
        kana2Pos.put('そ', new Point2D(64, 44));

        kana2Pos.put('た', new Point2D(0, 66));
        kana2Pos.put('ち', new Point2D(16, 66));
        kana2Pos.put('つ', new Point2D(32, 66));
        kana2Pos.put('て', new Point2D(48, 66));
        kana2Pos.put('と', new Point2D(64, 66));

        kana2Pos.put('な', new Point2D(0, 88));
        kana2Pos.put('に', new Point2D(16, 88));
        kana2Pos.put('ぬ', new Point2D(32, 88));
        kana2Pos.put('ね', new Point2D(48, 88));
        kana2Pos.put('の', new Point2D(64, 88));

        kana2Pos.put('は', new Point2D(0, 110));
        kana2Pos.put('ひ', new Point2D(16, 110));
        kana2Pos.put('ふ', new Point2D(32, 110));
        kana2Pos.put('へ', new Point2D(48, 110));
        kana2Pos.put('ほ', new Point2D(64, 110));

        kana2Pos.put('ま', new Point2D(0, 132));
        kana2Pos.put('み', new Point2D(16, 132));
        kana2Pos.put('む', new Point2D(32, 132));
        kana2Pos.put('め', new Point2D(48, 132));
        kana2Pos.put('も', new Point2D(64, 132));

        kana2Pos.put('や', new Point2D(0, 154));
        kana2Pos.put('ゆ', new Point2D(16, 154));
        kana2Pos.put('よ', new Point2D(32, 154));
        kana2Pos.put('わ', new Point2D(48, 154));
        kana2Pos.put('を', new Point2D(64, 154));

        kana2Pos.put('ら', new Point2D(0, 176));
        kana2Pos.put('り', new Point2D(16, 176));
        kana2Pos.put('る', new Point2D(32, 176));
        kana2Pos.put('れ', new Point2D(48, 176));
        kana2Pos.put('ろ', new Point2D(64, 176));

        kana2Pos.put('ん', new Point2D(0, 198));
        kana2Pos.put('ぃ', new Point2D(16, 198));
        kana2Pos.put('っ', new Point2D(32, 198));
        kana2Pos.put('ぇ', new Point2D(48, 198));
        kana2Pos.put('　', new Point2D(64, 198));

        kana2Pos.put('ゃ', new Point2D(0, 220));
        kana2Pos.put('ゅ', new Point2D(16, 220));
        kana2Pos.put('ょ', new Point2D(32, 220));
        kana2Pos.put('、', new Point2D(48, 220));
        kana2Pos.put('。', new Point2D(64, 220));

        kana2Pos.put('が', new Point2D(0, 242));
        kana2Pos.put('ぎ', new Point2D(16, 242));
        kana2Pos.put('ぐ', new Point2D(32, 242));
        kana2Pos.put('げ', new Point2D(48, 242));
        kana2Pos.put('ご', new Point2D(64, 242));

        kana2Pos.put('ざ', new Point2D(0, 264));
        kana2Pos.put('じ', new Point2D(16, 264));
        kana2Pos.put('ず', new Point2D(32, 264));
        kana2Pos.put('ぜ', new Point2D(48, 264));
        kana2Pos.put('ぞ', new Point2D(64, 264));

        kana2Pos.put('だ', new Point2D(0, 286));
        kana2Pos.put('ぢ', new Point2D(16, 286));
        kana2Pos.put('づ', new Point2D(32, 286));
        kana2Pos.put('で', new Point2D(48, 286));
        kana2Pos.put('ど', new Point2D(64, 286));

        kana2Pos.put('ば', new Point2D(0, 308));
        kana2Pos.put('び', new Point2D(16, 308));
        kana2Pos.put('ぶ', new Point2D(32, 308));
        kana2Pos.put('べ', new Point2D(48, 308));
        kana2Pos.put('ぼ', new Point2D(64, 308));

        kana2Pos.put('ぱ', new Point2D(0, 330));
        kana2Pos.put('ぴ', new Point2D(16, 330));
        kana2Pos.put('ぷ', new Point2D(32, 330));
        kana2Pos.put('ぺ', new Point2D(48, 330));
        kana2Pos.put('ぽ', new Point2D(64, 330));

        kana2Pos.put('ア', new Point2D(80, 0));
        kana2Pos.put('イ', new Point2D(96, 0));
        kana2Pos.put('ウ', new Point2D(112, 0));
        kana2Pos.put('エ', new Point2D(128, 0));
        kana2Pos.put('オ', new Point2D(144, 0));

        kana2Pos.put('カ', new Point2D(80, 22));
        kana2Pos.put('キ', new Point2D(96, 22));
        kana2Pos.put('ク', new Point2D(112, 22));
        kana2Pos.put('ケ', new Point2D(128, 22));
        kana2Pos.put('コ', new Point2D(144, 22));

        kana2Pos.put('サ', new Point2D(80, 44));
        kana2Pos.put('シ', new Point2D(96, 44));
        kana2Pos.put('ス', new Point2D(112, 44));
        kana2Pos.put('セ', new Point2D(128, 44));
        kana2Pos.put('ソ', new Point2D(144, 44));

        kana2Pos.put('タ', new Point2D(80, 66));
        kana2Pos.put('チ', new Point2D(96, 66));
        kana2Pos.put('ツ', new Point2D(112, 66));
        kana2Pos.put('テ', new Point2D(128, 66));
        kana2Pos.put('ト', new Point2D(144, 66));

        kana2Pos.put('ナ', new Point2D(80, 88));
        kana2Pos.put('ニ', new Point2D(96, 88));
        kana2Pos.put('ヌ', new Point2D(112, 88));
        kana2Pos.put('ネ', new Point2D(128, 88));
        kana2Pos.put('ノ', new Point2D(144, 88));

        kana2Pos.put('ハ', new Point2D(80, 110));
        kana2Pos.put('ヒ', new Point2D(96, 110));
        kana2Pos.put('フ', new Point2D(112, 110));
        kana2Pos.put('ヘ', new Point2D(128, 110));
        kana2Pos.put('ホ', new Point2D(144, 110));

        kana2Pos.put('マ', new Point2D(80, 132));
        kana2Pos.put('ミ', new Point2D(96, 132));
        kana2Pos.put('ム', new Point2D(112, 132));
        kana2Pos.put('メ', new Point2D(128, 132));
        kana2Pos.put('モ', new Point2D(144, 132));

        kana2Pos.put('ヤ', new Point2D(80, 154));
        kana2Pos.put('ユ', new Point2D(96, 154));
        kana2Pos.put('ヨ', new Point2D(112, 154));
        kana2Pos.put('ワ', new Point2D(128, 154));
        kana2Pos.put('ヲ', new Point2D(144, 154));

        kana2Pos.put('ラ', new Point2D(80, 176));
        kana2Pos.put('リ', new Point2D(96, 176));
        kana2Pos.put('ル', new Point2D(112, 176));
        kana2Pos.put('レ', new Point2D(128, 176));
        kana2Pos.put('ロ', new Point2D(144, 176));

        kana2Pos.put('ン', new Point2D(80, 198));
        kana2Pos.put('ィ', new Point2D(96, 198));
        kana2Pos.put('ッ', new Point2D(112, 198));
        kana2Pos.put('ェ', new Point2D(128, 198));
        kana2Pos.put('「', new Point2D(144, 198));

        kana2Pos.put('ャ', new Point2D(80, 220));
        kana2Pos.put('ュ', new Point2D(96, 220));
        kana2Pos.put('ョ', new Point2D(112, 220));
        kana2Pos.put('ー', new Point2D(128, 220));
        kana2Pos.put('」', new Point2D(144, 220));

        kana2Pos.put('ガ', new Point2D(80, 242));
        kana2Pos.put('ギ', new Point2D(96, 242));
        kana2Pos.put('グ', new Point2D(112, 242));
        kana2Pos.put('ゲ', new Point2D(128, 242));
        kana2Pos.put('ゴ', new Point2D(144, 242));

        kana2Pos.put('ザ', new Point2D(80, 264));
        kana2Pos.put('ジ', new Point2D(96, 264));
        kana2Pos.put('ズ', new Point2D(112, 264));
        kana2Pos.put('ゼ', new Point2D(128, 264));
        kana2Pos.put('ゾ', new Point2D(144, 264));

        kana2Pos.put('ダ', new Point2D(80, 286));
        kana2Pos.put('ヂ', new Point2D(96, 286));
        kana2Pos.put('ヅ', new Point2D(112, 286));
        kana2Pos.put('デ', new Point2D(128, 286));
        kana2Pos.put('ド', new Point2D(144, 286));

        kana2Pos.put('バ', new Point2D(80, 308));
        kana2Pos.put('ビ', new Point2D(96, 308));
        kana2Pos.put('ブ', new Point2D(112, 308));
        kana2Pos.put('ベ', new Point2D(128, 308));
        kana2Pos.put('ボ', new Point2D(144, 308));

        kana2Pos.put('パ', new Point2D(80, 330));
        kana2Pos.put('ピ', new Point2D(96, 330));
        kana2Pos.put('プ', new Point2D(112, 330));
        kana2Pos.put('ペ', new Point2D(128, 330));
        kana2Pos.put('ポ', new Point2D(144, 330));

        kana2Pos.put('0', new Point2D(0, 352));
        kana2Pos.put('1', new Point2D(16, 352));
        kana2Pos.put('2', new Point2D(32, 352));
        kana2Pos.put('3', new Point2D(48, 352));
        kana2Pos.put('4', new Point2D(64, 352));
        kana2Pos.put('5', new Point2D(80, 352));
        kana2Pos.put('6', new Point2D(96, 352));
        kana2Pos.put('7', new Point2D(112, 352));
        kana2Pos.put('8', new Point2D(128, 352));
        kana2Pos.put('9', new Point2D(144, 352));

        kana2Pos.put('Ａ', new Point2D(0, 374));
        kana2Pos.put('Ｂ', new Point2D(16, 374));
        kana2Pos.put('Ｃ', new Point2D(32, 374));
        kana2Pos.put('Ｄ', new Point2D(48, 374));
        kana2Pos.put('Ｅ', new Point2D(64, 374));
        kana2Pos.put('Ｆ', new Point2D(80, 374));
        kana2Pos.put('Ｇ', new Point2D(96, 374));
        kana2Pos.put('Ｈ', new Point2D(112, 374));
        kana2Pos.put('Ｉ', new Point2D(128, 374));
        kana2Pos.put('Ｊ', new Point2D(144, 374));

        kana2Pos.put('Ｋ', new Point2D(0, 396));
        kana2Pos.put('Ｌ', new Point2D(16, 396));
        kana2Pos.put('Ｍ', new Point2D(32, 396));
        kana2Pos.put('Ｎ', new Point2D(48, 396));
        kana2Pos.put('Ｏ', new Point2D(64, 396));
        kana2Pos.put('Ｐ', new Point2D(80, 396));
        kana2Pos.put('Ｑ', new Point2D(96, 396));
        kana2Pos.put('Ｒ', new Point2D(112, 396));
        kana2Pos.put('Ｓ', new Point2D(128, 396));
        kana2Pos.put('Ｔ', new Point2D(144, 396));

        kana2Pos.put('Ｕ', new Point2D(0, 418));
        kana2Pos.put('Ｖ', new Point2D(16, 418));
        kana2Pos.put('Ｗ', new Point2D(32, 418));
        kana2Pos.put('Ｘ', new Point2D(48, 418));
        kana2Pos.put('Ｙ', new Point2D(64, 418));
        kana2Pos.put('Ｚ', new Point2D(80, 418));
        kana2Pos.put('！', new Point2D(96, 418));
        kana2Pos.put('？', new Point2D(112, 418));
    }

}

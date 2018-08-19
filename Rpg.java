import javafx.application.Application;
import javafx.stage.Stage;

/*
 * Created on 8/18/2018
 *
 */

/**
 * @author nyatadecocoa
 */
public class Rpg extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // タイトルを設定
        primaryStage.setTitle("メッセージウィンドウの表示");

        // パネルを作成
        MainPanel panel = new MainPanel(primaryStage);
    }
}
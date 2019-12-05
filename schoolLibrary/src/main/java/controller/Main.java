package controller;

import controller.reader.ReaderController;
import controller.supervisor.SuperAdminController;
import controller.worker.WorkerController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    public static LoginController lc;
    public static ReaderController rc;
    public static SuperAdminController sac;
    public static WorkerController wc;
    public static Scene lcScene;
    public static Scene rcScene;
    public static Scene saScene;
    public static Scene wcScene;

    public final static Double width = 900.0;
    public final static Double height = 720.0;

    public final static Double sonWidth = 600.0;
    public final static Double sonHeight = 480.0;

    public final static String ICON_MAIN_LOC = "/images/icon-book.png";

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader lcLoader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        FXMLLoader rcLoader = new FXMLLoader(getClass().getResource("/fxml/reader/reader.fxml"));
        FXMLLoader saLoader = new FXMLLoader(getClass().getResource("/fxml/supervisor/superadmin.fxml"));
        FXMLLoader wcLoader = new FXMLLoader(getClass().getResource("/fxml/worker/worker.fxml"));

        Parent lcRoot = (Parent) lcLoader.load();
        Parent rcRoot = (Parent) rcLoader.load();
        Parent saRoot = (Parent) saLoader.load();
        Parent wcRoot = (Parent) wcLoader.load();

        lc = (LoginController) lcLoader.getController();
        rc = (ReaderController) rcLoader.getController();
        sac = (SuperAdminController) saLoader.getController();
        wc = (WorkerController) wcLoader.getController();

        lcScene = new Scene(lcRoot, width, height);
        rcScene = new Scene(rcRoot, width, height);
        saScene = new Scene(saRoot, width, height);
        wcScene = new Scene(wcRoot, width, height);

        //加载CSS样式
        //lcScene.getStylesheets().add(getClass().getResource("./css/main.css").toExternalForm());

        primaryStage.setTitle("登录主界面");
        primaryStage.getIcons().add(new Image(ICON_MAIN_LOC));

        //设置场景
        primaryStage.setScene(lcScene);
        //设置窗口不可调节
        primaryStage.setResizable(false);
        //展示场景
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
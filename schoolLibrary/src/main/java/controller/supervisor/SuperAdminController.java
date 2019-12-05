package controller.supervisor;

import controller.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SuperAdminController {

    @FXML
    private BorderPane borderPane;

    @FXML
    private Accordion accordion;

    @FXML
    private Button addreader;

    @FXML
    private Button addworker;

    @FXML
    private Button selectReader;

    @FXML
    private Button selectWorker;

    @FXML
    private Button cancellation;

    @FXML
    private Button alterRpw;

    @FXML
    private Button alterWpw;

    @FXML
    private Button alterSpw;

    @FXML
    private AnchorPane anchorpaneR;

    @FXML
    private TitledPane titleFirstPane;

    @FXML
    private TitledPane titleSecondPane;

    @FXML
    private TitledPane titleThirdPane;

    @FXML
    private TitledPane titleFourthPane;


    public BorderPane getBorderPane() {
        return borderPane;
    }

    public void initData() {
        //默认选择第一个标题，并且清空右边的内容，同时使得每个标题处于关闭状态
        //清空右边的面板
        anchorpaneR.getChildren().clear();
        //设置4个选项卡都为关闭状态
        titleFirstPane.setExpanded(false);
        titleSecondPane.setExpanded(false);
        titleThirdPane.setExpanded(false);
        titleFourthPane.setExpanded(false);
    }

    //跳转到添加读者页面
    @FXML
    public void addreaderClick() {
        try {
            switchView("add_readers.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //跳转到添加工作人员页面
    @FXML
    public void addworkerClick() {
        try {
            switchView("add_librarystaff.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //跳转到修改读者密码页面
    @FXML
    public void alterRpwClick() {
        try {
            switchView("alterRpw.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //跳转到修改超级管理员页面，界面为个人中心
    @FXML
    public void alterSpwClick() {
        try {
            switchView("alterSpw.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //跳转到修改工作人员密码界面
    @FXML
    public void alterWpwClick() {
        try {
            switchView("alterWpw.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //跳转到注销借阅证页面
    @FXML
    public void cancellationClick() {
        try {
            switchView("cancellation.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //默认界面
    @FXML
    public void listDefault() {

    }

    //退出登录
    @FXML
    public void returnToLoginBtnClick() {
        System.out.println("返回到登录界面~");
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.setTitle("登录主界面");
        stage.setScene(Main.lcScene);
        Main.lc.clearContent();
    }

    //跳转到查询读者页面
    @FXML
    public void selectRederClick() {
        try {
            switchView("selectReader.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //跳转到查询工作人员页面
    @FXML
    public void selectWorkeClick() {
        try {
            switchView("selectWorker.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //选择显示页面
    private void switchView(String fileName) throws Exception {

        anchorpaneR.getChildren().clear();
        AnchorPane anchorPane = new FXMLLoader(getClass().getResource("/fxml/supervisor/" + fileName)).load();

        anchorpaneR.getChildren().add(anchorPane);
    }

    @FXML
    public void titlePaneClick1() {
        accordion.setPrefHeight(360);
        System.out.println("点击添加用户");
    }

    @FXML
    public void titlePaneClick2() {
        accordion.setPrefHeight(360);
        System.out.println("点击查询用户");
    }

    @FXML
    public void titlePaneClick3() {
        accordion.setPrefHeight(420);
        System.out.println("点击信息管理");
    }

    @FXML
    public void titlePaneClick4() {
        accordion.setPrefHeight(310);
        System.out.println("点击个人中心");
    }
}

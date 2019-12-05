package controller.worker;

import com.jfoenix.controls.JFXButton;
import controller.Main;
import controller.reader.BorrowHistoryController;
import controller.reader.NowBorrowController;
import controller.reader.ReserveBookController;
import controller.reader.ViolationController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import java.io.IOException;

public class SelectReadermsController {

    @FXML
    private TextField inputRM;

    @FXML
    private JFXButton nborrowing;

    @FXML
    private JFXButton hreturn;

    @FXML
    private JFXButton qreserve;

    @FXML
    private JFXButton uncomply;

    private Alert alert = new Alert(Alert.AlertType.INFORMATION);

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    //正在借阅
    @FXML
    public void nborrowingClick() {
        String readerId = inputRM.getText();
        if(readerId == null || readerId.equals("")) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("输入不能为空");
            alert.show();
            return;
        }
        //先去查询一下是否存在这个读者
        String sql = "SELECT COUNT(*) FROM readers WHERE RId = ?;";
        Long cnt = template.queryForObject(sql, Long.class, readerId);
        if(cnt > 0) {
            try {
                FXMLLoader nbLoader = new FXMLLoader(getClass().getResource("/fxml/reader/nowBorrow.fxml"));
                Parent nbparent = nbLoader.load();
                NowBorrowController nbc = (NowBorrowController) nbLoader.getController();
                Stage stage = new Stage();
                stage.setTitle("正在借阅表");
                stage.setScene(new Scene(nbparent, Main.sonWidth, Main.sonHeight));
                stage.setResizable(false);
                //填充数据
                nbc.initData(readerId);
                Stage stageF = (Stage) Main.wc.getBorderPane().getScene().getWindow();
                //通过数据库查询来设置借阅和归还信息，设置模态
                stage.initOwner(stageF);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("读者账号不存在，请重新输入");
            alert.show();
        }
    }

    @FXML
    public void hreturnClick() {
        String readerId = inputRM.getText();
        if(readerId == null || readerId.equals("")) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("输入不能为空");
            alert.show();
            return;
        }
        //先去查询一下是否存在这个读者
        String sql = "SELECT COUNT(*) FROM readers WHERE RId = ?;";
        Long cnt = template.queryForObject(sql, Long.class, readerId);
        if(cnt > 0) {
            try {
                FXMLLoader bhLoader = new FXMLLoader(getClass().getResource("/fxml/reader/borrowHistory.fxml"));
                Parent bhparent = bhLoader.load();
                BorrowHistoryController nbc = (BorrowHistoryController) bhLoader.getController();
                Stage stage = new Stage();
                stage.setTitle("借阅历史");
                stage.setScene(new Scene(bhparent, Main.sonWidth, Main.sonHeight));
                stage.setResizable(false);
                //填充数据
                nbc.initHistoryData(readerId);
                Stage stageF = (Stage) Main.wc.getBorderPane().getScene().getWindow();
                //通过数据库查询来设置借阅和归还信息，设置模态
                stage.initOwner(stageF);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("读者账号不存在，请重新输入");
            alert.show();
        }
    }


//查询预约信息
    @FXML
    public void qreserveClick() {
        String readerId = inputRM.getText();
        if(readerId == null || readerId.equals("")) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("输入不能为空");
            alert.show();
            return;
        }
        //先去查询一下是否存在这个读者
        String sql = "SELECT COUNT(*) FROM readers WHERE RId = ?;";
        Long cnt = template.queryForObject(sql, Long.class, readerId);
        if(cnt > 0) {
            try {
                FXMLLoader rbbLoader = new FXMLLoader(getClass().getResource("/fxml/worker/workQueryReserveBook.fxml"));
                Parent rbbparent = rbbLoader.load();
                workQueryReserveBookController wrbc = (workQueryReserveBookController) rbbLoader.getController();
                Stage stage = new Stage();
                stage.setTitle("预约信息");
                stage.setScene(new Scene(rbbparent, Main.sonWidth, Main.sonHeight));
                stage.setResizable(false);
                //填充数据
                wrbc.initReserveData(readerId);
                Stage stageF = (Stage) Main.wc.getBorderPane().getScene().getWindow();
                //通过数据库查询来设置借阅和归还信息，设置模态
                stage.initOwner(stageF);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("读者账号不存在，请重新输入");
            alert.show();
        }
    }

    @FXML
    public void uncomplyClick() {
        String readerId = inputRM.getText();
        if(readerId == null || readerId.equals("")) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("输入不能为空");
            alert.show();
            return;
        }
        //先去查询一下是否存在这个读者
        String sql = "SELECT COUNT(*) FROM readers WHERE RId = ?;";
        Long cnt = template.queryForObject(sql, Long.class, readerId);
        if(cnt > 0) {
            try {
                FXMLLoader voLoader = new FXMLLoader(getClass().getResource("/fxml/reader/violation.fxml"));
                Parent bhparent = voLoader.load();
                ViolationController vc = (ViolationController) voLoader.getController();
                Stage stage = new Stage();
                stage.setTitle("违章记录");
                stage.setScene(new Scene(bhparent, Main.sonWidth, Main.sonHeight));
                stage.setResizable(false);
                Stage stageF = (Stage) Main.wc.getBorderPane().getScene().getWindow();
                //通过数据库查询来设置借阅和归还信息，设置模态
                stage.initOwner(stageF);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
                vc.initUncomplyData(readerId);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("读者账号不存在，请重新输入");
            alert.show();
        }
    }
}

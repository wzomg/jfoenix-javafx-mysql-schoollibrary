package controller.worker;

import com.jfoenix.controls.JFXPasswordField;
import controller.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import java.util.Map;
import java.util.Optional;

public class WorkerAltpwController {

    @FXML
    private JFXPasswordField inputConfirmPwd;

    @FXML
    private JFXPasswordField InputOldpwd;

    @FXML
    private JFXPasswordField InputNewpwd;

    @FXML
    private Label labelMsg;

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @FXML
    public void awWcancelClick() {
        InputNewpwd.setText("");
        InputOldpwd.setText("");
        inputConfirmPwd.setText("");
        labelMsg.setText("");
    }

    @FXML
    public void awWsureClick() {
        String oldPwd = InputOldpwd.getText();
        String newPwd = InputNewpwd.getText();
        String confirmPwd =  inputConfirmPwd.getText();
        System.out.println("旧密码：" + oldPwd);
        System.out.println("新密码：" + newPwd);
        System.out.println("确认密码：" + confirmPwd);
        boolean flag = false;
        if(oldPwd == null || oldPwd.equals("")) {
            flag = true;
        }
        if(newPwd == null || newPwd.equals("")) {
            flag = true;
        }
        if(confirmPwd == null || confirmPwd.equals("")) {
            flag = true;
        }
        if(flag) {
            labelMsg.setText("输入不能为空~");
            return;
        }
        if(!newPwd.equals(confirmPwd)) {
            labelMsg.setText("新密码与确认密码不一致，请重新输入！");
            return;
        }
        String workerID = Main.wc.getWorkerId();
        System.out.println(workerID);

        String sql = "SELECT WPasswd FROM workers WHERE WId = ?";
        System.out.println(sql);
        Map<String, Object> map = template.queryForMap(sql, workerID);
        if(!((String)map.get("WPasswd")).equals(oldPwd)) {
            labelMsg.setText("旧密码输入错误，请重新输入！");
            return;
        }
        sql = "UPDATE workers SET WPasswd = ? WHERE WId = ?;";
        int cnt = template.update(sql, confirmPwd, workerID);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Optional<ButtonType> type = null;
        if(cnt > 0) {
            alert.setHeaderText("INFORMATION");
            alert.setContentText("修改密码成功！");
            type = alert.showAndWait();
            if(type.isPresent() && type.get() == ButtonType.OK) {
                //清空表单数据
                awWcancelClick();
                //登录信息失效，请重新登录
                alert.setAlertType(Alert.AlertType.WARNING);
                alert.setHeaderText("WARNING!");
                alert.setContentText("密码已修改，为确保安全，请重新登录！");
                type = alert.showAndWait();
                if(type.isPresent() && (type.get() == ButtonType.OK || type.get() == ButtonType.CLOSE)) {
                    turnToLogin();
                }
            } else {
                awWcancelClick();
                turnToLogin();
            }
        }
    }

    private void turnToLogin() {
        System.out.println("返回到登录界面~");
        Stage stage = (Stage) Main.wc.getBorderPane().getScene().getWindow();
        stage.setTitle("登录主界面");
        stage.setScene(Main.lcScene);
        Main.lc.clearContent();
    }
}
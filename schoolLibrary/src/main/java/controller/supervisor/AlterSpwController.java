package controller.supervisor;

import controller.Main;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import java.util.Map;
import java.util.Optional;

public class AlterSpwController {

    @FXML
    private Button awSsure;

    @FXML
    private Button awScancel;

    @FXML
    private PasswordField InputNewpwd;

    @FXML
    private PasswordField inputConfirmPwd;

    @FXML
    private PasswordField InputOldpwd;

    @FXML
    private Label labelMsg;

    @FXML
    private AnchorPane superPane;

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @FXML
    public void awScancelClick() {
        InputNewpwd.setText("");
        InputOldpwd.setText("");
        inputConfirmPwd.setText("");
        labelMsg.setText("");
    }

    @FXML
    public void awSsureClick() {
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
        //此处去查询读者账号是否存在，不存在则直接设置错误信息
        String sql = "SELECT SPasswd FROM supermanager WHERE Sid = 'root';";

        Map<String, Object> map = template.queryForMap(sql);
        if(!((String)map.get("SPasswd")).equals(oldPwd)) {
            labelMsg.setText("旧密码输入错误，请重新输入！");
            return;
        }
        sql = "UPDATE supermanager SET SPasswd = ? WHERE Sid = 'root'";
        int cnt = template.update(sql, confirmPwd);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Optional<ButtonType> type = null;
        if(cnt > 0) {
            alert.setHeaderText("INFORMATION");
            alert.setContentText("修改密码成功！");
            type = alert.showAndWait();
            if(type.isPresent() && type.get() == ButtonType.OK) {
                //清空表单数据
                awScancelClick();
                //登录信息失效，请重新登录
                alert.setAlertType(Alert.AlertType.WARNING);
                alert.setHeaderText("WARNING!");
                alert.setContentText("密码已修改，为确保安全，请重新登录！");
                type = alert.showAndWait();
                if(type.isPresent() && (type.get() == ButtonType.OK || type.get() == ButtonType.CLOSE)) {
                    turnToLogin();
                }
            } else {
                awScancelClick();
                turnToLogin();
            }
        }
    }

    private void turnToLogin() {
        System.out.println("跳转到登录界面！");
        //获取外层的面板
        Stage stage  = (Stage) Main.sac.getBorderPane().getScene().getWindow();
        stage.setScene(Main.lcScene);
        //设置登录信息
        Main.lc.clearContent();
    }
}

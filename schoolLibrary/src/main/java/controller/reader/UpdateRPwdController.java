package controller.reader;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import controller.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import java.util.Map;
public class UpdateRPwdController {

    @FXML
    private AnchorPane updateRPwdMainPane;

    @FXML
    private JFXButton awRsure;

    @FXML
    private JFXButton awRcancel;

    @FXML
    private JFXPasswordField InputOldpwd;

    @FXML
    private JFXPasswordField InputNewpwd;

    @FXML
    private JFXPasswordField inputConfirmPwd;

    @FXML
    private Label labelMsg;

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @FXML
    public void awRcancelClick() {
        InputNewpwd.setText("");
        InputOldpwd.setText("");
        inputConfirmPwd.setText("");
        labelMsg.setText("");
    }

    @FXML
    public void awRsureClick() {
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
        String readerID = Main.rc.getReaderId();
        System.out.println(readerID);
        //此处去查询一下读者的旧密码与填写的密码匹配
        String sql = "SELECT RPasswd FROM readers WHERE RId = ?";
        System.out.println(sql);
        Map<String, Object> map = template.queryForMap(sql, readerID);
        if(!((String)map.get("RPasswd")).equals(oldPwd)) {
            labelMsg.setText("旧密码输入错误，请重新输入！");
            return;
        }
        sql = "UPDATE readers SET RPasswd = ? WHERE RId = ?;";
        int cnt = template.update(sql, confirmPwd, readerID);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if(cnt > 0) {
            alert.setHeaderText("INFORMATION");
            alert.setContentText("修改密码成功！");
            alert.show();
            awRcancelClick();
        }
    }
}

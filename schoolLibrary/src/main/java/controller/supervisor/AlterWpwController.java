package controller.supervisor;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import java.util.Optional;

public class AlterWpwController {

    @FXML
    private TextField InputWAccont;

    @FXML
    private Button awWsure;

    @FXML
    private Button awWcancel;

    @FXML
    private Label labelMsg;

    @FXML
    private PasswordField InputWpw;

    @FXML
    private PasswordField inputConfirmPwd;

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());


    @FXML
    public void awWcancelClick() {
        InputWAccont.setText("");
        InputWpw.setText("");
        inputConfirmPwd.setText("");
        labelMsg.setText("");
    }

    @FXML
    public void awWsureClick() {
        String wAcount =  InputWAccont.getText();
        String wNewPwd = InputWpw.getText();
        String wConfirmPwd = inputConfirmPwd.getText();
        System.out.println("工作人员的账号：" + wAcount);
        System.out.println("工作人员新密码：" + wNewPwd);
        System.out.println("确认工作人员的密码：" + wConfirmPwd);
        boolean flag = false;
        if(wAcount == null || wAcount.equals("")) {
            flag = true;
        }
        if(wNewPwd == null || wNewPwd.equals("")) {
            flag = true;
        }
        if(wConfirmPwd == null || wConfirmPwd.equals("")) {
            flag = true;
        }
        if(flag) {
            labelMsg.setText("输入不能为空~");
            return;
        }
        if(!wNewPwd.equals(wConfirmPwd)) {
            labelMsg.setText("新密码与确认密码不一致，请重新输入！");
            return;
        }
        String sql = "UPDATE workers SET WPasswd = ? WHERE WId = ?";
        int cnt = template.update(sql, wNewPwd, wAcount);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Optional<ButtonType> type = null;
        if(cnt > 0) {
            alert.setHeaderText("INFORMATION");
            alert.setContentText("修改工作人员密码成功！");
            type = alert.showAndWait();
            if(type.isPresent() && type.get() == ButtonType.OK) {
                //清空表单数据
                awWcancelClick();
            }
        } else {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR");
            alert.setContentText("请确认工作人员是否存在！");
            type = alert.showAndWait();
            if(type.isPresent() && type.get() == ButtonType.OK) {
                awWcancelClick();
            }
        }
    }
}

package controller.supervisor;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import java.util.Optional;

public class AlterRpwController {

    @FXML
    private TextField InputRAccont;

    @FXML
    private TextField InputRpw;

    @FXML
    private Button awRsure;

    @FXML
    private Button Rcancel;

    @FXML
    private Label labelMsg;

    @FXML
    private TextField ConfirmPwd;


    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @FXML
    public void awRcancelClick() {
        InputRAccont.setText("");
        InputRpw.setText("");
        labelMsg.setText("");
        ConfirmPwd.setText("");
    }

    @FXML
    public void awRsureClick() {
        String rId = InputRAccont.getText();
        String newRPwd = InputRpw.getText();
        String confirmPwd = ConfirmPwd.getText();
        System.out.println("读者账号：" + rId);
        System.out.println("读者新密码：" + newRPwd);
        System.out.println("确认的密码：" + confirmPwd);
        boolean flag = false;
        if(rId == null || rId.equals("")) {
            flag = true;
        }
        if(newRPwd == null || newRPwd.equals("")) {
            flag = true;
        }
        if(confirmPwd == null || confirmPwd.equals("")) {
            flag = true;
        }
        if(flag) {
            labelMsg.setText("输入不能为空~");
            return;
        }
        if(!newRPwd.equals(confirmPwd)) {
            labelMsg.setText("新密码与确认密码不一致，请重新输入！");
            return;
        }
        //此处去查询读者账号是否存在，不存在则直接设置错误信息
        String sql = "UPDATE readers SET RPasswd = ? WHERE RId = ?";
        int cnt = template.update(sql, newRPwd, rId);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Optional<ButtonType> type = null;
        if(cnt > 0) {
            alert.setHeaderText("INFORMATION");
            alert.setContentText("修改读者密码成功！");
            type = alert.showAndWait();
            if(type.isPresent() && type.get() == ButtonType.OK) {
                //清空表单数据
                awRcancelClick();
            }
        } else {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR");
            alert.setContentText("请确认读者是否存在！");
            type = alert.showAndWait();
            if(type.isPresent() && type.get() == ButtonType.OK) {
                awRcancelClick();
            }
        }
    }
}

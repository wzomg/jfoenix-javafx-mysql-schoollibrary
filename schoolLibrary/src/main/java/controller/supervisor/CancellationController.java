package controller.supervisor;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import java.util.Optional;

public class CancellationController {

    @FXML
    private Button RcSelect;

    @FXML
    private TextField inputRaccount;

    @FXML
    private JFXTextField judge;

    @FXML
    private Button cancelSure;

    @FXML
    private Label msg;

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    Alert alert = new Alert(Alert.AlertType.INFORMATION);

    //用来保存查询到时的读者id，避免直接获取选框内容导致不能查询到！
    private String tmpID = "";

    @FXML
    public void RcSelectClick() {
        String rID = inputRaccount.getText();
        System.out.println(rID);
        if(rID == null || rID.equals("")) {
            msg.setText("输入不能为空");
            return;
        }
        String sql = "SELECT COUNT(*) FROM readers WHERE RId = ?;";
        Long cnt = template.queryForObject(sql, Long.class, rID);
        if(cnt == 0) {
            msg.setText("读者不存在，请重新输入读者账号");
            return;
        }
        sql = "SELECT COUNT(*) FROM nborrowing WHERE NRId = ?;";
        cnt = template.queryForObject(sql, Long.class, rID);
        if(cnt > 0) {
            System.out.println("该读者借阅表记录不为空");
            judge.setText("否");
            msg.setText("");
        } else {
            //保存暂存记录
            tmpID = rID;
            System.out.println("该读者借阅表记录为空");
            judge.setText("是");
            msg.setText("");
        }
    }

    //清空内容
    public void clearContent() {
        inputRaccount.setText("");
        judge.setText("");
        msg.setText("");
        tmpID = "";
    }

    //点击注销
    @FXML
    public void cancelSureClick() {
        String isT = judge.getText();
        System.out.println(isT);
        String sql = "UPDATE readers SET RState = 2 WHERE RId = ?;";
        if(isT == null || isT.equals("")) {
            msg.setText("请先输入读者账号进行查询是否可以注销");
        } else if(isT.equals("是")){
            System.out.println(tmpID);
            int update = template.update(sql, tmpID);
            if(update > 0) {
                System.out.println("注销成功！");
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("读者：" + tmpID + "注销成功！");
                Optional<ButtonType> buttonType = alert.showAndWait();
                if(buttonType.isPresent() && buttonType.get() == ButtonType.OK) {
                    clearContent();
                } else {
                    clearContent();
                }
            } else {
                System.out.println("注销失败");
            }
        } else { // 否
            msg.setText("请先催促该读者归还所借书籍");
        }
    }
}

package controller.supervisor;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import java.util.List;
import java.util.Map;

public class SelectReaderController {

    @FXML
    private JFXTextField Rnum;

    @FXML
    private Button rslect;

    @FXML
    private TextField inputRM;

    @FXML
    private Label lReader;

    @FXML
    private JFXTextField Rid;

    @FXML
    private Label lPwd;

    @FXML
    private JFXTextField Rdate;

    @FXML
    private Label lDate;

    @FXML
    private Label lRnum;

    @FXML
    private JFXTextField Rname;

    @FXML
    private JFXTextField Rpassword;

    @FXML
    private JFXTextField Runit;

    @FXML
    private Label lName;

    @FXML
    private Label lStatus;

    @FXML
    private Label lSex;

    @FXML
    private JFXTextField Rsex;

    @FXML
    private JFXTextField Rstate;

    @FXML
    private Label labelMsg;

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @FXML
    public void rSelectClick() {
        String inputStr = inputRM.getText();
        String sql = "SELECT * FROM readers WHERE RId = ?;";
        List<Map<String, Object>> list = template.queryForList(sql, inputStr);
        if(list.size() == 0) {
            labelMsg.setText("读者卡号不存在，请输入正确的卡号~");
            Rname.setText("空");
            Rpassword.setText("空");
            Rstate.setText("空");
            Rsex.setText("空");
            Runit.setText("空");
            Rid.setText("空");
            Rnum.setText("空");
            Rdate.setText("空");
            return;
        } else {
            labelMsg.setText("");
        }
        Rname.setText((String) list.get(0).get("RName"));
        Rpassword.setText((String) list.get(0).get("RPasswd"));
        Integer o = (Integer) list.get(0).get("RState");
        Rstate.setText(o == 0 ? "正常使用中..." : "挂失中...");
        Rsex.setText((String) list.get(0).get("RSex"));
        Runit.setText((String) list.get(0).get("RUnit"));
        Rid.setText(inputStr);
        Rnum.setText(String.valueOf(list.get(0).get("Rnum")));
        Rdate.setText(String.valueOf(list.get(0).get("RDate")));
    }
}

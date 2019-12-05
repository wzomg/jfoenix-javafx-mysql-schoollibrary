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

public class SelectWorkerController {

    @FXML
    private Button Wselect;

    @FXML
    private TextField inputWM;

    @FXML
    private Label wId;

    @FXML
    private JFXTextField wid;

    @FXML
    private Label wPwd;

    @FXML
    private JFXTextField wdate;

    @FXML
    private Label wDate;

    @FXML
    private JFXTextField wname;

    @FXML
    private JFXTextField wpassword;

    @FXML
    private JFXTextField wunit;

    @FXML
    private Label wName;

    @FXML
    private Label wUnit;

    @FXML
    private Label wSex;

    @FXML
    private JFXTextField wsex;

    @FXML
    private Label wLabelMsg;

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @FXML
    public void wSelect() {
        String wId = inputWM.getText();
        String sql = "SELECT * FROM workers WHERE WId = ?;";
        List<Map<String, Object>> list = template.queryForList(sql, wId);
        if(list.size() == 0) {
            wLabelMsg.setText("工作人员卡号不存在，请输入正确的卡号~");
            wname.setText("空");
            wpassword.setText("空");
            wsex.setText("空");
            wunit.setText("空");
            wid.setText("空");
            wdate.setText("空");
            return;
        } else {
            wLabelMsg.setText("");
        }
        wname.setText((String) list.get(0).get("WName"));
        wpassword.setText((String) list.get(0).get("WPasswd"));
        wsex.setText((String) list.get(0).get("wSex"));
        wunit.setText((String) list.get(0).get("WUnit"));
        wid.setText(wId);
        wdate.setText(String.valueOf(list.get(0).get("WDate")));
    }
}

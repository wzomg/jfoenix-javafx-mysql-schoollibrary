package controller.reader;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.regex.Pattern;

public class ReserveCheckController {

    @FXML
    private AnchorPane reserveMainPane;

    @FXML
    private JFXTextField readerId;

    @FXML
    private JFXTextField bookId;

    @FXML
    private JFXTextField bookReserveStart;

    @FXML
    private DatePicker bookReserveEnd;

    @FXML
    private Button submitReserve;

    @FXML
    private Button cancelReserve;

    @FXML
    private Label labelMsg;

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    //初始化数据
    public void initData(String rdId, String bkId, String startTime) {
        bookId.setText(bkId);
        readerId.setText(rdId);
        bookReserveStart.setText(startTime);
        bookReserveEnd.setValue(null);
        bookReserveEnd.getEditor().setText("");
    }

    @FXML
    public void addReserveClick() {
        labelMsg.setText("");
        String rid = readerId.getText();
        String bid = bookId.getText();
        String startTime = bookReserveStart.getText();
        LocalDate endTime = bookReserveEnd.getValue();
        String editEndTime = null;
        boolean isT = false;
        String timeRegex1 = null;
        if(endTime == null) {
            editEndTime = bookReserveEnd.getEditor().getText();
            timeRegex1 = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|"+
                    "((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|"+
                    "((0[48]|[2468][048]|[3579][26])00))-02-29)$";
            if(editEndTime != null && !editEndTime.equals("")) {
                isT = Pattern.matches(timeRegex1, editEndTime);
                if(isT) { // 若输入正确才可赋值
                    endTime = LocalDate.parse(editEndTime,  DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                }
            }
            if(!isT) {
                labelMsg.setText("日期输入有误~");
                return;
            }
        }
        LocalDate sTime = LocalDate.parse(startTime, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        //预约开始日期必须比预约到书时间早，可以同一天
        if(endTime.isBefore(sTime)) {
            labelMsg.setText("预约到书日期不得早于预约开始日期~");
            //同时要清空遗留的信息
            bookReserveEnd.getEditor().setText("");
            bookReserveEnd.setValue(null);
            return;
        }
        String sql = "INSERT INTO qreserve(QBId, QRId, QSDate, QEDate) VALUES(?, ?, ?, ?);";

        int num = template.update(sql, bid, rid, sTime, endTime);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if(num > 0) {
            System.out.println("预约成功！");
            alert.setHeaderText("INFORMATION");
            alert.setContentText("预约成功");
            Optional<ButtonType> buttonType = alert.showAndWait();
            if(buttonType.isPresent() && buttonType.get() == ButtonType.OK) {
                //点击确定之后就销毁当前窗口
                cancelReserveClick();
            } else {
                //直接点击取消按钮也要销毁当前窗口
                cancelReserveClick();
            }

        } else {
            System.out.println("借阅失败！");
        }
    }

    //点击取消按钮，直接关闭面板
    @FXML
    public void cancelReserveClick() {
        Stage stage = (Stage) reserveMainPane.getScene().getWindow();
        stage.close();
    }
}

package controller.worker;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class CheckReasonController {

    @FXML
    private AnchorPane bookDetail;

    @FXML
    private JFXTextArea reason;

    @FXML
    private JFXTextField readerId;

    @FXML
    private JFXTextField bookId;

    @FXML
    private JFXTextField StartTime;

    @FXML
    private JFXTextField endTime;

    @FXML
    private Button submitBorrow;

    @FXML
    private Button cancelBorrow;

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    Alert alert = new Alert(Alert.AlertType.ERROR);

    //初始化表单数据
    public void initReasonData(String rid, String bid, String stime, String etime) {
        this.readerId.setText(rid);
        this.bookId.setText(bid);
        this.StartTime.setText(stime);
        this.endTime.setText(etime);
        this.reason.setText("");
    }

    //提交违约信息
    @FXML
    public void addBorrowClick() {
        String reasonWhy = reason.getText();
        if(reasonWhy == null || reasonWhy.equals("")) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("请填写违章原因！");
            alert.show();
            return;
        }
        String sql = "INSERT INTO uncomply(UBId, URId, USDate, UEDate, UReason) VALUES(?, ?, ?, ?, ?);";
        LocalDate sT = LocalDate.parse(StartTime.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate eT = LocalDate.parse(endTime.getText(),  DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        int cnt1 = template.update(sql, bookId.getText(), readerId.getText(), sT, eT, reasonWhy);
        if(cnt1 > 0) {
            System.out.println("提交成功");
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("提交违约信息成功");
            Optional<ButtonType> buttonType = alert.showAndWait();
            if(buttonType.isPresent() && buttonType.get() == ButtonType.OK) {
                cancelBorrowClick();
            } else {
                cancelBorrowClick();
            }
        } else {
            System.out.println("提交失败");
        }
    }

    @FXML
    public void cancelBorrowClick() {
        Stage stage = (Stage) bookDetail.getScene().getWindow();
        stage.close();
    }
}

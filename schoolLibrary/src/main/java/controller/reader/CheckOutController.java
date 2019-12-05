package controller.reader;

import com.jfoenix.controls.JFXTextField;
import controller.Main;
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

public class CheckOutController {

    @FXML
    private AnchorPane borrowMainPane;

    @FXML
    private JFXTextField bookId;

    @FXML
    private JFXTextField bookStartTime;

    @FXML
    private DatePicker bookEndTime;

    @FXML
    private Button submitBorrow;

    @FXML
    private JFXTextField readerId;

    @FXML
    private Button cancelBorrow;

    @FXML
    private Label labelMsg;

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    Alert alert = new Alert(Alert.AlertType.INFORMATION);

    private int numToRefresh = 0;

    //初始化数据
    public void initData(String rdId, String bkId, String startTime, int flag) {
        bookId.setText(bkId);
        readerId.setText(rdId);
        bookStartTime.setText(startTime);
        bookEndTime.setValue(null);
        bookEndTime.getEditor().setText("");
        this.numToRefresh = flag;
    }

    @FXML
    public void addBorrowClick() {
        //先清空，有提示再提示
        labelMsg.setText("");
        String rid = readerId.getText();
        String bid = bookId.getText();
        String startTime = bookStartTime.getText();
        LocalDate endTime = bookEndTime.getValue();
        String editEndTime = null;
        boolean isT = false;
        String timeRegex1 = null;
        if(endTime == null) {
            editEndTime = bookEndTime.getEditor().getText();
            System.out.println(editEndTime);
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
            System.out.println("还书日期不得早于借书日期~");
            labelMsg.setText("还书日期不得早于借书日期~");
            bookEndTime.getEditor().setText("");
            bookEndTime.setValue(null);
            return;
        }
        //向正在借阅表中添加一条记录，同时更新图书表中图书被借的次数+1
        String sql = "INSERT INTO nborrowing(NBId, NRId, NBTime, NBRTime) VALUES(?, ?, ?, ?);";
        String sql1 = "UPDATE book SET BCnt = BCnt + 1 WHERE BId = ?;";
        int update = template.update(sql1, bid);
        if(update > 0) {
            System.out.println("更新成功");
        } else {
            System.out.println("更新失败");
        }
        int num = template.update(sql, bid, rid, sTime, endTime);

        if(num > 0 && update > 0) {
            System.out.println("借阅成功！");
            alert.setHeaderText("INFORMATION");
            alert.setContentText("借阅成功");
            //再去修改列表中该书籍的借阅状态
            sql = "UPDATE book set BStatus = 0 WHERE BId = ?;";
            num = template.update(sql, bid);
            if(num > 0) {
                System.out.println("修改书籍借阅状态成功！");
                //刷新当前表单，直接操作当前项即可
                if(this.numToRefresh == 1) Main.rc.getBookLook().getSelectionModel().getSelectedItem().setBStatus("已借出");
                if(this.numToRefresh == 2) {
                    System.out.println("进入");
                    //借阅成功，那么也要将预约状态修改为1，表示完成预约
                    sql = "UPDATE qreserve SET QState = 1 WHERE QState = 2 AND QBId = ? AND QRId = ?;";
                    num = template.update(sql, bid, rid);
                    if(num > 0) {
                        System.out.println("成功更新预约状态为1");
                    } else {
                        System.out.println("更新失败！");
                    }
                }
            } else{
                System.out.println("修改失败！");
            }
            //更新读者在借册数 + 1 和最多可再借 - 1， 当管理员审核归还之后，再更新读者在借册数 - 1 和最多可再借 + 1
            sql = "UPDATE readers SET Rnum = Rnum + 1, RTotal = RTotal - 1 WHERE RId = ?;";
            num = template.update(sql, rid);
            if(num > 0) {
                System.out.println("更新读者在借册数 + 1 和最多可再借 - 1");
            } else {
                System.out.println("更新在借册数失败~~~~~~~~~~~~~");
            }
            //点击确定或者×都要销毁当前窗口
            Optional<ButtonType> buttonType = alert.showAndWait();
            if(buttonType.isPresent() && buttonType.get() == ButtonType.OK) {
                cancelBorrowClick();
            } else {
                cancelBorrowClick();
            }
        } else {
            System.out.println("借阅失败！");
        }
    }

    @FXML
    public void cancelBorrowClick() {
        Stage stage = (Stage) borrowMainPane.getScene().getWindow();
        stage.close();
    }
}

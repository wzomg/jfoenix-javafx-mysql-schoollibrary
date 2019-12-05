package controller.worker;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

public class AddBookController {

    @FXML
    private JFXTextField bookPress;

    @FXML
    private JFXTextField bookId;

    @FXML
    private JFXTextField bookName;

    @FXML
    private JFXTextField bookAuthor;

    @FXML
    private JFXTextField bookPrice;

    @FXML
    private ComboBox<String> bookType;

    @FXML
    private JFXTextArea bookSummary;

    @FXML
    private Button addSure;

    @FXML
    private Button addCancel;

    @FXML
    private Label labelBookId;

    @FXML
    private Label lableBookName;

    @FXML
    private Label lableIBookAuthor;

    @FXML
    private Label labelBookPrice;

    @FXML
    private Label lableIdBookPress;

    @FXML
    private Label labelBookType;

    @FXML
    private Label lableBookSummary;

    @FXML
    private Label lableBookIndate;

    @FXML
    private DatePicker bookDate;


    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    //每次添加书籍之前可以考虑显示上次查询的最新图书编号

    @FXML
    public void addCancelClick() {
        labelBookId.setText("");
        lableBookName.setText("");
        lableIBookAuthor.setText("");
        labelBookPrice.setText("");
        lableIdBookPress.setText("");
        labelBookType.setText("");
        lableBookSummary.setText("");
        lableBookIndate.setText("");

        bookId.setText("");
        bookName.setText("");
        bookAuthor.setText("");
        bookPress.setText("");
        bookPrice.setText("");
        bookDate.setValue(null);
        bookType.getSelectionModel().select(0);
        bookSummary.setText("");
    }

    @FXML
    public void addSureClick() {
        String bid = bookId.getText();
        String bName = bookName.getText();
        String bAuthor = bookAuthor.getText();
        String bPress = bookPress.getText();
        String bPrice = bookPrice.getText().trim();
        LocalDate bDate = bookDate.getValue();

        String bType = (String) bookType.getValue();
        String bSummary = bookSummary.getText();


        boolean flag = false;
        if(bid == null || bid.equals("")) {
            labelBookId.setText("书名不能为空~");
            flag = true;
        } else {
            labelBookId.setText("");
        }

        if(bName == null || bName.equals("")) {
            lableBookName.setText("书号不能为空~");
            flag = true;
        } else {
            lableBookName.setText("");
        }
        if(bAuthor == null || bAuthor.equals("")) {
            lableIBookAuthor.setText("作者不能空~");
            flag = true;
        } else {
            lableIBookAuthor.setText("");
        }
        if(bPress == null || bPress.equals("")) {
            lableIdBookPress.setText("出版社不能为空~");
            flag = true;
        } else {
            lableIdBookPress.setText("");
        }
        if(bPrice == null || bPrice.equals("")) {
            labelBookPrice.setText("价格不能为空~");
            flag = true;
        } else {
            labelBookPrice.setText("");
        }
        String ruTime = null;
        boolean isT = false;
        String timeRegex1 = null;
        if(bDate == null) {
            ruTime = bookDate.getEditor().getText();
            timeRegex1 = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|"+
                    "((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|"+
                    "((0[48]|[2468][048]|[3579][26])00))-02-29)$";
            if(ruTime != null && !ruTime.equals("")) {
                isT = Pattern.matches(timeRegex1, ruTime);
                if(isT) { // 若输入正确才可赋值
                    bDate = LocalDate.parse(ruTime,  DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                }
            }
            if(!isT) {
                lableBookIndate.setText("日期输入有误~");
                return;
            }
        } else {
            lableBookIndate.setText("");
        }

        if(bType == null || bType.equals("图书类别")) {
            labelBookType.setText("请选择图书类别");
            flag = true;
        } else {
            labelBookType.setText("");
        }

        //这里可以提示不能输入太多的文字！！！！！！！
        if(bSummary == null || bSummary.equals("")) {
            lableBookSummary.setText("摘要不能为空");
            flag = true;
        } else {
            lableBookSummary.setText("");
        }
        if(flag) return;
        String sql = "SELECT COUNT(*) FROM book WHERE BId = ?";
        Long num = template.queryForObject(sql, Long.class, bid);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if(num > 0) {
            System.out.println("书号重复！");
            alert.setContentText("书号不能重复，请重新输入！");
            alert.setHeaderText("ERROR");
            //此处直接直接展示，不做任何响应事件
            alert.show();
            return;
        }
        sql = "SELECT TId FROM tkind WHERE TName = ?;";
        Map<String, Object> map = template.queryForMap(sql, bType);
        String bkindId = (String) map.get("TId");
        sql = "INSERT INTO book(BId, BName, BAuthor, BKind, BPrice, BDate, BPress, BSummary) VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
        int cnt = template.update(sql, bid, bName, bAuthor, bkindId, Double.valueOf(bPrice), bDate, bPress, bSummary);
        if(cnt > 0) {
            System.out.println("成功添加书籍！");
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setHeaderText("添加读者状态");
            alert.setContentText("成功添加一本书籍：" + bName + "。");
            Optional<ButtonType> type = alert.showAndWait();
            if(type.isPresent() && type.get() == ButtonType.OK) {
                //清空表单数据
                addCancelClick();
            }
        } else {
            System.out.println("添加失败！");
        }
    }
}

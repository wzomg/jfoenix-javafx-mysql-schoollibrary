package controller.worker;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class AltBookMSController {

    @FXML
    private JFXTextField alBookPress;

    @FXML
    private JFXTextField alBookId;

    @FXML
    private JFXTextField alBookName;

    @FXML
    private JFXTextField alAuthor;

    @FXML
    private JFXTextField alPrice;

    @FXML
    private ComboBox<String> alBookKind;

    @FXML
    private JFXTextArea alSummary;

    @FXML
    private Button bselect;

    @FXML
    private TextField inputBms;

    @FXML
    private Label lableBookId;

    @FXML
    private Label labelBookName;

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
    private Button altSure;

    @FXML
    private Button altCancel;

    @FXML
    private DatePicker alDatePicker;

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    //初始化数据
    public void clearData() {
        System.out.println("清空数据");
        lableBookId.setText("");
        labelBookName.setText("");
        labelBookPrice.setText("");
        labelBookType.setText("");
        lableBookIndate.setText("");
        lableIBookAuthor.setText("");
        lableBookSummary.setText("");
        lableIdBookPress.setText("");

        alBookId.setText("");
        alBookName.setText("");
        alAuthor.setText("");
        alBookPress.setText("");
        alPrice.setText("");
        alDatePicker.setValue(null);
        alDatePicker.getEditor().setText("");
        alBookKind.getSelectionModel().select(0);
        alSummary.setText("");
    }

    /**
     * 修改书籍信息
     */
    @FXML
    public void altSureClick() {
        //书号不能编辑
        String bid = alBookId.getText(); //获取固定的书籍id
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("ERROR");
        alert.setContentText("请输入书号查询，再修改~");
        if(bid.equals("")) {
            alert.show();
            return;
        }
        String bName = alBookName.getText();
        String bAuthor = alAuthor.getText();
        String bPress = alBookPress.getText();
        String bPrice = alPrice.getText().trim();
        //可以有两种格式输入，插入数据库时自动转化成此种格式：yyyy-MM-dd
        LocalDate bDate = alDatePicker.getValue();
        String bType = (String) alBookKind.getValue();
        String bSummary = alSummary.getText();

        boolean flag = false;

        if(bName == null || bName.equals("")) {
            labelBookName.setText("书名不能为空~");
            flag = true;
        } else {
            labelBookName.setText("");
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
        if(bDate == null) {
            //正则表达式匹配
            String timeRegex1 = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|"+
                    "((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|"+
                    "((0[48]|[2468][048]|[3579][26])00))-02-29)$";
            String text = alDatePicker.getEditor().getText();
            System.out.println(text);
            //匹配字符串
            boolean isT = Pattern.matches(timeRegex1, text);
            if(!isT) {
                lableBookIndate.setText("日期格式错误~");
                flag = true;
            }
            if(text != null && !text.equals("")) {
                bDate = LocalDate.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }
            //字符串转换格式为LocalDate
            System.out.println(bDate);
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
        //也可以设置更长的存储空间
        if(bSummary == null || bSummary.equals("")) {
            lableBookSummary.setText("摘要不能为空");
            flag = true;
        } else {
            lableBookSummary.setText("");
        }
        if(flag) return;
        String sql = "SELECT TId FROM tkind WHERE TName = ?;";
        Map<String, Object> map = template.queryForMap(sql, bType);
        String bkindId = (String) map.get("TId");
        sql = "UPDATE book SET BName = ?, BAuthor = ?, BKind = ?, BPrice = ?, BDate = ?, BPress = ?, BSummary = ? WHERE BId = ?;";
        int cnt = template.update(sql, bName, bAuthor, bkindId, Double.valueOf(bPrice), bDate, bPress, bSummary, bid);
        if(cnt > 0) {
            System.out.println("成功修改书籍！");
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setHeaderText("修改状态");
            alert.setContentText("成功修改一本书籍");
            alert.show();
        } else {
            System.out.println("修改失败！");
        }
    }

    @FXML
    public void bSelectClick() {
        System.out.println("点击查询按钮");
        //要清空两边的空格
        String bookId = inputBms.getText().trim();
        System.out.println("输入的书号为" + bookId);
        if(bookId.equals("")) {
            return;
        }
        String sql = "SELECT * FROM book, tkind WHERE book.BKind = tkind.TId AND book.BId = ?;";
        List<Map<String, Object>> bookLen = template.queryForList(sql, bookId);
        Alert alert = new Alert(Alert.AlertType.WARNING);
        if(bookLen.size() == 0) {
            alert.setContentText("书号不存在，请重新输入！");
            alert.setHeaderText("WARNING");
            alert.show();
            clearData();
            return;
        }
        alBookId.setText((String) bookLen.get(0).get("BId"));
        alBookName.setText((String) bookLen.get(0).get("BName"));
        alAuthor.setText((String) bookLen.get(0).get("BAuthor"));
        alBookPress.setText((String) bookLen.get(0).get("BPress"));
        alPrice.setText(String.valueOf(bookLen.get(0).get("BPrice")));
        System.out.println(bookLen.get(0).get("TName"));
        alBookKind.getSelectionModel().select((String) bookLen.get(0).get("TName"));
        alDatePicker.getEditor().setText(bookLen.get(0).get("BDate").toString());
        alSummary.setText((String) bookLen.get(0).get("BSummary"));
    }
}

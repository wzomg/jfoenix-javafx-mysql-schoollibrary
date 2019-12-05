package controller.common;

import com.jfoenix.controls.JFXTextField;
import emp.EmpBookLook;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

public class BookDetailController {

    @FXML
    private JFXTextField bookId;

    @FXML
    private TextArea bookSummary;

    @FXML
    private JFXTextField bookAuthor;

    @FXML
    private JFXTextField bookType;

    @FXML
    private JFXTextField bookName;

    @FXML
    private JFXTextField bookPrice;

    @FXML
    private JFXTextField bookPress;

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    public void initDetail(EmpBookLook b) {
        bookId.setText(b.getBId());
        bookSummary.setText(b.getBSummary());
        bookAuthor.setText(b.getBAuthor());
        bookName.setText(b.getBName());
        bookPrice.setText(String.valueOf(b.getBPrice()));
        bookPress.setText(b.getBPress());
        bookType.setText(b.getTName());
    }
}

package controller.reader;

import emp.EmpBookLook;
import entity.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import java.util.List;

public class BorrowHistoryController {

    @FXML
    private TableView<EmpBookLook> bhTable;

    @FXML
    private TableColumn<EmpBookLook, String> bhBookId;

    @FXML
    private TableColumn<EmpBookLook, String> bhBookName;

    @FXML
    private TableColumn<EmpBookLook, String> bhBookAuthor;

    @FXML
    private TableColumn<EmpBookLook, String> bhBookStart;

    @FXML
    private TableColumn<EmpBookLook, String> bhBookEnd;

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    public void initHistoryData(String readerId) {
        ObservableList<EmpBookLook> data = FXCollections.observableArrayList();
        String sql = "SELECT * FROM readers, hreturn, book WHERE readers.RId = hreturn.HRId AND book.BId = hreturn.HBId AND hreturn.HRId = ?;";
        List<Book> bookHistoryList = template.query(sql, new BeanPropertyRowMapper<Book>(Book.class), readerId);
        System.out.println("查询结果的数量有" + bookHistoryList.size());
        for(int i = 0; i < bookHistoryList.size(); ++i) {
            System.out.println(bookHistoryList.get(i));
            data.add(new EmpBookLook(bookHistoryList.get(i)));
        }
        bhTable.setItems(data);
        bhBookId.setCellValueFactory(new PropertyValueFactory<EmpBookLook, String>("BId"));
        bhBookName.setCellValueFactory(new PropertyValueFactory<EmpBookLook, String>("BName"));
        bhBookAuthor.setCellValueFactory(new PropertyValueFactory<EmpBookLook, String>("BAuthor"));
        bhBookStart.setCellValueFactory(new PropertyValueFactory<EmpBookLook, String>("HBTime"));
        bhBookEnd.setCellValueFactory(new PropertyValueFactory<EmpBookLook, String>("HBRTime"));
    }
}

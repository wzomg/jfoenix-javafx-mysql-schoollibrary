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

public class NowBorrowController {

    @FXML
    private TableView<EmpBookLook> nBBookView;

    @FXML
    private TableColumn<EmpBookLook, String> nBBookId;

    @FXML
    private TableColumn<EmpBookLook, String> nBBookName;

    @FXML
    private TableColumn<EmpBookLook, String> nBBookAuthor;

    @FXML
    private TableColumn<EmpBookLook, String> nBBookStart;

    @FXML
    private TableColumn<EmpBookLook, String> nBBookEnd;

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    //初始化页面的数据
    public void initData(String readerId) {
        String sql = "SELECT * FROM nborrowing, readers, book WHERE book.BId = nborrowing.NBId AND readers.RId = nborrowing.NRId AND nborrowing.NState = 1 AND readers.RId = ?";
        List<Book> nbList = template.query(sql, new BeanPropertyRowMapper<>(Book.class), readerId);
        ObservableList<EmpBookLook> data = FXCollections.observableArrayList();
        System.out.println("查询结果的数量有" + nbList.size());
        for(int i = 0; i < nbList.size(); ++i) {
            System.out.println(nbList.get(i));
            data.add(new EmpBookLook(nbList.get(i)));
        }
        nBBookView.setItems(data);
        nBBookId.setCellValueFactory(new PropertyValueFactory<EmpBookLook, String>("BId"));
        nBBookName.setCellValueFactory(new PropertyValueFactory<EmpBookLook, String>("BName"));
        nBBookAuthor.setCellValueFactory(new PropertyValueFactory<EmpBookLook, String>("BAuthor"));
        nBBookStart.setCellValueFactory(new PropertyValueFactory<EmpBookLook, String>("NBTime"));
        nBBookEnd.setCellValueFactory(new PropertyValueFactory<EmpBookLook, String>("NBRTime"));
    }
}
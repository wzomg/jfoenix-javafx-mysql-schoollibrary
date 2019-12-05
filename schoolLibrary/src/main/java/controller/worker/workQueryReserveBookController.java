package controller.worker;

import emp.EmpBookLook;
import entity.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import java.util.List;

public class workQueryReserveBookController {

    @FXML
    private AnchorPane reserveMainPane;

    @FXML
    private TableView<EmpBookLook> qreserveTable;

    @FXML
    private TableColumn<EmpBookLook, String> qreserveBookId;

    @FXML
    private TableColumn<EmpBookLook, String> qreserveBookName;

    @FXML
    private TableColumn<EmpBookLook, String> qreserveStart;

    @FXML
    private TableColumn<EmpBookLook, String> qreserveEnd;

    @FXML
    private TableColumn<EmpBookLook, String> qreserveStatus;

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    private ObservableList<EmpBookLook> data = FXCollections.observableArrayList();

    private Alert alert = new Alert(Alert.AlertType.WARNING);

    public void initReserveData(String readerId) {
        data.clear();
        String sql = "SELECT * FROM readers, qreserve, book WHERE readers.RId = qreserve.QRId AND book.BId = qreserve.QBId AND qreserve.QRId = ?;";
        List<Book> bookReserveList = template.query(sql, new BeanPropertyRowMapper<Book>(Book.class), readerId);
        System.out.println("查询结果的数量有" + bookReserveList.size());
        for(int i = 0; i < bookReserveList.size(); ++i) {
            System.out.println(bookReserveList.get(i));
            data.add(new EmpBookLook(bookReserveList.get(i)));
        }
        qreserveTable.setItems(data);
        qreserveBookId.setCellValueFactory(new PropertyValueFactory<EmpBookLook, String>("BId"));
        qreserveBookName.setCellValueFactory(new PropertyValueFactory<EmpBookLook, String>("BName"));
        qreserveStart.setCellValueFactory(new PropertyValueFactory<EmpBookLook, String>("QSDate"));
        qreserveEnd.setCellValueFactory(new PropertyValueFactory<EmpBookLook, String>("QEDate"));
        qreserveStatus.setCellValueFactory(new PropertyValueFactory<EmpBookLook, String>("QState"));
    }
}

package controller.reader;

import controller.Main;
import emp.EmpBookLook;
import entity.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class ReserveBookController {

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

    @FXML
    private MenuItem cancelReserve;

    @FXML
    private MenuItem toBorrowBook;

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    private ObservableList<EmpBookLook> data = FXCollections.observableArrayList();

    private Alert alert = new Alert(Alert.AlertType.WARNING);

    @FXML
    public void cancelReserveClick() {
        if(Main.rc.getrStatus().equals("挂失")) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR");
            alert.setContentText("当前借阅证为挂失状态，请联系工作人员重新办理借阅证~");
            alert.show();
            return;
        }

        EmpBookLook bookReserve = qreserveTable.getSelectionModel().getSelectedItem();
        if(bookReserve.getQState().equals("预约成功")) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR");
            alert.setContentText("《" + bookReserve.getBName() + "》已经预约成功，不能取消预约！");
            alert.show();
            return;
        }

        //删除表语句，同时刷新表格
        String sql = "DELETE FROM qreserve WHERE QState = ? AND QBId = ? AND QRId = ? AND QSDate = ? AND QEDate = ?;";
        LocalDate sTime = LocalDate.parse(bookReserve.getQSDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate eTime = LocalDate.parse(bookReserve.getQEDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        int update = 0;
        alert.setAlertType(Alert.AlertType.WARNING);
        alert.setContentText("确定取消预约《"+ bookReserve.getBName() + "》这本书吗？");
        Optional<ButtonType> buttonType1 = alert.showAndWait();
        if(buttonType1.isPresent() && buttonType1.get() == ButtonType.OK) {
            System.out.println("已经点击确认取消预约");
            update = template.update(sql, bookReserve.getQState().equals("等待处理") ? 0 : 2, bookReserve.getBId(), bookReserve.getRId(), sTime, eTime);
            if(update > 0) {
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setHeaderText("INFORMATION");
                System.out.println("成功取消预约");
                alert.setContentText("成功取消预约《" + bookReserve.getBName() + "》！");
                alert.show();
                //刷新表单
                data.remove(bookReserve);
            } else {
                System.out.println("操作失败");
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setHeaderText("ERROR");
                alert.setContentText("操作失败");
                alert.show();
            }
        } else {
            System.out.println("点×了");
        }
    }

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

    //可借阅，直接点击借阅
    @FXML
    public void toBorrowBookClick() {
        System.out.println("点击借阅");
        EmpBookLook bookBorrow = qreserveTable.getSelectionModel().getSelectedItem();
        if(bookBorrow.getQState().equals("可以借阅")) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/reader/checkout.fxml"));
                Parent parent = loader.load();
                CheckOutController coc = loader.getController();
                Stage stage = new Stage(StageStyle.DECORATED);
                stage.setTitle("借阅信息");
                stage.setScene(new Scene(parent, Main.sonWidth, Main.sonHeight));
                stage.setResizable(false);
                Stage stageF = (Stage) reserveMainPane.getScene().getWindow();
                //通过数据库查询来设置借阅和归还信息，设置模态
                stage.initOwner(stageF);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
                // 填充数据，查询数据库
                String rId = Main.rc.getReaderId();
                System.out.println(rId);
                //初始化数据
                coc.initData(Main.rc.getReaderId(), bookBorrow.getBId(), LocalDate.now().toString(), 2);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR");
            alert.setContentText("该图书暂时不能借阅~");
            alert.show();
        }
    }

    //刷新个人预约表
    @FXML
    public void refreshClick() {
        initReserveData(Main.rc.getReaderId());
    }
}

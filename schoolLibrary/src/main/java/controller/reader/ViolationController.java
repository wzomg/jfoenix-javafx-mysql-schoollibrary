package controller.reader;

import controller.Main;
import controller.common.ReasonDetailController;
import emp.EmpBookLook;
import entity.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import java.io.IOException;
import java.util.List;

public class ViolationController {

    @FXML
    private TableView<EmpBookLook> violationTable;

    @FXML
    private TableColumn<EmpBookLook, String> uBid;

    @FXML
    private TableColumn<EmpBookLook, String> uBookname;

    @FXML
    private TableColumn<EmpBookLook, String> usDate;

    @FXML
    private TableColumn<EmpBookLook, String> ueDate;

    @FXML
    private TableColumn<EmpBookLook, String> uReason;

    @FXML
    private MenuItem uReasonDeatil;

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    private ObservableList<EmpBookLook> data = FXCollections.observableArrayList();

    @FXML
    public void uReasonDeatilClick() {
        EmpBookLook selectItem = violationTable.getSelectionModel().getSelectedItem();
        //记载页面
        System.out.println("点击违章记录详情");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/common/reasonDetail.fxml"));
            Parent parent = loader.load();
            ReasonDetailController rdc = loader.getController();
            //填充数据 一个有纯白背景和平台装饰的舞台
            Stage stage = new Stage(StageStyle.DECORATED);
            Stage faStage = (Stage) Main.wc.getBorderPane().getScene().getWindow();
            stage.setTitle("违章详情");
            stage.setScene(new Scene(parent, Main.sonWidth, Main.sonHeight));
            stage.setResizable(false);
            //注意要设置模态窗口
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(faStage);
            stage.show();
            rdc.initReasonData(selectItem.getRId(), selectItem.getBId(), selectItem.getUSDate(), selectItem.getUEDate(), selectItem.getUReason());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //初始化数据
    public void initUncomplyData(String readerID) {
        data.clear();
        String sql = "SELECT * FROM uncomply, book, readers WHERE book.BId = uncomply.UBId AND readers.RId = uncomply.URId AND uncomply.URId = ?;";
        List<Book> pUncomplyList = template.query(sql, new BeanPropertyRowMapper<Book>(Book.class), readerID);
        System.out.println("查询结果的数量有" + pUncomplyList.size());
        for(int i = 0; i < pUncomplyList.size(); ++i) {
            System.out.println(pUncomplyList.get(i));
            data.add(new EmpBookLook(pUncomplyList.get(i)));
        }
        violationTable.setItems(data);
        uBid.setCellValueFactory(new PropertyValueFactory<EmpBookLook, String>("BId"));
        uBookname.setCellValueFactory(new PropertyValueFactory<EmpBookLook, String>("BName"));
        usDate.setCellValueFactory(new PropertyValueFactory<EmpBookLook, String>("USDate"));
        ueDate.setCellValueFactory(new PropertyValueFactory<EmpBookLook, String>("UEDate"));
        uReason.setCellValueFactory(new PropertyValueFactory<EmpBookLook, String>("UReason"));
    }
}

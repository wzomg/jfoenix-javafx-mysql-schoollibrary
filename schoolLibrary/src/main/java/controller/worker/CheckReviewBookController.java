package controller.worker;

import emp.EmpBookLook;
import entity.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CheckReviewBookController {

    @FXML
    private TableView<EmpBookLook> reviewBookTable;

    @FXML
    private TableColumn<EmpBookLook, String> reviewRid;

    @FXML
    private TableColumn<EmpBookLook, String> reviewBid;

    @FXML
    private TableColumn<EmpBookLook, String> reviewBookName;

    @FXML
    private TableColumn<EmpBookLook, String> reviewBookStart;

    @FXML
    private TableColumn<EmpBookLook, String> reviewBookEnd;

    @FXML
    private MenuItem searchReview;

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    private ObservableList<EmpBookLook> data = FXCollections.observableArrayList();

    private Alert alert = new Alert(Alert.AlertType.INFORMATION);

    //初始化数据
    public void initReserveCheckData() {
        data.clear();
        String sql = "SELECT * FROM readers, qreserve, book WHERE readers.RId = qreserve.QRId AND book.BId = qreserve.QBId AND qreserve.QState = 0;";
        List<Book> ReserveCheckList = template.query(sql, new BeanPropertyRowMapper<Book>(Book.class));
        System.out.println("查询结果的数量有" + ReserveCheckList.size());
        for(int i = 0; i < ReserveCheckList.size(); ++i) {
            System.out.println(ReserveCheckList.get(i));
            data.add(new EmpBookLook(ReserveCheckList.get(i)));
        }
        reviewBookTable.setItems(data);
        reviewRid.setCellValueFactory(new PropertyValueFactory<EmpBookLook, String>("RId"));
        reviewBid.setCellValueFactory(new PropertyValueFactory<EmpBookLook, String>("BId"));
        reviewBookName.setCellValueFactory(new PropertyValueFactory<EmpBookLook, String>("BName"));
        reviewBookStart.setCellValueFactory(new PropertyValueFactory<EmpBookLook, String>("QSDate"));
        reviewBookEnd.setCellValueFactory(new PropertyValueFactory<EmpBookLook, String>("QEDate"));
    }

    //查询
    @FXML
    public void searchReviewClick() {
        System.out.println("查询可借");
        EmpBookLook reviewBookItem = reviewBookTable.getSelectionModel().getSelectedItem();
        System.out.println(reviewBookItem);
        //此处先去查询书籍是否可借
        String sql = "SELECT * FROM book WHERE BId = ?;";
        Map<String, Object> forMap = template.queryForMap(sql, reviewBookItem.getBId());
        System.out.println(forMap);
        Integer bst = (Integer) forMap.get("BStatus");
        System.out.println(bst);
        Optional<ButtonType> buttonType = null;
        if(bst == 1) {
            //可借推送消息
            alert.setHeaderText("查询状态");
            alert.setContentText("目前馆藏有此书，请问是否推送通知？");
            buttonType = alert.showAndWait();
            if(buttonType.isPresent() && buttonType.get() == ButtonType.OK) {
                //接下来就去修改预约表中预约状态为2，推送可借消息，并刷新表格（0表示申请预约，2表示推送可借消息）
                sql = "UPDATE qreserve SET QState = 2 WHERE QState = 0 AND QBId = ? AND QRId = ? AND QSDate = ? AND QEDate = ?;";
                LocalDate sTime = LocalDate.parse(reviewBookItem.getQSDate(),  DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                LocalDate eTime = LocalDate.parse(reviewBookItem.getQEDate(),  DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                int update = template.update(sql, reviewBookItem.getBId(), reviewBookItem.getRId(), sTime, eTime);
                if(update > 0) {
                    System.out.println("更新成功");
                    alert.setHeaderText("INFORMATION");
                    alert.setContentText("推送成功");
                    data.remove(reviewBookItem);
                } else {
                    System.out.println("推送失败");
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setHeaderText("ERROR");
                    alert.setContentText("推送失败");
                }
                alert.show();
            }
            //点击×或者取消都不处理
        } else {
            System.out.println("已借出，暂时不能推送消息");
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setHeaderText("WARNING");
            alert.setContentText("该书籍已借出，暂时不能审核通过！");
            alert.show();
        }
    }
}

package controller.reader;
import controller.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class ReaderPmsgController {

    @FXML
    private TextField raccount;

    @FXML
    private TextField rname;

    @FXML
    private TextField rsex;

    @FXML
    private TextField runit;

    @FXML
    private TextField regdate;

    @FXML
    private TextField rNum;

    @FXML
    private StackPane stackPane;

    @FXML
    private TextField rTotal;

    @FXML
    private TextField rStatus;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label progressValue;

    //分类
    private PieChart  bookClassification;

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    //初始化数据
    public void initData(Map<String, Object> res) {
        rname.setText((String) res.get("RName"));
        raccount.setText((String) res.get("RId"));
        rsex.setText((String) res.get("RSex"));
        runit.setText((String) res.get("RUnit"));
        regdate.setText(String.valueOf(res.get("RDate")));
        rNum.setText(String.valueOf(res.get("Rnum")));
        rTotal.setText(String.valueOf(res.get("RTotal")));
        rStatus.setText(((Integer) res.get("RState")) == 1 ? "挂失" : "正常");
        bookClassification = new PieChart(getClassBookGraphStatistics());
        stackPane.getChildren().clear();
        stackPane.getChildren().add(bookClassification);
        String sql1 = "SELECT COUNT(*) FROM hreturn WHERE HRId = ?;";
        String sql2 = "SELECT COUNT(*) FROM hreturn;";
        Long cnt1 = template.queryForObject(sql1, Long.class, Main.rc.getReaderId());
        Long cnt2 = template.queryForObject(sql2, Long.class);
        System.out.println("总共的历史记录有" + cnt2 + "条。");
        System.out.println("当前读者的历史记录有" + cnt1 + "条。");
        double progressVal = 1.0 * cnt1 / cnt2;
        progressBar.setProgress(progressVal);
        progressValue.setText((int)(progressVal * 100) + "%");
        System.out.println((int)(progressVal * 100) + "%");
    }

    public ObservableList<PieChart.Data> getClassBookGraphStatistics() {
        //图标数据可能实时更新，并不会变，所以每次需要重新初始化
        ObservableList<PieChart.Data> dataChart = FXCollections.observableArrayList();
        String sql = "SELECT COUNT(*) AS classifyNum, TName FROM book, tkind, hreturn WHERE book.BId = hreturn.HBId AND book.BKind = tkind.TId AND hreturn.HRId = ? GROUP BY tkind.TId ORDER BY classifyNum DESC;";
        List<Map<String, Object>> classifyBooks = template.queryForList(sql, Main.rc.getReaderId());
        System.out.println("一共有：" + classifyBooks.size() + "种！");
        for(int i = 0, len = classifyBooks.size(); i < len; ++i) {
            String btName = (String) classifyBooks.get(i).get("TName");
            String btCnt = classifyBooks.get(i).get("classifyNum").toString();
            dataChart.add(new PieChart.Data(btName + "(" + btCnt + ")", Integer.parseInt(btCnt)));
        }
        return dataChart;
    }
}

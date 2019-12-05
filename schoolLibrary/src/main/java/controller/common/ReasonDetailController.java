package controller.common;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class ReasonDetailController {

    @FXML
    private AnchorPane bookDetail;

    @FXML
    private JFXTextArea reason;

    @FXML
    private JFXTextField readerId;

    @FXML
    private JFXTextField bookId;

    @FXML
    private JFXTextField startTime;

    @FXML
    private JFXTextField endTime;

    //初始化表单数据
    public void initReasonData(String rid, String bid, String stime, String etime, String reasonW) {
        this.readerId.setText(rid);
        this.bookId.setText(bid);
        this.startTime.setText(stime);
        this.endTime.setText(etime);
        this.reason.setText(reasonW);
    }
}

package controller.worker;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
public class WorkerPeCeController {

    @FXML
    private JFXTextField wId;

    @FXML
    private JFXTextField wDate;

    @FXML
    private JFXTextField wName;

    @FXML
    private JFXTextField wUnit;

    @FXML
    private JFXTextField wSex;

    public void initData(String wId, String wDate, String wName,  String wUnit, String wSex ) {
        this.wId.setText(wId);
        this.wName.setText(wName);
        this.wDate.setText(wDate);
        this.wUnit.setText(wUnit);
        this.wSex.setText(wSex);
    }
}

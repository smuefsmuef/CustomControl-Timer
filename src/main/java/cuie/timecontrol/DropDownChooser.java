package cuie.timecontrol;

import java.time.LocalTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;


public class DropDownChooser extends VBox {
    private static final String STYLE_CSS = "dropDownChooser.css";

    private final MyTimeControl timeControl;

    ObservableList<LocalTime> times =
        FXCollections.observableArrayList(
            LocalTime.of(8, 0),
            LocalTime.of(12, 0),
            LocalTime.of(16, 0),
            LocalTime.of(20, 0),
            LocalTime.of(0, 0)
        );
    private ListView<LocalTime> list;


    public DropDownChooser(MyTimeControl timeControl) {
        this.timeControl = timeControl;
        initializeSelf();
        initializeParts();
        layoutParts();
        setupEventListeners();
        setupBindings();

    }

    private void initializeSelf() {
        String stylesheet = getClass().getResource(STYLE_CSS).toExternalForm();
        getStylesheets().add(stylesheet);

        getStyleClass().add("dropdown-chooser");
    }

    private void initializeParts() {
        list = new ListView<LocalTime>(times);

    }

    private void layoutParts() {
        getChildren().addAll( list);
    }

    private void setupEventListeners() {
        list.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                timeControl.setTime(list.getSelectionModel().getSelectedItem()); // 20:00
            }
        });
    }



        private void setupBindings() {
    }

}

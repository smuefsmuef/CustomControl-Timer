package cuie.timecontrol;

import java.time.LocalTime;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
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
            LocalTime.of(23, 59)
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
        list.setPrefWidth(100);
        list.setPrefHeight(70);

        getChildren().addAll(list);
    }

    private void setupEventListeners() {
        list.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                timeControl.setTime(list.getSelectionModel().getSelectedItem()); // z. B. 20:00
            }
        });

        list.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                timeControl.setTime(list.getSelectionModel().getSelectedItem()); // z. B. 20:00
            }

        });
    }

    private void setupBindings() {
    }

}

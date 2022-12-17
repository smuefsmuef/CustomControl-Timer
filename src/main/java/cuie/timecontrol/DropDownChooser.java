package cuie.timecontrol;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;


public class DropDownChooser extends VBox {
    private static final String STYLE_CSS = "dropDownChooser.css";

    private final MyTimeControl timeControl;

    private ListView list;

    ObservableList<String> options =
        FXCollections.observableArrayList(
            "08:00",
            "12:00",
            "16:00",
            "20:00"
        );


    public DropDownChooser(MyTimeControl timeControl) {
        this.timeControl = timeControl;
        initializeSelf();
        initializeParts();
        layoutParts();
        setupBindings();
    }

    private void initializeSelf() {
        String stylesheet = getClass().getResource(STYLE_CSS).toExternalForm();
        getStylesheets().add(stylesheet);

        getStyleClass().add("dropdown-chooser");


    }

    private void initializeParts() {
        list = new ListView<>(options);
    }

    private void layoutParts() {
        getChildren().addAll( list);
    }

    private void setupBindings() {
    }

}

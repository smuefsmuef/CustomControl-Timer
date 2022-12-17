package cuie.timecontrol;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;
import javafx.util.Duration;

import static javafx.scene.paint.Color.LIGHTYELLOW;
import static javafx.scene.paint.Color.RED;
import static javafx.scene.paint.Color.TRANSPARENT;
import static javafx.scene.paint.Color.WHITE;
import static javafx.scene.paint.Color.rgb;
import static javafx.scene.text.TextAlignment.CENTER;


class MyTimeSkin extends SkinBase<MyTimeControl> {

    private static final Color BLUE = rgb(54, 84, 112);

    //todo: replace it
    private TextField timeField;
    private Label captionLabel;
    private Label readOnlyTimeLabel;

    private Rectangle pillBackground;
    private Rectangle pillBlinker;
    private Rectangle pillForegroundRect;
    private Circle pillForegroundCircle;
    private Rectangle pillShadow;

    private Timeline alarm;

    private Pane drawingPane;

    MyTimeSkin(MyTimeControl control) {
        super(control);
        initializeSelf();
        initializeParts();
        initializeDrawingPane();
        layoutParts();
        initializeAnimations();
        setupEventListeners();
        setupValueChangeListeners();
        setupBindings();
    }

    private void initializeSelf() {
        getSkinnable().addStylesheetFiles("style.css");
    }

    private void initializeParts() {
        timeField = new TextField();
        timeField.getStyleClass().add("time-field");
        timeField.setLayoutX(255);
        timeField.setLayoutY(50);
        timeField.setPrefSize(220, 100);
        timeField.setAlignment(Pos.CENTER);
        timeField.setPadding(new Insets(2, 10, 2, 2));
        timeField.setBorder(new Border(new BorderStroke(Color.rgb(250, 0, 0),
            BorderStrokeStyle.DASHED,
            CornerRadii.EMPTY,
            new BorderWidths(3.0, 3.0, 3.0, 3))));

        readOnlyTimeLabel = new Label();
        readOnlyTimeLabel.getStyleClass().add("read-only-time-label");
        readOnlyTimeLabel.setLayoutX(255);
        readOnlyTimeLabel.setLayoutY(50);
        readOnlyTimeLabel.setPrefSize(220, 100);
        readOnlyTimeLabel.setTextAlignment(CENTER);
        readOnlyTimeLabel.setPadding(new Insets(2, 10, 2, 2));

        captionLabel = new Label();
        captionLabel.getStyleClass().add("caption");
        captionLabel.setLayoutX(10);
        captionLabel.setLayoutY(0);
        captionLabel.setPrefSize(220, 180);
        captionLabel.setTextAlignment(CENTER);
        captionLabel.setPadding(new Insets(2, 10, 2, 2));

        pillBackground = new Rectangle(0, 0, 500, 200);
        pillBackground.setFill(BLUE);
        pillBackground.getStyleClass().add("pill-background");
        pillBackground.setArcWidth(200.0);
        pillBackground.setArcHeight(200.0);

        pillBlinker = new Rectangle(0, 0, 500, 200);
        pillBlinker.setFill(RED);
        pillBlinker.setArcWidth(200.0);
        pillBlinker.setArcHeight(200.0);

        pillForegroundRect = new Rectangle(250, 10, 150, 180);
        pillForegroundRect.setFill(WHITE);
        pillForegroundCircle = new Circle(400, 100, 90);
        pillForegroundCircle.setFill(WHITE);

        pillShadow = new Rectangle(80, 10, 100, 10);
        pillShadow.setFill(WHITE);
        pillShadow.setArcWidth(15.0);
        pillShadow.setArcHeight(15.0);

        chooserButton = new Button();
        chooserButton.setText("Zeit wählen");
        chooserButton.getStyleClass().add("dropDownChooser");
        chooserButton.setLayoutX(255);
        chooserButton.setLayoutY(150);

        dropDownChooser = new DropDownChooser(getSkinnable());
        popup = new Popup();
    }

    private void initializeDrawingPane() {
        drawingPane = new Pane();
    }

    private void layoutParts() {
        popup.getContent().addAll(dropDownChooser);

        drawingPane.getChildren()
            .addAll(pillBlinker, pillBackground, pillForegroundRect, pillForegroundCircle, pillShadow, captionLabel,
                timeField,
                readOnlyTimeLabel, chooserButton);
        getChildren().add(drawingPane);
    }

    private void initializeAnimations() {
        alarm = new Timeline(
            new KeyFrame(Duration.seconds(0.5), e -> pillBlinker.setFill(RED)),
            new KeyFrame(Duration.seconds(1.0), e -> pillBlinker.setFill(BLUE))
        );
    }

    private void setupValueChangeListeners() {
        getSkinnable().timeProperty().addListener((observable, oldValue, newValue) -> {
            if (getSkinnable().checkTime()) {
                pillBackground.setFill(TRANSPARENT);
                alarm.setCycleCount(100);
                alarm.play();
            } else {
                alarm.stop();
                pillBackground.setFill(BLUE);
            }
        });
    }

    private void setupEventListeners() {
        timeField.setOnAction(event -> getSkinnable().convert());

        timeField.addEventFilter(KeyEvent.KEY_PRESSED,
            event -> {
                int caretPos = timeField.getCaretPosition();
                switch (event.getCode()) {
                case ESCAPE -> {
                    getSkinnable().reset();
                    event.consume();
                    event.consume();
                }

                case UP -> {
                    getSkinnable().increase();
                    event.consume();
                }

                case DOWN -> {
                    getSkinnable().decrease();
                    event.consume();
                }
                }
            });

        chooserButton.setOnAction(event -> {
            if (popup.isShowing()) {
                popup.hide();
            } else {
                popup.show(timeField.getScene().getWindow()); }
        });
        popup.setOnHidden(event -> {
           chooserButton.setText("Zeit wählen");
        });
        popup.setOnShown(event -> {
            chooserButton.setText("setzen");
            Point2D location = timeField.localToScreen(
                timeField.getWidth() -  dropDownChooser.getPrefWidth() - 3,
                timeField.getHeight() - 3);
            popup.setX(location.getX());
            popup.setY(location.getY());
        });
    }

    private void setupBindings() {
        captionLabel.textProperty().bind(getSkinnable().captionProperty());
        readOnlyTimeLabel.textProperty().bind(getSkinnable().timeProperty().asString());
        readOnlyTimeLabel.visibleProperty().bind(getSkinnable().editableProperty().not());

        chooserButton.visibleProperty().bind(getSkinnable().editableProperty());
        dropDownChooser.visibleProperty().bind(getSkinnable().editableProperty());

        timeField.visibleProperty().bind(getSkinnable().editableProperty());
        timeField.textProperty().bindBidirectional(getSkinnable().timeAsTextProperty());
    }
}

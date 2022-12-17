package cuie.timecontrol;

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

import static javafx.scene.paint.Color.WHITE;
import static javafx.scene.paint.Color.rgb;
import static javafx.scene.text.TextAlignment.CENTER;


class MyTimeSkin extends SkinBase<MyTimeControl> {
//    private static final double ARTBOARD_WIDTH = 300;
//    private static final double ARTBOARD_HEIGHT = 100;
//
//    private static final double ASPECT_RATIO = ARTBOARD_WIDTH / ARTBOARD_HEIGHT;
//
//    private static final double MINIMUM_WIDTH = 200;
//    private static final double MINIMUM_HEIGHT = MINIMUM_WIDTH / ASPECT_RATIO;
//
//    private static final double MAXIMUM_WIDTH = 1000;


    private static final Color BLUE = rgb(54, 84, 112);

    // wird spaeter gebraucht
    private static final int ICON_SIZE = 12;
    private static final int IMG_OFFSET = 4;


    //todo: replace it
    private TextField timeField;
    private Label captionLabel;  //in ass. 2 dürfen wir neben Text andere Standardlabel verwenden
    private Label readOnlyTimeLabel;

    private Rectangle pillBackground;
    private Rectangle pillForegroundRect;
    private Circle pillForegroundCircle;
    private Rectangle pillShadow;

    private Pane drawingPane;

    private Popup popup;
    private Pane dropDownChooser;
    private Button chooserButton;

    MyTimeSkin(MyTimeControl control) {
        super(control);
        initializeSelf();
        initializeParts();
        initializeDrawingPane();
        layoutParts();
        setupEventListeners();
        setupValueChangeListeners();
        setupBindings();
    }

    private void initializeSelf() {
        // getSkinnable().loadFonts("/fonts/Lato/Lato-Reg.ttf", "/fonts/Lato/Lato-Lig.ttf");
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
        //timeField.setShape (new Circle(350, 100, 100));

        readOnlyTimeLabel = new Label();
        readOnlyTimeLabel.getStyleClass().add("read-only-time-label");
        readOnlyTimeLabel.setLayoutX(255);
        readOnlyTimeLabel.setLayoutY(50);
        readOnlyTimeLabel.setPrefSize(220, 100);
        readOnlyTimeLabel.setTextAlignment(CENTER);
        readOnlyTimeLabel.setPadding(new Insets(2, 10, 2, 2));
//        readOnlyTimeLabel.setBorder(new Border(new BorderStroke(Color.rgb(54, 84, 112),
//            BorderStrokeStyle.SOLID,
//            CornerRadii.EMPTY,
//            new BorderWidths(0,0,3.0,0))));
        //readOnlyTimeLabel.setShape (new Circle(350, 100, 100));

        captionLabel = new Label();
        captionLabel.getStyleClass().add("caption");
        captionLabel.setLayoutX(10);
        captionLabel.setLayoutY(0);
        captionLabel.setPrefSize(220, 180);
        captionLabel.setTextAlignment(CENTER);
        captionLabel.setPadding(new Insets(2, 10, 2, 2));
        //readOnlyTimeLabel.setShape (new Circle(350, 100, 100));


        pillBackground = new Rectangle(0, 0, 500, 200);
        pillBackground.setFill(BLUE);
        pillBackground.getStyleClass().add("pill-background");
        //Setting the height and width of the arc; radius of rectangle-edges
        pillBackground.setArcWidth(200.0);
        pillBackground.setArcHeight(200.0);

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
//        drawingPane.getStyleClass().add("drawing-pane");
//        drawingPane.setMaxSize(ARTBOARD_WIDTH, ARTBOARD_HEIGHT);
//        drawingPane.setMinSize(ARTBOARD_WIDTH, ARTBOARD_HEIGHT);
//        drawingPane.setPrefSize(ARTBOARD_WIDTH, ARTBOARD_HEIGHT);
    }

    private void layoutParts() {
        popup.getContent().addAll(dropDownChooser);

        drawingPane.getChildren()
            .addAll(pillBackground, pillForegroundRect, pillForegroundCircle, pillShadow, captionLabel, timeField,
                readOnlyTimeLabel, chooserButton);
        getChildren().add(drawingPane);
    }

    private void setupValueChangeListeners() {
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
       }); // todo
        popup.setOnShown(event -> {
            chooserButton.setText("Setzen");
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


//    private void resize() {
//        Insets padding = getSkinnable().getPadding();
//        double availableWidth = getSkinnable().getWidth() - padding.getLeft() - padding.getRight();
//        double availableHeight = getSkinnable().getHeight() - padding.getTop() - padding.getBottom();
//
//        double width =
//            Math.max(Math.min(Math.min(availableWidth, availableHeight * ASPECT_RATIO), MAXIMUM_WIDTH), MINIMUM_WIDTH);
//
//        double scalingFactor = width / ARTBOARD_WIDTH;
//
//        if (availableWidth > 0 && availableHeight > 0) {
//            drawingPane.relocate((getSkinnable().getWidth() - ARTBOARD_WIDTH) * 0.5, (getSkinnable().getHeight() - ARTBOARD_HEIGHT) * 0.5);
//            drawingPane.setScaleX(scalingFactor);
//            drawingPane.setScaleY(scalingFactor);
//        }
//    }
}

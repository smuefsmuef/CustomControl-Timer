package cuie.timecontrol;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.css.PseudoClass;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.text.Font;

public class MyTimeControl extends Control {

    private static final String CONVERTIBLE_REGEX = "now|(\\d{1,2}[:]{0,1}\\d{0,2})";
    private static final String TIME_FORMAT_REGEX = "\\d{2}:\\d{2}";

    private static final String FORMATTED_TIME_PATTERN = "HH:mm";

    private static final Pattern CONVERTIBLE_PATTERN = Pattern.compile(CONVERTIBLE_REGEX);
    private static final Pattern TIME_FORMAT_PATTERN = Pattern.compile(TIME_FORMAT_REGEX);

    private final SkinType skinType;

    private static final PseudoClass MANDATORY_CLASS =  //Das ist eine Konstante, deshalb gross geschrieben
        PseudoClass.getPseudoClass("mandatory");

    private static final PseudoClass INVALID_CLASS = PseudoClass.getPseudoClass("invalid");
    private static final PseudoClass CONVERTIBLE_CLASS = PseudoClass.getPseudoClass("convertible");

    private final StringProperty timeAsText = new SimpleStringProperty();
    private final ObjectProperty<LocalTime> time = new SimpleObjectProperty();  //neutrale Bezeichnung, ohne Businesslogik
    private final StringProperty caption = new SimpleStringProperty();

    private final BooleanProperty mandatory = new SimpleBooleanProperty() {
        //neue anonyme Innerclass; hier kann ich invalidated überschreiben
        @Override
        protected void invalidated() {
            pseudoClassStateChanged(MANDATORY_CLASS, get());  //auf allen guten Properties ist get() definiert
        }
    };

    private final BooleanProperty editable = new SimpleBooleanProperty();
    private final BooleanProperty invalid = new SimpleBooleanProperty() {
        @Override
        protected void invalidated() {
            pseudoClassStateChanged(INVALID_CLASS, get());  //auf allen guten Properties ist get() definiert
        }
    };

    private final BooleanProperty convertible = new SimpleBooleanProperty(){
        @Override
        protected void invalidated() {
            super.invalidated();
            pseudoClassStateChanged(CONVERTIBLE_CLASS, get());
        }
    };


    //Verbindung zum skin
    public MyTimeControl(SkinType skinType) {
        this.skinType = skinType;

        initializeSelf();

        time.addListener( (observable, oldValue, newValue) -> {
            setTimeAsText(newValue.toString());
        });
        timeAsText.addListener( (observable, oldValue, newValue) -> {
            setTime(LocalTime.parse(newValue, DateTimeFormatter.ofPattern("H:mm")));
        });


        //hier überprüfen wir, ob der String valide und konvertierbar ist
        timeAsText.addListener((observable, oldValue, newValue) -> {
            setConvertible(newValue.equals("now"));  //if not now then setConvertible = false

            if(!isConvertible()) {

                if (TIME_FORMAT_PATTERN.matcher(newValue).matches()) {
                    setTime(LocalTime.parse(newValue, DateTimeFormatter.ofPattern("H:mm")));
                    setInvalid(false);
                } else {
                    setInvalid(true);
                }
            } else {
                setInvalid(false);
            }
        });
    }


    public void convert() {
        if(isConvertible()) {
            setTime(LocalTime.now());
        }
    }

    public void increase() {
        setTime(getTime().plusHours(1));
    }

    public void decrease() {
        setTime(getTime().minusHours(1));
    }

    public void reset() {
        setTime(getTime());
    }

    private void initializeSelf() {
        getStyleClass().add("my-time-control");
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return skinType.getFactory().apply(this);
    }


    private void setupBindings() {
    }


    //Hilfsmethoden
    public void loadFonts(String... font){
        for(String f : font){
            Font.loadFont(getClass().getResourceAsStream(f), 0);
        }
    }

    public void addStylesheetFiles(String... stylesheetFile){
        for(String file : stylesheetFile){
            String stylesheet = getClass().getResource(file).toExternalForm();
            getStylesheets().add(stylesheet);
        }
    }


    //getter und setter

    public LocalTime getTime() {
        return time.get();
    }

    public ObjectProperty<LocalTime> timeProperty() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time.set(time);
    }

    public String getCaption() {
        return caption.get();
    }

    public StringProperty captionProperty() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption.set(caption);
    }

    public boolean isMandatory() {
        return mandatory.get();
    }

    public BooleanProperty mandatoryProperty() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory.set(mandatory);
    }

    public boolean isEditable() {
        return editable.get();
    }

    public BooleanProperty editableProperty() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable.set(editable);
    }

    public String getTimeAsText() {
        return timeAsText.get();
    }

    public StringProperty timeAsTextProperty() {
        return timeAsText;
    }

    public void setTimeAsText(String timeAsText) {
        this.timeAsText.set(timeAsText);
    }

    public boolean isInvalid() {
        return invalid.get();
    }

    public BooleanProperty invalidProperty() {
        return invalid;
    }

    public void setInvalid(boolean invalid) {
        this.invalid.set(invalid);
    }

    public boolean isConvertible() {
        return convertible.get();
    }

    public BooleanProperty convertibleProperty() {
        return convertible;
    }

    public void setConvertible(boolean convertible) {
        this.convertible.set(convertible);
    }
}



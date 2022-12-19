package cuie.timecontrol.demo;

import java.time.LocalTime;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class PresentationModel {
    private final ObjectProperty<LocalTime> startTime = new SimpleObjectProperty<>(LocalTime.now());
    private final StringProperty label = new SimpleStringProperty("Medikament XY");
    private final BooleanProperty mandatory = new SimpleBooleanProperty(true);
    private final BooleanProperty readOnly = new SimpleBooleanProperty(false);
    private final BooleanProperty blinker = new SimpleBooleanProperty(false);


    public LocalTime getStartTime() {
        return startTime.get();
    }

    public ObjectProperty<LocalTime> startTimeProperty() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime.set(startTime);
    }

    public String getLabel() {
        return label.get();
    }

    public StringProperty labelProperty() {
        return label;
    }

    public void setLabel(String label) {
        this.label.set(label);
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

    public boolean isReadOnly() {
        return readOnly.get();
    }

    public BooleanProperty readOnlyProperty() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly.set(readOnly);
    }

    public boolean getBlinker() {
        return blinker.get();
    }

    public BooleanProperty blinkerProperty() {
        return blinker;
    }

    public void setBlinker(boolean blinker) {
        this.blinker.set(blinker);
    }
}

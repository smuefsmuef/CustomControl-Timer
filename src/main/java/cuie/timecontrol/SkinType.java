package cuie.timecontrol;

import java.util.function.Function;

import javafx.scene.control.SkinBase;

public enum SkinType {

    EXPERIMENTAL(MyTimeSkin::new);

    private final Function<MyTimeControl, SkinBase<MyTimeControl>> factory;

    SkinType(Function<MyTimeControl, SkinBase<MyTimeControl>> factory) {
        this.factory = factory;
    }

    public Function<MyTimeControl, SkinBase<MyTimeControl>> getFactory() {
        return factory;
    }

}

package cuie.timecontrol.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;


public class DemoStarter extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        PresentationModel pm        = new PresentationModel();
        Region            rootPanel = new DemoPane(pm);

        Scene scene = new Scene(rootPanel);

        primaryStage.setTitle("Business Control Demo");
        primaryStage.setScene(scene);

        primaryStage.show();

        // todo remove
//       ScenicView.show(scene);
 //       CSSFX.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

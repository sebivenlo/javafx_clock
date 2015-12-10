package javafxclock;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Ron Gebauer <mail@ron.gebauers.org>
 * @version 1
 */
public class JavaFXClock extends Application {

    private final FXMLLoader fXMLLoader = new FXMLLoader();

    /**
     * @param args the command line arguments
     */
    public static void main(String... args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        fXMLLoader.setLocation(getClass().getResource("Clockview.fxml"));

        Parent root = fXMLLoader.load();

        Scene scene = new Scene(root);

        stage.setTitle("Clock");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        ClockviewController controller = (ClockviewController) fXMLLoader.getController();

        controller.startApp(false);
    }

}

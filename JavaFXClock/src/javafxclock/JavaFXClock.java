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


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/Clock.fxml"));

        Scene scene = new Scene(root);

        stage.setTitle("Clock");
        stage.setScene(scene);
        stage.show();
    }

}

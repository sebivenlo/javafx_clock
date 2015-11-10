/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clock_ws;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 *
 * @author max
 */
public class clock_wsController implements Initializable {
    
    @FXML
    private Label label;
    private Timeline tl=new Timeline();
    private Time time=new Time();
    
  
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       tl.setCycleCount(Timeline.INDEFINITE);
        //add actions to timeLine
         label.textProperty().bind(time.total);
        tl.getKeyFrames().add(new KeyFrame(javafx.util.Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            //we define what should happen
            public void handle(ActionEvent event) {
                time.tick();
            }
             }));
        tl.play();
    }    
    
}

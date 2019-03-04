/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swarmproject;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import java.util.ArrayList;
/**
 *
 * @author Rayan
 */
public class SwarmProject extends Application {

    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Generate Solution");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        btn.setOnAction(new EventHandler<ActionEvent>(){    
            @Override
            public void handle(ActionEvent event) {
                Controller control = new Controller();
                ArrayList<String> methods = control.getMethods();
                for(int i=0;i<methods.size();i++)
                {
                control.ChooseMethod(methods.get(i),"");
                control.FolderTest("/Users/q/Documents/SII/PROJET ESSAIM/uf20-91");
                }
                
            }
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        HBox hbox = new HBox();
        Group gr = new Group(hbox);
        hbox.setSpacing(10);
        root.getChildren().add(gr);
        Scene scene = new Scene(root, 600, 400);
       
        
        primaryStage.setTitle("Méta-Heuristiques");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
   
        launch(args);
    }
    
}

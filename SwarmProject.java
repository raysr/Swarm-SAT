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
import java.util.Map;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import java.util.HashMap;
import javafx.scene.control.TextField;
/**
 *
 * @author Rayan
 */
public class SwarmProject extends Application {

    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Tester les Solveurs");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc = new BarChart<String,Number>(xAxis,yAxis);
            
        bc.setTitle("Performance des Algorithmes");
        xAxis.setLabel("Algorithmes");       
        yAxis.setLabel("Temps(nanosecondes)");
        XYChart.Series series1 = new XYChart.Series();
         series1.setName("Temps d'Execution");
        TextField nbr = new TextField();

        btn.setOnAction(new EventHandler<ActionEvent>(){    
            @Override
            public void handle(ActionEvent event) {
                Controller control = new Controller();
                int num = Integer.valueOf(nbr.getText());
                System.out.println ("Number of instances : "+num);
                HashMap<String, Double> map = control.TestAll(num);
                
                for (Map.Entry<String, Double> element : map.entrySet()) {
                    System.out.println("Key : "+element.getKey()+" | Value : "+element.getValue());
                    series1.getData().add(new XYChart.Data(element.getKey(), element.getValue()));
                    System.out.println();
                }
               
                
               
       
            }
        });
        
        
        VBox vbox = new VBox();
        HBox hbox = new HBox();
        
        hbox.getChildren().addAll(nbr,btn);
        vbox.setSpacing(10);
        vbox.getChildren().addAll(bc, hbox);
        btn.setTranslateX(350);
        Scene scene = new Scene(vbox, 800, 600);
       bc.getData().addAll(series1);
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

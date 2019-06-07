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
import javafx.stage.Stage;
import java.util.Map;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
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
            
        bc.setTitle("Performance en Temps");
        xAxis.setLabel("Algorithmes");       
        yAxis.setLabel("Temps(nanosecondes)");
        XYChart.Series series1 = new XYChart.Series();
         series1.setName("Temps d'Execution");
         
         
         
         final CategoryAxis xAxisClauses = new CategoryAxis();
        final NumberAxis yAxisClauses = new NumberAxis();
        final BarChart<String,Number> bcClauses = new BarChart<String,Number>(xAxis,yAxis);
            
        bcClauses.setTitle("Performance en Nombre de Clauses");
        xAxisClauses.setLabel("Algorithmes");       
        yAxisClauses.setLabel("Nombre de clauses");
        XYChart.Series series1Clauses = new XYChart.Series();
         series1Clauses.setName("Nombre de clauses Satisfaites");
         
         
         
         
        TextField nbr = new TextField();
        TextField time = new TextField();
        
        btn.setOnAction(new EventHandler<ActionEvent>(){    
            @Override
            public void handle(ActionEvent event) {
                Controller control = new Controller();
                int num = Integer.valueOf(nbr.getText());
                int timing = Integer.valueOf(time.getText());
                System.out.println ("Number of instances : "+num);
                System.out.println ("Max timing  : "+timing);
                HashMap<String, Statistic> map = control.TestAll(num, timing);
                
                for (Map.Entry<String, Statistic> element : map.entrySet()) {
                    System.out.println("Key : "+element.getKey()+" | Timing  : "+element.getValue().getTiming()+" | Nbr Clauses : "+element.getValue().getNbrClauses());
                    series1.getData().add(new XYChart.Data(element.getKey(), element.getValue().getTiming()));
                    series1Clauses.getData().add(new XYChart.Data(element.getKey(), element.getValue().getNbrClauses()));
                    System.out.println();
                }
               
                
               
       
            }
        });
        
        
        VBox vbox = new VBox();
        HBox hbox = new HBox();
        HBox graphs = new HBox();
        
        Label instancesLabel = new Label();
        instancesLabel.setText("Nombre d'instances du test ");
        Label timeLabel = new Label();
        timeLabel.setText("Temps maximum pour une instance (0 pour illimité) ");
        hbox.getChildren().addAll(instancesLabel,nbr,timeLabel,time,btn);
        hbox.setMaxSize(300, 100);
        btn.setMinSize(30, 10);
        vbox.setSpacing(10);
        graphs.getChildren().addAll(bcClauses,bc);
        vbox.getChildren().addAll(graphs, hbox);
        btn.setTranslateX(350);
        
        Scene scene = new Scene(vbox, 800, 600);
       bc.getData().addAll(series1);
       bcClauses.getData().addAll(series1Clauses);
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

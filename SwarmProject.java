/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swarmproject;

import javafx.geometry.Insets;
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
import javafx.scene.text.Font; 
import javafx.scene.text.FontPosture; 
import javafx.scene.text.FontWeight; 
import javafx.scene.Node;
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
        yAxis.setLabel("Temps(secondes/10000)");
        XYChart.Series series1 = new XYChart.Series();
         series1.setName("Temps d'Execution");
         
         
         
        final CategoryAxis xAxisClauses = new CategoryAxis();
        final NumberAxis yAxisClauses = new NumberAxis();
        final BarChart<String,Number> bcClauses = new BarChart<String,Number>(xAxisClauses,yAxisClauses);
            
        bcClauses.setTitle("Performance en Nombre de Clauses");
        xAxisClauses.setLabel("Algorithmes");       
        yAxisClauses.setLabel("Nombre de clauses");
        XYChart.Series series1Clauses = new XYChart.Series();
        series1Clauses.setName("Nombre de clauses Satisfaites");
         
        TextField tauxCroisement = new TextField();
        TextField tauxMutation = new TextField();
        TextField maxIter = new TextField();
        Label croisementLabel = new Label();
        croisementLabel.setText("Taux de Croisement [0 - 100]");
        Label mutationLabel = new Label();
        mutationLabel.setText("Taux de Mutation [0 - 100]");
        Label maxIterLabel = new Label();
        maxIterLabel.setText("Max Iterations (0 pour illimité)");
         
         
        TextField nbr = new TextField();
        TextField time = new TextField();

        btn.setOnAction(new EventHandler<ActionEvent>(){    
            @Override
            public void handle(ActionEvent event) {
                Controller control = new Controller();
                HashMap<String, Integer> parameters = new HashMap<String, Integer>();
                parameters.put("number_instances", Integer.valueOf(nbr.getText()));
                parameters.put("timing", Integer.valueOf(time.getText()));
                parameters.put("taux_croisement", Integer.valueOf(tauxCroisement.getText()));
                parameters.put("taux_mutation", Integer.valueOf(tauxMutation.getText()));
                parameters.put("max_iteration", Integer.valueOf(maxIter.getText()));
                
                HashMap<String, Statistic> map = control.TestAll(parameters);
                
                for (Map.Entry<String, Statistic> element : map.entrySet()) {
                    System.out.println("Key : "+element.getKey()+" | Timing  : "+element.getValue().getTiming()+" | Nbr Clauses : "+element.getValue().getNbrClauses());
                    series1.getData().add(new XYChart.Data(element.getKey(), element.getValue().getTiming()));
                    series1Clauses.getData().add(new XYChart.Data(element.getKey(), element.getValue().getNbrClauses()));
                    
                    xAxis.setAnimated(false);
                    yAxis.setAnimated(true);
                    series1.getChart().setAnimated(true);
                    xAxisClauses.setAnimated(false);
                    yAxisClauses.setAnimated(true);
                    series1Clauses.getChart().setAnimated(true);
                    
                    
                    //set first bar color
  for(Node n:bcClauses.lookupAll(".default-color0.chart-bar")) {
            n.setStyle("-fx-bar-fill: blue;");
        }
   
                    System.out.println();
                }
               
                
               
       
            }
        });
        
        
        VBox vbox = new VBox();
        HBox hbox = new HBox();
        HBox hboxALGOS = new HBox();
        HBox graphs = new HBox();
        
        VBox vboxGENETIC = new VBox();
        VBox vboxPSO = new VBox();
        
        Label generalTitle = new Label();
        generalTitle.setText("Paramètres généraux");
        generalTitle.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        Label geneticTitle = new Label();
        geneticTitle.setText("Paramètres Génétiques");
        geneticTitle.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        Label psoTitle = new Label();
        psoTitle.setText("Paramètres PSO");
        psoTitle.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        Label instancesLabel = new Label();
        instancesLabel.setText("Nombre d'instances");
        Label timeLabel = new Label();
        timeLabel.setText("Temps maximum pour une instance (0 pour illimité) ");
        

        
        
        hboxALGOS.getChildren().addAll(vboxGENETIC, vboxPSO);
        vboxGENETIC.getChildren().addAll(geneticTitle,maxIterLabel,maxIter,croisementLabel,tauxCroisement,mutationLabel,tauxMutation);
        vboxPSO.getChildren().addAll(psoTitle);
        hbox.getChildren().addAll(instancesLabel,nbr,timeLabel,time,btn);
        hbox.setMaxSize(1024, 768);
        
        hboxALGOS.setSpacing(200);
        vbox.setSpacing(30);
        graphs.getChildren().addAll(bcClauses,bc);
        vbox.getChildren().addAll(graphs, generalTitle, hbox, hboxALGOS);
        vbox.setPadding(new Insets(10, 50, 50, 50));
        Scene scene = new Scene(vbox, 1024, 768);
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

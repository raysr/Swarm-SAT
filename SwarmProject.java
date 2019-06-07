/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swarmproject;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.application.Platform;
import java.util.Scanner; 
import javafx.stage.DirectoryChooser;
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
import java.io.File;
import java.io.FileWriter;  

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
        final String directoy = new String();   
        bc.setTitle("Performance en Temps");
        xAxis.setLabel("Algorithmes");       
        yAxis.setLabel("Temps(nanosecondes/10000)");
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
         
        
        // Configuration Génétique
        TextField tauxCroisement = new TextField("30");
        TextField tauxMutation = new TextField("20");
        TextField maxIter = new TextField("100000");
        Label croisementLabel = new Label();
        croisementLabel.setText("Taux de Croisement [0 - 100]");
        Label mutationLabel = new Label();
        mutationLabel.setText("Taux de Mutation [0 - 100]");
        Label maxIterLabel = new Label();
        maxIterLabel.setText("Max Iterations (0 pour illimité)");
         
        
        
        final Label labelSelectedDirectory = new Label();
        
        
        // Configuration PSO 
        TextField numberOfParticles = new TextField("20");
        Label numberOfParticlesLabel = new Label();
        numberOfParticlesLabel.setText("Nombre de Particules");
        TextField C1 = new TextField("1");
        TextField C2 = new TextField("1");
        TextField PoidsInertie = new TextField("1");
        Label C1Label = new Label("Facteur d'apprentissage (Meilleure solution particule) /100");
        Label C2Label = new Label("Facteur d'apprentissage (Meilleure solution globale) /100");
        Label PoidsInertieLabel = new Label("Poids Inertiel /100");
        
        // Configuration Générale 
        TextField nbr = new TextField("10");
        TextField time = new TextField("500");

        
         File file = new File("configPROJECT"); 
                
                if(file.exists()){
                    String directory = new String();
                    try{
                Scanner sc = new Scanner(file); 
                    while (sc.hasNextLine()) 
                    {
                        directory = sc.nextLine();
                        
                        System.out.println("configPROJECT " +sc.nextLine()); 
                    }          
                    
                    }catch(Exception E){}
                
                labelSelectedDirectory.setText(directory);
                }
        
        
        btn.setOnAction(new EventHandler<ActionEvent>(){    
            @Override
            public void handle(ActionEvent event) {
                Controller control = new Controller();
                if(labelSelectedDirectory.getText().equals("") || !control.VerifyDirectory(labelSelectedDirectory.getText())){
                infoBox("Veuillez séléctionner un dossier contenant exclusivement des instances de SAT", "Alerte", null);
                return;
                }
                
                
                HashMap<String, Integer> parameters = new HashMap<String, Integer>();
                parameters.put("number_instances", Integer.valueOf(nbr.getText()));
                parameters.put("timing", Integer.valueOf(time.getText()));
                parameters.put("taux_croisement", Integer.valueOf(tauxCroisement.getText()));
                parameters.put("taux_mutation", Integer.valueOf(tauxMutation.getText()));
                parameters.put("max_iteration", Integer.valueOf(maxIter.getText()));
                parameters.put("C1", Integer.valueOf(C1.getText()));
                parameters.put("C2", Integer.valueOf(C2.getText()));
                parameters.put("W", Integer.valueOf(PoidsInertie.getText()));
                parameters.put("number_particles", Integer.valueOf(numberOfParticles.getText())); 
                String directory = labelSelectedDirectory.getText();
                
                HashMap<String, Statistic> map = control.TestAll(parameters, directory);
                
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
        vboxPSO.getChildren().addAll(psoTitle,numberOfParticlesLabel,numberOfParticles,C1Label,C1,C2Label,C2,PoidsInertieLabel,PoidsInertie);
        hbox.getChildren().addAll(instancesLabel,nbr,timeLabel,time,btn);
        hbox.setMaxSize(1024, 768);
        
        hboxALGOS.setSpacing(200);
        vbox.setSpacing(30);
        graphs.getChildren().addAll(bcClauses,bc);
        
        HBox directoryBox = new HBox();
              
         
        Button btnOpenDirectoryChooser = new Button();
        btnOpenDirectoryChooser.setText("Sélectionner un nouveau dossier");
        btnOpenDirectoryChooser.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DirectoryChooser directoryChooser = new DirectoryChooser();
                File selectedDirectory = 
                        directoryChooser.showDialog(primaryStage);
                 
                if(selectedDirectory == null){
                    labelSelectedDirectory.setText("No Directory selected");
                }else{
                    labelSelectedDirectory.setText(selectedDirectory.getAbsolutePath());
                    
                            
                            try{    
           FileWriter fw=new FileWriter("configPROJECT");    
           fw.write(selectedDirectory.getAbsolutePath());    
           fw.close();    
          }catch(Exception e){System.out.println(e);}    
          System.out.println("Success...");    
     }    
                            
                            
             }
            }
        );
        Label dirLabel = new Label("Dossier contenant les instances ");
        dirLabel.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 16));
         directoryBox.getChildren().addAll(dirLabel,labelSelectedDirectory,btnOpenDirectoryChooser);
        directoryBox.setSpacing(30);
        
        
        
        vbox.getChildren().addAll(directoryBox, graphs, generalTitle, hbox, hboxALGOS);
        vbox.setPadding(new Insets(10, 50, 50, 50));
        Scene scene = new Scene(vbox, 1024, 768);
       bc.getData().addAll(series1);
       bcClauses.getData().addAll(series1Clauses);
        primaryStage.setTitle("Méta-Heuristiques");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    
     public static void infoBox(String infoMessage, String titleBar, String headerMessage)
    {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);
        alert.showAndWait();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
   
        launch(args);
    }
    
}

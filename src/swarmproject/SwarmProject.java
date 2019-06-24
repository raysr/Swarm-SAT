package swarmproject;
import java.util.ArrayList;
import javafx.scene.control.ChoiceBox; 
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TitledPane;
import javafx.geometry.Insets;
import java.io.IOException;
import java.io.FileReader;
import javafx.geometry.Pos;
import javafx.scene.text.TextAlignment;
import javafx.scene.image.Image;
import javafx.stage.StageStyle;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Background;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.BufferedReader;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BackgroundImage;
import java.util.HashMap;
import javafx.scene.control.TextField;
import javafx.scene.text.Font; 
import javafx.scene.text.FontPosture; 
import javafx.scene.text.FontWeight; 
import java.io.File;
import java.io.FileWriter;  
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.Pane;
/**
 *
 * @author Rayan
 */
public class SwarmProject extends Application {
public HashMap<String,  StatisticParam> OPTIMISATION;
   public HashMap<String, Integer> roders = new HashMap<String, Integer>();

    public ArrayList<Pane> stats(){
        ArrayList<Pane> ret = new ArrayList<Pane>();
      Button btn = new Button();
        btn.setText("Tester les Solveurs");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc = new BarChart<String,Number>(xAxis,yAxis);
        bc.setMaxSize(800, 500);
          
        
        xAxis.setLabel("Val paramètre");       
        yAxis.setLabel("Nbr Clauses");
        XYChart.Series series1 = new XYChart.Series();
         series1.setName("Test Paramètre");
        
         Controller control = new Controller();
         
        final TableView table = new TableView();
        table.setEditable(false);
        TableColumn numeroCol = new TableColumn("N° Clause");
        TableColumn var1Col = new TableColumn("Variable 1");
        TableColumn var2Col = new TableColumn("Variable 2");
        TableColumn var3Col = new TableColumn("Variable 3");
        table.getColumns().addAll(numeroCol, var1Col, var2Col, var3Col);
        numeroCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        var1Col.setCellValueFactory(new PropertyValueFactory<>("var1"));
        var2Col.setCellValueFactory(new PropertyValueFactory<>("var2"));
        var3Col.setCellValueFactory(new PropertyValueFactory<>("var3"));
        table.getItems().add(new Row(10,"1","2","3"));
        
        ChoiceBox datasetChoiceBox = new ChoiceBox(); 
        datasetChoiceBox.getItems().addAll("uf20-91","uf50-218","uf75-325-100","uuf50-218-1000","uuf75-325-100");
               
       
        
        
        
        
        Label generalTitle = new Label();
        generalTitle.setText("Paramètres généraux");
        generalTitle.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        
        
        
        Label instancesLabel = new Label();
        instancesLabel.setText("Nombre d'instances");
        Label timeLabel = new Label();
        timeLabel.setText("Temps maximum pour une instance (0 pour illimité) ");
        

        
        

              
         
        Label dirLabel = new Label("DATASET");
        dirLabel.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 16));
        
        
        
        VBox program = new VBox();
        
        
        VBox datasetsBox = new VBox();
        VBox instancesBox = new VBox();
        
        VBox firstBox = new VBox();
        
        VBox secondBox = new VBox();
        VBox thirdBox = new VBox();
        VBox fourthBox = new VBox();
        
        VBox thirdBoxLeft = new VBox();
        VBox thirdBoxRight = new VBox();
        
        Label choixParam = new Label("Choisir Paramètre");
        ChoiceBox choixParamChoice = new ChoiceBox();
        ChoiceBox infoChoiceBox = new ChoiceBox();
        thirdBoxLeft.getChildren().addAll(choixParam, choixParamChoice, infoChoiceBox);
        thirdBoxRight.getChildren().addAll(bc);
        ChoiceBox selectionAlgo = new ChoiceBox(); 
        Button optimiserAlgo = new Button("Optimiser les paramètres");
        

        
        TextField timeInput = new TextField("500");
        
        

                
         Button lancer = new Button("LANCER");
        lancer.setOnAction(new EventHandler<ActionEvent>(){    
            @Override
            public void handle(ActionEvent event) {
                Controller control = new Controller();
             
                
                HashMap<String, Integer> parameters = new HashMap<String, Integer>();
                parameters.put("number_instances", 10);
                parameters.put("timing", roders.get("timing"));
                parameters.put("size_population", roders.get("sizepop"));
                parameters.put("taux_croisement", roders.get("taux_croisement"));
                parameters.put("taux_mutation", roders.get("taux_mutation"));
                parameters.put("max_iteration", roders.get("max_iterations"));
                parameters.put("C1", roders.get("c1"));
                parameters.put("C2", roders.get("c2"));
                parameters.put("W", roders.get("w"));
                parameters.put("number_particles", roders.get("number_particles")); 
                String directory = "datasets/"+(String)datasetChoiceBox.getSelectionModel().getSelectedItem();
                System.out.println("Directory : "+directory);
                OPTIMISATION = control.TestAll(parameters, directory);
                
                }
        });
         
        choixParamChoice.getItems().addAll("BFS","DFS","A*","GENETIQUE","PSO","COMPARATIF");
        infoChoiceBox.getItems().addAll("Meilleur nombre de clauses","Moyenne nombre de clauses","Meilleur Temps","Moyenne Temps");
                    
        choixParamChoice.setMinSize(160, 25);
        infoChoiceBox.setMinSize(160, 25);
        choixParamChoice.setMaxSize(160, 25);
        infoChoiceBox.setMaxSize(160, 25);
        
        lancer.setMinSize(160, 25);
        lancer.setMaxSize(160, 25);
        
        thirdBox.getChildren().addAll(lancer , choixParamChoice, infoChoiceBox, thirdBoxLeft, thirdBoxRight);
        thirdBox.setPadding(new Insets(0, 20, 10, 20)); 
        thirdBox.setSpacing(10.0);
        
       
        
        
        // RESULTATS OPTIMISESSSSS
        Label l = new Label("RESULTATS OPTIMISES");
        fourthBox.getChildren().addAll(l);
        
        ChoiceBox instancesChoiceBox = new ChoiceBox(); 
        Label instancesNumeroLabel = new Label("Instance");
        instancesChoiceBox.getItems().addAll("1","2","3","4","5","6","7","8","9","10");
        instancesBox.getChildren().addAll(table);
        Label timeInputLabel = new Label("Limitation de Temps pour l'Optimisation");
        
        datasetsBox.getChildren().addAll(dirLabel,datasetChoiceBox, instancesNumeroLabel,instancesChoiceBox,timeInputLabel, timeInput);
        
        selectionAlgo.getItems().addAll("BFS","DFS","GENETIC","PSO");
        
        
        
        
        
         
        
        
        
        choixParamChoice.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
        @Override
            public void changed(ObservableValue<? extends String> observable, //
                    String oldValue, String newValue) {
                               
                  
                String trim = (String)choixParamChoice.getSelectionModel().getSelectedItem();
                   if(!trim.equals("COMPARATIF")) {infoChoiceBox.getItems().clear();infoChoiceBox.getItems().addAll("Nombre de clauses","Temps");infoChoiceBox.getSelectionModel().selectFirst();}
                   else{infoChoiceBox.getItems().clear();infoChoiceBox.getItems().addAll("Meilleur nombre de clauses","Moyenne nombre de clauses","Meilleur Temps","Moyenne Temps");infoChoiceBox.getSelectionModel().selectFirst();}
                  
                
                
                if(choixParamChoice.getSelectionModel().getSelectedItem()!=null && infoChoiceBox.getSelectionModel().getSelectedItem()!=null){
                    
                    StatisticParam printer = new StatisticParam();
                    
                    if(trim.equals("DFS")){printer = OPTIMISATION.get("Largeur");}
                    else if(trim.equals("BFS")){printer = OPTIMISATION.get("Profondeur");}
                    else if(trim.equals("GENETIQUE")){printer = OPTIMISATION.get("Genetic");}
                
                 else{printer = OPTIMISATION.get(trim);}
                   
                    
                    
                  
                   
                   series1.getData().clear();
                   yAxis.setAnimated(false);
            xAxis.setAnimated(false);
            bc.setAnimated(false);
                   //printer.describe();
                   
                   if(trim.equals("COMPARATIF")){System.out.println("ADADA1");
                       for(String key:OPTIMISATION.keySet()){
                           printer=OPTIMISATION.get(key);
                    long y;
                       switch((String)infoChoiceBox.getSelectionModel().getSelectedItem()){
                           case "Meilleur nombre de clauses":
                               y=printer.meilleurNbrClauses;
                               xAxis.setLabel("Algorithme");       
                                yAxis.setLabel("Meilleur nombre de clauses");
                               break;
                           case "Moyenne nombre de clauses":
                               y=printer.moyenneNbrClauses;
                               xAxis.setLabel("Algorithme");       
                                yAxis.setLabel("Moyenne Clauses");
                               break;
                           case "Meilleur Temps":
                               y=printer.meilleurTiming;
                               xAxis.setLabel("Algorithme");       
                                yAxis.setLabel("Meilleur Temps");
                               break;
                           default:
                              y=printer.moyenneTiming;
                              xAxis.setLabel("Algorithme");       
                                yAxis.setLabel("Moyenne Temps");
                              break;
                       }
                       
                    series1.getData().add(new XYChart.Data(key, y));
                   }}
                   else{
                    
                       
                       for(int k=0;k<printer.instances.size();k++){
                           Statistic el = printer.instances.get(k);
                    long y;
                       switch((String)infoChoiceBox.getSelectionModel().getSelectedItem()){
                           case "Temps":
                               y=el.timing;
                               xAxis.setLabel("Instances");       
                                yAxis.setLabel("Temps d'éxecution");
                               break;
                           default:
                              y=el.nbrClauses;
                              xAxis.setLabel("Instances");       
                                yAxis.setLabel("Nombre de Clauses Satisfaites");
                              break;
                       }
                       
                    series1.getData().add(new XYChart.Data(String.valueOf(k), y));
                   }
                   
                   }
                   
                }
        }});
        infoChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
        @Override
            public void changed(ObservableValue<? extends String> observable, //
                    String oldValue, String newValue) {
                           String trim = (String)choixParamChoice.getSelectionModel().getSelectedItem();
                   
                
                
                if(choixParamChoice.getSelectionModel().getSelectedItem()!=null && infoChoiceBox.getSelectionModel().getSelectedItem()!=null){
                    
                    StatisticParam printer = new StatisticParam();
                    
                    if(trim.equals("DFS")){printer = OPTIMISATION.get("Largeur");}
                    else if(trim.equals("BFS")){printer = OPTIMISATION.get("Profondeur");}
                    else if(trim.equals("GENETIQUE")){printer = OPTIMISATION.get("Genetic");}
                 else{printer = OPTIMISATION.get(trim);}
                   
                    
                    
                  
                   
                   series1.getData().clear();
                    yAxis.setAnimated(false);
            xAxis.setAnimated(false);
            bc.setAnimated(false);
                   //printer.describe();
                   
                   if(trim.equals("COMPARATIF")){System.out.println("ADADA2");
                       for(String key:OPTIMISATION.keySet()){
                           printer=OPTIMISATION.get(key);
                    long y;
                       switch((String)infoChoiceBox.getSelectionModel().getSelectedItem()){
                           case "Meilleur nombre de clauses":
                               y=printer.meilleurNbrClauses;
                               xAxis.setLabel("Algorithme");       
                                yAxis.setLabel("Max Clauses");
                               break;
                           case "Moyenne nombre de clauses":
                               y=printer.moyenneNbrClauses;
                               xAxis.setLabel("Algorithme");       
                                yAxis.setLabel("Moyenne Clauses");
                               break;
                           case "Meilleur Temps":
                               y=printer.meilleurTiming;
                               xAxis.setLabel("Algorithme");       
                                yAxis.setLabel("Meilleur Temps");
                               break;
                           default:
                              y=printer.moyenneTiming;
                              xAxis.setLabel("Algorithme");       
                                yAxis.setLabel("Moyenne Temps");
                              break;
                       }
                       
                    series1.getData().add(new XYChart.Data(key, y));
                   }}
                   
                   else{
                 
                       for(int k=0;k<printer.instances.size();k++){
                           Statistic el = printer.instances.get(k);
                    long y;
                       switch((String)infoChoiceBox.getSelectionModel().getSelectedItem()){
                           case "Temps":
                               y=el.timing;
                               xAxis.setLabel("Instances");       
                                yAxis.setLabel("Temps");
                               break;
                           default:
                              y=el.nbrClauses;
                              xAxis.setLabel("Instances");       
                                yAxis.setLabel("Nombre de clauses satisfaites");
                              break;
                       }
                       
                    series1.getData().add(new XYChart.Data(String.valueOf(k), y));
                   }
                   
                   }
                   
                  
                }
        }});
        
        
        
        
        
        
        
        
        instancesChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
        @Override
            public void changed(ObservableValue<? extends String> observable, //
                    String oldValue, String newValue) {
                if (newValue != null) {
                    String data = (String)datasetChoiceBox.getSelectionModel().getSelectedItem();
                
                    table.getItems().clear();
                    ArrayList<Row> rows = control.getRows(data, Integer.valueOf(newValue));
                    for(int i=0;i<rows.size();i++)
                    {
                    table.getItems().add(rows.get(i));
                    }
                    
                }
            }
       
        });
        datasetChoiceBox.getSelectionModel().selectFirst();
        instancesChoiceBox.getSelectionModel().selectFirst();
       
        
        
        
        datasetChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
        
        @Override
            public void changed(ObservableValue<? extends String> observable, //
                    String oldValue, String newValue) {
                if (newValue != null) {
                    
                    String data = newValue;
                    Integer instance = Integer.valueOf((String)instancesChoiceBox.getSelectionModel().getSelectedItem());
                    
                    table.getItems().clear();
                    ArrayList<Row> rows = control.getRows(data, instance);
                    for(int i=0;i<rows.size();i++)
                    {
                    table.getItems().add(rows.get(i));
                    }
                    
                }
            }
       
        });
          
        
        
        firstBox.getChildren().addAll(datasetsBox, instancesBox);
        selectionAlgo.getSelectionModel().selectLast();
       fourthBox.setStyle("-fx-background-color: transparent ;");
       
       thirdBox.setMinWidth(450);
       fourthBox.setMinWidth(450);
       program.setMinWidth(350);
       program.getChildren().addAll(firstBox);
       bc.getData().addAll(series1);
       ret.add(program);ret.add(thirdBox);
       return ret;
    
    }
    
    
    public ArrayList<VBox> parametres(){
        ArrayList<VBox> ret = new ArrayList<VBox>();
    VBox program = new VBox();
       
        // Configuration Génétique
        TextField tauxCroisement = new TextField("30");
        TextField tauxMutation = new TextField("20");
        TextField maxIter = new TextField("100000");
        TextField sizePop = new TextField("20");
        Label croisementLabel = new Label();
        croisementLabel.setText("Taux de Croisement [0 - 100]");
        Label mutationLabel = new Label();
        mutationLabel.setText("Taux de Mutation [0 - 100]");
        Label maxIterLabel = new Label();
        maxIterLabel.setText("Max Iterations (0 pour illimité)");
         Label sizePopLabel = new Label();
        sizePopLabel.setText("Taille population");
        
        
        // Configuration PSO 
        TextField numberOfParticles = new TextField("20");
        Label numberOfParticlesLabel = new Label();
        numberOfParticlesLabel.setText("Nombre de Particules");
        TextField C1 = new TextField("1");
       // C1.setMaxWidth(100);
        TextField C2 = new TextField("1");
        TextField PoidsInertie = new TextField("1");
        Label C1Label = new Label("C1 (distance particule)");
        Label C2Label = new Label("C2 (distance globale)");
        Label PoidsInertieLabel = new Label("Poids Inertiel /100");
        
        // Configuration Générale 
        TextField time = new TextField("500");
        
        
        VBox vbox = new VBox();
        VBox hbox = new VBox();
        HBox hboxALGOS = new HBox();

        
        VBox vboxGENETIC = new VBox();
        VBox vboxPSO = new VBox();
        
        Label timeLabel = new Label();
        timeLabel.setText("Temps maximum par instance (0=illimité) ");
        timeLabel.setMinWidth(400);

        HBox hbox0 = new HBox();
        hbox0.getChildren().addAll(sizePopLabel,sizePop);
        sizePopLabel.setStyle("-fx-text-fill: #8e8e8e;");
        sizePopLabel.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 14));
        
        HBox hbox1 = new HBox();
        hbox1.getChildren().addAll(maxIterLabel,maxIter);
        maxIterLabel.setStyle("-fx-text-fill: #8e8e8e;");
        maxIterLabel.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 14));
        HBox hbox2 = new HBox();
        hbox2.getChildren().addAll(croisementLabel,tauxCroisement);
        croisementLabel.setStyle("-fx-text-fill: #8e8e8e;");
        croisementLabel.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 14));
        HBox hbox3 = new HBox();
        mutationLabel.setStyle("-fx-text-fill: #8e8e8e;");
        mutationLabel.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 14));
        hbox3.getChildren().addAll(mutationLabel,tauxMutation);
        
        HBox hbox4 = new HBox();
        hbox4.getChildren().addAll(numberOfParticlesLabel,numberOfParticles);
        numberOfParticlesLabel.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 14));
        numberOfParticlesLabel.setStyle("-fx-text-fill: #8e8e8e;");
        HBox hbox5 = new HBox();
        hbox5.getChildren().addAll(C1Label,C1);
        C1Label.setStyle("-fx-text-fill: #8e8e8e;");
        C1Label.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 14));
        HBox hbox6 = new HBox();
        hbox6.getChildren().addAll(C2Label,C2);
        C2Label.setStyle("-fx-text-fill: #8e8e8e;");
        C2Label.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 14));
        HBox hbox7 = new HBox();
        PoidsInertieLabel.setStyle("-fx-text-fill: #8e8e8e;");
        PoidsInertieLabel.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 14));
        hbox7.getChildren().addAll(PoidsInertieLabel,PoidsInertie);
        HBox hbox8 = new HBox();
        timeLabel.setStyle("-fx-text-fill: #8e8e8e;");
        timeLabel.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 14));
        hbox8.getChildren().addAll(timeLabel,time);
        
        time.setMinWidth(100);
        
        vboxGENETIC.getChildren().addAll(hbox0,hbox1,hbox2,hbox3);
        vboxPSO.getChildren().addAll(hbox4, hbox5, hbox6, hbox7);
        hbox.getChildren().addAll(hbox8);
        
        hboxALGOS.setSpacing(200);
        vbox.setSpacing(30);
        
               
        VBox tp1Box = new VBox();
        VBox tp2Box = new VBox();
        VBox tp3Box = new VBox();
        VBox tp4Box = new VBox();
        
        Button optiGenetic = new Button("Optimiser les Paramètres");
        Button optiPSO = new Button("Optimiser les Paramètres");
        
        optiGenetic.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            ParametersOptimization po = new ParametersOptimization("Genetic");
            int[] res = po.Gen();
            
  
                sizePop.setText(String.valueOf(res[3]));
                tauxCroisement.setText(String.valueOf(res[0]));
                tauxMutation.setText(String.valueOf(res[1]));
                maxIter.setText(String.valueOf(res[2]));

            
            }});
        
        optiPSO.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            ParametersOptimization po = new ParametersOptimization("PSO");
            int[] res = po.Gen();
            
  

                 C1.setText(String.valueOf(res[1]));
                C2.setText(String.valueOf(res[2]));
                PoidsInertie.setText(String.valueOf(res[3]));
                numberOfParticles.setText(String.valueOf(res[0])); 
            }});
        
        
        tp1Box.getChildren().addAll(hbox);
        tp2Box.getChildren().addAll(optiGenetic,vboxGENETIC);
        tp3Box.getChildren().addAll(optiPSO,vboxPSO);
        optiGenetic.setAlignment(Pos.CENTER);
        tp2Box.setSpacing(20);
        tp3Box.setSpacing(20);
        tp2Box.setAlignment(Pos.CENTER);
        tp3Box.setAlignment(Pos.CENTER);
        HBox heuristicBox = new HBox();
        heuristicBox.getChildren().addAll(tp2Box, tp3Box);
        program.setMinWidth(450);
        Button sauvegarder = new Button("Sauvegarder");
        
        sauvegarder.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            
                String timingL = time.getText();
                String sizepopL = sizePop.getText();
                String tauxCroisementL = tauxCroisement.getText();
                String tauxMutationL = tauxMutation.getText();
                String maxIterL = maxIter.getText();
                String c1L = C1.getText();
                String c2L = C2.getText();
                String wL = PoidsInertie.getText();
                String numberOfParticlesL = numberOfParticles.getText(); 
            
            
                 try{    
           FileWriter fw=new FileWriter("config.txt");    
           fw.write("timing:"+timingL+"\n");
           fw.write("sizepop:"+sizepopL+"\n");
           fw.write("taux_croisement:"+tauxCroisementL+"\n");
           fw.write("taux_mutation:"+tauxMutationL+"\n");
           fw.write("max_iterations:"+maxIterL+"\n");
           fw.write("c1:"+c1L+"\n");
           fw.write("c2:"+c2L+"\n");
           fw.write("w:"+wL+"\n");
           fw.write("number_particles:"+numberOfParticlesL);
           fw.close();    
          }catch(Exception e){System.out.println(e);}    
      
                
           
                
            }});
        
        
        try{
            BufferedReader br = new BufferedReader(new FileReader("config.txt"));
        for(String line; (line = br.readLine()) != null; ) {
       roders.put(line.split(":")[0], Integer.valueOf(line.split(":")[1]));
        }
                time.setText(String.valueOf(roders.get("timing")));
                sizePop.setText(String.valueOf(roders.get("sizepop")));
                tauxCroisement.setText(String.valueOf(roders.get("taux_croisement")));
                tauxMutation.setText(String.valueOf(roders.get("taux_mutation")));
                maxIter.setText(String.valueOf(roders.get("max_iterations")));
                C1.setText(String.valueOf(roders.get("c1")));
                C2.setText(String.valueOf(roders.get("c2")));
                PoidsInertie.setText(String.valueOf(roders.get("w")));
                numberOfParticles.setText(String.valueOf(roders.get("number_particles"))); 
                
    
        }catch(Exception e){System.out.println(e);}
        /*HBox bbox = new HBox();
        bbox.getChildren().addAll(sauvegarder);
        sauvegarder.setAlignment(Pos.CENTER);
        */
       program.getChildren().addAll(tp1Box, heuristicBox, sauvegarder);
       
       program.setSpacing(100);
       
       tp1Box.setSpacing(20);
      // heuristicBox.setSpacing(20);
       
        tp1Box.setMaxWidth(400);
        tp2Box.setMinWidth(400);
        tp3Box.setMinWidth(400);
        
        program.setAlignment(Pos.CENTER);
        tp1Box.setAlignment(Pos.CENTER);
        
        ret.add(program);ret.add(tp4Box);
    return ret;
    }
    
    
    
    
    
    
     public void start(Stage primaryStage) {
         primaryStage.initStyle(StageStyle.UNDECORATED);
        VBox program = new VBox();
        Button btnOpenNewWindow = new Button("TESTER");
        HBox topBar = new HBox();
        HBox topBarLeft = new HBox();
        HBox topBarRight = new HBox();
        HBox core = new HBox();
        VBox coreGauche = new VBox();
        VBox coreDroite = new VBox();
        topBar.setPadding(new Insets(10, 50, 50, 50));
        core.setPadding(new Insets(5, 50, 50, 50));
        coreGauche.setPadding(new Insets(100, 50, 50, 50));
        
        Label titre = new Label("SOSAT\nSolveur SAT");
        titre.setFont(Font.font("Open SANS", FontWeight.BOLD, 35));
        Label description = new Label("SOSAT est un solveur SAT utilisant différentes techniques de résolution, heuristique avec l'algorithme de parcours"
                + " en profondeur d'abord, le parcours en largeur mais également A*, et des méta-heuristiques avec des algorithmes évolutionnaires tel que l'algorithme"
                + " Génétique et PSO.");
        description.setFont(Font.font("Open SANS", FontWeight.BOLD, 12));
        description.setMaxWidth(300);
        HBox.setMargin(description,new Insets(10,10,10,10));
        description.setWrapText(true);
        HBox space = new HBox();
        space.setMinHeight(30);
        coreGauche.getChildren().addAll(titre, description,space,btnOpenNewWindow);
        core.getChildren().addAll(coreGauche, coreDroite);
        Label SOSAT = new Label("SOSAT");
        SOSAT.setFont(Font.font("Open SANS", FontWeight.BOLD, 16));
        Button parametres = new Button("Paramétres");
        parametres.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
        parametres.setStyle("-fx-background-color:white;");
        parametres.setFont(Font.font("Open SANS", FontWeight.BOLD, 12));
        Button contact = new Button("Contact");
        contact.setStyle("-fx-background-color:white;");
        contact.setFont(Font.font("Open SANS", FontWeight.BOLD, 12));
        contact.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
        Button faq = new Button("FAQ");
        faq.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
        faq.setStyle("-fx-background-color:white;");
        faq.setFont(Font.font("Open SANS", FontWeight.BOLD, 12));
        Button voirStats = new Button("Voir Stats");
        voirStats.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
        description.setStyle("-fx-text-fill: #8e8e8e;");
        description.setTextAlignment(TextAlignment.JUSTIFY);
        voirStats.setStyle("-fx-background-color:#00a4d6;-fx-text-fill: white;-fx-background-radius: 5em;");
        voirStats.setFont(Font.font("Open SANS", FontWeight.BOLD, 12));
        ArrayList<Pane> root = this.stats();
        ArrayList<VBox> rootParam = this.parametres();
        voirStats.setOnAction(new EventHandler<ActionEvent>() {
       public void handle(ActionEvent event) {
        try {
            program.setBackground(Background.EMPTY);
            core.getChildren().clear();
            
            coreGauche.getChildren().clear();
            root.get(0).setMaxSize(200, 400);

            coreGauche.getChildren().addAll(root.get(0));
            coreGauche.setPadding(new Insets(0, 0, 0, 0));
            coreDroite.getChildren().clear();
            root.get(1).setMaxSize(200, 400);
            coreDroite.getChildren().addAll(root.get(1));
            core.getChildren().addAll(coreGauche, coreDroite);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        }
        });
        
        parametres.setOnAction(new EventHandler<ActionEvent>() {
       public void handle(ActionEvent event) {
        try {
            program.setBackground(Background.EMPTY);
            core.getChildren().clear();
            core.getChildren().addAll(coreGauche, coreDroite);
            
            coreGauche.getChildren().clear();
            coreDroite.getChildren().clear();
            coreGauche.setPadding(new Insets(0, 0, 0, 0));
            rootParam.get(0).setMaxSize(800, 400);
            coreGauche.getChildren().addAll(rootParam.get(0));
            coreDroite.getChildren().addAll(rootParam.get(1));
            rootParam.get(1).setMaxSize(800, 400);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        }
        });
        topBarLeft.getChildren().addAll();
        topBarRight.getChildren().addAll();
        topBarRight.setSpacing(50);
        topBarLeft.setMinWidth(512);
        topBar.setMinHeight(100);
        topBarLeft.getChildren().addAll(SOSAT);
        topBarRight.getChildren().addAll(parametres, contact, faq, voirStats);
        topBar.getChildren().addAll(topBarLeft, topBarRight );
        program.getChildren().addAll(topBar, core);
        String url = "assets/Fond/accueil.png";
        Image img = new Image(new File(url).toURI().toString());
        BackgroundImage bgImg = new BackgroundImage(img, 
    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
    BackgroundPosition.DEFAULT, 
    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));

        
       
        program.setBackground(new Background(bgImg));
        btnOpenNewWindow.setStyle("-fx-background-color:#00a4d6;-fx-text-fill: white;-fx-background-radius: 5em;");
        HBox.setMargin(btnOpenNewWindow, new Insets(50, 10, 0, 0));
        btnOpenNewWindow.setMinWidth(50);
        btnOpenNewWindow.setFont(Font.font("Open SANS", FontWeight.BOLD, 12));
        btnOpenNewWindow.setOnAction(new EventHandler<ActionEvent>() {
       public void handle(ActionEvent event) {
        try {
            program.setBackground(Background.EMPTY);
            core.getChildren().clear();
            core.getChildren().addAll();
            coreGauche.getChildren().clear();
            coreGauche.setPadding(new Insets(0, 0, 0, 0));
            root.get(0).setMaxSize(200, 400);
            coreGauche.getChildren().addAll(root.get(0));
            coreDroite.getChildren().clear();
            root.get(1).setMaxSize(200, 400);
            coreDroite.getChildren().addAll(root.get(1));
            core.getChildren().addAll(coreGauche, coreDroite);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        }
        });
      Scene scene = new Scene(program, 1024, 600);
    
        primaryStage.setTitle("SOSAT");
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


    public static void main(String[] args) {
   
        launch(args);
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swarmproject;
import java.util.ArrayList;
import javafx.scene.control.ChoiceBox; 
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TitledPane;
import javafx.geometry.Insets;
import java.io.IOException;
import javafx.scene.control.Accordion;
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
import java.util.Map;
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
import javafx.scene.Node;
import java.io.File;
import java.io.FileWriter;  
import java.util.Scanner; 
import javafx.collections.ObservableList;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
/**
 *
 * @author Rayan
 */
public class SwarmProject extends Application {
public HashMap<String, HashMap<Long, StatisticParam>> OPTIMISATION;
   

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
        
        HBox firstBox = new HBox();
        
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
        
        TitledPane tpFirst = new TitledPane();
       TitledPane tpSecond = new TitledPane();
       TitledPane tpThird = new TitledPane();
       TitledPane tpFourth = new TitledPane();
        
        TextField timeInput = new TextField("500");
        
        
        optimiserAlgo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                
                    String data = (String)datasetChoiceBox.getSelectionModel().getSelectedItem();
                    String algo = (String)selectionAlgo.getSelectionModel().getSelectedItem();
                    int time = Integer.valueOf(timeInput.getText());
                   OPTIMISATION = control.TestParam(data, algo, time);
                   choixParamChoice.getItems().clear();
                    switch(algo){
                        case "GENETIC":
                            tpThird.setContent(thirdBox);
                            choixParamChoice.getItems().addAll("Taille Population","MaxIter","Taux Mutation","Taux Croisement");
                            //choixParamChoice.getSelectionModel().selectFirst();
                            break;
                        case "PSO":
                            tpThird.setContent(thirdBox);
                            choixParamChoice.getItems().addAll("Nombre de particules","C1","C2","Inertie");
                            //choixParamChoice.getSelectionModel().selectFirst();
                            break;
                        default:
                            HBox ne = new HBox();
                            tpThird.setContent(ne);
                            choixParamChoice.getItems().addAll("algorithm");
                            break;
                    }
                    infoChoiceBox.getItems().addAll("Meilleur nombre de clauses","Moyenne nombre de clauses","Meilleur Temps","Moyenne Temps");
                    choixParamChoice.getSelectionModel().selectFirst();
                    infoChoiceBox.getSelectionModel().selectFirst();
                    double bestClauses=0,value=0;
                    fourthBox.getChildren().clear();
                    for ( String key : OPTIMISATION.keySet() ) {
    HBox bbox = new HBox();
    Label lil = new Label(key);
    Label result = new Label();
    
    if(algo.equals("BFS")){ return;}
    if(algo.equals("DFS")){ return;}
    if(algo.equals("A*")){ return;}
    for ( Long keykey : OPTIMISATION.get(key).keySet() ) {
        StatisticParam test = OPTIMISATION.get(key).get(keykey);
    if(test.meilleurNbrClauses>bestClauses){bestClauses=test.meilleurNbrClauses;value=test.value;}
}
    result.setText(String.valueOf(value));
    bbox.getChildren().addAll(lil,result);
                        fourthBox.getChildren().addAll(bbox);
}
                    
                    
                    
                            
             }
            });
        
        thirdBox.getChildren().addAll(optimiserAlgo ,thirdBoxLeft, thirdBoxRight);
        
       
        
       
        
        
        // RESULTATS OPTIMISESSSSS
        Label l = new Label("RESULTATS OPTIMISES");
        fourthBox.getChildren().addAll(l);
        
        ChoiceBox instancesChoiceBox = new ChoiceBox(); 
        Label instancesNumeroLabel = new Label("Instance");
        instancesChoiceBox.getItems().addAll("1","2","3","4","5","6","7","8","9","10");
        instancesBox.getChildren().addAll(table);
        Label timeInputLabel = new Label("Limitation de Temps pour l'Optimisation");
        
        datasetsBox.getChildren().addAll(dirLabel,datasetChoiceBox, instancesNumeroLabel,instancesChoiceBox,timeInputLabel, timeInput);
        
        Label selectionAlgoLabel = new Label("Choisir Mêta-Heuristique/Heuristique");
        selectionAlgo.getItems().addAll("BFS","DFS","GENETIC","PSO");
        
        
        
        
        
         
        
        
        
        choixParamChoice.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
        @Override
            public void changed(ObservableValue<? extends String> observable, //
                    String oldValue, String newValue) {
                if(choixParamChoice.getSelectionModel().getSelectedItem()!=null && infoChoiceBox.getSelectionModel().getSelectedItem()!=null){
                 HashMap<Long, StatisticParam> printer = OPTIMISATION.get((String)choixParamChoice.getSelectionModel().getSelectedItem());
                   series1.getData().clear();
                   for ( Long key : printer.keySet() ) {
                       double y;
                       switch((String)infoChoiceBox.getSelectionModel().getSelectedItem()){
                           case "Meilleur nombre de clauses":
                               y=printer.get(key).meilleurNbrClauses;
                               xAxis.setLabel("Val paramètre");       
                                yAxis.setLabel("Max Clauses");
                               break;
                           case "Moyenne nombre de clauses":
                               y=printer.get(key).moyenneNbrClauses;
                               xAxis.setLabel("Val paramètre");       
                                yAxis.setLabel("Moyenne Clauses");
                               break;
                           case "Meilleur Temps":
                               y=printer.get(key).meilleurTiming;
                               xAxis.setLabel("Val paramètre");       
                                yAxis.setLabel("Meilleur Temps");
                               break;
                           default:
                              y=printer.get(key).moyenneTiming;
                              xAxis.setLabel("Val paramètre");       
                                yAxis.setLabel("Moyenne Temps");
                              break;
                       }
                       
                    series1.getData().add(new XYChart.Data(String.valueOf(key), y));
                    }
                }
        }});
        infoChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
        @Override
            public void changed(ObservableValue<? extends String> observable, //
                    String oldValue, String newValue) {
                if(choixParamChoice.getSelectionModel().getSelectedItem()!=null && infoChoiceBox.getSelectionModel().getSelectedItem()!=null){
                 HashMap<Long, StatisticParam> printer = OPTIMISATION.get((String)choixParamChoice.getSelectionModel().getSelectedItem());
                   series1.getData().clear();
                   for ( Long key : printer.keySet() ) {
                       double y;
                       switch((String)infoChoiceBox.getSelectionModel().getSelectedItem()){
                           case "Meilleur nombre de clauses":
                               y=printer.get(key).meilleurNbrClauses;
                               xAxis.setLabel("Val paramètre");       
                                yAxis.setLabel("Max Clauses");
                               break;
                           case "Moyenne nombre de clauses":
                               y=printer.get(key).moyenneNbrClauses;
                               xAxis.setLabel("Val paramètre");       
                                yAxis.setLabel("Moyenne Clauses");
                               break;
                           case "Meilleur Temps":
                               y=printer.get(key).meilleurTiming;
                               xAxis.setLabel("Val paramètre");       
                                yAxis.setLabel("Meilleur Temps");
                               break;
                           default:
                              y=printer.get(key).moyenneTiming;
                              xAxis.setLabel("Val paramètre");       
                                yAxis.setLabel("Moyenne Temps");
                              break;
                       }
                       
                    series1.getData().add(new XYChart.Data(String.valueOf(key), y));
                    }
                }
        }});
        
        
        
        
        
        
        
        
        instancesChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
        @Override
            public void changed(ObservableValue<? extends String> observable, //
                    String oldValue, String newValue) {
                if (newValue != null) {
                    //greetingLabel.setText(newValue.getGreeting());
                    
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
                    //greetingLabel.setText(newValue.getGreeting());
                    
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
        
        
        
        
        
        secondBox.getChildren().addAll(selectionAlgoLabel, selectionAlgo);
        
        firstBox.getChildren().addAll(datasetsBox, instancesBox);
        selectionAlgo.getSelectionModel().selectLast();
      // choixParamChoice.getSelectionModel().selectFirst();
       tpFirst.setContent(firstBox);
       tpFourth.setStyle("-fx-background-color: transparent ;");
       fourthBox.setStyle("-fx-background-color: transparent ;");
       tpSecond.setContent(secondBox);
       tpThird.setContent(thirdBox);
       tpFourth.setContent(fourthBox);
       
       tpFirst.setMinHeight(200);
       thirdBox.setMinWidth(450);
       tpFirst.setMinWidth(450);
       tpSecond.setMinWidth(450);
       fourthBox.setMinWidth(450);
      // tpFirst.setMinWidth();
       tpFirst.setCollapsible(true);
       tpFirst.setText("DATA SET");
       tpSecond.setText("HEURISTIQUE/META-HEURISTIQUE");
       tpThird.setText("PARAMETRES");
       tpFourth.setText("PERFORMANCES");
       Accordion accordion = new Accordion();
 accordion.getPanes().addAll(tpFirst,tpSecond);
       program.getChildren().addAll(accordion);
       /*String url = "assets/Fond/graphpng.png";
        Image img = new Image(new File(url).toURI().toString());
BackgroundImage bgImg = new BackgroundImage(img, 
    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
    BackgroundPosition.DEFAULT, 
    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));

        
       
       program.setBackground(new Background(bgImg));
       */
       bc.getData().addAll(series1);
       ret.add(program);ret.add(fourthBox);ret.add(thirdBox);
       return ret;
    
    }
    
    
    public ArrayList<VBox> parametres(){
        ArrayList<VBox> ret = new ArrayList<VBox>();
    VBox program = new VBox();
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
        TextField sizePop = new TextField("20");
        Label croisementLabel = new Label();
        croisementLabel.setText("Taux de Croisement [0 - 100]");
        Label mutationLabel = new Label();
        mutationLabel.setText("Taux de Mutation [0 - 100]");
        Label maxIterLabel = new Label();
        maxIterLabel.setText("Max Iterations (0 pour illimité)");
         Label sizePopLabel = new Label();
        sizePopLabel.setText("Taille population");
        
        
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
             
                
                HashMap<String, Integer> parameters = new HashMap<String, Integer>();
                parameters.put("number_instances", 10);
                parameters.put("timing", Integer.valueOf(time.getText()));
                parameters.put("size_population", Integer.valueOf(sizePop.getText()));
                parameters.put("taux_croisement", Integer.valueOf(tauxCroisement.getText()));
                parameters.put("taux_mutation", Integer.valueOf(tauxMutation.getText()));
                parameters.put("max_iteration", Integer.valueOf(maxIter.getText()));
                parameters.put("C1", Integer.valueOf(C1.getText()));
                parameters.put("C2", Integer.valueOf(C2.getText()));
                parameters.put("W", Integer.valueOf(PoidsInertie.getText()));
                parameters.put("number_particles", Integer.valueOf(numberOfParticles.getText())); 
                String directory = "datasets/uf50-218";
                
                HashMap<String, Statistic> map = control.TestAll(parameters, directory);
                
                for (Map.Entry<String, Statistic> element : map.entrySet()) {
                    System.out.println("Key : "+element.getKey()+" | Timing  : "+element.getValue().getTiming()+" | Nbr Clauses : "+element.getValue().getNbrClauses());
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
        VBox hbox = new VBox();
        HBox hboxALGOS = new HBox();
        HBox graphs = new HBox();
        
        VBox vboxGENETIC = new VBox();
        VBox vboxPSO = new VBox();
        
        Label timeLabel = new Label();
        timeLabel.setText("Temps maximum pour une instance (0 pour illimité) ");
        

        HBox hbox0 = new HBox();
        hbox0.getChildren().addAll(sizePopLabel,sizePop);
        sizePopLabel.setStyle("-fx-text-fill: #8e8e8e;");
        sizePopLabel.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 16));
        
        HBox hbox1 = new HBox();
        hbox1.getChildren().addAll(maxIterLabel,maxIter);
        maxIterLabel.setStyle("-fx-text-fill: #8e8e8e;");
        maxIterLabel.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 16));
        HBox hbox2 = new HBox();
        hbox2.getChildren().addAll(croisementLabel,tauxCroisement);
        croisementLabel.setStyle("-fx-text-fill: #8e8e8e;");
        croisementLabel.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 16));
        HBox hbox3 = new HBox();
        mutationLabel.setStyle("-fx-text-fill: #8e8e8e;");
        mutationLabel.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 16));
        hbox3.getChildren().addAll(mutationLabel,tauxMutation);
        
        HBox hbox4 = new HBox();
        hbox4.getChildren().addAll(numberOfParticlesLabel,numberOfParticles);
        numberOfParticlesLabel.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 16));
        numberOfParticlesLabel.setStyle("-fx-text-fill: #8e8e8e;");
        HBox hbox5 = new HBox();
        hbox5.getChildren().addAll(C1Label,C1);
        C1Label.setStyle("-fx-text-fill: #8e8e8e;");
        C1Label.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 16));
        HBox hbox6 = new HBox();
        hbox6.getChildren().addAll(C2Label,C2);
        C2Label.setStyle("-fx-text-fill: #8e8e8e;");
        C2Label.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 16));
        HBox hbox7 = new HBox();
        PoidsInertieLabel.setStyle("-fx-text-fill: #8e8e8e;");
        PoidsInertieLabel.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 16));
        hbox7.getChildren().addAll(PoidsInertieLabel,PoidsInertie);
        HBox hbox8 = new HBox();
        timeLabel.setStyle("-fx-text-fill: #8e8e8e;");
        timeLabel.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 16));
        hbox8.getChildren().addAll(timeLabel,time);
        
        vboxGENETIC.getChildren().addAll(hbox0,hbox1,hbox2,hbox3);
        vboxPSO.getChildren().addAll(hbox4, hbox5, hbox6, hbox7);
        hbox.getChildren().addAll(hbox8);
       // hbox.setMaxSize(1024, 768);
        
        hboxALGOS.setSpacing(200);
        vbox.setSpacing(30);
        graphs.getChildren().addAll(bcClauses,bc);
        
        HBox directoryBox = new HBox();
              
         
                            
                            
            
        Label dirLabel = new Label("Dossier contenant les instances ");
        dirLabel.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 16));
         
        directoryBox.setSpacing(30);
        
        
        TitledPane tp1 = new TitledPane();
        TitledPane tp2 = new TitledPane();
        TitledPane tp3 = new TitledPane();
        TitledPane tp4 = new TitledPane();
        VBox tp1Box = new VBox();
        VBox tp2Box = new VBox();
        VBox tp3Box = new VBox();
        VBox tp4Box = new VBox();
        tp1.setContent(tp1Box);
        tp2.setContent(tp2Box);
        tp3.setContent(tp3Box);
        tp4.setContent(tp4Box);
        
        tp1.getStyleClass().add("pane"); 
        tp2.getStyleClass().add("pane"); 
        tp3.getStyleClass().add("pane"); 
        tp4.getStyleClass().add("pane"); 
        
        tp1Box.getChildren().addAll(hbox);
        tp2Box.getChildren().addAll(vboxGENETIC);
        tp3Box.getChildren().addAll(vboxPSO);
        tp4Box.getChildren().addAll(btn,graphs);
        
        tp1.setText("PARAMETRES GENERAUX");
        tp2.setText("PARAMETRES ALGO GENETIQUE");
        tp3.setText("PARAMETRES ALGO PSO");
        tp4.setText("ETUDE COMPARATIVE");
        
        Accordion accordion = new Accordion();
 accordion.getPanes().addAll(tp1, tp2, tp3);
        program.getChildren().addAll(accordion);
        
        program.setMinWidth(450);
        accordion.setMinWidth(450);
        tp1Box.setMaxWidth(450);
        tp2Box.setMinWidth(450);
        tp3Box.setMinWidth(450);
       bc.getData().addAll(series1);
       bcClauses.getData().addAll(series1Clauses);
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
        parametres.setStyle("-fx-background-color:white;");
        parametres.setFont(Font.font("Open SANS", FontWeight.BOLD, 12));
        Button contact = new Button("Contact");
        contact.setStyle("-fx-background-color:white;");
        contact.setFont(Font.font("Open SANS", FontWeight.BOLD, 12));
        Button faq = new Button("FAQ");
        faq.setStyle("-fx-background-color:white;");
        faq.setFont(Font.font("Open SANS", FontWeight.BOLD, 12));
        Button voirStats = new Button("Voir Stats");
        description.setStyle("-fx-text-fill: #8e8e8e;");
        description.setTextAlignment(TextAlignment.JUSTIFY);
        voirStats.setStyle("-fx-background-color:#00a4d6;-fx-text-fill: white;-fx-background-radius: 5em;");
        voirStats.setFont(Font.font("Open SANS", FontWeight.BOLD, 12));
        ArrayList<Pane> root = this.stats();
        ArrayList<VBox> rootParam = this.parametres();
        voirStats.setOnAction(new EventHandler<ActionEvent>() {
       public void handle(ActionEvent event) {
        try {
            /*
            Stage stage = new Stage();
            
            stage.setTitle("Stats");
            stage.setScene(new Scene(root, 500, 700));
            stage.show();
            */
            program.setBackground(Background.EMPTY);
            coreGauche.getChildren().clear();
            root.get(0).setMaxSize(200, 400);
            coreGauche.getChildren().addAll(root.get(0));
            coreDroite.getChildren().clear();
            root.get(1).setMaxSize(200, 400);
            root.get(2).setMaxSize(200, 400);
            coreDroite.getChildren().addAll(root.get(2),root.get(1));
            // Hide this current window (if this is what you want)
            //((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
});
        
        parametres.setOnAction(new EventHandler<ActionEvent>() {
       public void handle(ActionEvent event) {
        try {
            /*
            Stage stage = new Stage();
            Scene sc = new Scene(rootParam, 500, 700);
            sc.getStylesheets().add("/Users/q/Documents/Rayan/SII/PROJET ESSAIM/SwarmProject/style.css"); 
            stage.setTitle("Paramètres");
            stage.setScene(sc);
            stage.show();
            */
            program.setBackground(Background.EMPTY);
            coreGauche.getChildren().clear();
            rootParam.get(0).setMaxSize(200, 400);
            coreGauche.getChildren().addAll(rootParam.get(0));
            coreDroite.getChildren().clear();
            rootParam.get(1).setMaxSize(200, 400);
            coreDroite.getChildren().addAll(rootParam.get(1));
            // Hide this current window (if this is what you want)
            //((Node)(event.getSource())).getScene().getWindow().hide();
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
            /*
            Stage stage = new Stage();
            
            stage.setTitle("Stats");
            stage.setScene(new Scene(root, 500, 700));
            stage.show();
            */
            program.setBackground(Background.EMPTY);
            coreGauche.getChildren().clear();
            root.get(0).setMaxSize(600, 400);
            coreGauche.getChildren().addAll(root.get(0));
            coreDroite.getChildren().clear();
            
            root.get(1).setMaxSize(800, 400);
            root.get(2).setMaxSize(800, 400);
            coreDroite.getChildren().addAll(root.get(2),root.get(1));
            // Hide this current window (if this is what you want)
            //((Node)(event.getSource())).getScene().getWindow().hide();
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
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
   
        launch(args);
    }
    
}

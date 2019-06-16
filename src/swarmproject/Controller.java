/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swarmproject;
import java.util.ArrayList;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;


/**
 *
 * @author Rayan
 */
public class Controller {
    private Sat sa;
    private int timing = 5;
    private String method;
    private ArrayList<String> methods= new ArrayList<String>() {{
    
    add("A*");
    add("Aléatoire");
    add("Largeur");
    add("Profondeur");
    add("Genetic");
     add("PSO");
 //   add("Jeroslow");
 //   add("Bohm");
 //   add("Moms");
}}; 
    public void ChooseMethod(String method)
    {
        switch(method)
        {
            case "Aléatoire":
                this.sa=new Sat();
                this.method="Aléatoire";
            case "Largeur":
                this.sa=new SatAveugle();
                this.sa.ChooseMethod("Largeur");
                this.method="Largeur";
                break;
            case "Profondeur":
                this.sa=new SatAveugle();
                this.sa.ChooseMethod("Profondeur");
                this.method="Profondeur";
                break;
            case "Bohm":
                this.sa=new SatAStar();
                this.sa.ChooseMethod("Bohm");
                this.method="Bohm";
                break;
            case "A*":
                this.sa=new SatAStar();
                this.sa.ChooseMethod("A* Par clauses non-satisfaites");
                this.method="A* Par clauses non-satisfaites";
                break;
            case "Moms":
                this.sa=new SatAStar();
                this.sa.ChooseMethod("Moms");
                this.method="Moms";
                break;
            case "Jeroslow":
                this.sa=new SatAStar();
                this.sa.ChooseMethod("Jeroslow");
                this.method="Jeroslow";
                break;
            case "Genetic":
                this.sa=new GeneticSat();
                this.method="Genetic";
                System.out.println("You have choose genetic ");
                break;
            case "PSO":
                this.sa=new ParticleSwarmSat();
                this.method="PSO";
                System.out.println("You have choose PSO ");
                break;
        }
    }
    
    public String getActivatedMethod(){return this.method;}
    
    public HashMap<String, HashMap<Long, StatisticParam>> TestParam(String dataset, String algorithm, int time)
    {
        ArrayList<Statistic> stats = new ArrayList<Statistic>();
       String directory = "datasets/"+dataset;
        if(algorithm.equals("PSO"))
        {this.ChooseMethod("PSO");}
        else if(algorithm.equals("Genetic")){this.ChooseMethod("Genetic");}
        else if(algorithm.equals("A*")){this.ChooseMethod("A*");}
            else if(algorithm.equals("BFS")){this.ChooseMethod("Largeur");}
            else{this.ChooseMethod("Profondeur");}
    final File folder = new File(directory);
    ArrayList<String> files= this.listFilesForFolder(folder);
    int i=0;
    int nbr = 10;
    int size = (files.size()<nbr)?files.size():nbr;
    HashMap<String, ArrayList<Statistic>> res = new HashMap<String, ArrayList<Statistic>>();
    ArrayList<HashMap> instances = new ArrayList<HashMap>();
    while(i<size)
    {
        System.out.println("INSTANCE ("+i+"/"+size+") Algorithm : "+algorithm);
        String f=files.get(i);
        res=this.FileTestParam(directory+"/"+f,  i, time);  
        instances.add(res);
        i++;
    }
    ArrayList<StatisticParam> resultatsFinaux = new ArrayList<StatisticParam>();
    HashMap<String, HashMap<Long, StatisticParam>> wello = new HashMap<String, HashMap<Long, StatisticParam>>();
   for ( String key : res.keySet() ) {
       HashMap<Long, StatisticParam> testKey = new HashMap<Long, StatisticParam>();

    for(int j=0;j<res.get(key).size();j++){
    StatisticParam statParam = new StatisticParam();
    statParam.parametre = key;

    for(int k=0;k<instances.size();k++)
        {
            HashMap<String, ArrayList<Statistic>> temp = instances.get(k);
            statParam.instances.add(temp.get(key).get(j));
        }
    statParam.calculate();
    testKey.put(statParam.value, statParam);
    }   
    wello.put(key, testKey);
    
    }
   
   
  // System.out.println("C1 keys : "+wello.get("C1").keySet());
    return wello;
    }
    
    public HashMap<String, ArrayList<Statistic>> FileTestParam(String file, int instance, int time)
    {
    this.sa.ChoosePath(file);
    //System.out.println("METHODE : "+this.method+" LETS GO !");
    return this.sa.CreateSolutionParameter(instance, time);
    }
    
    
    
    
    
    
    
    public boolean VerifyDirectory(String directory)
    {
       final File folder = new File(directory);
    ArrayList<String> files= this.listFilesForFolder(folder);
    int i=0;
    while(i<files.size())
    {
        String f=files.get(i);
        if(!files.get(i).endsWith(f)){return false;}
        i++;
    }
    return true;
    }
    
    
    public ArrayList<Row> getRows(String data,Integer instance)
    {
    String directory = "datasets/"+data;
    final File folder = new File(directory);

    ArrayList<String> files= this.listFilesForFolder(folder);
    int size = instance-1;
    String f=files.get(size);

    FileCnf cnf = new FileCnf(directory+"/"+f);   
     return cnf.getRows();
    }
    
    
    public ArrayList<String> getMethods(){return this.methods;}
    
    public HashMap TestAll(HashMap<String, Integer> parameters, String directory){
        HashMap<String, Statistic> map = new HashMap<String,Statistic>();
   ArrayList<String> methods = this.getMethods();
   
   Scanner scan= new Scanner(System.in);

//System.out.println(" Input directory of benchmark : ");
//String directory=scan.nextLine();

    for(int i=0;i<methods.size();i++)
    {
        System.out.println("Method "+methods.get(i));
        String method = methods.get(i);
        this.ChooseMethod(methods.get(i));
        Statistic mean = this.FolderTest(directory, parameters);
        map.put(method, mean);
    }
    
       System.out.println("Resultat Final : "+Arrays.asList(map));
       return map;
    }
    
    public Statistic FolderTest(String directory, HashMap<String, Integer> parameters)
    {
    Statistic stat = new Statistic();
    final File folder = new File(directory);
    ArrayList<String> files= this.listFilesForFolder(folder);
    int i=0;
    long sum=0;
    int nbr = parameters.get("number_instances");
    int size = (files.size()<nbr)?files.size():nbr;
    while(i<size)
    {
        String f=files.get(i);
        Statistic res=this.FileTest(directory+"/"+f, parameters, i);  
        stat.setTiming( stat.getTiming() + res.getTiming() ) ;
        stat.setNbrClauses( stat.getNbrClauses() + res.getNbrClauses() ) ;
        i++;
    }
    stat.setTiming( stat.getTiming() /i  ) ;
    stat.setNbrClauses( stat.getNbrClauses() /i ) ;
    return stat;
    }
    
    public Statistic FileTest(String file, HashMap<String, Integer> parameters, int instance)
    {
    this.sa.ChoosePath(file);
    return this.sa.CreateSolution(parameters, instance);
    }
        
    public ArrayList<String> listFilesForFolder(final File folder)
    {       
    ArrayList<String> files=new ArrayList<String>();
    for (final File fileEntry : folder.listFiles()) {
        if (fileEntry.isDirectory()) {
            listFilesForFolder(fileEntry);
        } else {
            files.add(fileEntry.getName());
        }
    }
    return files;
    }
}
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
    
    add("A* Par clauses non-satisfaites");
    add("Aléatoire");
    add("Largeur");
    add("Profondeur");
    add("Genetic");
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
            case "A* Par clauses non-satisfaites":
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
        }
    }
    
    public String getActivatedMethod(){return this.method;}
    
    public ArrayList<String> getMethods(){return this.methods;}
    
    public HashMap TestAll(int nbr, int timing){
        HashMap<String, Statistic> map = new HashMap<String,Statistic>();
   ArrayList<String> methods = this.getMethods();
   
   Scanner scan= new Scanner(System.in);

//System.out.println(" Input directory of benchmark : ");
//String directory=scan.nextLine();
String directory = "/Users/q/Documents/Rayan/SII/PROJET ESSAIM/uf20-91";
    for(int i=0;i<methods.size();i++)
    {
        System.out.println("Method "+methods.get(i));
        String method = methods.get(i);
        this.ChooseMethod(methods.get(i));
        Statistic mean = this.FolderTest(directory, nbr, timing);
        map.put(method, mean);
    }
    
       System.out.println("Resultat Final : "+Arrays.asList(map));
       return map;
    }
    
    public Statistic FolderTest(String directory, int nbr, int timing)
    {
    Statistic stat = new Statistic();
    final File folder = new File(directory);
    ArrayList<String> files= this.listFilesForFolder(folder);
    int i=0;
    long sum=0;
    int size = (files.size()<nbr)?files.size():nbr;
    while(i<size)
    {
        String f=files.get(i);
        Statistic res=this.FileTest(directory+"/"+f, timing);  
        stat.setTiming( stat.getTiming() + res.getTiming() ) ;
        stat.setNbrClauses( stat.getNbrClauses() + res.getNbrClauses() ) ;
        i++;
    }
    stat.setTiming( stat.getTiming() /i  ) ;
    stat.setNbrClauses( stat.getNbrClauses() /i ) ;
    return stat;
    }
    
    public Statistic FileTest(String file, int timing)
    {
    this.sa.ChoosePath(file);
    return this.sa.CreateSolution(timing);
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
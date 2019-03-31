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
    private String method;
    private ArrayList<String> methods= new ArrayList<String>() {{
    add("A* Par clauses non-satisfaites");
        add("Aléatoire");
     add("Largeur");
    add("Profondeur");
    
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
        }
    }
    
    public String getActivatedMethod(){return this.method;}
    
    public ArrayList<String> getMethods(){return this.methods;}
    
    public HashMap TestAll(int nbr){
        HashMap<String,Double> map = new HashMap<String,Double>();
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
        double mean = this.FolderTest(directory, nbr);
        map.put(method, mean);
    }
    
       System.out.println("Resultat Final : "+Arrays.asList(map));
       return map;
    }
    
    public double FolderTest(String directory, int nbr)
    {
    final File folder = new File(directory);
    ArrayList<String> files= this.listFilesForFolder(folder);
    int i=0;
    long sum=0;
    int size = (files.size()<nbr)?files.size():nbr;
    while(i<size)
    {
        String f=files.get(i);
//        System.out.println("File ("+i+"/"+files.size()+")");
        long res=this.FileTest(directory+"/"+f);  
        
 //       System.out.println("TIME "+res);
        sum+=res;
        i++;
    }
    return sum/i;
    }
    
    public long FileTest(String file)
    {
    this.sa.ChoosePath(file);
    return this.sa.CreateSolution();
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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swarmproject;
import java.util.ArrayList;
import java.io.File;



/**
 *
 * @author Rayan
 */
public class Controller {
    private Sat sa;
    private String method;
    private ArrayList<String> methods= new ArrayList<String>() {{
    add("Aléatoire");
     add("Largeur");
    add("Profondeur");
   
}}; 
    public void ChooseMethod(String method, String heuristic)
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
        }
    }
    
    public String getActivatedMethod(){return this.method;}
    
    public ArrayList<String> getMethods(){return this.methods;}
    
    
    public double FolderTest(String directory)
    {
    final File folder = new File(directory);
    ArrayList<String> files= this.listFilesForFolder(folder);
    int i=0;
    long sum=0;
    while(i<files.size())
    {
        String f=files.get(i);
        System.out.println("File ("+i+"/"+files.size()+")");
        sum+=this.FileTest(directory+"/"+f);     
        i++;
    }
    return sum/i;
    }
    
    public long FileTest(String file)
    {
    this.sa.ChoosePath(file);
    this.sa.CreateSolution();
    return this.sa.getExecutionTime();
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

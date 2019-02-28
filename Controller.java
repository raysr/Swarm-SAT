/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetessaim;
import java.util.ArrayList;
import java.io.File;
/**
 *
 * @author Rayan
 */
public class Controller {
    public FileCnf fc;
    public int[][] representation;
    public Sat sa;
    public Controller()
    {
    sa=new Sat();
    sa.RandomSolution();
    }
    
    public boolean FolderTest(String directory)
    {
        final File folder = new File(directory);
    ArrayList<String> files= this.listFilesForFolder(folder);
    boolean test=false;
    int i=0;
    while(!test && i<files.size())
    {
        test=FileTest(directory+"/"+files.get(i));
        i++;
    }
    return test;
    }
    public boolean FileTest(String file)
    {
    System.out.println("Reading file : "+file);
    boolean test=false;
    fc = new FileCnf(file);
    test = fc.TestCnfSolution(sa.getSolution());
    System.out.println("The result of validation was : "+ test);
    return test;
    }
    
    public ArrayList<String> listFilesForFolder(final File folder){       
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

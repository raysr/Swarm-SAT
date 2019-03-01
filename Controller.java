/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetessaim;
import java.util.ArrayList;
import java.io.File;
import java.util.Queue;

import javax.swing.DefaultListModel;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Rayan
 */
public class Controller {
    public FileCnf fc;
    public int[][] representation;
    public Sat sa;
    public void Randomize(DefaultListModel mod1)
    {
    sa=new Sat();
    sa.RandomSolution(mod1);
    }
    
    
    
    public String FolderTest(String directory, javax.swing.JTextField jtext1, javax.swing.JTextField jtext2)
    {
        final File folder = new File(directory);
    ArrayList<String> files= this.listFilesForFolder(folder);
    boolean test=false;
    int i=0;
    int acti=0;
    int max=0;
    String max_file="";
    while(acti!=91 && i<files.size())
    {
        String f=files.get(i);
        acti=this.FileTest(directory+"/"+f);
        if(acti>max)
        {
        max=acti;
        max_file=f;
        }
       
        i++;
    }

    jtext1.setText(max_file);
    jtext2.setText(Integer.toString(max));
 
    return max_file;
    }
    public int FileTest(String file)
    {
    fc = new FileCnf(file);
    return fc.TestCnfSolution(sa.getSolution());
    }
    
    
        public Queue<Queue> FillTest(String file)
    {
   
    fc = new FileCnf(file);
    return fc.FillSolution(sa.getSolution());
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

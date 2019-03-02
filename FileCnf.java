/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetessaim;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue; 

/**
 *
 * @author Rayan
 */
public class FileCnf {
      private int[][] cnfs_format;
      private String path;
      private int nbrClauses=0;
      private ArrayList<String[]> read= new ArrayList<String[]>();
    public FileCnf(String path)
    {
        try
    {
        this.path=path;
         BufferedReader reader;
         reader = new BufferedReader(new FileReader(path));
	String line = reader.readLine();
        int i=0;
        
	while (line != null) {
            try{
            if(line.charAt(0)!='c' && line.charAt(0)!='p' && line.charAt(0)!='0' && line.charAt(0)!='%')
            {
                String [] tmp = line.split("\\s+");
                String[] splited = Arrays.copyOf(tmp, tmp.length-1);
                String[] now=new String[3];
                int co=0;
                for(int bim=0;bim<splited.length;bim++)
                {
                if(!splited[bim].equals(""))
                {
                now[co]=splited[bim];
                co++;
                }
                }

                this.read.add(now);
            }
            }catch(Exception e){}
	line = reader.readLine();
	i++;
        }
	reader.close();
         }
          catch (IOException e)  
    {
        System.out.println("File Not Found.");
    }
        this.GenerateCnf();
    }
    public int ValidateSolution(int[] solution)
    {
    int size=this.cnfs_format.length;
    int i=0;
    boolean bigger_test=true;
    while(i<size && bigger_test)
    {
        boolean test=false;
        int j=0;
        int size2 = this.cnfs_format[i].length;
        while(j<size2 && !test)
        {
        int abs=Math.abs(this.cnfs_format[i][j]);
        if((this.cnfs_format[i][j]>0 && solution[abs]==1) || (this.cnfs_format[i][j]<0 && solution[abs]==-1)){test=true;}
        j++;
         }
        if(!test){bigger_test=false;return i;}
        i++;
    }
    return i;
    }
    
    
    
  public void GenerateCnf()
  {
    int size=this.read.size();
    int i=0;
    String[] clause=this.read.get(i);
    int size2= clause.length;
    this.cnfs_format=new int[size][size2];
    while(i<size)
    {
        clause=this.read.get(i);

        size2= clause.length;
        int j=0;
        while(j<size2)
        {
        int var=Integer.valueOf(clause[j]);
        this.cnfs_format[i][j]=var;
        j++;
        }
        i++;
    }
    this.nbrClauses=i;
  }
  public int getNbrClauses(){return this.nbrClauses;}
  public int[][] getFormatedCnf(){return this.cnfs_format;}
    
}

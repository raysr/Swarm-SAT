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
      private Queue<Queue> cnfs_format= new LinkedList<Queue>();
      private String path;
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
	// read next line
	line = reader.readLine();
	i++;
        }

	reader.close();
         }
          catch (IOException e)  
    {
        System.out.println("File Not Found.");
    }
    }
    public int TestCnfSolution(int[] solution)
    {
     int size=this.read.size();
    int i=0;
    int count=0;
    boolean bigger_test=true;
    while(i<size && bigger_test)
    {
        String[] clause=this.read.get(i);
        Queue<Integer> line = new LinkedList<Integer>();
        boolean test=false;
        int j=0;
        while(j<3 && !test)
        {
         
        int var=Integer.valueOf(clause[j]);
        int abs=Math.abs(var);
        if((var>0 && solution[abs]==1) || (var<0 && solution[abs]==0)){test=true;}
        line.add(var);
      
            
           j++;
         }
        if(!test){bigger_test=false;}
        this.cnfs_format.add(line);
        i++;
        count++;
    }

    return i;
    }
    
    
    
    
        public Queue<Queue> FillSolution(int[] solution)
    {
        System.out.println("Best one : "+this.path);
     int size=this.read.size();
    int i=0;
    int count=0;
    boolean bigger_test=true;
    while(i<size && bigger_test)
    {
        String[] clause=this.read.get(i);
        Queue<Integer> line = new LinkedList<Integer>();
        boolean test=false;
        int j=0;

        while(j<3 && !test)
        {
            
        int var=Integer.valueOf(clause[j]);
        int abs=Math.abs(var);
        if((var>0 && solution[abs]==1) || (var<0 && solution[abs]==0)){test=true;}
        line.add(var);
           j++;
         }
        if(!test){bigger_test=false;}
        this.cnfs_format.add(line);
        i++;
        count++;
    }
    System.out.println("Count : "+count+" size : "+this.cnfs_format.size());
    return this.cnfs_format;
    }
        
  public void GenerateCnfFormated()
  {
       int size=this.read.size();
    int i=0;
    int count=0;
    boolean bigger_test=true;
    while(i<size)
    {
        String[] clause=this.read.get(i);
        Queue<Integer> line = new LinkedList<Integer>();
        boolean test=false;
        int j=0;
        while(j<3)
        {
         
        int var=Integer.valueOf(clause[j]);
        int abs=Math.abs(var);
        
        line.add(var);
      
            
           j++;
         }
        count++;
        if(!test){bigger_test=false;}
        this.cnfs_format.add(line);
        i++;
    }
  }
    
  public Queue<Queue> getFormatedCnf(){return this.cnfs_format;}
    
}

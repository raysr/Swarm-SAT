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

/**
 *
 * @author Rayan
 */
public class FileCnf {
    private ArrayList<String[]> cnfs= new ArrayList<String[]>();
      private ArrayList<int[]> cnfs_format= new ArrayList<int[]>();
    public FileCnf(String path)
    {
        try
    {
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

                this.cnfs.add(now);
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
     int size=this.cnfs.size();
    int i=0;
    int count=0;
    boolean bigger_test=true;
    while(i<size && bigger_test)
    {
        String[] clause=this.cnfs.get(i);
        int[] line=new int[3];
        boolean test=false;
        int j=0;
        while(j<3 && !test)
        {
         
        int var=Integer.valueOf(clause[j]);
        int abs=Math.abs(var);
        if((var>0 && solution[abs]==1) || (var<0 && solution[abs]==0)){test=true;}
        line[j]=var;
      count++;
            
           j++;
         }
        if(!test){bigger_test=false;}
        this.cnfs_format.add(line);
        i++;
    }

    return i;
    }
    
    
    
    
        public ArrayList<int[]> TestCnfSpecialSolution(int[] solution)
    {
     int size=this.cnfs.size();
    int i=0;
    int count=0;
    boolean bigger_test=true;
    while(i<size && bigger_test)
    {
        String[] clause=this.cnfs.get(i);
        int[] line=new int[3];
        boolean test=false;
        int j=0;

        while(j<3 && !test)
        {
            
        int var=Integer.valueOf(clause[j]);
        int abs=Math.abs(var);
        if((var>0 && solution[abs]==1) || (var<0 && solution[abs]==0)){test=true;}
        line[j]=var;
    
        
      count++;
         
         
           j++;
         }
        /*
        String a=Integer.toString(line[0]);
        String b=Integer.toString(line[1]);
        String c=Integer.toString(line[2]);
        tabmodel1.addRow(new Object[]{a, b, c});
*/
        if(!test){bigger_test=false;}
        this.cnfs_format.add(line);
        i++;
    }

    return this.cnfs_format;
    }
        
 
    
    
}

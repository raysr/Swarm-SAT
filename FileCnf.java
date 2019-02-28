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
                cnfs.add(splited);
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
    public boolean TestCnfSolution(int[] solution)
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
            if(clause[j].equals("")==false)
            {
        int var=Integer.valueOf(clause[j]);
        int abs=Math.abs(var);
        if((var>0 && solution[abs]==1) || (var<0 && solution[abs]==0)){test=true;}
        line[j]=var;
      count++;
            }
           j++;
         }
        if(!test){bigger_test=false;}
        this.cnfs_format.add(line);
        System.out.println("Line :"+i);
        i++;
    }
    return bigger_test;
    }
    
    public void print()
    {
                System.out.println("Length : "+cnfs.size());
        System.out.println("Example : "+Arrays.toString(cnfs.get(3)));
    }
    public ArrayList<String[]> getCnfs(){return this.cnfs;}
}

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
    
    public void print()
    {
                System.out.println("Length : "+cnfs.size());
        System.out.println("Example : "+Arrays.toString(cnfs.get(3)));
    }
}

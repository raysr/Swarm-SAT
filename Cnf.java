/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetessaim;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.*;
/**
 *
 * @author Rayan
 */
public class Cnf {
    /* Ensemble ou chaque ligne représente une variable, chaque colonne représente une des clauses
    ou cette variable est présente, et la colonne 21 représente le nombre de clauses ou celle-ci est présente
    */
   private int[][] cnfs= new int[21][92]; 
   
    public Cnf(ArrayList<String[]> entry)
    {
    int size=entry.size();
    int i=0;
    int count=0;
    for(i=0;i<size;i++)
    {
        String[] clause=entry.get(i);
        for(int j=0;j<3;j++)
        {
            if(clause[j].equals("")==false)
            {
          System.out.println(clause[j]);
        int var=Integer.valueOf(clause[j]);
        System.out.println("Foundi "+var);
        int lastPresence=this.cnfs[Math.abs(var)][91];
        this.cnfs[Math.abs(var)][lastPresence]=(var>0)?i:-i;
        this.cnfs[Math.abs(var)][91]++;
        System.out.println("line : "+this.cnfs[Math.abs(var)][lastPresence]);
        
      count++;
            }
           
         }
     
        System.out.println("TOTAL : "+count)
    }
    
    }
    
    public int[][] getMatrix()
    {
    return this.cnfs;
    }
    
}

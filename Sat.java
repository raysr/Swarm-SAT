/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swarmproject;
import java.util.Random;
import java.lang.*;
/**
 *
 * @author Rayan
 */
public class Sat {
   private int[] solution; 
   private FileCnf cnf;
   private long startTime,endTime,totalTime;
   
   
   
   public void startTime(){this.startTime = System.nanoTime();}
   public void endTime(){this.endTime = System.nanoTime();this.totalTime=this.endTime-this.startTime;}
   public Sat()
    {
        System.out.println("Aléatoire crée");
    }
    public void ChoosePath(String path)
    {
        this.cnf = new FileCnf(path);
    }
   
   
    public Sat(String path)
    {
        this.cnf = new FileCnf(path);
    }
   public long CreateSolution()
   {
       System.out.println("Aléatoire choisi");
       this.startTime();
    int size=this.cnf.getNbrVars();
    boolean test=false;
    while(!test)
    {
    this.solution=new int[size];   
    int[] poss={0,1};
    for(int i=1;i<size;i++)
    {
        Random generator = new Random();
        int randomIndex = generator.nextInt(poss.length);
        this.solution[i]=poss[randomIndex];
    }
    test=(this.cnf.ValidateSolution(this.solution)==this.cnf.getNbrClauses());
    }
    this.endTime();
    return this.totalTime;
   }
   
   public void ChooseMethod(String method)
   {}
public int[] getSolution(){return this.solution;}
public long getExecutionTime(){return this.totalTime;}

}

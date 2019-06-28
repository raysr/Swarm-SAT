/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swarmproject;
import java.util.Random;
import java.lang.*;
import java.util.HashMap;
import java.util.ArrayList;
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
        //System.out.println("Aléatoire crée");
    }
    public void ChoosePath(String path)
    {
        this.cnf = new FileCnf(path);
    }
   
   
    public Sat(String path)
    {
        this.cnf = new FileCnf(path);
    }
    public HashMap<String, ArrayList<Statistic>> CreateSolutionParameter( int instance, int time)
    {
    return new HashMap<String, ArrayList<Statistic>>();
    }
   public Statistic CreateSolution(HashMap<String, Double> parameters, int instance)
   {
       double timing = parameters.get("timing");
      // System.out.println("Aléatoire choisi");
       Statistic stat = new Statistic();
       long check=0;
       int best=0;
       this.startTime();
    int size=this.cnf.getNbrVars();
    int tryit = 0;
    boolean test=false;
    while(!test && ((timing!=0 && check<timing) || timing==0))
    {
    this.solution=new int[size];   
    int[] poss={0,1};
    for(int i=1;i<size;i++)
    {
        Random generator = new Random();
        int randomIndex = generator.nextInt(poss.length);
        this.solution[i]=poss[randomIndex];
    }
    tryit = this.cnf.ValidateSolution(this.solution);
    if(tryit>best){best=tryit;}
    test=(tryit==this.cnf.getNbrClauses());
    System.out.println("FOUND ! ");
    check = (System.nanoTime() - this.startTime)/ 10000;
    }
    this.endTime();
    stat.setNbrClauses(best);
    stat.instance=instance;
     stat.setTiming(this.endTime);
    return stat;
   }
   
   public void ChooseMethod(String method)
   {}
public int[] getSolution(){return this.solution;}
public long getExecutionTime(){return this.totalTime;}

}

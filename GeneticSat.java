/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swarmproject;

import java.util.ArrayList;
import java.lang.Math;
import java.util.Arrays;
/**
 *
 * @author Rayan
 */
public class GeneticSat extends Sat{
        public String path;
    private FileCnf cnf;
    private long startTime,endTime,totalTime;
    private long RateMutation=50, RateCroisement=50;
   
    public GeneticSat(String path)
    {
        this.cnf = new FileCnf(path);
    }
    public GeneticSat(){}
    @Override
    public void ChoosePath(String path)
    {
        this.cnf = new FileCnf(path);
    }
    
    public ArrayList<Integer> fitness(ArrayList<int[]> solutions)
    {
        ArrayList<Integer> results = new ArrayList<Integer>();
        int i=0;
        while(i<solutions.size())
        {         
            results.add(this.cnf.ValidateSolution(solutions.get(i)));
            i++;
        }
        return results;
    }
    
    public ArrayList<int[]> selection(ArrayList<int[]> solutions, ArrayList<Integer> results, int number)
    {
    ArrayList<int[]> newGen = new ArrayList<int[]>();
    int max=0,maxi=-1;
    for(int i=0;i<number;i++)
    {
        max=0;maxi=-1;
        for(int j=0;j<results.size();j++)
        {
            if(results.get(j)>max)
            {
                maxi=j;
                max=results.get(j);
            }
        }
        if(maxi!=-1)
        {
        results.remove(maxi);
        newGen.add(solutions.remove(maxi));
        }   
    }
    return newGen;
    }
    
    public ArrayList<int[]> generate(int sizeOfPopulation)
    {
        int max=1,min=-1;
    ArrayList<int[]> randomGen = new ArrayList<int[]>();
    int i=0;
    for(i=0;i<sizeOfPopulation;i++)
    {
    int[] sol = new int[21];
    int rand;
    for(int j=0;j<21;j++)
    {
        rand = (int)(Math.random() * ((max - min) + 1)) + min;
        if(rand<0){sol[j]= -1;}
        else{    sol[j]=1 ;}
    }
    randomGen.add(sol);
    }
    return randomGen;
    }
    
    @Override
       public void startTime(){this.startTime = System.nanoTime();}
  @Override
       public void endTime()
   {
       this.endTime = System.nanoTime();this.totalTime=this.endTime-this.startTime;
       System.out.println("Start : "+this.startTime+" | End : "+this.endTime+" | Total :"+this.totalTime);
   }
       public void croisement(ArrayList<int[]> population)
       {
           int lower=1, upper=20;
       int rand = (int) (Math.random() * (upper - lower)) + lower;
       int[] solution = new int[21];
       for(int i=0;i<rand;i++)
       {
       solution[i]=population.get(0)[i];
       }
       
       for(int i=rand;i<21;i++)
       {
       solution[i]=population.get(1)[i];
       }
       population.add(solution);
       }
       
       
       public void mutation(ArrayList<int[]> population)
       {
           int lower=1, upper=20;
       int rand = (int) (Math.random() * (upper - lower)) + lower;
       if(population.get(0)[rand]==-1){population.get(0)[rand]=1;}
       else{population.get(0)[rand]=0;}
       }
        
    @Override
    public void ChooseMethod(String method)
    {
        return;
    }
    
   public Statistic CreateSolution(int timing)
   {
     return this.Gen(timing);
   }
    
    
     public Statistic Gen(int timing)
     {
     Statistic stat = new Statistic();
     this.startTime();
     boolean NotStagn = true;
     boolean Found = false;
     int i=0;
     int lower = 10;
     int upper = 100;
     
     ArrayList<int[]> population = this.generate(2);
     ArrayList<Integer> results = new ArrayList<Integer>();
     
     int best= 0;
     long check=0;
     while(!Found && ((timing!=0 && check<timing) || timing==0))
     {
         int rand = (int) (Math.random() * (upper - lower)) + lower;
        
         if(rand<this.RateCroisement){this.croisement(population);}
         rand = (int) (Math.random() * (upper - lower)) + lower;
         
         if(rand<this.RateMutation){this.mutation(population);}
         results = this.fitness(population);
         for(int d=0;d<results.size();d++)
         {
         if(results.get(d)==this.cnf.getNbrClauses()){Found=true;}
         if(results.get(d)>best){best= results.get(d);}
         }
         population = this.selection(population, results, 2);
         i++;
         check=  (System.nanoTime() - this.startTime) / 100000000;
     }
     
     this.endTime();
     System.out.println("CHECK : "+check);
     stat.setNbrClauses(best);
     stat.setTiming(check);
    return stat;
     }
}

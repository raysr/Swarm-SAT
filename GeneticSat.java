/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swarmproject;

import java.util.ArrayList;
import java.lang.Math;
import java.util.Arrays;
import java.util.HashMap;
/**
 *
 * @author Rayan
 */
public class GeneticSat extends Sat{
        public String path;
    private FileCnf cnf;
    private long startTime,endTime,totalTime;
    private long RateMutation=10, RateCroisement=10;
   private int MaxIter = 100;
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
       public ArrayList<int[]> croisement(ArrayList<int[]> population)
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
       return population;
       }
       
       
       public ArrayList<int[]> mutation(ArrayList<int[]> population)
       {
           int lower=1, upper=20;
       int rand = (int) (Math.random() * (upper - lower)) + lower;
       if(population.get(0)[rand]==-1){population.get(0)[rand]=1;}
       else{population.get(0)[rand]=0;}
       return population;
       }
        
    @Override
    public void ChooseMethod(String method)
    {
        return;
    }
    
   public Statistic CreateSolution(HashMap<String, Integer> parameters)
   {
      int timing = parameters.get("timing");
      this.RateCroisement = parameters.get("taux_croisement");
      this.RateMutation = parameters.get("taux_mutation");
      this.MaxIter = parameters.get("max_iteration");
     return this.Gen(timing);
   }
    
    
     public Statistic Gen(int timing)
     {
         System.out.println("Taux de Croisement : "+this.RateCroisement);
         System.out.println("Taux de Mutation : "+this.RateMutation);
         System.out.println("MaxIter : "+this.MaxIter);
     Statistic stat = new Statistic();
     this.startTime();
     boolean NotStagn = true;
     boolean Found = false;
     int i=0;
     int lower = 10;
     int upper = 100;
     int meanStagn = 0;
     ArrayList<int[]> population = this.generate(2); // Generation de 2 individus
     ArrayList<Integer> results = new ArrayList<Integer>();

     int countStagn = 0; // Variable pour compter le nombre d'iteration ou il y'a eu stagnation
     
     int best= 0; // Pour garder en mémoire le meilleur resultat obtenu
     long check=0;
     int countIter = 0;
     while(!Found && ((timing!=0 && check<timing) || timing==0) && (this.MaxIter==0 || (this.MaxIter!=0 && countIter<this.MaxIter)))
     {
         
         if(countStagn>4) // Il y'a stagnation depuis plusieurs iterations
         {
            ArrayList<int[]> individus = this.generate(1);
            individus.add(population.get(0));
            individus = this.croisement(individus);
            individus.add(population.get(1));
            ArrayList<Integer> resultsNew = new ArrayList<Integer>();
            resultsNew = this.fitness(individus);
            population = this.selection(individus, resultsNew, 2);
         }
         // Générer un nombre aléatoire pour appliquer ou non un croisement
         int rand = (int) (Math.random() * (upper - lower)) + lower;
         if(rand<this.RateCroisement){population = this.croisement(population);}
         
         // Générer un nombre aléatoire pour appliquer ou non une mutation
         rand = (int) (Math.random() * (upper - lower)) + lower;
         if(rand<this.RateMutation){population = this.mutation(population);}
         
         
         results = this.fitness(population);
         for(int d=0;d<results.size();d++)
         {
         if(results.get(d)==this.cnf.getNbrClauses()){Found=true;}
         if(results.get(d)>best){best= results.get(d);}
         }
         
       
         
         
         // Calcul de stagnation
         int oldStagn = meanStagn; // on sauvegarde l'ancienne moyenne de nombre de clauses satisfaites
         meanStagn=0;
         int j;
         for(j=0;j<results.size();j++)
         {
         meanStagn+=results.get(j);     // On calcule la nouvelle moyenne 
         }
         meanStagn=meanStagn/results.size();
         /*
           Si la difference des moyennes de clauses satisfaites entre les deux iterations dépasse un
          certain seuil on incrémente le compteur d'iteration de stagnation, SINON on le remet à 0
          */
         if(oldStagn!=0 && Math.abs((double)oldStagn-meanStagn)>4){countStagn+=1;} 
         else{countStagn=0;}
         
        // Effectuer une selection des 2 meilleures individus de la population actuelle
         population = this.selection(population, results, 2);
         check =  (System.nanoTime() - this.startTime) / 10000; // Variable pour controler le temps
     countIter++;
     }
     
     this.endTime();
     System.out.println("CHECK : "+check);
     stat.setNbrClauses(best);
     stat.setTiming(check);
    return stat;
     }
}

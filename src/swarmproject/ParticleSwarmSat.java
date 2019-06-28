/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swarmproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.math.BigDecimal;
/**
 *
 * @author Rayan
 */
public class ParticleSwarmSat extends Sat{
            public String path;
    private FileCnf cnf;
    private long startTime,endTime,totalTime;
    private double C1=1,C2=1,W=1;
    private double sizepop=20;
  
       public void setParameters(int[] param)
   {
   this.sizepop=param[0];
   this.C1=param[1];
   this.C2=param[2];
   this.W=param[3];
   }
    
    
   public ParticleSwarmSat(String path)
    {
        this.cnf = new FileCnf(path);
    }
    public ParticleSwarmSat(){}
    @Override
    public void ChoosePath(String path)
    {
        this.cnf = new FileCnf(path);
    }

  @Override
       public void startTime(){this.startTime = System.nanoTime();}
  @Override
       public void endTime()
   {
       this.endTime = System.nanoTime();this.totalTime=this.endTime-this.startTime;

   }

        
    @Override
    public void ChooseMethod(String method)
    {
        return;
    }
    
   public Statistic CreateSolution(HashMap<String, Double> parameters, int instance)
   {
     int timing = (int)Math.round(parameters.get("timing"));
     int number_particles = (int)Math.round(parameters.get("number_particles"));
     this.C1 = parameters.get("C1");
     this.C2 = parameters.get("C2");
     this.W = parameters.get("W");
   //  System.out.println(" PSO | C1 : "+this.C1+" C2 : "+this.C2+" W :"+this.W);
     return this.PSO(timing, number_particles, instance);
   }
   public HashMap<String, ArrayList<Statistic>> CreateSolutionParameter(int instance,int timing)
   {
       HashMap<String, ArrayList<Statistic>> results = new HashMap<String, ArrayList<Statistic>>();
       ArrayList<Statistic> stats = new ArrayList<Statistic>();
       
    
       
       int number_particles=20;
   
     for(int i=1;i<=100;i++)
     {
         number_particles=i;
         stats.add(this.PSO(timing, number_particles, instance));
     }
     results.put("Nombre de particules", stats);
     stats = new ArrayList<Statistic>();
     for(int i=1;i<=100;i++)
     {
         this.C1=((double)i)/100;
         stats.add(this.PSO(timing, number_particles, instance));
     }
     results.put("C1", stats);
     stats = new ArrayList<Statistic>();
     for(int i=1;i<=100;i++)
     {
         this.C2=((double)i)/100;
         stats.add(this.PSO(timing, number_particles, instance));
     }
     results.put("C2", stats);
     stats = new ArrayList<Statistic>();
      for(int i=1;i<=100;i++)
     {
         this.W=((double)i)/100;
         stats.add(this.PSO(timing, number_particles, instance));
     }
      results.put("Inertie", stats);
      stats = new ArrayList<Statistic>();
     return results ;
   }
    
    
     public Statistic PSO(int timing, int number_particles, int instance)
     {
         number_particles=(int)this.sizepop;
         
     Statistic stat = new Statistic();
     this.startTime();
     boolean Found = false;
     int i=0;
     
     ArrayList<Particle> population = new ArrayList<Particle>();
     
     int globalBestScore = 0;
     long check=0;
     int evaluation = 0;
     int[] globalBestSolution = new int[this.cnf.getNbrVars()];
    
     for(i=0;i<number_particles;i++) 
    {
        Particle part = new Particle(this.cnf.getNbrVars(),this.C1, this.C2, this.W);
        evaluation = this.cnf.ValidateSolution(part.getSolution()); 
        part.UpdateScore(evaluation);
        if(evaluation>globalBestScore)
        {
            globalBestScore=evaluation;
            globalBestSolution=part.getSolution();
        }
        population.add(part);
    }

     while(!Found && ((timing!=0 && check<timing) || timing==0) )
     {
         for(i=0;i<population.size();i++) 
        {
         
         // Générer un nombre aléatoire pour appliquer ou non un croisement
        
         
         population.get(i).UpdateVelocity(globalBestSolution);
         population.get(i).Converge();
         evaluation = this.cnf.ValidateSolution(population.get(i).getSolution()); 
         population.get(i).UpdateScore(evaluation);
        
         if(evaluation>globalBestScore)
           {
                globalBestScore=evaluation; 
                globalBestSolution=population.get(i).getSolution();
           }
        
        }
         
        check =  (System.nanoTime() - this.startTime) / 10000; // Variable pour controler le temps
     }

     this.endTime();

     stat.setNbrClauses(globalBestScore);
     stat.setTiming(check);
     stat.number_particles = number_particles ;
     
     stat.C1 = this.C1*100;
     stat.C2 = this.C2*100;
     stat.W = this.W*100;
     stat.algorithm="PSO";
     stat.instance=instance;
      return stat;
     }
}

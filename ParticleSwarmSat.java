/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swarmproject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Rayan
 */
public class ParticleSwarmSat extends Sat{
            public String path;
    private FileCnf cnf;
    private long startTime,endTime,totalTime;
    private double C1=1,C2=1,W=1;

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
       System.out.println("Start : "+this.startTime+" | End : "+this.endTime+" | Total :"+this.totalTime);
   }

        
    @Override
    public void ChooseMethod(String method)
    {
        return;
    }
    
   public Statistic CreateSolution(HashMap<String, Integer> parameters)
   {
     int timing = parameters.get("timing");
     int number_particles = parameters.get("number_particles");
     this.C1 = parameters.get("C1")/100;
     this.C2 = parameters.get("C2")/100;
     this.W = parameters.get("W")/100;
     return this.PSO(timing, number_particles);
   }
    
    
     public Statistic PSO(int timing, int number_particles)
     {
        System.out.println("NOMBRE DE VARIABLES : "+this.cnf.getNbrVars()); 
         
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
     System.out.println("CHECK : "+check);
     stat.setNbrClauses(globalBestScore);
     stat.setTiming(check);
      return stat;
     }
}

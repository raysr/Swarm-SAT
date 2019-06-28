/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swarmproject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
/**
 *
 * @author Rayan
 */
public class ParametersOptimization {
    private long RateMutation=10, RateCroisement=30;
   private int MaxIter = 100;
   private int sizePop = 20;
   public Controller c = new Controller();
    public String algorithm="";
      
    private double best_score = 0;
    private double[] vector = new double[4];
    
    
    public ParametersOptimization(String algorithm)
    {
       this.c.ChooseMethod(algorithm);
       this.algorithm=algorithm;
       System.out.println("Active Algorithm :"+this.c.getActivatedMethod());
    }
    
    public ArrayList<double[]> Mutation(ArrayList<double[]> pop)
    {
        // System.out.println("Mutation");
    Random rand = new Random();
        int min=1,max=pop.size()-1;
         int randomFellow = rand.nextInt((max - min) + 1) + min;
         int randomGen = rand.nextInt((3 - 0) + 1) + 0;
         min = 2; max=100;
         double randomValue=1;
         
          if(this.algorithm.equals("PSO")){
              switch (randomGen){
                      case 0:
                min=2;max=100; // Croisement
         randomValue = min + (max - min) * rand.nextDouble();
                      case 1:
         min=2;max=100; // Mutation
        randomValue = min + (max - min) * rand.nextDouble();
                      case 2:
        min=2;max=100; // MaxIter
        randomValue = min + (max - min) * rand.nextDouble();
                      default:
        min=2;max=100; // Taille population
        randomValue = min + (max - min) * rand.nextDouble();
              }
            }
            else{
                    switch(randomGen){
                        case 0:
            min=2;max=100; // Particules
            randomValue = min + (max - min) * rand.nextDouble();
                        case 1:
             min=0;max=2; // C1
        randomValue = min + (max - min) * rand.nextDouble();
                        case 2:
         min=0;max=2; // C2
        randomValue = min + (max - min) * rand.nextDouble();
                        default:
         min=0;max=2; // W
        randomValue = min + (max - min) * rand.nextDouble();
                    }
            }
         
         
         
         pop.get(randomFellow)[randomGen]=randomValue;
         return pop;
    }
    
    public ArrayList<double[]> Croisement(ArrayList<double[]> pop)
    {
       // System.out.println("Croisement");
    Random rand = new Random();
        int min=0,max=pop.size()-1;
        double[] individu = new double[4];
         int fellow1 = rand.nextInt((max - min) + 1) + min;
        int fellow2 = rand.nextInt((max - min) + 1) + min;
        min=0;max=100;
        for(int i=0;i<4;i++)
        {
        double r = min + (max - min) * rand.nextDouble();
        if(r>50){individu[i]=pop.get(fellow1)[i];}
        else{individu[i]=pop.get(fellow2)[i];}
        
        }
        pop.add(individu);
        return pop;
    }
    
    public ArrayList<double[]> Selection(ArrayList<double[]> pop) 
   {
     //  System.out.println("Selection");
   ArrayList<StatisticParam> sp = new ArrayList<StatisticParam>();
   ArrayList<double []> results = new ArrayList<double[]>();
   HashMap<String, Double> parameters = new HashMap<String, Double>();
   for(int i=0;i<pop.size();i++)
   {
       parameters.put("timing", 5000.0);
       if(this.algorithm.equals("PSO")){
       parameters.put("number_particles",pop.get(i)[0]);
      parameters.put("C1", pop.get(i)[1]);
      parameters.put("C2", pop.get(i)[2]);
      parameters.put("W", pop.get(i)[3]);

       }
       else{
            
      parameters.put("taux_croisement",pop.get(i)[0]);
      parameters.put("taux_mutation", pop.get(i)[1]);
      parameters.put("max_iteration", pop.get(i)[2]);
      parameters.put("size_population", pop.get(i)[3]);
       }
       sp.add(this.c.FolderTest("datasets/uf75-325-100/", parameters));
   }
   int count = 0;
   while(count<this.sizePop+1)
   {
       
   double max_value=-1;
   int max_index=-1;
   for(int j=0;j<pop.size();j++)
   {
       if(sp.get(j).moyenneNbrClauses>max_value){max_value=sp.get(j).moyenneNbrClauses;max_index=j;}
       
       if(sp.get(j).moyenneNbrClauses>this.best_score){this.best_score=sp.get(j).moyenneNbrClauses;this.vector=pop.get(j).clone();}
   }
   if(count==0){System.out.println("Best generation value : "+max_value);}
  
   results.add(pop.remove(max_index));
    sp.remove(max_index);
   count++;
   }
   
   return results;
   }
    
    public ArrayList<double[]> Generation(int size)
    {
        ArrayList<double[]> pop = new ArrayList<double[]>();
        Random rand = new Random();
        int min=2,max=100;
        for(int i=0;i<size;i++){
            double[] individu = new double[4];
            if(this.algorithm.equals("PSO")){
                
                
                 min=2;max=100; // Particules
            individu[0] = min + (max - min) * rand.nextDouble();
             min=0;max=2; // C1
        individu[1] = min + (max - min) * rand.nextDouble();
         min=0;max=2; // C2
        individu[2] = min + (max - min) * rand.nextDouble();
         min=0;max=2; // W
        individu[3] = min + (max - min) * rand.nextDouble();
        
                
            }
            else{
           min=20;max=40; // Croisement
         individu[0] = min + (max - min) * rand.nextDouble();
         min=5;max=20; // Mutation
        individu[1] = min + (max - min) * rand.nextDouble();
        min=100;max=1000; // MaxIter
        individu[2] = min + (max - min) * rand.nextDouble();
        min=10;max=30; // Taille population
        individu[3] = min + (max - min) * rand.nextDouble();
            
            }
        pop.add(individu);
        }
         return pop;
    }
    public double[] Gen(){
     
    int i=0;
    ArrayList<double[]> population = this.Generation(20);
    int count_stagnation = 0;
    double last_score=0;
        while(i<this.MaxIter)
        {
            System.out.println("Iteration "+i);
            for(int k=0;k<this.RateCroisement;k++){population=this.Croisement(population);}
            for(int j=0;j<this.RateMutation;j++){population=this.Mutation(population);}
            population=this.Selection(population); 
            
            
            // INCREMENTATION DE STAGNATION
            if(this.best_score == last_score){count_stagnation++;}
            else{last_score = this.best_score;}
            
            if(count_stagnation==5) // CAS DE STAGNATION
            {
                count_stagnation=0;
            ArrayList<double[]> nouveaux = this.Generation(10);
            population.addAll(nouveaux);
            for(int k=0;k<this.RateCroisement;k++){population=this.Croisement(population);}
            population=this.Selection(population); 
            }
            i++;
        }
    System.out.println("Meilleur résultat : "+this.best_score);
    return this.vector;
    }
    
    
}

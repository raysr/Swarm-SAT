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
    private long RateMutation=10, RateCroisement=10;
   private int MaxIter = 100;
   private int sizePop = 20;
   public Controller c = new Controller();
    public String algorithm="";
      
    private double best_score = 0;
    private int[] vector = new int[4];
    
    
    public ParametersOptimization(String algorithm)
    {
       this.c.ChooseMethod(algorithm);
       System.out.println("Active Algorithm :"+this.c.getActivatedMethod());
    }
    
    public ArrayList<int[]> Mutation(ArrayList<int[]> pop)
    {
        // System.out.println("Mutation");
    Random rand = new Random();
        int min=1,max=pop.size()-1;
         int randomFellow = rand.nextInt((max - min) + 1) + min;
         int randomGen = rand.nextInt((3 - 0) + 1) + 0;
         int randomValue = rand.nextInt((100 - 0) + 1) + 0;
         pop.get(randomFellow)[randomGen]=randomValue;
         return pop;
    }
    
    public ArrayList<int[]> Croisement(ArrayList<int[]> pop)
    {
       // System.out.println("Croisement");
    Random rand = new Random();
        int min=0,max=pop.size()-1;
        int[] individu = new int[4];
         int fellow1 = rand.nextInt((max - min) + 1) + min;
        int fellow2 = rand.nextInt((max - min) + 1) + min;
        min=0;max=100;
        for(int i=0;i<4;i++)
        {
        int r = rand.nextInt((max - min) + 1) + min;
        if(r>50){individu[i]=pop.get(fellow1)[i];}
        else{individu[i]=pop.get(fellow2)[i];}
        
        }
        pop.add(individu);
        return pop;
    }
    
    public ArrayList<int[]> Selection(ArrayList<int[]> pop) 
   {
     //  System.out.println("Selection");
   ArrayList<StatisticParam> sp = new ArrayList<StatisticParam>();
   ArrayList<int []> results = new ArrayList<int[]>();
   HashMap<String, Integer> parameters = new HashMap<String, Integer>();
   for(int i=0;i<pop.size();i++)
   {
       // 2 - 100
       parameters.put("timing", 500);
      parameters.put("taux_croisement",pop.get(i)[0]/2);
      parameters.put("taux_mutation", pop.get(i)[1]/2);
      parameters.put("max_iteration", pop.get(i)[2]*200);
      parameters.put("size_population", pop.get(i)[3]*2);
      
      parameters.put("number_particles",pop.get(i)[0]*2);
      parameters.put("C1", pop.get(i)[1]/10);
      parameters.put("C2", pop.get(i)[2]/10);
      parameters.put("W", pop.get(i)[3]/10);
      
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
    
    public ArrayList<int[]> Generation(int size)
    {
        ArrayList<int[]> pop = new ArrayList<int[]>();
        Random rand = new Random();
        int min=2,max=100;
        for(int i=0;i<size;i++){
            int[] individu = new int[4];
         individu[0] = rand.nextInt((max - min) + 1) + min;
        individu[1] = rand.nextInt((max - min) + 1) + min;
        individu[2] = rand.nextInt((max - min) + 1) + min;
        individu[3] = rand.nextInt((max - min) + 1) + min;
        pop.add(individu);
        }
         return pop;
    }
    public int[] Gen(){
     
    int i=0;
    ArrayList<int[]> population = this.Generation(20);
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
            ArrayList<int[]> nouveaux = this.Generation(10);
            population.addAll(nouveaux);
            for(int k=0;k<this.RateCroisement;k++){population=this.Croisement(population);}
            population=this.Selection(population); 
            }
            i++;
        }
    System.out.println("Meilleur résultat : "+this.best_score);
    return population.get(0);
    }
    
    
}

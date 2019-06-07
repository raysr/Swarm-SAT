/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swarmproject;
import java.util.ArrayList;


/**
 *
 * @author Rayan
 */
public class SatAStar extends Sat{
    public String path;
    private String heuristic;
    private FileCnf cnf;
    private long startTime,endTime,totalTime;
   
    public SatAStar(String path)
    {
        this.cnf = new FileCnf(path);
    }
    public SatAStar(){}
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
    if(method.equals("Bohm")){this.heuristic="Bohm"; System.out.println("Bohm Choisi");}
    if(method.equals("Moms")){this.heuristic="Moms";System.out.println("Moms Choisi");}
    if(method.equals("Jeroslow")){this.heuristic="Jeroslow";System.out.println("Jeroslow Choisi");}
    if(method.equals("A* Par clauses non-satisfaites")){this.heuristic="A* Par clauses non-satisfaites";System.out.println("Simple Choisi");}
    }
    
    @Override
    public Statistic CreateSolution(int timing)
    {   
     int nbvar = 21;
    return this.AStar(nbvar, timing);
    }
    public int g(int[] sol){return this.cnf.getNbrClauses()-this.cnf.ValidateSolution(sol);}
    public int f(int[] sol, int var)
     {
        int result =  this.g(sol)+this.h(sol, var);
        System.out.println("F = "+result);
        return result;
     }
 
    public int h(int[] solution, int x)
    {
        if(this.heuristic.equals("Bohm"))
        {return this.Bohm(solution, x);}
        else if(this.heuristic.equals("Moms"))
        {return this.Moms(solution, x);}
        else if(this.heuristic.equals("Jeroslow"))
        {return this.Jeroslow(solution, x);}
        else if(this.heuristic.equals("A* Par clauses non-satisfaites"))
        {return this.cnf.getNbrClauses()-this.cnf.ValidateSolution(solution);}
        else return 0;
    }
    
    public int h(int[] solution){return this.cnf.getNbrClauses()-this.cnf.ValidateSolution(solution);}
    
    
    // BOHM HEURISTIC
    public int Bohm(int[] solution, int x) // Bohm’s Heuristic 1992
    { 
    int alpha=1, beta=2;
    int H =alpha*(Math.max(h(x, true, solution),h(x, false, solution))) +beta*Math.min(h(x, true, solution),h(x, false, solution));
    return H;
    }    

    public int h(int indice, boolean value, int[] solution) // the number of not yet satisfied clauses with i literals that contain the literal x.
    {
        return this.cnf.ValidateSolutionBohm(solution, indice, value);
    }   
    
    // MOMS HEURISTIC
    public int Moms(int[] solution, int x) //Popular in the mid 90s
    {
    int k=1;
    int S = (fmoms(x, true, solution)+fmoms(x, true, solution))*2*k+(fmoms(x, true, solution)*fmoms(x, false, solution));
    return S;
    }    

    public int fmoms(int indice, boolean value, int[] solution) // the number of not yet satisfied clauses with i literals that contain the literal x.
    {
        return this.cnf.ValidateSolutionBohm(solution, indice, value);
    }   
    
    
    // Jeroslow-Wang Heuristic (Better than Bohm and Moms)
    public int Jeroslow(int[] solution, int x) //Popular in the mid 90s
    {
        int S=0;
        int nbr=this.cnf.nbrClauses(x, true);
        nbr+=this.cnf.nbrClauses(x, false);
        for(int i=0;i<nbr;i++)
        {
            S+= Math.pow(2,-3);
        }
        return S;
    }    
    
     public Statistic AStar(int size, int timing)
     {
      Statistic stat = new Statistic();
     OpenList open = new OpenList();
     ClosedList closed = new ClosedList();
     int result;
     int nbrClauses = this.cnf.getNbrClauses();
     int i;
     int[] solution = new int[size];
     boolean found=false;
     long check = 0;
     int best = 0;
     this.startTime();
     
     open.add(new Node(solution, 0, 1));
     while(!found && !open.isEmpty() && ((timing!=0 && check<timing) || timing==0))
     {
        Node n = open.remove();
        ArrayList<Node> successeurs = n.getChilds();
        i=0;
        while(i<successeurs.size() && !found)
        {
            n = successeurs.get(i);
            result = this.cnf.ValidateSolution(n.solution);
            if(result==nbrClauses)
            {
                found = true;
            }
            if(result>best){best=result;}
            n.h = nbrClauses-result;
            n.f=n.h+n.g;
            open.add(n);
            i++;
        }
        check = (System.nanoTime() - this.startTime)/ 100000000;
                
     }
     this.endTime();
     System.out.println("CHECK : "+check);
     stat.setNbrClauses(best);
     stat.setTiming(check);
     return stat;
     }
 
}

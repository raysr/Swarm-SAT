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
    public void ChoosePath(String path)
    {
        this.cnf = new FileCnf(path);
    }

        
    @Override
    public void ChooseMethod(String method)
    {
    if(method.equals("Bohm")){this.heuristic="Bohm"; System.out.println("Bohm Choisi");}
    if(method.equals("Moms")){this.heuristic="Moms";System.out.println("Moms Choisi");}
    if(method.equals("Jeroslow")){this.heuristic="Jeroslow";System.out.println("Jeroslow Choisi");}
    if(method.equals("Simple")){this.heuristic="Simple";System.out.println("Simple Choisi");}
    }
    
    @Override
    public long CreateSolution()
    {   
     this.startTime();
     int nbvar = 21;
     this.AStar(nbvar);
    return this.totalTime;
    }
    public int g(int[] sol){return this.cnf.nbrNonValid(sol);}
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
        else if(this.heuristic.equals("Simple"))
        {return this.cnf.nbrNonValid(solution);}
        else return 0;
    }
    
    public int h(int[] solution){return this.cnf.nbrNonValid(solution);}
    
    
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
    
   
    
    
     public void AStar(int size)
     {
     System.out.println("File : "+this.cnf.path);
     this.startTime();
     OpenList open = new OpenList();
     ClosedList closed = new ClosedList();
     int[] solution = new int[size];
     open.add(new Node(solution, 0, 1));
     boolean found=false;
     while(!found && open.size()!=0)
     {
        Node n = open.remove();
        closed.add(n);
//        System.out.println(" Best :  "+n.f);
        int validation = this.cnf.ValidateSolution(n.solution);
        found = (this.cnf.getNbrClauses()==validation);
        open.DeployChilds(n.getChilds(), closed, new Command(){ public int execute(int[] sol){ return h(sol); }});
     }
     System.out.println("\n\n \t FOUND \n\n \t");
     this.endTime();
     }
 
}

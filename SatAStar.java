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

    private int[] solution; 
    private boolean found=false;
    private String method;
    private FileCnf cnf;
    private int best=0;
    
    private long startTime,endTime,totalTime;
   
   
   
   public void startTime(){this.startTime = System.nanoTime();}
   public void endTime()
   {
       this.endTime = System.nanoTime();this.totalTime=this.endTime-this.startTime;
       System.out.println("Start : "+this.startTime+" | End : "+this.endTime+" | Total :"+this.totalTime);
   }
    public SatAStar()
    {
    }

    public int getLowest(ArrayList<Node> nodes) // Retourne l'indice dans la liste du plus bas F cost
    {
        int min=9999999;
        int indice=9999999;
        for(int i=0;i<nodes.size();i++)
        {
            if(nodes.get(i).f<min)
            {
                min=nodes.get(i).f;
                indice=i;
            }
        }
        return indice;
    }
    
    
    
    public SatAStar(String path)
    {
        this.cnf = new FileCnf(path);
    }
    
    
    @Override
    public void ChooseMethod(String method)
    {
    if(method.equals("Largeur")){this.method="Largeur"; System.out.println("Largeur Choisi");}
    if(method.equals("Profondeur")){this.method="Profondeur";System.out.println("Profondeur Choisi");}
    }
    @Override
    public long CreateSolution()
    {   
     this.startTime();
     int nbvar = 21;
     this.AStar(nbvar);
    return this.totalTime;
    }
       public void ChoosePath(String path)
    {
        this.cnf = new FileCnf(path);
    }
       
     public boolean isInClosed(Node nod, ArrayList<Node> closed)
     {
     for(int i=0;i<closed.size();i++)
     {
         if(closed.get(i).solution==nod.solution) return true;
     }
     return false;
     }
     
     public void addToOpen(ArrayList<Node> following, ArrayList<Node> open, ArrayList<Node> closed) // Ajouter à Closed sans redondance
     {
     for(int i=0;i<following.size();i++)
     {
        if(!(isInClosed(following.get(i), closed)))
        {
            boolean test=false;
            for(int j=0;j<open.size();j++)
            {
                if(open.get(j).solution==following.get(i).solution)
                {
                    open.get(j).f= (open.get(j).f<following.get(i).f)?following.get(i).f:open.get(j).f;
                    test=true;
                }
            }
            if(!test)
            {
                open.add(following.get(i));
            }
        }
     }
     }
    public int Heuristic(int[] solution)
    {
    return 0;
    }
       
     public void AStar(int size)
     {
      this.startTime();
     ArrayList<Node> open = new ArrayList<Node>();
     ArrayList<Node> closed = new ArrayList<Node>();
     int[] solution = new int[size];
     open.add(new Node(solution, 0+Heuristic(solution), 1));
     boolean found=false;
     while(!found && open.size()!=0)
     {
        int j = this.getLowest(open);
        Node n = open.get(j);
        open.remove(j);
        closed.add(n);
        found = (this.cnf.getNbrClauses()==this.cnf.ValidateSolution(n.solution));
        this.addToOpen(n.getFollowing(), open, closed);
     }
     this.endTime();
     }
 
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swarmproject;
import java.util.Queue; 
import java.util.Stack;
import java.util.LinkedList;
import java.util.ArrayList;

/**
 *
 * @author Rayan
 */
public class SatAveugle extends Sat{
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
    public SatAveugle()
    {
    }

    public SatAveugle(String path)
    {
        this.cnf = new FileCnf(path);
    }
    @Override
    public void ChooseMethod(String method)
    {
    if(method.equals("Largeur")){this.method="Largeur"; }
    if(method.equals("Profondeur")){this.method="Profondeur";}
    }
    @Override
    public long CreateSolution()
    {
        
        this.startTime();
     int nbvar = 21;
    if(this.method.equals("Largeur"))Largeur(nbvar);
    if(this.method.equals("Profondeur"))Profondeur(nbvar);
    return this.totalTime;
    }
       public void ChoosePath(String path)
    {
        this.cnf = new FileCnf(path);
    }
    
       
    public void Profondeur(int nbvar){
    Stack<Node> Ouverte = new Stack<Node>();
    Queue<Node> Ferme = new LinkedList<Node>();
    int[] solution = new int[nbvar+1];
    int last=1;
    int i;
    Node n1 = new Node(solution, 0 , last);
    boolean found = false;
    this.startTime();
    Ouverte.add(n1);
    while( !Ouverte.isEmpty() && found == false)
    {
    Node n = Ouverte.pop();
    ArrayList<Node> successeurs = n.getChilds();
    i=0;
        while(i<successeurs.size() && !found)
    {
        if(this.cnf.ValidateSolution(successeurs.get(i).solution)==this.cnf.getNbrClauses())
        {
        found = true;
        }
        Ouverte.add(successeurs.get(i));
        i++;
    }
    }
    this.endTime();
    }
    
    
    public void Largeur(int nbvar){
        int max=0;
    Queue<Node> Ouverte = new LinkedList<Node>();
    Queue<Node> Ferme = new LinkedList<Node>();
    int i;
    int[] solution = new int[nbvar+1];
    int last=1;
    Node n1 = new Node(solution, 0 , last);
    this.startTime();
    Ouverte.add(n1);
    boolean found = false;
    while( !Ouverte.isEmpty() && found == false)
    {
        
    Node n = Ouverte.poll();
    ArrayList<Node> successeurs = n.getChilds();
    i=0;
    while(i<successeurs.size())
    {
        if(this.cnf.ValidateSolution(successeurs.get(i).solution)==this.cnf.getNbrClauses())
        {
        found = true;
        }
        Ouverte.add(successeurs.get(i));
        i++;
    }
    }
    this.endTime();
    }
       
}

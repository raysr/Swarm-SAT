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
import java.util.HashMap;

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
    public Statistic CreateSolution(HashMap<String, Double> parameters, int instance)
    {
        int timing = (int)Math.round(parameters.get("timing"));
        this.startTime();
     int nbvar = this.cnf.getNbrVars();
    if(this.method.equals("Largeur"))return this.Largeur(nbvar, timing, instance);
    else{return Profondeur(nbvar, timing, instance);}
   
    }
       public void ChoosePath(String path)
    {
        this.cnf = new FileCnf(path);
    }
    
       
    public Statistic Profondeur(int nbvar, int timing, int instance){
        Statistic stat = new Statistic();
        int best = 0;
        long check = 0;
    Stack<Node> Ouverte = new Stack<Node>();
    Queue<Node> Ferme = new LinkedList<Node>();
    int[] solution = new int[nbvar+1];
    int last=1;
    int i;
    Node n1 = new Node(solution, 0 , last);
    boolean found = false;
    this.startTime();
    Ouverte.add(n1);
    while( !Ouverte.isEmpty() && found == false && ((timing!=0 && check<timing) || timing==0))
    {
    Node n = Ouverte.pop();
    ArrayList<Node> successeurs = n.getChilds();
    i=0;
        while(i<successeurs.size() && !found)
    {
        int tryit= this.cnf.ValidateSolution(successeurs.get(i).solution);
        if(tryit>best){best=tryit;}
        if(tryit==this.cnf.getNbrClauses())
        {
        found = true;
        }
        Ouverte.add(successeurs.get(i));
        i++;
    }
        check = (System.nanoTime() - this.startTime)/ 10000;
    }
    this.endTime();
    stat.setNbrClauses(best);
    stat.instance=instance;
     stat.setTiming(check);
    
    return stat;
    }
    
    
    public Statistic Largeur(int nbvar, int timing, int instance){
        int max=0;
        Statistic stat = new Statistic();
        int best = 0;
        long check = 0;
    Queue<Node> Ouverte = new LinkedList<Node>();
    Queue<Node> Ferme = new LinkedList<Node>();
    int i;
    int[] solution = new int[nbvar+1];
    int last=1;
    Node n1 = new Node(solution, 0 , last);
    this.startTime();
    Ouverte.add(n1);
    boolean found = false;
    while( !Ouverte.isEmpty() && found == false && ((timing!=0 && check<timing) || timing==0))
    {
        
    Node n = Ouverte.poll();
    ArrayList<Node> successeurs = n.getChilds();
    i=0;
    while(i<successeurs.size())
    {
        int tryit = this.cnf.ValidateSolution(successeurs.get(i).solution);
        if(tryit>best){best=tryit;}
        if(tryit==this.cnf.getNbrClauses())
        {
        found = true;
        }
        Ouverte.add(successeurs.get(i));
        i++;
    }
    check = (System.nanoTime()-this.startTime)/10000;
    }
    this.endTime();
    System.out.println("CHECK : "+check);
    stat.setNbrClauses(best);
    stat.instance=instance;
     stat.setTiming(check);
    return stat;
    }
      
    
    
    
    
     @Override
    public HashMap<String, ArrayList<Statistic>> CreateSolutionParameter(int instance, int time)
    {
        HashMap<String, ArrayList<Statistic>> ret = new HashMap<String, ArrayList<Statistic>>();
     ArrayList<Statistic> stat = new ArrayList<Statistic>();
     
    
    
        this.startTime();
     int nbvar = this.cnf.getNbrVars();
    if(this.method.equals("Largeur"))stat.add(this.Largeur(nbvar, time, instance));
    else{stat.add(Profondeur(nbvar, time, instance));}
   ret.put("algorithm",stat);
    return ret;
    }
}

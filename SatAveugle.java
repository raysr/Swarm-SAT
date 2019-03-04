/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swarmproject;
import java.util.Queue; 
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
    if(method.equals("Largeur")){this.method="Largeur"; System.out.println("Largeur Choisi");}
    if(method.equals("Profondeur")){this.method="Profondeur";System.out.println("Profondeur Choisi");}
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
    this.found=false;
    this.solution = new int[nbvar+1];
    int last=1;
    this.ProfondeurRec(this.solution, last);
    }
    
    public void ProfondeurRec(int[] sol, int indice)
    {
        if(indice==21 || this.found)return;
        if(this.cnf.ValidateSolution(sol)==this.cnf.getNbrClauses()){this.found=true;this.solution=sol;System.out.println("Found");this.endTime();}
        int[] neg=sol.clone();
       int[] pos=sol.clone();
       pos[indice]=1;
       this.ProfondeurRec(pos, indice+1);
       neg[indice]=-1;
       this.ProfondeurRec(neg, indice+1);
    }
 
    public void Largeur(int nbvar){
    this.found=false;
    this.solution = new int[nbvar+1];
    int last=1;
    this.LargeurRec(this.solution, last);
    
    }
    
    public void LargeurRec(int[] sol, int indice)
    {
        if(indice==21 || this.found)return;
        if(this.cnf.ValidateSolution(sol)==this.cnf.getNbrClauses()){this.found=true;this.solution=sol;this.endTime();}
        int[] neg=sol.clone();
       int[] pos=sol.clone();
       pos[indice]=1;
       if(this.cnf.ValidateSolution(pos)==this.cnf.getNbrClauses()){this.found=true;this.solution=pos;this.endTime();}
       neg[indice]=-1;
       if(this.cnf.ValidateSolution(neg)==this.cnf.getNbrClauses()){this.found=true;this.solution=neg;this.endTime();}
       this.LargeurRec(pos, indice+1);
       this.LargeurRec(neg, indice+1);
    }
 
}

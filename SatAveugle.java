/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetessaim;
import java.util.Queue; 
/**
 *
 * @author Rayan
 */
public class SatAveugle extends Sat{
    public String path;
    public Queue<Queue> cnf_format;
    private int[] solution; 
    private boolean found=false;
    private FileCnf cnf;
    private int best=0;
    public SatAveugle(String path)
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
        int c=this.cnf.ValidateSolution(sol);
        if(c>this.best)
        {
        this.best=c;
        System.out.println("Profondeur : "+this.best);
        }
        if(this.cnf.ValidateSolution(sol)==this.cnf.getNbrClauses()){this.found=true;this.solution=sol;System.out.println("Found");}
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
        int c=this.cnf.ValidateSolution(sol);
        if(c>this.best)
        {
        this.best=c;
        System.out.println("Largeur  : "+this.best);
        }
        if(this.cnf.ValidateSolution(sol)==this.cnf.getNbrClauses()){this.found=true;this.solution=sol;System.out.println("Found");}
        int[] neg=sol.clone();
       int[] pos=sol.clone();
       pos[indice]=1;
       if(this.cnf.ValidateSolution(pos)==this.cnf.getNbrClauses()){this.found=true;this.solution=pos;System.out.println("Found");}
       neg[indice]=-1;
       if(this.cnf.ValidateSolution(neg)==this.cnf.getNbrClauses()){this.found=true;this.solution=neg;System.out.println("Found");}
       this.LargeurRec(pos, indice+1);
       this.LargeurRec(neg, indice+1);
    }
 
}

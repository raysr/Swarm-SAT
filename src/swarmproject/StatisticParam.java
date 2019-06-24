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
public class StatisticParam extends Statistic {
    public String parametre="";
    public long value=-1;
    public long moyenneNbrClauses=0;
    public long meilleurNbrClauses=-1;
    public long moyenneTiming=0;
    public long meilleurTiming=-1;
    public ArrayList<Statistic> instances = new ArrayList<Statistic>();
    
    
    public void describe(){
    System.out.println("Moyenne nombre de clauses : "+this.moyenneNbrClauses);
    System.out.println("Moyenne timing : "+this.moyenneTiming);
    System.out.println("Meilleur nombre de clauses : "+this.meilleurNbrClauses);
    System.out.println("Meilleur timing : "+this.meilleurTiming);
    }
    
    
    public void calculate(){
    for(int i=0;i<this.instances.size();i++)
    {
        this.moyenneTiming+=instances.get(i).timing;
        this.moyenneNbrClauses+=instances.get(i).nbrClauses;
       if(instances.get(i).nbrClauses>this.meilleurNbrClauses){this.meilleurNbrClauses=instances.get(i).nbrClauses;} 
       if(instances.get(i).timing>this.meilleurTiming){this.meilleurTiming=instances.get(i).timing;} 
    }
this.moyenneTiming/=instances.size();
        this.moyenneNbrClauses/=instances.size();
        System.out.println(" Moyenne : "+this.moyenneNbrClauses);

}
}

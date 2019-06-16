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
    public void calculate(){
       // System.out.println("NBR INSTANCES : "+instances.size());
    for(int i=0;i<this.instances.size();i++)
    {
        this.moyenneTiming+=instances.get(i).timing;
        this.meilleurNbrClauses+=instances.get(i).nbrClauses;
       if(instances.get(i).nbrClauses>this.meilleurNbrClauses){this.meilleurNbrClauses=instances.get(i).nbrClauses;} 
       if(instances.get(i).timing>this.meilleurTiming){this.meilleurTiming=instances.get(i).timing;} 
    }
this.moyenneTiming/=instances.size();
        this.meilleurNbrClauses/=instances.size();
        switch(this.parametre)
                       {
                           case "C1":
                               this.value=(long)(instances.get(0).C1);
                               break;
                           case "C2":
                               this.value=(long)(instances.get(0).C2);
                               break;
                           case "Inertie":
                               this.value=(long)(instances.get(0).W);
                               break;
                           case "Taille Population":
                               this.value=(long)instances.get(0).sizePop;
                               break;
                          case "Taux Croisement":
                               this.value=(long)instances.get(0).RateCroisement;
                               break;
                          case "Taux Mutation":
                               this.value=(long)instances.get(0).RateMutation;
                               break;
                          case "MaxIter":
                               this.value=(long)instances.get(0).MaxIter;
                               break;
                          default:
                               this.value=(long)instances.get(0).number_particles;
                               break;
                       }    

    /*
    boolean test=true;
    if(this.parametre.equals("Taux Mutation"))
    {for(int j=0;j<this.instances.size();j++){
    if(instances.get(j).RateMutation!=instances.get(0).RateMutation){test=false;}
    }}
    if(!test){System.out.println("THEY LIED !!!!!!!");}
    else{System.out.println("IT'S OKAY BROOOO !!!");}
*/    
}
}

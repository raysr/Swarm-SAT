/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swarmproject;

/**
 *
 * @author Rayan
 */
public class Statistic {
    public int nbrClauses=0;
    public long timing=0;
    public long RateMutation=-1, RateCroisement=-1;
   public int MaxIter = -1;
   public int sizePop = -1;
   public double C1=-1,C2=-1,W=-1;
   public int number_particles=-1;
   public String algorithm;
   public int instance=-1;  
    public Statistic(int nbr, long time)
    {
        this.nbrClauses=nbr;
        this.timing=time;
    }
    public Statistic(){}
    public void setNbrClauses(int nbr){this.nbrClauses=nbr;}
    public void setTiming(long time){this.timing=time;}
    
    public int getNbrClauses(){return this.nbrClauses;}
    public long getTiming(){return this.timing;}
}

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

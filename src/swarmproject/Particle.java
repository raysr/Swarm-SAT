/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swarmproject;

import java.util.ArrayList;
import java.util.Random;
/**
 *
 * @author Rayan
 */
public class Particle {
    private int[] bestPosition, actualPosition;
    private int bestScore=0, actualScore=0;
    private double W=1, C1=1, C2=1;
    private double velocity;
    public Particle(int size, double C1, double C2, double W)
    {
         int max=1,min=-1;
         this.C1=C1;
         this.C2=C2;
         this.W=W;
    ArrayList<int[]> randomGen = new ArrayList<int[]>();
    int i=0;
    this.actualPosition = new int[size];
    int rand;
    this.velocity = 0;
    for(int j=0;j<size;j++)
    {
        rand = (int)(Math.random() * ((max - min) + 1)) + min;
        if(rand<0){this.actualPosition[j]= -1;}
        else{this.actualPosition[j]=1 ;}
    }
        this.bestPosition=this.actualPosition;
    }
    
    public void Converge()
    {
        int upper=this.actualPosition.length-1,lower=0;
        double rand = 0;
        double result = 0;
        for(int i=0;i<this.velocity;i++)
        {
           
            rand = (Math.random() * (upper - lower)) + lower;
            this.actualPosition[i]=this.getOneOrZero();
        }
    }
    
    public int getOneOrZero() {
    Random random = new Random();
    boolean isOne = random.nextBoolean();
    if (isOne) return 1;
    else return 0;
}
    public void UpdateVelocity(int[] globalBestSolution)
    {
            int upper=1,lower=0;
        
         double U1 =(Math.random() * (upper - lower)) + lower;
         double U2 =(Math.random() * (upper - lower)) + lower;
        this.velocity = this.W * this.velocity+ this.C1*U1*this.Distance(globalBestSolution, this.actualPosition) + this.C2*U2*this.Distance(this.bestPosition, this.actualPosition); 
        
    }
    public double Distance(int[] d1, int[] d2)
    {
    double count=0;
    for(int i=0;i<d1.length;i++)
    {
    if(d1[i]!=d2[i])count++;
    }
    return count;
    }
    public int[] getSolution(){return this.actualPosition;}
    public void UpdateScore(int score)
    {
        if(score>bestScore){bestScore=score;this.bestPosition=this.actualPosition;}
        this.actualScore=score;
    }
    public boolean compare(int nbrClauses){return this.bestScore>nbrClauses;}
}

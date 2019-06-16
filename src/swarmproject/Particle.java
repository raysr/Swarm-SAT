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
public class Particle {
    private int[] bestPosition, actualPosition;
    private int bestScore=0, actualScore=0;
    private double W=1, C1=1, C2=1;
    private double[] velocity;
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
    this.velocity = new double[size];
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
        int upper=1,lower=0;
        double rand = 0;
        double result = 0;
        for(int i=0;i<this.velocity.length;i++)
        {
            rand = (Math.random() * (upper - lower)) + lower;
            result = 1 / (1 + Math.exp(-1*this.velocity[i]) );
            if(rand<result)
            {
            this.actualPosition[i] = 1; 
                    }
            else
            {
            this.actualPosition[i] = -1; 
            }
        }
    }
    public void UpdateVelocity(int[] globalBestSolution)
    {
            int upper=1,lower=0;
        for(int i=0;i<this.actualPosition.length;i++)
        {
         double U1 =(Math.random() * (upper - lower)) + lower;
         double U2 =(Math.random() * (upper - lower)) + lower;
        this.velocity[i] = this.W * this.velocity[i] + this.C1*U1*(globalBestSolution[i] - this.actualPosition[i]) + this.C2*U2*(this.bestPosition[i] - this.actualPosition[i]); 
        }
    }
    public int[] getSolution(){return this.actualPosition;}
    public void UpdateScore(int score)
    {
        if(score>bestScore){bestScore=score;this.bestPosition=this.actualPosition;}
        this.actualScore=score;
    }
    public boolean compare(int nbrClauses){return this.bestScore>nbrClauses;}
}

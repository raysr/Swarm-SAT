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
public class Particle {
    private int[] bestPos, actualPos;
    private int bestScore=0;
    public Particle(int[] sol, int score){this.bestPos=sol;this.actualPos=sol;this.bestScore=score;}
    
    public void converge(){}
    public int[] getSolution(){return this.actualPos;}
    public boolean compare(int nbrClauses){return this.bestScore>nbrClauses;}
}

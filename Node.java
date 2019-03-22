/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swarmproject;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Rayan
 */
public class Node implements Comparable< Node >{
    public int[] solution;
    public int newone=0;
    public int f,g,h,indice;
    public String format;
    public Node(int[] solution, int f, int indice)
    {
        this.solution=solution.clone();
        this.f=f;
        this.indice=indice;
        this.format=Arrays.toString(this.solution)+" F="+Integer.toString(f);
    }
    public void print()
    {
    System.out.println("Variables : "+Arrays.toString(this.solution)+"  F="+this.f);
    }
    public ArrayList<Integer> getFollowing() // Return variables that weren't defined yet
    {
        ArrayList<Integer> following = new ArrayList<Integer>();
        for(int i=1;i<21;i++)
        {
            if(solution[i]==0){following.add(i);}
        }
        return following;
    }
    
     @Override
    public int compareTo(Node o){
        return (this.f==o.f)?1:0;
    }
}

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
    
    public void calculateF(){this.f=this.g+this.h;}
    public ArrayList<Integer> getFollowing() // Return variables that weren't defined yet
    {
        ArrayList<Integer> following = new ArrayList<Integer>();
        for(int i=1;i<21;i++)
        {
            if(solution[i]==0){following.add(i);}
        }
        return following;
    }
    
    public ArrayList<Node> getChilds()
    {
        
        ArrayList<Node> following = new ArrayList<Node>();
        if(this.indice!=20)
        {
        int[] pos = this.solution.clone();
        pos[this.indice+1]=1;
        Node n = new Node(pos,0,this.indice+1);
        n.g = this.g+1;
        following.add(n);
        int[] neg = this.solution.clone();
        neg[this.indice+1]=-1;
        n = new Node(neg, 0,this.indice+1);
        n.g = this.g+1;
        following.add(n);
        }
        return following;
    }
    
     @Override
    public int compareTo(Node o)
    {
        return (this.f==o.f)?1:0;
    }
}

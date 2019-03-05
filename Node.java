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
public class Node {
    public int[] solution;
    public int f,indice;
    public Node(int[] solution, int f, int indice)
    {
    this.solution=solution.clone();
    this.f=f;
    this.indice=indice;
    }
    public ArrayList<Node> getFollowing()
    {
    ArrayList<Node> following = new ArrayList<Node>();
    int[] pos = this.solution.clone();
    pos[this.indice+1]=1;
    following.add(new Node(pos,this.f,this.indice+1));
    int[] neg = this.solution.clone();
    neg[this.indice+1]=-1;
    following.add(new Node(neg,this.f,this.indice+1));
    return following;
    }
}

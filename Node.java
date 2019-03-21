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
    public ArrayList<Integer> getFollowing() // Return variables that weren't defined yet
    {
    ArrayList<Integer> following = new ArrayList<Integer>();
    for(int i=1;i<21;i++)
    {
    if(solution[i]==0)following.add(i);
    }
    return following;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swarmproject;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.*; 
/**
 *
 * @author Rayan
 */
public class OpenList extends PriorityQueue<Node> {
    public OpenList()
    {
    super(new Comparator<Node>() {
            @Override
            public int compare(Node n1, Node n2) {
                return n1.f - n2.f;
            }
});
    }
    
    
    /*
    public void Deploy(int[] solution, ArrayList<Integer> resting, ClosedList closed, Command f)
    {   
         ArrayList<Node> following = new ArrayList<Node>();         
         for(int j=0;j<resting.size();j++)
         {
            int[] pos = solution.clone();
            pos[resting.get(j)]=1;
            following.add(new Node(pos, f.calc(pos, resting.get(j)), 0));
            int[] neg = solution.clone();
            neg[resting.get(j)]=-1;
            following.add(new Node(neg, f.calc(neg, resting.get(j)), 0));
         }
     
     Node nod;
     for(int i=0;i<following.size();i++)
     {
         Iterator<Node> value = this.iterator(); 
        if(!(closed.containsKey(following.get(i).format)))
        {
            boolean test=false;
            while(value.hasNext())
            {
                nod=value.next();
                if(nod.solution.equals(following.get(i).solution))
                {
                    nod.f= (nod.f<following.get(i).f)?following.get(i).f:nod.f;
                    test=true;
                }
            }
            if(!test)
            {
                this.add(following.get(i));
            }
        }
     }
    }
    */
    public void DeployChilds(ArrayList<Node> following, ClosedList closed, Command h)
    {
    for(int i=0;i<following.size();i++)
     {
         Iterator<Node> value = this.iterator(); 
        if(!(closed.containsKey(following.get(i).format)))
        {
            boolean test=false;
            while(value.hasNext())
            {
                Node nod=value.next();
                if(nod.solution.equals(following.get(i).solution))
                {
                    nod.f= (nod.f<following.get(i).f)?following.get(i).f:nod.f;
                    test=true;
                }
            }
            if(!test)
            {
                following.get(i).h= h.execute(following.get(i).solution);
                following.get(i).calculateF();
                this.add(following.get(i));
            }
        }
     }
    
    }
    public void Print()
    {
        ArrayList<Integer> list = new ArrayList<Integer>();
        Iterator<Node> n = this.iterator();
        if(n.hasNext())list.add(n.next().f);
        System.out.println("OpenList : "+Arrays.toString(list.toArray()));
    }
}
    

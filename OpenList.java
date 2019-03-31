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
}
    

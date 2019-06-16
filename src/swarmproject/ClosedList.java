/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swarmproject;
import java.util.LinkedHashMap;
/**
 *
 * @author Rayan
 */
public class ClosedList extends LinkedHashMap<String, Node>{
    public void add(Node n)
    {
        super.put(n.format, n);
    }
}

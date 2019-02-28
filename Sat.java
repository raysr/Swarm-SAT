/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetessaim;
import javax.swing.DefaultListModel;
import java.util.Random;
/**
 *
 * @author Rayan
 */
public class Sat {
   private int[] solution = new int[21]; 

   public void RandomSolution(DefaultListModel mod1)
   {
       System.out.println("Generate random");
       int[] poss={0,1};
   for(int i=1;i<21;i++)
   {
   Random generator = new Random();
int randomIndex = generator.nextInt(poss.length);
this.solution[i]=poss[randomIndex];
mod1.addElement("X"+i+" : "+poss[randomIndex]);
   }
   }
public int[] getSolution(){return this.solution;}

}

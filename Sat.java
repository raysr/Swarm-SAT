/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetessaim;
import java.util.Random;
/**
 *
 * @author Rayan
 */
public class Sat {
   private Integer[] solution = new Integer[21]; 

   public void RandomSolution()
   {
       int[] poss={-1,1};
   for(int i=1;i<21;i++)
   {
   Random generator = new Random();
int randomIndex = generator.nextInt(poss.length);
this.solution[i]=poss[randomIndex];
   }
   }


public boolean ValidateSolution(int[][] cnfs)
{
return true;
}



}

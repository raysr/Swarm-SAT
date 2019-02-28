/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetessaim;
import java.util.Arrays;
import java.util.Random;
/**
 *
 * @author Rayan
 */
public class Sat {
   private int[] solution = new int[21]; 

   public void RandomSolution()
   {
       System.out.println("Generate random");
       int[] poss={0,1};
   for(int i=1;i<21;i++)
   {
   Random generator = new Random();
int randomIndex = generator.nextInt(poss.length);
this.solution[i]=poss[randomIndex];
   }
   }
public int[] getSolution(){return this.solution;}
/*
public boolean ValidateSolution(int[][] cnfs)
{
Integer[] sat = new Integer[91];
int size = cnfs.length;
for(int i=1;i<size;i++)
{
int size2=cnfs[i].length;
int last=cnfs[i][size2-1];
for(int j=0;j<last;j++)
{
sat[Math.abs(cnfs[i][j])]=((this.solution[i]>0 && cnfs[i][j]>0) || (this.solution[i]<0 && cnfs[i][j]<0))?1:0;
}
}
boolean test=true;
int i=0;
while(test && i<91)
{
    if(sat[i]==0){test=false;}
}
System.out.println("Variable instance : "+Arrays.toString(this.solution));
return test;
}

*/

}

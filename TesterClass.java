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
public class TesterClass {
     public static void main(String[] args){
    Controller control = new Controller();
   ArrayList<String> methods = control.getMethods();
  
    for(int i=0;i<methods.size();i++)
    {
         System.out.println("Method "+methods.get(i));
        control.ChooseMethod(methods.get(i),"");
        control.FolderTest("/Users/q/Documents/SII/PROJET ESSAIM/uf20-91");
    }

    }
    
}

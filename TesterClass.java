/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swarmproject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Scanner;
/*
 *
 *
 * @author Rayan
 */
    public class TesterClass 
    {
    public static void main(String[] args)
    {
       
    HashMap map = new HashMap();
    Controller control = new Controller();
    ArrayList<String> methods = control.getMethods();
    Scanner scan= new Scanner(System.in);

System.out.println(" Input directory of benchmark : ");

String directory=scan.nextLine();
    for(int i=0;i<methods.size();i++)
    {
        System.out.println("Method "+methods.get(i));
        String method = methods.get(i);
        control.ChooseMethod(methods.get(i));
        double mean = control.FolderTest(directory, 1);
        // map.put(map,mean);
    }
    
       System.out.println("Final Resultat : "+Arrays.asList(map));
    
    
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetessaim;
import java.util.ArrayList;
import java.util.Arrays;
/**
 *
 * @author Rayan
 */
public class SatAveugle extends Sat{
    public String path;
    public ArrayList<int[]> cnf_format;
    public SatAveugle(String path)
    {
        FileCnf f = new FileCnf(path);
        f.GenerateCnfFormated();
        this.cnf_format=f.getFormatedCnf();
    }
    
    public void Largeur(){
    ArrayList<Integer> defined = new ArrayList<Integer>();
    int[] sol= new int[21];
    }
    
    public int[] LargeurRec(int[] sol, ArrayList<Integer> defined, int indice)
    {
    for(int i=0;i<this.cnf_format.get(indice).length;i++)
    if(!Arrays.asList(defined).contains(this.cnf_format.get(indice)[i]))
    {
        ArrayList<Integer> copy=(ArrayList<Integer>)defined.clone();
        int[] new_sol=(int[])sol.clone();
        copy.add(this.cnf_format.get(indice)[i]);
        LargeurRec(new_sol ,copy, indice+1);   
    }
    }
    public void Profondeur(FileCnf f){}
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetessaim;

import java.util.ArrayList;
import java.io.FileNotFoundException; 
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Arrays;
/**
 *
 * @author Rayan
 */
public class ProjetEssaim {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    FileCnf fc = new FileCnf("/Users/q/Documents/SII/PROJET ESSAIM/uf20-91/uf20-024.cnf");
   fc.print();
    }
    
}

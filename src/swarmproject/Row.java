/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swarmproject;

/**
 *
 * @author Rayan
 */
public class Row {
    
    private String var1,var2,var3,number;

    public Row() {
    }

    public Row(int number, String var1, String var2, String var3) {
        this.var1=var1;
        this.var2=var2;
        this.var3=var3;
        this.number=String.valueOf(number);
    }

    public String getVar1() {
        return var1;
    }

    public String getVar2() {
        return var2;
    }

    public String getVar3() {
        return var3;
    }

    public String getNumber() {
        return number;
    }

    public void setVar1(String var1) {
        this.var1 = var1;
    }

    public void setVar2(String var2) {
        this.var2 = var2;
    }

    public void setVar3(String var3) {
        this.var3 = var3;
    }

    public void setNumber(String number) {
        this.number = number;
    }

   
}

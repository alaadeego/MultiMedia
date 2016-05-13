package vectorquantizer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.util.ArrayList;


public class Label {
    
    public ArrayList <Integer> pixels = new ArrayList <Integer> ();
    
    public Label(){
        //
    }
    
    public void showLabel(int delim){
        for (int i = 0;i < pixels.size();i++){
            if (i % delim == 0){
                System.out.println();
            }
            System.out.print(pixels.get(i) + ",");
        }
        System.out.println();
    }
}

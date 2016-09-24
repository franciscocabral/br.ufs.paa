/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lista1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;

/**
 *
 * @author Francisco Mota Cabral Filho
 */
public class franciscocabral_201210009197_porto {

    private int[][] vector;
    private int[][] temp;
    
    private int indexOut = 0;
    public PrintWriter fileOut;

    public void mergeSort(int[][] vector) {
        this.vector = vector;
        this.temp = new int[vector.length][3];
        
        sort(0, vector.length-1);
    }

    private void sort(int first, int last) {
        if (first < last) {
            int middle = first + (last-first)/2;
            sort(first, middle);
            sort(middle+1, last);
            merge(first, middle, last);
        }
    }
    

    private void merge(int first, int mid, int last) {
        for(int i = first; i <= last; i++) temp[i] = vector[i];
        
        int i = first, j = mid+1, k = first;
        
        while (i <= mid && j <= last) {
            int propI = temp[i][0],
                propJ = temp[j][0],
                prioI = temp[i][2],
                prioJ = temp[j][2];
            
            if (propI < propJ || (propI == propJ && prioI >= prioJ)) vector[k++] = temp[i++];
            else vector[k++] = temp[j++];
        }
        
        while(i <= mid) vector[k++] = temp[i++];
        
        if(indexOut > 0){
            String text = (indexOut++)+":";
            for(int x = 0; x < vector.length; x++){
               String number = Integer.toHexString(vector[x][0]).toUpperCase();
               text += " "+"000".substring(number.length())+number;
               
               number = Integer.toHexString(vector[x][1]).toUpperCase();
               text += "00000".substring(number.length())+number;
            }
            System.out.println(text);
            fileOut.println(text);
        }else{
            indexOut++;
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Input(arg[0]): "+args[0]+
                "\nOutput(arg[1]): "+args[1]+
                "\n--------------------------------");
        try {
            BufferedReader fileIn = new BufferedReader(new FileReader(args[0]));
            PrintWriter fileOut = new PrintWriter(args[1], "UTF-8");
            String line;
            
            int n = 0;
            
            line = fileIn.readLine();
            n = Integer.parseInt(line);
            
            int[][] vec = new int[n][3];
            
            System.out.println("Input File:"+
                    "\n"+n);
            for(int i=0; i<n; i++){
                line = fileIn.readLine();
                String[] words = line.split(" ");
                vec[i][0] = (int)Long.parseLong(words[0].split("x")[1].substring(0,3), 16);
                vec[i][1] = (int)Long.parseLong(words[0].split("x")[1].substring(3), 16);
                vec[i][2] = (int)Long.parseLong(words[1]);
                System.out.println(vec[i][0]+" "+vec[i][1]+" "+vec[i][2]);
            }
            System.out.println("\n--------------------------------"+
                    "\nOutput File:");
            
            franciscocabral_201210009197_porto myMerge = new franciscocabral_201210009197_porto();
            myMerge.fileOut = fileOut;
            myMerge.mergeSort(vec);
            
            fileOut.close();
            fileIn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

package lista1;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package franciscoufs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;

/**
 *
 * @author Francisco Mota Cabral Filho
 */
public class franciscocabral_201210009197_monitoramento {

    private int[][] vector;
    
    private int indexOut = 0;
    public PrintWriter fileOut;

    public void maxSort(int[][] vector) {
        this.vector = vector;
        int n = vector.length;
        
        for(int i = n/2; i >= 0; i--){
            heapify(i,n);
        }
        
        print();
        
        for(int i = n-1; i > 0; i--){
            int[] temp = vector[0];
            vector[0] = vector[i];
            vector[i] = temp;
            heapify(0,i);
            print();
        }
        
    }
    private void print(){
        String text = (indexOut++)+":";
        for(int j = 0; j < vector.length; j++){
           String number = Integer.toHexString(vector[j][0]).toUpperCase();
           text += " "+"00000000".substring(number.length())+number;
        }
        System.out.println(text);
        fileOut.println(text);
    }

    private void heapify(int i, int n) {
        int x;
        int[] temp;
        
        for(temp =  vector[i]; (2*i+1) < n; i = x){
            x = (2*i+1);
            if(x!= n-1 && vector[x][1]>= vector[x+1][1]) x++;
            if(temp[1] >= vector[x][1]) vector[i] = vector[x];
            else break;
        }
       vector[i] = temp;
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
            
            int[][] vec = new int[n][2];
            
            System.out.println("Input File:"+
                    "\n"+n);
            for(int i=0; i<n; i++){
                line = fileIn.readLine();
                String[] words = line.split(" ");
                vec[i][0] = (int)Long.parseLong(words[0].split("x")[1], 16);
                vec[i][1] = (int)Long.parseLong(words[1]);
                System.out.println(vec[i][0]+" "+vec[i][1]);
            }
            System.out.println("\n--------------------------------"+
                    "\nOutput File:");
            
            franciscocabral_201210009197_monitoramento myHeap = new franciscocabral_201210009197_monitoramento();
            myHeap.fileOut = fileOut;
            myHeap.maxSort(vec);
            
            fileOut.close();
            fileIn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

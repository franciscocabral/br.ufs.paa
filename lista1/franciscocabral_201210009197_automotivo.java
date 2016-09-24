/* To change this license header, choose License Headers in Project Properties.
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
public class franciscocabral_201210009197_automotivo {
    public static int MAIOR = 1;
    public static int IGUAL = 0;
    public static int MENOR = -1;
    
    public String[][] vector;
    
    private int indexOut = 0;
    public PrintWriter fileOut;
    
    private void printFile(){
        String Text = "";
        Text = (indexOut++)+":";
        for(int i=0; i<=vector.length-1; i++)                
            Text += (" "+vector[i][0]+vector[i][1]);

        System.out.println(Text);
        fileOut.println(Text);
            
    }
    
    public void quicksort(String[][] v){
       vector = v;
       printFile();
       sort(0,v.length-1);
    }
    
    private void sort(int first, int last){
        if(first < last){
            int pivoIndex = particionar(first, last);

            printFile();

            sort(first, pivoIndex -1);
            sort(pivoIndex+1, last);
        }
        
    }
    
    private int particionar(int first, int last){
        int i = first-1,
            j = last;
        String[] temp;
        String[] pivo = vector[last];
        
        for(j = first; j < last; j++){
            if(comp2(vector[j],pivo) <= IGUAL){
                i++;
                temp = vector[i];
                vector[i] = vector[j];
                vector[j] = temp;
            }
        }
        temp = vector[i+1];
        vector[i+1] = vector[last];
        vector[last] = temp;
       
        return i+1;
    }
    
    private int comp2(String[] sv1, String[] sv2){
        int l1, l2, res;
        String s1 = sv1[0], 
                s2 = sv2[0];
        String e1 = sv1[1], 
                e2 = sv2[1];
        l1 = s1.length();
        l2 = s2.length();
        
        if(l1 == l2){
            res = s1.compareTo(s2);
            if(res == IGUAL) res = e1.compareTo(e2);
        }
        else if (l1 > l2) {
            String ns1 = (s1.substring(0,l2));
            res = ns1.compareTo(s2);
            if (res == IGUAL) res = MAIOR;
        }
        else {
            String ns2 = (s2.substring(0,l1));
            res = (s1).compareTo(ns2);
            if (res == IGUAL) res = MENOR;
        }
        
        return res;
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
            
            String[][] vec = new String[n][2];
            
            System.out.println("Input File:"+
                    "\n"+n);
            for(int i=0; i<n; i++){
                line = fileIn.readLine();
                vec[i][0] = line.substring(0,line.length()-4);
                vec[i][1] = line.substring(line.length()-4,line.length());
                System.out.println(vec[i][0]+" "+vec[i][1]);
            }
            System.out.println("\n--------------------------------"+
                    "\nOutput File:");
            
            franciscocabral_201210009197_automotivo myQuicky = new franciscocabral_201210009197_automotivo();
            myQuicky.fileOut = fileOut;
            myQuicky.quicksort(vec);
            
            fileOut.close();
            fileIn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
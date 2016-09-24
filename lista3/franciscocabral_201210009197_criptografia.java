/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package lista3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;

/**
 *
 * @author Fancisco
 */
public class franciscocabral_201210009197_criptografia {
    public static PrintWriter fileOut;
    
    private int k ;
    private int p;
    private int secureKey;
    
    private int M = 1103515245;
    private int D = 12345;
    
    public static int j = 0;
    public static int key;

    public franciscocabral_201210009197_criptografia(int k) {
        this.k = k;
    }

    public int setKey(int p, int g) {
        this.p = p;
        return (int) (Math.pow(g, k) % p);
    }
    public void setKey(double A){
        this.secureKey = (int) (Math.pow(A, k) % p);
    }
    
    public static void print(String Text){
            //System.out.println(Text);
            fileOut.println(Text);
    }
    
    private int G(){
        if(j == 0) key = M * secureKey + D;
        int z = ((key >> j) & 0xFF);
        j = (j+1)%32;
        return z;
    }
    
    public int cript(char t){
        return (t ^ G());
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
            fileOut = new PrintWriter(args[1], "UTF-8");
            String line;
            String num[];
            franciscocabral_201210009197_criptografia A;
            franciscocabral_201210009197_criptografia B;
            
            line = fileIn.readLine();
            num = line.split("\\W+");
            int a2b = Integer.parseInt(num[1]);
            line = fileIn.readLine();
            num = line.split("\\W+");
            int b2a = Integer.parseInt(num[1]);
            
            A = new franciscocabral_201210009197_criptografia(b2a); 
            B = new franciscocabral_201210009197_criptografia(a2b); 
                
            line = fileIn.readLine();
            num = line.split("\\W+");
            int p = Integer.parseInt(num[1]);
            int g = Integer.parseInt(num[2]);
            
            int b = A.setKey(p, g);
            int a = B.setKey(p, g);
            print("A->B: "+a);
            print("B->A: "+b);
            
            A.setKey(b);
            B.setKey(a);
            
            line = fileIn.readLine();
            int n = Integer.parseInt(line);
            
            for(int i=0; i<n; i++){     
                line = fileIn.readLine();
                char[] lineChar = line.toCharArray();
                String txt ="";
                if(i%2 == 0)
                    txt = "A->B: ";
                else
                    txt = "B->A: ";
                for(int j = 0; j<lineChar.length; j++){
                    txt+=A.cript(lineChar[j])+" ";
                }
                print(txt);
            }
            fileOut.close();
            fileIn.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

package lista2;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package franciscoufs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import static java.lang.Math.*;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 *
 * @author Francisco Mota Cabral Filho
 */
public class franciscocabral_201210009197_compressao{
   
    public PrintWriter fileOut;
    static HashMap<Character, Integer> count = new HashMap<Character, Integer>();
    static HashMap<Character, String> HuffTab = new HashMap<Character, String>();
    
    static String char2bin(char c){
        String bin = Integer.toString(c,2);
        return "00000000".substring(bin.length())+bin;
    }
    
    static String dec2bin(int d){
        return Integer.toString(d, 2);
    }
    
    static int percent(String Origin, String New){
        float lo = Origin.length();
        float no = New.length();
        float per = no/lo;
        return round(100*per);
    }
    
    private void print(String text){
        //System.out.println(text);
        fileOut.println(text);
    }
    
    static String bin(String text){
        StringBuffer text_bin = new StringBuffer("");
        char[] t = text.toCharArray();
        int m = text.length();
        int i;
        for(i=0;i<m;i++){
            text_bin.append(char2bin(t[i]));
        }
        return text_bin.toString();
    }
    
    static String rle(String text){
        StringBuffer text_rle = new StringBuffer("");
        char[] t = text.toCharArray();
        int m = text.length();
        int i;
        for(i=0;i<m;i++){
            int runLenght = 1;
            while(i+1 < m && t[i] == t[i+1] && runLenght < 16){
                runLenght++;
                i++;
            }
            text_rle.append(dec2bin(runLenght)+char2bin(t[i]));
        }
        return text_rle.toString();
    }

    static String huf(String text){
        StringBuffer  text_huf = new StringBuffer("");
        char[] t = text.toCharArray();
        int m = text.length();
        int i;
        
        //Criar lista com frequência
        for(i=0; i<m; i++){
            Integer freq = count.get(t[i]);
            if(freq == null){
                count.put(t[i], 1);
            }else{
                count.put(t[i], freq+1);
            }
        }
        
        //criação inicial da fila
        fila myFila = new fila();
        for(Entry<Character, Integer> entry : count.entrySet()){
            char simb = entry.getKey();
            int freq = entry.getValue();
            no T = new no(simb, freq);
            myFila.add(T);
        }
        
        int tot = myFila.size();
        //Gerar Árvore
        while(myFila.size() > 1){
            no T1 = myFila.remove();
            no T2 = myFila.remove();
            int sum = 0;
            if(T1 != null) sum += T1.freq;
            if(T2 != null) sum += T2.freq;
            
            no T3 = new no('\0', sum);
            T3.esq = T1;
            T3.dir = T2;
            myFila.add(T3);
        }
        if(tot > 1){
            gerarTabela(myFila.Head, "");
        }else{
            HuffTab.put(myFila.Head.simb, "0");
        }
        for(i=0;i<m;i++){
            text_huf.append(HuffTab.get(t[i]));
        }
        
        return text_huf.toString();
    }
    
    static void gerarTabela(no T, String last){
        if(T.simb == '\0'){
            gerarTabela(T.esq, last+"0");
            gerarTabela(T.dir, last+"1");
        }else{
            HuffTab.put(T.simb, last);
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
            
            line = fileIn.readLine();
            int n = Integer.parseInt(line);
            String[] text = new String[n];
            
            System.out.println("Input File:\n"+n);
            for(int i=0; i<n; i++){
                text[i] = fileIn.readLine();
                //System.out.println(text[i]);
            }
            System.out.println("\n--------------------------------"+"\nOutput File:");
            franciscocabral_201210009197_compressao myC = new franciscocabral_201210009197_compressao();
            myC.fileOut = fileOut;
            
            String txtbin, txtrle, txthuf;
            int per_rle, per_huf;
            for(int i = 0; i <n; i++){
                txtbin = bin(text[i]);
                txtrle = rle(text[i]);
                txthuf = huf(text[i]);
                per_rle = percent(txtbin, txtrle);
                per_huf = percent(txtbin, txthuf);
                if(per_huf < per_rle)
                    myC.print(i+": HUF "+txthuf+" ("+per_huf+"%)");
                else if(per_huf > per_rle)
                    myC.print(i+": RLE "+txtrle+" ("+per_rle+"%)");
                else{
                    myC.print(i+": RLE "+txtrle+" ("+per_rle+"%)");
                    myC.print(i+": HUF "+txthuf+" ("+per_huf+"%)");
                }         
                
                count.clear();
                HuffTab.clear();       
            }         
            
            fileOut.close();
            fileIn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

class no{
    public int freq;
    public char simb;
    public no dir;
    public no esq;
    public no next;
    public no(char simb, int freq){
        this.simb = simb;
        this.freq = freq;
    }
}

class fila{
    public no Head;
    
    public int size(){
        no act = this.Head;
        if (act == null) return 0;
        int i = 0;
        do{
            i++;
            act = act.next;
        }while(act != null);
        return i;
    }
    
    public no busca(int freq){
        no act = this.Head;
        if (act == null) return null;
        do{
            if(act.next != null)
                act = act.next;
        }while(act.next != null && act.next.freq <= freq);
        
        return act;
    }
    
    public void add(no N){
        if(this.Head == null){
            this.Head = N;
        }else{
            no act = this.busca(N.freq);
            no T = act.next;
            act.next = N;
            N.next = T;
        }
    }
    
    public no remove(){
        no act = this.Head;
        no T = act.next;
        this.Head = T;
        return act;
    }
}
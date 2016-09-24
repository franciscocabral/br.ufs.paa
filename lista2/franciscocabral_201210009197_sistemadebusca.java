package lista2;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author Francisco Mota Cabral Filho
 */
public class franciscocabral_201210009197_sistemadebusca {
    
    private int[] tab;
    private ArrayList<Integer>[][] pos;
    private int[] wordsCount;
    
    public PrintWriter fileOut;

    private void print(String Text){
            System.out.println(Text);
            fileOut.println(Text);
    }
    
    private void calcTable(String p){
        int i;
        int m = p.length();
        int j=-1;
        this.tab = new int[m];
        
        char[] P = p.toCharArray();
        
        for(i=1; i < m; i++){
            while(j>= 0 && P[j+1] != P[i])
                j = tab[j];
            if(P[j+1]==P[i]) j++;
            tab[i] = j;
        }
        
    }
    
    private void KMP(String[] ts, String[] ps){
        int k;
        int l;
        pos = new ArrayList[ps.length][ts.length];
        wordsCount = new int[ts.length];
        for(l = 0; l < ps.length; l++){
            String p = ps[l];
            for(k = 0; k < ts.length; k++){ 
                pos[l][k] = new ArrayList<Integer>();
                String t = ts[k];
                char[] T = t.toCharArray();
                char[] P = p.toCharArray();

                int i, n = t.length();
                int m = p.length();
                int j = -1;

                calcTable(p);

                for(i=0;i<n;i++){
                   while(j>=n && P[j+1] != T[i]) j = tab[j];
                   if(P[j+1]==T[i]) j++;
                   if(j == m-1){
                       pos[l][k].add(i-m+1);
                       wordsCount[l]++;
                       j=tab[j];
                   }
                }
            }
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
            int m = 0;
            
            line = fileIn.readLine();
            n = Integer.parseInt(line);
            
            String[] vec  = new String[n];
            
            System.out.println("Input File:"+"\n"+n);
            for(int i=0; i<n; i++){
                vec[i] = fileIn.readLine();
                System.out.println(vec[i]);
            }
            line = fileIn.readLine();
            m = Integer.parseInt(line);
            String[] words = new String[m];
            for(int i=0; i<m; i++){
                words[i] = fileIn.readLine();
                System.out.println(words[i]);
            }
            
            System.out.println("\n--------------------------------"+
                "\nOutput File:");
            
            /*
                Programa
            */
            franciscocabral_201210009197_sistemadebusca myBusca = new franciscocabral_201210009197_sistemadebusca();
            myBusca.fileOut = fileOut;
            
            myBusca.KMP(vec, words);
            
            boolean plural, initial, resultado;
            StringBuffer text, textBefore,textAfter;
            int y = 0;
            int x;
            
            for(y=0; y < words.length; y++){
                resultado = myBusca.wordsCount[y] > 0;
                if(resultado){
                    plural = myBusca.wordsCount[y] > 1;
                    textBefore = new StringBuffer(plural ? "resultados nas cadeias" : "resultado na caseia");
                    textBefore.append("["+words[y]+"] "+myBusca.wordsCount[y]+" "+textBefore);
                    textAfter = new StringBuffer();
                    initial = true;
                    for(x = 0; x < vec.length; x++){
                        if(myBusca.pos[y][x].size() > 0){
                            if(initial){
                                textAfter.append(x+":"+myBusca.pos[y][x].size());
                                initial = false;
                            }
                            else
                                textAfter.append(", "+x+":"+myBusca.pos[y][x].size());
                        }
                    }
                    text = new StringBuffer(textBefore.toString()+" "+textAfter.toString());
                }else{
                    text = new StringBuffer("["+words[y]+"] Sem resultados");
                }
                myBusca.print(text.toString());
            }
                
            fileOut.close();
            fileIn.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

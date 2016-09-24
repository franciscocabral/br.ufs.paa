//package lista3;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;

/**
 *
 * @author Francisco Mota Cabral Filho
 */
public class franciscocabral_201210009197_labirinto {
    public static PrintWriter fileOut;

    public static final int LIVRE = 0;
    public static final int PAREDE = 1;
    public static final int CHECADO = 2;
    public static final pos VOLTAR = new pos(-1,-1);
    
    public int largura = 0;
    public int altura = 0;
    
    public pos inicio;
    
    private lista caminho;
    
    /**
     * 0: livre
     * 1: parede
     * 2: checado
     */
    public int[][] matriz;
    
    public static void print(String Text){
            //System.out.println(Text);
            fileOut.println(Text);
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
            int labirintos = 0;
            franciscocabral_201210009197_labirinto labirinto;
            
            line = fileIn.readLine();
            labirintos = Integer.parseInt(line);
            
            for(int i=0; i<labirintos; i++){
                labirinto = new franciscocabral_201210009197_labirinto();
                print("L"+i+":");
                //System.out.println("--------------------------------");
                
                
                line = fileIn.readLine();
                String num[] = line.split("\\W+");
                labirinto.largura = Integer.parseInt(num[0]);
                labirinto.altura = Integer.parseInt(num[1]);
                labirinto.matriz = new int[labirinto.altura][labirinto.largura];
                
                for(int ia = 0; ia < labirinto.altura; ia++){
                    line = fileIn.readLine();
                    num = line.split("\\W+");
                    for(int il = 0; il < labirinto.largura; il++){
                        if(num[il].toLowerCase().compareTo("x") == 0){
                            labirinto.inicio = new pos(ia,il);
                            labirinto.matriz[ia][il] = LIVRE;
                        }else {
                            labirinto.matriz[ia][il] = Integer.parseInt(num[il]);
                        }
                        System.out.print(num[il]+" ");
                    }
                    System.out.print("\n");
                }
                //System.out.println("--------------------------------"+
                //                 "\nOutput File:"+
                //                 "\n--------------------------------");
                labirinto.acharSaida();
                
                //System.out.println("--------------------------------");
            }
            fileOut.close();
            fileIn.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    public void acharSaida(){
        caminho = new lista();
        caminho.add(inicio);
        pos atual = inicio;
        print("INICIO "+inicio.toString());
        
        while(caminho.size > 0 && !naBorda(atual)){
            checa(atual);
            atual = temProximo(atual);
            if(atual == VOLTAR){
                pos bt = caminho.rem();
                if(caminho.size>0){
                    atual = caminho.last();
                    print("BT "+atual.toString()+"<-"+bt.toString());
                }else{
                    print("SEM SAIDA");
                }
            }else if(naBorda(atual)){
                print("SAIDA "+atual.toString());
                break;
            }else{
                caminho.add(atual);
            }            
        
        }
    }
    public void checa(pos posicao){
        matriz[posicao.x][posicao.y] = CHECADO;
    }
    
    public boolean naBorda(pos posicao){
        return (posicao.x == 0 
            || posicao.x == altura-1
            || posicao.y == 0 
            || posicao.y == largura-1);
    }
    
    public boolean existe(pos posicao){
        return (posicao.x >= 0 
            && posicao.x <= altura-1
            && posicao.y >= 0 
            && posicao.y <= largura-1);
    }
    public boolean livre(pos posicao){
        return matriz[posicao.x][posicao.y] == LIVRE;
    }
    
    public pos temProximo(pos posicao){
        /* Verifica se existe próximo */     
        /* right, top, left, bottom */
        /* x: altura
           y: largura */
        
        //Bordas
        pos proximo = VOLTAR;
        String txt = "";
        if (existe(posicao.direita()) && livre(posicao.direita())) {
            txt = "D ";
            proximo= posicao.direita();
        } else if (existe(posicao.frente()) && livre(posicao.frente())) {
            txt = "F ";
            proximo=  posicao.frente();
        } else if (existe(posicao.esquerda()) && livre(posicao.esquerda())) {
            txt = "E ";
            proximo=  posicao.esquerda();
        } else if (existe(posicao.tras()) && livre(posicao.tras())) {
            txt = "T ";
            proximo=  posicao.tras();
        } else {
            return proximo;
        }
       print(txt+posicao.toString()+"->"+proximo.toString());
       return proximo;
    }

}

class pos{
    public int x, y;
    
    public pos next;
    
    public pos(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public pos direita(){
        return new pos(x,y+1);
    }
    public pos frente(){
        return new pos(x-1,y);
    }
    public pos esquerda(){
        return new pos(x,y-1);
    }
    public pos tras(){
        return new pos(x+1,y);
    }
    
    @Override
    public String toString(){
        return "["+x+","+y+"]";
    }
}

class lista{
    public pos head;
    public int size;
    
    public lista(){
        head = null;
        size = 0;
    }
    
    public pos rem(){
        pos a = head;
        pos p = null;
        if(a == null) return null;
        if(a.next == null){
            head = null;
            size--;
            return a;
        }
        
        while(a.next != null){
            p = a;
            a = a.next;
        }
        p.next = null;
        size--;
        return a;
    }
    
    public void add(pos p){
        pos n =  p;
        if(size == 0){
            head = n;
        }else{
            last().next = n;
        }
        size++;
    }
    
    public pos last(){
        pos a = head;
        if(a == null) return null;
        while(a.next != null){
            a = a.next;
        }
        return a;
    }
}
package grafos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author Francisco Mota Cabral Filho
 *
 * @Créditos
 *      http://www.random.org      
 *          Por gerar os números para as entradas de test.
 *      http://www.cs.waikato.ac.nz/~bernhard/317/source/graph/UnionFind.java
 *          Exemplo de Union-Find em Java onde me baseei para escrever o meu Union-Find.
 *      http://en.wikipedia.org/wiki/Kruskal's_algorithm
 *          Algoritmo de Kurskal em Pseudo Código
 *      http://stackoverflow.com/questions/18441846/how-sort-a-arraylist-in-java
 *          Dica de como fazer Sort de ArrayList em Java
 * 
 * 
 */
public class franciscocabral_agm {
   
    public PrintWriter fileOut;

    public static Graph kurskal(Graph G){
        UnionFind uf = new UnionFind();
        uf.add(G.getNodes());
        G.sort();
        Graph A = new Graph();
        for(Iterator<Node> i = G.getNodes().iterator(); i.hasNext();){
            Node nextNode = i.next();
            A.addNode(nextNode.getName());
        }
        for (Iterator<Edge> iterator = G.getEdges().iterator(); iterator.hasNext();) {
            Edge nextEdge = iterator.next();
            Integer u = nextEdge.getFrom().getId(), 
                    v = nextEdge.getTo().getId(),
                    w = nextEdge.getWeight();
            Integer findU = uf.Find(u).getId(),
                    findV = uf.Find(v).getId();
            if(findU.compareTo(findV) != 0){
                A.addEdge(u, v, w);
                uf.Union(findU, findV);
            }
        }
        return A;
    }
    
    public static Graph prim(Graph G){
        Graph A = new Graph();
        
        ArrayList<Integer> dist = new ArrayList<Integer>();
        ArrayList<Integer> neigh = new ArrayList<Integer>();
        
        for(Iterator<Node> i = G.getNodes().iterator(); i.hasNext();){
            Node nextNode = i.next();
            A.addNode(nextNode.getName());
            nextNode.setChecked(false);
            neigh.add(0);
            dist.add(Integer.MAX_VALUE);
        }
        dist.set(0, 0);
        
        Integer Vertex = 0;
        
        for(Iterator<Node> i = G.getNodes().iterator(); i.hasNext();){
            Node nextNode = i.next();
            for(Iterator<Node> j = G.getNodes().iterator(); j.hasNext();){
                Node nextNeigh = j.next();
                if((nextNeigh.isChecked()) || (nextNeigh.getId() == Vertex)){
                    continue;
                }
                
                for(Iterator<Edge> k = G.getEdges().iterator(); k.hasNext();){
                    Edge nextEdge = k.next();
                    if(nextEdge.isMyNodes(nextNode, nextNeigh)){
                        if(nextEdge.getWeight() < dist.get(nextNeigh.getId())){
                             dist.set(nextNeigh.getId(), nextEdge.getWeight());
                             neigh.set(nextNeigh.getId(), Vertex);
                        }
                    }
                }
            }
            
            nextNode.setChecked(true); 
            Vertex = 0;
            Integer distCompared = Integer.MAX_VALUE;
            
            for (Iterator<Node> x = G.getCheckedNodes().iterator(); x.hasNext();) {
                Node next = x.next();
                if(dist.get(next.getId()).compareTo(distCompared) < 0){
                    distCompared = dist.get(next.getId());
                    Vertex = next.getId();
                }
            }
        }
        
        for (Iterator<Node> i = G.getNodes().iterator(); i.hasNext();) {
            Node next = i.next();
            A.addEdge(next.getId(), neigh.get(next.getId()), dist.get(next.getId()));
        }
        
        
        
        return A;
    }
    
    private void print(String Text){
            System.out.println(Text);
            fileOut.println(Text);
    }
  
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Algorytm(arg[0]): "+args[0]+
                "\nInput(arg[1]): "+args[1]+
                "\n--------------------------------");
        try {
            Integer algorytm = Integer.parseInt(args[0]);
            BufferedReader fileIn = new BufferedReader(new FileReader(args[1]));
//            PrintWriter fileOut = new PrintWriter(args[1], "UTF-8");
            
            String line;
            int n = 0;
            int m = 0;
            Graph grafoIn = new Graph();
            String[] values;
            
            line = fileIn.readLine();
            n = Integer.parseInt(line);
            
            for(int i=0; i<n; i++){
                grafoIn.addNode(fileIn.readLine());
            }
            
            line = fileIn.readLine();
            m = Integer.parseInt(line);
            
            for(int i=0; i<m; i++){
                line = fileIn.readLine();
                values = line.split("\\W+");
                grafoIn.addEdge(Integer.parseInt(values[0]), Integer.parseInt(values[1]), Integer.parseInt(values[2]));
            }
            
            /*
                Programa
            */
            System.out.println("Grafo In:");
            grafoIn.printOut();
            System.out.println("--------------------------------");
            if(algorytm == 1){
                System.out.println("Algorytm: Kurskal"
                        + "\n--------------------------------");
                Graph K = kurskal(grafoIn);
                K.printOut();
                System.out.println("--------------------------------");
            }
            if(algorytm == 2){
                System.out.println("Algorytm: Prim"
                        + "\n--------------------------------");
                Graph K = prim(grafoIn);
                K.printOut();
                System.out.println("--------------------------------");
            }


            fileIn.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

class UnionFind{
    private HashMap<Integer, Node> Sets = new HashMap<Integer,Node>();
    
    public void add(ArrayList<Node> Nodes){
        for (Iterator<Node> iterator = Nodes.iterator(); iterator.hasNext();) {
            Node next = iterator.next();
            Node N = new Node(next.getName(), next.getId());
            N.setHead(N);
            N.setParent(N);
            Sets.put(N.getId(),N);
        }
    }
    
    public boolean Union(Integer from, Integer to){
        Node f = Find(from);
        Node t = Find(to);
        if(f.getId().compareTo(t.getId()) == 0){return false;}
        else{
            t.setParent(f);
            t.setHead(f.getHead());
            return true;
        }
    }
    
    public Node Find(Integer id){
        Node N = Sets.get(id);
        return N.getHead();
    }
    
}

class Node{
    private String Name;
    private Integer Id;
    private Node Head;
    private Node Parent;
    private boolean Checked;

    public Node(String Name, Integer Id) {
        this.Name = Name;
        this.Id = Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public Node getHead() {
        return Head;
    }

    public void setHead(Node Head) {
        this.Head = Head;
    }

    public Node getParent() {
        return Parent;
    }

    public void setParent(Node Parent) {
        this.Parent = Parent;
    }

    public boolean isChecked() {
        return Checked;
    }

    public void setChecked(boolean Checked) {
        this.Checked = Checked;
    }

    @Override
    public String toString() {
        return "Node{Id="+ Id +", " + "Name=" + Name + '}';
    }
}

class Edge{
    private Node from;
    private Node to;
    private Integer weight;

    public Edge(Node from, Node to, Integer weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public Node getFrom() {
        return from;
    }

    public void setFrom(Node from) {
        this.from = from;
    }

    public Node getTo() {
        return to;
    }

    public void setTo(Node to) {
        this.to = to;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
    public boolean isMyNodes(Node From, Node To){
        if(From.getId().compareTo(from.getId()) == 0
                && To.getId().compareTo(to.getId()) == 0)
            return true;
        return false;
    }

    @Override
    public String toString() {
        return "\n\t\tEdge{" + "from=" + from + ",\tto=" + to + ",\tweight=" + weight + "}";
    }
}

class Graph{
    private ArrayList<Node> Nodes;
    private ArrayList<Edge> Edges;

    public Graph(){
        this.Nodes = new ArrayList<Node>();
        this.Edges = new ArrayList<Edge>();
    }
    
    public Graph(ArrayList<Node> Nodes, ArrayList<Edge> Edges) {
        this.Nodes = Nodes;
        this.Edges = Edges;
    }
    
    public void sort(){
        Collections.sort(Edges, new Comparator<Edge>(){
            @Override
            public int compare(Edge edge1, Edge edge2) {
                return edge1.getWeight().compareTo(edge2.getWeight());
            }
        });
    }

    public ArrayList<Node> getNodes() {
        return Nodes;
    }

    public void setNodes(ArrayList<Node> Nodes) {
        this.Nodes = Nodes;
    }

    public ArrayList<Edge> getEdges() {
        return Edges;
    }

    public void setEdges(ArrayList<Edge> Edges) {
        this.Edges = Edges;
    }
    
    public void addNode(String Name){
        Node N = new Node(Name,this.Nodes.size());
        this.Nodes.add(N);
    }
    
    public void addEdge(Integer ID1, Integer ID2, Integer Weight){
        try{
            Edge E = new Edge(Nodes.get(ID1), Nodes.get(ID2), Weight);
            this.Edges.add(E);
        }catch(Exception e){
            System.out.println("ERROR 001");
        }
    }
    
    public ArrayList<Node> getCheckedNodes(){
        ArrayList<Node> A = new ArrayList<Node>();
        for (Iterator<Node> i = Nodes.iterator(); i.hasNext();) {
            Node next = i.next();
            if(next.isChecked())
                A.add(next);
        }
        return A;
    }

    @Override
    public String toString() {
        return "Graph{\n\t" + "Nodes=" + Nodes + ", \n\tEdges=" + Edges + "\n}";
    }
    
    public void printOut(){
        System.out.println(getNodes().size());
        for(Iterator<Node> i = getNodes().iterator(); i.hasNext();){
            Node nextNode = i.next();
            System.out.println(nextNode.getName());
        }
        System.out.println(getEdges().size());
        for (Iterator<Edge> iterator = getEdges().iterator(); iterator.hasNext();) {
            Edge nextEdge = iterator.next();
            System.out.println(nextEdge.getFrom().getId()+" "+nextEdge.getTo().getId()+" "+nextEdge.getWeight());
            
        }
    }
}
package interfaces;

public interface Grafo {
    public int ordem();
    public  int tamanho();
    public Iterable<Integer> vertices();
    public Iterable<Integer> vizinhos(int v);
    public int grau(int v);
    public boolean tem_aresta(int u, int v);
    public void inserir_aresta(int u, int v);
}

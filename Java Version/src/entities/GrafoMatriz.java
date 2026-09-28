package entities;

import interfaces.Grafo;

import java.util.ArrayList;
import java.util.List;

public class GrafoMatriz implements Grafo {

    private int[][] a;
    private int m = 0;

    public GrafoMatriz(int n) {
        a = new int[n][n];
        m = 0;
    }

    @Override
    public int ordem() {
        return a.length;
    }

    @Override
    public int tamanho() {
        return m;
    }

    @Override
    public Iterable<Integer> vertices() {
        List<Integer> vertices = new ArrayList<>();

        for (int i = 0; i < ordem(); i++) {
            vertices.add(i);
        }
        return vertices;
    }

    @Override
    public Iterable<Integer> vizinhos(int v) {
        List<Integer> vizinhos = new ArrayList<>();

        for (int i = 0; i < ordem(); i++) {
            if (a[v][i] == 1){
                vizinhos.add(i);
            }
        }

        return vizinhos;

    }

    @Override
    public int grau(int v) {
        int grau = 0;
        for (int i = 0; i < ordem(); i++) {
            if (a[v][i] == 1){
                grau++;
            }
        }
        return grau;
    }

    @Override
    public boolean tem_aresta(int u, int v) {
        return a[u][v] == 1;
    }

    @Override
    public void inserir_aresta(int u, int v) {
        if (!tem_aresta(u,v)){
            a[u][v] = 1;
            a[v][u] = 1;
            m++;
        }

    }

}

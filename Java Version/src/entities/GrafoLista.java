package entities;

import interfaces.Grafo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GrafoLista implements Grafo {

    private List<Set<Integer>> adj;
    private int m;

    public GrafoLista(int n) {
        adj = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            adj.add(new HashSet<>());
        }

        m = 0;

    }

    @Override
    public int ordem() {
        return adj.size();
    }

    @Override
    public int tamanho() {
        return m;
    }

    @Override
    public Iterable<Integer> vertices() {

        List<Integer> v = new ArrayList<>();

        for (int i = 0; i < ordem(); i++) {
            v.add(i);
        }
        return v;

    }

    @Override
    public Iterable<Integer> vizinhos(int v) {
        return adj.get(v);
    }

    @Override
    public int grau(int v) {
        return adj.get(v).size();
    }

    @Override
    public boolean tem_aresta(int u, int v) {
        return adj.get(u).contains(v);
    }

    @Override
    public void inserir_aresta(int u, int v) {
        if (!tem_aresta(u,v)){
            adj.get(u).add(v);
            adj.get(v).add(u);
            m++;
        }
    }
}

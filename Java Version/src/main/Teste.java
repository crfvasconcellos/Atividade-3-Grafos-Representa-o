package main;

import entities.GrafoLista;
import entities.GrafoMatriz;
import interfaces.Grafo;

public class Teste {
    static void main() {

//        a → 0
//        b → 1
//        c → 2
//        d → 3
//        e → 4
//        f → 5


        Grafo matriz = new GrafoMatriz(6);
        Grafo lista = new GrafoLista(6);

        int[][] arestas = {
                {0,1},
                {0,2},
                {1,2},
                {1,3},
                {2,3},
                {2,4},
                {3,4},
                {4,5}
        };

        for (int[] aresta : arestas){
            matriz.inserir_aresta(aresta[0],aresta[1]);
            lista.inserir_aresta(aresta[0],aresta[1]);
        }

        testarGrafo("Matriz",matriz);
        testarGrafo("Lista",lista);



    }

    public static void testarGrafo(String nome, Grafo grafo) {

        System.out.println("===== " + nome + " =====");
        
        System.out.println("Ordem: " + grafo.ordem());


        System.out.println("Tamanho: " + grafo.tamanho());

        // Graus esperados:
        // a = 2
        // b = 3
        // c = 4
        // d = 3
        // e = 3
        // f = 1
        int[] grausEsperados = {2, 3, 4, 3, 3, 1};

        System.out.print("Graus: ");

        int somaGraus = 0;

        for (int v : grafo.vertices()) {
            int grau = grafo.grau(v);

            System.out.print(grau + " ");

            somaGraus += grau;
        }

        System.out.println();

        System.out.println("Soma dos graus: " + somaGraus);
        System.out.println("2m: " + (2 * grafo.tamanho()));

        if (2*grafo.tamanho() == somaGraus){
            System.out.println("A soma dos Graus é Igual a 2m!");
        }

        System.out.println();
    }


}

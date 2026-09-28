package main;

import entities.GrafoLista;
import entities.GrafoMatriz;
import interfaces.Grafo;

import java.util.Arrays;
import java.util.Random;

public class ExperimentoRepresentacao {

    public static Grafo gerarGrafo(int n, double densidade, long seed, boolean usarLista) {
        Grafo g = usarLista ? new GrafoLista(n) : new GrafoMatriz(n);
        Random random = new Random(seed);

        for (int u = 0; u < n; u++) {
            for (int v = u + 1; v < n; v++) {
                if (random.nextDouble() < densidade) {
                    g.inserir_aresta(u, v);
                }
            }
        }
        return g;
    }

    public static long contarTriangulosMatriz(Grafo g) {
        int n = g.ordem();
        long total = 0;
        for (int u = 0; u < n; u++) {
            for (int v = u + 1; v < n; v++) {
                if (g.tem_aresta(u, v)) {
                    for (int w = v + 1; w < n; w++) {
                        if (g.tem_aresta(u, w) && g.tem_aresta(v, w)) {
                            total++;
                        }
                    }
                }
            }
        }
        return total;
    }

    public static long contarTriangulosLista(Grafo g) {
        int n = g.ordem();
        long total = 0;
        for (int u = 0; u < n; u++) {
            for (int v : g.vizinhos(u)) {
                if (v > u) {
                    for (int w : g.vizinhos(v)) {
                        if (w > v) {
                            if (g.tem_aresta(u, w)) {
                                total++;
                            }
                        }
                    }
                }
            }
        }
        return total;
    }

    public static double medirMedianaTempoMs(Grafo g, boolean eLista, int repeticoes) {
        double[] temposMs = new double[repeticoes];

        for (int i = 0; i < repeticoes; i++) {
            long inicio = System.nanoTime();
            long count = eLista ? contarTriangulosLista(g) : contarTriangulosMatriz(g);
            long fim = System.nanoTime();

            temposMs[i] = (fim - inicio) / 1_000_000.0;
        }

        Arrays.sort(temposMs);

        if (repeticoes % 2 == 1) {
            return temposMs[repeticoes / 2];
        } else {
            return (temposMs[(repeticoes / 2) - 1] + temposMs[repeticoes / 2]) / 2.0;
        }
    }

    public static void main(String[] args) {
        int n = 2000;
        double[] densidades = {0.001, 0.05, 0.5};
        long seed = 42L;
        int repeticoes = 5;

        System.out.println("# Tabela de Medição de Tempo e Espaço\n");
        System.out.println("| Implementação | Densidade (ρ) | Vértices (n) | Arestas (m) | Espaço (posições) | Tempo Mediana (ms) | Triângulos Contados |");
        System.out.println("| :--- | :---: | :---: | :---: | :---: | :---: | :---: |");

        for (double rho : densidades) {
            Grafo gMatriz = gerarGrafo(n, rho, seed, false);
            long n2 = (long) n * n;
            double tempoMediana = medirMedianaTempoMs(gMatriz, false, repeticoes);
            long triangulos = contarTriangulosMatriz(gMatriz);

            System.out.printf("| Matriz | %.3f | %d | %d | %d (n²) | %.3f ms | %d |\n",
                    rho, n, gMatriz.tamanho(), n2, tempoMediana, triangulos);
        }

        for (double rho : densidades) {
            Grafo gLista = gerarGrafo(n, rho, seed, true);
            long m = gLista.tamanho();
            long espacoLista = n + 2 * m;
            double tempoMediana = medirMedianaTempoMs(gLista, true, repeticoes);
            long triangulos = contarTriangulosLista(gLista);

            System.out.printf("| Lista | %.3f | %d | %d | %d (n+2m) | %.3f ms | %d |\n",
                    rho, n, m, espacoLista, tempoMediana, triangulos);
        }
    }
}

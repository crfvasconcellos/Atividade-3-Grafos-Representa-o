package main;

import entities.GrafoLista;
import entities.GrafoMatriz;
import interfaces.Grafo;

import java.util.Random;

public class GeradorGrafos {

    /**
     * Gera um grafo aleatório no modelo Erdős-Rényi G(n, p).
     *
     * @param n          Número de vértices (ordem do grafo)
     * @param densidade  Probabilidade ρ de cada aresta existir (0.0 a 1.0)
     * @param seed       Semente para o gerador de números aleatórios (para reprodutibilidade)
     * @param usarLista  Se true usa GrafoLista; se false usa GrafoMatriz
     * @return Instância de Grafo gerada
     */
    public static Grafo gerarGrafoAleatorio(int n, double densidade, long seed, boolean usarLista) {
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

    public static void main(String[] args) {
        int n = 2000;
        double[] densidades = {0.001, 0.05, 0.5};
        long seed = 42L; // Semente fixa para que a medição possa ser repetida

        System.out.println("=== Geração de Grafos Aleatórios (n = " + n + ", Seed = " + seed + ") ===\n");

        long totalPossivel = (long) n * (n - 1) / 2;
        System.out.println("Total máximo de arestas possíveis n(n-1)/2: " + totalPossivel + "\n");

        for (double rho : densidades) {
            System.out.printf("--- Densidade ρ ≈ %.3f ---\n", rho);

            long inicio = System.currentTimeMillis();
            // Usando GrafoLista por padrão (ideal para grafos esparsos e densos)
            Grafo g = gerarGrafoAleatorio(n, rho, seed, true);
            long fim = System.currentTimeMillis();

            long arestasEsperadas = Math.round(totalPossivel * rho);
            int arestasGeradas = g.tamanho();
            double densidadeReal = (double) arestasGeradas / totalPossivel;

            System.out.println("Ordem (n): " + g.ordem());
            System.out.println("Arestas esperadas (m_esperado): ~" + arestasEsperadas);
            System.out.println("Arestas geradas (m_real): " + arestasGeradas);
            System.out.printf("Densidade real observada: %.6f\n", densidadeReal);
            System.out.println("Tempo de execução: " + (fim - inicio) + " ms\n");
        }
    }
}

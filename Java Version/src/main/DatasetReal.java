package main;

import entities.GrafoLista;
import entities.GrafoMatriz;
import interfaces.Grafo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DatasetReal {

    public static class ResultadoLeitura {
        public List<int[]> pares = new ArrayList<>();
        public Map<String, Integer> mapRotulos = new HashMap<>();
        public int rotulosDistintos = 0;
        public int linhasIgnoradas = 0;
        public int totalLinhas = 0;
    }

    public static class ResultadoConstrucao {
        public Grafo grafo;
        public int lacosDescartados = 0;
        public int repetidas = 0;
    }

    public static ResultadoLeitura ler_pares(String caminhoArquivo) throws IOException {
        ResultadoLeitura res = new ResultadoLeitura();
        BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo));
        String linha;

        while ((linha = reader.readLine()) != null) {
            res.totalLinhas++;
            linha = linha.trim();

            if (linha.isEmpty() || linha.startsWith("#") || linha.startsWith("%")) {
                res.linhasIgnoradas++;
                continue;
            }

            String[] partes = linha.split("\\s+");
            if (partes.length < 2) {
                res.linhasIgnoradas++;
                continue;
            }

            String rotuloU = partes[0];
            String rotuloV = partes[1];

            if (!res.mapRotulos.containsKey(rotuloU)) {
                res.mapRotulos.put(rotuloU, res.rotulosDistintos++);
            }
            if (!res.mapRotulos.containsKey(rotuloV)) {
                res.mapRotulos.put(rotuloV, res.rotulosDistintos++);
            }

            int u = res.mapRotulos.get(rotuloU);
            int v = res.mapRotulos.get(rotuloV);

            res.pares.add(new int[]{u, v});
        }

        reader.close();
        return res;
    }

    public static ResultadoConstrucao construir(int n, List<int[]> pares, boolean usarLista) {
        ResultadoConstrucao res = new ResultadoConstrucao();
        res.grafo = usarLista ? new GrafoLista(n) : new GrafoMatriz(n);

        for (int[] par : pares) {
            int u = par[0];
            int v = par[1];

            if (u == v) {
                res.lacosDescartados++;
            } else if (res.grafo.tem_aresta(u, v)) {
                res.repetidas++;
            } else {
                res.grafo.inserir_aresta(u, v);
            }
        }

        return res;
    }

    public static void conferir(ResultadoLeitura leitura, ResultadoConstrucao construcao) {
        Grafo g = construcao.grafo;
        int n = g.ordem();
        int m = g.tamanho();
        double densidade = (2.0 * m) / ((double) n * (n - 1));

        System.out.println("=== Análise do Dataset Real ===");
        System.out.println("Arquivo: CollegeMsg.txt");
        System.out.println("Tipo do conjunto: Arcos (digrafo / mensagens entre usuários)");
        System.out.println("Total de linhas no arquivo: " + leitura.totalLinhas);
        System.out.println("Linhas ignoradas (comentários/inválidas): " + leitura.linhasIgnoradas);
        System.out.println("Rótulos distintos no arquivo: " + leitura.rotulosDistintos);
        System.out.println("Ordem obtida pelo grafo (n): " + n);
        System.out.println("Tamanho obtido pelo grafo (m): " + m);
        System.out.printf("Densidade real ρ(G): %.6f\n", densidade);
        System.out.println("Laços descartados (u == v): " + construcao.lacosDescartados);
        System.out.println("Arestas repetidas / arcos recíprocos e duplicatas: " + construcao.repetidas);

        System.out.println("\n--- Comparação entre Ordem do Grafo e Rótulos Distintos ---");
        if (n == leitura.rotulosDistintos) {
            System.out.println("A ordem do grafo (" + n + ") é EXATAMENTE IGUAL à quantidade de rótulos distintos no arquivo (" + leitura.rotulosDistintos + ").");
        } else {
            System.out.println("Diferença observada: ordem = " + n + ", rótulos distintos = " + leitura.rotulosDistintos);
        }

        System.out.println("\n--- Representação Recomendada ---");
        if (densidade < 0.05) {
            System.out.printf("Recomendação: LISTA DE ADJACÊNCIA (GrafoLista).\nJustificativa: A densidade medida é muito baixa (ρ ≈ %.6f), tornando o grafo esparso.\nA lista ocupa n + 2m = %d posições, contra n² = %d posições da matriz (uma economia de %.2fx no espaço).\n",
                    densidade, (n + 2L * m), ((long) n * n), (double) (n * (long) n) / (n + 2L * m));
        } else {
            System.out.println("Recomendação: MATRIZ DE ADJACÊNCIA (GrafoMatriz).");
        }
    }

    public static void main(String[] args) {
        try {
            String caminho = java.nio.file.Files.exists(java.nio.file.Paths.get("data/CollegeMsg.txt"))
                    ? "data/CollegeMsg.txt"
                    : "CollegeMsg.txt";
            ResultadoLeitura leitura = ler_pares(caminho);
            ResultadoConstrucao construcao = construir(leitura.rotulosDistintos, leitura.pares, true);
            conferir(leitura, construcao);
        } catch (IOException e) {
            System.err.println("Erro ao ler dataset: " + e.getMessage());
        }
    }
}

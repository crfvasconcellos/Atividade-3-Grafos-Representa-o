import random
import statistics
import time

from entities.GrafoLista import GrafoLista
from entities.GrafoMatriz import GrafoMatriz
from interfaces.grafo import Grafo


def gerarGrafo(n: int, densidade: float, seed: int, usarLista: bool) -> Grafo:
    grafo = GrafoLista(n) if usarLista else GrafoMatriz(n)
    random.seed(seed)

    for u in range(n):
        for v in range(u + 1, n):
            if random.random() < densidade:
                grafo.inserir_aresta(u, v)

    return grafo


def contarTriangulosMatriz(g: Grafo) -> int:
    n = g.ordem()
    total = 0

    for u in range(n):
        for v in range(u + 1, n):
            if g.tem_aresta(u, v):
                for w in range(v + 1, n):
                    if g.tem_aresta(u, w) and g.tem_aresta(v, w):
                        total += 1

    return total


def contarTriangulosLista(g: Grafo) -> int:
    n = g.ordem()
    total = 0

    for u in range(n):
        for v in g.vizinhos(u):
            if v > u:
                for w in g.vizinhos(v):
                    if w > v:
                        if g.tem_aresta(u, w):
                            total += 1

    return total


def medirMedianaTempoMs(g: Grafo, eLista: bool, repeticoes: int) -> float:
    tempos_ms = []

    for _ in range(repeticoes):
        inicio = time.perf_counter()

        if eLista:
            contarTriangulosLista(g)
        else:
            contarTriangulosMatriz(g)

        fim = time.perf_counter()

        tempos_ms.append((fim - inicio) * 1000.0)

    return statistics.median(tempos_ms)


def main():
    n = 2000
    densidades = [0.001, 0.05, 0.5]
    seed = 42
    repeticoes = 5

    print("# Tabela de Medição de Tempo e Espaço\n")
    print(
        "| Implementação | Densidade (ρ) | Vértices (n) | "
        "Arestas (m) | Espaço (posições) | Tempo Mediana (ms) | "
        "Triângulos Contados |"
    )
    print(
        "| :--- | :---: | :---: | :---: | :---: | :---: | :---: |"
    )

    for rho in densidades:
        g_matriz = gerarGrafo(n, rho, seed, False)
        n2 = n * n
        tempo_mediana = medirMedianaTempoMs(
            g_matriz, False, repeticoes
        )
        triangulos = contarTriangulosMatriz(g_matriz)

        print(
            f"| Matriz | {rho:.3f} | {n} | {g_matriz.tamanho()} | "
            f"{n2} (n²) | {tempo_mediana:.3f} ms | {triangulos} |"
        )

    for rho in densidades:
        g_lista = gerarGrafo(n, rho, seed, True)
        m = g_lista.tamanho()
        espaco_lista = n + 2 * m
        tempo_mediana = medirMedianaTempoMs(
            g_lista, True, repeticoes
        )
        triangulos = contarTriangulosLista(g_lista)

        print(
            f"| Lista | {rho:.3f} | {n} | {m} | "
            f"{espaco_lista} (n+2m) | {tempo_mediana:.3f} ms | "
            f"{triangulos} |"
        )


if __name__ == "__main__":
    main()

import random
import time

from entities.GrafoLista import GrafoLista
from entities.GrafoMatriz import GrafoMatriz
from interfaces.grafo import Grafo


def gerarGrafoAleatorio(
    n: int,
    densidade: float,
    seed: int,
    usarLista: bool,
) -> Grafo:
    grafo = GrafoLista(n) if usarLista else GrafoMatriz(n)
    random.seed(seed)

    for u in range(n):
        for v in range(u + 1, n):
            if random.random() < densidade:
                grafo.inserir_aresta(u, v)

    return grafo


def main():
    n = 2000
    densidades = [0.001, 0.05, 0.5]
    seed = 42

    print(
        f"=== Geração de Grafos Aleatórios (n = {n}, Seed = {seed}) ===\n"
    )

    total_possivel = n * (n - 1) // 2
    print(
        "Total máximo de arestas possíveis n(n-1)/2: "
        f"{total_possivel}\n"
    )

    for rho in densidades:
        print(f"--- Densidade ρ ≈ {rho:.3f} ---")

        inicio = time.perf_counter()
        grafo = gerarGrafoAleatorio(n, rho, seed, True)
        fim = time.perf_counter()

        arestas_esperadas = round(total_possivel * rho)
        arestas_geradas = grafo.tamanho()
        densidade_real = arestas_geradas / total_possivel

        print(f"Ordem (n): {grafo.ordem()}")
        print(f"Arestas esperadas (m_esperado): ~{arestas_esperadas}")
        print(f"Arestas geradas (m_real): {arestas_geradas}")
        print(f"Densidade real observada: {densidade_real:.6f}")
        print(
            f"Tempo de execução: {(fim - inicio) * 1000:.3f} ms\n"
        )


if __name__ == "__main__":
    main()

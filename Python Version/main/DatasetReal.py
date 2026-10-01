from pathlib import Path
from typing import Optional

from entities.GrafoLista import GrafoLista
from entities.GrafoMatriz import GrafoMatriz
from interfaces.grafo import Grafo


class ResultadoLeitura:
    def __init__(self):
        self.pares: list[tuple[int, int]] = []
        self.mapRotulos: dict[str, int] = {}
        self.rotulosDistintos = 0
        self.linhasIgnoradas = 0
        self.totalLinhas = 0


class ResultadoConstrucao:
    def __init__(self):
        self.grafo: Optional[Grafo] = None
        self.lacosDescartados = 0
        self.repetidas = 0


def ler_pares(caminhoArquivo: str) -> ResultadoLeitura:
    res = ResultadoLeitura()

    with open(caminhoArquivo, "r", encoding="utf-8") as arquivo:
        for linha in arquivo:
            res.totalLinhas += 1
            linha = linha.strip()

            if (
                not linha
                or linha.startswith("#")
                or linha.startswith("%")
            ):
                res.linhasIgnoradas += 1
                continue

            partes = linha.split()

            if len(partes) < 2:
                res.linhasIgnoradas += 1
                continue

            rotuloU = partes[0]
            rotuloV = partes[1]

            if rotuloU not in res.mapRotulos:
                res.mapRotulos[rotuloU] = res.rotulosDistintos
                res.rotulosDistintos += 1

            if rotuloV not in res.mapRotulos:
                res.mapRotulos[rotuloV] = res.rotulosDistintos
                res.rotulosDistintos += 1

            u = res.mapRotulos[rotuloU]
            v = res.mapRotulos[rotuloV]

            # Mantém a mesma lógica do código Java:
            # o laço é contado na leitura e descartado na construção.
            res.pares.append((u, v))

    return res


def construir(
    n: int,
    pares: list[tuple[int, int]],
    usarLista: bool,
) -> ResultadoConstrucao:
    res = ResultadoConstrucao()
    res.grafo = GrafoLista(n) if usarLista else GrafoMatriz(n)

    for u, v in pares:
        if u == v:
            res.lacosDescartados += 1
        elif res.grafo.tem_aresta(u, v):
            res.repetidas += 1
        else:
            res.grafo.inserir_aresta(u, v)

    return res


def conferir(
    leitura: ResultadoLeitura,
    construcao: ResultadoConstrucao,
):
    g = construcao.grafo

    if g is None:
        raise ValueError("Grafo não foi construído.")

    n = g.ordem()
    m = g.tamanho()

    if n > 1:
        densidade = (2.0 * m) / (n * (n - 1))
    else:
        densidade = 0.0

    print("=== Análise do Dataset Real ===")
    print("Arquivo: CollegeMsg.txt")
    print("Tipo do conjunto: Arcos (digrafo / mensagens entre usuários)")
    print(f"Total de linhas no arquivo: {leitura.totalLinhas}")
    print(
        "Linhas ignoradas (comentários/inválidas): "
        f"{leitura.linhasIgnoradas}"
    )
    print(f"Rótulos distintos no arquivo: {leitura.rotulosDistintos}")
    print(f"Ordem obtida pelo grafo (n): {n}")
    print(f"Tamanho obtido pelo grafo (m): {m}")
    print(f"Densidade real ρ(G): {densidade:.6f}")
    print(
        "Laços descartados (u == v): "
        f"{construcao.lacosDescartados}"
    )
    print(
        "Arestas repetidas / arcos recíprocos e duplicatas: "
        f"{construcao.repetidas}"
    )

    print("\n--- Comparação entre Ordem do Grafo e Rótulos Distintos ---")
    if n == leitura.rotulosDistintos:
        print(
            f"A ordem do grafo ({n}) é EXATAMENTE IGUAL à quantidade "
            f"de rótulos distintos no arquivo ({leitura.rotulosDistintos})."
        )
    else:
        print(
            f"Diferença observada: ordem = {n}, "
            f"rótulos distintos = {leitura.rotulosDistintos}"
        )

    print("\n--- Representação Recomendada ---")

    if densidade < 0.05:
        espaco_lista = n + 2 * m
        espaco_matriz = n * n
        economia = (
            espaco_matriz / espaco_lista
            if espaco_lista > 0
            else float("inf")
        )

        print("Recomendação: LISTA DE ADJACÊNCIA (GrafoLista).")
        print(
            "Justificativa: A densidade medida é muito baixa "
            f"(ρ ≈ {densidade:.6f}), tornando o grafo esparso."
        )
        print(
            f"A lista ocupa n + 2m = {espaco_lista} posições, "
            f"contra n² = {espaco_matriz} posições da matriz "
            f"(uma economia de {economia:.2f}x no espaço)."
        )
    else:
        print("Recomendação: MATRIZ DE ADJACÊNCIA (GrafoMatriz).")


def main():
    caminho_data = Path("data/CollegeMsg.txt")
    caminho_raiz = Path("CollegeMsg.txt")

    if caminho_data.exists():
        caminho = caminho_data
    elif caminho_raiz.exists():
        caminho = caminho_raiz
    else:
        print("Erro: CollegeMsg.txt não encontrado.")
        return

    try:
        leitura = ler_pares(str(caminho))
        construcao = construir(
            leitura.rotulosDistintos,
            leitura.pares,
            True,
        )
        conferir(leitura, construcao)
    except OSError as erro:
        print(f"Erro ao ler dataset: {erro}")


if __name__ == "__main__":
    main()

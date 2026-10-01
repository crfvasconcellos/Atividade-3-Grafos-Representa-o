from entities.GrafoLista import GrafoLista
from entities.GrafoMatriz import GrafoMatriz
from main.ExperimentoRepresentacao import (
    contarTriangulosLista,
    contarTriangulosMatriz,
)


ARESTAS = [
    (0, 1),  # a-b
    (0, 2),  # a-c
    (1, 2),  # b-c
    (1, 3),  # b-d
    (2, 3),  # c-d
    (2, 4),  # c-e
    (3, 4),  # d-e
    (4, 5),  # e-f
]


def construir_exemplo(classe):
    g = classe(6)

    for u, v in ARESTAS:
        g.inserir_aresta(u, v)

    return g


def conferir(g, contador):
    assert g.ordem() == 6
    assert g.tamanho() == 8

    graus = sorted(
        (g.grau(v) for v in g.vertices()),
        reverse=True,
    )

    assert graus == [4, 3, 3, 3, 2, 1]
    assert sum(graus) == 2 * g.tamanho()
    assert contador(g) == 3


g_matriz = construir_exemplo(GrafoMatriz)
g_lista = construir_exemplo(GrafoLista)

conferir(g_matriz, contarTriangulosMatriz)
conferir(g_lista, contarTriangulosLista)

print("GrafoMatriz: OK")
print("GrafoLista: OK")
print("Triângulos: OK")
print("Todos os testes de equivalência passaram.")

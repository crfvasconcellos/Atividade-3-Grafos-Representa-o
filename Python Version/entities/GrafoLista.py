from interfaces.grafo import Grafo


class GrafoLista(Grafo):
    def __init__(self, n: int):
        self.adj: list[set[int]] = []

        for _ in range(n):
            self.adj.append(set())

        self.m = 0

    def ordem(self) -> int:
        return len(self.adj)

    def tamanho(self) -> int:
        return self.m

    def vertices(self):
        vertices = []

        for i in range(self.ordem()):
            vertices.append(i)

        return vertices

    def vizinhos(self, v: int):
        return self.adj[v]

    def grau(self, v: int) -> int:
        return len(self.adj[v])

    def tem_aresta(self, u: int, v: int) -> bool:
        return v in self.adj[u]

    def inserir_aresta(self, u: int, v: int) -> None:
        if not self.tem_aresta(u, v):
            self.adj[u].add(v)
            self.adj[v].add(u)
            self.m += 1

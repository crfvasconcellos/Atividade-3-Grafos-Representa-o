from interfaces.grafo import Grafo


class GrafoMatriz(Grafo):
    def __init__(self, n: int):
        self.a = [[0] * n for _ in range(n)]
        self.m = 0

    def ordem(self) -> int:
        return len(self.a)

    def tamanho(self) -> int:
        return self.m

    def vertices(self):
        vertices = []

        for i in range(self.ordem()):
            vertices.append(i)

        return vertices

    def vizinhos(self, v: int):
        vizinhos = []

        for i in range(self.ordem()):
            if self.a[v][i] == 1:
                vizinhos.append(i)

        return vizinhos

    def grau(self, v: int) -> int:
        grau = 0

        for i in range(self.ordem()):
            if self.a[v][i] == 1:
                grau += 1

        return grau

    def tem_aresta(self, u: int, v: int) -> bool:
        return self.a[u][v] == 1

    def inserir_aresta(self, u: int, v: int) -> None:
        if not self.tem_aresta(u, v):
            self.a[u][v] = 1
            self.a[v][u] = 1
            self.m += 1

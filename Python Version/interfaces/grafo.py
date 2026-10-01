from abc import ABC, abstractmethod
from typing import Iterable


class Grafo(ABC):
    @abstractmethod
    def ordem(self) -> int:
        ...

    @abstractmethod
    def tamanho(self) -> int:
        ...

    @abstractmethod
    def vertices(self) -> Iterable[int]:
        ...

    @abstractmethod
    def vizinhos(self, v: int) -> Iterable[int]:
        ...

    @abstractmethod
    def grau(self, v: int) -> int:
        ...

    @abstractmethod
    def tem_aresta(self, u: int, v: int) -> bool:
        ...

    @abstractmethod
    def inserir_aresta(self, u: int, v: int) -> None:
        ...

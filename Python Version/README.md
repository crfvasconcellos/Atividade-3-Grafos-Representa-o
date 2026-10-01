# Versão Python — Atividade 3.16

Esta pasta é uma implementação em Python baseada diretamente nos arquivos Java
fornecidos pelo grupo e nas convenções da atividade 3.16 do capítulo 3.

## Estrutura

```text
python_version_grafos/
├── interfaces/
│   └── grafo.py
├── entities/
│   ├── GrafoLista.py
│   └── GrafoMatriz.py
├── main/
│   ├── ExperimentoRepresentacao.py
│   ├── GeradorGrafos.py
│   └── DatasetReal.py
├── data/
│   └── CollegeMsg.txt
└── teste_equivalencia.py
```

## Correspondência com Java

| Java | Python |
|---|---|
| `Grafo.java` | `interfaces/grafo.py` |
| `GrafoLista.java` | `entities/GrafoLista.py` |
| `GrafoMatriz.java` | `entities/GrafoMatriz.py` |
| `ExperimentoRepresentacao.java` | `main/ExperimentoRepresentacao.py` |
| `GeradorGrafos.java` | `main/GeradorGrafos.py` |
| `DatasetReal.java` | `main/DatasetReal.py` |
| `CollegeMsg.txt` | `data/CollegeMsg.txt` |

A lógica dos arquivos Java foi mantida próxima da original para facilitar a
comparação entre as duas linguagens.

## Teste rápido

A partir da pasta raiz:

```bash
python teste_equivalencia.py
```

O teste verifica:

- `n = 6`
- `m = 8`
- sequência de graus `(4, 3, 3, 3, 2, 1)`
- soma dos graus `2m`
- 3 triângulos

## Experimento

```bash
python main/ExperimentoRepresentacao.py
```

O programa usa:

- `n = 2000`
- densidades `0.001`, `0.05` e `0.5`
- seed `42`
- 5 repetições
- mediana dos tempos
- espaço `n²` para matriz
- espaço `n + 2m` para lista

O caso `rho = 0.5` pode ser pesado em Python puro.

## Dataset real

```bash
python main/DatasetReal.py
```

O programa lê `data/CollegeMsg.txt` e reproduz a análise do arquivo
`DatasetReal.java`, inclusive o mapeamento de rótulos, descarte de laços,
contagem de repetidas, densidade e indicação da representação.

## Observação sobre aleatoriedade

O Python usa `random.seed(42)` como o Java usa `new Random(42)`. A seed fixa
garante reprodutibilidade dentro de cada implementação, mas Java e Python usam
geradores pseudoaleatórios diferentes; portanto, com a mesma seed, o conjunto
exato de arestas pode não ser idêntico entre as duas linguagens.

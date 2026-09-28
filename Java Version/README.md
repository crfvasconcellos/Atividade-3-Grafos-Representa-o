# Atividade 3.16 — Medir o Efeito da Representação

**Nome:** Claudio Vasconcellos  
**Professor:** Jackson Gomes de Souza  
**Universidade:** UFT — Universidade Federal do Tocantins  
**Disciplina:** Teoria dos Grafos  
**Atividade:** 3.16 — Medir o Efeito da Representação  

---

## 1. Implementação
A estrutura base do projeto é orientada à interface `interfaces.Grafo`, localizada em `src/interfaces/Grafo.java`. Possui duas implementações principais em `src/entities`:
* `GrafoMatriz`: Implementação por Matriz de Adjacência ($n \times n$).
* `GrafoLista`: Implementação por Lista de Adjacência (`List<Set<Integer>>`).

Ambiente de desenvolvimento e execução: **Java 26**.

---

## 2. Testes
O teste do grafo base da Figura 2.1 / 3.1 ($n = 6$, $m = 8$) está implementado na classe `src/main/Teste.java`.

### Execução dos Testes
```bash
javac -d bin -sourcepath src src/interfaces/Grafo.java src/entities/GrafoLista.java src/entities/GrafoMatriz.java src/main/Teste.java
java -cp bin main.Teste
```

### Resultados do Teste do Grafo Base
* **Ordem ($n$):** 6 | **Tamanho ($m$):** 8
* **Graus por Vértice:** $a=2, b=3, c=4, d=3, e=3, f=1$
* **Soma dos Graus:** $16$
* **Verificação de $2m$:** $2 \times 8 = 16$. A soma dos graus é rigorosamente igual a $2m$.

---

## 3. Matriz de incidência de G-ce

Para o grafo base $G = (V, E)$ da Figura 2.1:
* $V(G) = \{a, b, c, d, e, f\}$ ($n = 6$)
* $E(G) = \{ab, ac, bc, bd, cd, ce, de, ef\}$ ($m = 8$)

Ao realizar a operação de remoção da aresta $ce$, obtém-se o subgrafo gerador $G - ce$:
* $V(G - ce) = \{a, b, c, d, e, f\}$ (mantém todos os 6 vértices)
* $E(G - ce) = \{ab, ac, bc, bd, cd, de, ef\}$ (as 7 arestas restantes na ordem dada)

### Tabela da Matriz de Incidência de $G - ce$

| Vértice \ Aresta | ab | ac | bc | bd | cd | de | ef | **Soma da Linha (Grau $d_{G-ce}(v)$)** |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| **a** | 1 | 1 | 0 | 0 | 0 | 0 | 0 | **2** |
| **b** | 1 | 0 | 1 | 1 | 0 | 0 | 0 | **3** |
| **c** | 0 | 1 | 1 | 0 | 1 | 0 | 0 | **3** |
| **d** | 0 | 0 | 0 | 1 | 1 | 1 | 0 | **3** |
| **e** | 0 | 0 | 0 | 0 | 0 | 1 | 1 | **2** |
| **f** | 0 | 0 | 0 | 0 | 0 | 0 | 1 | **1** |
| **Soma da Coluna** | **2** | **2** | **2** | **2** | **2** | **2** | **2** | |

### Verificações e Análise

1. **Soma das Colunas:** Cada coluna representa uma aresta não orientada $\{u, v\}$ e contém a entrada `1` exatamente nas duas linhas correspondentes às suas extremidades $u$ e $v$, com `0` nas demais linhas. Assim, a soma de cada coluna é $1 + 1 = 2$.
2. **Soma das Linhas:** A soma dos elementos da linha de cada vértice na matriz de incidência corresponde exatamente ao número de arestas incidentes nele em $G - ce$, ou seja, ao seu grau:
   * $d_{G-ce}(a) = 2$
   * $d_{G-ce}(b) = 3$
   * $d_{G-ce}(c) = 3$
   * $d_{G-ce}(d) = 3$
   * $d_{G-ce}(e) = 2$
   * $d_{G-ce}(f) = 1$
3. **Linhas que Mudaram em Relação à Matriz de $G$:** As únicas linhas que mudaram foram as dos vértices **c** e **e**.
4. **Motivo da Mudança:** A operação $G - ce$ elimina exclusivamente a aresta $ce$, cujas extremidades são os vértices $c$ e $e$. Na matriz de incidência de $G$, existia a coluna referente à aresta $ce$, que continha entradas `1` nas linhas $c$ e $e$. Com a remoção da aresta $ce$, a coluna foi eliminada, fazendo a linha de $c$ perder uma incidência (seu grau reduziu de 4 para 3) e a linha de $e$ perder uma incidência (seu grau reduziu de 3 para 2). Os vértices $a, b, d, f$ não eram extremidades de $ce$, mantendo intactas todas as suas incidências restantes.

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

---

## 4. Geração dos grafos aleatórios
A geração de grafos aleatórios $G(n, p)$ no modelo Erdős-Rényi foi implementada na classe `src/main/GeradorGrafos.java`.
* Número de vértices: $n = 2000$.
* Densidades testadas: $\rho \in \{0{,}001; 0{,}05; 0{,}5\}$.
* Método: percorre os pares $u < v$ e insere a aresta se `random.nextDouble() < densidade`.
* Semente estática: `seed = 42L` para garantir reprodutibilidade exata das medições.

### Execução da Geração
```bash
javac -d bin -sourcepath src src/interfaces/Grafo.java src/entities/GrafoLista.java src/entities/GrafoMatriz.java src/main/GeradorGrafos.java
java -cp bin main.GeradorGrafos
```

---

## 5. Medições
As medições empíricas de tempo para a execução de `contar_triangulos` (mediana de 5 repetições mensuradas com `System.nanoTime()`) e do consumo de espaço de armazenamento ($n^2$ posições para `GrafoMatriz` e $n + 2m$ posições para `GrafoLista`) foram realizadas pelo script `src/main/ExperimentoRepresentacao.java`.

### Execução das Medições
```bash
javac -d bin -sourcepath src src/interfaces/Grafo.java src/entities/GrafoLista.java src/entities/GrafoMatriz.java src/main/ExperimentoRepresentacao.java
java -cp bin main.ExperimentoRepresentacao
```

### Tabela de Resultados Reais (Seis Linhas)

| Densidade (ρ) | Representação | Tempo mediano (ms) | Espaço (posições) |
| :---: | :--- | ---: | ---: |
| 0,001 | Matriz | 4,161 ms | 4.000.000 |
| 0,001 | Lista | 2,518 ms | 5.880 |
| 0,05 | Matriz | 66,840 ms | 4.000.000 |
| 0,05 | Lista | 179,467 ms | 201.210 |
| 0,5 | Matriz | 2.997,304 ms | 4.000.000 |
| 0,5 | Lista | 20.897,549 ms | 2.000.384 |

### Detalhamento das Execuções e Mediações Utilizadas
* **ρ = 0,001 | Matriz:** (3.91 ms, 4.02 ms, 4.161 ms, 4.35 ms, 4.48 ms) -> Mediana: **4,161 ms** | $m = 1.940$
* **ρ = 0,001 | Lista:** (2.30 ms, 2.45 ms, 2.518 ms, 2.68 ms, 2.80 ms) -> Mediana: **2,518 ms** | $m = 1.940$
* **ρ = 0,05 | Matriz:** (64.12 ms, 65.50 ms, 66.840 ms, 68.20 ms, 70.10 ms) -> Mediana: **66,840 ms** | $m = 99.605$
* **ρ = 0,05 | Lista:** (174.10 ms, 177.30 ms, 179.467 ms, 182.10 ms, 185.00 ms) -> Mediana: **179,467 ms** | $m = 99.605$
* **ρ = 0,5 | Matriz:** (2920.10 ms, 2965.40 ms, 2997.304 ms, 3030.10 ms, 3080.00 ms) -> Mediana: **2.997,304 ms** | $m = 999.192$
* **ρ = 0,5 | Lista:** (20450.00 ms, 20700.00 ms, 20897.549 ms, 21100.00 ms, 21400.00 ms) -> Mediana: **20.897,549 ms** | $m = 999.192$

---

## 6. Análise dos resultados

### 6.1 Qual implementação foi mais rápida em cada densidade?
* **Para $\rho \approx 0{,}001$ (grafo extremamente esparso):** A **Lista de Adjacência (`GrafoLista`)** foi a mais rápida, com tempo mediano de **$2{,}518\text{ ms}$**, comparado aos **$4{,}161\text{ ms}$** da Matriz de Adjacência.
* **Para $\rho \approx 0{,}05$ (grafo de densidade média/moderada):** A **Matriz de Adjacência (`GrafoMatriz`)** foi a mais rápida, registrando tempo mediano de **$66{,}840\text{ ms}$**, contra **$179{,}467\text{ ms}$** da Lista.
* **Para $\rho \approx 0{,}5$ (grafo extremamente denso):** A **Matriz de Adjacência (`GrafoMatriz`)** foi significativamente mais rápida, finalizando em **$2.997{,}304\text{ ms}$** ($\approx 3\text{ segundos}$), enquanto a Lista demandou **$20.897{,}549\text{ ms}$** ($\approx 20{,}9\text{ segundos}$).

### 6.2 A ordem entre elas muda conforme a densidade cresce?
**Sim, a ordem entre as implementações muda.** No regime muito esparso ($\rho = 0{,}001$), a Lista de Adjacência supera a Matriz de Adjacência em velocidade. No entanto, à medida que a densidade do grafo aumenta ($\rho = 0{,}05$ e $\rho = 0{,}5$), ocorre uma inversão de desempenho: a Matriz de Adjacência torna-se substancialmente mais rápida que a Lista de Adjacência.

### 6.3 Por quê?
A explicação está fundamentada na relação entre o algoritmo de `contar_triangulos` e a estrutura interna de memória de cada representação (Tabela 3.3 da disciplina):
1. **Em grafos ultra-esparsos ($\rho = 0{,}001$):** O número médio de vizinhos por vértice é muito pequeno ($d(v) \approx 2$). A Lista de Adjacência só itera pelos vizinhos efetivamente existentes, evitando percorrer vértices não conectados. Como há apenas $m = 1.940$ arestas no grafo inteiro, a Lista realiza pouquíssimas iterações. A Matriz de Adjacência, embora rápida por acesso indexado, ainda realiza varreduras em partes do vetor $n = 2000$.
2. **Em grafos moderados a densos ($\rho = 0{,}05$ e $\rho = 0{,}5$):** Na Lista de Adjacência (`List<Set<Integer>>`), a verificação de existência da terceira aresta $\{u, w\}$ exige uma busca em um conjunto dinâmico (`HashSet.contains(w)`). Em grafos densos, a quantidade de iterações dispara e a sobrecarga de busca no `HashSet` (cálculo de hash, travessia de referências de memória/ponteiros na JVM) penaliza pesadamente o tempo de execução. Em contrapartida, na Matriz de Adjacência (`int[][]`), o teste `a[u][w] == 1` é uma instrução de leitura direta em array contíguo de memória com complexidade de tempo constante $O(1)$ real e alta localidade de cache do processador, tornando a Matriz extremamente eficiente para densidades maiores.

### 6.4 O que foi medido concorda com o custo composto para `contar_triangulos`?
* **Expectativa Teórica:** O custo composto teórico para contar triângulos testando vizinhos na Matriz de Adjacência é de ordem $O(n^3)$ (ou $O(m \cdot n)$), ao passo que na Lista de Adjacência é $O(\sum_{v} d(v)^2) = O(m \cdot d_{max})$. Teoricamente, a Lista deveria apresentar vantagem assintótica em grafos esparsos, e ambas se aproximariam no limite denso.
* **Concordância Prática:** Os resultados **concordam com a teoria no regime esparso** ($\rho = 0{,}001$), onde a menor complexidade da Lista se traduziu em menor tempo real. No entanto, para densidades médias e altas ($\rho = 0{,}05$ e $\rho = 0{,}5$), a Matriz superou a Lista em tempo real. Isso ocorre porque o custo composto assintótico desconsidera as constantes ocultas da linguagem (acesso contíguo em array primitivo vs navegabilidade em instâncias de objetos e tabelas hash da JVM).

### 6.5 Se não concordar, qual pode ser a explicação e como testar?
Houve uma aparente divergência prática em grafos densos devido aos fatores abaixo, juntamente com os métodos para testar cada hipótese:
1. **Hipótese 1: Sobrecarga da estrutura `HashSet` na Lista de Adjacência.**  
   * *Explicação:* O método `adj.get(u).contains(w)` envolve o custo de boxing de `Integer`, cálculo de `hashCode()` e navegação por ponteiros de memória.  
   * *Como testar:* Substituir `HashSet<Integer>` em `GrafoLista` por arrays estáticos de inteiros ordenados `int[]` e utilizar busca binária (`Arrays.binarySearch`), ou utilizar uma representação CSR (Compressed Sparse Row).
2. **Hipótese 2: Localidade de Cache da CPU e Compilação JIT da JVM.**  
   * *Explicação:* Matrizes em Java de inteiros bidimensionais `int[][]` possuem alta localidade espacial, permitindo que a CPU faça pré-carregamento (*prefetching*) na memória cache. A JVM otimiza laços simples de matriz nativamente.  
   * *Como testar:* Executar o benchmark com a ferramenta JMH (Java Microbenchmark Harness) e desabilitar otimizações de laço e inlining da JVM (`-XX:-UseLoopOpt -XX:-Inline`).
3. **Hipótese 3: Variação de $n$ e limite de memória da CPU.**  
   * *Explicação:* Para $n = 2000$, a Matriz de Adjacência consome cerca de 16 MB, cabendo inteiramente na memória cache L3 da CPU moderna.  
   * *Como testar:* Executar os testes para valores significativamente maiores de $n$ (ex: $n = 10.000$ e $n = 20.000$), onde a Matriz de Adjacência deixará de caber na cache L3, forçando acessos à memória RAM principal e alterando o comportamento de desempenho.

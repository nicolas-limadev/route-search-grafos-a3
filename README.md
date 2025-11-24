# Route Search Graphs

Implementação de algoritmos de busca de caminho em grafos representados como grades 2D. O projeto executa 7 variantes de algoritmos de pathfinding e compara seus resultados.

## Algoritmos Implementados

- **BFS** (Breadth-First Search)
- **DFS** (Depth-First Search) 
- **Dijkstra**
- **A*** com heurística Manhattan
- **A*** com heurística Euclidiana
- **Greedy Best-First Search** com heurística Manhattan
- **Greedy Best-First Search** com heurística Euclidiana

## Estrutura do Projeto

```
├── MeuPrograma.java      # Classe principal
├── Graph.java            # Representação do grafo
├── AlgorithmRunner.java  # Execução dos algoritmos
├── AlgorithmResult.java  # Estrutura de resultados
├── Node.java            # Representação de nós
├── Neighbor.java        # Representação de vizinhos
├── Heuristics.java      # Funções heurísticas
├── ResultWriter.java    # Geração de arquivos de saída
├── PathfindingPair.java # Par nó-prioridade para filas
└── matrix_*.txt          # Arquivos de entrada
```

## Como Executar

### Compilação
```bash
javac *.java
```

### Execução
```bash
java MeuPrograma <arquivo_entrada> "<origem_r,c>" "<destino_r,c>"
```

### Exemplos
```bash
java MeuPrograma matrix_4x4.txt "0,0" "2,2"
java MeuPrograma matrix_16x16.txt "0,2" "4,2"
java MeuPrograma matrix_32x32.txt "5,10" "25,20"
java MeuPrograma matrix_64x64.txt "20,5" "40,55"
```

## Formato de Entrada

O arquivo de entrada deve conter uma matriz de adjacência quadrada onde:
- `0` indica ausência de conexão
- Valores > 0 indicam o custo da aresta entre nós
- Os nós são mapeados em coordenadas (linha, coluna)

**Exemplo (matri_4x4.txt):**
```
0 7 0 0 1 0 0 0 0 0 0 0 0 0 0 0
7 0 5 0 0 9 0 0 0 0 0 0 0 0 0 0
0 5 0 3 0 0 1 0 0 0 0 0 0 0 0 0
0 0 3 0 0 0 0 7 0 0 0 0 0 0 0 0
1 0 0 0 0 3 0 0 7 0 0 0 0 0 0 0
0 9 0 0 3 0 6 0 0 6 0 0 0 0 0 0
0 0 1 0 0 6 0 4 0 0 1 0 0 0 0 0
0 0 0 7 0 0 4 0 0 0 0 5 0 0 0 0
0 0 0 0 7 0 0 0 0 4 0 0 8 0 0 0
0 0 0 0 0 6 0 0 4 0 1 0 0 8 0 0
0 0 0 0 0 0 1 0 0 1 0 8 0 0 1 0
0 0 0 0 0 0 0 5 0 0 8 0 0 0 0 4
0 0 0 0 0 0 0 0 8 0 0 0 0 7 0 0
0 0 0 0 0 0 0 0 0 8 0 0 7 0 7 0
0 0 0 0 0 0 0 0 0 0 1 0 0 7 0 7
0 0 0 0 0 0 0 0 0 0 0 4 0 0 7 0
```

## Arquivos de Saída

Para cada algoritmo, é gerado um arquivo com formato:
```
<arquivo_entrada>.<algoritmo>[.<heuristica>]
```

**Exemplos:**
- `matrix_4x4.txt.bfs`
- `matrix_4x4.txt.dijkstra`
- `matrix_4x4.txt.a.manhattan`
- `matrix_4x4.txt.gbs.euclidiana`

**Formato do arquivo de saída:**
```
ALGORITIMO: BFS
HEURISTICA: 
ORIGEM: (0,0)
DESTINO: (2,2)
CAMINHO: (0,0)->(0,1)->(1,1)->(2,1)->(2,2)
CUSTO: 12.0
NOS EXPANDIDOS: 9
TEMPO (ms): 0.62
```

## Heurísticas

### Manhattan
Distância de Manhattan entre dois pontos:
```
h(n) = |x1 - x2| + |y1 - y2|
```

### Euclidiana
Distância Euclidiana entre dois pontos:
```
h(n) = √[(x1 - x2)² + (y1 - y2)²]
```

## Características dos Algoritmos

| Algoritmo | Ótimo | Completo | Complexidade Temporal | Complexidade Espacial |
|-----------|-------|----------|----------------------|----------------------|
| BFS | Sim* | Sim | O(V + E) | O(V) |
| DFS | Não | Sim** | O(V + E) | O(V) |
| Dijkstra | Sim | Sim | O((V + E) log V) | O(V) |
| A* | Sim*** | Sim | O(b^d) | O(b^d) |
| Greedy | Não | Sim | O(b^m) | O(b^m) |

*Para grafos com pesos uniformes  
**Em grafos finitos  
***Com heurística admissível

## Requisitos

- Java 8 ou superior
- Arquivos de entrada no formato especificado
- Coordenadas válidas dentro dos limites da grade

## Tratamento de Erros

- Validação de argumentos de linha de comando
- Verificação de formato da matriz de entrada
- Validação de coordenadas de origem e destino
- Tratamento de arquivos inexistentes ou ilegíveis
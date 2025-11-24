import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Exemplo de execução:
 * java MeuPrograma matriz_4x4.txt "0,0" "2,2"
 */
public class MeuPrograma {

    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("Uso: java MeuPrograma <arquivo_entrada> \"<origem_r,c>\" \"<destino_r,c>\"");
            System.err.println("Exemplo: java MeuPrograma teste_3x3.txt \"0,0\" \"2,2\"");
            return;
        }

        String inputFileName = args[0];
        String startCoord = args[1];
        String goalCoord = args[2];

        try {
            double[][] matrix = readMatrixFromFile(inputFileName);
            Graph graph = new Graph(matrix);

            Node startNode = graph.getNodeFromCoord(startCoord);
            Node goalNode = graph.getNodeFromCoord(goalCoord);

            if (startNode == null || goalNode == null) {
                System.err.println("Coordenadas de origem ou destino inválidas.");
                return;
            }

            AlgorithmRunner runner = new AlgorithmRunner(graph, startNode, goalNode);
            List<AlgorithmResult> results = new ArrayList<>();

            Heuristics.Heuristic manhattan = new Heuristics.Manhattan();
            Heuristics.Heuristic euclidean = new Heuristics.Euclidean();

            System.out.println("Executando BFS...");
            results.add(runner.runBFS());
            
            System.out.println("Executando DFS...");
            results.add(runner.runDFS());
            
            System.out.println("Executando Dijkstra...");
            results.add(runner.runDijkstra());

            System.out.println("Executando A* (Manhattan)...");
            results.add(runner.runAStar(manhattan));
            
            System.out.println("Executando A* (Euclidean)...");
            results.add(runner.runAStar(euclidean));

            System.out.println("Executando Greedy-BS (Manhattan)...");
            results.add(runner.runGBS(manhattan));

            System.out.println("Executando Greedy-BS (Euclidean)...");
            results.add(runner.runGBS(euclidean));

            for (AlgorithmResult result : results) {
                ResultWriter.writeResultFile(inputFileName, result);
            }

            System.out.println("Execução concluída. Arquivos de saída gerados.");

        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de entrada: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Erro de formato no arquivo de entrada ou coordenadas: " + e.getMessage());
        }
    }

    private static double[][] readMatrixFromFile(String fileName) throws IOException, NumberFormatException {
        List<double[]> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split("\\s+");
                double[] row = new double[parts.length];
                for (int i = 0; i < parts.length; i++) {
                    row[i] = Double.parseDouble(parts[i]);
                }
                lines.add(row);
            }
        }

        if (lines.isEmpty() || lines.size() != lines.get(0).length) {
            throw new IOException("A matriz de entrada deve ser quadrada e não vazia.");
        }

        return lines.toArray(new double[0][]);
    }
}
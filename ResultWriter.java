import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.StringJoiner;

public class ResultWriter {

    public static void writeResultFile(String baseFileName, AlgorithmResult result) throws IOException {
        String outputFileName = getOutputFileName(baseFileName, result);
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFileName))) {
            writer.println("ALGORITIMO: " + result.algorithmName);
            writer.println("HEURISTICA: " + result.heuristicName);
            writer.println("ORIGEM: " + result.start);
            writer.println("DESTINO: " + result.goal);

            if (result.pathFound) {
                StringJoiner pathString = new StringJoiner("->");
                for (Node node : result.path) {
                    pathString.add(node.toString());
                }
                writer.println("CAMINHO: " + pathString);
            } else {
                writer.println("CAMINHO: ");
            }

            writer.println("CUSTO: " + (result.pathFound ? result.cost : ""));
            writer.println("NOS EXPANDIDOS: " + result.expandedNodes);
            
            double timeMs = result.executionTimeNanos / 1_000_000.0;
            writer.println(String.format(Locale.US, "TEMPO (ms): %.2f", timeMs));
        }
    }

    private static String getOutputFileName(String baseFileName, AlgorithmResult result) {
        String suffix = "";
        switch (result.algorithmName) {
            case "BFS":
                suffix = ".bfs";
                break;
            case "DFS":
                suffix = ".dfs";
                break;
            case "DIJKSTRA":
                suffix = ".dijkstra";
                break;
            case "A*":
                suffix = ".a." + result.heuristicName.toLowerCase();
                break;
            case "GREEDY BEST-FIRST-SEARCH":
                suffix = ".gbs." + result.heuristicName.toLowerCase();
                break;
            default:
                suffix = ".unknown";
                break;
        }
        return baseFileName + suffix;
    }
}
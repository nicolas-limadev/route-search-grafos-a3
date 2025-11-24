import java.util.List;

public class AlgorithmResult {
    String algorithmName;
    String heuristicName = "";
    Node start;
    Node goal;
    List<Node> path;
    double cost = 0;
    int expandedNodes = 0;
    long executionTimeNanos = 0;
    boolean pathFound = false;

    public AlgorithmResult(String algorithmName) {
        this.algorithmName = algorithmName;
    }
}
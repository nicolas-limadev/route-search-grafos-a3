import java.util.ArrayList;
import java.util.List;

public class Graph {

    final double[][] adjMatrix;
    final int nodeCount;
    final int gridSize;
    final Node[] nodes;

    public Graph(double[][] matrix) {
        this.adjMatrix = matrix;
        this.nodeCount = matrix.length;
        this.gridSize = (int) Math.sqrt(nodeCount);

        if (gridSize * gridSize != nodeCount) {
            throw new IllegalArgumentException("A matriz de adjacência não representa uma grade quadrada perfeita.");
        }


        this.nodes = new Node[nodeCount];
        for (int i = 0; i < nodeCount; i++) {
            int r = i / gridSize;
            int c = i % gridSize;
            this.nodes[i] = new Node(i, r, c);
        }
    }

    public Node getNode(int index) {
        return nodes[index];
    }

    public Node getNodeFromCoord(String coord) {
        try {
            String[] parts = coord.split(",");
            int r = Integer.parseInt(parts[0]);
            int c = Integer.parseInt(parts[1]);
            int index = r * gridSize + c;
            if (index >= 0 && index < nodeCount) {
                return nodes[index];
            }
        } catch (Exception e) {
            
        }
        return null;
    }

    public List<Neighbor> getNeighbors(Node node) {
        List<Neighbor> neighbors = new ArrayList<>();
        int fromIndex = node.index;
        for (int toIndex = 0; toIndex < nodeCount; toIndex++) {
            double cost = adjMatrix[fromIndex][toIndex];
            if (cost > 0) {
                neighbors.add(new Neighbor(nodes[toIndex], cost));
            }
        }
        return neighbors;
    }
}
public class PathfindingPair implements Comparable<PathfindingPair> {
    final Node node;
    final double priority;

    public PathfindingPair(Node node, double priority) {
        this.node = node;
        this.priority = priority;
    }

    @Override
    public int compareTo(PathfindingPair other) {
        return Double.compare(this.priority, other.priority);
    }
}
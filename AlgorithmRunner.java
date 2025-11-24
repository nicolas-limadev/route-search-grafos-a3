import java.util.*;

public class AlgorithmRunner {

    private final Graph graph;
    private final Node start;
    private final Node goal;

    public AlgorithmRunner(Graph graph, Node start, Node goal) {
        this.graph = graph;
        this.start = start;
        this.goal = goal;
    }

    public AlgorithmResult runBFS() {
        long startTime = System.nanoTime();
        AlgorithmResult result = new AlgorithmResult("BFS");
        result.start = start;
        result.goal = goal;

        Queue<Node> frontier = new LinkedList<>();
        Map<Node, Node> cameFrom = new HashMap<>();
        
        frontier.add(start);
        cameFrom.put(start, null);
        
        while (!frontier.isEmpty()) {
            Node current = frontier.poll();
            result.expandedNodes++;

            if (current.equals(goal)) {
                result.pathFound = true;
                break;
            }

            for (Neighbor next : graph.getNeighbors(current)) {
                if (!cameFrom.containsKey(next.node)) {
                    frontier.add(next.node);
                    cameFrom.put(next.node, current);
                }
            }
        }

        if (result.pathFound) {
            reconstructPathAndCost(cameFrom, result);
        }
        result.executionTimeNanos = System.nanoTime() - startTime;
        return result;
    }

    public AlgorithmResult runDFS() {
        long startTime = System.nanoTime();
        AlgorithmResult result = new AlgorithmResult("DFS");
        result.start = start;
        result.goal = goal;

        Stack<Node> frontier = new Stack<>();
        Map<Node, Node> cameFrom = new HashMap<>();

        frontier.push(start);
        cameFrom.put(start, null);

        while (!frontier.isEmpty()) {
            Node current = frontier.pop();
            result.expandedNodes++;

            if (current.equals(goal)) {
                result.pathFound = true;
                break;
            }

            for (Neighbor next : graph.getNeighbors(current)) {
                if (!cameFrom.containsKey(next.node)) {
                    frontier.push(next.node);
                    cameFrom.put(next.node, current);
                }
            }
        }

        if (result.pathFound) {
            reconstructPathAndCost(cameFrom, result);
        }
        result.executionTimeNanos = System.nanoTime() - startTime;
        return result;
    }

    public AlgorithmResult runDijkstra() {
        long startTime = System.nanoTime();
        AlgorithmResult result = new AlgorithmResult("DIJKSTRA");
        result.start = start;
        result.goal = goal;

        PriorityQueue<PathfindingPair> frontier = new PriorityQueue<>();
        Map<Node, Node> cameFrom = new HashMap<>();
        Map<Node, Double> costSoFar = new HashMap<>();

        frontier.add(new PathfindingPair(start, 0));
        cameFrom.put(start, null);
        costSoFar.put(start, 0.0);

        while (!frontier.isEmpty()) {
            Node current = frontier.poll().node;
            result.expandedNodes++;

            if (current.equals(goal)) {
                result.pathFound = true;
                break;
            }

            for (Neighbor next : graph.getNeighbors(current)) {
                double newCost = costSoFar.get(current) + next.cost;
                if (!costSoFar.containsKey(next.node) || newCost < costSoFar.get(next.node)) {
                    costSoFar.put(next.node, newCost);
                    frontier.add(new PathfindingPair(next.node, newCost));
                    cameFrom.put(next.node, current);
                }
            }
        }

        if (result.pathFound) {
            result.path = reconstructPath(cameFrom);
            result.cost = costSoFar.get(goal);
        }
        result.executionTimeNanos = System.nanoTime() - startTime;
        return result;
    }

    public AlgorithmResult runAStar(Heuristics.Heuristic heuristic) {
        long startTime = System.nanoTime();
        AlgorithmResult result = new AlgorithmResult("A*");
        result.heuristicName = heuristic.getName();
        result.start = start;
        result.goal = goal;

        PriorityQueue<PathfindingPair> frontier = new PriorityQueue<>();
        Map<Node, Node> cameFrom = new HashMap<>();
        Map<Node, Double> costSoFar = new HashMap<>();

        frontier.add(new PathfindingPair(start, 0));
        cameFrom.put(start, null);
        costSoFar.put(start, 0.0);

        while (!frontier.isEmpty()) {
            Node current = frontier.poll().node;
            result.expandedNodes++;

            if (current.equals(goal)) {
                result.pathFound = true;
                break;
            }

            for (Neighbor next : graph.getNeighbors(current)) {
                double newCost = costSoFar.get(current) + next.cost;
                if (!costSoFar.containsKey(next.node) || newCost < costSoFar.get(next.node)) {
                    costSoFar.put(next.node, newCost);
                    double priority = newCost + heuristic.calculate(next.node, goal);
                    frontier.add(new PathfindingPair(next.node, priority));
                    cameFrom.put(next.node, current);
                }
            }
        }

        if (result.pathFound) {
            result.path = reconstructPath(cameFrom);
            result.cost = costSoFar.get(goal);
        }
        result.executionTimeNanos = System.nanoTime() - startTime;
        return result;
    }

    public AlgorithmResult runGBS(Heuristics.Heuristic heuristic) {
        long startTime = System.nanoTime();
        AlgorithmResult result = new AlgorithmResult("GREEDY BEST-FIRST-SEARCH");
        result.heuristicName = heuristic.getName();
        result.start = start;
        result.goal = goal;

        PriorityQueue<PathfindingPair> frontier = new PriorityQueue<>();
        Map<Node, Node> cameFrom = new HashMap<>();

        frontier.add(new PathfindingPair(start, heuristic.calculate(start, goal)));
        cameFrom.put(start, null);

        while (!frontier.isEmpty()) {
            Node current = frontier.poll().node;
            result.expandedNodes++;

            if (current.equals(goal)) {
                result.pathFound = true;
                break;
            }

            for (Neighbor next : graph.getNeighbors(current)) {
                if (!cameFrom.containsKey(next.node)) {
                    double priority = heuristic.calculate(next.node, goal);
                    frontier.add(new PathfindingPair(next.node, priority));
                    cameFrom.put(next.node, current);
                }
            }
        }

        if (result.pathFound) {
            reconstructPathAndCost(cameFrom, result);
        }
        result.executionTimeNanos = System.nanoTime() - startTime;
        return result;
    }

    private List<Node> reconstructPath(Map<Node, Node> cameFrom) {
        List<Node> path = new ArrayList<>();
        Node current = goal;
        while (current != null) {
            path.add(current);
            current = cameFrom.get(current);
        }
        Collections.reverse(path);
        return path;
    }

    private void reconstructPathAndCost(Map<Node, Node> cameFrom, AlgorithmResult result) {
        List<Node> path = reconstructPath(cameFrom);
        result.path = path;

        double totalCost = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            Node u = path.get(i);
            Node v = path.get(i + 1);
            totalCost += graph.adjMatrix[u.index][v.index];
        }
        result.cost = totalCost;
    }
}
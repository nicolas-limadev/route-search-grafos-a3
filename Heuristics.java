public class Heuristics {

    public interface Heuristic {
        String getName();
        double calculate(Node a, Node b);
    }

    public static class Manhattan implements Heuristic {
        @Override
        public String getName() {
            return "Manhattan";
        }

        @Override
        public double calculate(Node a, Node b) {
            return Math.abs(a.r - b.r) + Math.abs(a.c - b.c);
        }
    }

    public static class Euclidean implements Heuristic {
        @Override
        public String getName() {
            return "Euclidiana";
        }

        @Override
        public double calculate(Node a, Node b) {
            return Math.sqrt(Math.pow(a.r - b.r, 2) + Math.pow(a.c - b.c, 2));
        }
    }
}
import java.util.Objects;

public class Node {
    final int index;
    final int r;
    final int c;

    public Node(int index, int r, int c) {
        this.index = index;
        this.r = r;
        this.c = c;
    }

    @Override
    public String toString() {
        return "(" + r + "," + c + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return index == node.index;
    }

    @Override
    public int hashCode() {
        return Objects.hash(index);
    }
}
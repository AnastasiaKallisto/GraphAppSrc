package GraphWork;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Edge {
    private Vertex a;
    private Vertex b;
    private int c;

    public Edge(Vertex a, Vertex b, int c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public Vertex getA() {
        return a;
    }

    public Vertex getB() {
        return b;
    }

    public int getC() {
        return c;
    }

    public Set<Vertex> getVertices() {
        Set<Vertex> vertices = new HashSet<>();
        vertices.add(a);
        vertices.add(b);
        return vertices;
    }

    public void setC(int c) {
        this.c = c;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return c == edge.c && a.equals(edge.a) && b.equals(edge.b);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b, c);
    }

    public int compareTo(Edge edge) {
        return Integer.compare(this.getC(), edge.getC());
    }
}

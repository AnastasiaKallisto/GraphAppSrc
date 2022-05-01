package GraphWork;

import java.util.Objects;

public class Edge {
    private Vertex a;
    private Vertex b;
    private double c;

    public Edge(Vertex a, Vertex b, double c) {
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

    public double getC() {
        return c;
    }

    public void setC(double c) {
        this.c = c;
    }

    @Override
    public boolean equals(Object o) { // без учета С
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return a.equals(edge.a) && b.equals(edge.b);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }

    public int compareTo(Edge edge) {
        return Double.compare(this.getC(), edge.getC());
    }
}

package GraphWork;

import java.util.*;

public class Edge {
    private ArrayList<Vertex> vertices;
    private int weight;

    public Edge(Vertex a, Vertex b, int weight) {
        vertices = new ArrayList<>();
        vertices.add(a);
        vertices.add(b);
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public Vertex getA(){
        return vertices.get(0);
    }

    public Vertex getB(){
        return vertices.get(1);
    }

    public ArrayList<Vertex> getVertices() {
        return vertices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return new HashSet<>(vertices).equals(new HashSet<>(edge.vertices));
    }

    @Override
    public int hashCode() {
        return Objects.hash(new HashSet<>(vertices));
    }

    public int compareTo(Edge edge) {
        return Integer.compare(this.getWeight(), edge.getWeight());
    }
}

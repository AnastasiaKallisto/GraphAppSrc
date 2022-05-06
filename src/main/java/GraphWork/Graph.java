package GraphWork;

import java.util.*;

public class Graph {
    private Set<Edge> edges;
    private TreeMap<Integer, Vertex> vertices;

    public Graph(int n, int sizeFrameX, int sizeFrameY) {
        vertices = new TreeMap<>();
        edges = new HashSet<>();
        generateVertices(n, sizeFrameX, sizeFrameY);
        int quantityOfActedVertices = 2;
        int firstNumber, secondNumber;
        boolean check = false;
        edges.add(new Edge(vertices.get(1), vertices.get(2), (int) (Math.random() * 100 + 1)));
        // создаем дерево
        while (quantityOfActedVertices < n) {
            firstNumber = (int) Math.floor(Math.random() * quantityOfActedVertices) + 1; // задейств. вершина
            secondNumber = quantityOfActedVertices + 1; // новая вершина
            edges.add(new Edge(vertices.get(firstNumber), vertices.get(secondNumber), (int) (Math.random() * 100 + 1)));
            quantityOfActedVertices++;
        }
        //усложним граф
        Set<Edge> set = new HashSet<>();
        for (int i = 1; i < (int) (Math.random() * n * 2) + 1; i++) {
            firstNumber = (int) Math.floor(Math.random() * n) + 1;
            secondNumber = (int) Math.floor(Math.random() * n) + 1;
            if (firstNumber != secondNumber) {
                set.add(new Edge(vertices.get(firstNumber), vertices.get(secondNumber), (int) (Math.random() * 100 + 1)));
            }
        }
        for (Edge edge : set) {
            if (!containsEdge(edge)) {
                edges.add(edge);
            }
        }
    }

    public boolean containsEdge(Edge edge) {
        for (Edge edge1 : edges) {
            if (edge.getVertices().equals(edge1.getVertices())) {
                return true;
            }
        }
        return false;
    }

    private void generateVertices(int n, int sizeFrameX, int sizeFrameY) {
        int centerX = sizeFrameX / 2;
        int centerY = sizeFrameY / 2;
        int radius = (int) (centerY * 0.9);
        for (int i = 1; i <= n; i++) {
            vertices.put(i, new Vertex((int) (centerX + radius * Math.cos(i * Math.PI * 2 / n)), (int) (centerY + radius * Math.sin(i * Math.PI * 2 / n)), i));
        }
    }

    public Set<Edge> getEdges() {
        return edges;
    }

    public Map<Integer, Vertex> getVertices() {
        return vertices;
    }
}

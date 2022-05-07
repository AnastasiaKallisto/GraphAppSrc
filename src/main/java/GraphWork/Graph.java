package GraphWork;

import java.util.*;

public class Graph {
    private Set<Edge> edges;
    private Map<Integer, Vertex> vertices;

    public Graph(int n, int sizeFrameX, int sizeFrameY) {
        vertices = new TreeMap<>();
        edges = new HashSet<>();
        generateVertices(n, sizeFrameX, sizeFrameY);
        int quantityOfActedVertices = 2;
        int firstNumber, secondNumber;
        edges.add(new Edge(vertices.get(1), vertices.get(2), (int) (Math.random() * 100 + 1)));
        // создаем дерево
        while (quantityOfActedVertices < n) {
            firstNumber = (int) Math.floor(Math.random() * quantityOfActedVertices) + 1; // задейств. вершина
            secondNumber = ++quantityOfActedVertices; // новая вершина
            edges.add(new Edge(vertices.get(firstNumber), vertices.get(secondNumber), (int) (Math.random() * 100 + 1)));
        }
        //усложним граф
        int randomQuantity = (int) (Math.random() * 2 * n) + 1;
        for (int i = 1; i < randomQuantity; i++) {
            firstNumber = (int) Math.floor(Math.random() * n) + 1;
            secondNumber = (int) Math.floor(Math.random() * n) + 1;
            if (firstNumber != secondNumber) {
                edges.add(new Edge(vertices.get(firstNumber), vertices.get(secondNumber), (int) (Math.random() * 100 + 1)));
            }
        }
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

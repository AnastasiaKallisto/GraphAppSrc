package Intervalization;


import GraphWork.Edge;
import GraphWork.Vertex;

import java.util.*;

public class IntervalGraph {
    private ArrayList<IntervalEdge> edges;
    private Map<Integer, Vertex> vertices;

    public IntervalGraph(int n, int sizeFrameX, int sizeFrameY) {
        vertices = new TreeMap<>();
        edges = new ArrayList<>();
        generateVertices(n, sizeFrameX, sizeFrameY);
        int quantityOfActedVertices = 2;
        int firstNumber, secondNumber;
        int start, end;
        start = (int) (Math.random() * 100 + 1);
        end = start + (int) (Math.random() * 100 + 1);
        edges.add(new IntervalEdge(vertices.get(1), vertices.get(2), start, end));
        // создаем дерево
        while (quantityOfActedVertices < n) {
            firstNumber = (int) Math.floor(Math.random() * quantityOfActedVertices) + 1; // задейств. вершина
            secondNumber = ++quantityOfActedVertices; // новая вершина
            start = (int) (Math.random() * 100 + 1);
            end = start + (int) (Math.random() * 100 + 1);
            edges.add(new IntervalEdge(vertices.get(firstNumber), vertices.get(secondNumber), start, end));
        }
        //усложним граф
        int randomQuantity = (int) (Math.random() * 2 * n) + 1;
        for (int i = 1; i < randomQuantity; i++) {
            firstNumber = (int) Math.floor(Math.random() * n) + 1;
            secondNumber = (int) Math.floor(Math.random() * n) + 1;
            if (firstNumber != secondNumber) {
                start = (int) (Math.random() * 100 + 1);
                end = start + (int) (Math.random() * 100 + 1);
                edges.add(new IntervalEdge(vertices.get(firstNumber), vertices.get(secondNumber), start, end));
            }
        }
    }

    public IntervalGraph(IntervalGraph graph){
        this.edges = new ArrayList<>(graph.getEdges());
        this.vertices = new TreeMap<>(graph.getVertices());
    }

    private void generateVertices(int n, int sizeFrameX, int sizeFrameY) {
        int centerX = sizeFrameX / 2;
        int centerY = sizeFrameY / 2;
        int radius = (int) (centerY * 0.9);
        for (int i = 1; i <= n; i++) {
            vertices.put(i, new Vertex((int) (centerX + radius * Math.cos(i * Math.PI * 2 / n)), (int) (centerY + radius * Math.sin(i * Math.PI * 2 / n)), i));
        }
    }

    public ArrayList<IntervalEdge> getEdges() {
        return edges;
    }

    public Map<Integer, Vertex> getVertices() {
        return vertices;
    }

    public void addEdge(IntervalEdge edge){
        if (!edges.contains(edge)){
            edges.add(edge);
            vertices.putIfAbsent(edge.getA().getNumber(),edge.getA());
            vertices.putIfAbsent(edge.getB().getNumber(),edge.getB());
        }
    }
}
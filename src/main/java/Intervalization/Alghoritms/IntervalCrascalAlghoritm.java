package Intervalization.Alghoritms;

import GraphWork.Edge;
import GraphWork.Graph;
import GraphWork.Vertex;
import Intervalization.IntervalEdge;
import Intervalization.IntervalGraph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IntervalCrascalAlghoritm extends IntervalGraphAlghoritm{
    @Override
    public ArrayList<IntervalEdge> getNextEdges(IntervalGraph graph, ArrayList<IntervalEdge> availableEdges) {
        return null;
    }

    @Override
    public ArrayList<IntervalEdge> returnOnlyNeсessaryEdges(IntervalGraph helpGraph, ArrayList<IntervalEdge> edges) {
        return null;
    }

    public static ArrayList<Edge> returnMinSpanningTreeCrascal(Graph graph) {
        long t = System.currentTimeMillis();
        ArrayList<Edge> minSpanningTreeCrascal = new ArrayList<>();
        List<Edge> availableEdges = new ArrayList<>(graph.getEdges());
        Edge edge;
        availableEdges.sort(Edge::compareTo);
        Set<Vertex> checkedVertices = new HashSet<>();

        for (int i = 0; i < availableEdges.size(); i++) {
            edge = availableEdges.get(i);
            if (!searchCircle(edge.getA(), edge.getB(), minSpanningTreeCrascal, checkedVertices)) {
                minSpanningTreeCrascal.add(edge);
            }
            checkedVertices.clear();
        }
        System.out.println("Crascal " + graph.getEdges().size() + ": " + (System.currentTimeMillis() - t) + " millis");
        return minSpanningTreeCrascal;
    }

    private static boolean searchCircle(Vertex a, Vertex b, ArrayList<Edge> minSpanningTree, Set<Vertex> checkedVertices) {
        //ищем цепочку из a в b.
        // Для этого рекурсивно будем смотреть непроверенные вершины, не придем ли мы из них в вершину b
        // по уже существующим ребрам дерева
        Set<Vertex> needToBeCheckedSet = new HashSet<>();
        // для каждого ребра из уже существующего дерева
        for (Edge edge : minSpanningTree) {
            // если нашли ребро, приходящее в b, то смысла смотреть дальше нет
            if (edge.getA().equals(a)) {
                if (edge.getB().equals(b)) {
                    return true;
                }
                if (!checkedVertices.contains(edge.getB())) {
                    needToBeCheckedSet.add(edge.getB());
                }
            }
            if (edge.getB().equals(a)) {
                if (edge.getA().equals(b)) {
                    return true;
                }
                if (!checkedVertices.contains(edge.getA())) { // мы вторую уже проверяли?
                    needToBeCheckedSet.add(edge.getA()); // нет - надо проверить.
                }
            }
        }
        checkedVertices.add(a);// из нее точно не попадаем в b
        for (Vertex vertex : needToBeCheckedSet) {
            if (searchCircle(vertex, b, minSpanningTree, checkedVertices)) {
                return true;
            }
        }
        return false;
    }
}

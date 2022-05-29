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

public class IntervalPrimAlghoritm extends IntervalGraphAlghoritm{
    @Override
    public ArrayList<IntervalEdge> getNextEdges(IntervalGraph graph, ArrayList<IntervalEdge> availableEdges) {
        return null;
    }

    @Override
    public ArrayList<IntervalEdge> returnOnlyNeсessaryEdges(IntervalGraph helpGraph, ArrayList<IntervalEdge> helpListOfAvailableEdges) {
        return null;
    }

    public static ArrayList<Edge> returnMinSpanningTreePrim(Graph graph) {
        long t = System.currentTimeMillis();
        ArrayList<Edge> minSpanningTreePrim = new ArrayList<>();
        List<Edge> availableEdges = new ArrayList<>(graph.getEdges());
        List<Edge> actualIncidentEdges = null;
        List<Vertex> availableVertices = new ArrayList<>(graph.getVertices().values());
        Edge curMinEdge;
        availableEdges.sort(Edge::compareTo);
        Set<Vertex> usedVertices = new HashSet<>();
        Vertex a;

        a = availableVertices.get(0);
        usedVertices.add(a);
        availableVertices.remove(a);
        curMinEdge = searchNotUsedIncidentEdgesForPrim(a, availableEdges, availableVertices).get(0);
        //пока возможно
        while (availableVertices.size() > 0) {
            //ищем минимум по всем ребрам которые вообще можно добавить к сущ. дереву
            for (Vertex vertex : usedVertices) {
                // для конкретной вершины посмотрели все незадействованные ребра, выбрали из них одно
                actualIncidentEdges = searchNotUsedIncidentEdgesForPrim(vertex, availableEdges, availableVertices);
                if (actualIncidentEdges.size() > 0 && curMinEdge == null) {
                    curMinEdge = actualIncidentEdges.get(0);
                }
                // нашли минимальное по весу ребро
                for (Edge edge : actualIncidentEdges) {
                    if (curMinEdge.getWeight() > edge.getWeight()) {
                        curMinEdge = edge;
                    }
                }
            }
            // теперь мы знаем минимальное ребро. добавим его
            // из него добавится вершина в использованные, уйдет из доступных
            // добавится ребро в мин.ост.дерево, уйдет из доступных
            availableVertices.removeAll(curMinEdge.getVertices());
            availableEdges.remove(curMinEdge);
            usedVertices.addAll(curMinEdge.getVertices());
            minSpanningTreePrim.add(curMinEdge);
            curMinEdge = null;
        }
        System.out.println("Prim " + graph.getEdges().size() + ": " + (System.currentTimeMillis() - t) + " millis");
        return minSpanningTreePrim;
    }

    public static List<Edge> searchNotUsedIncidentEdgesForPrim(Vertex a, List<Edge> availableEdges, List<Vertex> availableVertices) {
        List<Edge> notUsedIncidentEdges = new ArrayList<>();
        for (Edge edge : availableEdges) {
            if ((edge.getVertices().contains(a)) && (availableVertices.contains(edge.getA()) || availableVertices.contains(edge.getB()))) { // ???
                notUsedIncidentEdges.add(edge);
            }
        }
        return notUsedIncidentEdges;
    }
}

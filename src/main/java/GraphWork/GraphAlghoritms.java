package GraphWork;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GraphAlghoritms {

    /*

     */
    public static ArrayList<Edge> returnMinSpanningTreePrim(Graph graph) {
        ArrayList<Edge> minSpanningTreePrim = new ArrayList<>();
        List<Edge> availableEdges = new ArrayList<>(graph.getEdges());
        List<Vertex> availableVertices = new ArrayList<>(graph.getVertices().values());
        Set<Vertex> usedVertices = new HashSet<>();
        availableEdges.sort(Edge::compareTo);
        Vertex a = availableVertices.get(0);
        usedVertices.add(a);
        availableVertices.remove(a);
        Edge curMinEdge = searchNotUsedIncidentEdgesForPrim(a, availableEdges, availableVertices).get(0);
        while (availableVertices.size() > 0) {
            for (Vertex vertex : usedVertices) {
                List<Edge> incidentEdgesForVertex =
                        searchNotUsedIncidentEdgesForPrim(vertex, availableEdges, availableVertices);
                if (incidentEdgesForVertex.size() > 0 && curMinEdge == null) {
                    curMinEdge = incidentEdgesForVertex.get(0);
                } else {
                    for (Edge edge : incidentEdgesForVertex) {
                        if (curMinEdge.getWeight() > edge.getWeight()) {
                            curMinEdge = edge;
                        }
                    }
                }
            }
            availableVertices.removeAll(curMinEdge.getVertices());
            availableEdges.remove(curMinEdge);
            usedVertices.addAll(curMinEdge.getVertices());
            minSpanningTreePrim.add(curMinEdge);
            curMinEdge = null;
        }
        return minSpanningTreePrim;
    }


    public static ArrayList<Edge> returnMinSpanningTreeKruskal(Graph graph) {
        ArrayList<Edge> minSpanningTreeKruskal = new ArrayList<>();
        List<Edge> availableEdges = new ArrayList<>(graph.getEdges());
        availableEdges.sort(Edge::compareTo);
        for (Edge edge : availableEdges) {
            if (!searchChain(edge.getA(), edge.getB(), minSpanningTreeKruskal, new HashSet<>())) {
                minSpanningTreeKruskal.add(edge);
            }
        }
        return minSpanningTreeKruskal;
    }

    private static boolean searchChain(Vertex a, Vertex b,
                                       ArrayList<Edge> graph,
                                       Set<Vertex> checkedVertices) {
        Set<Vertex> needToBeCheckedSet = new HashSet<>();
        for (Edge edge : graph) {
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
                if (!checkedVertices.contains(edge.getA())) {
                    needToBeCheckedSet.add(edge.getA());
                }
            }
        }
        checkedVertices.add(a);
        for (Vertex vertex : needToBeCheckedSet) {
            if (searchChain(vertex, b, graph, checkedVertices)) {
                return true;
            }
        }
        return false;
    }

    public static List<Edge> searchNotUsedIncidentEdgesForPrim(Vertex a,
                                                               List<Edge> availableEdges,
                                                               List<Vertex> availableVertices) {
        List<Edge> notUsedIncidentEdges = new ArrayList<>();
        for (Edge edge : availableEdges) {
            if ((edge.getVertices().contains(a)) &&
                    (availableVertices.contains(edge.getA()) || availableVertices.contains(edge.getB()))) {
                notUsedIncidentEdges.add(edge);
            }
        }
        return notUsedIncidentEdges;
    }

}

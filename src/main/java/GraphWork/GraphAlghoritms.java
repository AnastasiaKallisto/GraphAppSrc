package GraphWork;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GraphAlghoritms {

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

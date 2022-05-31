package Intervalization.Alghoritms;

import Intervalization.IntervalEdge;
import Intervalization.IntervalGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class IntervalPrimAlghoritm extends IntervalGraphAlghoritm{
    @Override //формируется множество Q
    public List<IntervalEdge> getNextEdges(IntervalGraph graph, List<IntervalEdge> incidentEdges) {
        incidentEdges = returnEdgesWithoutCycle(graph, incidentEdges);
        // нашли ребро с мин. правой границей
        incidentEdges.sort(IntervalEdge::compareToRight);
        int minRightBorder = incidentEdges.get(0).getIntervalWeight().getEnd();
        // найдем инцид. ребра, для к-х есть веса меньше, чем minRightBorder
        List<IntervalEdge> answer = new ArrayList<>();
        for (IntervalEdge edge: incidentEdges) {
            if (edge.getIntervalWeight().getStart() <= minRightBorder){
                answer.add(edge);
            }
        }
        return answer;
    }

     //убираются ребра, которые могли бы дать цикл
    public List<IntervalEdge> returnEdgesWithoutCycle(IntervalGraph graph, List<IntervalEdge> edges) {
        List<IntervalEdge> needToBeRemoved = new ArrayList<>();
        for (IntervalEdge edge: edges){
            // если обе вершины ребра уже содержатся во множестве задействованных вершин, значит, оно даст цикл
            if (graph.getVertices().values().containsAll(edge.getVertices())){
                needToBeRemoved.add(edge);
            }
        }
        List<IntervalEdge> answer = new ArrayList<>(edges);
        answer.removeAll(needToBeRemoved);
        return answer;
    }

    public static List<IntervalEdge> searchIncidentEdgesForPrim(IntervalGraph graph, List<IntervalEdge> edges) {
       List<IntervalEdge> notUsedIncidentEdges = new ArrayList<>();
       Set<Integer> vertexNumbers = graph.getVertices().keySet();
        for (IntervalEdge edge : edges) {
            if (vertexNumbers.contains(edge.getA().getNumber()) || vertexNumbers.contains(edge.getB().getNumber())){
                notUsedIncidentEdges.add(edge);
            }
        }
        return notUsedIncidentEdges;
    }
}

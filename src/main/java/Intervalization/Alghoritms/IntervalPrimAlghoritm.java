package Intervalization.Alghoritms;

import Intervalization.IntervalEdge;
import Intervalization.IntervalGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class IntervalPrimAlghoritm extends IntervalGraphAlghoritm{
    @Override
    public List<IntervalEdge> getNextEdges(IntervalGraph graph, List<IntervalEdge> availableEdges) {
        List<IntervalEdge> needToBeRemoved = new ArrayList<>();
        List<IntervalEdge> incidentEdges = searchIncidentEdgesForPrim(graph, availableEdges);
        for (IntervalEdge edge: incidentEdges){
            // если обе вершины ребра уже содержатся во множестве задействованных вершин, значит, оно даст цикл
            if (graph.getVertices().values().containsAll(edge.getVertices())){
                needToBeRemoved.add(edge);
            }
        }
        incidentEdges.removeAll(needToBeRemoved);
        availableEdges.removeAll(needToBeRemoved);
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

    /**
     *
     * @param graph - уже вычисленная часть дерева
     * @param availableEdges - все доступные для добавления ребра
     * @return - список ребер, инцидентных с вершинами, содержащимися в graph
     */
    public static List<IntervalEdge> searchIncidentEdgesForPrim(IntervalGraph graph, List<IntervalEdge> availableEdges) {
       List<IntervalEdge> incidentEdges = new ArrayList<>();
       Set<Integer> vertexNumbers = graph.getVertices().keySet();
        for (IntervalEdge edge : availableEdges) {
            if (vertexNumbers.contains(edge.getA().getNumber()) || vertexNumbers.contains(edge.getB().getNumber())){
                incidentEdges.add(edge);
            }
        }
        return incidentEdges;
    }
}

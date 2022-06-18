package Intervalization.Alghoritms;

import Intervalization.IntervalEdge;
import Intervalization.IntervalGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class IntervalPrimAlghoritm extends IntervalGraphAlghoritm{
    /**
     *
     * @param graph - уже составленная на данный момент часть дерева
     * @param availableEdges - все доступные для добавления рёбра
     * @return - список рёбер, которые будет рационально добавлять на данном этапе (множество Q)
     */

    @Override
    public List<IntervalEdge> getNextEdges(IntervalGraph graph, List<IntervalEdge> availableEdges) {
        // те ребра, которые нужно убрать
        List<IntervalEdge> needToBeRemoved = new ArrayList<>();
        // найдём все инцидентные на данный момент рёбра
        List<IntervalEdge> incidentEdges = searchIncidentEdgesForPrim(graph, availableEdges);
        // составим список рёбер, которые дадут цикл
        for (IntervalEdge edge: incidentEdges){
            // если обе вершины ребра уже содержатся во множестве задействованных вершин, значит, оно даст цикл
            if (graph.getVertices().values().containsAll(edge.getVertices())){
                needToBeRemoved.add(edge);
            }
        }
        // рёбра, что дадут цикл, не имеет смысла больше рассматривать, ни на данном этапе, ни на всех последующих
        incidentEdges.removeAll(needToBeRemoved);
        availableEdges.removeAll(needToBeRemoved);
        // если никаких рёбер больше не можем добавить - возвращаем пустой список
        if (incidentEdges.size() == 0){
            return new ArrayList<>();
        }
        // отсортируем рёбра по правой границе
        incidentEdges.sort(IntervalEdge::compareToRight);
        // найдем минимальную правую границу
        int minRightBorder = incidentEdges.get(0).getIntervalWeight().getEnd();
        // найдем инцид. ребра, для к-х есть веса меньше, чем minRightBorder
        // это будет множество Q
        List<IntervalEdge> answer = new ArrayList<>();
        for (IntervalEdge edge: incidentEdges) {
            if (edge.getIntervalWeight().getStart() <= minRightBorder){
                answer.add(edge);
            }
        }
        return answer;
    }

    /**
     * Функция, которая находит все рёбра, инцидентные вершинам уще вычисленной части дерева
     * @param graph - уже вычисленная часть дерева
     * @param availableEdges - все доступные для добавления ребра
     * @return - список ребер, инцидентных с вершинами, содержащимися в graph
     */
    public static List<IntervalEdge> searchIncidentEdgesForPrim(IntervalGraph graph, List<IntervalEdge> availableEdges) {
       List<IntervalEdge> incidentEdges = new ArrayList<>();
       // множество вершин уже вычисленной части дерева
       Set<Integer> vertexNumbers = graph.getVertices().keySet();
        for (IntervalEdge edge : availableEdges) {
            // если хоть одна вершина доступного ребра есть в этом множестве, значит, оно является инцидентным
            if (vertexNumbers.contains(edge.getA().getNumber()) || vertexNumbers.contains(edge.getB().getNumber())){
                incidentEdges.add(edge);
            }
        }
        return incidentEdges;
    }
}

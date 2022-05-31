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
    public List<IntervalEdge> getNextEdges(IntervalGraph graph, List<IntervalEdge> availableEdges) {
        List<IntervalEdge> needToBeRemoved = new ArrayList<>();
        List<IntervalEdge> answer = new ArrayList<>();
        availableEdges.sort(IntervalEdge::compareToRight);
        int minRightBorder = 0;
        // находим ребро с минимальной правой границей
        // идем по порядку. Если встречаем ребро, которое дает цикл,
        // вносим его в список тех, что нужно убрать
        // Если ребро не дает цикл - оно то, что нам нужно.
        for (IntervalEdge edge: availableEdges){
            if (!searchCircle(edge.getA(),edge.getB(), graph, new HashSet<>())){
                answer.add(edge);
                needToBeRemoved.add(edge);
                minRightBorder = edge.getIntervalWeight().getEnd();
                break;
            } else {
                needToBeRemoved.add(edge);
            }
        }
        availableEdges.removeAll(needToBeRemoved);
        // Найдем ВСЕ ребра с весами меньше, чем minRightBorder, попутно убирая те, что дают цикл.
        // Получим множество Q
        availableEdges.sort(IntervalEdge::compareToLeft);
        for (IntervalEdge edge: availableEdges){
            //если левый вес тот что надо
            if (edge.getIntervalWeight().getStart() <= minRightBorder){
                // если нет цикла
                if (!searchCircle(edge.getA(),edge.getB(), graph, new HashSet<>())){
                    answer.add(edge);
                } else {
                    needToBeRemoved.add(edge);
                }
            } else {
                break;
            }
        }
        return answer;
    }

    private static boolean searchCircle(Vertex a, Vertex b, IntervalGraph graph, Set<Vertex> checkedVertices) {
        //ищем цепочку из a в b.
        // Для этого рекурсивно будем смотреть непроверенные вершины, не придем ли мы из них в вершину b
        // по уже существующим ребрам дерева
        Set<Vertex> needToBeCheckedSet = new HashSet<>();
        // для каждого ребра из уже существующего дерева
        for (IntervalEdge edge : graph.getEdges()) {
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
            if (searchCircle(vertex, b, graph, checkedVertices)) {
                return true;
            }
        }
        return false;
    }
}

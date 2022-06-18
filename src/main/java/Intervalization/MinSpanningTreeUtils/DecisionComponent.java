package Intervalization.MinSpanningTreeUtils;

import Intervalization.IntervalEdge;
import Intervalization.IntervalGraph;
import Intervalization.Alghoritms.IntervalGraphAlghoritm;

import java.util.ArrayList;
import java.util.List;

public class DecisionComponent {
    private double probability; // вероятность попасть в этот компонент дерева решений
    private ArrayList<DecisionComponent> nextComponents; // варианты компонентов, в которые мы можем прийти отсюда
    private IntervalGraph minSpanningTree; // null если не дошли до листа дерева решений, иначе - это мин.остовное дерево (одно из)


    public DecisionComponent(double probability,
                             IntervalGraphAlghoritm alghoritm,
                             IntervalGraph graph,
                             List<IntervalEdge> availableEdges) {
        this.nextComponents = new ArrayList<>();

        // Теперь получим множество следующих потенциальных ребер, множество Q
        List<IntervalEdge> Q = alghoritm.getNextEdges(graph, availableEdges);
        // и поработаем с ним - посмотрим, что там будет дальше
        for (IntervalEdge edge: Q) {
            IntervalGraph helpGraph = new IntervalGraph(graph);
            // формируем новый вариант графа, в который добавили ребро
            helpGraph.addEdge(edge);
            // отсортируем рёбра по правой границе
            Q.sort(IntervalEdge::compareToRight);
            // найдем минимальную правую границу среди правых границ рёбер из Q
            int minRightBorder = Q.get(0).getIntervalWeight().getEnd();
            // подрезаем веса ребер из Q
            Q = IntervalGraphAlghoritm.cutEdges(edge, minRightBorder, Q);
            // рассчитываем вероятность выбрать именно это ребро из Q с учетом весов на данном этапе
            this.probability = getProbability(probability, edge, Q);
            // это ребро больше не появится в списке доступных
            List<IntervalEdge> helpListOfAvailableEdges = new ArrayList<>(availableEdges);
            helpListOfAvailableEdges.remove(edge);
            // сравнение рёбер на равенство идет только по вершинам
            helpListOfAvailableEdges.removeAll(Q);
            // мы удалили рёбра и добавили их же, но уже с другими весами
            helpListOfAvailableEdges.addAll(Q);
            // создаем новый компонент решения со своим новым начальным графом и новым множеством доступных ребер
            nextComponents.add(new DecisionComponent(probability, alghoritm, helpGraph, helpListOfAvailableEdges));
        }
        minSpanningTree = null;
        if (nextComponents.size() == 0){
            minSpanningTree = graph;
        }
    }

    public double getProbability(double probability, IntervalEdge edge, List<IntervalEdge> Q) {
        //сейчас именно это - главная задача
        return this.probability;
    }

    public ArrayList<IntervalGraph> getDecisions(){
        // формируем множество деревьев, из них формируется ответ на задачу
        ArrayList<IntervalGraph> graphs = new ArrayList<>();
        if (this.minSpanningTree != null){
            // если это лист, добавляем дерево из листа
            graphs.add(this.minSpanningTree);
        }
        // если мы не в листе дерева решений, закапываемся вглубь
        for (DecisionComponent decisionComponent: nextComponents){
            graphs.addAll(decisionComponent.getDecisions());
        }
        return graphs;
    }
}

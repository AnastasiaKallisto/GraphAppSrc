package Intervalization.MinSpanningTreeUtils;

import Intervalization.Alghoritms.Partition;
import Intervalization.Alghoritms.Probability;
import Intervalization.Interval;
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
        this.probability = probability;
        List<IntervalEdge> availableEdgesForNextComponents = new ArrayList<>(availableEdges);
        // Теперь получим множество следующих потенциальных ребер, множество Q
        // Также внутри этой функции удаляются из списка все рёбра, которые могли бы дать цикл
        List<IntervalEdge> Q = alghoritm.getQ(graph, availableEdgesForNextComponents);
        if (Q.size()!= 0) {
            // отсортируем рёбра из Q по правой границе
            Q.sort(IntervalEdge::compareToRight);
            // найдем минимальную правую границу среди правых границ рёбер из Q
            int minRightBorder = Q.get(0).getIntervalWeight().getEnd();
            // Найдем все границы для элементов разбиений весов рёбер из Q
            List<Integer> bordersForPartitionOfQ = Partition.getBordersForPartitionOfQ(Q, minRightBorder);
            // Найдем все элементы разбиений весов для рёбер из Q
            List<Interval> partitionForQ = Partition.getPartitionForQ(bordersForPartitionOfQ);
            // и поработаем с Q
            for (IntervalEdge edge: Q) {
                // итак, раз мы здесь, то точный вес ребра edge оказался меньше всех остальных
                IntervalGraph helpGraph = new IntervalGraph(graph);
                // формируем новый вариант графа, в который добавили это ребро
                helpGraph.addEdge(edge);
                // формируется Q1 на основе Q, для каждого ребра подрезки могут быть разными
                List<IntervalEdge> Q1 = IntervalGraphAlghoritm.cutEdges(edge, minRightBorder, Q);
                // Формируем разбиение веса ребра edge
                List<Interval> partitionOfEdgeWeight = Partition.getPartitionFor_e_q(edge, bordersForPartitionOfQ, partitionForQ);
                // рассчитываем вероятность выбрать именно это ребро из Q1 с учетом весов на данном этапе
                this.probability = getProbability(probability, edge, partitionOfEdgeWeight, Q1);
                // это ребро больше не появится в списке доступных
                List<IntervalEdge> helpListOfAvailableEdges = new ArrayList<>(availableEdgesForNextComponents);
                helpListOfAvailableEdges.remove(edge);
                // сравнение рёбер на равенство идет только по вершинам,
                // веса ребер при проверке на равенство значения не имеют
                helpListOfAvailableEdges.removeAll(Q);
                // мы удалили рёбра и добавили их же, но уже с другими весами
                helpListOfAvailableEdges.addAll(Q1);
                // создаем новый компонент решения со своим новым начальным графом и новым множеством доступных ребер
                nextComponents.add(new DecisionComponent(this.probability, alghoritm, helpGraph, helpListOfAvailableEdges));
            }
        }
        minSpanningTree = null;
        if (nextComponents.size() == 0){
            minSpanningTree = new IntervalGraph(graph);
            minSpanningTree.setProbability(this.probability);
            StringBuilder s = new StringBuilder();
            for (IntervalEdge edge: minSpanningTree.getEdges()) {
                s.append("\n V");
                s.append(edge.getA().getNumber());
                s.append(" V");
                s.append(edge.getB().getNumber());
                s.append(": ");
                s.append(edge.getIntervalWeight().getStart());
                s.append(" - ");
                s.append(edge.getIntervalWeight().getEnd());
            }
            System.out.println("\n вероятность " + minSpanningTree.getProbability() + "\n" + s);
        }
    }

    public double getProbability(double probability, IntervalEdge edge, List<Interval> partitionFor_e_q, List<IntervalEdge> Q) {
        return Probability.countProbabilityOfE_q(probability, edge, partitionFor_e_q,Q);
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

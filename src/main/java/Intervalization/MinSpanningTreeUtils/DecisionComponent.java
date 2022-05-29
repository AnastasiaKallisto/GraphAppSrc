package Intervalization.MinSpanningTreeUtils;

import Intervalization.IntervalEdge;
import Intervalization.IntervalGraph;
import Intervalization.Alghoritms.IntervalGraphAlghoritm;

import java.util.ArrayList;

public class DecisionComponent {
    private double probability; // вероятность попасть в этот компонент дерева решений
    private ArrayList<DecisionComponent> nextComponents; // варианты компонентов, в которые мы можем прийти отсюда
    private IntervalGraph minSpanningTree; // null если не дошли до листа дерева решений, иначе - задано


    public DecisionComponent(double probability,
                             IntervalGraphAlghoritm alghoritm,
                             IntervalGraph graph,
                             ArrayList<IntervalEdge> availableEdges) {
        this.probability = probability; //где-то тут считается нормально вероятность
        this.nextComponents = new ArrayList<>();
        minSpanningTree = null;
        IntervalGraph helpGraph;
        ArrayList<IntervalEdge> helpListOfAvailableEdges = new ArrayList<>(availableEdges);

        // Теперь получим множество следующих потенциальных ребер, множество Q
        for (IntervalEdge edge: alghoritm.getNextEdges(graph, availableEdges)) {
            helpGraph = new IntervalGraph(graph);
            helpGraph.addEdge(edge);
            helpListOfAvailableEdges.remove(edge);
            nextComponents.add(new DecisionComponent(probability, alghoritm, helpGraph, helpListOfAvailableEdges));
            helpListOfAvailableEdges.add(edge);
        }
        // т.е. мы один раз вызвали конструктор и по сути задали сразу все дерево решений
        // промежуточные результаты в принципе хранить незачем, но исправить это несложно,
        // достаточно поменять название minSpanningTree на более подходящее и убрать условие (ниже)
        if (nextComponents.size() == 0){
            minSpanningTree = graph;
        }
    }

    public double getProbability() {
        return probability;
    }

    public ArrayList<IntervalGraph> getDecisions(){
        ArrayList<IntervalGraph> graphs = new ArrayList<>(); // множество деревьев, из них формируется ответ на задачу
        if (this.nextComponents.size() == 0){
            graphs.add(this.minSpanningTree); // добавляем дерево из листа
        }
        for (DecisionComponent decisionComponent: nextComponents){ // закапываемся вглубь, мы не в листе, решений пока не видно
            graphs.addAll(decisionComponent.getDecisions());
        }
        return graphs;
    }
}

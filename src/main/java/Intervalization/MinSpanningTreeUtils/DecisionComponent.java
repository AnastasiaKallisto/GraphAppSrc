package Intervalization.MinSpanningTreeUtils;

import Intervalization.IntervalEdge;
import Intervalization.IntervalGraph;
import Intervalization.Alghoritms.IntervalGraphAlghoritm;

import java.util.ArrayList;

public class DecisionComponent {
    private double probability; // вероятность попасть в этот компонент дерева решений
    private ArrayList<DecisionComponent> nextComponents; // варианты компонентов, в которые мы можем прийти отсюда
    private IntervalGraph minSpanningTree; // null если не дошли до листа дерева решений, иначе - это мин.остовное дерево (одно из)


    public DecisionComponent(double probability,
                             IntervalGraphAlghoritm alghoritm,
                             IntervalGraph graph,
                             ArrayList<IntervalEdge> availableEdges) {
        this.nextComponents = new ArrayList<>();
        minSpanningTree = null;
        this.probability = probability;
        //где-то тут считается нормально вероятность
        // кстати, если она слишком маленькая, можно на этом закончить создание объекта
        IntervalGraph helpGraph;
        ArrayList<IntervalEdge> helpListOfAvailableEdges;

        // Теперь получим множество следующих потенциальных ребер, множество Q
        for (IntervalEdge edge: alghoritm.getNextEdges(graph, availableEdges)) {
            helpGraph = new IntervalGraph(graph);
            helpListOfAvailableEdges = new ArrayList<>(availableEdges);

            // в граф добавили ребро
            helpGraph.addEdge(edge);
            // это ребро больше не появится в списке доступных
            helpListOfAvailableEdges.remove(edge);
            // удаляем те ребра, что дадут цикл - они нам больше не понадобятся
            helpListOfAvailableEdges = alghoritm.returnOnlyNeсessaryEdges(helpGraph,helpListOfAvailableEdges);
            // подрезаем ребра
            helpListOfAvailableEdges = IntervalGraphAlghoritm.cutEdges(edge, helpListOfAvailableEdges);
            // создаем новый компонент решения со своим новым начальным графом и множеством доступных ребер
            nextComponents.add(new DecisionComponent(probability, alghoritm, helpGraph, helpListOfAvailableEdges));
        }
        // т.е. мы один раз вызвали конструктор и задали сразу все дерево решений
        // промежуточные результаты хранить незачем, но исправить это несложно,
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

package Intervalization.Alghoritms;
import Intervalization.IntervalEdge;
import Intervalization.IntervalGraph;
import java.util.ArrayList;

public abstract class IntervalGraphAlghoritm {
    /**
     * Поскольку availableEdges это ссылка, не следует просто так удалять из этого списка ребра,
     * которые на каком-то этапе будут давать цикл
     * т.к. позже алгоритм вернется в более высокую ветвь решения, где это ребро уже не дает цикл
     * **/
    public abstract ArrayList<IntervalEdge> getNextEdges(IntervalGraph graph, ArrayList<IntervalEdge> availableEdges);
}

package Intervalization.Alghoritms;

import Intervalization.IntervalEdge;
import Intervalization.IntervalGraph;

import java.util.ArrayList;

public abstract class IntervalGraphAlghoritm {
    public static ArrayList<IntervalEdge> cutEdges(IntervalEdge edge, ArrayList<IntervalEdge> edges) {
        ArrayList<IntervalEdge> answer = new ArrayList<>(edges);
        answer.sort(IntervalEdge::compareTo);
        int leftBorder = edge.getIntervalWeight().getStart();
        for (int i = 0; i < answer.size(); i++) {
            if (answer.get(i).getIntervalWeight().getStart() < leftBorder) {
                answer.get(i).setStart(leftBorder);
            }
        }
        return answer;
    }

    /**
     * Поскольку availableEdges это ссылка, не следует просто так удалять из этого списка ребра,
     * которые на каком-то этапе будут давать цикл
     * т.к. позже алгоритм вернется в более высокую ветвь решения, где это ребро уже не дает цикл
     **/
    public abstract ArrayList<IntervalEdge> getNextEdges(IntervalGraph graph, ArrayList<IntervalEdge> availableEdges);

    public abstract ArrayList<IntervalEdge> returnOnlyNeсessaryEdges(IntervalGraph helpGraph, ArrayList<IntervalEdge> edges);
}

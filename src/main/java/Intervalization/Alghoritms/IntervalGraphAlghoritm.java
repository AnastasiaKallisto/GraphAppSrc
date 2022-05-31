package Intervalization.Alghoritms;

import Intervalization.IntervalEdge;
import Intervalization.IntervalGraph;

import java.util.ArrayList;
import java.util.List;

public abstract class IntervalGraphAlghoritm {
    //в этот метод идем уже после избавления от лишних ребер, которые могли бы дать цикл
    public static List<IntervalEdge> cutEdges(IntervalEdge edge, List<IntervalEdge> edges) {
        List<IntervalEdge> answer = new ArrayList<>(edges);
        answer.sort(IntervalEdge::compareToRight);
        // это правая граница
        int rightBorder = edge.getIntervalWeight().getEnd();
        for (int i = 0; i < answer.size(); i++) {
            if (answer.get(i).getIntervalWeight().getStart() < rightBorder) {
                answer.get(i).setStart(rightBorder);
            }
        }
        return answer;
    }

    public abstract List<IntervalEdge> getNextEdges(IntervalGraph graph, List<IntervalEdge> availableEdges);
}

package Intervalization.Alghoritms;

import Intervalization.IntervalEdge;
import Intervalization.IntervalGraph;

import java.util.ArrayList;
import java.util.List;

public abstract class IntervalGraphAlghoritm {
    /**
     *
     * @param minEdge - выбранное ребро (для которого есть веса левее минимальной правой границы,
     *                в некотором роде минимальное ребро)
     * @param edges - все доступные ребра
     * @return - список ребер с подрезанными слева весами
     * + в этом списке вес выбранного ребра обрезается справа.
     * если его максимальный вес больше минимальной правой границы,
     * то он примет ее значение
     * теперь их минимальный вес совпадает или больше, чем минимальный вес ребра minEdge
     */
    public static List<IntervalEdge> cutEdges(IntervalEdge minEdge, int minRightBorder, List<IntervalEdge> edges) {
        List<IntervalEdge> answer = new ArrayList<>(edges);
        answer.sort(IntervalEdge::compareToLeft);
        int leftBorder = minEdge.getIntervalWeight().getStart();
        for (IntervalEdge edge : answer) {
            if (edge.getIntervalWeight().getStart() < leftBorder) {
                edge.setStart(leftBorder);
            } else {
                break;
            }
        }
        answer.remove(minEdge);
        IntervalEdge newMinEdge = new IntervalEdge(minEdge);
        if (newMinEdge.getIntervalWeight().getEnd() > minRightBorder) {
            newMinEdge.setEnd(minRightBorder);
        }
        return answer;
    }

    /**
     *
     * @param graph
     * @param availableEdges - ВСЕ ребра, которые мы можем когда-нибудь добавить в граф
     * @return Список ребер, которые могут быть добавлены в граф
     * Эти ребра не дают цикл при их добавлении в граф
     * И являются в некотором роде "минимальными"
     * т.е. имеют веса левее минимальной правой границы
     * В процессе нахождения этих ребер изменяется список availableEdges
     * Из него также удаляется часть ребер, которые дают цикл на данном этапе
     * Раз они уже дают цикл, значит, давали бы и во всех следующих задачах
     * А значит, их можно сразу убрать.
     * Но удаляется лишь часть из тех, с которыми методу вообще приходилось взаимодействовать, а не все
     */
    public abstract List<IntervalEdge> getNextEdges(IntervalGraph graph, List<IntervalEdge> availableEdges);
}

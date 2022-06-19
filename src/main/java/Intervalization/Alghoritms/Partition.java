package Intervalization.Alghoritms;

import Intervalization.Interval;
import Intervalization.IntervalEdge;

import java.util.ArrayList;
import java.util.List;

public class Partition {
    /**
     *
     * @param Q - множество рёбер без обрезанных весов, отсортировано по левой границе
     * @param minRightBorder - мин правая граница среди всех правых границ весов ребер из Q
     * @return - отсортированный список всех левых границ кусочков и minRightBorder, без повторений
     */
    public static List<Integer> getBordersForPartitionOfQ(List<IntervalEdge> Q, int minRightBorder){
        List<Integer> answer = new ArrayList<>();
        int a;
        for(IntervalEdge edge: Q){
            a = edge.getIntervalWeight().getStart();
            if (!answer.contains(a)){
                answer.add(a);
            }
        }
        if (!answer.contains(minRightBorder)){
            answer.add(minRightBorder);
        }
        return answer;
    }

    /**
     *
     * @param e_q - ребро с уже обрезанным весом
     * @param bordersForPartitionOfQ - список возможных границ кусочков без повторений
     * @param partitionForQ - все элементы разбиения весов, которые только встречаются среди ребер из Q
     * @return - список элементов разбиения веса ребра e_q
     */
    public static List<Interval> getPartitionFor_e_q(IntervalEdge e_q,
                                                     List<Integer> bordersForPartitionOfQ,
                                                     List<Interval> partitionForQ){
        List<Interval> answer = new ArrayList<>();
        int startIndex = bordersForPartitionOfQ.indexOf(e_q.getIntervalWeight().getStart());
        int endIndex = bordersForPartitionOfQ.size()-1;
        for (int i = startIndex; i < endIndex; i++) {
            answer.add(partitionForQ.get(i));
        }
        return answer;
    }

    /**
     *
     * @param bordersForPartitionOfQ - список возможных границ кусочков без повторений
     * @return - все элементы разбиения весов, которые только встречаются среди ребер из Q
     */
    public static List<Interval> getPartitionForQ(List<Integer> bordersForPartitionOfQ){
        List<Interval> answer = new ArrayList<>();
        for (int i = 0; i < bordersForPartitionOfQ.size()-1; i++) {
            answer.add(new Interval(bordersForPartitionOfQ.get(i), bordersForPartitionOfQ.get(i+1)));
        }
        return answer;
    }
}

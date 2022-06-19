package Intervalization.Alghoritms;

import Intervalization.Interval;
import Intervalization.IntervalEdge;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Probability {
    /**
     * list_of_v_q_j отсортировано (по левой границе, или по правой - результат один и тот же)
     * Q отсортировано по левой границе
     * @param e_q           - ребро e_q
     * @param list_of_v_q_j - элементы разбиения интервального веса ребра e_q(кусочки веса v_q_j)
     * @param Q             - множество рёбер, которые имеют веса левее мин. правой границы,
     *                      и которые при определенном раскладе могут быть добавлены в граф
     * @return вероятность выбора ребра e_q среди всех остальных рёбер из Q
     */
    public static double countProbabilityOfE_q(double prevProbability, IntervalEdge e_q,
                                               List<Interval> list_of_v_q_j, List<IntervalEdge> Q) {
        double probability = 0;
        for (Interval v_q_j: list_of_v_q_j) {
            List<Integer> R = getR_for_v_q_j(v_q_j, Q.indexOf(e_q),Q);
            probability+=count_P_e_q_if_weight_q_in_v_q_j(R,v_q_j,Q)*count_P_v_q_in_v_q_j(v_q_j, e_q.getIntervalWeight());
        }
        probability*=prevProbability;
        return probability;
    }

    // геом. вероятность, что точный вес ребра окажется именно в этом кусочке веса
    // формула, кстати, совпадает с расчетом P_i_j
    public static double count_P_v_q_in_v_q_j(Interval v_q_j, Interval v_q){
        return (v_q_j.getEnd() - v_q_j.getStart()) / (double) (v_q.getEnd() - v_q.getStart());
    }

    /**
     * @param v_q_j - элемент разбиения веса ребра e_q с индексом j
     * @param v_i   - вес ребра e_i (i берется из r, r - подмножество R)
     * @return разность макс. и мин. значений v_q_j, деленая на разность макс. и мин. значений v_i
     * это вероятность того, что точный вес ребра e_i окажется в пределах интервала v_g_j
     */
    public static double count_P_i_j(Interval v_q_j, Interval v_i) {
        return (v_q_j.getEnd() - v_q_j.getStart()) / (double) (v_i.getEnd() - v_i.getStart());
    }

    /**
     * @param v_q_j - элемент разбиения веса ребра e_q с индексом j
     * @param v_i   - вес ребра e_i (i берется из R\r, r - подмножество R)
     * @return разность макс. значения v_i и макс. значения v_q_j, деленая на разность макс. и мин. значений v_i
     * это вероятность того, что точный вес ребра e_i окажется больше, чем макс. знач. интервала v_g_j
     * (и меньше чем макс. вес v_i, очевидно)
     */
    public static double count_Q_i_j(Interval v_q_j, Interval v_i) {
        return (v_i.getEnd() - v_q_j.getEnd()) / (double) (v_i.getEnd() - v_i.getStart());
    }


    /**
     * R рассчитывается для v_q_j, r - одно из подмножеств R
     * Подразумевается, что если дошли до вызова этой функции, то R не пустое
     * Q должно быть отсортировано по левой границе
     *
     * @param r     - подмножество индексов R. смысл r в том, что e_i (i из r) имеют вес меньший, чем точный вес ребра e_q
     * @param R     - общее мн-во индексов i таких, что i != q, e_i in Q, v_q_j in в v_q_i
     * @param v_q_j - элемент разбиения веса ребра e_q  и индексом j
     * @param Q     - множество рёбер, которые имеют веса левее мин. правой границы,
     *              и которые при определенном раскладе могут быть добавлены в граф
     * @return - вероятность того, что вес рёбер из r окажется меньше, чем вес тех, что R\r
     */
    public static double count_P_r(Set<Integer> r, List<Integer> R, Interval v_q_j, List<IntervalEdge> Q) {
        double resOfMultiplyP_i_j = 1;
        double resOfMultiplyQ_i_j = 1;
        Set<Integer> Rminusr = new HashSet<>(R);
        Rminusr.removeAll(r);
        for (int i : Rminusr) {
            resOfMultiplyQ_i_j *= count_Q_i_j(v_q_j, Q.get(i).getIntervalWeight());
        }
        for (int i : r) {
            resOfMultiplyP_i_j *= count_P_i_j(v_q_j, Q.get(i).getIntervalWeight());
        }
        return resOfMultiplyP_i_j * resOfMultiplyQ_i_j;
    }

    /**
     * R - отсортировано
     * Q - отсортировано по левой границе
     *
     * @param R     - множество индексов i таких, что i != q, e_i in Q, v_q_j in в v_q_i
     * @param v_q_j - элемент разбиения веса ребра e_q  и индексом j
     * @param Q     - множество рёбер, которые имеют веса левее мин. правой границы,
     *              и которые при определенном раскладе могут быть добавлены в граф
     * @return - вероятность выбора ребра e_q из всех ребер из Q, при условии, что
     * точный вес ребра e_q из интервала v_q_j
     */
    public static double count_P_e_q_if_weight_q_in_v_q_j(List<Integer> R, Interval v_q_j, List<IntervalEdge> Q) {
        // нужно множество всех подмножеств
        // переберем циклом все числа от 1 до 2^(n+1) не включительное, получим их двоичные записи
        // переведем их в массивы, если значение элемента массива 1, то
        // в r можно добавить элемент множества R с таким же индексом и получать множества r
        int n = R.size();
        if (n == 0){
            return 1;
        }
        double answer = 0;
        for (int i = 1; i < Math.pow(2, n); i++) {
            int[] binarValue = getBinar(i, n);
            Set<Integer> r = new HashSet<>();
            for (int j = 0; j < n; j++) {
                if (binarValue[j] == 1) {
                    r.add(R.get(j));
                }
            }
            answer += count_P_r(r, R, v_q_j, Q) / (r.size() + 1);
        }
        return answer;
    }

    /**
     * Q - отсортировано по левой границе
     *
     * @param v_q_j - элемент разбиения веса ребра e_q  и индексом j
     * @param Q     - множество рёбер, которые имеют веса левее мин. правой границы,
     *              и которые при определенном раскладе могут быть добавлены в граф
     * @param q     - индекс ребра e_q
     * @return - R - множество индексов i таких, что i != q, e_i in Q, v_q_j in в v_q_i
     */
    public static List<Integer> getR_for_v_q_j(Interval v_q_j, Integer q, List<IntervalEdge> Q) {
        List<Integer> R = new ArrayList<>();
        for (int i = 0; i < Q.size(); i++) {
            if (i != q) {
                // e_i in Q - очевидно, т.к. мы идем по элементам множества Q
                Interval e_i_weight = Q.get(i).getIntervalWeight();
                // содержится ли этот кусочек веса внутри интерв. веса ребра e_i?
                if (e_i_weight.getStart() <= v_q_j.getStart() &&
                        e_i_weight.getEnd() >= v_q_j.getEnd()) {
                    R.add(i);
                } else {
                    // значит, таких рёбер, вес которых включал бы этот кусочек, больше нет
                    // их левая граница больше левой границы этого кусочка
                    // и все последующие будут больше, либо равны, т.к. Q отсортировано по левой границе
                    break;
                }
            }
        }
        // R - отсортированное множество индексов, удовл. условиям
        return R;
    }

    public static int[] getBinar(int a, int n) {
        String s = Integer.toBinaryString(a);
        int[] answer = new int[n];
        for (int i = 0; i < s.length(); i++) {
            answer[n - 1 - i] = Character.getNumericValue(s.charAt(s.length() - 1 - i));
        }
        return answer;
    }
}

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;

import java.util.Comparator;
import java.util.HashSet;
import java.util.TreeSet;

public class GraphGenerator {
    private MultiValuedMap<Integer, Integer> edges;

    public GraphGenerator(int n) {
        edges = new HashSetValuedHashMap();
        Integer current = 1;
        Integer prev = current;
        TreeSet<Integer> vertexes = new TreeSet<>();
        vertexes.add(current);
        while (current < n) {
            if (Double.compare(Math.random(), 0.6) > 0) {
                prev = current;
                current = vertexes.stream().max(Integer::compareTo).get() + 1;
                vertexes.add(current);
                edges.put(prev, current);
            } else {
                current = prev;
                if (prev > 1) {
                    prev--;
                }
            }
        }
    }

    public MultiValuedMap<Integer, Integer> getEdges() {
        return edges;
    }
}

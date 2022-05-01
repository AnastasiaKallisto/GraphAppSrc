package GraphWork;

import java.util.*;

public class Graph {
    private Set<Edge> edges;
    private TreeMap<Integer, Vertex> vertices;

    public Graph(Map<Integer, Vertex> vertexMap) {
        vertices = new TreeMap<>();
        edges = new HashSet<>();
        Integer current = 1;
        Integer prev = current;
        List<Integer> listOfNumbers = new ArrayList<>();
        listOfNumbers.add(current);
        vertices.put(current, vertexMap.get(current));
        int n = vertexMap.size();
        double c = 1;
        while (current < n) {
            if (Math.random() > 0.4) {
                prev = current;
                current = vertices.lastKey()+1;
                vertices.put(current,vertexMap.get(current));
                listOfNumbers.add(current);
                c+=1;
                edges.add(new Edge(vertexMap.get(prev), vertexMap.get(current), c));
            } else {
                if (prev > 1) {
                    prev = listOfNumbers.get((int)Math.floor(Math.random()*(listOfNumbers.size()-1)));
                }
                current = prev;
            }
        }
        //усложним граф
        for (int i = 1; i < (int) (Math.random() * n) + 1; i++) {
            current = (int) (Math.random() * n);
            prev = (int) (Math.random() * n);
            if (prev != current && prev != 0 && current != 0) {
                c+=1;
                if (!edges.contains(new Edge(vertexMap.get(prev), vertexMap.get(current), c))){
                    edges.add(new Edge(vertexMap.get(prev), vertexMap.get(current), c));
                }
            }
        }
    } //номер, вершина

    public Graph(TreeMap<Integer, Vertex> vertexMap, HashSet<Edge> edges){
        this.edges = edges;
        this.vertices = vertexMap;
    }



    public Set<Edge> getEdges() {
        return edges;
    }
}

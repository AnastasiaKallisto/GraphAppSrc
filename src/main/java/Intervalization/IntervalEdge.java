package Intervalization;

import GraphWork.Vertex;
import Intervalization.MinSpanningTreeUtils.DecisionComponent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

public class IntervalEdge  {
    private ArrayList<Vertex> vertices;
    private Interval intervalWeight;

    public IntervalEdge(Vertex a, Vertex b, int start, int end) {
        vertices = new ArrayList<>();
        vertices.add(a);
        vertices.add(b);
        intervalWeight = new Interval(start, end);
    }

    public IntervalEdge(Vertex a, Vertex b, Interval interval) {
        vertices = new ArrayList<>();
        vertices.add(a);
        vertices.add(b);
        intervalWeight = new Interval(interval.getStart(), interval.getEnd());
    }

    public Interval getIntervalWeight() {
        return intervalWeight;
    }

    public Vertex getA(){
        return vertices.get(0);
    }

    public Vertex getB(){
        return vertices.get(1);
    }

    public ArrayList<Vertex> getVertices() {
        return vertices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntervalEdge that = (IntervalEdge) o;
        return vertices.equals(that.vertices) && intervalWeight.equals(that.intervalWeight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(new HashSet<>(vertices));
    }

    public int compareTo(IntervalEdge edge) {
        return Integer.compare(this.getIntervalWeight().getEnd(), edge.getIntervalWeight().getEnd());
    }
}
package Intervalization;

public class Interval {
    private int start;
    private int end;

    Interval() {
        start = 0;
        end = 0;
    }

    Interval(int s, int e) {
        start = s;
        end = e;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}

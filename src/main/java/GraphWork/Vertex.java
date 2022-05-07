package GraphWork;

import java.util.Objects;

public class Vertex {
    private int x;
    private int y;
    private int number;

    public Vertex(int x, int y, int number) {
        this.x = x;
        this.y = y;
        this.number = number;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return x == vertex.x && y == vertex.y && number == vertex.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, number);
    }

    @Override
    public String toString() {
        return "V" + number; //  + "(" + x + ", " + y + ")"
    }
}

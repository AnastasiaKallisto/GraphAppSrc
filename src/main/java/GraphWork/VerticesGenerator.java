package GraphWork;

import GraphWork.Vertex;

import java.util.HashMap;

public class VerticesGenerator {
    private HashMap<Integer, Vertex> vertices = new HashMap<>();

    public VerticesGenerator(int n, int sizeFrameX, int sizeFrameY) {
        int centerX = sizeFrameX/2;
        int centerY = sizeFrameY/2;
        int radius = (int) (centerY*0.9);
        for (int i = 1; i <= n; i++) {
            vertices.put(i,new Vertex((int) (centerX+radius*Math.cos(i*Math.PI*2/n)),(int)(centerY+radius*Math.sin(i*Math.PI*2/n)),i));
        }
    }

    public HashMap<Integer,Vertex> getVertices() {
        return vertices;
    }
}

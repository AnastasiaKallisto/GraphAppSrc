import java.util.ArrayList;
import java.util.List;

public class GraphVertexes {
    // Граф будет в виде точек, расположенных на окружности
    // первая точка - справа, там где 0 градусов на окружности
    // остальные согласно расположению точек на правильном n-угольнике
    // угол поворота - 360/n
    // итоговый угол для k-й вершины - 360/n * k
    // у этого угла есть sin и cos, которые можно умножить на радиус окружности
    // и получить координаты для смещения от центра окружности (o1,o2)
    // для получения итоговых координат точек (x_i, y_i)
    // координаты сохраняются в массив вершин
    // вершина - номер вершины, координата х, координата У
    // нумерация с 1 начиная с правой крайней и против часовой

    //реализация вычислений координат вершин графа должна быть отдельно от его внутреннего устройства
    // и отдельно от окна, в котором рисуется все приложение

    private List<Vertex> vertexes = new ArrayList<Vertex>();

    public GraphVertexes(int n, int sizeFrameX, int sizeFrameY) {
        int centerX = sizeFrameX/2;
        int centerY = sizeFrameY/2;
        int radius = (int) (centerY*0.9);
        for (int i = 1; i <= n; i++) {
            vertexes.add(new Vertex((int) (centerX+radius*Math.cos(i*Math.PI*2/n)),(int)(centerY+radius*Math.sin(i*Math.PI*2/n)),i));
        }
    }

    public List<Vertex> getVertexes() {
        return vertexes;
    }
}

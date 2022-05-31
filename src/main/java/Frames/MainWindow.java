package Frames;

import GraphWork.*;
import Intervalization.IntervalGraph;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class MainWindow extends JFrame {
    private static final int sizeX = Toolkit.getDefaultToolkit().getScreenSize().width;
    private static final int sizeY = Toolkit.getDefaultToolkit().getScreenSize().height;

    private EnterQuantityFrame enterQuantityFrame1;
    private JButton generateGraphButton1;
    private JButton primButton1;
    private JButton kruskalButton1;
    private JButton clearButton1;
    private JPanel buttonsPanel1;
    private JPanel tabsPanel1;

    private EnterQuantityFrame enterQuantityFrame2;
    private JButton generateGraphButton2;
    private JButton primButton2;
    private JButton kruskalButton2;
    private JButton clearButton2;
    private JPanel buttonsPanel2;
    private JPanel tabsPanel2;

    private JTabbedPane tabbedPane;
    private Graphics2D graphics2D;

    private static Integer quantityOfVerticesForExactGraph;
    private Graph exactGraph;
    private ArrayList<Edge> exactMinSpanningTreeKruskal;
    private ArrayList<Edge> exactMinSpanningTreePrim;
    private boolean isExactGraphPainted;
    private boolean isExactMinSpanningTreePaintedKruskal;
    private boolean isExactMinSpanningTreePaintedPrim;

    private static Integer quantityOfVerticesForIntervalGraph;
    private IntervalGraph intervalGraph;
    private ArrayList<Edge> intervalMinSpanningTreeKruskal;
    private ArrayList<Edge> intervalMinSpanningTreePrim;
    private boolean isIntervalGraphPainted;
    private boolean isIntervalMinSpanningTreePaintedKruskal;
    private boolean isIntervalMinSpanningTreePaintedPrim;

    public static void main(String[] args) {
        JFrame window = new MainWindow(); //при создании элемента метод paint вызывается автоматически
    }

    MainWindow() {
        super("Graph Alghoritms");
        setSize(sizeX, sizeY);
        quantityOfVerticesForExactGraph = null;
        isExactGraphPainted = false;

        enterQuantityFrame1 = new EnterQuantityFrame(this);
        enterQuantityFrame2 = new EnterQuantityFrame(this);
        tabsPanel1 = new JPanel();
        tabsPanel2 = new JPanel();
        tabbedPane = new JTabbedPane();

        buttonsPanel1 = new JPanel(new GridLayout(15, 1, 10, 10));
        generateGraphButton1 = new JButton("Нарисовать граф");
        primButton1 = new JButton("Запустить алгоритм Прима");
        kruskalButton1 = new JButton("Запустить алгоритм Краскала");
        clearButton1 = new JButton("Очистить");
        buttonsPanel1.add(generateGraphButton1);
        buttonsPanel1.add(primButton1);
        buttonsPanel1.add(kruskalButton1);
        buttonsPanel1.add(clearButton1);
        tabsPanel1.add(buttonsPanel1, BorderLayout.WEST);

        buttonsPanel2 = new JPanel(new GridLayout(15, 1, 10, 10));
        generateGraphButton2 = new JButton("Нарисовать граф");
        primButton2 = new JButton("Запустить алгоритм Прима");
        kruskalButton2 = new JButton("Запустить алгоритмgfhfgj Краскала");
        clearButton2 = new JButton("Очистить");
        buttonsPanel2.add(generateGraphButton2);
        buttonsPanel2.add(primButton2);
        buttonsPanel2.add(kruskalButton2);
        buttonsPanel2.add(clearButton2);
        tabsPanel2.add(buttonsPanel2, BorderLayout.WEST);

        tabbedPane.addTab("Точные веса " , tabsPanel1);
        tabbedPane.addTab("Интервальные веса " , tabsPanel2);
        this.getContentPane().add(tabbedPane, BorderLayout.NORTH);
        //this.getContentPane().add(buttonsPanel, BorderLayout.WEST);
        exactGraph = null;
        exactMinSpanningTreeKruskal = null;
        exactMinSpanningTreePrim = null;
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void paint(Graphics g) {
        super.paint(g);
        isExactMinSpanningTreePaintedKruskal = false;
        isExactMinSpanningTreePaintedPrim = false;
        graphics2D = (Graphics2D) getGraphics();
        generateGraphButton1.addActionListener(e -> {
            if (!isExactGraphPainted) {
                enterQuantityFrame1.setVisible(true);
            }
        });
        clearButton1.addActionListener(e -> {
            repaint();
            isExactGraphPainted = false;
            enterQuantityFrame1.setQuantityNull(); // костыль
        });
        kruskalButton1.addActionListener(e -> {
            if (isExactGraphPainted) {
                if (!isExactMinSpanningTreePaintedKruskal) {
                    exactMinSpanningTreeKruskal = GraphAlghoritms.returnMinSpanningTreeKruskal(exactGraph);
                    paintMinSpanningTree(5, exactMinSpanningTreeKruskal, Color.RED);
                }
            }
        });
        primButton1.addActionListener(e -> {
            if (isExactGraphPainted) {
                if (!isExactMinSpanningTreePaintedPrim) {
                    exactMinSpanningTreePrim = GraphAlghoritms.returnMinSpanningTreePrim(exactGraph);
                    paintMinSpanningTree(-5, exactMinSpanningTreePrim, Color.BLUE);
                }
            }
        });
        paintGraph(g);
    }

    public boolean isGraphPainted() {
        return isExactGraphPainted;
    }

    public void paintGraph(Graphics g) {
        if (isExactGraphPainted) {
            return;
        }
        quantityOfVerticesForExactGraph = enterQuantityFrame1.getQuantity();
        if (quantityOfVerticesForExactGraph == null) {
            return;
        }
        exactGraph = new Graph(quantityOfVerticesForExactGraph, sizeX, sizeY);
        for (Vertex vertex : exactGraph.getVertices().values()) {
            g.fillOval(vertex.getX(), vertex.getY(), 5, 5);
            g.drawString(vertex.toString(), vertex.getX(), vertex.getY() + 20);
        }
        int x1, y1, x2, y2;
        g.setColor(Color.gray);
        for (Edge edge : exactGraph.getEdges()) {
            x1 = edge.getA().getX();
            y1 = edge.getA().getY();
            x2 = edge.getB().getX();
            y2 = edge.getB().getY();
            graphics2D.drawLine(x1, y1, x2, y2);
            g.drawString(String.valueOf(edge.getWeight()), (x1 + x2) / 2 - 10, (y1 + y2) / 2 - 10);
        }
        g.setColor(Color.BLACK);
        isExactGraphPainted = true;
    }

    public void paintMinSpanningTree(int offset, ArrayList<Edge> minSpanningTree, Color color) {
        if (minSpanningTree == exactMinSpanningTreePrim) {
            if (isExactMinSpanningTreePaintedPrim) {
                return;
            }
            isExactMinSpanningTreePaintedPrim = true;
        }
        if (minSpanningTree == exactMinSpanningTreeKruskal) {
            if (isExactMinSpanningTreePaintedKruskal) {
                return;
            }
            isExactMinSpanningTreePaintedKruskal = true;
        }
        int x1, x2, y1, y2;
        graphics2D.setColor(color);
        graphics2D.setStroke(new BasicStroke(2));
        graphics2D.setFont(new Font("TimesRoman", Font.PLAIN, 13));
        Edge edge;
        for (int i = 0; i < minSpanningTree.size(); i++) {
            edge = minSpanningTree.get(i);
            x1 = edge.getA().getX() + offset;
            y1 = edge.getA().getY() + offset;
            x2 = edge.getB().getX() + offset;
            y2 = edge.getB().getY() + offset;
            graphics2D.drawLine(x1, y1, x2, y2);
            graphics2D.drawString("(" + (i + 1) + ": " + edge.getWeight() + ")", (x1 + x2) / 2 + 5 * offset, (y1 + y2) / 2 - 20);
        }
        graphics2D.setColor(Color.BLACK);
    }
}
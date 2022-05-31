package Frames;

import GraphWork.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class WindowForExactGraph extends JFrame {
    private static final int sizeX = Toolkit.getDefaultToolkit().getScreenSize().width;
    private static final int sizeY = Toolkit.getDefaultToolkit().getScreenSize().height;
    private EnterQuantityFrame enterQuantityFrame;
    private JButton generateGraphButton;
    private JButton primButton;
    private JButton crascalButton;
    private JButton clearButton;
    private JPanel buttonsPanel;

    private Graphics2D graphics2D;

    private static Integer quantityOfVertices;
    private Graph graph;
    private ArrayList<Edge> minSpanningTreeCrascal;
    private ArrayList<Edge> minSpanningTreePrim;
    private boolean isGraphPainted;
    private boolean isMinSpanningTreePaintedCrascal;
    private boolean isMinSpanningTreePaintedPrim;

    public static void main(String[] args) {
        JFrame window = new WindowForExactGraph(); //при создании элемента метод paint вызывается автоматически
    }

    WindowForExactGraph() {
        super("Graph Alghoritms");
        setSize(sizeX, sizeY);
        quantityOfVertices = null;
        isGraphPainted = false;

        enterQuantityFrame = new EnterQuantityFrame(this);
        buttonsPanel = new JPanel(new GridLayout(15, 1, 10, 10));
        generateGraphButton = new JButton("Нарисовать граф");
        primButton = new JButton("Запустить алгоритм Прима");
        crascalButton = new JButton("Запустить алгоритм Краскала");
        clearButton = new JButton("Очистить");
        buttonsPanel.add(generateGraphButton);
        buttonsPanel.add(primButton);
        buttonsPanel.add(crascalButton);
        buttonsPanel.add(clearButton);

        this.getContentPane().add(buttonsPanel, BorderLayout.WEST);

        graph = null;
        minSpanningTreeCrascal = null;
        minSpanningTreePrim = null;
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void paint(Graphics g) {
        super.paint(g);
        isMinSpanningTreePaintedCrascal = false;
        isMinSpanningTreePaintedPrim = false;
        graphics2D = (Graphics2D) getGraphics();
        generateGraphButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isGraphPainted) {
                    enterQuantityFrame.setVisible(true);
                    quantityOfVertices = enterQuantityFrame.getQuantity();
                }
            }
        });
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
                isGraphPainted = false;
                enterQuantityFrame.setQuantityNull(); // костыль
            }
        });
        crascalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isGraphPainted) {
                    if (!isMinSpanningTreePaintedCrascal) {
                        minSpanningTreeCrascal = GraphAlghoritms.returnMinSpanningTreeKruskal(graph);
                        paintMinSpanningTree(5, minSpanningTreeCrascal, Color.RED);
                    }
                }
            }
        });
        primButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isGraphPainted) {
                    if (!isMinSpanningTreePaintedPrim) {
                        minSpanningTreePrim = GraphAlghoritms.returnMinSpanningTreePrim(graph);
                        paintMinSpanningTree(-5, minSpanningTreePrim, Color.BLUE);
                    }
                }
            }
        });
        if (!isGraphPainted) {
            paintGraph(g);
        }
    }

    public void paintGraph(Graphics g) {
        quantityOfVertices = enterQuantityFrame.getQuantity();
        if (quantityOfVertices == null) {
            return;
        }
        graph = new Graph(quantityOfVertices, sizeX, sizeY);
        for (Vertex vertex : graph.getVertices().values()) {
            g.fillOval(vertex.getX(), vertex.getY(), 5, 5);
            g.drawString(vertex.toString(), vertex.getX(), vertex.getY() + 20);
        }
        int x1, y1, x2, y2;
        g.setColor(Color.gray);
        for (Edge edge : graph.getEdges()) {
            x1 = edge.getA().getX();
            y1 = edge.getA().getY();
            x2 = edge.getB().getX();
            y2 = edge.getB().getY();
            graphics2D.drawLine(x1, y1, x2, y2);
            g.drawString(String.valueOf(edge.getWeight()), (x1 + x2) / 2 - 10, (y1 + y2) / 2 - 10);
        }
        g.setColor(Color.BLACK);
        isGraphPainted = true;
    }

    public void paintMinSpanningTree(int offset, ArrayList<Edge> minSpanningTree, Color color) {
        if (minSpanningTree == minSpanningTreePrim) {
            if (isMinSpanningTreePaintedPrim) {
                return;
            }
            isMinSpanningTreePaintedPrim = true;
        }
        if (minSpanningTree == minSpanningTreeCrascal) {
            if (isMinSpanningTreePaintedCrascal) {
                return;
            }
            isMinSpanningTreePaintedCrascal = true;
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
package Frames;

import GraphWork.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.spec.ECGenParameterSpec;
import java.util.*;
import java.util.List;

public class MainWindow extends JFrame {
    private static final int sizeX = Toolkit.getDefaultToolkit().getScreenSize().width;
    private static final int sizeY = Toolkit.getDefaultToolkit().getScreenSize().height;
    private static Integer quantityOfVertices;
    private JButton generateGraphButton;
    private JButton primButton;
    private JButton crascalButton;
    private JButton clearButton;
    private JPanel buttonsPanel;
    private Map<Integer, Vertex> vertices;
    private Graphics2D graphics2D;
    private Set<Edge> graph;
    private ArrayList<Edge> minSpanningTree;
    private boolean isGraphPainted;
    private boolean isMinSpanningTreePainted;
    private EnterQuantityFrame enterQuantityFrame;

    public static void main(String[] args) {
        JFrame window = new MainWindow(); //при создании элемента метод paint вызывается автоматически
    }

    MainWindow() {
        super("Graph Alghoritms");
        setSize(sizeX, sizeY);
        quantityOfVertices = null;
        isGraphPainted = false;

        enterQuantityFrame = new EnterQuantityFrame(this);
        buttonsPanel = new JPanel(new GridLayout(15, 1, 10, 10));
        generateGraphButton = new JButton("Сгенерировать граф");
        primButton = new JButton("Запустить алгоритм Прима");
        crascalButton = new JButton("Запустить алгоритм Краскала");
        clearButton = new JButton("Очистить");
        buttonsPanel.add(generateGraphButton);
        buttonsPanel.add(primButton);
        buttonsPanel.add(crascalButton);
        buttonsPanel.add(clearButton);
        this.getContentPane().add(buttonsPanel, BorderLayout.WEST);
        graph = null;
        minSpanningTree = null;
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void paint(Graphics g) {
        super.paint(g);
        isMinSpanningTreePainted = false;
        graphics2D = (Graphics2D) getGraphics();
        generateGraphButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isGraphPainted) {
                    enterQuantityFrame.setVisible(true);
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
                    if (!isMinSpanningTreePainted) {
                        returnMinSpanningTreeCrascal();
                        paintMinSpanningTree();
                    }
                }
            }
        });
        paintGraph(g);
    }

    public boolean isGraphPainted() {
        return isGraphPainted;
    }

    public void paintGraph(Graphics g) {
        if (isGraphPainted == false) {
            quantityOfVertices = enterQuantityFrame.getQuantity();
            if (quantityOfVertices != null) {
                vertices = new VerticesGenerator(quantityOfVertices, sizeX, sizeY).getVertices();
                for (Vertex vertex : vertices.values()) {
                    g.fillOval(vertex.getX(), vertex.getY(), 5, 5);
                    g.drawString(vertex.toString(), vertex.getX(), vertex.getY() + 20);
                }
                graph = new Graph(vertices).getEdges();
                int x1, y1, x2, y2;
                setEdgesCapacity();
                g.setColor(Color.gray);
                for (Edge edge : graph) {
                    x1 = edge.getA().getX();
                    y1 = edge.getA().getY();
                    x2 = edge.getB().getX();
                    y2 = edge.getB().getY();
                    graphics2D.drawLine(x1, y1, x2, y2);
                    g.drawString(String.valueOf((int) edge.getC()), (x1 + x2) / 2 - 10, (y1 + y2) / 2 - 10);
                }
                g.setColor(Color.BLACK);
                isGraphPainted = true;
            }
        }
    }

    public void setEdgesCapacity() {
        for (Edge edge : graph) {
            edge.setC((int) (Math.random() * 100 + 1));
        }
    }

    public List<Edge> returnMinSpanningTreeCrascal() {
        minSpanningTree = new ArrayList<>();
        List<Edge> availableEdges = new ArrayList<>(graph);
        Edge edge;
        availableEdges.sort(Edge::compareTo);
        Set<Vertex> checkedVertices = new HashSet<>();
        Vertex a, b;

        for (int i = 0; i < availableEdges.size(); i++) {
            edge = availableEdges.get(i);
            a = edge.getA();
            b = edge.getB();
            if (!searchCircle(a, b, minSpanningTree, checkedVertices)) {
                minSpanningTree.add(edge);
            }
            checkedVertices.clear();
        }
        minSpanningTree.sort(Edge::compareTo);
        return minSpanningTree;
    }

    private boolean searchCircle(Vertex a, Vertex b, ArrayList<Edge> minSpanningTree, Set<Vertex> checkedVertices) {
        ArrayList<Edge> edgesFromA = new ArrayList<>();
        Set<Vertex> needToBeCheckedSet = new HashSet<>();
        for (Edge edge : minSpanningTree) {
            if (edge.getA().equals(a)) { // надо бы не a и b, а list из этих 2 вершин, или set, equals подозрительный
                if (edge.getB().equals(b)) {
                    return true;
                }
                if (!checkedVertices.contains(edge.getB())) {
                    needToBeCheckedSet.add(edge.getB());
                }
            }
            if (edge.getB().equals(a)) { // else?
                if (edge.getA().equals(b)) {
                    return true;
                }
                if (!checkedVertices.contains(edge.getA())) {
                    needToBeCheckedSet.add(edge.getA());
                }
            }
            edgesFromA.add(edge);
        }
        checkedVertices.add(a);// из нее точно не попадаем в b
        for (Vertex vertex : needToBeCheckedSet) {
            if (searchCircle(vertex, b, minSpanningTree, checkedVertices)) {
                return true;
            }
        }
        return false;
    }

    public void paintMinSpanningTree() {
        if (isMinSpanningTreePainted) {
            return;
        }
        int x1, x2, y1, y2;
        graphics2D.setColor(Color.RED);
        graphics2D.setStroke(new BasicStroke(5));
        graphics2D.setFont(new Font("TimesRoman", Font.PLAIN, 13));
        Edge edge;
        for (int i = 0; i < minSpanningTree.size(); i++) {
            edge = minSpanningTree.get(i);
            x1 = edge.getA().getX();
            y1 = edge.getA().getY();
            x2 = edge.getB().getX();
            y2 = edge.getB().getY();
            graphics2D.drawLine(x1, y1, x2, y2);
            graphics2D.drawString("(" + (i+1) + ": " + edge.getC() + ")", (x1 + x2) / 2 + 15, (y1 + y2) / 2 - 20);
        }
        graphics2D.setColor(Color.BLACK);
        isMinSpanningTreePainted = true;
    }
}
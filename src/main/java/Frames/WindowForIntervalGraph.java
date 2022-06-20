package Frames;

import GraphWork.*;
import Intervalization.Alghoritms.IntervalKruskalAlghoritm;
import Intervalization.Alghoritms.IntervalPrimAlghoritm;
import Intervalization.Interval;
import Intervalization.IntervalEdge;
import Intervalization.IntervalGraph;
import Intervalization.MinSpanningTreeUtils.DecisionComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.util.List;

public class WindowForIntervalGraph extends JFrame {
    private static final int sizeX = Toolkit.getDefaultToolkit().getScreenSize().width;
    private static final int sizeY = Toolkit.getDefaultToolkit().getScreenSize().height;
    private EnterQuantityFrame enterQuantityFrame;
    private JButton drawGraphFromFileButton;
    private JButton generateGraphButton;
    private JButton primButton;
    private JButton kruskalButton;
    private JButton clearButton;
    private JPanel buttonsPanel;


    private Graphics2D graphics2D;

    private static Integer quantityOfVertices;
    private IntervalGraph graph;
    private List<IntervalGraph> minSpanningTreesKruskal;
    private List<IntervalGraph> minSpanningTreesPrim;
    private boolean isGraphPainted;
    private boolean isMinSpanningTreePaintedKruskal;
    private boolean isMinSpanningTreePaintedPrim;


    public static void main(String[] args) {
        JFrame window = new WindowForIntervalGraph(); //при создании элемента метод paint вызывается автоматически
    }

    WindowForIntervalGraph() {
        super("Graph Alghoritms");
        setSize(sizeX, sizeY);
        quantityOfVertices = null;
        isGraphPainted = false;

        enterQuantityFrame = new EnterQuantityFrame(this);
        buttonsPanel = new JPanel(new GridLayout(15, 1, 10, 10));
        drawGraphFromFileButton = new JButton("Нарисовать граф из файла .txt");
        generateGraphButton = new JButton("Нарисовать случайный граф");
        primButton = new JButton("Запустить алгоритм Прима");
        kruskalButton = new JButton("Запустить алгоритм Краскала");
        clearButton = new JButton("Очистить");
        buttonsPanel.add(drawGraphFromFileButton);
        buttonsPanel.add(generateGraphButton);
        buttonsPanel.add(primButton);
        buttonsPanel.add(kruskalButton);
        buttonsPanel.add(clearButton);

        this.getContentPane().add(buttonsPanel, BorderLayout.WEST);
        graph = null;
        minSpanningTreesKruskal = null;
        minSpanningTreesPrim = null;
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void paint(Graphics g) {
        super.paint(g);
        isMinSpanningTreePaintedKruskal = false;
        isMinSpanningTreePaintedPrim = false;
        graphics2D = (Graphics2D) getGraphics();
        drawGraphFromFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isGraphPainted) {
                    JFileChooser fileopen = new JFileChooser();
                    int ret = fileopen.showDialog(null, "Открыть файл");
                    if (ret == JFileChooser.APPROVE_OPTION) {
                        File file = fileopen.getSelectedFile();
                        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
                            quantityOfVertices = Integer.parseInt(br.readLine());
                            graph = new IntervalGraph();
                            graph.generateVertices(quantityOfVertices, sizeX, sizeY);
                            String[] stringArray;
                            int a, b, startWeight, endWeight;
                            for (int i = 0; i < quantityOfVertices; i++) {
                                stringArray = br.readLine().split(" ");
                                a = Integer.parseInt(stringArray[0]);
                                b = Integer.parseInt(stringArray[1]);
                                startWeight = Integer.parseInt(stringArray[2]);
                                endWeight = Integer.parseInt(stringArray[3]);
                                Vertex v1 = graph.getVertices().get(a);
                                Vertex v2 = graph.getVertices().get(b);
                                graph.addEdge(new IntervalEdge(v1, v2, new Interval(startWeight, endWeight)));

                            }
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                }
                isGraphPainted = true;
                repaint();
            }
        });
        generateGraphButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isGraphPainted) {
                    enterQuantityFrame.setVisible(true);
                    quantityOfVertices = enterQuantityFrame.getQuantity();
                    if (quantityOfVertices == null) {
                        return;
                    }
                    graph = new IntervalGraph(quantityOfVertices, sizeX, sizeY);
                }
            }
        });
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isGraphPainted = false;
                enterQuantityFrame.setQuantityNull(); // костыль
                graph = null;
                repaint();
            }
        });
        kruskalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isGraphPainted) {
                    if (!isMinSpanningTreePaintedKruskal) {
                        DecisionComponent component = new DecisionComponent(1, new IntervalKruskalAlghoritm(), new IntervalGraph(), graph.getEdges());
                        minSpanningTreesKruskal = component.getDecisions();
                        ArrayList<IntervalGraph> allDecisions = component.getDecisions();
                        double probabilitySum = 0;
                        for (IntervalGraph graph : allDecisions) {
                            probabilitySum += graph.getProbability();
                        }
                        System.out.println(probabilitySum);
                        minSpanningTreesKruskal = new ArrayList<>();
                        for(IntervalGraph intervalGraph: allDecisions){
                            if (minSpanningTreesKruskal.contains(intervalGraph)){
                                int index = minSpanningTreesKruskal.indexOf(intervalGraph);
                                minSpanningTreesKruskal.get(index).setProbability(intervalGraph.getProbability() + minSpanningTreesKruskal.get(index).getProbability());
                            } else {
                                minSpanningTreesKruskal.add(intervalGraph);
                            }
                        }
                        for (IntervalGraph intervalGraph: minSpanningTreesKruskal){
                            for (IntervalEdge edge: intervalGraph.getEdges()){
                                int indexOfGraphEdge = graph.getEdges().indexOf(edge);
                                Interval graphEdgeWeight = graph.getEdges().get(indexOfGraphEdge).getIntervalWeight();
                                edge.setStart(graphEdgeWeight.getStart());
                                edge.setEnd(graphEdgeWeight.getEnd());
                            }
                        }
                        probabilitySum = 0;
                        for (IntervalGraph graph : minSpanningTreesKruskal) {
                            probabilitySum += graph.getProbability();
                        }
                        System.out.println(probabilitySum);
                    }
                }
            }
        });
        primButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isGraphPainted) {
                    if (!isMinSpanningTreePaintedPrim) {
                        IntervalGraph startGraph = new IntervalGraph();
                        startGraph.addVertex(graph.getVertices().get((int) Math.random() * graph.getVertices().size() + 1));
                        DecisionComponent component = new DecisionComponent(1, new IntervalPrimAlghoritm(), startGraph, graph.getEdges());
                        ArrayList<IntervalGraph> allDecisions = component.getDecisions();
                        double probabilitySum = 0;
                        for (IntervalGraph graph : allDecisions) {
                            probabilitySum += graph.getProbability();
                        }
                        System.out.println(probabilitySum);
                        minSpanningTreesPrim = new ArrayList<>();
                        for(IntervalGraph intervalGraph: allDecisions){
                            if (minSpanningTreesPrim.contains(intervalGraph)){
                                int index = minSpanningTreesPrim.indexOf(intervalGraph);
                                minSpanningTreesPrim.get(index).setProbability(intervalGraph.getProbability() + minSpanningTreesPrim.get(index).getProbability());
                            } else {
                                minSpanningTreesPrim.add(intervalGraph);
                            }
                        }
                        for (IntervalGraph intervalGraph: minSpanningTreesPrim){
                            for (IntervalEdge edge: intervalGraph.getEdges()){
                                int indexOfGraphEdge = graph.getEdges().indexOf(edge);
                                Interval graphEdgeWeight = graph.getEdges().get(indexOfGraphEdge).getIntervalWeight();
                                edge.setStart(graphEdgeWeight.getStart());
                                edge.setEnd(graphEdgeWeight.getEnd());
                            }
                        }
                        probabilitySum = 0;
                        for (IntervalGraph graph : minSpanningTreesPrim) {
                            probabilitySum += graph.getProbability();
                        }
                        System.out.println(probabilitySum);
                        //paintMinSpanningTree(-5, minSpanningTreesPrim, Color.BLUE);
                    }
                }
            }
        });
        if (graph != null) {
            paintGraph(g);
        }
    }

    public void paintGraph(Graphics g) {
        for (Vertex vertex : graph.getVertices().values()) {
            g.fillOval(vertex.getX(), vertex.getY(), 5, 5);
            g.drawString(vertex.toString(), vertex.getX(), vertex.getY() + 20);
        }
        int x1, y1, x2, y2;
        g.setColor(Color.gray);
        for (IntervalEdge edge : graph.getEdges()) {
            x1 = edge.getA().getX();
            y1 = edge.getA().getY();
            x2 = edge.getB().getX();
            y2 = edge.getB().getY();
            graphics2D.drawLine(x1, y1, x2, y2);
            g.drawString(String.valueOf(edge.getIntervalWeight().getStart() + "-" + edge.getIntervalWeight().getEnd()), (x1 + x2) / 2 - 10, (y1 + y2) / 2 - 10);
        }
        g.setColor(Color.BLACK);
        isGraphPainted = true;
    }
/*
    public void paintMinSpanningTree(int offset, ArrayList<Edge> minSpanningTree, Color color) {
        if (minSpanningTree == minSpanningTreePrim) {
            if (isMinSpanningTreePaintedPrim) {
                return;
            }
            isMinSpanningTreePaintedPrim = true;
        }
        if (minSpanningTree == minSpanningTreeKruskal) {
            if (isMinSpanningTreePaintedKruskal) {
                return;
            }
            isMinSpanningTreePaintedKruskal = true;
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
    }*/
}
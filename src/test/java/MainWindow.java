import org.apache.commons.collections4.MultiValuedMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {
    private static final int sizeX = 1600;
    private static final int sizeY = 750;
    private static int quantityOfVertexes;
    private JButton generateGraphButton;
    private JButton primButton;
    private JButton crascalButton;
    private JPanel buttonsPanel;
    MainWindow() {
        super("Graph Alghoritms");
        setSize(sizeX, sizeY);
        quantityOfVertexes = 20;

        buttonsPanel = new JPanel(new GridLayout(15, 1, 10, 10));
        generateGraphButton = new JButton("Сгенерировать граф");
        primButton = new JButton("Запустить алгоритм Прима");
        crascalButton = new JButton("Запустить алгоритм Краскала");
        buttonsPanel.add(generateGraphButton);
        buttonsPanel.add(primButton);
        buttonsPanel.add(crascalButton);
        this.getContentPane().add(buttonsPanel, BorderLayout.WEST);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
    public void paint(Graphics g){
        Graphics2D graphics2D = (Graphics2D) getGraphics();
        Font font = new Font("Century Gothic", Font.BOLD, 14);
        GraphVertexes vertexes = new GraphVertexes(quantityOfVertexes, sizeX, sizeY);
        for (Vertex vertex: vertexes.getVertexes()){
            g.fillOval(vertex.getX(),vertex.getY(),5,5);
            g.drawString(vertex.toString(), vertex.getX(), vertex.getY()+20);
        }

        generateGraphButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MultiValuedMap<Integer, Integer> map = new GraphGenerator(quantityOfVertexes).getEdges();
                int x1,y1,x2,y2;
                for(Integer a: map.keySet()){
                    x1=vertexes.getVertexes().get(a-1).getX();
                    y1=vertexes.getVertexes().get(a-1).getY();
                    for (Integer b: map.get(a)){
                        x2=vertexes.getVertexes().get(b-1).getX();
                        y2=vertexes.getVertexes().get(b-1).getY();
                        graphics2D.drawLine(x1,y1,x2,y2);
                        //setVisible(true);
                    }
                }
            }
        });
        setVisible(true);
    }

    public static void main(String[] args) {
        JFrame window = new MainWindow(); //при создании элемента метод paint вызывается автоматически
    }

}
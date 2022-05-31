package Frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {
    private WindowForExactGraph frameExact;
    private WindowForIntervalGraph frameInterval;

    MainWindow(){
        frameExact = new WindowForExactGraph(this);
        frameInterval = new WindowForIntervalGraph(this);
        frameInterval.setVisible(false);

    }

    public static void main(String[] args) {
        new MainWindow();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (frameExact.isVisible()){
            frameExact.setVisible(false);
            frameInterval.setVisible(true);
            //frameInterval.repaint();
            frameInterval.paintGraph(g);
            frameInterval.paintTrees();
        } else {
            frameExact.setVisible(true);
            frameInterval.setVisible(false);
            //frameExact.repaint();
            frameExact.paintGraph(g);
            frameExact.paintTrees();
        }
    }
}

package Frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class EnterQuantityFrame extends JFrame
{
    private final int locationX = Toolkit.getDefaultToolkit().getScreenSize().width/2;
    private final int locationY = Toolkit.getDefaultToolkit().getScreenSize().height/2;
    private final int frameWidth = 400;
    private final int frameHeight = 100;
    private JPanel enterQuantityPanel;
    private JTextField quantityTextField;
    private Integer quantity;
    private JFrame frame;

    public EnterQuantityFrame(JFrame frame) {
        super("Введите количество вершин графа");
        this.frame = frame;
        quantityTextField = new JTextField(15);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        quantity = null;
        quantityTextField.setHorizontalAlignment(JTextField.RIGHT);
        quantityTextField.setText("");
        enterQuantityPanel = new JPanel();
        enterQuantityPanel.add(quantityTextField);
        this.getContentPane().add(enterQuantityPanel);
        this.setBounds(locationX-frameWidth/2,locationY-frameHeight/2,frameWidth,frameHeight);
        quantityTextField.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) { // нажатие без чтения клавиш модификаторов

            }

            @Override
            public void keyPressed(KeyEvent e) { //нажатие
                if (e.getKeyCode() == 10){ //ENTER
                    getQuantity();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) { //отпускание клавиши

            }
        });
    }

    public Integer getQuantity() {
        try {
            quantity = Integer.parseInt(quantityTextField.getText());
            if (quantity<1){
                throw new NumberFormatException();
            }
            quantityTextField.setText("");
            dispose();
            frame.repaint();
        } catch (NumberFormatException exception){

        }
        return quantity;
    }

    public void setQuantityNull() {
        this.quantity = null;
    }
}

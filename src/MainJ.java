package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

public class MainJ {
    JFrame frame;
    JPanel panel;
    screen s;
    MainJ() {
        frame = new JFrame("Sorting Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(1000, 600));
        frame.pack();
        frame.setContentPane(panel);
        frame.pack();
        frame.setVisible(true);
        panel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                Point2D click = e.getPoint();
                s.onClick((int) click.getX(), (int) click.getY());
            }
        });

    }
    private static void runGUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        MainJ drive = new MainJ();
    }
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                runGUI();
            }
        });
    }
    // if inspecting = true, pointers are for inspecting the element
    // if false, pointers are for swapping
    public void updateUI(int[] array, int[] pointers, int size, boolean inspecting) {
        s.setArrays(array,pointers,size);
        s.render((Graphics2D) panel.getGraphics());
    }
}

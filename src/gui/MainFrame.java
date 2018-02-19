package gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        super("Интерполирование кубическими сплайнами");
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        super.setResizable(false);
        super.setLayout(new BorderLayout());
        super.setSize(800, 600);

        Graph graph = new Graph();
        this.add(graph, BorderLayout.CENTER);
        this.setVisible(true);
    }
}

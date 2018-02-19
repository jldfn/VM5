package gui;

import Main.InitialData;
import Interpolation.CubicSpline;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SouthPanel extends JPanel {
    public SouthPanel(Graph graph) {
        super(new FlowLayout());
        JTextField xTextField = new JTextField("0.000000000");
        JButton button = new JButton("Вывести значение функции");
        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double x;
                try {
                    x = new Double(xTextField.getText());
                } catch (NumberFormatException exc) {
                    x = Double.NaN;
                }

                JDialog dialog = new JDialog();
                dialog.setSize(500, 60);
                dialog.setLayout(new FlowLayout());
                if (!Double.isNaN(x)) {
                    CubicSpline cubicSpline = new CubicSpline(Graph.displayPoints);
                    Double y = cubicSpline.interpolate(x);
                    dialog.add(new JLabel("y:"));
                    dialog.add(new JLabel(y.toString()));
                } else {
                    dialog.add(new JLabel("Неверно задан x"));
                }
                dialog.setVisible(true);
            }
        });
        super.add(new Label("x:"));
        super.add(xTextField);
        super.add(button);
    }
}
import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setTitle("Procon");
        window.setResizable(false);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Panel panel = new Panel();
        window.add(panel);
        panel.setPreferredSize(new Dimension(900, 600));
        window.pack();
        window.setVisible(true);
        panel.execLoop();
    }
}
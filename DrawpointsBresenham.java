import java.awt.*;
import javax.swing.*;
 
class DrawpointsBresenham extends JPanel {
    public void paintComponent(Graphics g) {
        int range = 500;
        for (int i = 0; i < 5000; i++) {
            int x1 = (int) (Math.random() * range + 100),
                    y1 = (int) (Math.random() * range + 100),
                    x2 = (int) (Math.random() * range + 100),
                    y2 = (int) (Math.random() * range + 100);
            // CODE FROM GEEKSFORGEEKS
            // https://www.geeksforgeeks.org/bresenhams-line-generation-algorithm/
            int m_new = 2 * (y2 - y1);
            int slope_error_new = m_new - (x2 - x1);
 
            for (int x = x1, y = y1; x <= x2; x++) {
                g.drawLine(x, y, x, y);
 
                // Add slope to increment angle formed
                slope_error_new += m_new;
 
                // Slope error reached limit, time to
                // increment y and update slope error.
                if (slope_error_new >= 0) {
                    y++;
                    slope_error_new -= 2 * (x2 - x1);
                }
            }
           
 
        }
    }
 
    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Draw Points");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.white);
        frame.setSize(800, 800);
 
        DrawpointsBresenham panel = new DrawpointsBresenham();
 
        frame.add(panel);
 
        frame.setVisible(true);
    }
}


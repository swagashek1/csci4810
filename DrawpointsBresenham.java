import java.awt.*;
import javax.swing.*;
import java.util.*;

public class DrawpointsBresenham extends JPanel {
    static ArrayList<ArrayList<Integer>> listr = new ArrayList<ArrayList<Integer>>();

    public void paintComponent(Graphics g) {
        for (int i = 0; i < listr.get(0).size(); i++) {
            int x = listr.get(0).get(i);
            int y = listr.get(1).get(i);
            g.drawLine(x, y, x, y);

        }
        // g.drawLine(x1, y1, x2, y2);
    }

    public static void brez() {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("how many lines");
            int count = sc.nextInt();
            // int range = 500;
            ArrayList<Integer> arrlx = new ArrayList<Integer>();
            ArrayList<Integer> arrly = new ArrayList<Integer>();
            for (int i = 0; i < count; i++) {
                /*
                 * do{
                 * int x1 = (int) (Math.random() * range + 100),
                 * y1 = (int) (Math.random() * range + 100),
                 * x2 = (int) (Math.random() * range + 100),
                 * y2 = (int) (Math.random() * range + 100);
                 * }while(y2<y1 && ((|y2-y1|)/(x2-x1)) );
                 */

                int x1 = 800,
                        y1 = 100,
                        x2 = 100,
                        y2 = 800;
                // CODE FROM Github mariohercules 
                // https://github.com/mariohercules/bresenham-algorithm/blob/master/src/Bresenham.java
                int deltaX = Math.abs(x2 - x1);
                int deltaY = Math.abs(y2 - y1);

                int sx = x1 < x2 ? 1 : -1;
                int sy = y1 < y2 ? 1 : -1;

                int erro = deltaX - deltaY;
                int e2;

                while (true) {
                    arrlx.add(x1);
                    arrly.add(y1);

                    if (x1 == x2 && y1 == y2)
                        break;

                    e2 = 2 * erro;
                    if (e2 > -deltaY) {
                        erro = erro - deltaY;
                        x1 = x1 + sx;
                    }

                    if (e2 < deltaX) {
                        erro = erro + deltaX;
                        y1 = y1 + sy;
                    }
                }

            }
            listr.add(arrlx);
            listr.add(arrly);

        }
    }

    public static void main(String[] args) {

        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Draw Points");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.white);
        frame.setSize(800, 800);
        brez();

        DrawpointsBresenham panel = new DrawpointsBresenham();

        frame.add(panel);

        frame.setVisible(true);
    }

}

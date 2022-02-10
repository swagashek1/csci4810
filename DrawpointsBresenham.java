import java.awt.*;
import javax.swing.*;
import java.util.*;
 
public class DrawpointsBresenham extends JPanel {
    static ArrayList<ArrayList<Integer>> listr = new ArrayList<ArrayList<Integer>>();
    public void paintComponent(Graphics g) {
        for(int i=0; i< listr.get(0).size(); i++){
            int x = listr.get(0).get(i);
            int y= listr.get(1).get(i);
            g.drawLine(x, y, x, y);
           
        }
        // g.drawLine(x1, y1, x2, y2);
    }
 
    public static void brez() {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("how many lines");
            int count = sc.nextInt();
            int range = 500;
            ArrayList<Integer> arrlx = new ArrayList<Integer>();
            ArrayList<Integer> arrly = new ArrayList<Integer>();
            for (int i = 0; i < count; i++) {
                int x1 = (int) (Math.random() * range + 100),
                        y1 = (int) (Math.random() * range + 100),
                        x2 = (int) (Math.random() * range + 100),
                        y2 = (int) (Math.random() * range + 100);
                // CODE FROM GEEKSFORGEEKS
                // https://www.geeksforgeeks.org/bresenhams-line-generation-algorithm/
                if(x2<x1){
                    int temp = y1, temp1 = x1;
							y1 = y2;
							x1 = x2;
							y2 = temp;
							x2 = temp1;
                }
                int m_new = 2 * (y2 - y1);
                int slope_error_new = m_new - (x2 - x1);
 
                for (int x = x1, y = y1; x <= x2; x++) {
                    arrlx.add(x);
                    arrly.add(y);
 
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
        System.out.println(listr.get(0).size()+"  next    ");
 
        frame.add(panel);
 
        frame.setVisible(true);
    }
 
}
 
 

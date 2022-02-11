import java.awt.*;
import javax.swing.*;
import java.util.*;

public class DrawPoints extends JPanel {
	static ArrayList<ArrayList<Integer>> listr = new ArrayList<ArrayList<Integer>>();
	public void paintComponent(Graphics g) {
		for(int i=0; i< listr.get(0).size(); i++){
			int x = listr.get(0).get(i);
			int y= listr.get(1).get(i);
			g.drawLine(x, y, x, y);
		}
		// g.drawLine(x1, y1, x2, y2);
	}

	public static void basicAlg() {
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

                

				int m = 1;
				if (y2 == y1) {
					if (x2 < x1) {
						int temp = x1;
						x1 = x2;
						x2 = temp;
					}
					for (int x = x1; x < x2; x++) {
						arrlx.add(x);
						arrly.add(y1);
					}
				} else if (x1 == x2) {
					if (y2 < y1) {
						int temp = y1;
						y1 = y2;
						y2 = temp;
					}
					for (int y = y1; y < y2; y++) {
						arrlx.add(x1);
						arrly.add(y);
					}
				} else {
					m = Math.round((y2 - y1) / (x2 - x1));
					if (m <= 1 && m >= -1) {
						double m1 = (double) (y2 - y1) / (x2 - x1);
						if (x2 < x1) {
							int temp = y1, temp1 = x1;
							y1 = y2;
							x1 = x2;
							y2 = temp;
							x2 = temp1;
						}
						double c = (double) y1 - (m1 * (double) x1);
						for (int x = x1; x < x2; x++) {
							int y = (int) Math.round((m1 * x) + c);
							arrlx.add(x);
							arrly.add(y);
						}
					} else if (m > 1 || m < 1) {
						if (y2 < y1) {
							int temp = y1, temp1 = x1;
							y1 = y2;
							x1 = x2;
							y2 = temp;
							x2 = temp1;
						}
						int c = y1 - m * x1;
						for (int y = y1; y < y2; y++) {
							int x = Math.round((y - c) / m);
							arrlx.add(x);
							arrly.add(y);
						}
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
		long time1= System.nanoTime();
        basicAlg();
		long time2= System.nanoTime();
		System.out.println(time2-time1);

		DrawPoints panel = new DrawPoints();

		frame.add(panel);

		frame.setVisible(true);
	}

}

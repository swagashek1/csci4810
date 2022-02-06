import java.awt.*;
import javax.swing.*;

public class DrawPoints extends JPanel {
	public void paintComponent(Graphics g) {

		int range = 500;
		for (int i = 0; i < 1; i++) {

			
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
					g.drawLine(x, y1, x, y1);
				}
			} else if (x1 == x2) {
				if (y2 < y1) {
					int temp = y1;
					y1 = y2;
					y2 = temp;
				}
				for (int y = y1; y < y2; y++) {
					g.drawLine(x1, y, x1, y);
				}
			} else {
				m = Math.round((y2 - y1) / (x2 - x1));
				if (m <= 1 && m >= -1) {
					double m1 = (double)(y2 - y1) / (x2 - x1);
					if (x2 < x1) {
						int temp = y1, temp1 = x1;
						y1 = y2;
						x1 = x2;
						y2 = temp;
						x2 = temp1;
					}
					double c = (double)y1 - (m1 * (double)x1);
					for (int x = x1; x < x2; x++) {
						int y = (int)Math.round((m1 * x )+ c);
						g.drawLine(x, y, x, y);
					}
				} else if (m > 1) {
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
						g.drawLine(x, y, x, y);
					}
				} else if (m < -1) {
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
						g.drawLine(x, y, x, y);
					}
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

		DrawPoints panel = new DrawPoints();

		frame.add(panel);

		frame.setVisible(true);
	}

}

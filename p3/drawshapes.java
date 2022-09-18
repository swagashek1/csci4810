import java.awt.*;
import java.io.*;
import java.lang.*;

import javax.swing.*;
import java.util.*;

public class drawshapes extends JPanel {
	static ArrayList<ArrayList<Integer>> pixelpoints = new ArrayList<ArrayList<Integer>>();
	static ArrayList<ArrayList<Integer>> points = new ArrayList<ArrayList<Integer>>();
	static float[][] arrtransform = new float[3][3];

	// inputs the lines from a input file
	public static void Inputlines(String name) {
		try {
			ArrayList<Integer> lineInput = new ArrayList<>();
			Scanner input = new Scanner(System.in);

			File file = new File(name);

			input = new Scanner(file);

			while (input.hasNextLine()) {
				if (input.hasNextInt()) {
					lineInput.add(input.nextInt());
				}
			}
			input.close();
			int count = 0;
			ArrayList<Integer> templine = new ArrayList<>();
			for (int i = 0; i < lineInput.size(); i++) {
				templine.add(lineInput.get(i));

				if (templine.size() == 4) {
					points.add(templine);
					templine = new ArrayList<>();
				}

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	// plots the pixels of the lines
	public static void basicAlg() {

		ArrayList<Integer> arrlx = new ArrayList<Integer>();
		ArrayList<Integer> arrly = new ArrayList<Integer>();
		if (points.size() != 0) {
			for (int i = 0; i < points.size(); i++) {

				int x1 = points.get(i).get(0),
						y1 = points.get(i).get(1),
						x2 = points.get(i).get(2),
						y2 = points.get(i).get(3);

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
		}
		pixelpoints.add(arrlx);
		pixelpoints.add(arrly);
	}

	// paints the lines
	public void paintComponent(Graphics g) {
		for (int i = 0; i < pixelpoints.get(0).size(); i++) {
			int x = pixelpoints.get(0).get(i);
			int y = pixelpoints.get(1).get(i);
			g.drawLine(x, y, x, y);
		}
	}

	// translates the object from the origin
	public static void BasicTranslate(double Tx, double Ty) {
		float[][] arr = new float[3][3];
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length; j++) {
				if (i == j) {
					arr[i][j] = 1;
				} else {
					arr[i][j] = 0;
				}
			}
		}
		arr[2][0] = (float) Tx;
		arr[2][1] = (float) Ty;
		AddTransformation(arr);
	}

	// scales an object at the origin
	public static void Basicscale(double Sx, double Sy) {
		float[][] arr = new float[3][3];
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length; j++) {
				if (i == j) {
					arr[i][j] = 1;
				} else {
					arr[i][j] = 0;
				}
			}
		}
		arr[0][0] = (float) Sx;
		arr[1][1] = (float) Sy;
		AddTransformation(arr);
	}

	// scales the object at a given point
	public static void Scale(double Sx, double Sy, int Tx, int Ty) {
		BasicTranslate(-Tx, -Ty);
		Basicscale(Sx, Sy);
		BasicTranslate(Tx, Ty);
	}

	// rotates the object from the origin
	public static void Basicrotate(double angle) {
		float[][] arr = new float[3][3];
		for (int i = 0; i < arr.length; i++) {
			arr[2][i] = 0;
			arr[i][2] = 0;
		}
		arr[0][0] = (float) Math.cos(Math.toRadians(angle));
		arr[0][1] = (float) ((-1) * Math.sin(Math.toRadians(angle)));
		arr[1][0] = (float) Math.sin(Math.toRadians(angle));
		arr[1][1] = (float) Math.cos(Math.toRadians(angle));
		arr[2][2] = 1;

		AddTransformation(arr);
	}

	// rotate's the object along the given axis coordinate
	public static void Rotate(double angle, int Tx, int Ty) {
		BasicTranslate(-Tx, -Ty);
		Basicrotate(angle);
		BasicTranslate(Tx, Ty);
	}

	// adds the transformations to the transformation matrix through matrix
	// multiplication
	public static void twoMatrixMult(float[][] matrix2) {
		float[][] temp = new float[3][3];
		for (int i = 0; i < matrix2.length; i++) {
			for (int j = 0; j < arrtransform[0].length; j++) {
				for (int k = 0; k < matrix2[0].length; k++) {
					temp[i][j] += arrtransform[i][k] * matrix2[k][j];
				}
			}
		}
		for (int i = 0; i < arrtransform.length; i++) {
			arrtransform[i] = Arrays.copyOf(temp[i], temp[i].length);
		}

	}

	// applies the transformation on the corrdinates through matrix multiplication
	public static float[][] matrixPointsMult(float[][] matrix) {
		float[][] temp = new float[1][3];
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < arrtransform[0].length; j++) {
				for (int k = 0; k < matrix[0].length; k++) {
					temp[i][j] += matrix[i][k] * arrtransform[k][j];
				}
			}

		}
		return temp;
	}

	// adds the transformation to the transformation matrix
	public static void AddTransformation(float[][] arr) {
		twoMatrixMult(arr);

	}

	// applies the transformation matrix onto the coordinates
	public static void ApplyTransformation() {
		for (int i = 0; i < points.size(); i++) {

			int x1 = points.get(i).get(0);
			int y1 = points.get(i).get(1);
			int x2 = points.get(i).get(2);
			int y2 = points.get(i).get(3);
			float[][] temp = { { x1, y1, 1 } };
			float[][] temp2 = { { x2, y2, 1 } };
			float[][] temparr1 = matrixPointsMult(temp);
			float[][] temparr2 = matrixPointsMult(temp2);
			points.get(i).set(0, (int) Math.floor(temparr1[0][0]));
			points.get(i).set(1, (int) Math.floor(temparr1[0][1]));
			points.get(i).set(2, (int) Math.floor(temparr2[0][0]));
			points.get(i).set(3, (int) Math.floor(temparr2[0][1]));

		}

		initialtransform();

	}

	// resets transformation matrix
	public static void initialtransform() {
		for (int i = 0; i < arrtransform.length; i++) {
			for (int j = 0; j < arrtransform.length; j++) {
				if (i == j) {
					arrtransform[i][j] = 1;
				} else {
					arrtransform[i][j] = 0;
				}
			}
		}
	}

	// pushes coordinates to outer file
	public static void outputLines(String name) {
		try {
			File f = new File(name);
			f.createNewFile();
			System.setOut(new PrintStream(f));
			for (int i = 0; i < points.size(); i++) {
				for (int j = 0; j < points.get(0).size(); j++) {
					System.out.print(points.get(i).get(j) + "   ");
				}
				System.out.println();
			}
		} catch (IOException ioe) {

		}
	}

	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame("Draw Points");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBackground(Color.white);
		frame.setSize(800, 800);
		initialtransform();
		Inputlines("lines.txt");
		// Rotate(25, 250, 250);
		// Scale(1.5, 1.5, 250, 250);
		// Basicrotate(25);
		// Basicscale(1.5, 1.5);
		// BasicTranslate(100, 100);

		ApplyTransformation();
		basicAlg();
		outputLines("output.txt");

		drawshapes panel = new drawshapes();

		frame.add(panel);

		frame.setVisible(true);
	}

}

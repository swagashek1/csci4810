import java.awt.*;
import java.io.*;
import java.lang.*;
import javax.swing.*;
import java.util.*;

public class drawobjects extends JPanel {

    static ArrayList<ArrayList<Float>> worldcoordinates = new ArrayList<ArrayList<Float>>();
    static ArrayList<ArrayList<Float>> screencoordinates = new ArrayList<ArrayList<Float>>();
    static ArrayList<ArrayList<Integer>> pixelpoints = new ArrayList<ArrayList<Integer>>();
    static ArrayList<ArrayList<Integer>> points = new ArrayList<ArrayList<Integer>>();
    static float[][] arrtransform = new float[4][4];
    static float[][] convert = new float[4][4];
    static float viewingX = 6;
    static float viewingY = 8;
    static float viewingZ = (float) 7.5;
    static float d = 60;
    static float s = 15;

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
            ArrayList<Float> templine = new ArrayList<>();
            for (int i = 0; i < lineInput.size(); i++) {
                templine.add((float) lineInput.get(i));
                if (templine.size() == 6) {
                    worldcoordinates.add(templine);
                    templine = new ArrayList<>();
                }

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void convert3d2d() {
        initialconvert();

        T1();

        for (int i = 0; i < worldcoordinates.size(); i++) {

            float x1 = worldcoordinates.get(i).get(0);
            float y1 = worldcoordinates.get(i).get(1);
            float z1 = worldcoordinates.get(i).get(2);
            float x2 = worldcoordinates.get(i).get(3);
            float y2 = worldcoordinates.get(i).get(4);
            float z2 = worldcoordinates.get(i).get(5);
            // System.out.println("X1: "+x1+" Y1: "+y1+" Z1: "+z1+" X2: "+x2+" Y2: "+y2+"
            // Z2: "+z2);
            float[][] tempconversion = new float[1][4];
            float[][] tempconversion2 = new float[1][4];
            float[][] temp = { { x1, y1, z1, 1 } };
            float[][] temp2 = { { x2, y2, z2, 1 } };
            tempconversion = matrixPointsMult(convert, temp);
            tempconversion2 = matrixPointsMult(convert, temp2);

            // (xc/zc)VSX+VcY
            int coorX =  Math.round(((tempconversion[0][0] / tempconversion[0][2]) * 200 + 200));
            // (xc/zc)VSX+VcY
            int coorY =  Math.round(((tempconversion[0][1] / tempconversion[0][2]) * 200 + 200));

            // (xc/zc)VSX+VcY
            int coorX1 =  Math.round(((tempconversion2[0][0] / tempconversion2[0][2]) * 200 + 200));
            // (xc/zc)VSX+VcY
            int coorY1 =  Math.round(((tempconversion2[0][1] / tempconversion2[0][2]) * 200 + 200));

            ArrayList<Integer> templine1 = new ArrayList<>();
            templine1.add(coorX);
            templine1.add(coorY);
            templine1.add(coorX1);
            templine1.add(coorY1);

            // System.out.println("X1:"+coorX+"Y1:"+coorY+"X2:"+coorX1+"Y2:"+coorY1);

            points.add(templine1);

        }

    }

    public static void T1() {
        float[][] temp = new float[4][4];
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp.length; j++) {
                temp[i][j] = 0;
            }
        }
        temp[0][0] = 1;
        temp[1][1] = 1;
        temp[2][2] = 1;
        temp[3][0] = (-1) * viewingX;
        temp[3][1] = (-1) * viewingY;
        temp[3][2] = (-1) * viewingZ;
        temp[3][3] = 1;
        twoMatrixMult(convert, temp);

        T2();

    }

    public static void T2() {
        float[][] temp = new float[4][4];
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp.length; j++) {
                temp[i][j] = 0;
            }
        }
        temp[0][0] = 1;
        temp[1][2] = -1;
        temp[2][1] = 1;
        temp[3][3] = 1;

        twoMatrixMult(convert, temp);

        T3();
    }

    public static void T3() {
        float[][] temp = new float[4][4];
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp.length; j++) {
                temp[i][j] = 0;
            }
        }
        // DOUBLE CHECK IF SOMETHING GOES WRONG

        float tempX = (float) (viewingX / (Math.sqrt(Math.pow(viewingX, 2) + Math.pow(viewingY, 2))));
        float tempY = (float) ((-1) * (viewingY / (Math.sqrt(Math.pow(viewingX, 2) + Math.pow(viewingY, 2)))));
        temp[0][0] = tempY;
        temp[0][2] = tempX;
        temp[1][1] = 1;
        temp[2][0] = (-1) * tempX;
        temp[2][2] = tempY;
        temp[3][3] = 1;
        twoMatrixMult(convert, temp);

        T4();
    }

    public static void T4() {
        float[][] temp = new float[4][4];
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp.length; j++) {
                temp[i][j] = 0;
            }
        }
        float tempXY = (float) (Math.sqrt(Math.pow(viewingX, 2) + Math.pow(viewingY, 2))
                / (Math.sqrt(Math.pow(viewingX, 2) + Math.pow(viewingY, 2) + Math.pow(viewingZ, 2))));

        float tempZ = (float) (viewingZ
                / (Math.sqrt(Math.pow(viewingX, 2) + Math.pow(viewingY, 2) + Math.pow(viewingZ, 2))));

        temp[0][0] = 1;
        temp[1][1] = tempXY;
        temp[1][2] = tempZ;
        temp[2][1] = (-1) * tempZ;
        temp[2][2] = tempXY;
        temp[3][3] = 1;
        twoMatrixMult(convert, temp);

        T5();
    }

    public static void T5() {
        float[][] temp = new float[4][4];
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp.length; j++) {
                temp[i][j] = 0;
            }
        }
        temp[0][0] = 1;
        temp[1][1] = 1;
        temp[2][2] = -1;
        temp[3][3] = 1;
        twoMatrixMult(convert, temp);

        N();
    }

    public static void N() {
        float[][] temp = new float[4][4];
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp.length; j++) {
                temp[i][j] = 0;
            }
        }
        temp[0][0] = d / s;
        temp[1][1] = d / s;
        temp[2][2] = 1;
        temp[3][3] = 1;
        twoMatrixMult(convert, temp);

    }

    // initialize convert
    public static void initialconvert() {
        for (int i = 0; i < convert.length; i++) {
            for (int j = 0; j < convert.length; j++) {
                if (i == j) {
                    convert[i][j] = 1;
                } else {
                    convert[i][j] = 0;
                }
            }
        }
    }

    static int count = 0;

    // plots the pixels of the lines
    public static void brez() {
        ArrayList<Integer> arrlx = new ArrayList<Integer>();
        ArrayList<Integer> arrly = new ArrayList<Integer>();
       for (int i = 0; i < points.size(); i++) {

              int x1 = points.get(i).get(0),
                    y1 = points.get(i).get(1),
                    x2 = points.get(i).get(2),
                    y2 = points.get(i).get(3);




            // CODE FROM Github mariohercules
            // https://github.com/mariohercules/bresenham-algorithm/blob/master/src/Bresenham.java
            //change in x and y
            int deltaX = Math.abs(x2 - x1);
            int deltaY = Math.abs(y2 - y1);
            //orientation of the line positive negative
            int sx = x1 < x2 ? 1 : -1;
            int sy = y1 < y2 ? 1 : -1;
            //error1
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
    public static void BasicTranslate(float Tx, float Ty, float Tz) {
        float[][] arr = new float[4][4];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                if (i == j) {
                    arr[i][j] = 1;
                } else {
                    arr[i][j] = 0;
                }
            }
        }
        arr[3][0] = (float) Tx;
        arr[3][1] = (float) Ty;
        arr[3][2] = (float) Tz;

        AddTransformation(arr);
    }

    // scales an object at the origin
    public static void Basicscale(float Sx, float Sy, float Sz) {
        float[][] arr = new float[4][4];
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
        arr[2][2] = (float) Sz;

        AddTransformation(arr);
    }

    // scales the object at a given point
    /*
     * public static void Scale(float Sx, float Sy, float Sz, int Tx, int Ty, int
     * Tz) {
     * BasicTranslate(-Tx, -Ty, -Tz);
     * Basicscale(Sx, Sy, Sz);
     * BasicTranslate(Tx, Ty, Tz);
     * }
     */

    // rotates the object from the origin
    public static void rotateZ(float angle) {
        float[][] arr = new float[4][4];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                if (i == j) {
                    arr[i][j] = 1;
                } else {
                    arr[i][j] = 0;
                }
            }
        }
        arr[0][0] = (float) Math.cos(Math.toRadians(angle));
        arr[0][1] = (float) Math.sin(Math.toRadians(angle));
        arr[1][0] = (float) ((-1) * Math.sin(Math.toRadians(angle)));
        arr[1][1] = (float) Math.cos(Math.toRadians(angle));

        AddTransformation(arr);
    }

    public static void rotateY(float angle) {
        float[][] arr = new float[4][4];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                if (i == j) {
                    arr[i][j] = 1;
                } else {
                    arr[i][j] = 0;
                }
            }
        }
        arr[0][0] = (float) Math.cos(Math.toRadians(angle));
        arr[0][2] = (float) ((-1) * Math.sin(Math.toRadians(angle)));
        arr[2][0] = (float) Math.sin(Math.toRadians(angle));
        arr[2][2] = (float) Math.cos(Math.toRadians(angle));

        AddTransformation(arr);
    }

    public static void rotateX(float angle) {
        float[][] arr = new float[4][4];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                if (i == j) {
                    arr[i][j] = 1;
                } else {
                    arr[i][j] = 0;
                }
            }
        }
        arr[1][1] = (float) Math.cos(Math.toRadians(angle));
        arr[1][2] = (float) (Math.sin(Math.toRadians(angle)));
        arr[2][1] = (float) ((-1) * Math.sin(Math.toRadians(angle)));
        arr[2][2] = (float) Math.cos(Math.toRadians(angle));

        AddTransformation(arr);
    }

    // rotate's the object along the given axis coordinate
    /*
     * public static void Rotate(float angle, int Tx, int Ty) {
     * BasicTranslate(-Tx, -Ty);
     * Basicrotate(angle);
     * BasicTranslate(Tx, Ty);
     * }
     */

    // adds the transformations to the transformation matrix through matrix
    // multiplication
    public static void twoMatrixMult(float[][] arrtransform, float[][] matrix2) {
        float[][] temp = new float[4][4];
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
    public static float[][] matrixPointsMult(float[][] arrtransform, float[][] matrix) {
        float[][] temp = new float[1][4];
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
        twoMatrixMult(arrtransform, arr);
        // System.out.println("Array after add transform");

        /*
         * for (int i = 0; i < arrtransform.length; i++) {
         * for (int j = 0; j < arrtransform[i].length; j++) {
         * System.out.print(arrtransform[i][j] + "   ");
         * }
         * System.out.println();
         * }
         * for (int i = 0; i < arrtransform.length; i++) {
         * for (int j = 0; j < arrtransform[i].length; j++) {
         * System.out.print(arrtransform[i][j] + "   ");
         * }
         * System.out.println();
         * }
         */
    }

    // applies the transformation matrix onto the coordinates
    public static void ApplyTransformation() {

        for (int i = 0; i < worldcoordinates.size(); i++) {

            float x1 = worldcoordinates.get(i).get(0);
            float y1 = worldcoordinates.get(i).get(1);
            float z1 = worldcoordinates.get(i).get(2);
            float x2 = worldcoordinates.get(i).get(3);
            float y2 = worldcoordinates.get(i).get(4);
            float z2 = worldcoordinates.get(i).get(5);
            float[][] temp = { { x1, y1, z1, 1 } };
            float[][] temp2 = { { x2, y2, z2, 1 } };
            float[][] temparr1 = matrixPointsMult(arrtransform, temp);
            float[][] temparr2 = matrixPointsMult(arrtransform, temp2);

            worldcoordinates.get(i).set(0, temparr1[0][0]);
            worldcoordinates.get(i).set(1, temparr1[0][1]);
            worldcoordinates.get(i).set(2, temparr1[0][2]);
            worldcoordinates.get(i).set(3, temparr2[0][0]);
            worldcoordinates.get(i).set(4, temparr2[0][1]);
            worldcoordinates.get(i).set(5, temparr2[0][2]);

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
            for (int i = 0; i < worldcoordinates.size(); i++) {
                for (int j = 0; j < worldcoordinates.get(0).size(); j++) {
                    System.out.print(worldcoordinates.get(i).get(j) + "   ");
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
        initialconvert();
        Inputlines("lines.txt");

        Basicscale(2, 0.5f, 1);
        BasicTranslate(-5, -5, -5);
        rotateX(30);
        rotateY(30);
        rotateZ(30);

        ApplyTransformation();

        convert3d2d();
        brez();
        outputLines("output.txt");

        drawobjects panel = new drawobjects();

        frame.add(panel);

        frame.setVisible(true);
    }

}

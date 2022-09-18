import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.util.ArrayList;
import java.util.Scanner; // Import the Scanner class

class p5 {
    static ArrayList<String> bitstring = new ArrayList<>();
    static BufferedImage img = null;
    static String bitmessage = "";
    static String bitmessagechars = "";
    static File f = null;
    static Scanner myObj = new Scanner(System.in); // Create a Scanner object
    public static void main(String args[]) throws IOException {

        // read image
        try {
            
            System.out.println("Enter 1 for encode and 2 for decode");
            int choice = myObj.nextInt();
            myObj.nextLine();
            
            if (choice == 1) {
                System.out.println("message to be encoded");
                String message = myObj.nextLine(); // Read user input
                
                message += "#";
                convertStringToBinary(message);
                System.out.println("picture to be changed");
                String thefile = myObj.nextLine();
                f = new File(thefile);

                img = ImageIO.read(f);
                encode();

            } else {
                System.out.println("picture to be decoded");
                String thefile = myObj.nextLine();
                f = new File(thefile);
                img = ImageIO.read(f);
                // System.out.println("break/r/n");
                printrgb();
                // System.out.println(bitmessage);

                System.out.println(bitmessagechars);
            }
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    private static void printrgb() {
        
        int count = 0;
        String bitseight = "";
        int j = 0;
        while (bitseight != "00100011" ) {

            int p = img.getRGB(count, j);
            // get alpha
            int a = (p >> 24) & 0xff;
            bitmessage += String.valueOf(a % 2);
            // get red
            int r = (p >> 16) & 0xff;
            bitmessage += String.valueOf(r % 2);
            // get green
            int g = (p >> 8) & 0xff;
            bitmessage += String.valueOf(g % 2);
            // get blue
            int b = p & 0xff;
            bitmessage += String.valueOf(b % 2);
            bitseight += String.valueOf(a % 2) + String.valueOf(r % 2) + String.valueOf(g % 2) + String.valueOf(b % 2);
            if ((char) Integer.parseInt(bitseight, 2) == '#') {
                break;
            }
            if (j % 2 == 1) {
                bitmessage += "  ";
            }


            if (bitseight.length() == 8 && bitseight != "00100011") {
                bitmessagechars += (char) Integer.parseInt(bitseight, 2);
                bitseight = "";
            }
            j++;
            if(j>=img.getHeight()){
                j=0;
                count++;
            }
        }
    }

    static void encode() {
        int pixcount = 0;
        int count = 0;
        for (int i = 0; i < bitstring.size(); i++) {
            for (int j = 0; j < 8; j += 0) {
                int temp;
                int p = img.getRGB(count, pixcount);
                // get alpha
                int a = (p >> 24) & 0xff;

                temp = bitstring.get(i).charAt(j);

                if (temp % 2 == 1 && a % 2 == 0) {
                    a = a + 1;
                } else if (temp % 2 == 0 && a % 2 == 1) {
                    a = a - 1;
                }
                j++;
                // get red
                int r = (p >> 16) & 0xff;

                temp = bitstring.get(i).charAt(j);

                if (temp % 2 == 1 && r % 2 == 0) {
                    r = r + 1;
                } else if (temp % 2 == 0 && r % 2 == 1) {
                    r = r - 1;
                }
                j++;

                // get green
                int g = (p >> 8) & 0xff;

                temp = bitstring.get(i).charAt(j);

                if (temp % 2 == 1 && g % 2 == 0) {
                    g = g + 1;
                } else if (temp % 2 == 0 && g % 2 == 1) {
                    g = g - 1;
                }
                j++;

                // get blue
                int b = p & 0xff;

                temp = bitstring.get(i).charAt(j);

                if (temp % 2 == 1 && b % 2 == 0) {
                    b = b + 1;
                } else if (temp % 2 == 0 && b % 2 == 1) {
                    b = b - 1;
                }
                j++;

                // set the pixel value
                p = (a << 24) | (r << 16) | (g << 8) | b;

                img.setRGB(count, pixcount, p);
                pixcount++;
                if (pixcount >= img.getHeight() ) {
                    count++;
                    pixcount = 0;
                }
            }
        }
        try {
            System.out.println("output file with .png");
            String file= myObj.nextLine();;
            f = new File(file);
            ImageIO.write(img, "png", f);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private static ArrayList<String> convertStringToBinary(String input) {

        char[] chars = input.toCharArray();
        for (char aChar : chars) {
            bitstring.add(
                    String.format("%8s", Integer.toBinaryString(aChar)) // char -> int, auto-cast
                            .replaceAll(" ", "0") // zero pads
            );
        }
        return bitstring;

    }
}
package Predictiva;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class Main {
    public static int[][] readImage(String filePath) {
        File file = new File(filePath);

        BufferedImage image = null;

        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int width = image.getWidth();
        int height = image.getHeight();
        int[][] pixels = new int[height][width];


        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int rgb = image.getRGB(x, y);

                int r = (rgb >> 16) & 0xff;
                int g = (rgb >> 8) & 0xff;
                int b = (rgb >> 0) & 0xff;

                pixels[y][x] = r;

            }
        }


        return pixels;
    }

    public static void writeImage(int[][] pixels, int height, int width, String outputFilePath) {

        File fileout = new File(outputFilePath);

        BufferedImage image2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);


        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++) {
                image2.setRGB(x, y, (pixels[y][x] << 16) | (pixels[y][x] << 8) | (pixels[y][x]));
            }

        try {
            ImageIO.write(image2, "jpg", fileout);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int levels = input.nextInt();
        int range = 255 * 2 / levels;

        int quantizer[] = new int[levels];
        int upper = -255;
        for (int i = 0; i < levels; i++) {
            upper += range;
            quantizer[i] = upper;
        }

        int original[][] = readImage("C:\\Users\\HP WIN 8\\Desktop\\lena.jpg");

        int predicted[][] = new int[original.length][original[0].length];

        for (int i = 0; i < original.length || i < original[0].length; i++) {
            if (i < original.length)
                predicted[i][0] = original[i][0];
            if (i < original[0].length)
                predicted[0][i] = original[0][i];
        }

        for (int i = 1; i < original.length; i++) {
            for (int j = 1; j < original[0].length; j++) {
                int A = original[i][j - 1], B = original[i - 1][j - 1], C = original[i - 1][j];
                if (B <= Math.min(C, A))
                    predicted[i][j] = Math.max(A, C);
                else if (B >= Math.max(A, C))
                    predicted[i][j] = Math.min(A, C);
                else
                    predicted[i][j] = A + C - B;
            }
        }

        int difference[][] = new int[original.length][original[0].length];

        for (int i = 1; i < predicted.length; i++) {
            for (int j = 1; j < predicted[0].length; j++) {
                difference[i][j] = original[i][j] - predicted[i][j];
            }
        }
        int qunatizedDifference[][] = new int[original.length][original[0].length];
        int deQuantizedDiff[][] = new int[original.length][original[0].length];


        for (int i = 1; i < original.length; i++) {
            for (int j = 1; j < original[i].length; j++) {
                for (int k = 0; k < levels; k++) {
                    if (difference[i][j] < quantizer[k]) {
                        qunatizedDifference[i][j] = k;
                        if (k != 0)
                            deQuantizedDiff[i][j] = (quantizer[k] + quantizer[k - 1]) / 2;
                        else
                            deQuantizedDiff[i][j] = quantizer[k] / 2;

                        break;
                    }
                }
            }
        }


        int decoded[][] = new int[original.length][original[0].length];
        for (int i = 0; i < original.length || i < original[0].length; i++) {
            if (i < original.length)
                decoded[i][0] = original[i][0];
            if (i < original[0].length)
                decoded[0][i] = original[0][i];
        }

        for (int i = 1; i < original.length; i++) {
            for (int j = 1; j < original[i].length; j++) {
                decoded[i][j] = deQuantizedDiff[i][j] + predicted[i][j];
            }
        }
       
   

        writeImage(decoded, decoded.length, decoded[0].length, "C:\\Users\\HP WIN 8\\Desktop\\outd.jpg");
        System.out.println("Writing done");
    }

}

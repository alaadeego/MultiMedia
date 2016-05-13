package Predictiva;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
//import java.util.jar.Pack200.Packer;

import javax.imageio.ImageIO;

public class Predictive2D {
	public static int[][] readImage(String filePath) {      //function read
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


        for (int x = 0; x < width; x++) 
        {
            for (int y = 0; y < height; y++) 
            {
                int rgb = image.getRGB(x, y);

                int r = (rgb >> 16) & 0xff;
                int g = (rgb >> 8) & 0xff;
                int b = (rgb >> 0) & 0xff;

                pixels[y][x] = r;

            }
        }
        return pixels;
	}
	
	//----------------------------------------------------------------------------
	public static void writeImage(int[][] pixels, int height, int width, String outputFilePath) {  //function write

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
	
//------------------------------------------------------------------------
	
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		Scanner input =new Scanner(System.in);
		System.out.println("Enter Numper of Level");
		int level=input.nextInt();
		int [][]orginal=	readImage("C:\\Users\\HP WIN 8\\Desktop\\bb.jpg");
		int height=orginal.length;
		int width=orginal[0].length;
		
		int[][]predicted=new int [height][width];   // set predicted
		for(int i=0;i<width;i++)
		{
			predicted[0][i]=orginal[0][i];
		}
		for(int i=0;i<height;i++)
		{
			predicted[i][0]=orginal[i][0];
		}
		
		
		for(int i=1;i<height;i++)       //set predicted
		{
			for(int j=1;j<width;j++)
			{
				int A=orginal[i][j-1];
				int B=orginal[i-1][j-1];
				int C=orginal[i-1][j];
				if(B<=Math.min(A,C))
				{
					predicted[i][j]=Math.max(A,C);
				}
				else if (B>=Math.max(A,C))
				{
					predicted[i][j]=Math.min(A,C);
				}
				else
					predicted[i][j]=A+C-B;
					
			}
		}
		
	    int[][] diffrence = new int[height][width];    //set diffrence
	    
		for(int i=0;i<width;i++)
		{
			diffrence[0][i]=orginal[0][i];
		}
		for(int i=0;i<height;i++)
		{
			diffrence[i][0]=orginal[i][0];
		}
	    
	    for(int i=1;i<height;i++)
	    {
	    	for(int j=1;j<width;j++)
	    	{
	    		diffrence[i][j]=orginal[i][j]-predicted[i][j];
	    	}
	    }
		
	    int range=255*2/level;						//Generate Quantizer && Dequantizer
		int[] quantizer=new int [level];
		int[] Q1=new int [level];
		int[] Q2=new int [level];
		int[] dequantizer=new int [level];
		int R=-255;
		for(int i=0;i<level;i++)
		{
			Q1[i]=R;
			Q2[i]=R+range;
			R+=range+1;
		}		
		
		
		int[][] quantizeddiffrence = new int[height][width];
		int[][] dequantizeddiffrence = new int[height][width];
		for(int i=1;i<height;i++)      //set Quantzed Diffrence
		{
			for(int j=1;j<width;j++)
			{
				for(int k=0;k<level;k++)
				{
					if(diffrence[i][j]>=Q1[k] && diffrence[i][j]<=Q2[k])
					{
						quantizeddiffrence[i][j]=k;   
						dequantizeddiffrence[i][j]=(Q1[k]+Q2[k])/2;
						
					}
				}
			}
		}
		int[][] decode = new int[height][width];
		for(int i=0;i<width;i++)
		{
			decode[0][i]=orginal[0][i];
		}
		for(int i=0;i<height;i++)
		{
			decode[i][0]=orginal[i][0];
		}
		
		
		for(int i=1;i<height;i++)
	    {
	    	for(int j=1;j<width;j++)
	    	{
	    		decode[i][j]=dequantizeddiffrence[i][j]-predicted[i][j];
	    	}
	    }
		 
	
//		PrintWriter writer = new PrintWriter("the-file-name.txt", "UTF-8");
//		for(int i=0;i<height;i++)
//		{
//			for(int j=0 ; j<width;j++)
//			{
//				writer.print(decode[i][j]+",");
//			}
//			writer.println("");
//		}
		
	
//		writer.close();
//http://stackoverflow.com/questions/2885173/how-to-create-a-file-and-write-to-a-file-in-java		
		
        writeImage(decode, height, width, "C:\\Users\\HP WIN 8\\Desktop\\outalaa.jpg");

		System.out.println("Done");
		
		
		
		
	
	}
}



package vectorquantizer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Converter {
    
    public void ConvertImageToGray(){
        
        BufferedImage image = null;
        BufferedImage imageOut = null;
        
        try{
            image = ImageIO.read(new File("C:\\Users\\ESC\\Documents\\NetBeansProjects\\VectorQuantizer\\src\\vectorquantizer\\download.jpg"));
            imageOut = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        } 
        catch (IOException ex) {
            System.out.println("Failed loading image");
        }
        
        int x = image.getWidth();
        int y = image.getHeight();
        
        for (int i = 0;i < x;i++){
            
            for (int j = 0;j < y;j++){

                Color grayScale = new Color(image.getRGB(i, j));
                int red = (int)(grayScale.getRed() * 0.21);
                int green = (int)(grayScale.getGreen() * 0.72);
                int blue = (int)(grayScale.getBlue() *0.07);
                
                int sum = red + green + blue;
                Color newGrayScale = new Color (sum, sum, sum);
                
                imageOut.setRGB(i, j, newGrayScale.getRGB());
                
            }
        }
        
        File ouptut = new File("grayscale.jpg");
         
        try {
            ImageIO.write(imageOut, "jpg", ouptut);

        } 
        catch (IOException ex) {
            //
        }
       
    }
}
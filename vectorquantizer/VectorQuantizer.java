package vectorquantizer;


import java.awt.Color;
import java.awt.image.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class VectorQuantizer {
    
    int codeBookSize;
    int blockSize;
    int width;
    int height;
    int toRemove = 2;
    Vector <Label> codeBook;
    Vector <Label> labels = new Vector <Label> ();
    Converter imageConverter;
    
    public VectorQuantizer(){
        //
    }
    
    public VectorQuantizer(int CBS, int BS){
        codeBookSize = CBS;
        blockSize = BS;
        codeBook = new Vector <Label> (codeBookSize);
    }
    
    public void Convert(){
        
        imageConverter = new Converter();
        imageConverter.ConvertImageToGray();
        
    }
     
    public void createBlocks(){      //  divide to  block 
        BufferedImage image = null;
        
        try{
            image = ImageIO.read(new File("C:\\Users\\HP WIN 8\\Desktop\\bb.jpg"));
        } 
        catch (IOException ex) {
            System.out.println("Failed loading image");
        }
        
        int x = image.getWidth();
        int y = image.getHeight();
        
        width = x;
        height = y;
        
        for (int i = 0;i < x;i += blockSize){
            for (int j = 0;j < y;j += blockSize){
                
                Label temp = new Label();
                
                for (int s = j;s < j + blockSize;s++){
                    for (int t = i;t < i + blockSize;t++){
                
                        int gray = image.getRGB(t, s) & 0xFF;
                        temp.pixels.add(gray);
                    }
                }
                
                labels.add(temp); 
            }
        }
    }

    public void writeCodeBook(){
        FileWriter file = null;
        BufferedWriter bw = null;
        
        try {
            file = new FileWriter("C:\\Users\\HP WIN 8\\Desktop\\CodeBook.txt");
            bw = new BufferedWriter(file);
        
            for (int i = 0;i < codeBook.size();i++){
                for (int ind = 0;ind < codeBook.get(i).pixels.size();ind++){
                    bw.write(String.valueOf(codeBook.get(i).pixels.get(ind)));
                    bw.write(" ");
                }
                bw.newLine();
            }
            bw.flush();
            bw.close();
        }
        catch (IOException ex) {
            //
        } 
    }
    
    public void decompress(){
        FileReader file = null;
        BufferedReader bw = null;
        BufferedImage imageOut = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        
        try {
            file = new FileReader("C:\\Users\\HP WIN 8\\Desktop\\CodeBook.txt");
            bw = new BufferedReader(file);
            
            for (int i = 0;i < codeBookSize;i++){
                String toParse = bw.readLine();
                String [] arr;
                arr = toParse.split(" ");
                codeBook.add(new Label());
                
                for (int j = 0;j < arr.length;j++){
                    codeBook.get(i).pixels.add(Integer.parseInt(arr[j]));
                }
            }
        
            int x = 0;
        
            for (int i = 0;i < width;i += blockSize){
                for (int j = 0;j < height;j += blockSize){
                
                    Label temp = codeBook.get(compareToDivide(labels.get(x)));
                    x++;
                    int ind = 0;
                
                    for (int s = j;s < j + blockSize;s++){
                        for (int t = i;t < i + blockSize;t++, ind++){
                        
                        int color = temp.pixels.get(ind);
                        Color inGray = new Color(color, color, color);
                        imageOut.setRGB(t, s, inGray.getRGB());
                        }
                    }
                }
            }
        }
        catch (IOException ex) {
            //
        } 
        
        File ouptut = new File("grayscale.jpg");
         
        try {
            ImageIO.write(imageOut, "jpg", ouptut);

        } 
        catch (IOException ex) {
            //
        }
    }
    
    public double calculateAvg(ArrayList <Integer> label){
        double avg = 0.0;
        for (int i = 0;i < label.size();i++){
            avg += label.get(i);
        }
        avg /= label.size();
        return avg;
    }
    
    public int compareToDivide(Label x){  //loop to 
        int min = 256 * blockSize * blockSize;
        int ind = 0;
        int totalDiff;
        
        for (int i = 0;i < codeBook.size();i++){
            totalDiff = 0;
            for (int s = 0;s < blockSize * blockSize;s++){
                totalDiff += Math.abs(x.pixels.get(s) - codeBook.get(i).pixels.get(s));
            }
            if (totalDiff < min){
                min = totalDiff;
                ind = i;
            }
        }
        return ind;
    }
    
    public void divideToCodeBooks(ArrayList <Label> x){
        Label newBlock1 = new Label ();
        Label newBlock2 = new Label ();
        
        for (int s = 0;s < blockSize * blockSize;s++){
        
        ArrayList <Integer> temp = new ArrayList <Integer> ();
        
        for (int i = 0;i < x.size();i++)
            temp.add(x.get(i).pixels.get(s));
        
        double divider = calculateAvg(temp);
        newBlock1.pixels.add((int)divider - 1);
        newBlock2.pixels.add((int)divider + 1);
        
        temp.clear();
        
        }
        
        codeBook.add(newBlock1);
        codeBook.add(newBlock2);
        
    }
    
    public void createCodeBook(){
        ArrayList <ArrayList <Label>> compare = new ArrayList <ArrayList <Label>> (codeBook.size());
        int x = codeBook.size();
        for (int i = 0;i < x;i++){
            ArrayList <Label> t = new ArrayList <Label> ();
            compare.add(t);
        }
        
        for (int i = 0;i < labels.size();i++){
            Label temp = labels.get(i);
            compare.get(compareToDivide(temp)).add(temp);
        }
        
        for (int i = 0;i < compare.size();i++){
            divideToCodeBooks(compare.get(i));
        }
        
        if (codeBook.size() > 2){
            for (int i = 0;i < toRemove;i++){
                codeBook.remove(i);
            }
            toRemove *= 2;
        }
    }
    
    public void compress(){
        createBlocks();
        
        /* first iteration of creating code book */
        Label newBlock1 = new Label ();
        Label newBlock2 = new Label ();
        
        for (int s = 0;s < blockSize * blockSize;s++){
        
        ArrayList <Integer> temp = new ArrayList <Integer> ();
        
        for (int i = 0;i < labels.size();i++)
            temp.add(labels.get(i).pixels.get(s));
        
        double divider = calculateAvg(temp);    //  calcule avrage 
        newBlock1.pixels.add((int)divider - 1);    // new lable 
        newBlock2.pixels.add((int)divider + 1);
        
        temp.clear();
        
        }
        
        codeBook.add(newBlock1);
        codeBook.add(newBlock2);
        
        /* rest iterations */
        
        while (true){
            if (codeBook.size() == codeBookSize)
                break;
            createCodeBook();   
        }
        writeCodeBook();
        codeBook.clear();
    }
    
    public void show(){
        //System.out.println();
        for (int i = 0;i < codeBook.size();i++){
            codeBook.get(i).showLabel(blockSize);
        }
    }
    
    public static void main(String[] args) {
        
        Scanner scan = new Scanner(System.in);
        int x = scan.nextInt();
        int y = scan.nextInt();
        VectorQuantizer vq = new VectorQuantizer(x, y);
        
        vq.compress();
        //vq.show();
        vq.decompress();
    }
}

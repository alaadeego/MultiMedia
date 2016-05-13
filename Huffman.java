import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Huffman {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HuffmanTree huffman = new HuffmanTree()	;
		huffman.initail();
		System.out.println(" -------------------------------");
		huffman.construct();
	//	huffman.show();
		String c="";
		huffman.preorder(huffman.root,c );
		//huffman.print(huffman.root);
		System.out.println("Huffman code--------------------");
		//huffman.printhuffcode();
		//for(int i=0; i<)
		huffman.compress();
		huffman.decompress();
	}

}

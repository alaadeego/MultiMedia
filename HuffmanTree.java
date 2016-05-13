import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public  class HuffmanTree  {
	 private ArrayList<Node> leafs=new ArrayList<Node>();
	 private HashMap<Character ,String > huffmancode=new HashMap<Character ,String>(); //Arra to stor codes of Alpha
	 private HashMap<Character ,Integer> hm=new HashMap<Character ,Integer>(); // to Arrange alpha  from file 
	 String strToCompress="";
	 String compressedstr="";
	 String decompressedstr="";
   	 Node root=new Node();
	 String Huff[] = new String[128]; //  no need for it 
		 
	/*
	 *TO read String From file then count #of each alpha
	 * Construct the leafs ArrayLisr
	 * */
	 public void initail()                       
	 {
		File text=new File("a.txt");
		Scanner scnr;
		try {                                   //read  from file to String  
			scnr = new Scanner (text)		;
			while(scnr.hasNextLine())
			strToCompress=strToCompress+scnr.nextLine();
			}catch (FileNotFoundException e1){System.out.println("Enter Valid File ..!");e1.printStackTrace();}
						    	
			for(int i=0 ; i<strToCompress.length();i++)   //HashMap
			{
				char charAt=strToCompress.charAt(i);
				if(!hm.containsKey(charAt))
					hm.put(charAt, 1);
				else
					hm.put(charAt,hm.get(charAt)+1);
			}
			
			for(Map.Entry m:hm.entrySet())             								 // to loop in map 
			{	Character Char=(Character) m.getKey();            				     //convert(casting)  object to primitive
				Integer   value=(Integer) m.getValue();								//construct leafs ArrayList
				Node      node=new Node(Char.charValue(),value.intValue());
				this.put(node);
				
			}
	 }
	public void put(Node node )
	{
		
			leafs.add(node);
		
	}
	public void show( )
	{
		System.out.println("size of leaf= "+ leafs.size());
		for(int i=0 ;i<leafs.size();i++)
		{
			System.out.println(leafs.get(i).frequency + "   "+ leafs.get(i).symbol);
		}
	}
	public void construct()
	{ 	
		while(leafs.size()!=1)
		{	int ind1=0,ind2=0, min1=10000, min2=1000; 
			Node node=new Node();
			for(int i=0 ; i<leafs.size();i++)    //find 1st smallest element 
			{
				if(leafs.get(i).frequency<min1)
				{
					ind1=i;
					min1=leafs.get(i).frequency;
				}
				
			}
			for(int i=0 ; i<leafs.size();i++)   //find 2end smallest element 
			{
				if(i!=ind1 && leafs.get(i).frequency<min2)
				{
					ind2=i;
					min2=leafs.get(i).frequency;
				}
			}
			leafs.get(ind1).parent=node;
			leafs.get(ind2).parent=node;
			
			
			node.left=leafs.get(ind1);
			node.right=leafs.get(ind2);
			node.frequency=leafs.get(ind1).frequency+leafs.get(ind2).frequency;
			
			int v=leafs.get(ind2).frequency;
			char c=leafs.get(ind2).symbol;
			leafs.remove(ind1);
			for(int i=0; i<leafs.size();i++)
			{
				if(leafs.get(i).frequency==v && leafs.get(i).symbol==c)
					leafs.remove(i);
					
			}
			leafs.add(node);
		
		}
		root=leafs.get(0);
	}
	
	public void preorder(Node node, String c) 
	{ 
		 node.code = c;
		 if(node.left !=null )
		 {
			 preorder(node.left,c+'0');
			 preorder(node.right,c+'1');
		 }
		 else
		 {
			char Char=node.symbol;
			 Huff[Char]=c;
			 char  s=node.symbol;
			 String  code=c;
			 huffmancode.put(s, code);
			 
			 
			 
		 }
		 
	}
	public void printhuffcode()
	{
		
		System.out.println(huffmancode.toString());
	}
	public void print(Node root)
	{	
		if(root == null)
			return;
		System.out.println("Node freg="+root.frequency +" Node char= "+root.symbol+"Node code"+ root.code );
		 print(root.left); 
		 print(root.right);	
	}
	
	public void  compress()
	{
		int sz =strToCompress.length();
		for(int i=0; i<sz;i++)
		{
			compressedstr=compressedstr+huffmancode.get(strToCompress.charAt(i));
		//	System.out.println(strToCompress.charAt(i) +" || "+ huffmancode.get(strToCompress.charAt(i)) );
		}
		//System.out.println(compressedstr);
//		System.out.println("Bytes");
//		byte[] bytes=compressedstr.getBytes(StandardCharsets.UTF_8);
//		Arrays.toString(bytes);
	}
	
	public void decompress()
	{
		String s="";
		
		for(int i=0;i<compressedstr.length();i++)
		{
			s=s+compressedstr.charAt(i);
			//if(huffmancode.containsValue(s))
			//{	
					for(Map.Entry m:huffmancode.entrySet())             								  
					{	
						if(s.equals(m.getValue()))
						{
							decompressedstr=decompressedstr+m.getKey();
							
							System.out.println(s +"  ||  "+m.getKey());
							s="";
							
						}
						
					}
					
			//}
		}
			
			
			
			
			
		
//			s=compressedstr.charAt(i)+s;
//			if(huffmancode.containsValue(s))
//			{	
//				for(Map.Entry m:huffmancode.entrySet())             								  
//				{	
//					if(m.getValue().equals(s))
//						decompressedstr=m.getKey()+decompressedstr;            				     
//					
//					
//				}
//				s="";
//			}
//			
//		}
	System.out.println("Compressed String is ");
	System.out.println(decompressedstr);
//		//{a=001, c=10011, d=0100, e=101, h=1000, i=1111, l=0101, n=1110, o=000, r=1100, s=1101, t=011, u=10010}
	}
	
	
}

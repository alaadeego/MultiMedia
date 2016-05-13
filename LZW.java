import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.io.File;
import java.util.*;
import java.awt.Button;


public class LZW{

	private JFrame frame;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LZW window = new LZW();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
	
		});
	}

	/**
	 * Create the application.
	 */
	public LZW() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(SystemColor.inactiveCaption);
		frame.setBounds(100, 100, 454, 286);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnCom = new JButton("Compress");
		btnCom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s=new String();
				s=textField.getText();
				ArrayList<String> Dictionary=new ArrayList<>();
				String array="";
				File text=new File(s);
				Scanner scnr;
				try {
					scnr = new Scanner (text);
					while(scnr.hasNextLine())
					{
						 array=array+scnr.nextLine();
						//System.out.println(line);
						
					}
					System.out.println(array);
					ArrayList<Integer> Compress=new ArrayList<>();
					int p=0, sz=array.length(),index=0;
					String str=Character.toString(array.charAt(p));
					Boolean found=true,end=true;
					for(int i=0 ;i<128;i++)
						Dictionary.add(Character.toString((char)i));
					System.out.println(Dictionary.size());
							
					while(p<sz && end)
					{
						while(found && end)
						{
							found=false;
							for(int i=0 ; i<Dictionary.size();i++)
							{
								if(Dictionary.get(i).equals(str))
								{
									found=true;
									index=i;
									
									p++;
									if(p>=sz)
									{
										end=false;
										break;
									}
									str=str+array.charAt(p);
									break;
									
								}
							}
						}
						Compress.add(index);
						//System.out.println(index);
						Dictionary.add(str);
						if(p<sz)
							 str=Character.toString(array.charAt(p));
						found=true;
							
					}
					System.out.println("Compration Symbol are :");
					for(int i=0 ;i<Compress.size(); i++)
						System.out.println(Compress.get(i));
				
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					System.out.println("Enter Valid File ..!");
					e1.printStackTrace();
				}
					
					
				
			}
		});
		btnCom.setBounds(223, 139, 119, 36);
		btnCom.setFont(new Font("Tempus Sans ITC", Font.BOLD, 15));
		frame.getContentPane().add(btnCom);
		
		textField = new JTextField();
		textField.setBounds(97, 74, 225, 36);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnDec = new JButton("Decompress");
		btnDec.setFont(new Font("Tempus Sans ITC", Font.BOLD, 15));
		btnDec.setBounds(81, 139, 119, 36);
		btnDec.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> Dictionary=new ArrayList<>();
				ArrayList<Integer>Decompress=new ArrayList<>();
				String current="",prestr="",str="", string="";
				Boolean found=false;
				for(int i=0 ;i<128;i++)
					Dictionary.add(Character.toString((char)i));
				String s=new String();
				s=textField.getText();
		
				try {
					FileReader file1 = new FileReader(s);
					Scanner sc=new Scanner(file1);
					while(sc.hasNext())
					{
						Decompress.add(sc.nextInt());
					}
					sc.close();
					int sz=Decompress.size(),value=0;
					for(int  i=0 ;i<sz;i++)
					{
						value=Decompress.get(i);
						System.out.println(value + Dictionary.size() );
						if(value < Dictionary.size())
						{
							current=Dictionary.get(value);
							string=string +current;
							str=prestr+ current.charAt(0);
							found=false;
							String aa="";
							for(int j=0;j<Dictionary.size();j++)
							{
								aa=Dictionary.get(j);
								if(str.equals(aa))
									found=true;
							}
							
							if(!found)
								Dictionary.add(str);
							prestr=current;
						}
						else
						{
							str=prestr+ prestr.charAt(0);
							string=string +str;
							Dictionary.add(str);
							prestr=str;
						}
						
					}
					System.out.println(string);
					
				
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					System.out.println("Enter Valid File ..!");
					e1.printStackTrace();
				}
					
				
				
			}
		});
		frame.getContentPane().add(btnDec);
		
		JLabel lblNewLabel = new JLabel("Link :");
		lblNewLabel.setForeground(Color.BLUE);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(24, 72, 56, 36);
		frame.getContentPane().add(lblNewLabel);
	}
}

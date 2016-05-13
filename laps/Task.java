package laps;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Task {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner input=new Scanner (System.in);
	
		String s1=input.next();
		String s2=input.next();
		String sd="";
		String sadd="";
		Boolean found;
		for(int i=0 ;i<s2.length();i++)           //loop for delete
		{	found=false;
			for(int j =0 ; j<s1.length();j++ )
			{
				if(s2.charAt(i)==s1.charAt(j))
					found=true;
			}
			if(!found)
				System.out.println("Delete Char "+s2.charAt(i)+" At index "+ i );
				
			else
				sd=sd+s2.charAt(i);
			
		}
		for(int i=0;i<s1.length() && i<s2.length();i++)
		{
			
			if(s2.charAt(i)!=s1.charAt(i))
			{
				System.out.println("Add Char "+s1.charAt(i));
				System.out.println("Delet Char "+s2.charAt(i));
				String ss=s2;
				s2="";
				for(int k=0; k<=i;k++)
					s2+=s1.charAt(k);
				for(int k=i+1;k<ss.length();k++)
					s2+=ss.charAt(k);
			}
		
		}
		
			
		
		System.out.println(s2);

	}

}

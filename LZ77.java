import java.util.*;
import java.util.Scanner;

public class LZ77 {


	public static int stringToint( String str ){
        int i = 0, number = 0;
      boolean isNegative = false;
        int len = str.length();
        if( str.charAt(0) == '-' ){
            isNegative = true;
            i = 1;
        }
        while( i < len ){
            number *= 10;
            number += ( str.charAt(i++) - '0' );
        }
        if( isNegative )
        number = -number;
        return number;
    }  
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String arr="abaababaabbbbbbbbbbbba";  //abaababaabbbbbbbbbbbba
		String comp="";
		int point=0,sz=arr.length();
		while(point<sz)
		{
			int i=0,j=0 , iC=-1, jC=0,pB=point-1,pBC=0;
			char nextChar=' ';
			Boolean found =false;
			while(pB>=0)
			{
				if(arr.charAt(point)==arr.charAt(pB))
				{
					found=true;
					i=pB+1;
					j=point+1;
					while(i!=point &&j<sz)
					{
						if(arr.charAt(i)!=arr.charAt(j))
							break;
						i++;
						j++;
					}
					
					i=i-pB;
					j=j-point;
					
					if(i>iC)
					{
						iC=i;
						pBC=point-pB;
						jC=j;
				
						if(i+point==sz)
							nextChar='-';
						else
							nextChar=arr.charAt(j+point);
							
						
					}
				}
				pB--;
			}
			
			if(found)
			{
				System.out.println(pBC+" ,"+iC+" ,"+ nextChar );
				comp=comp+pBC+","+iC+","+nextChar+",";
			
			}
			if(!found)
			{
				System.out.println("0 ,0 ,"+arr.charAt(point));
				nextChar=arr.charAt(point);
				comp=comp+"0,0,"+nextChar+",";
			}
			
			point+=jC+1;
				
		}
		
		System.out.println(comp);
		
	
		//-----------Decompress-----------------------0,0,a,0,0,b,2,1,a,3,2,b,5,3,b,2,2,b,5,5,b,14,2,-,

/*	String tag="0,0,a,0,0,b,2,1,a,3,2,b,5,3,b,2,2,b,5,5,b,14,2,-,"; //0,0,a,0,0,b,2,1,a,3,2,b,5,3,b,2,2,b,5,5,b,14,2,-,	
		String decomp="";
		int i=0,j=2,k=4,sz=tag.length();
		while(k<sz&& i<sz)
		{
			if(tag.charAt(i)=='0')
				decomp=decomp+tag.charAt(k);
			
			else
			{
				int sz2=decomp.length();
				System.out.println("arr:"+decomp+" "+sz2);
				String ind="",len="";
				while(tag.charAt(i)!=',')
				{
					ind=ind+tag.charAt(i);
					i++;
				}
				System.out.println("i="+i);
				while(tag.charAt(j)!=',')
				{
					len=len+tag.charAt(j);
					j++;
				}
				//j++;//
				int index=Integer.parseInt(ind);
				int length=Integer.parseInt(len);
			
				index=sz2-index;
				ind="";len="";
				while(length!=0)
				{
					decomp=decomp+decomp.charAt(index);
					index++;
					length--;
				}
				if(tag.charAt(k)!='-')
					decomp=decomp+tag.charAt(k);
				
			}
			
			i=k+2;
		
			if(i<sz)
			{
				j=i;
				while( tag.charAt(j)!=',')
				{ j++; }
				j++;
				k=j;
				while(tag.charAt(k)!=',')
				{ k++; }
				k++;
					System.out.println(i+" "+j+" "+k);
			}
		
		}
*/
	
		
 }
}
package A2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class main 
{
    public static void Compress(File inp,Formatter output,File Probability) throws FileNotFoundException
    {
        Scanner scan=new Scanner(inp);
        Formatter pro=new Formatter(Probability);
        String input=scan.next();
        input += "$";
        Map<Character, Double> cnt = new HashMap<>();
        Map<Character,Double> prob = new HashMap<>();
        Map<Character,Double> low = new HashMap<>();
        Map<Character,Double> high = new HashMap<>();      
        Double tmp = 0.0;
        for(int i=0; i < input.length() ; i++)   // for counter 
        {
            if(cnt.get(input.charAt(i))==null)
                cnt.put(input.charAt(i), 1.0);
            else
                cnt.put(input.charAt(i), cnt.get(input.charAt(i))+1.0);
        }
        for (Map.Entry<Character, Double> entry : cnt.entrySet())  // for prob 
        {
            prob.put(entry.getKey(), entry.getValue()/input.length());
        }
        for (Map.Entry<Character, Double> entry : prob.entrySet())   // for low high
        {
            low.put(entry.getKey(),tmp);   // temp =0 in it1 
            tmp+=entry.getValue();
            high.put(entry.getKey(),tmp);
            //System.out.println(entry.getKey() + " " + low.get(entry.getKey())+ " " + high.get(entry.getKey()));
            pro.format("%c %.6f %.6f\n",entry.getKey(),low.get(entry.getKey()),high.get(entry.getKey()));  // prob in file
        }
        Double lower = low.get(input.charAt(0)),upper = high.get(input.charAt(0));
        for(int i=1 ; i < input.length() ; i++)
        {
            Double Range=upper-lower,Lower_tmp=lower;
            lower = lower +Range*low.get(input.charAt(i));
            upper = Lower_tmp + Range*high.get(input.charAt(i));
        }
        Double mean = (upper+lower)/2;
        output.format("%.6f\n",mean);
        output.close();
        pro.close();
    }
    public static void Decompress(File inp,File Pro,Formatter out) throws FileNotFoundException//,Formatter output) throws FileNotFoundException
    {
        Scanner scan = new Scanner(Pro);
        Scanner scan2 = new Scanner(inp);
        Double input =scan2.nextDouble(),low,high;
        ArrayList<Character> C=new ArrayList<Character>();
        ArrayList<Double> Low=new ArrayList<Double>(),High=new ArrayList<Double>();
        Character c;
        Boolean ok=false;
        while(scan.hasNext())      // l & h in array
        {
            String tmp=scan.next();
            c=tmp.charAt(0);
            low=scan.nextDouble();
            high=scan.nextDouble();
            C.add(c);
            Low.add(low);
            High.add(high);
        }
        while(!ok)
        {                       
            ok=true;
            for(int i=0 ;i < Low.size() ; i++)
            {
                if(input <= High.get(i) && input >= Low.get(i))
                {
                    ok=false;
                    if(C.get(i)=='$')
                    {
                        ok=true;
                        break;
                    }
                    out.format("%s",C.get(i));
                    low=Low.get(i);
                    Double upper=High.get(i);
                    Double Range=upper-low;
                    input = (input - low)/Range; // mean 
                    break;
                }
            }
        }
    }
    public static void main(String[] args) throws FileNotFoundException 
    {
        File input = new File("Input");
        File Prob = new File("Probability");
        Formatter out=new Formatter("Output");   
        Compress(input,out,Prob);
       // Decompress(input,Prob,out);
    }
}

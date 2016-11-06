package com.cooltrickshome;

// Print out DNS Record for an Internet Address
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

public class DNSLookup
{
	static FileWriter fw=null;
	static BufferedWriter bw=null;
	
	public static void main(String args[]) throws IOException
	{
		FileReader r=new FileReader("subdomain.txt");
		BufferedReader br=new BufferedReader(r);
		fw =new FileWriter("dnsinformation.txt");
		bw=new BufferedWriter(fw);
		String line=br.readLine();
		while(line!=null)
		{
			System.out.println("DNS information for "+line);
			bw.write("DNS information for "+line);
			bw.newLine();
			getDNSRecord(line);
			System.out.println("**************************************************\n");
			bw.write("**************************************************");
			bw.newLine();
			line=br.readLine();
		}
		br.close();
		r.close();
		bw.close();
		fw.close();
	}
	
    public static void getDNSRecord(String domain) throws IOException
    {
        try
        {
            InitialDirContext iDirC = new InitialDirContext();
            
            // Use below if you want to extract only certain record like A, CNAME, MX
            //Attributes attributes = iDirC.getAttributes("dns:/" + domain,new String[]{"A","CNAME","MX"});
            
            Attributes attributes = iDirC.getAttributes("dns:/" + domain);
            NamingEnumeration attributeEnumeration = attributes.getAll();
            while (attributeEnumeration.hasMore())
            {
            	Object info=attributeEnumeration.next();
                System.out.println("" + info);
                bw.write("" + info);
                bw.newLine();
            }
            attributeEnumeration.close();
        }
        catch (NamingException exception)
        {
            System.err.println("No DNS record for '" + domain + "'");
        }
    }
}
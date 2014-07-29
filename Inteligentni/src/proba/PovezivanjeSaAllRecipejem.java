/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package proba;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import org.apache.log4j.PropertyConfigurator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author TFamilija
 */
public class PovezivanjeSaAllRecipejem {
    private String inputFileName;
    
    private void izvuciPodatkeIzRecepta(String linkDoRecepta) throws Exception {
 
        String ceoLink = "http://allrecipes.com"+linkDoRecepta;
        
        String link =URLEncoder.encode(ceoLink, "UTF-8");
        
	String url = "http://www.w3.org/2012/pyMicrodata/extract?uri="+link+"&format=turtle&vocab_expansion=false&vocab_cache=true";
 
	URL obj = new URL(url);
	HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
	// optional default is GET
	con.setRequestMethod("GET");
 
	//add request header
	con.setRequestProperty("User-Agent", "Mozilla/5.0");
 
	int responseCode = con.getResponseCode();
	System.out.println("\nSending 'GET' request to URL : " + url);
	System.out.println("Response Code : " + responseCode);
 
	BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
	String inputLine;
	StringBuffer response = new StringBuffer();
 
	while ((inputLine = in.readLine()) != null) {
		response.append(inputLine);
	}
	in.close();
 
	//print result
	System.out.println(response.toString());
        
        procitajPodatke(response.toString());
 
	}
    
      private void pronadjiRecepte() throws Exception {
 
	String url = "http://allrecipes.com/search/default.aspx?ms=0&origin=Home+Page&rt=r&qt=i&wt=checken+soup&pqt=i&fo=0";
 
	URL obj = new URL(url);
	HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
	// optional default is GET
	con.setRequestMethod("GET");
 
	//add request header
	con.setRequestProperty("User-Agent", "Mozilla/5.0");
 
	int responseCode = con.getResponseCode();
	System.out.println("\nSending 'GET' request to URL : " + url);
	System.out.println("Response Code : " + responseCode);
 
	BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
	String inputLine;
	StringBuffer response = new StringBuffer();
 
	while ((inputLine = in.readLine()) != null) {
		response.append(inputLine);
	}
	in.close();
 
	//print result
	System.out.println(response.toString());
        
        
        Document doc;
	try {
 
		// need http protocol
		doc = Jsoup.connect("http://allrecipes.com/search/default.aspx?ms=0&origin=Home+Page&rt=r&qt=i&wt=bread&pqt=i&fo=0").get();
 
		// get page title
		String title = doc.title();
		System.out.println("title : " + title);
 
                Elements divs = doc.getElementsByClass("recipe-info");
                for (Element div : divs) {
                    Elements links = div.select("a[href]");
                    for (Element link : links) {
                        String linkDoRecepta = link.attr("href");
                        if(linkDoRecepta.startsWith("/")){                        
			// get the value from href attribute
			System.out.println("link do recepta : " + linkDoRecepta);
			    izvuciPodatkeIzRecepta(linkDoRecepta);
                        }
                    }
                }
                
		
 
	} catch (IOException e) {
		e.printStackTrace();
	}
        
 
	}
    
    public static void main(String[] args) throws Exception {
     //   org.apache.log4j.BasicConfigurator.configure();
        //PropertyConfigurator.configure(args[0]);
		PovezivanjeSaAllRecipejem http = new PovezivanjeSaAllRecipejem();
 
		System.out.println("Testing 1 - Send Http GET request");
		http.pronadjiRecepte();
 
		
 
	}

    private void procitajPodatke(String tekst) {
       //Log.setLog4j("jena-log4j.properties");   
      // LogCtl.setCmdLogging("jena-log4j.properties");
        // create an empty model
        Model model = ModelFactory.createDefaultModel();

        // use the FileManager to find the input file
    /*    InputStream in = FileManager.get().open( "" );
            if (in == null) {
                throw new IllegalArgumentException(
                                 "File: " + inputFileName + " not found");
        }*/

        // read the RDF/XML file
        model.read(tekst, "TURTLE");

        // write it to standard out
        model.write(System.out);
      
    }
    
}

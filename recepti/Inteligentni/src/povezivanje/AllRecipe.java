/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package povezivanje;

import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.util.FileManager;
import domen.Recept;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.PropertyConfigurator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import util.Constants;

/**
 *
 * @author TFamilija
 */
public class AllRecipe {
     
    
    public List<Model> vratiRecepte(String parametriPretrage) throws Exception {
        //parametre pretrage srediti na formi - staviti pluseve i slicno    \\
        //http://allrecipes.com/search/default.aspx?ms=0&origin=Home+Page&rt=r&qt=i&wt=checken+soup&pqt=i&fo=0  
	String url = "http://allrecipes.com/search/default.aspx?ms=0&origin=Home+Page&rt=r&qt=i&wt"+parametriPretrage+"&pqt=i&fo=0";
 
	URL obj = new URL(url);
	HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
	// optional default is GET
	con.setRequestMethod("GET");
 
	//add request header
	con.setRequestProperty("User-Agent", "Mozilla/5.0");
 
	int responseCode = con.getResponseCode();
	System.out.println("\nSending 'GET' request to URL : " + url);
	System.out.println("Response Code : " + responseCode);
        StringBuffer response;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
            }
        }
 
	//print result
	System.out.println(response.toString());
        
        
        Document doc;
	
 
	doc = Jsoup.connect("http://allrecipes.com/search/default.aspx?ms=0&origin=Home+Page&rt=r&qt=i&wt="+parametriPretrage+"&pqt=i&fo=0").get();
 
	String title = doc.title();
	System.out.println("title : " + title);
        List<Model> modeli = new ArrayList<Model>();
                Elements divs = doc.getElementsByClass("recipe-info");
                for (Element div : divs) {
                    Elements links = div.select("a[href]");
                    for (Element link : links) {
                        String linkDoRecepta = link.attr("href");
                        if(linkDoRecepta.startsWith("/")){                        
			System.out.println("link do recepta : " + linkDoRecepta);
			Model model = procitajRecept(linkDoRecepta);
                        modeli.add(model);
                        }
                    }
                }
                
	return modeli;
    }

    
    protected Model procitajRecept(String id) throws Exception {
        String ceoLink = "http://allrecipes.com"+id;
        
        String link =URLEncoder.encode(ceoLink, "UTF-8");
        
	String url = "http://www.w3.org/2012/pyMicrodata/extract?uri="+link+"&format=turtle&vocab_expansion=false&vocab_cache=true";
            
        Model model = popuniReceptZajednickimPodacima(url);
        return model;
    }

    
    protected Model popuniReceptZajednickimPodacima(String podatakZaCitanje) throws Exception {
         Model model = ModelFactory.createDefaultModel();

        model.read(podatakZaCitanje, "TURTLE");

        model.write(System.out);
      //  Property p = model.getProperty(Constants.SCHEMA, "author");
      //  System.out.println(p.toString());
        
        
        
    /*    System.out.println("*************");
        Property p = model.getProperty(Constants.SCHEMA, "author");
        Resource x = model.getResource(Constants.SCHEMA+"/author");
        Statement s= x.getProperty(p);
        System.out.println(s.getLiteral().toString());
        
        System.out.println("*************");
        Recept recept = new Recept();*/
        return model;
    }
    
}

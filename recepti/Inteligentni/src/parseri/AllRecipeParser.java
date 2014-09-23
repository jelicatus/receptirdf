/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package parseri;

import domen.AgregatniRejting;
import domen.NutritivneInformacije;
import domen.Recept;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author TFamilija
 */
public class AllRecipeParser {
    
    private static String url = "http://allrecipes.com/search/default.aspx?qt=k&wt=";
    List<Recept> recepti = new ArrayList<Recept>();
    
     public List<Recept> vratiRecepte(String parametriPretrage) throws MalformedURLException, IOException, org.json.simple.parser.ParseException {
        
        url += parametriPretrage+"&rt=r&origin=Search%20Results&hb=";
        System.out.println(url);
        
	
        String mainDishes = "80&p34=SR_FilterByMainDish";
        String salads = "96&p34=SR_FilterBySalad";
        
      
        procitajPetStrana(url+mainDishes);
        procitajPetStrana(url+salads);
       
        
        return recepti;
        
    }
     
    private void procitajPetStrana(String url) throws IOException, MalformedURLException, ParseException{        
        for (int i = 1; i <= 5; i++) {
        String urlStr = url+"&Page="+i;
        Document doc;
	
        doc = Jsoup.connect(urlStr).get();
        
        String title = doc.title();
       
	System.out.println("title : " + title);
        Elements divs = doc.getElementsByClass("recipe-info");
                for (Element div : divs) {
                    Elements links = div.select("a[href]");
                    for (Element link : links) {
                        String linkDoRecepta = link.attr("href");
                        if(linkDoRecepta.startsWith("/") && !linkDoRecepta.endsWith("true") && recepti.size()<100){                        
			System.out.println("link do recepta : " + linkDoRecepta);
			Recept r = procitajRecept(linkDoRecepta);
                        if(r!=null) recepti.add(r);
                        }
                    }
                }
        }
       
    }


    protected Recept procitajRecept(String id) throws MalformedURLException, IOException, org.json.simple.parser.ParseException {
        String ceoLink = "http://allrecipes.com"+id;        
        String link =URLEncoder.encode(ceoLink, "UTF-8");        
	String url = "http://www.w3.org/2012/pyMicrodata/extract?uri="+link+"&format=json&vocab_expansion=false&vocab_cache=true";   
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
        System.out.println(response.toString()+" ovo je odgovor :D");
        
        return  popuniReceptZajednickimPodacima(response.toString());
    }

    protected Recept popuniReceptZajednickimPodacima(String podatakZaCitanje) throws org.json.simple.parser.ParseException {
        JSONParser parser = new JSONParser();
        Object parse = parser.parse(podatakZaCitanje);

        JSONObject jsonObject = (JSONObject) parse;
        
        JSONArray graph = (JSONArray) jsonObject.get("@graph");
        
        int brojac = 0;
        
        Recept recept = new Recept();
        NutritivneInformacije ni = new NutritivneInformacije();
        AgregatniRejting ar = new AgregatniRejting();
        recept.setNutritivneInformacije(ni);
        recept.setRejting(ar);
        
         
        if(graph!=null)  {  Iterator<String> iterator = graph.iterator();
        while (iterator.hasNext() && brojac!=graph.size()) {
            try {
                JSONObject objekatUGrafNizu =  (JSONObject) graph.get(brojac);
                if(((String)objekatUGrafNizu.get("@id")).startsWith("http://allrecipes.com")){
                    JSONObject mdItem = (JSONObject) objekatUGrafNizu.get("md:item");
                JSONArray list = (JSONArray) mdItem.get("@list");
                JSONObject elementListe = (JSONObject) list.get(0);
                
                String naziv = (String) elementListe.get("name");
                recept.setNaziv(naziv);
                
                String vremePripreme = (String) elementListe.get("totalTime");
                recept.setVremePripreme(vremePripreme);
                
                String slika = (String) elementListe.get("image");
                recept.setSlika(new URI(slika));
                
                String brojOsoba = (String) elementListe.get("recipeYield");
                recept.setBrojPorcija(brojOsoba);
                
                JSONObject agregatniRejting = (JSONObject) elementListe.get("aggregateRating");
                String rejting = (String) agregatniRejting.get("ratingValue");
                double rejtingDouble = Double.parseDouble(rejting);
                long rejtingLong = (long) rejtingDouble;
                ar.setRejting(rejtingLong);
                
                JSONObject nutritivneInformacije = (JSONObject) elementListe.get("nutrition");
                if(nutritivneInformacije!=null){
                String proteini = (String) nutritivneInformacije.get("proteinContent");
                double kolicinaProteina = Double.parseDouble(proteini.substring(0,proteini.length()-1));
                
                ni.setKolicinaProteina(kolicinaProteina);
                
                String vlakna = (String) nutritivneInformacije.get("fiberContent");
                double kolicinaVlakana = Double.parseDouble(vlakna.substring(0,vlakna.length()-1));
                
                ni.setKolicinaVlakana(kolicinaVlakana);
                
                String ugljeniHidrati = (String) nutritivneInformacije.get("carbohydrateContent");
                double kolicinaUgljenihHidrata = Double.parseDouble(ugljeniHidrati.substring(0,ugljeniHidrati.length()-1));
                
                ni.setKolicinaUgljenihHidrata(kolicinaUgljenihHidrata);
                
                String masti = (String) nutritivneInformacije.get("fatContent");
                double kolicinaMasti = Double.parseDouble(masti.substring(0,masti.length()-1));
                
                ni.setKolicinaMasti(kolicinaMasti);
                
                String kalorije = (String) nutritivneInformacije.get("calories");
                int brojKalorija = Integer.parseInt(kalorije);
                
                ni.setBrojKalorija(brojKalorija);
                } else return null;
                
                JSONArray sastojci = (JSONArray) elementListe.get("ingredients"); 
                Iterator<String> iterator2 = sastojci.iterator();
                int brojac2 = 0;
                while (iterator2.hasNext() && brojac2!=sastojci.size()) {
                    String sastojak = (String) sastojci.get(brojac2);
                    sastojak = sastojak.replace("\n","").trim();
                    sastojak = sastojak.replace("\t", "").trim();
                    recept.getSastojciString().add(sastojak);
                    brojac2++;
                }
                return recept;   
                }
                brojac++;
                
                }
                
             catch (Exception e) {
                e.printStackTrace();
                
            }

        }
         
        }        
        return null;
    }
    
    
}

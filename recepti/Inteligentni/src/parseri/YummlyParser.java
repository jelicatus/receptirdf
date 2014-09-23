package parseri;

import domen.AgregatniRejting;
import domen.NutritivneInformacije;
import domen.Recept;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import sun.net.www.http.HttpClient;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author TFamilija
 */
public class YummlyParser {
    
    public List<Recept> vratiRecepte(String parametriPretrage) throws MalformedURLException, IOException, org.json.simple.parser.ParseException, URISyntaxException {
        String url = "http://api.yummly.com/v1/api/recipes?_app_id=85fa428a&_app_key=b120c80efb746ba15e098e03d829a7cc&q="+parametriPretrage+"&maxResult=100";
        
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");

        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        int responseCode = con.getResponseCode();
        System.out.println("\nSalje se GET zahtev na URL : " + url);
        System.out.println("Kod odgovora : " + responseCode);
        StringBuffer response;
            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String inputLine;
                response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
        }

        String json = response.toString();
        
        System.out.println(json);

        JSONParser parser = new JSONParser();
        Object parse = parser.parse(json);

        JSONObject jsonObject = (JSONObject) parse;

        String name = (String) jsonObject.get("name");
        System.out.println(name);

        JSONArray msg = (JSONArray) jsonObject.get("matches");

        int brojac = 0;

        Iterator<String> iterator = msg.iterator();
        List<Recept> recepti = new ArrayList<Recept>();
        while (iterator.hasNext() && brojac!=msg.size()) {
                JSONObject objekat = (JSONObject) msg.get(brojac);

                String id = (String) objekat.get("id");
                System.out.println(id);
                Recept recept = procitajRecept(id);
                recepti.add(recept);
                brojac++;

            

        }
        return recepti;        
        
    }

    protected Recept procitajRecept(String id) throws MalformedURLException, IOException, org.json.simple.parser.ParseException, URISyntaxException {
        String url = "http://api.yummly.com/v1/api/recipe/" + id + "?_app_id=85fa428a&_app_key=b120c80efb746ba15e098e03d829a7cc";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");

        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        int responseCode = con.getResponseCode();
        System.out.println("\nSlanje GET zahteva na URL : " + url);
        System.out.println("Kod odgovora : " + responseCode);
        StringBuffer response;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }

        Recept recept = popuniReceptZajednickimPodacima(response.toString());
        
        return recept;
    }

    protected Recept popuniReceptZajednickimPodacima(String podatakZaCitanje) throws org.json.simple.parser.ParseException, URISyntaxException {
        JSONParser parser = new JSONParser();
        Object parse = parser.parse(podatakZaCitanje);

        JSONObject jsonObject = (JSONObject) parse;
        
        Recept recept = new Recept();
        int brojKalorije=0;
        double kolicinaProteini=0;
        double kolicinaVlakna=0;
        double kolicinaUgljenihHidrati=0;
        double kolicinaMasti=0;
        

        //izvlacenje kalorija
        JSONArray nutritionEstimates = (JSONArray) jsonObject.get("nutritionEstimates");
        
        int brojac = 0;        

        Iterator<String> iterator = nutritionEstimates.iterator();
        while (iterator.hasNext() && brojac!=nutritionEstimates.size()) {
            try {
                JSONObject objekat = (JSONObject) nutritionEstimates.get(brojac);
                String podatak =(String) objekat.get("attribute"); 
                if(podatak.equals("ENERC_KCAL")){ 
                    System.out.println(podatak);                    
                    Double kalorije = (Double) objekat.get("value");
                    System.out.println("Kalorje double:"+kalorije);
                    brojKalorije = kalorije.intValue();                
                    
                    System.out.println(brojKalorije);
                }
                if(podatak.equals("FAT")){ 
                    System.out.println(podatak);
                   
                    kolicinaMasti = (Double) objekat.get("value");
                    
                   
                   
                }
                if(podatak.equals("PROCNT")){ 
                    System.out.println(podatak);
                    
                    kolicinaProteini = (Double) objekat.get("value");
                    
                    
                    System.out.println(kolicinaProteini);
                }
                if(podatak.equals("CHOCDF")){ 
                    System.out.println(podatak);
                   
                    kolicinaUgljenihHidrati = (Double) objekat.get("value");
                    
                    System.out.println(kolicinaUgljenihHidrati);
                }
                if(podatak.equals("FIBTG")){ 
                    System.out.println(podatak);
                    
                    kolicinaVlakna= (Double) objekat.get("value");
                 
                    System.out.println(kolicinaVlakna);
                }
                brojac++;

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        if(brojKalorije!=0 || kolicinaProteini!=0 || kolicinaVlakna!=0 || kolicinaUgljenihHidrati!=0 ||
        kolicinaMasti !=0){
            NutritivneInformacije nutritivneInformacije = new NutritivneInformacije();
            nutritivneInformacije.setBrojKalorija(brojKalorije);
            nutritivneInformacije.setKolicinaMasti(kolicinaMasti);
            nutritivneInformacije.setKolicinaProteina(kolicinaProteini);
            nutritivneInformacije.setKolicinaUgljenihHidrata(kolicinaUgljenihHidrati);
            nutritivneInformacije.setKolicinaVlakana(kolicinaVlakna);
            recept.setNutritivneInformacije(nutritivneInformacije);
        }
        if(jsonObject.get("images")!=null){
        JSONArray images = (JSONArray) jsonObject.get("images");
        JSONObject slike =  (JSONObject) images.get(0);
        String slika = (String) slike.get("hostedLargeUrl");
        recept.setSlika(new URI(slika));
        System.out.println("Link do slike: " + slika);
        }
        
        if(jsonObject.get("name")!=null){
        String naziv = (String) jsonObject.get("name");
        recept.setNaziv(naziv);
        System.out.println("Naziv recepta: "+naziv);
        }
        
        if(jsonObject.get("yield")!=null){
        String brojOsoba = (String) jsonObject.get("yield");
        recept.setBrojPorcija(brojOsoba); 
        System.out.println("Za: "+brojOsoba+ " osoba");
        }
        
        if(jsonObject.get("totalTimeInSeconds")!=null){
        Long vremePripremeUSekundama = (Long) jsonObject.get("totalTimeInSeconds");
        recept.setVremePripreme(ISO8601Vreme(vremePripremeUSekundama));         
        }
        
        if(jsonObject.get("rating")!=null){
        /*JSONArray aggregateRating = (JSONArray) jsonObject.get("aggregateRating"); 
        JSONObject rating =  (JSONObject) aggregateRating.get(0);
        Long rejting = (Long) rating.get("ratingValue");*/
        Long rejting = (Long) jsonObject.get("rating");
        AgregatniRejting agregatniRejting = new AgregatniRejting();
        agregatniRejting.setRejting(rejting);
        recept.setRejting(agregatniRejting);
        System.out.println("Rejting: "+rejting);
        }
        
        JSONArray sastojci = (JSONArray) jsonObject.get("ingredientLines");
        int brojac1 = 0;

        List<String> sviSajstojci = new ArrayList<String>();
        Iterator<String> iterator1 = sastojci.iterator();
        while (iterator.hasNext() && brojac1!=sastojci.size()) {
           String clanNiza = (String) sastojci.get(brojac1);
           sviSajstojci.add(clanNiza);
           brojac1++;
        }
        
        recept.setSastojciString(sviSajstojci);
        return recept;
    }
    
    public String ISO8601Vreme(double sekundi){
        String standard = "PT";
        if(sekundi>3600){
            int brojSati = (int) (sekundi/3600);
            standard += brojSati+"H";
            double ostatak = sekundi - 3600*brojSati;
            if(ostatak>60){
                int brojMinuta = (int) (ostatak/60);
                standard += brojMinuta+"M";
            }
            return standard;
        }
        if(sekundi>60){
                int brojMinuta = (int) (sekundi/60);
                standard += brojMinuta+"M";
        }
        return standard;
    }

}

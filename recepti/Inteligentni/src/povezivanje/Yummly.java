package povezivanje;

import domen.NutritivneInformacije;
import domen.Recept;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
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
public class Yummly {
    
    public List<Recept> vratiRecepte(String parametriPretrage) throws MalformedURLException, IOException, org.json.simple.parser.ParseException {
        String url = "http://api.yummly.com/v1/api/recipes?_app_id=85fa428a&_app_key=b120c80efb746ba15e098e03d829a7cc&q="+parametriPretrage;
        
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

    protected Recept procitajRecept(String id) throws MalformedURLException, IOException, org.json.simple.parser.ParseException {
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

    protected Recept popuniReceptZajednickimPodacima(String podatakZaCitanje) throws org.json.simple.parser.ParseException {
        JSONParser parser = new JSONParser();
        Object parse = parser.parse(podatakZaCitanje);

        JSONObject jsonObject = (JSONObject) parse;
        
        Recept recept = new Recept();
        NutritivneInformacije nutritivneInformacije = new NutritivneInformacije();
        recept.dodajNutritivneInformacije(nutritivneInformacije);

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
                    nutritivneInformacije.setBrojKalorija(kalorije);
                    System.out.println(kalorije);
                }
                if(podatak.equals("FAT")){ 
                    System.out.println(podatak);
                    Double masti = (Double) objekat.get("value");
                    nutritivneInformacije.setKolicinaMasti(masti);
                    System.out.println(masti);
                }
                if(podatak.equals("PROCNT")){ 
                    System.out.println(podatak);
                    Double proteini = (Double) objekat.get("value");
                    nutritivneInformacije.setKolicinaProteina(proteini);
                    System.out.println(proteini);
                }
                if(podatak.equals("CHOCDF")){ 
                    System.out.println(podatak);
                    Double ugljeniHidrati = (Double) objekat.get("value");
                    nutritivneInformacije.setKolicinaUgljenihHidrata(ugljeniHidrati);
                    System.out.println(ugljeniHidrati);
                }
                if(podatak.equals("FIBTG")){ 
                    System.out.println(podatak);
                    Double vlakna = (Double) objekat.get("value");
                    nutritivneInformacije.setKolicinaVlakana(vlakna);
                    System.out.println(vlakna);
                }
                brojac++;

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        
        if(jsonObject.get("images")!=null){
        JSONArray images = (JSONArray) jsonObject.get("images");
        JSONObject slike =  (JSONObject) images.get(0);
        String slika = (String) slike.get("hostedLargeUrl");
        recept.setSlika(slika);
        System.out.println("Link do slike: " + slika);
        }
        
        if(jsonObject.get("name")!=null){
        String naziv = (String) jsonObject.get("name");
        recept.setNaziv(naziv);
        System.out.println("Naziv recepta: "+naziv);
        }
        
        if(jsonObject.get("numberOfServings")!=null){
        Long brojOsoba = (Long) jsonObject.get("numberOfServings");
        recept.setBrojOsoba(brojOsoba.toString()); 
        System.out.println("Za: "+brojOsoba+ " osoba");
        }
        
        if(jsonObject.get("totalTimeInSeconds")!=null){
        Long vremePripremeUSekundama = (Long) jsonObject.get("totalTimeInSeconds");
        System.out.println("*************");
            System.out.println(vremePripremeUSekundama);
        System.out.println("*************");
        recept.setVremePripreme(vremePripremeUSekundama.toString()); //SREDITI VREME PREMA STANDARDU
        SimpleDateFormat d = new SimpleDateFormat("X");
        System.out.println("Vreme pripreme u sekundama: "+vremePripremeUSekundama); //moze se desiti i da je null
        }
        
        if(jsonObject.get("aggregateRating")!=null){
        JSONArray aggregateRating = (JSONArray) jsonObject.get("aggregateRating");
        JSONObject rating =  (JSONObject) aggregateRating.get(0);
        Long rejting = (Long) rating.get("ratingValue");
        recept.setRejting(rejting);
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

}

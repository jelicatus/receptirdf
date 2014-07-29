/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kontroler;

import domen.Recept;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import persistance.RDFModel;
import proba.PovezivanjeSaYummlyjem;

/**
 *
 * @author TFamilija
 */
public class Kontroler {
    
     private static void vratiReceptYummly(String idRecepta) throws Exception {

        String url = "http://api.yummly.com/v1/api/recipe/" + idRecepta + "?_app_id=85fa428a&_app_key=b120c80efb746ba15e098e03d829a7cc";

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
       procitajZajednickePodatkeYummly(response.toString());

    }

    public static void pretraziRecepteYummly() throws Exception {

        String url = "http://api.yummly.com/v1/api/recipes?_app_id=85fa428a&_app_key=b120c80efb746ba15e098e03d829a7cc&q=chicken+soup";

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
        String json = response.toString();

        JSONParser parser = new JSONParser();
        Object parse = parser.parse(json);

        JSONObject jsonObject = (JSONObject) parse;

        String name = (String) jsonObject.get("name");
        System.out.println(name);

        // loop array
        JSONArray msg = (JSONArray) jsonObject.get("matches");

        int brojac = 0;

        Iterator<String> iterator = msg.iterator();
        while (iterator.hasNext() && brojac!=msg.size()) {
            try {
                JSONObject objekat = (JSONObject) msg.get(brojac);

                String id = (String) objekat.get("id");
                System.out.println(id);
                vratiReceptYummly(id);
                brojac++;

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    private static void procitajZajednickePodatkeYummly(String json) throws org.json.simple.parser.ParseException {
        Recept recept = new Recept();
        JSONParser parser = new JSONParser();
        Object parse = parser.parse(json);

        JSONObject jsonObject = (JSONObject) parse;

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
                    System.out.println(kalorije);
                    recept.setBrojKalorija(kalorije);
                }
                if(podatak.equals("FAT")){ 
                    System.out.println(podatak);
                    Double masti = (Double) objekat.get("value");
                    System.out.println(masti);
                    recept.setKolicinaMasti(masti);
                }
                if(podatak.equals("PROCNT")){ 
                    System.out.println(podatak);
                    Double proteini = (Double) objekat.get("value");
                    System.out.println(proteini);
                    recept.setKolicinaProteina(proteini);
                }
                if(podatak.equals("CHOCDF")){ 
                    System.out.println(podatak);
                    Double ugljeniHidrati = (Double) objekat.get("value");
                    System.out.println(ugljeniHidrati);
                    recept.setKolicinaUgljenihHidrata(ugljeniHidrati);
                }
                if(podatak.equals("FIBTG")){ 
                    System.out.println(podatak);
                    Double vlakna = (Double) objekat.get("value");
                    System.out.println(vlakna);
                    recept.setKolicinaVlakana(vlakna);
                }
                brojac++;

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        
        JSONArray images = (JSONArray) jsonObject.get("images");
        JSONObject slike =  (JSONObject) images.get(0);
        String slika = (String) slike.get("hostedLargeUrl");
        System.out.println("Link do slike: " + slika);
        recept.setSlika(slika);
        
        String naziv = (String) jsonObject.get("name");
        System.out.println("Naziv recepta: "+naziv);
        recept.setNaziv(naziv);
        
        Long brojOsoba = (Long) jsonObject.get("numberOfServings");
        System.out.println("Za: "+brojOsoba+ " osoba");
        recept.setBrojOsoba(brojOsoba+" osoba");
        
        Long vremePripremeUSekundama = (Long) jsonObject.get("totalTimeInSeconds");
        System.out.println("Vreme pripreme u sekundama: "+vremePripremeUSekundama); //moze se desiti i da je null
        //VIDETI STA ZA VREME PRIPREME
        
        Long rejting = (Long) jsonObject.get("rating");
        System.out.println("Rejting: "+rejting);
        recept.setRejting(rejting);
        
        JSONArray sastojci = (JSONArray) jsonObject.get("ingredientLines");
        int brojac1 = 0;

        Iterator<String> iterator1 = sastojci.iterator();
        String sastojciString = "";
        while (iterator.hasNext() && brojac1!=sastojci.size()) {
           String clanNiza = (String) sastojci.get(brojac1);
           System.out.println(clanNiza);
           sastojciString+=clanNiza+" ";
           brojac1++;
        }
        recept.setSastojciString(sastojciString);
        RDFModel.getInstance().save(recept);

    }

    public static void main(String[] args) throws Exception {

      
        Kontroler.pretraziRecepteYummly();

    }
    
}

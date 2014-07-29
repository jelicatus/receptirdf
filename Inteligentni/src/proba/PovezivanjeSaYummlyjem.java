package proba;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.Iterator;
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
public class PovezivanjeSaYummlyjem {

    private void sendGet(String idRecepta) throws Exception {

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

    private void sendGet2() throws Exception {

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
                sendGet(id);
                brojac++;

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public void procitajZajednickePodatkeYummly(String json) throws org.json.simple.parser.ParseException {

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
                }
                if(podatak.equals("FAT")){ 
                    System.out.println(podatak);
                    Double masti = (Double) objekat.get("value");
                    System.out.println(masti);
                }
                if(podatak.equals("PROCNT")){ 
                    System.out.println(podatak);
                    Double proteini = (Double) objekat.get("value");
                    System.out.println(proteini);
                }
                if(podatak.equals("CHOCDF")){ 
                    System.out.println(podatak);
                    Double ugljeniHidrati = (Double) objekat.get("value");
                    System.out.println(ugljeniHidrati);
                }
                if(podatak.equals("FIBTG")){ 
                    System.out.println(podatak);
                    Double vlakna = (Double) objekat.get("value");
                    System.out.println(vlakna);
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
        
        String naziv = (String) jsonObject.get("name");
        System.out.println("Naziv recepta: "+naziv);
        
        Long brojOsoba = (Long) jsonObject.get("numberOfServings");
        System.out.println("Za: "+brojOsoba+ " osoba");
        
        Long vremePripremeUSekundama = (Long) jsonObject.get("totalTimeInSeconds");
        System.out.println("Vreme pripreme u sekundama: "+vremePripremeUSekundama); //moze se desiti i da je null
        
        Long rejting = (Long) jsonObject.get("rating");
        System.out.println("Rejting: "+rejting);
        
        JSONArray sastojci = (JSONArray) jsonObject.get("ingredientLines");
        int brojac1 = 0;

        Iterator<String> iterator1 = sastojci.iterator();
        while (iterator.hasNext() && brojac1!=sastojci.size()) {
           String clanNiza = (String) sastojci.get(brojac1);
           System.out.println(clanNiza);
           brojac1++;
        }
        
    /*    JSONObject objekat=(JSONObject) nutritionEstimates.get(0);
        Double kalorije = (Double) objekat.get("value"); 
        String provera1 =(String) objekat.get("description");  //description
        System.out.println(provera1);
        System.out.println("Kalorije:"+kalorije);
        
        JSONObject objekat1=(JSONObject) nutritionEstimates.get(1);
        Double masti = (Double) objekat1.get("value"); 
        String provera2 =(String) objekat1.get("description");  //description
        System.out.println(provera2);
        System.out.println("Masti:"+masti);
        
        JSONObject objekat2=(JSONObject) nutritionEstimates.get(6);
        Double ugljeniHidrati = (Double) objekat2.get("value"); 
        String provera =(String) objekat2.get("description");  //description
        System.out.println(provera);
        System.out.println("Ugljeni hidrati:"+masti);*/

    }

    public static void main(String[] args) throws Exception {

        PovezivanjeSaYummlyjem http = new PovezivanjeSaYummlyjem();

        System.out.println("Testing 1 - Send Http GET request");
        http.sendGet2();

    }

}

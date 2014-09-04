/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domen;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import thewebsemantic.Namespace;
import thewebsemantic.RdfProperty;
import thewebsemantic.RdfType;
import util.Constants;

/**
 *
 * @author TFamilija
 */
@Namespace(Constants.SCHEMA)
@RdfType("Recipe")
public class Recept extends Thing{
    private int receptId;
    @RdfProperty(Constants.SCHEMA + "name")
    private String naziv;
    
    /* @RdfProperty(Constants.SCHEMA + "calories")
    private double brojKalorija;
    @RdfProperty(Constants.SCHEMA + "carbohydrateContent")
    private double kolicinaUgljenihHidrata;
    @RdfProperty(Constants.SCHEMA + "fatContent")
    private double kolicinaMasti;
    @RdfProperty(Constants.SCHEMA + "proteinContent")
    private double kolicinaProteina;
    @RdfProperty(Constants.SCHEMA + "fiberContent")
    private double kolicinaVlakana; */
    
    @RdfProperty(Constants.SCHEMA + "nutrition")
    private Collection<NutritivneInformacije> nutritivneInformacije;    
    
    @RdfProperty(Constants.SCHEMA + "totalTime")
    private String vremePripreme;
    @RdfProperty(Constants.NS + "aggregateRating")
    private long rejting;
    @RdfProperty(Constants.SCHEMA+ "image")
    private String slika;
    
    @RdfProperty(Constants.NS+ "recipeYield")
    private String brojOsoba;
    
    
    @RdfProperty(Constants.SCHEMA + "ingridients")
    private List<String> sastojciString;
   

    public Recept() {
      nutritivneInformacije = new ArrayList<NutritivneInformacije>();
    }


    public void dodajNutritivneInformacije(NutritivneInformacije nu){
        nutritivneInformacije.add(nu);
    }
    

    public String getBrojOsoba() {
        return brojOsoba;
    }

    public void setBrojOsoba(String brojOsoba) {
        this.brojOsoba = brojOsoba;
    }

    public String getVremePripreme() {
        return vremePripreme;
    }

    public void setVremePripreme(String vremePripreme) {
        this.vremePripreme = vremePripreme;
    }
    
    

   

    public List<String> getSastojciString() {
        return sastojciString;
    }

    public void setSastojciString(List<String> sastojciString) {
        this.sastojciString = sastojciString;
    }

    public int getReceptId() {
        return receptId;
    }

    public void setReceptId(int receptId) {
        this.receptId = receptId;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    
 

    public long getRejting() {
        return rejting;
    }

    public void setRejting(long rejting) {
        this.rejting = rejting;
    }

   

    public String getSlika() {
        return slika;
    }

    public void setSlika(String slika) {
        this.slika = slika;
    }

 
    
    
    
    
    
}

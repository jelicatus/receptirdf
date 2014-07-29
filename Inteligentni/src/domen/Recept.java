/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domen;

import java.util.ArrayList;
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
public class Recept {
    private int receptId;
    @RdfProperty(Constants.SCHEMA + "name")
    private String naziv;
    
    
    @RdfProperty(Constants.SCHEMA + "calories")
    private double brojKalorija;
    @RdfProperty(Constants.SCHEMA + "carbohydrateContent")
    private double kolicinaUgljenihHidrata;
    @RdfProperty(Constants.SCHEMA + "fatContent")
    private double kolicinaMasti;
    @RdfProperty(Constants.SCHEMA + "proteinContent")
    private double kolicinaProteina;
    @RdfProperty(Constants.SCHEMA + "fiberContent")
    private double kolicinaVlakana; 
    
    
    
    @RdfProperty(Constants.SCHEMA + "totalTime")
    private long vremePripreme;
    @RdfProperty(Constants.NS + "rating")
    private long rejting;
    @RdfProperty(Constants.SCHEMA+ "image")
    private String slika;
    
    @RdfProperty(Constants.NS+ "recipeYield")
    private String brojOsoba;
    
    
    @RdfProperty(Constants.SCHEMA + "ingridients")
    private String sastojciString;
   

    public Recept() {
        
    }

    public Recept(int receptId, String naziv, double brojKalorija, double kolicinaUgljenihHidrata, double kolicinaMasti, double kolicinaProteina, double kolicinaVlakana, long vremePripreme, long rejting, String slika, String brojOsoba, String sastojciString) {
        this.receptId = receptId;
        this.naziv = naziv;
        this.brojKalorija = brojKalorija;
        this.kolicinaUgljenihHidrata = kolicinaUgljenihHidrata;
        this.kolicinaMasti = kolicinaMasti;
        this.kolicinaProteina = kolicinaProteina;
        this.kolicinaVlakana = kolicinaVlakana;
        this.vremePripreme = vremePripreme;
        this.rejting = rejting;
        this.slika = slika;
        this.brojOsoba = brojOsoba;
        this.sastojciString = sastojciString;
        
    }


    

    public String getBrojOsoba() {
        return brojOsoba;
    }

    public void setBrojOsoba(String brojOsoba) {
        this.brojOsoba = brojOsoba;
    }

    public long getVremePripreme() {
        return vremePripreme;
    }

    public void setVremePripreme(int vremePripreme) {
        this.vremePripreme = vremePripreme;
    }
    
    

   

    public String getSastojciString() {
        return sastojciString;
    }

    public void setSastojciString(String sastojciString) {
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

    public double getBrojKalorija() {
        return brojKalorija;
    }

    public void setBrojKalorija(double brojKalorija) {
        this.brojKalorija = brojKalorija;
    }

    public double getKolicinaUgljenihHidrata() {
        return kolicinaUgljenihHidrata;
    }

    public void setKolicinaUgljenihHidrata(double kolicinaUgljenihHidrata) {
        this.kolicinaUgljenihHidrata = kolicinaUgljenihHidrata;
    }

    public double getKolicinaMasti() {
        return kolicinaMasti;
    }

    public void setKolicinaMasti(double kolicinaMasti) {
        this.kolicinaMasti = kolicinaMasti;
    }

    public double getKolicinaProteina() {
        return kolicinaProteina;
    }

    public void setKolicinaProteina(double kolicinaProteina) {
        this.kolicinaProteina = kolicinaProteina;
    }

    public double getKolicinaVlakana() {
        return kolicinaVlakana;
    }

    public void setKolicinaVlakana(double kolicinaVlakana) {
        this.kolicinaVlakana = kolicinaVlakana;
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

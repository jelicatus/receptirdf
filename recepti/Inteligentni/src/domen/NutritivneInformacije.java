/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;

import java.io.Serializable;
import thewebsemantic.Namespace;
import thewebsemantic.RdfProperty;
import thewebsemantic.RdfType;
import util.Constants;

/**
 *
 * @author M&J
 */
@Namespace(Constants.SCHEMA)
@RdfType("NutritionInformation")
public class NutritivneInformacije extends Thing implements Serializable{
    
    @RdfProperty(Constants.SCHEMA + "calories")
    private int brojKalorija;
    @RdfProperty(Constants.SCHEMA + "carbohydrateContent")
    private double kolicinaUgljenihHidrata;
    @RdfProperty(Constants.SCHEMA + "fatContent")
    private double kolicinaMasti;
    @RdfProperty(Constants.SCHEMA + "proteinContent")
    private double kolicinaProteina;
    @RdfProperty(Constants.SCHEMA + "fiberContent")
    private double kolicinaVlakana;

    public NutritivneInformacije() {
    }

    public int getBrojKalorija() {
        return brojKalorija;
    }

    public void setBrojKalorija(int brojKalorija) {
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

    

    
    
    
    
    
    
}

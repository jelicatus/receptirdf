/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;

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
public class NutritivneInformacije extends Thing {
    
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

    public NutritivneInformacije() {
    }

    public NutritivneInformacije(double brojKalorija, double kolicinaUgljenihHidrata, double kolicinaMasti, double kolicinaProteina, double kolicinaVlakana) {
        this.brojKalorija = brojKalorija;
        this.kolicinaUgljenihHidrata = kolicinaUgljenihHidrata;
        this.kolicinaMasti = kolicinaMasti;
        this.kolicinaProteina = kolicinaProteina;
        this.kolicinaVlakana = kolicinaVlakana;
    }

    public double getBrojKalorija() {
        return brojKalorija;
    }

    public void setBrojKalorija(double brojKalorija) {
        this.brojKalorija = brojKalorija;
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

    public double getKolicinaUgljenihHidrata() {
        return kolicinaUgljenihHidrata;
    }

    public void setKolicinaUgljenihHidrata(double kolicinaUgljenihHidrata) {
        this.kolicinaUgljenihHidrata = kolicinaUgljenihHidrata;
    }

    public double getKolicinaVlakana() {
        return kolicinaVlakana;
    }

    public void setKolicinaVlakana(double kolicinaVlakana) {
        this.kolicinaVlakana = kolicinaVlakana;
    }
    
    
    
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domen;

import java.io.Serializable;
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
public class Recept extends Thing implements Serializable{
    private String tipRecepta; //postaviti id-jeve
    @RdfProperty(Constants.SCHEMA + "name")
    private String naziv;
      
    @RdfProperty(Constants.SCHEMA + "nutrition")
    private NutritivneInformacije nutritivneInformacije;    
    
    @RdfProperty(Constants.SCHEMA + "totalTime")
    private String vremePripreme;
    
    /*@RdfProperty(Constants.SCHEMA + "aggregateRating")
    private long rejting;*/
    
    @RdfProperty(Constants.SCHEMA + "aggregateRating")
    private AgregatniRejting rejting;    
   
    
    @RdfProperty(Constants.SCHEMA+ "recipeYield")
    private String brojPorcija;
  
    
    @RdfProperty(Constants.SCHEMA + "ingredients")
    private Collection<String> sastojci;    


    public Recept() {
        sastojci = new ArrayList<String>();
    }   
    

    public NutritivneInformacije getNutritivneInformacije() {
        return nutritivneInformacije;
    }

    public void setNutritivneInformacije(NutritivneInformacije nutritivneInformacije) {
        this.nutritivneInformacije = nutritivneInformacije;
    }

    public String getBrojPorcija() {
        return brojPorcija;
    }

    public void setBrojPorcija(String brojPorcija) {
        this.brojPorcija = brojPorcija;
    }
       
   

    public String getVremePripreme() {
        return vremePripreme;
    }

    public void setVremePripreme(String vremePripreme) {
        this.vremePripreme = vremePripreme;
    }
    
    

   

    public Collection<String> getSastojciString() {
        return sastojci;
    }

    public void setSastojciString(Collection<String> sastojciString) {
        this.sastojci = sastojciString;
    }

    public String getTipRecepta() {
        return tipRecepta;
    }

    public void setTipRecepta(String tipRecepta) {
        this.tipRecepta = tipRecepta;
    }

    

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public AgregatniRejting getRejting() {
        return rejting;
    }

    public void setRejting(AgregatniRejting rejting) {
        this.rejting = rejting;
    }  
 

    
    
    
}

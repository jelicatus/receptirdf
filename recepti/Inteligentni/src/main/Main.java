/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import com.hp.hpl.jena.rdf.model.Model;
import domen.Recept;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistance.RDFModel;
import povezivanje.AllRecipe;
import povezivanje.Yummly;
import util.URIGenerator;

/**
 *
 * @author M&J
 */
public class Main {
    
    public static void main(String[] args) {
        AllRecipe povezivanjeAllRecipe = new AllRecipe();
        Yummly povezivanjeYummly = new Yummly();
        try {
            List<Recept> recepti = povezivanjeYummly.vratiRecepte("chicken");
            for (Recept recept : recepti) {
                if(recept == null) System.out.println("AAAAA");
                recept.setUri(URIGenerator.generate(recept));
                RDFModel.getInstance().save(recept);                
            }
            
            List<Model> modeli = povezivanjeAllRecipe.vratiRecepte("chicken");
            for (Model model : modeli) {
                RDFModel.getInstance().add(model);
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
@RdfType("AggregateRating")
public class AgregatniRejting extends Thing implements Serializable{
    
    @RdfProperty(Constants.SCHEMA + "ratingValue")
    private long rejting; 

    public long getRejting() {
        return rejting;
    }

    public void setRejting(long rejting) {
        this.rejting = rejting;
    }
    
    
    
}

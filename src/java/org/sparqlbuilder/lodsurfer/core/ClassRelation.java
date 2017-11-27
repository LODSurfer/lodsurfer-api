/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sparqlbuilder.lodsurfer.core;

/**
 *
 * @author atsuko
 */
public class ClassRelation {
    String propertyURI = ""; // url
    String endpointURI = ""; // url
    int dsn;
    int don;
    int trn;
    
    @Override
    public boolean equals(Object ob){
        if ( this == ob ){ return true; }
        if ( ob instanceof ClassRelation){
            ClassRelation cr = (ClassRelation) ob;
            if ( propertyURI.equals(cr.propertyURI) && endpointURI.equals(cr.endpointURI) ){
                return true;
            }
        }
        return false;

    }
}

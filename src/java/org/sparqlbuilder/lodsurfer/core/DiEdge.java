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
public class DiEdge {    
    boolean direction; // true: forward, false: backward
    ClassRelation cr;    
    
    public DiEdge(ClassRelation cr, boolean direction){
        //this.node = node;
        this.cr = cr;
        this.direction = direction;
    }    
}

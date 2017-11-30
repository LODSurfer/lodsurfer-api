/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sparqlbuilder.lodsurfer.core;

import java.util.*;

/**
 *
 * @author atsuko
 */

public class ClassPath {
    List<Integer> classes;
    List<DiEdge> properties;
    
    public ClassPath(){
        //classes = new LinkedList<Integer>();
        properties = new LinkedList<>(); 
    } 
}

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
public class LSCtrl {
    
    ClassGraph cg = null;
    Map<String, ClassInfo> cl = null;  // classURI->classInfo
    Map<String, TreeSet<String>> clrel = null; // class->(step,classes)
    Set<String> eps = null;
    //Set<String> yum = null; // yummy ep list
    //Set<String> avep = null;
    
    String clfile = "cc/cl.txt";
    String epfile = "cc/ep.txt";
    String crelfile = "cc/crel.txt";
    String cldir = "cc/";
    
    public LSCtrl(){
        // cl.txt -> cl
        eps = LSIO.readEP(epfile);
        cl = LSIO.readCL(clfile);
        clrel = LSIO.readCR(crelfile);

        cg = LSIO.readCLDir(cldir, eps);
        // clrel.txt -> crel        
    }
    
    
    
}

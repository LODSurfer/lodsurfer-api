/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sparqlbuilder.lodsurfer.core;

import java.math.BigDecimal;
import java.util.*;
import javax.json.*;

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
    
    public JsonArray getEPs(){
        JsonBuilderFactory jbfactory = Json.createBuilderFactory(null);
        JsonArrayBuilder jab = jbfactory.createArrayBuilder();

        Iterator<String> eit = eps.iterator();
        while (eit.hasNext()){
            jab.add(eit.next());
        }
        JsonArray ja = jab.build();
        return ja;
    }
    
    public JsonArray getAllClasses(){
        JsonBuilderFactory jbfactory = Json.createBuilderFactory(null);
        JsonArrayBuilder jab = jbfactory.createArrayBuilder();
            Iterator<String> cit = cl.keySet().iterator();
            JsonObjectBuilder job = jbfactory.createObjectBuilder();
            while( cit.hasNext() ){
                String classuri = cit.next();
                ClassInfo classinfo = cl.get(classuri);
                ListIterator<String> eit = classinfo.endpoints.listIterator();
                while ( eit.hasNext() ){
                    String ep = eit.next();
                    job.add("ep", ep);
                    job.add("uri", classuri);
                    //job.add("label", classinfo.prlabel);
                    job.add("label", classinfo.labels.get(0));
                    job.add("number", classinfo.instances4e.get(ep));
                    jab.add(job);
                }
            }
        JsonArray ja = jab.build();
        return ja;
        
    }
    
    public JsonArray getCLs(String class1){
        JsonBuilderFactory jbfactory = Json.createBuilderFactory(null);
        JsonArrayBuilder jab = jbfactory.createArrayBuilder();
        if (class1 == null){
            return null;
        }else{
            //
            TreeSet<String> classes = clrel.get(class1);
            Iterator<String> cit = classes.descendingIterator();
            JsonObjectBuilder job = jbfactory.createObjectBuilder();
            while( cit.hasNext() ){
                String nclassuri = cit.next();
                String classuri = nclassuri.split(" ")[1];
                ClassInfo classinfo = cl.get(classuri);
                ListIterator<String> eit = classinfo.endpoints.listIterator();
                while ( eit.hasNext() ){
                    String ep = eit.next();
                    job.add("ep", ep);
                    job.add("uri", classuri);
                    job.add("label", classinfo.labels.get(0));
                    job.add("number", classinfo.instances4e.get(ep));
                    jab.add(job);
                }
            }
        }       
        JsonArray ja = jab.build();
        return ja;
    }

    public JsonArray getPaths(String class1, String class2){
        JsonBuilderFactory jbfactory = Json.createBuilderFactory(null);
        JsonArrayBuilder jab = jbfactory.createArrayBuilder();
        
        TreeSet<String> cl = clrel.get(class1);
        Iterator<String> cit = cl.iterator();
        int minstep = 0;
        while ( cit.hasNext() ){
            String[] cdata = cit.next().split(" ");
            if ( cdata[1].equals(class2)){
                minstep = Integer.parseInt(cdata[0]);
            }
        }
        
        List<ClassPath> paths = cg.getPaths(class1, class2, minstep);
        ListIterator<ClassPath> pit = paths.listIterator();
        while ( pit.hasNext() ){
            jab.add(toJsonFromPath(pit.next()));
        }        
        JsonArray ja = jab.build();
        return ja;
    }
    
    public JsonObject toJsonFromPath(ClassPath cp){
        JsonBuilderFactory jbfactory = Json.createBuilderFactory(null);
        JsonObjectBuilder job = jbfactory.createObjectBuilder();
        JsonArrayBuilder jab4c = jbfactory.createArrayBuilder();
        ListIterator<Integer> clit = cp.classes.listIterator();
        while ( clit.hasNext()){
            int cn = clit.next();
            jab4c.add(cg.classURIs.get(cn));
        }
        job.add("classes", jab4c);

        JsonArrayBuilder jab4p = jbfactory.createArrayBuilder();
        ListIterator<DiEdge> prit = cp.properties.listIterator();
        while ( prit.hasNext() ){
            DiEdge edge = prit.next();
            jab4p.add(toJsonFromDiEdge(edge));
        }
        
        JsonObject jo = job.build();
        return jo;
    }
    
    public JsonObject toJsonFromDiEdge(DiEdge edge){
        JsonBuilderFactory jbfactory = Json.createBuilderFactory(null);
        JsonObjectBuilder job = jbfactory.createObjectBuilder();

        job.add("ep", edge.cr.endpointURI);
        job.add("property", edge.cr.propertyURI);
        job.add("direction", edge.direction);
        job.add("dsn", edge.cr.dsn);
        job.add("don", edge.cr.don);
        job.add("trn", edge.cr.trn);
        
        JsonObject jo = job.build();
        return jo;        
    }    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sparqlbuilder.lodsurfer.core;

import java.util.*;
import javax.json.*;

/**
 *
 * @author atsuko
 */
public class LSCtrl {
    
    List<String> eps = null;
    ClassGraph cg = null;
    List<Boolean> singleton = null;
    
    Map<String, ClassInfo> cl = null;  // classURI->classInfo
    List<Set<Integer>> cc = null; // cc
    //Set<String> yum = null; // yummy ep list
    
    //Map<String, Set<SPathInfo>> clrel = null; // class->(step,classes)

    
    String epfile = "md/ep.txt"; // ep URLs
    String clfile = "md/cl.txt"; // cl uri \tab cl label \tab ent
    //String plfile = "md/pl.txt"; // prop
    String crfile = "md/cr.txt"; // cl1 url \tab prop url \tab cl2url \tab epurl
    String ccfile = "md/cc.txt"; // number \tab id1,id2,... (for each cc) 
    //String cldir = "cc/";
    
    public LSCtrl(){
        cg = new ClassGraph();
        singleton = new ArrayList<>(); // true = singleton        
        // cl.txt -> cl
        eps = LSIO.readEP(epfile);
        List<String> cls = LSIO.readCL(clfile);       
        parseCL(cls);
        
        LSIO.readCR(crfile, cg);
        cc = LSIO.readCC(ccfile, singleton);
    }
    
/*
    public class SPathInfo{
        String class2;
        int minstep;
        public SPathInfo(String cl, int sp){
            class2 = cl;
            minstep = sp;
        }
    }
*/    
    
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
    
    public JsonArray getAllClasses(Boolean isolated){
        JsonBuilderFactory jbfactory = Json.createBuilderFactory(null);
        JsonArrayBuilder jab = jbfactory.createArrayBuilder();
        Iterator<String> cit = cg.classURIs.iterator();
        Iterator<Boolean> bit = singleton.iterator();
        JsonObjectBuilder job = jbfactory.createObjectBuilder();
        while( cit.hasNext() ){
            String classuri = cit.next();
            if ( isolated != null ){
                boolean b = bit.next();
                if ( isolated.booleanValue() == true && b == false){ 
                    continue;
                }
                if ( isolated.booleanValue() == false && b == true ){
                    continue;
                }
            }
            ClassInfo classinfo = cl.get(classuri);
            job.add("uri", classuri);
            job.add("label", classinfo.prlabel);
            job.add("instances", classinfo.instances);
            jab.add(job);
        }
        JsonArray ja = jab.build();
        return ja;        
    }
    
    public JsonArray getCLs(String class1){
        JsonBuilderFactory jbfactory = Json.createBuilderFactory(null);
        JsonArrayBuilder jab = jbfactory.createArrayBuilder();
        if (class1 == null){
            return getAllClasses(new Boolean(false));
        }else{
            Integer cid = cg.uRI2node.get(class1);
            if ( cid != null ){
                if ( singleton.get(cid) ){
                    JsonArray ja = jab.build();
                    return ja;                    
                } 
                Set<Integer> compo = getCompo(cid);
                if ( compo != null ){
                    Iterator<Integer> coit = compo.iterator();
                    JsonObjectBuilder job = jbfactory.createObjectBuilder();
                    while ( coit.hasNext() ){
                        int clid = coit.next();
                        String cluri = cg.classURIs.get(clid);
                        ClassInfo classinfo = cl.get(cluri);
                        job.add("uri", cluri);
                        job.add("label", classinfo.prlabel);
                        job.add("instances", classinfo.instances);
                        jab.add(job);
                    }
                }
            }
        }
        JsonArray ja = jab.build();
        return ja;
    }

    public JsonArray getPaths(String class1, String class2){
        JsonBuilderFactory jbfactory = Json.createBuilderFactory(null);
        JsonArrayBuilder jab = jbfactory.createArrayBuilder();
        
        List<ClassPath> paths = cg.getPaths(class1, class2);        
        
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
        job.add("relations", jab4p);
        
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
    
    public String getSPARQL(String path, String instances){
        //if (path == null){ return "";}
        //if (path.length() == 0){ return "";}
        JsonObject jo = LSIO.parseJsonObject(path);
        
        JsonArray cl = jo.getJsonArray("classes");
        List<String> curl = new LinkedList<>();
        Iterator<JsonValue> jit = cl.listIterator();
        while ( jit.hasNext() ){
            String c = ((JsonString) jit.next()).getString();
            curl.add(c);
        } 
        
        JsonArray rel = jo.getJsonArray("relations");
        List<DiEdge> crl = new LinkedList<>();
        jit = rel.listIterator();
        while ( jit.hasNext() ){
            JsonObject jcr = (JsonObject) jit.next();            
            ClassRelation cr = new ClassRelation(jcr.getString("property"), jcr.getString("ep"),
                                                 100, 100, 100);
            DiEdge de = new DiEdge(cr, jcr.getBoolean("direction"));
            crl.add(de);
        }
        
        List<String> ins = null;
        if ( instances != null ){
            JsonArray ja = LSIO.parseJsonArray(instances);
            // kongo
        }
        //JsonBuilderFactory jbfactory = Json.createBuilderFactory(null);
        //JsonArrayBuilder jab = jbfactory.createArrayBuilder();
        //jab.add(LSQuery.getSPARQL(curl, crl, ins));
        //return jab.build();
        return LSQuery.getSPARQL(curl, crl, ins);
    }
    
    public String getResult(String path, String instances){
        JsonObject jo = LSIO.parseJsonObject(path);
        
        JsonArray cl = jo.getJsonArray("classes");
        List<String> curl = new LinkedList<>();
        Iterator<JsonValue> jit = cl.listIterator();
        while ( jit.hasNext() ){
            String c = ((JsonString) jit.next()).getString();
            curl.add(c);
        } 
        
        JsonArray rel = jo.getJsonArray("relations");
        List<DiEdge> crl = new LinkedList<>();
        jit = rel.listIterator();
        while ( jit.hasNext() ){
            JsonObject jcr = (JsonObject) jit.next();            
            ClassRelation cr = new ClassRelation(jcr.getString("property"), jcr.getString("ep"),
                                                 100, 100, 100);
            DiEdge de = new DiEdge(cr, jcr.getBoolean("direction"));
            crl.add(de);
        }
        //String sparql = getSPARQL(path, instances);
        // koko TODO
        return "";
    }
    
/*
    private Map<String, Set<SPathInfo>> convert2SPath(Map<String, Set<String>> reltmp){
        Map<String, Set<SPathInfo>> sp = new HashMap<>();
        Iterator<String> cit = reltmp.keySet().iterator();
        while ( cit.hasNext()){
            sp.put(cit.next(), new HashSet<SPathInfo>());
        }
        
        cit = reltmp.keySet().iterator();
        while ( cit.hasNext()){         
            String cl1 = cit.next();
            Iterator<String> sit = reltmp.get(cl1).iterator();
            while ( sit.hasNext()){
                String[] scl = sit.next().split(" ");
                sp.get(cl1).add(new SPathInfo(scl[1], Integer.parseInt(scl[0])));
                sp.get(scl[1]).add(new SPathInfo(cl1, Integer.parseInt(scl[0])));                
            }
        }        
        return sp;
    }
*/
    
    private void parseCL(List<String> cls){
        cl = new HashMap<>();
        ListIterator<String> cit = cls.listIterator();
        int count = 0;
        while ( cit.hasNext() ){
            String ci = cit.next();
            String data[] = ci.split("\t");
            cg.addNode(data[0]);
            singleton.add(new Boolean(true));
            cl.put(data[0], new ClassInfo(data[0], data[1], Integer.parseInt(data[2])));
            count ++;
        }
    }
    
    private Set<Integer> getCompo(Integer cid){
        Iterator<Set<Integer>> cit = cc.iterator();
        while(cit.hasNext()){
            Set<Integer> compo = cit.next();
            if ( compo.contains(cid)){
                return compo;
            }
        }
        return null; //new HashSet<Integer>();
    }
}

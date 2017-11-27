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

public class ClassGraph {
    List<String> classURIs;
    Map<String,Integer> uRI2node;
    List<Set<Integer>> adjlist;
    List<Map<Integer,Set<DiEdge>>> properties; // n1 - (n2 - labels)
    
    public ClassGraph(){
        classURIs = new ArrayList<>();
        uRI2node = new HashMap<>();
        adjlist = new ArrayList<>();
        properties = new ArrayList<>();
    }
    
    public void addNode(String classURI){
        if (uRI2node.containsKey(classURI)){ return; }
        uRI2node.put(classURI, classURIs.size());
        classURIs.add(classURI);
        adjlist.add(new HashSet<Integer>());
        properties.add(new HashMap<Integer, Set<DiEdge>>());
    }
    
    public void addEdge(String classURI1, String classURI2, String property, String endpointURI,
            int dsn, int don, int trn){
        Integer node1 = uRI2node.get(classURI1);
        Integer node2 = uRI2node.get(classURI2);
        addEdge(node1, node2, property, endpointURI, dsn, don, trn);
    }
    
    public void addEdge(Integer node1, Integer node2, String property, String endpointURI,
            int dsn, int don, int trn){
        adjlist.get(node1).add(node2);
        adjlist.get(node2).add(node1);
        ClassRelation cr = new ClassRelation();
        cr.endpointURI = endpointURI;
        cr.propertyURI = property;
        cr.dsn = dsn;
        cr.don = don;
        cr.trn = trn;
        
        if (properties.get(node1).get(node2) == null){ properties.get(node1).put(node2, new HashSet<DiEdge>());}
        properties.get(node1).get(node2).add(new DiEdge(cr, true));
        if (properties.get(node2).get(node1) == null){ properties.get(node2).put(node1, new HashSet<DiEdge>());}
        properties.get(node2).get(node1).add(new DiEdge(cr, false));
    }
    
    public List<ClassPath> getPaths(String class1, String class2, int nsteps){
        List<ClassPath> cps = new LinkedList<>();
        
        //checkedpaths = new HashMap<String, Boolean>();
        Integer snode = uRI2node.get(class1);
        Integer enode = uRI2node.get(class2);
        List<List<Integer>> simplePaths = searchSimplePaths(snode, enode, nsteps);
 
        ListIterator<List<Integer>> pit = simplePaths.listIterator();       
        while( pit.hasNext()){
            List<Integer> spath = pit.next();
            List<ClassPath> convertedPaths = convertSimplePathToPaths(spath);
            cps.addAll(convertedPaths);
        }        
        return cps;
    }
    
    private List<List<Integer>> searchSimplePaths(Integer snode, Integer enode, int nsteps){
        List<List<Integer>> simplePaths = new LinkedList<>();
        List<List<Integer>> lp = new LinkedList<>();
        List<Integer> ini = new LinkedList<Integer>(); // initial path
        ini.add(snode);
        lp.add(ini);
        for (int i = 0; i < nsteps; i++ ){
            ListIterator<List<Integer>> lit = lp.listIterator();
            List<List<Integer>> nextlp = new LinkedList<>();
            while ( lit.hasNext() ){ 
                List<Integer> crrpath = lit.next();
                Integer crrnode = crrpath.get(crrpath.size()-1);
                Set<Integer> nexts = adjlist.get(crrnode);
                Iterator<Integer> nit = nexts.iterator();
                while( nit.hasNext() ){
                    Integer nextnode = nit.next();
                    if ( crrpath.contains(nextnode) ){ continue; }
                    List<Integer> nextpath = new LinkedList<Integer>(crrpath); // copy
                    nextpath.add(nextnode);
                    if ( nextnode.equals(enode) ){
                        simplePaths.add(nextpath);
                        continue;
                    }
                    nextlp.add(nextpath);
                }
	    }
            lp = nextlp;
        }        
        return simplePaths;
    }
    
    private List<ClassPath> convertSimplePathToPaths(List<Integer> simplePath){
        List<ClassPath> paths = new LinkedList<ClassPath>();
        ListIterator<Integer> spit = simplePath.listIterator();

        Integer start = spit.next();
        String startClass = this.classURIs.get(start);
        Integer end = spit.next();
        Set<DiEdge> edges = properties.get(start).get(end);
        Iterator<DiEdge> eit = edges.iterator();
        while ( eit.hasNext() ){
            ClassPath cp = new ClassPath();
            cp.classes = simplePath;
            cp.properties = new LinkedList<DiEdge>(); 
            cp.properties.add(eit.next());
            paths.add(cp);
        }
        start = end;
        while( spit.hasNext() ){
            end = spit.next();
            // start-end
            edges = properties.get(start).get(end);
            List<ClassPath> tmppaths = new LinkedList<ClassPath>();            
            // current path
            ListIterator<ClassPath> pit = paths.listIterator();
            while ( pit.hasNext() ){
                ClassPath basepath = pit.next();
                eit = edges.iterator();
                while ( eit.hasNext() ){
                    // koko kara
                    DiEdge cl = eit.next();
                    ClassPath addedpath = new ClassPath();
                    addedpath.classes = basepath.classes;
                    addedpath.properties.add(cl);
                    tmppaths.add(addedpath);
                }
            }
            paths = tmppaths;
            start = end;
        }        
        return paths;
    }
    
}

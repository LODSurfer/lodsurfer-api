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
    List<Map<Integer,List<DiEdge>>> properties; // n1 - (n2 - labels)
    
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
        properties.add(new HashMap<Integer, List<DiEdge>>());
    }
    
    public void addEdge(String classURI1, String classURI2, String property, String endpointURI,
            int dsn, int don, int trn){
        Integer node1 = uRI2node.get(classURI1);
        Integer node2 = uRI2node.get(classURI1);
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
        
        if (properties.get(node1).get(node2) == null){ properties.get(node1).put(node2, new LinkedList<DiEdge>());}
        properties.get(node1).get(node2).add(new DiEdge(cr, true));
        if (properties.get(node2).get(node1) == null){ properties.get(node2).put(node1, new LinkedList<DiEdge>());}
        properties.get(node2).get(node1).add(new DiEdge(cr, false));
    }
    
}

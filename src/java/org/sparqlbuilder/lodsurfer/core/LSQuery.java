/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sparqlbuilder.lodsurfer.core;

//import javax.json.*;
import java.util.*;

/**
 *
 * @author atsuko
 */
public class LSQuery {
    static String prefixHeader = 
           "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
           "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
           "SELECT * WHERE{\n";
    
    static public String getSPARQL(List<String> curl, List<DiEdge> crl, List<String> ins){
        StringBuilder sparql = new StringBuilder(prefixHeader);
        if ( curl.size() < 2 || curl.size() != crl.size() + 1){ return "";}
        ListIterator<DiEdge> rit = crl.listIterator();
        int count = 0;
        while ( rit.hasNext() ){
            sparql.append("SERVICE <");
            String r1 = "?res".concat(Integer.toString(count));
            String r2 = "?res".concat(Integer.toString(count+1));
            DiEdge de = rit.next();
            sparql.append(de.cr.endpointURI);
            sparql.append(">{\n");
            if ( de.direction ){ 
                sparql.append(r1).append(" <").append(de.cr.propertyURI).append("> ").append(r2).append(" .\n");
            }else{
                sparql.append(r2).append(" <").append(de.cr.propertyURI).append("> ").append(r1).append(" .\n");
            }
            sparql.append(r1).append(" <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <").append(curl.get(count)).append("> .\n");
            sparql.append(r2).append(" <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <").append(curl.get(count+1)).append("> .\n");
            sparql.append("}\n");
            count ++;
        }
        
        sparql.append("}");
        return sparql.toString();
    }
    
    /*
    static public List<String> getSPARQLwithIns(List<String> curl, List<DiEdge> crl, List<String> ins){
        StringBuilder sparql = new StringBuilder(prefixHeader);
        if ( curl.size() < 2 || curl.size() != crl.size() + 1){ return null;}
        ListIterator<DiEdge> rit = crl.listIterator();
        int count = 0;
        while ( rit.hasNext() ){
            //sparql.append("SERVICE <");
            String r1 = "?res".concat(Integer.toString(count));
            String r2 = "?res".concat(Integer.toString(count+1));
            DiEdge de = rit.next();
            //sparql.append(de.cr.endpointURI);
            //sparql.append(">{\n");
            if ( de.direction ){ 
                sparql.append(r1).append(" <").append(de.cr.propertyURI).append("> ").append(r2).append(" .\n");
            }else{
                sparql.append(r2).append(" <").append(de.cr.propertyURI).append("> ").append(r1).append(" .\n");
            }
            sparql.append(r1).append(" <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <").append(curl.get(count)).append("> .\n");
            sparql.append(r2).append(" <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <").append(curl.get(count+1)).append("> .\n");
            //sparql.append("}\n");
            count ++;
        }
        
        List<String> qs = new LinkedList<>();
        if ( ins == null ){ 
            qs.add(sparql.append("}").toString());
        }
        
        
        //sparql.append("}");
        return qs;
   }
    */
}

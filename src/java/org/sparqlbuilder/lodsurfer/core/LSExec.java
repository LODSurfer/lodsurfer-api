/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sparqlbuilder.lodsurfer.core;

import java.util.*;
//import org.apache.jena.query.*;
/**
 *
 * @author atsuko
 */
public class LSExec {
    static public String execPath(List<String> curl, List<DiEdge> crl){
        List<DiEdge> crr = new LinkedList<>();
        crr.add(crl.get(0));
        List<String> cls = new LinkedList<>();
        cls.add(cls.get(0)); cls.add(cls.get(1));
        String crrep = crl.get(0).cr.endpointURI;
        List<String> ins = new LinkedList<>();
        for ( int i = 1 ; i < crl.size() ; i++){
            if ( crrep.equals(crl.get(i).cr.endpointURI)){
                crr.add(crl.get(i));
                cls.add(cls.get(i+1));
            }
            /*else{
                // crr search
                List<String> q = LSQuery.getSPARQLswithIns(cls, crl, ins);
                Query query = QueryFactory.create(q);
                QueryExecution qe = QueryExecutionFactory.sparqlService(crrep, query);
                ResultSet results = qe.execSelect();
                qe.close();
            }*/
        }
        return " ";
    }
}

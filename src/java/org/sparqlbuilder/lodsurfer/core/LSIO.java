package org.sparqlbuilder.lodsurfer.core;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.*;
import java.io.*;

/**
 *
 * @author atsuko
 */
public class LSIO {
    
    static Set<String> readEP(String filename){
        Set<String> eps = new HashSet<>();
        File epfile = new File(filename);
        try{
            BufferedReader br = new BufferedReader(new FileReader(epfile));
            String buf;
            while ( (buf = br.readLine()) != null ){
                eps.add(buf);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return eps;
    }
    
    static Map<String, ClassInfo> readCL(String filename){
        Map<String, ClassInfo> cl = new HashMap<String, ClassInfo>();
        File clfile = new File(filename);
        try{
            BufferedReader br = new BufferedReader(new FileReader(clfile));
            String buf;
            while ( (buf = br.readLine()) != null ){
                String[] data = buf.split("\t");// cl url \tab cl label \tab ep \tab #ins
                ClassInfo cli = cl.get(data[0]);
                if (cli == null){
                    cli = new ClassInfo(data[2], data[0], data[1], Integer.parseInt(data[3]));
                    cl.put(data[0], cli);
                }else{
                    cli.addInfo(data[2], data[1], Integer.parseInt(data[3]));
                }
                
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return cl;
    }
    
    static Map<String, TreeSet<String>> readCR(String filename){
        Map<String, TreeSet<String>> cr = new HashMap<>();
        File crfile = new File(filename);
        try{
            BufferedReader br = new BufferedReader(new FileReader(crfile));
            String buf;
            while ( (buf = br.readLine()) != null ){
                String[] data = buf.split("\t");// cl url \tab cl, cl,....
                String[] cls = data[1].split(",");
                TreeSet<String> clss = new TreeSet<>();
                for ( int i = 0; i < cls.length; i++){
                    clss.add(cls[i]);
                }
                cr.put(data[0], clss);
            }
        }catch(IOException e){
            e.printStackTrace();
        }       
        return cr;
    }
    
    static ClassGraph readCLDir(String dir, Set<String> eps){
        ClassGraph cg = new ClassGraph();
        
        Iterator<String> eit = eps.iterator();
        while ( eit.hasNext()){
            String ep = eit.next();
            String epc = ep.split("//")[1].replace("/", "_").replace("#", "-");
            File epclfile = new File(dir.concat(epc).concat(".cl")); // cl-relation-cl
            try{
                BufferedReader br = new BufferedReader(new FileReader(epclfile));
                String buf;
                while ( (buf = br.readLine()) != null ){
                    String[] data = buf.split("\t");
                    cg.addNode(data[0]);
                    cg.addNode(data[1]);
                    cg.addEdge(data[0], data[1], data[2], ep, Integer.parseInt(data[3]), 
                            Integer.parseInt(data[4]), Integer.parseInt(data[5]));
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        
        return cg;
    }
}

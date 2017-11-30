package org.sparqlbuilder.lodsurfer.core;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.*;
import java.io.*;
import javax.json.*;

/**
 *
 * @author atsuko
 */
public class LSIO {
    
    static List<String> readEP(String filename){
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
        return new LinkedList(eps);
    }

    /*
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
    }*/
    
    static List<String> readCL(String filename){
        List<String> cl = new LinkedList<String>();
        File clfile = new File(filename);
        
        try{
            BufferedReader br = new BufferedReader(new FileReader(clfile));
            String buf;
            while ( (buf = br.readLine()) != null ){
                cl.add(buf);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return cl;
    }
    
    static void readCR(String filename, ClassGraph cg){
        File crfile = new File(filename);
        try{
            BufferedReader br = new BufferedReader(new FileReader(crfile));
            String buf;
            while ( (buf = br.readLine()) != null ){
                String[] data = buf.split("\t");// cl1 pr cl2 ep
                cg.addEdge(data[0], data[2], data[1], data[3], 100, 100, 100);
            }
        }catch(IOException e){
            e.printStackTrace();
        }        
    }
    
    static List<Set<Integer>> readCC(String filename, List<Boolean> singleton){
        File crfile = new File(filename);
        List<Set<Integer>> cc = new LinkedList<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(crfile));
            String buf;
            while ( (buf = br.readLine()) != null ){
                Set<Integer> compo = new HashSet<>();
                String[] data = buf.split("\t");// cl url \tab cl, cl,....
                String[] ids = data[1].split(","); 
                for ( int i = 0; i < ids.length; i++ ){
                    compo.add(new Integer(ids[i]));
                    singleton.set(Integer.parseInt(ids[i]), new Boolean(false));                    
                }
                cc.add(compo);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return cc;
    }
    
    /*
        Map<String, Set<String>> cr = new HashMap<>();
        File crfile = new File(filename);
        try{
            BufferedReader br = new BufferedReader(new FileReader(crfile));
            String buf;
            while ( (buf = br.readLine()) != null ){
                String[] data = buf.split("\t");// cl url \tab cl, cl,....
                if (data.length <= 1){
                    cr.put(data[0], new HashSet<>());
                    continue;
                }
                if ( data[1] == null ){ 
                    cr.put(data[0], new HashSet<>());
                    continue;
                }
                String[] cls = data[1].split(",");
                Set<String> clss = new HashSet<>();
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
    */
    
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
    
    static public JsonObject parseJsonObject(String jos){
        //JsonParserFactory f = Json.createParserFactory(null);
        JsonReader jr = Json.createReader(new StringReader(jos));
        JsonObject jo = jr.readObject();
        jr.close();
        return jo;
    }

    static public JsonArray parseJsonArray(String jas){
        //JsonParserFactory f = Json.createParserFactory(null);
        JsonReader jr = Json.createReader(new StringReader(jas));
        JsonArray ja = jr.readArray();
        jr.close();
        return ja;
    }
    
}

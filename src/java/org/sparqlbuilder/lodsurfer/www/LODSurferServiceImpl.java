/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sparqlbuilder.lodsurfer.www;

//import javax.ws.rs.PathParam;

import org.sparqlbuilder.lodsurfer.core.LSCtrl;


/**
 *
 * @author atsuko
 */
public class LODSurferServiceImpl implements LODSurferService{

    LSCtrl lsctrl;
    
    public LODSurferServiceImpl(){
        lsctrl = new LSCtrl();
    }
    
    @Override
    public String getUsage(){
        return "Usage: \n"
               + "/api/clist             Get a list of all the classes \n"
               + "/api/clist/{class1 URL} Get a list of all the classes having paths from class1 ";
    }
    
    @Override
    public String getCList(String class1){
        //return lsctrl;
        return "";
    }
    
    @Override
    public String getEPList(String class1){
        return "";
    }
    
    @Override
    public String getPaths(String class1, String class2, String filters){
        return "";
    }

    @Override
    public String getIPaths(String path, String instances){
        return "";
    }
}

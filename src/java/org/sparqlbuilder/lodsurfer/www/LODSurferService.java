/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sparqlbuilder.lodsurfer.www;

import javax.ws.rs.*;
import org.sparqlbuilder.lodsurfer.core.LSCtrl;

/**
 *
 * @author atsuko
 */

@Path("/")
public class LODSurferService {
    
    LSCtrl lsctrl;
    
    public LODSurferService(){
        if ( lsctrl == null ){
            lsctrl = new LSCtrl();
        }
    }    
    
    @GET
    public String getUsage(){
        return "";
    }
    
    @GET
    @Path("/eplist")
    public String getEPList(){
        return lsctrl.getEPs().toString();
    }
/*
    @GET
    @Path("/clist")
    public String getCList(){
        return lsctrl.getAllClasses().toString();
    }
//*/
    
    
///*    
    @GET
    @Path("/clist")
    public String getCList(@QueryParam("class1") String class1){
        if (class1 == null){
            return lsctrl.getAllClasses().toString();
        }else{
            return lsctrl.getCLs(class1).toString();                    
        }
    }

    @GET
    @Path("/path/{class1}/{class2}")
    public String getPaths(@PathParam("class1") String class1, @PathParam("class2") String class2){
        return lsctrl.getPaths(class1, class2).toString();
    }
    
    @GET
    @Path("/path/{class1}/{class2}/{filters}")
    public String getPaths(@PathParam("class1") String class1, @PathParam("class2") String class2,
                           @PathParam("filters") String filters){
        return "Hi";
        
    }
   
    @GET
    @Path("/ipath/{path}/{instances}")
    public String getIPaths(@PathParam("path") String iclass, @PathParam("instances") String oclass){
        return "Hi";
        
    }
}

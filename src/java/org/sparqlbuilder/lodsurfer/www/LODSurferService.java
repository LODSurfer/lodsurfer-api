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
    
    LSCtrl lsctrl = new LSCtrl();;
    
    /*public LODSurferService(){
        if ( lsctrl == null ){
            lsctrl = new LSCtrl();
        }
    } */   
    
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
        
    @GET
    @Path("/clist")
    public String getCList(@QueryParam("class1") String class1){
        if (class1 == null){
            return lsctrl.getAllClasses().toString();
        }else{
            return lsctrl.getCLs(class1).toString();                    
        }
    }

    /*@GET
    @Path("/path")
    public String getPaths(@QueryParam("class1") String class1, @QueryParam("class2") String class2){
        return lsctrl.getPaths(class1, class2).toString();
    }
    */
    
    @GET
    @Path("/path")
    public String getPaths(@QueryParam("class1") String class1, @QueryParam("class2") String class2,
                           @QueryParam("filter") String filters){
        return lsctrl.getPaths(class1, class2).toString();
        
    }
   
    /*
    @GET
    @Path("/tps")
    public String getTPS(@QueryParam("path") String path){
        return lsctrl.getTPs(path).toString();        
    }
    */
    
    @GET
    @Path("/sparql")
    public String getSPARQL(@QueryParam("path") String path, @QueryParam("instances") String instances){
        return lsctrl.getSPARQL(path, instances);
    }
    
    @GET
    @Path("/result")
    public String getResult(@QueryParam("path") String path, @QueryParam("instances") String instances){
        return lsctrl.getResult(path, instances);
    }
}

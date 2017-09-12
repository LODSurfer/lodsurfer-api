/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sparqlbuilder.lodsurfer.www;

import javax.ws.rs.*;

/**
 *
 * @author atsuko
 */

@Path("/")
public interface LODSurferService {
    
    @GET
    public String getUsage();
    
    @GET
    @Path("/clist/{class1}")
    public String getCList(@PathParam("class1") String class1);
   
    @GET
    @Path("/eplist/{class1}")
    public String getEPList(@PathParam("class1") String class1);
   
    @GET
    @Path("/path/{class1}/{class2}/{filters}")
    public String getPaths(@PathParam("class1") String class1, @PathParam("class2") String class2,
                           @PathParam("filters") String filters);
   
    @GET
    @Path("/ipath/{path}/{instances}")
    public String getIPaths(@PathParam("path") String iclass, @PathParam("instances") String oclass);
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sparqlbuilder.lodsurfer.www;

import javax.ws.rs.core.Application;
import javax.ws.rs.*;
import org.sparqlbuilder.lodsurfer.core.LSCtrl;

/*
 * @author atsuko
 */

@ApplicationPath("api")
public class LODSurferApp extends Application{
    LSCtrl lsctrl;

    public LODSurferApp(){
        //lsctrl = new LSCtrl();    
    }
}

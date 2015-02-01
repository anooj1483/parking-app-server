/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.collaborativeclouds.rest;

import com.collaborativeclouds.workers.ParkingData;
import com.collaborativeclouds.workers.UserOperations;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.HttpHeaders;
import org.json.JSONException;

/**
 * REST Web Service
 *
 * @author CollaborativeClouds Software Solutions
 * <www.collaborativeclouds.com>
 * <collaborativeclouds@gmail.com>
 */
@Path("park")
public class ParkingResource {
    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ParkingResource
     */
    public ParkingResource() {
    }

    /**
     * Retrieves representation of an instance of
     * com.collaborativeclouds.rest.ParkingResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Path("/status/")
    @Produces("application/json")
    public String getParkingStatus() {
        //TODO return proper representation object
        try {
            ParkingData mParking = new ParkingData();
            String result = mParking.getParkingStatus();
            return result;
        } catch (Exception ex) {
            return "Failed";
        }
    }
    
    @POST
    @Path("/bookslot/")
    @Consumes("application/json")
    @Produces("application/json")
    public String bookParkingSlot(String bookedSlot, @Context HttpHeaders headers) throws JSONException {
        
        String username = headers.getRequestHeader("username").get(0);
        UserOperations mOperations = new UserOperations();
        if (mOperations.isValidUserName(username)) {
            ParkingData mParkingData = new ParkingData();
            String book_status = mParkingData.bookSlot(bookedSlot,username);
            return book_status;
        }else{
            return "Failed";
        }
        
    }

    @POST
    @Path("/getSlotofUser/")
    @Consumes("application/json")
    @Produces("application/json")
    public String getParkingSlotofUser(String bookedSlot, @Context HttpHeaders headers) throws JSONException {
        
        String username = headers.getRequestHeader("username").get(0);
        UserOperations mOperations = new UserOperations();
        if (mOperations.isValidUserName(username)) {
            ParkingData mParkingData = new ParkingData();
            String book_status = mParkingData.getSlotofUser(username);
            return book_status;
        }else{
            return "Failed";
        }
    }
    
    @POST
    @Path("/login/")
    @Consumes("application/json")
    @Produces("application/json")
    public String login(String userInfo) throws JSONException {
        
            UserOperations mUserData = new UserOperations();
            boolean book_status = mUserData.validateLogin(userInfo);
            if(book_status){
                return "Success";
            }else{
                return "Failed";
            }
    }
    /**
     * PUT method for updating or creating an instance of ParkingResource
     *
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.collaborativeclouds.workers;

import com.collaborativeclouds.mappers.Parking;
import com.collaborativeclouds.utils.JSONUtils;
import com.google.gson.Gson;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author CollaborativeClouds Software Solutions
 * <www.collaborativeclouds.com>
 * <collaborativeclouds@gmail.com>
 */
public class ParkingData {

    private Session mSession = null;
    private Transaction mTransaction = null;

    public String getParkingStatus() {
        try {
            SessionFactory sessFact = new Configuration().configure().buildSessionFactory();
            mSession = sessFact.openSession();
            mTransaction = mSession.beginTransaction();
            List<Parking> mPark = null;
            Query getParkData = mSession.createQuery("select slotno from Parking");
            mPark = (List<Parking>) getParkData.list();
            String json = new Gson().toJson(mPark);
            return json;
        } catch (Exception ex) {
            return "Failed";
        }
    }
    
    public String getSlotofUser(String username) {
        try {
            SessionFactory sessFact = new Configuration().configure().buildSessionFactory();
            mSession = sessFact.openSession();
            mTransaction = mSession.beginTransaction();
            List<Parking> mPark = null;
            Query getParkData = mSession.createQuery("from Parking where username='"+username+"'");
            mPark = (List<Parking>) getParkData.list();
            String json = new Gson().toJson(mPark);
            return json;
        } catch (Exception ex) {
            return "Failed";
        }
    }
    
        /*public String removeSlotofUser(String userInfo) {
        try {
            SessionFactory sessFact = new Configuration().configure().buildSessionFactory();
            mSession = sessFact.openSession();
            mTransaction = mSession.beginTransaction();
            List<Parking> mPark = null;
            Query getParkData = mSession.createQuery("from Parking where username='"+username+"'");
            mPark = (List<Parking>) getParkData.list();
            String json = new Gson().toJson(mPark);
            return json;
        } catch (Exception ex) {
            return "Failed";
        }
    }*/

    public String bookSlot(String Slotnum, String username) throws JSONException {

        try{
        SessionFactory sessFact = new Configuration().configure().buildSessionFactory();
        mSession = sessFact.openSession();
        mTransaction = mSession.beginTransaction();
        JSONUtils mUtils = new JSONUtils();
        if (mUtils.isJSONValid(Slotnum)) {
            JSONObject mObject = new JSONObject(Slotnum);
            String mSlot = mObject.getString("slotnumber");
            //UUID sess_id = UUID.randomUUID();
            Random mRandom  =   new Random();
            int sess_id_  =   mRandom.nextInt(100000 - 100 + 1)+100;
            String sess_id  =   ""+sess_id_;
            Parking mParkData = new Parking();
            mParkData.setSlotno(mSlot);
            mParkData.setStatus("booked");
            mParkData.setCode("" + sess_id);
            mParkData.setUsername(username);
            mSession.save(mParkData);
            mTransaction.commit();
            
            return ""+sess_id;
        } else {
            return "Failed";
        }
        }catch(Exception e){
            return "Failed";
        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.collaborativeclouds.workers;

import com.collaborativeclouds.mappers.Users;
import com.collaborativeclouds.utils.JSONUtils;
import java.util.List;
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
public class UserOperations {

    private Session mSession = null;
    private Transaction mTransaction = null;

    public boolean validateLogin(String userdata) throws JSONException {
        try {
            SessionFactory sessFact = new Configuration().configure().buildSessionFactory();
            mSession = sessFact.openSession();
            mTransaction = mSession.beginTransaction();
            JSONUtils mUtils = new JSONUtils();
            if (mUtils.isJSONValid(userdata)) {

                JSONObject mObject = new JSONObject(userdata);
                String mUsername = mObject.getString("username");
                String mPassword = mObject.getString("password");
                List<Users> mUserInfo = null;
                Query mUserData = mSession.createQuery("select username from Users where username='" + mUsername + "' and password='" + mPassword + "'");
                mUserInfo = (List<Users>) mUserData.list();
                if (mUserInfo.size() > 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public String registerUser(String registerData) throws JSONException {

        try {
            SessionFactory sessFact = new Configuration().configure().buildSessionFactory();
            mSession = sessFact.openSession();
            mTransaction = mSession.beginTransaction();

            JSONUtils mUtils = new JSONUtils();
            if (mUtils.isJSONValid(registerData)) {

                JSONObject mObject = new JSONObject(registerData);
                String mUsername = mObject.getString("username");
                String mPassword = mObject.getString("password");

                Users mUser = new Users();
                mUser.setUsername(mUsername);
                mUser.setPassword(mPassword);
                mSession.save(mUser);
                mTransaction.commit();

                return "Success";
            } else {
                return "Failed";
            }
        } catch (Exception e) {
            return "Failed";
        }
    }

    public boolean isValidUserName(String username) throws JSONException {
        SessionFactory sessFact = new Configuration().configure().buildSessionFactory();
        mSession = sessFact.openSession();
        mTransaction = mSession.beginTransaction();
        List<Users> mUserInfo = null;
        Query mUserData = mSession.createQuery("select username from Users where username='" + username + "'");
        mUserInfo = (List<Users>) mUserData.list();
        if (mUserInfo.size() > 0) {
            return true;
        } else {
            return false;
        }

    }
}

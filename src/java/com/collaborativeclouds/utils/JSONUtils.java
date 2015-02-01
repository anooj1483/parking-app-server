/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.collaborativeclouds.utils;

import com.google.gson.Gson;

/**
 *
 * @author CollaborativeClouds Software Solutions
 * <www.collaborativeclouds.com>
 * <collaborativeclouds@gmail.com>
 */
public final class JSONUtils {
  private static final Gson gson = new Gson();

  public JSONUtils(){}

  public boolean isJSONValid(String JSON_STRING) {
      try {
          gson.fromJson(JSON_STRING, Object.class);
          return true;
      } catch(com.google.gson.JsonSyntaxException ex) { 
          return false;
      }
  }
}

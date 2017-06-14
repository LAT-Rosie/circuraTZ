/**
 */
package com.circura;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.AlarmManager;
import android.content.Context;
import android.util.Log;

import java.util.Date;
import java.util.TimeZone;



import android.util.Log;

import java.util.Date;

public class CircuraTZ extends CordovaPlugin {
  private static final String TAG = "CircuraTZ";
  String tzStrings[];

  @Override
  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
      super.initialize(cordova, webView);
      // your init code here
      Log.d(TAG, "Initializing CircuraTZ");
      tzStrings = TimeZone.getAvailableIDs();
  }

  @Override
  public void onPause(boolean multitasking) {
      Log.d(TAG, "onPause");
      super.onPause(multitasking);
  }

  @Override
  public void onResume(boolean multitasking) {
      Log.d(TAG, "onResume " );
      super.onResume(multitasking);
  }
      
  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    try {
      if ("setTimeZone".equals(action)) {
        
        AlarmManager alarmMgr = (AlarmManager)(this.cordova.getActivity().getSystemService(Context.ALARM_SERVICE));
        alarmMgr.setTimeZone(args.getString(0));
        // alarmMgr.setTimeZone(tzStrings[145]); // Pacific
        
        callbackContext.success("Time Zone set at: " + args.getString(0));
          return true;    
      }
      if ("getTimeZone".equals(action)) {
        TimeZone tz = TimeZone.getDefault();
        String currentTZ = tz.getDisplayName (tz.inDaylightTime(new Date()), TimeZone.LONG);
        // callbackContext.success("Default Time Zone: " + currentTZ);
        final PluginResult result = new PluginResult(PluginResult.Status.OK, currentTZ);
        callbackContext.sendPluginResult(result);
        return true;

      }
      if ("getAllTimeZones".equals(action)) {
        if (tzStrings.length > 0) {
            StringBuilder tzBuilder = new StringBuilder();

            for (String n : tzStrings) {
                tzBuilder.append("'").append(n.replace("'", "\\'")).append("',");
            }

            tzBuilder.deleteCharAt(tzBuilder.length() - 1);

            final PluginResult result = new PluginResult(PluginResult.Status.OK, tzBuilder.toString());
            callbackContext.sendPluginResult(result);
            return true;
        }
      }
      return false;

    } catch(Exception e) {
        System.err.println("Exception: " + e.getMessage());
        callbackContext.error(e.getMessage());
        return false;
    }
  }
}

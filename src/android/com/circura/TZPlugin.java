package com.circura;

import android.app.AlarmManager;
import android.content.Context;
import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.Date;
import java.util.TimeZone;

public class TZPlugin extends CordovaPlugin {

	String tzStrings[];
	
	@Override
	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
	    super.initialize(cordova, webView);
	    // your init code here
	    tzStrings = TimeZone.getAvailableIDs();
	}

    @Override
    public void onPause(boolean multitasking) {
        Log.d("TZPlugin", "onPause");
        super.onPause(multitasking);
    }

    @Override
    public void onResume(boolean multitasking) {
        Log.d("TZPlugin", "onResume " );
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
				callbackContext.success("Default Time Zone: " + currentTZ);
			    return true; 		
			}
			if ("getAllTimeZones".equals(action)) {
				if (tzStrings.length > 0) {
				    StringBuilder tzBuilder = new StringBuilder();

				    for (String n : tzStrings) {
				        tzBuilder.append("'").append(n.replace("'", "\\'")).append("',");
				    }

				    tzBuilder.deleteCharAt(tzBuilder.length() - 1);

				callbackContext.success(tzBuilder.toString());
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

package com.circura.tzplugin;

// import java.text.SimpleDateFormat;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.Date;
import java.util.TimeZone;
import android.app.AlarmManager;
// import android.app.KeyguardManager;
import android.app.PendingIntent;
// import android.app.KeyguardManager.KeyguardLock;
import android.content.Context;
import android.content.Intent;
// import android.content.SharedPreferences;
// import android.os.PowerManager;
// import android.os.PowerManager.WakeLock;
// import android.preference.PreferenceManager;
import android.util.Log;
import java.lang.StringBuilder;


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
        
        // PowerManager pm = (PowerManager)this.cordova.getActivity().getSystemService(Context.POWER_SERVICE);
        // WakeLock wakeLock = pm.newWakeLock((PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "TAG");
        // wakeLock.acquire();
 
        // KeyguardManager keyguardManager = (KeyguardManager) this.cordova.getActivity().getSystemService(Context.KEYGUARD_SERVICE); 
        // KeyguardLock keyguardLock =  keyguardManager.newKeyguardLock("TAG");
        // keyguardLock.disableKeyguard();
    }
	    
	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		try {
			if ("setTimeZone".equals(action)) {
				
				AlarmManager alarmMgr = (AlarmManager)(this.cordova.getActivity().getSystemService(Context.ALARM_SERVICE));
				// alarmMgr.setTimeZone(args.getString(0));
				alarmMgr.setTimeZone(tzStrings[145]);
				
				callbackContext.success("Time Zone set at: " + tzStrings[145]);
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

				    for (String n : name) {
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

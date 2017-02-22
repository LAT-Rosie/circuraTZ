package com.circura.tzplugin;

// import java.text.SimpleDateFormat;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.Date;
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


public class TZPlugin extends CordovaPlugin {

	
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
				alarmMgr.setTimeZone(args.get(0));
				
				callbackContext.success("Time Zone set at: " +args.get(0));
			    return true; 		
			}
			return false;		
		} catch(Exception e) {
		    System.err.println("Exception: " + e.getMessage());
		    callbackContext.error(e.getMessage());
		    return false;
		} 
	}
}

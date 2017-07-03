/**
 */
package com.circura;

import android.app.AlarmManager;
import android.content.Context;
import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.Date;
import java.util.TimeZone;

import static java.lang.Math.abs;

public class CircuraTZ extends CordovaPlugin {
    private static final String TAG = "CircuraTZ";


    StringBuilder tzBuilder;
    String tzStrings[];
    Integer mOffset = 100; // Only works with setTimeZoneByOffset()

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        // your init code here
        Log.d(TAG, "Initializing CircuraTZ");
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
            if ("setTimeZoneByOffset".equals(action)) {
                Integer offset;
                String posix_offset;

                AlarmManager alarmMgr = (AlarmManager)(this.cordova.getActivity().getSystemService(Context.ALARM_SERVICE));
                // Expecting signed integers in the range [-12, +14]
                try {
                    offset = new Integer(args.getInt(0));
                    // If the offset is the same, exit
                    if (offset == mOffset) {
                        return true;
                    }
                    if ((offset < -12) || (offset > 14)) {
                        throw (new Exception("Time Zone offset out of range: " + args.getString(0)) );
                    }
                    // Now, the POSIX-style actually reverses the value
                    if (offset > 0) {
                        posix_offset = "-" + offset.toString();
                    } else if (offset < 0) {
                        posix_offset = "+" + abs(offset);
                    } else {
                        posix_offset = "+0";
                    }
                    alarmMgr.setTimeZone("Etc/GMT" + posix_offset);
                    mOffset = offset;
                } catch ( NumberFormatException nfe ) {
                    throw (nfe);
                }
                callbackContext.success("Time Zone set at: " + "Etc/GMT" + posix_offset);
                return true;
            }
            if ("getTimeZone".equals(action)) {
                TimeZone tz = TimeZone.getDefault();
                String currentTZ = tz.getDisplayName (tz.inDaylightTime(new Date()), TimeZone.LONG);
                final PluginResult result = new PluginResult(PluginResult.Status.OK, currentTZ);
                callbackContext.sendPluginResult(result);
                return true;

            }
            if ("getAllTimeZones".equals(action)) {
                if (tzStrings.length <= 0) {
                    tzStrings = TimeZone.getAvailableIDs();

                    tzBuilder = new StringBuilder();

                    for (String n : tzStrings) {
                        tzBuilder.append("'").append(n.replace("'", "\\'")).append("',");
                    }

                    tzBuilder.deleteCharAt(tzBuilder.length() - 1);
                }
                if (tzStrings.length > 0) {

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

var exec = require('cordova/exec');

var PLUGIN_NAME = 'circuraTZ';

var circuraTZ = {
    setTimeZone: function(tzString, successCallback, errorCallback) {
        if(tzString === null)
    		return;
    	
        exec(
            successCallback,
            errorCallback,
            PLUGIN_NAME,
            "setTimeZone",
            [tzString]
            // ["firstArgument", "secondArgument", 42, false]);
        );
    },
    getTimeZone: function(successCallback, errorCallback) {
        exec(
            successCallback,
            errorCallback,
            PLUGIN_NAME,
            "getTimeZone",
            null
        );
    },
    getAllTimeZones: function(successCallback, errorCallback) {
        exec(
            successCallback,
            errorCallback,
            PLUGIN_NAME,
            "getAllTimeZones",
            null
        );
    }
};
module.exports = circuraTZ;


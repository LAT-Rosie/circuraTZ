var exec = require('cordova/exec');

var tz_controller = {
    setTimeZone: function(tzString, successCallback, errorCallback) {
        if(tzString === null)
    		return;
    	
        exec(
            successCallback,
            errorCallback,
            "TZPlugin",
            "setTimeZone",
            [tzString]
            // ["firstArgument", "secondArgument", 42, false]);
        );
    },
    getTimeZone: function(successCallback, errorCallback) {
        exec(
            successCallback,
            errorCallback,
            "TZPlugin",
            "getTimeZone",
            null
        );
    },
    getAllTimeZones: function(successCallback, errorCallback) {
        exec(
            successCallback,
            errorCallback,
            "TZPlugin",
            "getAllTimeZones",
            null
        );
    }
};
module.exports = tz_controller;

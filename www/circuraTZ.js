var exec = require('cordova/exec');

var tz_controller = {
    set: function(tzString, successCallback, errorCallback) {
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
    }
    get: function(tzString, successCallback, errorCallback) {
        if(tzString === null)
    		return;
    	
        exec(
            successCallback,
            errorCallback,
            "TZPlugin",
            "getTimeZone",
            [tzString]
            // ["firstArgument", "secondArgument", 42, false]);
        );
    }

};
module.exports = tz_controller;

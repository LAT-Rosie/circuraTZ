
var exec = require('cordova/exec');

var PLUGIN_NAME = 'CircuraTZ';

var CircuraTZ = {
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
            []
        );
    },
    getAllTimeZones: function(successCallback, errorCallback) {
        exec(
            successCallback,
            errorCallback,
            PLUGIN_NAME,
            "getAllTimeZones",
            []
        );
    }
};

module.exports = CircuraTZ;

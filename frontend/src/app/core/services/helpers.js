"use strict";
var http_1 = require("@angular/http");
exports.objToSearchParams = function (obj) {
    var params = new http_1.URLSearchParams();
    for (var i in obj) {
        if (obj.hasOwnProperty(i)) {
            if (obj[i]) {
                params.append(i, obj[i]);
            }
        }
    }
    return params;
};

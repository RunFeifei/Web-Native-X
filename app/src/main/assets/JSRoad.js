(function (win) {
    var hasOwnProperty = Object.prototype.hasOwnProperty;
    var JSRoad = win.JSRoad || (win.JSRoad = {});
    var Adapter = {
        callbackFun:null,
        callNative: function (method, params, callback) {
            console.log(callback);
            this.callbackFun = callback;
            var uri="dianrong" + '://' + "schema" + '/' + method + '?' + params;
            console.log(uri);
            window.prompt(uri, "");
        },
        onNativeFinish: function (result){
            console.log(result);
            console.log(this.callbackFun);
            this.callbackFun && this.callbackFun(result);
        },
    };
    for (var key in Adapter) {
        if (!hasOwnProperty.call(JSRoad, key)) {
            JSRoad[key] = Adapter[key];
        }
    }
})(window);
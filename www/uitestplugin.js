var UITestPlugin = function () {
};

UITestPlugin.prototype.open = function (success, fail, msg) {
    return cordova.exec(success, fail, "UITestPlugin", "open", [msg]);
};

var plugin = new UITestPlugin();

module.exports = plugin;
// HTML编码
String.prototype.HTMLEncode = function () {
    var temp = document.createElement("div");
    (temp.textContent != null) ? (temp.textContent = this)
        : (temp.innerText = this);
    var output = temp.innerHTML;
    temp = null;
    return output;
};
// HTML解码
String.prototype.HTMLDecode = function () {
    var temp = document.createElement("div");
    temp.innerHTML = this;
    var output = temp.innerText || temp.textContent;
    temp = null;
    return output;
};
String.prototype.trim = function () {
    return this.replace(/^\s+|\s+$/g, '');
};
String.prototype.length = function () {
    return this.length;
};
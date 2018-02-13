Number.prototype.fullNumberStr = function(len) {
	var ret = this;
	for (var i = 0; i < len - (ret + '').length; i++) {
		ret = '0' + ret;
	}
	return ret;
}
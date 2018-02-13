function Alert(param) {
	param = param === undefined? {} :param;
	var width = param.width;
	var height = param.height;
	var background = param.background;
	var showTime = param.showTime === undefined? 800: param.showTime;
	var stopTime = param.stopTime === undefined? 5000: param.stopTime;
	var cancelTime = param.cancelTime === undefined? 800: param.cancelTime;
	this.alert = function (msg){
		Alert.alertQueue.push(msg);
	}
		
		
	Alert.timerFlag = 0;
	Alert.alertQueue = new Array();
	Alert.timerExecute = function () {
		
		// ¿ªÊ¼ÏÔÊ¾
		var msg = Alert.alertQueue.shift();
		if (msg !== undefined) {
			
			
			$('body').append('<div class="alert_window" style="top:-50px;"><div class="display">' + msg + '</div></div>');
			var div = $('.alert_window :last');
			if (width !== undefined) {
				div.css('width', width);
			}
			if (height !== undefined) {
				div.css('height', height);
			}
			if (background !== undefined) {
				div.css('background-image', 'url(' + background + ')');
			}
		
			$('.alert_window').each(function(){
				Alert.timerFlag++;
				$(this).animate({top:'+=60px'}, showTime, function () {
					Alert.timerFlag--;
				});
			});
			div.find('.display').animate({top:'+=0px'}, stopTime, function() {
				$(this).animate({height:'0px',padding:'0px'}, cancelTime, function() {
					$(this).parent().remove();
				});
			});
			
		}
		
		var waitTime = 50;
		if (Alert.timerFlag !== 0) {
			waitTime += showTime;
		}
		setTimeout('Alert.timerExecute()', waitTime);
		
	}
	Alert.timerExecute();
}

alert = new Alert().alert;
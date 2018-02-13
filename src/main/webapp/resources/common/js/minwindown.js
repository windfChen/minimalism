function minwindown (title, url, id, params) {	
		var minwindownId = 'minwindown';
		if (id != undefined) {
			minwindownId += '_' + id;
		}
		if ($('#' + minwindownId).length == 0) {
			var minwindownContent = '<div id="' + minwindownId + '" class="minwindown-popover">\
			 <div class="minwindown-poptit">\
				  <a href="javascript:;" title="关闭" class="close">×</a>\
				  <h3 class="minwindown_title"></h3>\
			 </div>\
			 <div>\
				<iframe class="minwindown_iframe" onload="" src="" marginheight="0" marginwidth="0" frameborder="0" scrolling="no" ></iframe>\
			 </div>\
			</div>';			
			$('body').append(minwindownContent);
			if ($('.minwindown-popover-mask').length == 0) {
				$('body').append('<div class="minwindown-popover-mask"></div>');
			}
			
			$('#' + minwindownId).find('.minwindown-poptit .close').click(function(){
				$('.minwindown-popover-mask').fadeOut(100);
				$('.minwindown-popover').slideUp(200);
			});
			
			$('#' + minwindownId).find('.minwindown_iframe').load(function(){
				var name = 'name_' + minwindownId;
				$(this).attr('id', name);
			});
		}
		
		if (title != undefined) {
			$('#' + minwindownId + ' .minwindown_title').html(title);
		}
		if (url != undefined) {
			$('#' + minwindownId + ' .minwindown_iframe').attr('src', url);
		}
		
		if(params) {
			if (params.width) {
				$('#' + minwindownId + ' .minwindown_iframe').css('width', params.width);
			}
			if (params.height) {
				$('#' + minwindownId + ' .minwindown_iframe').css('height', params.height);
			}
		}
		
		$('.minwindown-popover-mask').fadeIn(100);
		$('#' + minwindownId).slideDown(200);
	}
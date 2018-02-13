$(function(){
    $('[iterator]').each(function(){    
        var info = $(this).attr('iterator');
        ajaxLoader.add('iterator',info, this);
    });
    
    
    $('[page]').each(function(){    
        var info = $(this).attr('page');
        ajaxLoader.add('page',info, this);
    });
	
	ajaxLoader.doAll();
});

(function() {
	function AjaxLoader (){
		
	}

	AjaxLoader.prototype = {
		urlConfigs : {},
		ajaxConfigs : {},
		add : function(type, info, obj) {
			var config = ajaxLoader._parseInfos(info);
			var urlStr = config.urlStr;
			config.divObject = obj;
			config.classStr = 'AL_' + new Date().getTime() + '' + parseInt(Math.random() * 1000);
			config.templet = $(config.divObject).addClass(config.classStr).prop("outerHTML");
			config.type = type;
			var configs = ajaxLoader.ajaxConfigs[urlStr];
			if (configs == undefined) {
				configs = [];
				ajaxLoader.ajaxConfigs[urlStr] = configs;
			}
			configs[configs.length] = config;
		},
		doAll : function () {
			for (var urlStr in this.ajaxConfigs) {
				this.doAjax(urlStr);
			}
		},
		doAjax : function(urlStr) {
			var requestConfig = this._parseRequestConfig(urlStr);
			var obj = this;
			$.ajax({
				async:true,
				url: requestConfig.url + queryString,
				type: "GET",
				dataType: 'json',
				data: requestConfig.params,
				beforeSend: function(){
					
				},
				success: function (json) {
					obj.load(json, urlStr, requestConfig);
				},
				error:function(XHR, textStatus, errorThrown){
					alert('error: ' + errorThrown);
				}
			});
		},
		load : function (json, urlStr, requestConfig) {
			var configs = this.ajaxConfigs[urlStr];
			
			for (var i = 0; i < configs.length; i++) {
				var config = configs[i];
				var data = '';
				if (config.valueName == '') {
					data = json;
				} else {
					data = eval('json.' + config.valueName);
				}
				
				if (config.type == 'iterator') {
					this._loadIterator(data, config);
				} else if (config.type == 'page') {
					this._loadPage(data, config, requestConfig);
				}
				$('.' + config.classStr).show();
			}
		},
		_loadPage: function(data, config, requestConfig) {
			$(config.divObject).html(getPageHtml(requestConfig.params.page, data, requestConfig.params.limit)).show();
		},
		_loadIterator : function(data, config) {
			// 记录原始节点信息
			$('.' + config.classStr + '[AL_copied=1]').remove();	
			var iteratorHtml = config.templet;
			var allHtmls = '';
			for (var i = 0; i < data.length; i++) {
				var item = data[i];
				var html = iteratorHtml;
				if (config.adapterName != '') {
					item = eval(config.adapterName + '(item)');
				}
				for(var p in item) {
					var reg = new RegExp('@{' + config.itemName + '.' + p + '}',"g");   
					html = html.replace(reg, item[p]);
				}
				allHtmls += html;
			}
			$(config.divObject).replaceWith(allHtmls);
			$('.' + config.classStr + " [ajaxContent]").each(function(){
				$(this).html($(this).attr('ajaxContent'));
			});
			$('.' + config.classStr + ':gt(0)').removeAttr(config.type).attr('AL_copied', '1');
			config.divObject = $('.' + config.classStr + ':eq(0)')[0];
		},
		_parseInfos : function(str) {
			var config = {
				itemName : undefined,
				urlStr : undefined,
				url : undefined,
				params : undefined,
				valueName : undefined,
				adapterName :undefined
			}
			
			// 解析整体参数，变量名(可选)，url信息，处理方法
			var infos = str.split('::');
			var urlConfig = '';
			if (infos.length == 1) {
				urlConfig = infos[0];
			} else {
				config.itemName = infos[0];
				urlConfig = infos[1];
				config.adapterName = infos[2];
			}
			
			// 解析url配置，url地址??参数信息->取变量值
			var ss1 = urlConfig.split('->');
			config.urlStr = ss1[0];
			if (ss1.length == 2) {
				config.valueName = ss1[1];
			}
			
			return config; 
		},
		
		_parseRequestConfig : function(urlStr) {
			
			var r = {
				urlStr : urlStr,
				url : undefined,
				params : {},
				fullUrl : undefined,
				paramsStr : undefined,
				valueName : undefined
			}
		
			
			// 解析url配置，url地址??参数信息
			var ss2 = urlStr.split('??');
			r.url = ss2[0];
			// 解析参数信息，name1=取值地址&name2=取值地址&...name3=取值地址
			if (ss2.length == 2) {
				var param = ss2[1];
				var ss3 = param.split('&');
				// 解析参数地址
				for (var i = 0; i < ss3.length; i++) {
					var ss4 = ss3[i].split('=');
					var name = '';
					var value = '';
					if (ss4.length == 2) {
						name = ss4[0];
						value = ss4[1];
					} else {
						value = ss4[0];
					}
					
					// 根据取值地址取值
					var vv = '';
					$(value).each(function(){
						// 尝试获取值
						var v = $(this).val();
						if (!v) {
							v = $(this).text();
						}
						// 如果没有设置名称，从对象中获取name
						if (name == '') {
							r.params[$(this).attr('name')] = v;
						} else {
							// 否则拼接参数，','隔开
							if (vv.length > 0) {
								vv += ',';
							}
							vv += v;
						}
					});
					// 如果有值，设置值
					if (vv != '' && name != '') {
						r.params[name] = vv;
					}
					
				}
				r.paramsStr = parseJSON2URLParams(r.params);
				if (r.url.indexOf('?') == -1) {
					r.url += '?';
				} else {
					r.url += '&';
				}
				r.fullUrl += r.paramsStr;
			}
				
			return r;
			
		}
	},

	window.ajaxLoader = new AjaxLoader();
	
	function parseJSON2URLParams (json) {
		var jsonStr = '';
		for (var i in json) {
			var v = json[i];
			if (jsonStr.length != 0) {
				jsonStr += '&'
			}
			jsonStr += i + '=' + v;
		}
		return jsonStr;
	}
})();
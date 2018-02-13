//ueditor
				$(".work_text").each(function(){
					if($.trim($(this).val())!=''){
						initUEditor(this);
					}
				});
					
				$(".work_text").on("focus", function(){
						initUEditor(this);
						$(".edui-default").removeClass("error");
				});
					
				
				//控制textarea文本高度
			    $.fn.extend({
			        textareaAutoHeight: function (options) {
			            this._options = {
			                minHeight: 0,
			                maxHeight: 1000
			            },
			
			            this.init = function () {
			                for (var p in options) {
			                    this._options[p] = options[p];
			                }
			                if (this._options.minHeight == 0) {
			                    this._options.minHeight=parseFloat($(this).height());
			                }
			    			
			    			for (var p in this._options) {
			                    if ($(this).attr(p) == null) {
			                        $(this).attr(p, this._options[p]);
			                    }
			                }
			    			if($(this).prop("scrollHeight") <= this._options.minHeight) {
			    				$(this).height(this._options.minHeight);
			    			} else if($(this).prop("scrollHeight") >= this._options.maxHeight){
			    				$(this).height(this._options.maxHeight);
			    			}else{
			    				$(this).height($(this).prop("scrollHeight"));
			   				}
			    			
			               
			                $(this).keyup(this.resetHeight).change(this.resetHeight)
			                .focus(this.resetHeight);
			            },
			            this.resetHeight = function () {
			                var _minHeight = parseFloat($(this).attr("minHeight"));
			                var _maxHeight = parseFloat($(this).attr("maxHeight"));
			
			                if (!$.browser.msie) {
			                    $(this).height(0);
			                }
			                var h = parseFloat(this.scrollHeight);
			                h = h < _minHeight ? _minHeight :
			                            h > _maxHeight ? _maxHeight : h;
			                $(this).height(h).scrollTop(h);
			                if (h >= _maxHeight) {
			                    $(this).css("overflow-y", "scroll");
			                }
			                else {
			                    $(this).css("overflow-y", "hidden");
			                }
			            },
			            this.init();
			        }
			    });
			    
				function initUEditor(dom){
					var $ueDom = $(dom);
					var hasEditor = $ueDom.attr("hasEditor");
					if(hasEditor !== '1'){
						var domId = $ueDom.attr("id");
						var maxlength = $ueDom.attr("maxlength");
						if(maxlength == undefined || maxlength==null || maxlength==0){
							maxlength = 10000;
						}
						var ue = UE.getEditor(domId,{
							zIndex:10,
							maximumWords:maxlength,
							wordCount: true,
							elementPathEnabled: false,
							initialFrameWidth: 720,
							initialFrameHeight:150,
							autoHeightEnabled:true
						 });
						//ue.render(dom);
				   		ue.addListener('blur', function(){
				        	var cont = this.getContentTxt();
				        	if(cont == null || $.trim(cont) == "" || cont == "undefined") {
				        		this.destroy();
				        		$ueDom.attr("hasEditor","0");
				        		$ueDom.attr("style","").show();
				        	} else {
				        		this.sync();
				        	}
				    	});
				    	ue.addListener('ready', function() {
				    		$ueDom.attr("hasEditor","1");
					        this.focus(); //编辑器家在完成后，让编辑器拿到焦点
					 	});
					}
				    $(".inputBox.edui-default").css("border", "none");
				}
				//end ueditor
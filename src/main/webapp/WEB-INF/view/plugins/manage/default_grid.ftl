<!DOCTYPE html>
<html>
	<head>
		<title></title>
		<#include "/common/include/res.ftl" >
		
		<script>
			$(function(){
				window.grid = new Grid();
				grid.init();
			});
		</script>
	</head>
	<body>
	
		<#include "/common/include/header.ftl" >
		
		<!--main start -->
		<section class="container mt-sm25 mt-xs-15">
			<div class="row">
			
				<#include "/common/include/left.ftl" >
				
				<div class="te-md-8 mt-xs-10">
					<div class="center_rigt">
						<div id="listPage" class="boxClear">
							<div class="mod_title1">
								<div id="menus_div" class="pull-right work_qxbtn">
									<a href="javascript:;" style="display:none;" id="add_btn"><i class="iconfont icon-tianjia"></i>添加</a>
									<a href="javascript:;" style="display:none;" id="del_btn"><i class="iconfont icon-weibiaoti--"></i>删除</a>
								</div>
						    	<h3 class="pull-left" id="title"></h3>
						    </div>
							
							<div id="table_search"></div>
							
						    <div class="work_qxbox">
						    	<div class="work_qxlist">
						    		<ul id="grid" class="clearfix"></ul>
						    	</div>
								<div id="page" ></div>
						    </div>
						</div>
						<div id="savePage" style="display:none;" class="boxClear">
						</div>
					</div>
				</div>
			</div>
		</section>
	    <!-- main end -->
		<#include "/common/include/footer.ftl" >
	</body>
</html>

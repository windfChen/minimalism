<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>提示信息</title>
	<link type="text/css" rel="stylesheet" href="${rc.contextPath}/resources/common/css/base.css" />
	<link type="text/css" rel="stylesheet" href="${rc.contextPath}/resources/common/css/info.css" />
	<script>
		var _basePath = "${rc.contextPath}";
	</script>
</head>
<body>
	<div class="header">
	  <div class="up"></div>
	  <div class="title_text">
		<div class="title_name" style="color:#448aca"><img src="${rc.contextPath}/resources/common/images/point.gif" align="absmiddle">&nbsp;&nbsp;<strong>提示信息</strong></div>
	  </div>
	</div>
	<div class="pay_message">
		<div class="messageBox">
			<div class="blue_box border1p">
				<div class="bgwhite clearfix">
					<div class="alert_box clearfix">
						<span class="icon_success"></span>
						<div class="fl">
							<p class="main_word" style="width: 656px">${message}</p>
							<p class="sub_word" style="margin-top:15px;">
								<a href="${sureHref}">确定</a>
							</p>
						</div>
					</div>

				</div>


			</div>
			<div class="boxBottom"></div>
		</div>
	</div>
</body>
</html>
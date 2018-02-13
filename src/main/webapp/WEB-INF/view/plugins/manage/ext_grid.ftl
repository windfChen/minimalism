<!DOCTYPE html>
<html>
	<head>
		<title>管理端表格</title>
		<meta charset="utf-8" />
		<script type="text/javascript">
			var Sys = {};
			var ua = navigator.userAgent.toLowerCase();
			var s;
			(s = ua.match(/msie ([\d.]+)/)) ? Sys.ie = s[1] : (s = ua
					.match(/firefox\/([\d.]+)/)) ? Sys.firefox = s[1] : (s = ua
					.match(/chrome\/([\d.]+)/)) ? Sys.chrome = s[1] : (s = ua
					.match(/opera.([\d.]+)/)) ? Sys.opera = s[1] : (s = ua
					.match(/version\/([\d.]+).*safari/)) ? Sys.safari = s[1]
					: 0;
			//以下进行测试
			if(Sys.ie > '7.0') {
				document.write("<meta http-equiv='X-UA-Compatible' content='IE=EmulateIE8' >");
			}
			
			var basePath = '${rc.contextPath}';
			var resourceBasePath = '${rc.contextPath}/resources/';
			var queryString = '${data.queryString}';
			queryString = queryString == ''? '': '?' + queryString;
		</script>
		<link rel="stylesheet" type="text/css" href="${rc.contextPath}/resources/plugins/manage/css/ext_grid.css">
		<link rel="stylesheet" type="text/css" href="${rc.contextPath}/resources/common/plugins/extjs/css/ext-all.css" />
		<link rel="stylesheet" type="text/css" href="${rc.contextPath}/resources/common/plugins/extjs/css/ext-ext.css" />
		<link rel="stylesheet" type="text/css" href="${rc.contextPath}/resources/common/plugins/extjs/css/whatyExtjs.css" />
		<link rel="stylesheet" type="text/css" href="${rc.contextPath}/resources/common/plugins/extjs/examples/Datetime/datetime.css"></link>
		<link rel="stylesheet" type="text/css" href="${rc.contextPath}/resources/common/plugins/extjs/pub/Multiselect.css" />
		
		<script type="text/javascript" src="${rc.contextPath}/resources/common/plugins/extjs/pub/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="${rc.contextPath}/resources/common/plugins/extjs/pub/ext-all.js"></script>
		<script type="text/javascript" src="${rc.contextPath}/resources/common/plugins/extjs/pub/whatyExtjs.js?version=1"></script>
		<script type="text/javascript" src="${rc.contextPath}/resources/common/plugins/extjs/examples/Datetime/Datetime.js"></script>
		<script type="text/javascript" src="${rc.contextPath}/resources/common/plugins/extjs/pub/ext-lang-zh_CN.js"></script> 
		<script type="text/javascript" src="${rc.contextPath}/resources/common/plugins/extjs/pub/ajax.js"></script>
		<script type="text/javascript" src="${rc.contextPath}/resources/common/plugins/extjs/pub/DDView.js"></script>
		<script type="text/javascript" src="${rc.contextPath}/resources/common/plugins/extjs/pub/Multiselect.js"></script>
		<script type="text/javascript" src="${rc.contextPath}/resources/plugins/manage/js/ext_grid.js"></script>
		
		<style>
			.ext-safari .x-toolbar .x-form-text, .ext-chrome .x-toolbar .x-form-text {
				height: 14px!important;
			}
		</style>
	</head>
	<body id="main_content" leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0" style="background-color:#ffffff;">
		<table width="100%" height="100%">
			<tr>
				<td width="1%"></td>
				<td valign="top">
					<div id="user-defined-content"></div>
					<div id="searchtool"></div>
					<div id="model-grid"></div>
					<div id="note"></div>
					<div id="exportexcel"></div>
				</td>
				<td width="1%"></td>
			</tr>
		</table>
	</body>
</html>

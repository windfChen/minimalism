<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String cloudPath = request.getContextPath();
	String cloudBasePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+cloudPath+"/";
%>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <title>云盘附件选择</title>
    <script type="text/javascript" src="../internal.js"></script>
    <script type="text/javascript" src="/resource/common/js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="/resource/common/js/jquery-migrate-1.1.0.js"></script>
    <script type="text/javascript">
    	var platformBasePath = "<%=cloudBasePath %>";
    </script>
</head>
<body>
<div style="width:710px;height:540px" >
    <iframe src="http://yunpan.sdk.webtrn.cn/cloud_api/sdk_login?notice=<%=cloudBasePath %>UEditor/dialogs/cloudupload/cloud_callback.jsp?action=cloudupload" width="100%" height="100%" frameborder="0"></iframe>
</div>
<script type="text/javascript" src="cloudupload.js"></script>
</body>
</html>
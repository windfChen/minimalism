<%@ page language="java" import="java.util.*,com.whaty.commons.ueditor.ActionEnter" pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding( "utf-8" );
	String rootPath = application.getRealPath("/");
	String cloudPath = request.getContextPath();
	String cloudBasePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+cloudPath+"/";
	String cloudFileUrl = new ActionEnter( request, rootPath ).exec();
	if (cloudFileUrl == null) {
		cloudFileUrl = "";
	}
	String path = (String)request.getParameter("path");
%>

<script type="text/javascript" src="/resource/common/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="/resource/common/js/jquery-migrate-1.1.0.js"></script>
<script type="text/javascript">
    var cloudFileUrl = "<%=cloudFileUrl %>";
    var cloudFileName = "<%=path %>";
	var success = parent.insertCloudFile(cloudFileUrl, cloudFileName);
	if (!success) {
		window.history.back();
	}
</script>
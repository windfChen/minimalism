/**
 * User: Zhoudonghua
 * Date: 16-07-19
 * Time: 下午13:50
 * 云盘图片选择
 */

function cloudDialogClose(){
	dialog.close();
}

function insertCloudFile(fileUrl, fileName){
	if (fileUrl != null && fileUrl != "") {
		var list = [];
		var ext = fileUrl.substr(fileUrl.lastIndexOf('.') + 1);
		var name = "";
		if (fileName == undefined || fileName == "") {
			name = fileUrl.substr(fileUrl.lastIndexOf('/') + 1);
		} else {
			name = fileName.substr(fileName.lastIndexOf('/') + 1);
		}
		/* 检查是否是可上传的图片 */
		var acceptImageExtensions = (editor.getOpt('imageAllowFiles') || []).join('').replace(/\./g, ',').replace(/^[,]/, '');
        if (acceptImageExtensions.indexOf(ext.toLowerCase()) >= 0) {
            list.push({
                src: fileUrl,
                alt: name
            });
    		editor.execCommand('insertimage', list);
        } else {
        	 /* 检查是否是可上传的其它文件 */
        	 var acceptFileExtensions = (editor.getOpt('fileAllowFiles') || []).join('').replace(/\./g, ',').replace(/^[,]/, '');
        	 if (acceptFileExtensions.indexOf(ext.toLowerCase()) >= 0) {
        		 fileUrl = platformBasePath + "course/study/learnResourseDownload_downloadRemoteFile.action?longFileName="
        		    + encodeURIComponent(name)
        		    +"&longUrl="+encodeURIComponent(fileUrl);
                 list.push({
                     title: name,
                     url: fileUrl
                 });
         		editor.execCommand('insertfile', list);
             } else {
            	 alert("允许上传的文件后缀有:" + acceptFileExtensions + ".");
            	 return false;
             }
        }
        cloudDialogClose();
        return true;
	}
}
function pageJumpInputFocus(obj) {
	$(obj).parent().addClass('pagin-jump-focus').removeClass('pagin-jump');
}
function pageJumpInputBlur(obj) {
	if ($(obj).val().length == 0) {
		$(obj).parent().addClass('pagin-jump').removeClass('pagin-jump-focus');
	}
}
function pageJumpInputKeyUp(obj) {
	if (event.keyCode == "13") {
		$('#pagin_jump_btn').click();
	}
}
function pageJump(pageIndex, currentIndex, indexTotal) {
	if (!pageIndex) {
		return false;
	}
	if (pageIndex < 1) {
		pageIndex = 1;
	}
	if (pageIndex > indexTotal) {
		pageIndex = indexTotal;
	}
	
	if (currentIndex == pageIndex) {
		return false;
	}
	
	onPageJump(pageIndex);
}
function getPageHtml(currentPage, total, pageSize) {
	// 设置分页
	var pageTotal = parseInt(total / pageSize) + (total % pageSize == 0? 0: 1);
	
	var indexCount = 8;
	var indexTotal = pageTotal;
	var startIndex = 1;
	var endIndex = pageTotal ;
	var frontMore = false;
	var nextMore=false;
	
	// 总页数大于要显示的页数
	if (indexTotal > indexCount) {
		startIndex = currentPage - indexCount / 2 + 1;
		endIndex = currentPage + indexCount / 2
		
		// 首尾超出后移动
		if (startIndex < 1) {
			endIndex = endIndex + (1 - startIndex);
			startIndex = 1;
		}
		if ( endIndex > indexTotal) {
			startIndex = startIndex + (indexTotal - endIndex);
			endIndex = indexTotal
		}
		
		// 首尾不够的情况下，显示...
		if (startIndex > 1) {
			startIndex = startIndex + 1;
			frontMore = true;
		}
		if (endIndex < indexTotal) {
			endIndex = endIndex - 1;
			nextMore = true;
		}
		
		
	}
	
	var pageData = {
		total : total,
		currentPage : total,
		startIndex : startIndex,
		endIndex : endIndex,
		indexTotal : indexTotal,
		pageTotal : pageTotal,
		frontPage : (currentPage > 1?currentPage*1 - 1:1),
		nextPage : (currentPage < pageTotal ?currentPage*1 + 1:pageTotal)
	};
	
	var b = '<span class="pagin-ell" style="padding-right: 20px;">共' + total + '条</span>';
	b += '<a class="pagin-link" href="javascript:void(0);" onclick="searchPage(' + pageData.frontPage + ')"><i class="library icon-dArrleft"></i> 上一页</a>';
	if (frontMore) {
		b += '<a class="pagin-link" href="javascript:void(0);" onclick="searchPage(1)">1</a><span class="pagin-ell">';
		b += '<span class="pagin-ell">...</span>';
	}
	
	for (var i = startIndex; i <= endIndex; i++) {
		b += '<a class="pagin-link ' + (currentPage == i? 'pagin-cur': '') + '" href="javascript:void(0);" onclick="searchPage(' + i + ')">' + i + '</a><span class="pagin-ell">';
	}
	
	if (nextMore) {
		b += '<span class="pagin-ell">...</span>';
		b += '<a class="pagin-link" href="javascript:void(0);" onclick="searchPage(' + indexTotal + ')">' + indexTotal + '</a><span class="pagin-ell">';
	}
	b += '<a class="pagin-link" href="javascript:void(0);" onclick="searchPage(' + pageData.nextPage + ');">下一页 <i class="library icon-dArrright"></i></a>';
	b += '<span class="pagin-txt">到</span><span class="pagin-jump"><input class="pagin-input" title="输入回车跳转" onkeyup="searchPageJump(this.value, ' + pageTotal + ')" type="text"><a class="pagin-btn" id="paginJump_btn" href="javascript:void(0);">确定</a></span><span class="pagin-txt">页</span>';
	
	
	return b;
}

function searchPageJump(value, pageTotal) {
	var e = event || window.event || arguments.callee.caller.arguments[0];
	if (e.keyCode == "13") {
		if(value < 1 || value > pageTotal) {
			alert('输入不合法');
			return;
		}
		pageNum = value;
		searchPage(pageNum);
	}
}

function searchPage(pageNum) {
	load(pageNum)
}

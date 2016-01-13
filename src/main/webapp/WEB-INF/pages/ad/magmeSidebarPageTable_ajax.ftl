<#import "magmeSidebarPageTable.ftl" as pt>

<script type="text/javascript">
	curPageNum = ${pageNum!"1"};
	pageSize = ${pageSize!"10"};
	pageCount = ${pageBar.totalPageCount!"0"};
</script>
<@pt.main />
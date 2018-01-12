/**
 * 会员信息js
 */

$(function () {
	initialPage();
	getGrid();
});

function initialPage() {
	$(window).resize(function() {
		$('#dataGrid').bootstrapTable('resetView', {height: $(window).height()-54});
	});
}

function getGrid() {
	$('#dataGrid').bootstrapTableEx({
		url: '../../base/member/listData?_' + $.now(),
		height: $(window).height()-54,
		queryParams: function(params){
			params.name = vm.keyword;
			return params;
		},
		columns: [
			{checkbox: true},
			{field : "memberId", title : "会员编号", width : "100px"}, 
			{field : "memberType", title : "会员类型", width : "100px"}, 
			{field : "memberName", title : "会员名", width : "100px"}, 
			{field : "cardId", title : "会员卡号", width : "100px"}, 
			{field : "mobile", title : "银行卡绑定手机号", width : "100px"}, 
			{field : "email", title : "电子邮箱", width : "100px"}, 
			{field : "idCard", title : "身份证号", width : "100px"}, 
			{field : "idCardType", title : "身份证类型(默认1为身份证)", width : "100px"}, 
			{field : "isAvailable", title : "可用标识(0-不可用;1-可用)", width : "100px"}, 
			{field : "remark", title : "备注", width : "100px"},
            {field : "memberAddress", title : "会员地址", width : "100px"},
            {field : "toCorpAddress", title : "商品去向地址", width : "100px"},
			{field : "gmtCreate", title : "创建时间", width : "100px"}, 
			{field : "gmtModified", title : "修改时间", width : "100px"}
		]
	})
}

var vm = new Vue({
	el:'#dpLTE',
	data: {
		keyword: null
	},
	methods : {
		load: function() {
			$('#dataGrid').bootstrapTable('refresh');
		},
		save: function() {
			dialogOpen({
				title: '新增会员信息',
				url: 'base/member/add.html?_' + $.now(),
				width: '420px',
				height: '350px',
				yes : function(iframeId) {
					top.frames[iframeId].vm.acceptClick();
				},
			});
		},
		edit: function() {
			var ck = $('#dataGrid').bootstrapTable('getSelections');
			if(checkedRow(ck)){
				dialogOpen({
					title: '编辑会员信息',
					url: 'base/member/edit.html?_' + $.now(),
					width: '420px',
					height: '350px',
					success: function(iframeId){
						top.frames[iframeId].vm.memberInfo.id = ck[0].id;
						top.frames[iframeId].vm.setForm();
					},
					yes: function(iframeId){
						top.frames[iframeId].vm.acceptClick();
					}
				});
			}
		},
		remove: function() {
			var ck = $('#dataGrid').bootstrapTable('getSelections'), ids = [];	
			if(checkedArray(ck)){
				$.each(ck, function(idx, item){
					ids[idx] = item.id;
				});
				$.RemoveForm({
					url: '../../base/member/remove?_' + $.now(),
			    	param: ids,
			    	success: function(data) {
			    		vm.load();
			    	}
				});
			}
		}
	}
})
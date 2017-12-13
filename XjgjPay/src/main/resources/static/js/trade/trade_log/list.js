/**
 * 交易记录js
 */

$(function () {
    initialPage();
    getGrid();
});

function initialPage() {
    $(window).resize(function () {
        $('#dataGrid').bootstrapTable('resetView', {height: $(window).height() - 54});
    });
}

function getGrid() {
    $('#dataGrid').bootstrapTableEx({
        url: '../../trade/trade_log/listData?_' + $.now(),
        height: $(window).height() - 54,
        queryParams: function (params) {
            params.name = vm.keyword;
            return params;
        },
        columns: [
            {checkbox: true},
            {field: "transSn", title: "交易流水号", width: "100px"},
            {field: "transType", title: "交易类型", width: "100px"}, //(1-充值;2-提款;3-消费;4-转账;5-收款)
            {field: "sellerOrderId", title: "商户订单号", width: "100px"},
            {field: "amtMoney", title: "交易金额", width: "100px"},
            {field: "payModeId", title: "支付方式", width: "100px"}, //(1-宝付;2-西郊结算系统)
            {field: "bankAccName", title: "银行账户姓名", width: "100px"},
            {field: "bankAccCard", title: "银行卡号", width: "100px"},
            {field: "bankCode", title: "发卡行编号", width: "100px"},
            {field: "bfBindId", title: "宝付绑定标识号", width: "100px"},
            {field: "remark", title: "备注", width: "100px"},
            {field: "gmtCreate", title: "交易时间", width: "100px"}
        ]
    })
}

var vm = new Vue({
    el: '#dpLTE',
    data: {
        keyword: null
    },
    methods: {
        load: function () {
            $('#dataGrid').bootstrapTable('refresh');
        }
    }
})
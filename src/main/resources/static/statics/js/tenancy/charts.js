const topVolumeChart = echarts.init($('#topVolumeChart')[0]);
const topAmountChart = echarts.init($('#topAmountChart')[0]);
const salesSituationChart = echarts.init($('#salesSituationChart')[0]);

function init() {
    loadSalesSituationChart();
    loadTopAmountChart();
    loadTopVolumeChart();
    loadTotalSalesSituation();
    loadTodaySalesSituation();
}

const data = {
    volumeChartParam: {
        startDate: addDate(new Date(), -30, '-'),
        endDate: dateFmt("yyyy-MM-dd", new Date()),
        x: 10,
    },
    amountChartParam: {
        startDate: addDate(new Date(), -30, '-'),
        endDate: dateFmt("yyyy-MM-dd", new Date()),
        x: 10,
    },
    situationChartParam: {
        startDate: addDate(new Date(), -30, '-'),
        endDate: dateFmt("yyyy-MM-dd", new Date()),
    },
    salesSituationParam: {
        startDate: addDate(new Date(), -30, '-'),
        endDate: dateFmt("yyyy-MM-dd", new Date())
    }
};

const methods = {
    loadSalesSituationChart,
    loadTopAmountChart,
    loadTopVolumeChart,
    loadTotalSalesSituation,
    loadTodaySalesSituation
};

const chartApp = new Rmj({
    el: "#chart-app",
    data: data,
    methods: methods,
    init: init
});

// 窗口改变时调整图表大小
window.onresize = adjust;
function adjust(){
    salesSituationChart.resize();
    topAmountChart.resize();
    topVolumeChart.resize();
}

function loadTopVolumeChart() {
    $.ajax({
        url: '/api/charts/topSalesVolume',
        data: data.volumeChartParam,
        success: function (res) {
            if (res.code == 0) {
                let option = res.option;
                topVolumeChart.setOption(option);
            } else {
                handleAjax(res);
            }
        }
    });
}
function loadTopAmountChart() {
    $.ajax({
        url: '/api/charts/topSalesAmount',
        data: data.amountChartParam,
        success: function (res) {
            if (res.code == 0) {
                let option = res.option;
                topAmountChart.setOption(option);
            } else {
                handleAjax(res);
            }
        }
    });
}

function loadSalesSituationChart() {
    $.ajax({
        url: '/api/charts/sellSituation',
        data: data.situationChartParam,
        success: function (res) {
            if (res.code == 0) {
                let option = res.option;
                salesSituationChart.setOption(option);
            } else {
                handleAjax(res);
            }
        }
    });
}

function loadTotalSalesSituation() {
    $.ajax({
        url: '/api/charts/totalSalesSituation',
        data: data.salesSituationParam,
        success: function (res) {
            if (res.code == 0) {
                $('#totalVolume').text(res.data.salesVolume);
                $('#totalAmount').text(formatMoney(res.data.salesAmount));
            } else {
                handleAjax(res);
            }
        }
    });
}

function loadTodaySalesSituation() {
    $.ajax({
        url: '/api/charts/todaySalesSituation',
        success: function (res) {
            if (res.code == 0) {
                $('#todayVolume').text(res.data.salesVolume);
                $('#todayAmount').text(formatMoney(res.data.salesAmount));
            } else {
                handleAjax(res);
            }
        }
    });
}


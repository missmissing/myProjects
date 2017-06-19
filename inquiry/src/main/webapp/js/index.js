var myChart = echarts.init(document.getElementById('content'));
myChart.showLoading();
myChart.hideLoading();
myChart.setOption(
    option = {
        title : {
            text: '平台用户注册数',
            x:'center'
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross'

            }
        },
        toolbox: {
            feature: {
                dataView: {show: true, readOnly: false},
                magicType: {show: true, type: ['line', 'bar']},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        legend: {
            data:['平台用户注册数']
        },
        xAxis: [
            {
                type: 'category',
                data: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
            }
        ],
        yAxis: [
            {
                type: 'value',
                name: '注册数',
                min: 0,
                max: 100,
                interval: 20,
                axisLabel: {
                    formatter: '{value} '
                }
            }
        ],
        series: [
            {
                name:'用户注册数',
                type:'bar',
                data:[2.0, 4.0, 5.0, 8.0, 16.0, 20.0, 28.0, 32.0, 36.0, 42.0, 59.0, 80.0]
            },
            {
                name:'用户注册数',
                type:'line',
                yAxisIndex: 0,
                data:[2.0, 4.0, 5.0, 8.0, 16.0, 20.0, 28.0, 32.0, 36.0, 42.0, 59.0, 80.0]
            }
        ]
    });

var myChartB = echarts.init(document.getElementById('contentB'));
myChartB.showLoading();
myChartB.hideLoading();
myChartB.setOption(
     option = {
         title : {
             text: '平台用户查询分类',
             x:'center'
         },
         tooltip : {
             trigger: 'item',
             formatter: "{a} <br/>{b} : {c} ({d}%)"
         },
         legend: {
             x : 'center',
             y : 'bottom',
             data:['公积金','社保','职业信息','学历学籍','失信人信息']
         },
         toolbox: {
             show : true,
             feature : {
                 mark : {show: true},
                 dataView : {show: true, readOnly: false},
                 magicType : {
                     show: true,
                     type: ['pie', 'funnel']
                 },
                 restore : {show: true},
                 saveAsImage : {show: true}
             }
         },
         calculable : true,
         series : [
             {
                 name:'分类',
                 type:'pie',
                 radius : [30, 110],
                 center : ['50%', '50%'],
                 roseType : 'area',
                 data:[
                     {value:10, name:'公积金'},
                     {value:5, name:'社保'},
                     {value:15, name:'职业信息'},
                     {value:25, name:'学历学籍'},
                     {value:20, name:'失信人信息'}
                 ]
             }
         ]
     });


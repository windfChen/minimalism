
// 基于准备好的dom，初始化echarts实例
var myChart6 = echarts.init(document.getElementById('main6'));

// 指定图表的配置项和数据
    option = {
tooltip : {
    trigger: 'axis',
axisPointer : {            // 坐标轴指示器，坐标轴触发有效
type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
    }
},
legend: {
    data: ['教授论文', '副教授论文','讲师论文','其他发表论文']
},
grid: {
    left: '3%',
right: '4%',
bottom: '3%',
    containLabel: true
},
xAxis:  {
    type: 'value'
},
yAxis: {
    type: 'category',
data: ['名办高校','专科院校','本科院校','未立党高校']
},
series: [
    {
        name: '教授论文',
type: 'bar',
stack: '总量',
label: {
    normal: {
        show: true,
        position: 'insideRight'
        }
    },
    data: [340, 340, 340, 340]
},
{
    name: '副教授论文',
type: 'bar',
stack: '总量',
label: {
    normal: {
        show: true,
        position: 'insideRight'
        }
    },
    data: [120, 132, 101, 100]
},
{
    name: '讲师论文',
type: 'bar',
stack: '总量',
label: {
    normal: {
        show: true,
        position: 'insideRight'
        }
    },
    data: [220, 182, 191, 201]
},
{
    name: '其他发表论文',
type: 'bar',
stack: '总量',
label: {
    normal: {
        show: true,
        position: 'insideRight'
                }
            },
            data: [150, 212, 201 ,180]
        }
        
    ]
};

        // 使用刚指定的配置项和数据显示图表。
myChart6.setOption(option);
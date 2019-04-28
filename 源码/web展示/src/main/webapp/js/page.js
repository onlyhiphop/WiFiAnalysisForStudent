// 定义数据名，和当前的url,默认
var dataContent = "", url = "/month.action", sendData;

//页面初始化
init();

function init() {
    // 设置时间函数
    setTime();

    //绑定事件函数
    bindEvent();
}


function setTime() {
    var nowTime = new Date(),
        year = nowTime.getFullYear(),
        month = nowTime.getMonth() + 1;
    // 添加前六年年份
    for (var i = 0; i < 6; i++) {
        $('#year option:nth-child(' + (i + 1) + ')').html(year - i);
        $('#year option:nth-child(' + (i + 1) + ')').attr("value",year - i)
    }
    // 判断当前是否为1月份
    if(month == 1) {
        //变为上一年12月份
        month = 12;
        // 变为上一年
        console.log(year - 1);
        $('#year').val(year - 1);
    }
    //默认为上个月
    $('#month').val(month - 1);
}

//绑定操作事件
function bindEvent() {
    // 点击导航栏切换选中选项及改变图表
    $(".left-nav").on("click", "li", function () {
        //图表切换
        $(this).addClass("checked").siblings().removeClass("checked");
        var index = $('.left-nav li').index($(this));
        $('.content > div:nth-child(' + (index + 1) + ')').removeClass("right-box").addClass("now-box")
            .siblings(".now-box").removeClass('now-box').addClass("right-box");



        // if($(this) == $('.left-nav ul li:nth-child(1)')){
        //     console.log("第一块");
        //     // 如果为第一个选项，则url为
        //     url = "/MonthServlet";
        //     //调用createChart方法，创建图表
        //     // createChart(url, sendData);
        // }else if($(this) == $('.left-nav ul li:nth-child(2)')){
        //     alert("执行");
        //     //如果为第二个选项，则url为
        //     console.log("第二块")
        // }

        month_check = $('.left-nav > li:nth-child(1)').hasClass('checked');
        img_check = $('.left-nav > li:nth-child(2)').hasClass('checked');

        if(month_check){
            url = '/month.action';
        }
        if(img_check){
            url = '/stuimg.action';
            $('#month').remove();
        }
        console.log(url)
    });

    var month_check;
    var img_check;

        //点击搜索
    $('.time-search button, .number-search button').on("click", function () {
    	sendData = {
        		"id":$('.number-search input').val(),
                "year":$("#year").val(),
                "month": $("#month").val()
            };
    	console.log(sendData);
    	if(img_check){
    	    createImgChart(url, sendData);
    	    return;
        }
        createMonthChart(url, sendData);
    })
}

function createImgChart(url, data){
    //人物画像
    var nowChartBox2 = $('.content > div:nth-child(2)');
    var img_box1 = nowChartBox2.find('.img_box1');
    var img_box2 = nowChartBox2.find('.img_box2');
    var img_box3 = nowChartBox2.find('.img_box3');
    var img_box4 = nowChartBox2.find('.img_box4');

    var img_chart1 = echarts.init(img_box1[0]);
    var img_chart2 = echarts.init(img_box2[0]);
    var img_chart3 = echarts.init(img_box3[0]);
    var img_chart4 = echarts.init(img_box4[0]);


    var fn = function (data) {
        console.log(data);
        dataContent=JSON.parse(data);
        console.log(dataContent);
        //人物画像部分
        var consum = dataContent.studentImage.consumption_ability;
        var name = dataContent.studentInfo.name;
        var id = dataContent.studentInfo.id;
        var phone = dataContent.studentInfo.phone;

        document.getElementById("u_consume").innerHTML = consum;
        document.getElementById('u_name').innerHTML = name;
        document.getElementById('u_id').innerHTML = id;
        document.getElementById('u_phone').innerHTML = phone;

        createPie_hobbies(img_chart1, dataContent);
        createPie_stayaddr(img_chart2, dataContent);
        createPie_goaddr(img_chart3, dataContent);
        createBar_ds(img_chart4, dataContent);
    };

    $.post(url,data,fn,"text");
}


function createMonthChart(url, data) {
    //取出被选中的页面
    var nowChartBox = $('.content > div:nth-child(1)');
    // 把原来的图表去除
    // nowChartBox.find('.box1').html("");
    // nowChartBox.find('.box2').html("");
    //容器装图表
    var box1 = nowChartBox.find('.box1');
    var box2 = nowChartBox.find('.box2');
    var box3 = nowChartBox.find('.box3');
    var box4 = nowChartBox.find('.box4');
    var box5 = nowChartBox.find('.box5');

    //调用echart插件，初始化图表
    var myChart1 = echarts.init(box1[0]);
    var myChart2 = echarts.init(box2[0]);
    var myChart3 = echarts.init(box3[0]);
    var myChart4 = echarts.init(box4[0]);

    var myChart5 = echarts.init(box5[0]);


    //判断选中的选项
    var fn=function(data){
        // 判断是否为string
        console.log(data);
        dataContent=JSON.parse(data);
        console.log(dataContent);
        //成功调用的函数，dataContent为获取的数据,myChart为创建的图表对象
        createLine(myChart1, dataContent, "time");
        createPie(myChart2, dataContent, "time");
        createLine(myChart3, dataContent, "go_times");
        createPie(myChart4, dataContent, "go_times");

        createBar_ds(myChart5, dataContent)
    };
    $.post(url,data,fn,"text");

}


function createLine(myChart, dataContent, rule) {
    var y_data = "";
    var display = "";
    var title_name = "";
    if (rule == "time") {
        y_data = dataContent.addr_data.totals;
        display = "停留时间/min"
        title_name = "各地点停留时间"
    }
    if (rule == "go_times") {
        y_data = dataContent.addr_data.goes;
        display = "次数"
        title_name = "各地点出现次数"
    }
    console.log(dataContent);
    var option = {
        title: {
            text: title_name,
            left: 'center',
            top: 20,
            textStyle: {
                color: '#636363'
            }
        },
        toolbox: {
            show: true,
            feature: {
                saveAsImage: {}
            }
        },
        color: ['#3398DB'],
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis : [
            {
                type : 'category',
                data : dataContent.addr_data.addrs,
                axisTick: {
                    alignWithLabel: true
                }
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        series : [
            {
                name:display,
                type:'bar',
                barWidth: '45%',
                data: y_data
            }
        ]
    };
    myChart.setOption(option);
}


function createPie(myChart, dataContent, rule) {
    var y_data = "";
    var title_name = "";
    if (rule == "time") {
        y_data = dataContent.addr_data.totals;
        title_name = "各地点停留时间"
    }
    if (rule == "go_times") {
        y_data = dataContent.addr_data.goes;
        title_name = "各地点出现次数"
    }

    var ags = [];
    var addrs = dataContent.addr_data.addrs;

    for (var i = 0;i < addrs.length;i++){
        var ag = {value:y_data[i], name:addrs[i]};
        ags[i]=ag;
    }

    var option = {
        backgroundColor: '#2c343c',
    
        title: {
            text: title_name,
            left: 'center',
            top: 20,
            textStyle: {
                color: '#ccc'
            }
        },
        toolbox: {
            show: true,
            feature: {
                saveAsImage: {}
            }
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
    
        visualMap: {
            show: false,
            min: 80,
            max: 600,
            inRange: {
                colorLightness: [0, 1]
            }
        },
        series : [
            {
                name:'访问来源',
                type:'pie',
                radius : '55%',
                center: ['50%', '50%'],
                data:ags.sort(function (a, b) { return a.value - b.value; }),
                roseType: 'radius',
                label: {
                    normal: {
                        textStyle: {
                            color: 'rgba(255, 255, 255, 0.3)'
                        }
                    }
                },
                labelLine: {
                    normal: {
                        lineStyle: {
                            color: 'rgba(255, 255, 255, 0.3)'
                        },
                        smooth: 0.2,
                        length: 10,
                        length2: 20
                    }
                },
                itemStyle: {
                    normal: {
                        color: '#c23531',
                        shadowBlur: 200,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                },
    
                animationType: 'scale',
                animationEasing: 'elasticOut',
                animationDelay: function (idx) {
                    return Math.random() * 200;
                }
            }
        ]
    };
    myChart.setOption(option);
}

function createPie_hobbies(myChart, dataContent){
    var hobbies_time = dataContent.studentImage.hobbies.split('-');
    var hobbies = [];
    var format_data = [];
    for (var i = 0; i < hobbies_time.length; i++){
        var item = hobbies_time[i];
        var hname = item.split(':')[0];
        var htime = item.split(':')[1];
        hobbies.push(hname);
        var dic = {value:htime, name:hname};
        format_data.push(dic);
    }
    console.log(hobbies);
    console.log(format_data);

    var option = {
        toolbox: {
            show: true,
            feature: {
                saveAsImage: {}
            }
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b}: {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            x: 'left',
            data:hobbies
        },
        series: [
            {
                name:'访问来源',
                type:'pie',
                radius: ['50%', '70%'],
                avoidLabelOverlap: false,
                label: {
                    normal: {
                        show: false,
                        position: 'center'
                    },
                    emphasis: {
                        show: true,
                        textStyle: {
                            fontSize: '30',
                            fontWeight: 'bold'
                        }
                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    }
                },
                data:format_data
            }
        ]
    };
    myChart.setOption(option);
}

function createPie_stayaddr(myChart, dataContent){
    var addr_time = dataContent.studentImage.favorite_stay_place.split('-');
    var format_data = [];
    for (var i = 0; i < addr_time.length; i++){
        var s = addr_time[i].split(':');
        s[1] = Number(s[1]);
        format_data.push(s);
    }
    var option = {
        legend: {},
        tooltip: {},
        toolbox: {
            show: true,
            feature: {
                saveAsImage: {}
            }
        },
        dataset: {
            source:format_data
        },
        series: [{
            type: 'pie',
            radius: 60,
            center: ['50%', '50%']
            // No encode specified, by default, it is '2012'.
        }]
    };
    myChart.setOption(option);
}



function createPie_goaddr(myChart, dataContent) {
    var go_data = dataContent.studentImage.favorite_go_place.split('-');

    var go_addr = [];
    var format_data = [];

    for (var i = 0; i < go_data.length; i++){
        var s = go_data[i].split(':');
        go_addr.push(s[0]);
        var dic = {value:s[1], name:s[0]};
        format_data.push(dic);
    }
    console.log(format_data);
    var option = {
        title : {
            text: '各地点访问次数',
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        toolbox: {
            show: true,
            feature: {
                saveAsImage: {}
            }
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: go_addr
        },
        series : [
            {
                name: '访问次数',
                type: 'pie',
                radius : '55%',
                center: ['50%', '50%'],
                data:format_data,
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
    myChart.setOption(option);

}

function createBar_ds(myChart, dataContent){
    var day = [0, 0.5, 1, 1.5, 2, 2.5, 3, 3.5, 4, 4.5, 5, 5.5, 6, 6.5, 7, 7.5, 8, 8.5, 9, 9.5, 10, 10.5, 11, 11.5, 12, 12.5,
        13,
        13.5, 14, 14.5, 15, 15.5, 16, 16.5, 17, 17.5, 18, 18.5, 19, 19.5, 20, 20.5, 21, 21.5, 22, 22.5, 23, 23.5];

    var ds_time = dataContent.dsBean.active_time.split('-');
    var format_data = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]

    for(var i = 0; i <ds_time.length; i++){
        var t = ds_time[i].split(':')[0];
        var c = ds_time[i].split(':')[1];
        var ind = day.indexOf(Number(t));
        format_data[ind] = format_data[ind] + Number(c);
    }

    // var morning = format_data.splice(day.indexOf(6), 24);
    // var morning_sort = morning.reverse();
    // var mor_data = morning_sort.splice(0, 5)
    //
    // var night = format_data.splice(day.indexOf(18), 10);
    // var night_sort = night.reverse();
    // var nig_data = night_sort.splice(0, 5)
    //
    // var every_night = format_data.splice(day.indexOf(23), 14);
    // var every_night_sort = every_night.reverse();
    // var ev_nig_data = every_night_sort.splice(0, 5)

    var option = {
        title: {
            text: '24h手机活跃情况',
        },

        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross'
            }
        },
        toolbox: {
            show: true,
            feature: {
                saveAsImage: {}
            }
        },
        xAxis:  {
            type: 'category',
            boundaryGap: false,
            data:day
            },
        yAxis: {
            type: 'value',
            axisLabel: {
                formatter: '{value} 次'
            },
            axisPointer: {
                snap: true
            }
        },
        visualMap: {
            show: false,
            dimension: 0,
            pieces: [{
                lte: 6,
                color: 'green'
            }, {
                gt: 6,
                lte: 8,
                color: 'red'
            }, {
                gt: 8,
                lte: 14,
                color: 'green'
            }, {
                gt: 14,
                lte: 17,
                color: 'red'
            }, {
                gt: 17,
                color: 'green'
            }]
        },
        series: [
            {
                name:'次数',
                type:'line',
                smooth: true,
                data: format_data
                // markArea: {
                //     data: [ [{
                //         name: '早高峰',
                //         xAxis: '07:30'
                //     }, {
                //         xAxis: '10:00'
                //     }], [{
                //         name: '晚高峰',
                //         xAxis: '17:30'
                //     }, {
                //         xAxis: '21:15'
                //     }] ]
                // }
            }
        ]
    };

    myChart.setOption(option);
}
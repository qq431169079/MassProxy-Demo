<template>
    <div id="type-pie">
        <p style="text-align: center">分类统计 <span>{{sum}}</span></p>
        <div id="type-pie-chart">

        </div>
    </div>
</template>

<script>
    import echarts from 'echarts'

    export default {
        name: 'TypePieChart',
        data() {
            return {
                loading: true,
                sum: ''
            }
        },
        methods: {
            initPieChart(res) {
                let myChart = echarts.init(document.getElementById('type-pie-chart'));
                let option = {
                    tooltip: {
                        trigger: 'item',
                        formatter: "{b}<br/>{c}<br/>{d}%"
                    },
                    color: ['#33CC33', '#FEDB5B', '#708090'],
                    series: [
                        {
                            name: '分类统计',
                            type: 'pie',
                            radius: '50%',
                            data: res,
                            itemStyle: {
                                emphasis: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            },
                            label: {
                                textStyle: {
                                    fontSize: 10
                                }
                            }
                        }
                    ]
                };
                myChart.setOption(option)
            }
        },
        mounted() {
            this.$ajax.get('/proxy/countByType').then(res => {
                this.initPieChart(res);
                let sum = 0;
                for (let i = 0; i < 3; i++) {
                    if (res[i].name === 'HTTP' || res[i].name === 'HTTPS') {
                        sum += res[i].value;
                    } else {
                        sum -= res[i].value;
                    }
                }
                this.sum = sum;
                this.loading = false
            })
        }
    }
</script>

<style scoped>
    #type-pie {
        font-size: 13px;
    }

    #type-pie-chart {
        height: 160px;
        width: 200px;
        margin: auto;
    }
</style>

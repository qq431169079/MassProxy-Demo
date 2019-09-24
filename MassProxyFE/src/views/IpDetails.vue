<template>
    <div id="ip-details">
        <t-zoom-in-top>
            <p id="ip-details-title">IP详细数据</p>

            <div id="ip-details-chart"></div>

            <el-row type="flex" justify="center">
                <el-col :span="8">
                    <el-table :data="locTableData" border stripe>
                        <el-table-column prop="n" label="数据" width="140"/>
                        <el-table-column prop="i" label="信息" align="center"/>
                    </el-table>
                </el-col>
                <el-col :span="8" v-if="ipDetails.inProxyDatabase">
                    <el-table border stripe :data="proxyTableData">
                        <el-table-column prop="n" label="数据" width="180"/>
                        <el-table-column prop="i" label="代理信息" align="center"/>
                    </el-table>
                </el-col>
            </el-row>
        </t-zoom-in-top>
    </div>
</template>

<script>
    import echarts from 'echarts'

    export default {
        name: 'IpDetails',
        data() {
            return {
                loading: true,
                ipDetails: {
                    ip: '',
                    countryCode: '',
                    country: '',
                    region: '',
                    city: '',
                    latitude: 0,
                    longitude: 0,
                    inProxyDatabase: false,
                    port: null,
                    type: null,
                    connectTime: null,
                    responseTime: null,
                    score: null,
                    lastCheckTime: null,
                    recordTime: null,
                },
                locTableData: [],
                proxyTableData: []
            }
        },
        methods: {
            getIpDetails(ip) {
                this.$ajax.get('/location/getIpDetails', {
                    ip: ip
                }).then(res => {
                    this.update(res);
                    this.loading = false
                })
            },
            update(data) {
                const type = ['', 'HTTP', 'HTTPS', 'HTTP/HTTPS'];

                this.ipDetails = data;
                this.locTableData = [
                    {n: 'IP地址', i: data.ip},
                    {n: '国家', i: data.countryCode + ' ' + data.country},
                    {n: '地理位置', i: data.region + ' ' + data.city},
                    {n: '中心经度', i: data.longitude},
                    {n: '中心纬度', i: data.latitude},
                    {n: '代理已收录', i: data.inProxyDatabase ? '是' : '否'},
                ];

                if (data.inProxyDatabase) {
                    this.proxyTableData = [
                        {n: '端口', i: data.port},
                        {n: '类型', i: type[data.type]},
                        {n: '连接/响应时间:', i: data.connectTime + 'ms / ' + data.responseTime + 'ms'},
                        {n: '可靠性得分:', i: data.score},
                        {n: '上次检查时间', i: data.lastCheckTime.toString().substr(0, 19)},
                        {n: '收录时间', i: data.recordTime.toString().substr(0, 19)}
                    ];
                }

                this.initChart([data.longitude, data.latitude], data.countryCode + ' ' + data.region + ' ' + data.city);
            },
            initChart(loc, name) {
                let myChart = echarts.init(document.getElementById('ip-details-chart'));
                let option = {
                    backgroundColor: new echarts.graphic.RadialGradient(0.5, 0.5, 0.4, [{
                        offset: 0,
                        color: '#4b5769'
                    }, {
                        offset: 1,
                        color: '#404a59'
                    }]),
                    tooltip: {
                        trigger: 'item',
                        formatter: name + '<br>{c}'
                    },
                    geo: {
                        map: 'world',
                        silent: true,
                        label: {
                            emphasis: {
                                show: false,
                                areaColor: '#eee'
                            }
                        },
                        itemStyle: {
                            normal: {
                                borderWidth: 0.2,
                                borderColor: '#404a59'
                            }
                        },
                        roam: true
                    },
                    series: [
                        {
                            name: '坐标',
                            type: 'scatter',
                            coordinateSystem: 'geo',
                            data: [{name: '', value: loc}],
                            activeOpacity: 1,
                            symbolSize: 15,
                            itemStyle: {
                                normal: {
                                    borderColor: '#fff',
                                    color: '#577ceb',
                                }
                            }
                        }
                    ]
                };
                myChart.setOption(option);

                let resizing = false;
                window.onresize = () => {
                    if (resizing === false) {
                        resizing = true;
                        setTimeout(() => {
                            myChart.resize();
                            resizing = false
                        }, 200)
                    }
                }
            }
        },
        created() {
            this.getIpDetails(this.$route.params.ip);
        }
    }
</script>

<style scoped>
    #ip-details-title {
        font-size: 32px;
        text-align: center;
        margin: 10px;
    }

    #ip-details-chart {
        width: 55%;
        height: 300px;
        margin-left: auto;
        margin-right: auto;
        margin-bottom: 10px;
        background-color: #404A59;
    }
</style>

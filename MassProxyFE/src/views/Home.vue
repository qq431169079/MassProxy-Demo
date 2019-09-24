<template>
    <div id="home">
        <el-container>
            <el-main>
                <div id="home-main">
                    <t-zoom-in-top>
                        <div id="home-title">
                            <p id="home-title-h">Welcome to <b>M</b>ass <b>P</b>roxy - <b>分布式</b>海量代理平台, 日扫<b>百亿</b>地址
                            </p>
                        </div>
                    </t-zoom-in-top>
                    <t-zoom-in-top>
                        <proxy-heatmap/>
                    </t-zoom-in-top>
                </div>
            </el-main>

            <el-aside id="home-aside" width="20%">
                <type-pie-chart/>
                <t-zoom-in-top>
                    <top-country-table/>
                </t-zoom-in-top>
                <t-zoom-in-bottom>
                    <stable-proxy-table/>
                </t-zoom-in-bottom>
            </el-aside>
        </el-container>
    </div>
</template>

<script>
    import TopCountryTable from "@/components/statistic/TopCountryTable";
    import ProxyHeatmap from "@/components/statistic/ProxyHeatmap";
    import TypePieChart from "@/components/statistic/TypePieChart";
    import StableProxyTable from "@/components/statistic/StableProxyTable";

    export default {
        name: 'Home',
        components: {StableProxyTable, TypePieChart, ProxyHeatmap, TopCountryTable}
    }
</script>

<style>
    #home-title-h {
        margin-top: 5px;
        text-align: center;
    }
</style>

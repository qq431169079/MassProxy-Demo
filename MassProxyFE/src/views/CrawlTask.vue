<template>
    <div id="crawl-task">
        <t-zoom-in-top>
            <p style="text-align: center; font-size: 30px;">添加抓取任务</p>
            <el-row type="flex" justify="center">
                <el-col :span="8">
                    <el-tooltip class="item" effect="dark" content="如 https://www.xicidaili.com/nn/%s" placement="top">
                        <el-input v-model="url" placeholder="URL模板"></el-input>
                    </el-tooltip>
                </el-col>
                <el-col :span="2">
                    <el-tooltip class="item" effect="dark" content="如 1" placement="top">
                        <el-input v-model="start" placeholder="开始页号"></el-input>
                    </el-tooltip>
                </el-col>
                <el-col :span="2">
                    <el-tooltip class="item" effect="dark" content="如 10" placement="top">
                        <el-input v-model="end" placeholder="结束页号"></el-input>
                    </el-tooltip>
                </el-col>
                <el-col :span="1">
                    <el-button type="primary" @click="addCrawlTask(url, start, end)">添加</el-button>
                </el-col>
            </el-row>
            <br/>
            <el-row type="flex" justify="center">
                <el-col :span="1">
                    <el-tag style="width: 100%; text-align: center;">代理站</el-tag>
                </el-col>
                <el-col :span="8">
                    <el-select v-model="value" placeholder="请选择" size="small" style="width: 100%;" @change="fill">
                        <el-option v-for="item in options" :key="item.value" :label="item" :value="item">
                        </el-option>
                    </el-select>
                </el-col>
            </el-row>
            <el-row>
                <p class="scan-text"><b>Url模板: </b>要抓取的代理网站模板 %s 代表变量 <b>开始页号: </b>%s的开始值 <b>结束页号: </b>%s的结束值</p>
            </el-row>
            <el-row>
                <p class="scan-text"><b>例: </b>要抓取西刺代理1-20页 Url模板: https://www.xicidaili.com/nn/%s 开始页号:1 结束页号:20</p>
            </el-row>
            <el-row>
                <p class="scan-text">您可以直接在<b>代理站</b>列表中选择现有爬取规则</p>
            </el-row>
            <el-row>
                <p class="scan-text">自适应抓取算法, 无需编写代码即可抓取90%代理网站</p>
            </el-row>

        </t-zoom-in-top>
    </div>
</template>


<script>
    export default {
        name: 'CrawlTask',
        data() {
            return {
                url: '',
                start: 1,
                end: 10,
                options: ['http://www.xiladaili.com/gaoni/%s/', 'http://www.xiladaili.com/http/%s/', 'http://www.xiladaili.com/https/%s/', 'http://www.nimadaili.com/gaoni/%s/', 'http://www.nimadaili.com/http/%s/', 'http://www.nimadaili.com/https/%s/', 'https://www.xicidaili.com/nn/%s', 'https://www.xicidaili.com/nt/%s', 'https://www.xicidaili.com/wn/%s', 'https://www.xicidaili.com/wt/%s', 'https://raw.githubusercontent.com/a2u/free-proxy-list/master/free-proxy-list.txt', 'https://www.kuaidaili.com/free/inha/%s/', 'https://www.kuaidaili.com/free/intr/%s/', 'https://raw.githubusercontent.com/clarketm/proxy-list/master/proxy-list.txt', 'http://www.iphai.com/free/ng', 'http://www.iphai.com/free/wg', 'http://www.iphai.com/free/wp', 'http://ip.jiangxianli.com/?page=%s', 'http://www.proxylists.net/http_highanon.txt', 'http://www.ip3366.net/free/?stype=1&page=%s', 'http://www.ip3366.net/free/?stype=2&page=%s', 'http://ab57.ru/downloads/proxyold.txt', 'https://www.rmccurdy.com/scripts/proxy/good.txt', 'http://www.kxdaili.com/dailiip/1/%s.html', 'http://www.kxdaili.com/dailiip/2/%s.html'],
                value: ''
            }
        },
        methods: {
            addCrawlTask(url, start, end) {
                this.$ajax.post('/task/addCrawlTask', {
                    url: url,
                    start: start,
                    end: end
                }).then(res => {
                    this.$message('添加成功! 当前扫描队列任务数: ' + res);
                    this.url = '';
                    this.start = 1;
                    this.end = 10;
                })
            },
            fill() {
                this.url = this.value;
                if(this.url.indexOf("%s") !== -1) {
                    this.start = 1;
                    this.end = 10;
                } else {
                    this.start = 1;
                    this.end = 1;
                }
            }
        }
    }
</script>

<style scoped>
    .scan-text {
        text-align: center;
        font-size: 12px;
        color: gray;
        margin-bottom: 0px;
    }

    .btn {
        width: 90%;
    }
</style>

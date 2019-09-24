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
            <el-row>
                <p class="scan-text"><b>Url模板: </b>要抓取的代理网站模板 %s 代表变量 <b>开始页号: </b>%s的开始值 <b>结束页号: </b>%s的结束值</p>
            </el-row>
            <el-row>
                <p class="scan-text"><b>例: </b>要抓取西刺代理的列表, 其结果页面为https://www.xicidaili.com/nn/1 (1代表页号)</p>
            </el-row>
            <el-row>
                <p class="scan-text">Url模板: https://www.xicidaili.com/nn/%s 开始索引:1 结束索引:20</p>
            </el-row>
            <el-row>
                <p class="scan-text">上述任务会抓取西刺代理1-20页的内容</p>
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
                start: '',
                end: ''
            }
        },
        methods: {
            addCrawlTask(url, start, end) {
                this.$ajax.post('/task/addCrawlTask', {
                    url: url,
                    start: start,
                    end: end
                }).then(res => {
                    this.$message('添加成功! 当前扫描队列任务数: ' + res)
                })
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
</style>

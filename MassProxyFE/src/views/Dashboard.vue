<template>
    <div id="dash-board">
        <t-zoom-in-top>
            <p style="text-align: center; font-size: 30px;">仪表板</p>
            <el-collapse v-model="activeNames" style="width: 80%; margin: auto;">
                <el-collapse-item name="1">
                    <template slot="title">
                        <div class="collapse-title">扫描任务</div>
                    </template>
                    <el-card class="box-card" shadow="hover">
                        <div class="collapse-content">
                            <el-row>
                                <el-col :span="3">任务数</el-col>
                                <el-col :span="2">速度</el-col>
                                <el-col :span="3">结果数</el-col>
                                <el-col :span="12">下个任务</el-col>
                                <el-col :span="2">操作</el-col>
                            </el-row>
                            <el-row>
                                <el-col :span="3">{{data.taskSize}}</el-col>
                                <el-col :span="2">{{data.taskSpeed}}/s</el-col>
                                <el-col :span="3">{{data.taskCount}}</el-col>
                                <el-col :span="12">{{data.taskNext}}</el-col>
                                <el-col :span="2"><el-button size="mini" type="primary" @click="scan">添加</el-button></el-col>
                            </el-row>
                        </div>
                    </el-card>
                </el-collapse-item>

                <el-collapse-item name="2">
                    <template slot="title">
                        <div class="collapse-title">爬虫任务</div>
                    </template>
                    <el-card class="box-card" shadow="hover">
                        <div class="collapse-content">
                            <el-row>
                                <el-col :span="3">任务数</el-col>
                                <el-col :span="2">速度</el-col>
                                <el-col :span="3">结果数</el-col>
                                <el-col :span="12">下个任务</el-col>
                                <el-col :span="2">操作</el-col>
                            </el-row>
                            <el-row>
                                <el-col :span="3">{{data.crawlSize}}</el-col>
                                <el-col :span="2">{{data.crawlSpeed}}/s</el-col>
                                <el-col :span="3">{{data.crawlCount}}</el-col>
                                <el-col :span="12">{{data.crawlNext}}</el-col>
                                <el-col :span="2"><el-button size="mini" type="primary" @click="crawl">添加</el-button></el-col>
                            </el-row>
                        </div>
                    </el-card>
                </el-collapse-item>

                <el-collapse-item name="3">
                    <template slot="title">
                        <div class="collapse-title">快速验证</div>
                    </template>
                    <el-card class="box-card" shadow="hover">
                        <div class="collapse-content">
                            <el-row>
                                <el-col :span="3">队列长度</el-col>
                                <el-col :span="2">速度</el-col>
                                <el-col :span="3">结果数</el-col>
                                <el-col :span="12">下个任务</el-col>
                            </el-row>
                            <el-row>
                                <el-col :span="3">{{data.openSize}}</el-col>
                                <el-col :span="2">{{data.openSpeed}}/s</el-col>
                                <el-col :span="3">{{data.openCount}}</el-col>
                                <el-col :span="12">{{data.fastNext}}</el-col>
                            </el-row>
                        </div>
                    </el-card>
                </el-collapse-item>

                <el-collapse-item name="4">
                    <template slot="title">
                        <div class="collapse-title">精确验证</div>
                    </template>
                    <el-card class="box-card" shadow="hover">
                        <div class="collapse-content">
                            <el-row>
                                <el-col :span="3">队列长度</el-col>
                                <el-col :span="2">速度</el-col>
                                <el-col :span="3">结果数</el-col>
                                <el-col :span="12">下个任务</el-col>
                                <el-col :span="2">操作</el-col>
                            </el-row>
                            <el-row>
                                <el-col :span="3">{{data.okSize}}</el-col>
                                <el-col :span="2">{{data.okSpeed}}/s</el-col>
                                <el-col :span="3">{{data.okCount}}</el-col>
                                <el-col :span="12">{{data.accNext}}</el-col>
                                <el-col :span="2"><el-button size="mini" type="primary" @click="importProxy">添加</el-button></el-col>
                            </el-row>
                        </div>
                    </el-card>
                </el-collapse-item>

            </el-collapse>
        </t-zoom-in-top>
    </div>
</template>

<script>
    export default {
        name: 'Dashboard',
        data() {
            return {
                activeNames: ['1', '2', '3', '4'],
                data: {
                    accNext: '无',
                    crawlCount: 0,
                    crawlNext: '无',
                    crawlSize: 0,
                    crawlSpeed: 0,
                    fastNext: '无',
                    okCount: 0,
                    okSize: 0,
                    okSpeed: 0,
                    openCount: 0,
                    openSize: 0,
                    openSpeed: 0,
                    taskCount: 0,
                    taskNext: '无',
                    taskSize: 0,
                    taskSpeed: 0
                }
            };
        },
        methods: {
            update() {
                this.$ajax.get('/task/getDashboardData').then(res =>{
                    res.taskNext = res.taskNext === null ? '无' : res.taskNext;
                    res.fastNext = res.fastNext === null ? '无' : res.fastNext;
                    res.accNext = res.accNext === null ? '无' : res.accNext;
                    res.crawlNext = res.crawlNext === null ? '无' : res.crawlNext;
                    this.data = res;
                    setTimeout(()=> {
                        this.update();
                    }, 2000);
                });
            },
            scan() {
                this.$router.push({name: 'ScanTask'});
            },
            crawl() {
                this.$router.push({name: 'CrawlTask'});
            },
            importProxy() {
                this.$router.push({name: 'ImportProxy'});
            }
        },
        created() {
            this.update();
        }
    }
</script>

<style scoped>
    .collapse-title {
        font-size: 20px;
        font-weight: bold;
        color: #303133;
    }

    .collapse-content {
        font-size: 14px;
    }

    .box-card {
        margin-top: 10px;
    }

    .el-card {
        border: 2px solid #EBEEF5;
    }
    pre {
        margin: 0;
        font-size: 16px;
    }
</style>

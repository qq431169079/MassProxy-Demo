<template>
    <div id="scan-task">
        <t-zoom-in-top>
            <p style="text-align: center; font-size: 30px;">添加扫描任务</p>
            <el-row type="flex" justify="center">
                <el-col :span="4">
                    <el-tooltip class="item" effect="dark" content="如0.0.0.0/0" placement="top">
                        <el-input v-model="ipRange" placeholder="IP范围"></el-input>
                    </el-tooltip>
                </el-col>
                <el-col :span="2">
                    <el-tooltip class="item" effect="dark" content="如80-85,3128,8080" placement="top">
                        <el-input v-model="portRange" placeholder="端口范围"></el-input>
                    </el-tooltip>
                </el-col>
                <el-col :span="1">
                    <el-tooltip class="item" effect="dark" content="将扫描任务分为多少份" placement="top">
                        <el-input v-model="part" placeholder="分片"></el-input>
                    </el-tooltip>
                </el-col>
                <el-col :span="1">
                    <el-button type="primary" @click="addScanTask(ipRange, portRange, part)">添加</el-button>
                </el-col>
            </el-row>
            <el-row>
                <p class="scan-text"><b>IP范围</b>: 格式如0.0.0.0/0 代表扫描的IP范围</p>
            </el-row>
            <el-row>
                <p class="scan-text"><b>端口范围</b>: 如8080或8080,8081 使用逗号隔开多个端口</p>
            </el-row>
            <el-row>
                <p class="scan-text"><b>分片</b>: 将整个任务分成多少份, 用于分布式扫描</p>
            </el-row>
            <el-row>
                <p class="scan-text">如扫描全球42亿个地址, 分为100片, 则集群中最多可以有100个节点执行扫描任务</p>
            </el-row>
            <el-row>
                <p class="scan-text"><b>注: </b>后端扫描队列会自动跳过格式错误的任务</p>
            </el-row>
        </t-zoom-in-top>
    </div>
</template>


<script>
    export default {
        name: 'ScanTask',
        data() {
            return {
                ipRange: '',
                portRange: '',
                part: 10
            }
        },
        methods: {
            addScanTask(ip, port, part) {
                if (ip === '' || port === '' || part === '') {
                    this.$message('参数不能为空');
                    return;
                }

                this.$ajax.post('/task/addScanTask', {
                    task: ip + ' -p' + port,
                    part: part
                }).then(res => {
                    this.$message('添加成功! 当前扫描队列任务数: ' + res);
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

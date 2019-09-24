<template>
    <div id="stable-proxy">
        <p style="text-align: center">稳定代理</p>
        <el-table id="stable-proxy-table" :data="tableData" size="mini" stripe
                  v-loading="loading">
            <el-table-column prop="first" label="Ip" align="center"/>
            <el-table-column prop="second" label="端口" align="center" width="65"/>

            <el-table-column prop="proxy" label="操作" align="center" width="60">
                <template slot-scope="scope">
                    <span class="cursor-pointer" v-clipboard:copy="scope.row.proxy" v-clipboard:success="onCopySuccess"
                          v-clipboard:error="onCopyError"><i class="el-icon-document-copy"></i></span>
                </template>
            </el-table-column>
        </el-table>
    </div>
</template>

<script>
    export default {
        name: 'StableProxyTable',
        data() {
            return {
                loading: true,
                tableData: []
            }
        },
        methods: {
            getData() {
                this.$ajax.get('/proxy/listStable').then(res => {
                    for (let i = 0; i < res.length; i++) {
                        res[i].proxy = res[i].first + ':' + res[i].second;
                    }
                    this.tableData = res;
                    this.loading = false
                })
            },
            onCopySuccess() {
                this.$message('复制成功')
            },
            onCopyError() {
                this.$message('复制失败')
            }
        },
        created() {
            this.getData()
        }
    }
</script>

<style scoped>
    #stable-proxy {
        width: 100%;
        font-size: 13px;
    }

    .cursor-pointer {
        cursor: pointer;
    }
</style>

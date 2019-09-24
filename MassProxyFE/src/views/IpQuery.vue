<template>
    <div id="scan-task">
        <t-zoom-in-top>
            <p style="text-align: center; font-size: 30px;">查询IP信息</p>
            <el-row type="flex" justify="center">
                <el-col :span="4">
                    <el-tooltip class="item" effect="dark" content="如 223.99.1.1" placement="top">
                        <el-input v-model="ip" placeholder="IP"></el-input>
                    </el-tooltip>
                </el-col>
                <el-col :span="1">
                    <el-button type="primary" @click="query()">查询</el-button>
                </el-col>
            </el-row>
        </t-zoom-in-top>
    </div>
</template>

<script>
    export default {
        name: 'IpQuery',
        data() {
            return {
                ip: ''
            }
        },
        methods: {
            query() {
                if (this.ip === '') {
                    this.$message('IP不能为空!');
                    return;
                }
                this.$router.push({name: 'IpDetails', params: {ip: this.ip}})
            }
        }
    }
</script>

<style scoped>

</style>

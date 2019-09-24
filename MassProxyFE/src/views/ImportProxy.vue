<template>
    <div id="impory-proxy">
        <t-zoom-in-top>
            <p style="text-align: center; font-size: 30px;">导入代理</p>
            <el-row type="flex" justify="center">
                <el-col :span="1">
                    <el-button type="primary" @click="addTestTask(data)">导入</el-button>
                </el-col>
            </el-row>
            <el-row>
                <p style="text-align: center; font-size: 12px; color: gray">后端验证队列会自动去除重复和格式错误的代理地址</p>
            </el-row>
            <el-row type="flex" justify="center" style="margin-top: 0px;">
                <el-col :span="16">
                    <el-tooltip class="item" effect="dark" content="如1.1.1.1:3128" placement="top-end">
                        <el-input v-model="data" type="textarea" :autosize="{ minRows: 10, maxRows: 20}"
                                  placeholder="代理IP集, 每行一个IP:端口"></el-input>
                    </el-tooltip>
                </el-col>
            </el-row>
        </t-zoom-in-top>
    </div>
</template>


<script>
    export default {
        name: 'ImportProxy',
        data() {
            return {
                data: ''
            }
        },
        methods: {
            addTestTask(data) {
                if (data === '') {
                    this.$message('代理数据不能为空');
                    return;
                }

                this.$ajax.post('/task/addTestTask', {
                    data: data
                }).then(res => {
                    this.$message('添加成功! 当前验证队列任务数: ' + res);
                })
            }
        }
    }
</script>

<style scoped>

</style>

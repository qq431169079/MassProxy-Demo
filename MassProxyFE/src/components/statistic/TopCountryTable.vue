<template>
    <div id="top-country">
        <p style="text-align: center">最多国家</p>
        <el-table id="top-country-table" :data="tableData" size="mini" stripe
                  v-loading="loading">
            <el-table-column prop="country" label="国家" align="center">
            </el-table-column>
            <el-table-column prop="count" label="代理数" align="center">
            </el-table-column>
        </el-table>
    </div>
</template>

<script>
    export default {
        name: 'TopCountryTable',
        data() {
            return {
                loading: true,
                tableData: []
            }
        },
        methods: {
            countByCountry() {
                this.$ajax.get('/proxy/listMostCountry').then(res => {
                    this.tableData = res;
                    this.loading = false
                })
            }
        },
        created() {
            this.countByCountry()
        }
    }
</script>

<style scoped>
    #top-country {
        width: 100%;
        font-size: 13px;
    }
</style>

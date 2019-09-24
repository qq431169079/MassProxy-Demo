<template>
    <div id="proxy-list" v-loading="loading">
        <t-zoom-in-top>
            <p id="proxy-list-title">代理列表</p>

            <el-form id="proxy-list-form" ref="form" :model="queryForm" label-width="80px">
                <el-row>
                    <el-col :span="5">
                        <el-row>
                            <el-form-item label="端口" size="mini">
                                <el-input v-model="queryForm.port" type='number'/>
                            </el-form-item>
                        </el-row>
                        <el-row>
                            <el-form-item label="国家" size="mini">
                                <el-select v-model="queryForm.country" placeholder="国家">
                                    <el-option v-for="(country, index) in countryList" :key="index" :label="country"
                                               :value="country"/>
                                </el-select>
                            </el-form-item>
                        </el-row>
                    </el-col>
                    <el-col :span="10">
                        <el-row>
                            <el-form-item label="类型" size="mini">
                                <el-radio-group v-model="queryForm.type">
                                    <el-radio-button label="">Any</el-radio-button>
                                    <el-radio-button label="1">HTTP</el-radio-button>
                                    <el-radio-button label="2">HTTPS</el-radio-button>
                                    <el-radio-button label="3">HTTP/S</el-radio-button>
                                </el-radio-group>
                            </el-form-item>
                        </el-row>
                        <el-row>
                            <el-form-item label="排序" size="mini">
                                <el-radio-group v-model="queryForm.order">
                                    <el-radio-button label="">ID</el-radio-button>
                                    <el-radio-button label="1">连接</el-radio-button>
                                    <el-radio-button label="2">响应</el-radio-button>
                                    <el-radio-button label="3">评分</el-radio-button>
                                    <el-radio-button label="4">综合</el-radio-button>
                                </el-radio-group>
                            </el-form-item>
                        </el-row>
                    </el-col>
                    <el-col :span="4">
                        <el-row>
                            <el-form-item label="连接时间<" size="mini">
                                <el-input v-model="queryForm.maxConnTime" type='number'/>
                            </el-form-item>
                        </el-row>
                        <el-row>
                            <el-form-item label="响应时间<" size="mini">
                                <el-input v-model="queryForm.maxRespTime" type='number'/>
                            </el-form-item>
                        </el-row>
                    </el-col>
                    <el-col :span="5">
                        <el-row>
                            <el-form-item label="评分>" size="mini">
                                <el-input v-model="queryForm.minScore" type='number'/>
                            </el-form-item>
                        </el-row>
                        <el-row>
                            <el-form-item label="操作" size="mini">
                                <el-button type="primary" @click="query">查询</el-button>
                                <el-button type="success" @click="extractProxy">提取</el-button>
                            </el-form-item>
                        </el-row>
                    </el-col>
                </el-row>

            </el-form>

            <el-pagination id="proxy-list-pagination" layout="prev, pager, next"
                           background element-loading-spinner="el-icon-more-outline"
                           v-show="itemCount > pageSize" :page-size="pageSize"
                           :total="itemCount" :current-page="currentPage" @current-change="getPage">
            </el-pagination>

            <el-table :data="tableData" stripe size="small">
                <el-table-column prop="ip" label="Ip" min-width="120px"/>
                <el-table-column prop="port" label="端口" min-width="60px"/>
                <el-table-column prop="type" label="类型" min-width="80px"/>
                <el-table-column prop="country" label="国家" min-width="100px"/>
                <el-table-column prop="location" label="地理位置" min-width="200px"/>
                <el-table-column prop="connectTime" label="连接时间" min-width="80px"/>
                <el-table-column prop="responseTime" label="响应时间" min-width="80px"/>
                <el-table-column prop="score" label="评分" min-width="50px"/>
                <el-table-column prop="lastCheckTime" label="上次检查" min-width="90px"/>
                <el-table-column prop="recordTime" label="入库时间" min-width="90px"/>
                <el-table-column label="操作" width="120px" align="center">
                    <template slot-scope="scope">
                        <span class="cursor-pointer el-icon-document-copy"
                              @click="copyProxy(scope.row.ip, scope.row.port)">复制</span> /
                        <span class="cursor-pointer el-icon-aim" @click="toIpDetails(scope.row.ip)">详情</span>
                    </template>
                </el-table-column>
            </el-table>
        </t-zoom-in-top>
    </div>
</template>

<script>
    export default {
        name: 'Proxy',
        data() {
            return {
                loading: true,
                itemCount: 20,
                pageSize: 20,
                tableData: [],
                countryList: [],
                currentPage: 1,
                queryForm: {
                    page: 1,
                    port: '',
                    type: '',
                    country: null,
                    maxConnTime: '',
                    maxRespTime: '',
                    minScore: '',
                    order: ''
                },
                queryCond: {}
            }
        },
        methods: {
            getPage(page) {
                this.loading = true;
                this.currentPage = page;
                this.queryCond.page = page;
                this.$ajax.get('/proxy/query', this.queryCond).then(res => {
                    const type = ['', 'HTTP', 'HTTPS', '通用'];
                    for (let i = 0; i < res.length; i++) {
                        res[i].type = type[res[i].type];
                        res[i].connectTime += 'ms';
                        res[i].responseTime += 'ms';
                        res[i].lastCheckTime += '前';
                        res[i].recordTime += '前';
                    }
                    this.tableData = res;
                    this.loading = false
                })
            },
            getPageInfo() {
                this.$ajax.get('/proxy/queryPageInfo', this.queryCond).then(res => {
                    this.itemCount = res.itemCount;
                    this.pageSize = res.pageSize;
                    this.getPage(1);
                })
            },
            setQueryCond(cond) {
                this.queryCond = JSON.parse(JSON.stringify(cond));
                if (this.queryCond.country === '') {
                    this.queryCond.country = null;
                }
            },
            query() {
                this.setQueryCond(this.queryForm);
                this.getPageInfo();
            },
            getCountry() {
                this.$ajax.get('/location/listAvailableCountry').then(res => {
                    res.unshift('');
                    this.countryList = res;
                })
            },
            extractProxy() {
                this.loading = true;

                let cond = JSON.parse(JSON.stringify(this.queryForm));
                if (cond.country === '') {
                    cond.country = null;
                }

                this.$ajax.get('/proxy/extract', cond).then(res => {

                    this.$copyText(res).then(() => {
                        this.$message('已将提取代理写入剪切板')
                    }, () => {
                        this.$message('无法将提取代理写入剪切板')
                    });

                    this.loading = false;
                })
            },
            copyProxy(ip, port) {
                this.$copyText(ip + ':' + port).then(() => {
                    this.$message('已将代理写入剪切板')
                }, () => {
                    this.$message('无法将代理写入剪切板')
                });
            },
            toIpDetails(ip) {
                this.$router.push({name: 'IpDetails', params: {ip: ip}})
            }
        },
        created() {
            this.getCountry();
            this.query()
        }
    }
</script>

<style scoped>
    #proxy-list-title {
        text-align: center;
        font-size: 24px;
        margin-top: 5px;
        margin-bottom: 10px;
    }

    #proxy-list {
        width: 90%;
        min-width: 1000px;
        height: 100%;
        margin: auto;
    }

    #proxy-list-form {
        width: 980px;
        margin: auto;
    }

    #proxy-list-pagination {
        text-align: center;
    }

    .cursor-pointer {
        cursor: pointer;
    }
</style>

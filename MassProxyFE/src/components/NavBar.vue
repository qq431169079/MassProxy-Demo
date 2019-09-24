<template>
    <div id="nav-bar">
        <el-row>
            <el-col :span="2">
                <div id="nav-bar-name">
                    MassProxy
                </div>
            </el-col>

            <el-col :span="14">
                <el-menu mode="horizontal" router :default-active="activeIndex" ref="rootMenu">
                    <el-menu-item index="/"><i class="el-icon-s-home"></i>态势</el-menu-item>
                    <el-menu-item index="/proxy"><i class="el-icon-tickets"></i>代理</el-menu-item>
                    <el-menu-item index="/dashboard"><i class="el-icon-monitor"></i>仪表板</el-menu-item>
                    <el-menu-item index="/scan"><i class="el-icon-zoom-in"></i>扫描</el-menu-item>
                    <el-menu-item index="/crawl"><i class="el-icon-circle-plus-outline"></i>抓取</el-menu-item>
                    <el-menu-item index="/import"><i class="el-icon-circle-plus-outline"></i>导入</el-menu-item>
                    <el-menu-item index="/ipquery"><i class="el-icon-search"></i>IP查询</el-menu-item>
                </el-menu>
            </el-col>
        </el-row>
    </div>
</template>

<script>
    export default {
        name: 'NavBar',
        data() {
            return {
                activeIndex: this.$route.path
            }
        },
        watch: {
            $route(to, from) {
                this.$refs.rootMenu.activeIndex = to.path
            }
        }
    }
</script>

<style scoped>
    #nav-bar {
        height: 100%;
        border-bottom: solid 1px #e6e6e6;
    }

    #nav-bar-name {
        text-align: left;
        color: #303133;
        font-size: 16px;
        margin-top: 21px;
    }
</style>

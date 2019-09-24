import Vue from 'vue'
import Router from 'vue-router'
import Home from './views/Home'
import Proxy from './views/Proxy'
import Dashboard from './views/Dashboard'
import IpDetails from './views/IpDetails'
import ScanTask from './views/ScanTask'
import CrawlTask from './views/CrawlTask'
import ImportProxy from './views/ImportProxy'
import IpQuery from './views/IpQuery'

Vue.use(Router);

export default new Router({
    mode: 'history',
    base: process.env.BASE_URL,
    routes: [
        {
            path: '/',
            name: 'Home',
            component: Home
        },
        {
            path: '/proxy',
            name: 'Proxy',
            component: Proxy
        },
        {
            path: '/proxy/details/:ip',
            name: 'IpDetails',
            component: IpDetails
        },
        {
            path: '/dashboard',
            name: 'Dashboard',
            component: Dashboard
        },
        {
            path: '/scan',
            name: 'ScanTask',
            component: ScanTask
        },
        {
            path: '/crawl',
            name: 'CrawlTask',
            component: CrawlTask
        },
        {
            path: '/import',
            name: 'ImportProxy',
            component: ImportProxy
        },
        {
            path: '/ipquery',
            name: 'IpQuery',
            component: IpQuery
        }
    ]
})

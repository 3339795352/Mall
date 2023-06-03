import { createRouter, createWebHistory } from 'vue-router';
import Home from '../views/HomeView.vue';
import Login from '../views/LoginView.vue';
import AdminBrand from '../views/admin/admin-brand.vue';
import AdminSpecification from '../views/admin/admin-specification.vue';
import AdminTemplates from '../views/admin/admin-template.vue';
import AdminGoods from '../views/admin/admin-goods.vue';
import AdminCategory from '../views/admin/admin-category.vue';
import AdminContent from '../views/content/admin-content.vue';
var routes = [
    {
        path: '/',
        name: 'Login',
        component: Login
    },
    {
        path: '/home',
        name: 'Home',
        component: Home,
        children: [
            {
                path: '/admin/brand',
                name: 'AdminBrand',
                component: AdminBrand
            },
            {
                path: '/admin/specification',
                name: 'AdminSpecification',
                component: AdminSpecification
            },
            {
                path: '/admin/templates',
                name: 'AdminTemplates',
                component: AdminTemplates
            },
            {
                path: '/admin/goods',
                name: 'AdminGoods',
                component: AdminGoods
            },
            {
                path: '/admin/category',
                name: 'AdminCategory',
                component: AdminCategory
            },
            {
                path: '/content/admincontent',
                name: 'AdminContent',
                component: AdminContent
            }
        ]
    }
];
var router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes: routes
});
export default router;
//# sourceMappingURL=index.js.map
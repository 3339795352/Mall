<template>
  <a-layout id="components-layout-demo-top-side-2">
    <the-header></the-header>
    <a-layout>
      <a-layout-sider width="200"  style="background: #fff">
        <a-menu
                v-model:selectedKeys="selectedKeys2"
                v-model:openKeys="openKeys"
                mode="inline"
                :style="{ height: '100%', borderRight: 0 }"
        >
          <a-sub-menu key="sub1">
            <template #title>
              <span>
                <user-outlined />
                商家管理
              </span>
            </template>
            <a-menu-item key="1">option1</a-menu-item>
            <a-menu-item key="2">option2</a-menu-item>
            <a-menu-item key="3">option3</a-menu-item>
            <a-menu-item key="4">option4</a-menu-item>
          </a-sub-menu>
          <a-sub-menu key="sub2">
            <template #title>
              <span>
                <laptop-outlined />
                商品管理
              </span>
            </template>
            <a-menu-item key="/admin/brand">
              <router-link to="/admin/brand">品牌管理</router-link>
            </a-menu-item>
            <a-menu-item key="/admin/specification">
              <router-link to="/admin/specification">规格管理</router-link>
            </a-menu-item>
            <a-menu-item key="/admin/template">
              <router-link to="/admin/template">模板管理</router-link>
            </a-menu-item>
            <a-menu-item key="/admin/category">
              <router-link to="/admin/category">商品分类</router-link>
            </a-menu-item>
            <a-menu-item key="/admin/goods">
              <router-link to="/admin/goods">商品审核</router-link>
            </a-menu-item>
          </a-sub-menu>
          <a-sub-menu key="sub3">
            <template #title>
              <span>
                <notification-outlined />
                广告管理
              </span>
            </template>
            <a-menu-item key="9">广告类别</a-menu-item>
            <a-menu-item key="/admin/content">
              <router-link to="/admin/content">广告内容</router-link>
            </a-menu-item>
          </a-sub-menu>
        </a-menu>
      </a-layout-sider>
      <a-layout style="padding: 0 24px 24px">
        <a-layout-content
                :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '600px' }"
        >
          <router-view></router-view>
        </a-layout-content>
      </a-layout>
    </a-layout>
    <the-footer></the-footer>
  </a-layout>
</template>

<script lang="ts">
  import { Options, Vue } from 'vue-class-component';
  import HelloWorld from '@/components/HelloWorld.vue'; // @ is an alias to /src

  @Options({
    components: {
      HelloWorld,
    },
  })
  export default class Home extends Vue {}
</script>
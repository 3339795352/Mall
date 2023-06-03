<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <a-layout>
        <a-layout-content
                :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
        >
            <a-button type="primary" @click="add">新建</a-button>
            <a-button type="primary" @click="refresh">刷新</a-button>
            <a-table :dataSource="brandList"
                     :columns="columns"
                     :pagination="pagination"
                     :loading="Loading"
                     @change="handleTableChange"
            >
                <template v-slot:action="{text,record}">
                    <a-button type="primary" @click="edit(record)">修改</a-button>
                    <a-popconfirm
                            title="删除后无法恢复，确认删除?"
                            ok-text="是"
                            cancel-text="否"
                            @confirm="del(record.id)"
                    >
                        <a-button type="danger">删除</a-button>
                    </a-popconfirm>
                </template>
            </a-table>
        </a-layout-content>
    </a-layout>

    <a-modal v-model:visible="modelVisible"
             title="品牌编辑表单"
             cancel-text="取消"
             ok-text="确定"
             @ok="handleModelOk">
        <a-form
                :model="brand"
                :label-col="{ span: 6 }"
                :wrapper-col="{ span: 16 }"
        >

            <a-form-item label="品牌名称">
                <a-input v-model:value="brand.name"/>
            </a-form-item>

            <a-form-item label="品牌首字母">
                <a-input v-model:value="brand.firstChar"/>
            </a-form-item>
        </a-form>
    </a-modal>
</template>

<script lang="ts">
    //ref:vue3中model和view双向绑定间的中介
    import {defineComponent, onMounted, ref} from 'vue';
    import axios from "axios";
    import {Tool} from "@/util/tool";

    export default defineComponent({
        name: "admin-brand",

        //data,methods,created合体
        setup() {

            const brandList = ref();
            const brand = ref({});


            const columns = [
                {
                    title: '品牌ID',
                    dataIndex: 'id',
                    key: 'id',
                    width: 200
                },
                {
                    title: '品牌名称',
                    dataIndex: 'name',
                    key: 'name',
                    width: 200
                },
                {
                    title: '品牌首字母',
                    dataIndex: 'firstChar',
                    key: 'firstChar',
                    width: 200
                },
                {
                    title: '操作',
                    key: 'action',
                    slots: {customRender: 'action'},
                    width: 200
                }
            ];


            //添加或编辑
            const handleModelOk = () => {
                axios.post('http://localhost:8899/mall-manager/saveBrand', brand.value).then(response => {
                    modelVisible.value = false;  //关闭窗口
                    //加载最新品牌列表
                    BrandQueryNewsList({
                        page: pagination.value.current,
                        size: pagination.value.pageSize
                    });
                })
            };

            const add = () => {
                modelVisible.value = true;
                //初始化新闻对象
                brand.value = {};
            };

            const edit = (record: any) => {
                modelVisible.value = true;
                brand.value = Tool.copy(record); //复制对象
            }

            const pagination = ref({
                current: 1,
                pageSize: 4,
                total: 0
            });

            const loading = ref<boolean>(false);
            const modelVisible = ref<boolean>(false);


            //提交删除
            const del = (id: number) => {
                axios.delete('http://localhost:8899/mall-manager/deleteBrand/' + id).then(response => {
                    //加载最新新闻列表
                    BrandQueryNewsList({
                        page: pagination.value.current,
                        size: pagination.value.pageSize
                    });
                })
            };

            //分页
            const BrandQueryNewsList = (params: any) => {
                loading.value = true;
                axios.get('http://localhost:8899/mall-manager/listByPage',
                    {
                        params: {
                            page: params.page,
                            size: params.size
                        }
                    }
                ).then((response) => {
                    loading.value = false;
                    brandList.value = response.data.list;
                    //重置分页按钮
                    pagination.value.current = params.page;
                    pagination.value.total = response.data.total;

                });
            };

            const handleTableChange = (pagination: any) => {
                BrandQueryNewsList({
                    page: pagination.current,
                    size: pagination.pageSize
                })
            }

            onMounted(() => {
                BrandQueryNewsList({
                    page: 1,
                    size: pagination.value.pageSize
                });
            });

            return {
                brandList,
                BrandQueryNewsList,
                columns,
                pagination,
                handleTableChange,
                loading,
                add,
                modelVisible,
                handleModelOk,
                brand,
                edit,
                del,


            }
        }
    })
</script>

<style scoped>

</style>
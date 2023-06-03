<template>
    <a-layout>
        <a-layout-content
                :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
        >
            <a-button type="primary" @click="add">新增</a-button>
            <a-table :dataSource="SpecificationList"
                     :columns="columns"
                     :pagination="pagination"
                     :loading="Loading"
                     @change="handleTableChange"
            >
                <template v-slot:action="{record}">
                    <a-button type="primary" @click="edit(record)">编辑</a-button>
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
             title="商品规格编辑表单"
             cancel-text="取消"
             ok-text="确定"
             @ok="handleModelOk">
        <a-form
                :model="Specification"
                :label-col="{ span: 6 }"
                :wrapper-col="{ span: 16 }"
        >

            <a-form-item label="规格名称">
                <a-input v-model:value="Specification.firstChar"/>
            </a-form-item>

            <a-form-item label="规格选项">
                <a-input v-model:value="Specification.firstChar"/>
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
        name: "admin-specification.vue",

        //data,methods,created合体
        setup() {

            const SpecificationList = ref();
            const Specification = ref({});


            const columns = [
                {
                    title: '规格ID',
                    dataIndex: 'id',
                    key: 'id',
                    width: 300
                },
                {
                    title: '规格名称',
                    dataIndex: 'specName',
                    key: 'specName',
                    width: 300
                },
                {
                    title: '操作',
                    key: 'action',
                    slots: {customRender: 'action'},
                    width: 300
                }
            ];


            //添加或编辑
            const handleModelOk = () => {

                axios.post('http://localhost:8899/mall-manager/saveSpecification', Specification.value).then(response => {
                    modelVisible.value = false;  //关闭窗口
                    //加载最新品牌列表
                    SpecificationQueryList({
                        page: pagination.value.current,
                        size: pagination.value.pageSize
                    });
                })
            };

            const add = () => {
                modelVisible.value = true;
                //初始化新闻对象
                Specification.value = {};
            };

            const edit = (record: any) => {
                console.log(record)
                modelVisible.value = true;
                Specification.value = Tool.copy(record); //复制对象
            }

            const pagination = ref({
                current: 1,
                pageSize: 4,
                total: 0
            });

            const loading = ref<boolean>(false);
            const modelVisible = ref<boolean>(false);
            const specEntity = ref<any>({
                specification:{},
                specificationOptionList:[]
            });

            //提交删除
            const del = (id: number) => {
                axios.delete('http://localhost:8899/mall-manager/deleteBrand/' + id).then(response => {
                    //加载最新新闻列表
                    SpecificationQueryList({
                        page: pagination.value.current,
                        size: pagination.value.pageSize
                    });
                })
            };

            //分页
            const SpecificationQueryList = (params: any) => {
                loading.value = true;
                axios.get('http://localhost:8899/mall-manager/SpecificationByPage',
                    {
                        params: {
                            page: params.page,
                            size: params.size
                        }
                    }
                ).then((response) => {
                    loading.value = false;
                    SpecificationList.value = response.data.list;
                    //重置分页按钮
                    pagination.value.current = params.page;
                    pagination.value.total = response.data.total;

                });
            };

            const handleTableChange = (pagination: any) => {
                SpecificationQueryList({
                    page: pagination.current,
                    size: pagination.pageSize
                })
            }

            onMounted(() => {
                SpecificationQueryList({
                    page: 1,
                    size: pagination.value.pageSize
                });
            });

            return {
                SpecificationList,
                SpecificationQueryList,
                columns,
                pagination,
                handleTableChange,
                loading,
                add,
                modelVisible,
                handleModelOk,
                Specification,
                edit,
                del,
                specEntity,




            }
        }
    })
</script>

<style scoped>

</style>
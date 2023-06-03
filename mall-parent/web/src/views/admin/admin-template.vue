<template>
    <a-layout>
        <a-layout-content :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }">
            <a-button type="primary" @click="add">新增</a-button>
            <a-table
                    :dataSource="TemplateList"
                    :columns="columns"
                    :row-key="record => record.id"
                    :pagination="pagination"
                    :loading="modalloading"
                    @change="handleTableChange"
            >
                <template #specIds="{text}">
                    {{ jsonToString(text, 'text') }}
                </template>
                <template #brandIds="{ text }">
                    {{ jsonToString(text, 'text') }}
                </template>
                <template #customAttributeItems="{ text }">
                    {{ jsonToString(text, 'text') }}
                </template>
                <template v-slot:action="{ record }">
                    <a-button type="primary" @click="edit(record)">
                        编辑
                    </a-button>
                    <a-popconfirm
                            title="删除后无法恢复，确认删除?"
                            ok-text="是"
                            cancel-text="否"
                            @confirm="del(record.id)"
                    >
                        <a-button type="danger">
                            删除
                        </a-button>
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
        <a-form :model="templateEntity" :label-col="{span:6}" :wrapper-col="wrapperCol">
            <a-form-item label="分类模板名称">
                <a-input  v-model:value="templateEntity.name"/>
            </a-form-item>

            <a-form-item label="关联品牌">
                <a-select
                        v-model:value="templateEntity.brandIds"
                        mode="multiple"
                        style="width: 100%;"
                        placeholder="选择品牌"
                        :label-in-value="true"
                >
                    <a-select-option v-for="item in brandList"
                                     :key="item.id"
                                     :value="item.id"
                                     :label="item.name"
                    >
                        {{ item.name }}
                    </a-select-option>
                </a-select>
            </a-form-item>

            <a-form-item label="关联规格">
                <a-select
                        v-model:value="templateEntity.specIds"
                        mode="multiple"
                        style="width: 100%;"
                        placeholder="选择规格..."
                        :label-in-value="true"
                >
                    <a-select-option v-for="item in specificationList"
                                     :key="item.id"
                                     :value="item.id"
                                     :label="item.specName"
                    >
                        {{ item.specName }}
                    </a-select-option>
                </a-select>
            </a-form-item>

            <a-form-item label="扩展属性">
                <a-button type="primary" @click="addTableRow">新增扩展属性</a-button>
                <table width="340px">
                    <thead>
                    <tr>
                        <td>属性名称</td>
                        <td>操作</td>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="pojo, index in templateEntity.customAttributeItems" >
                        <td><a-input v-model:value="pojo.text"/></td>
                        <td><a-button danger block @click="deleTableRow(index)">删除</a-button></td>
                    </tr>
                    </tbody>
                </table>
            </a-form-item>
        </a-form>

    </a-modal>

</template>

<script lang="ts">
    import {defineComponent, onMounted, ref} from 'vue';
    import axios from 'axios';
    import {message} from "ant-design-vue";


    export default defineComponent({
        name: 'admin-template',
        setup: function () {
            const TemplateList = ref<any>([]);
            const maxTagTextLength = ref(10);
            const brandList = ref();
            const brandOptions = ref<any>([]);
            const specificationList = ref();
            const modelVisible = ref<any>(false);
            const modalloading = ref(false);
            const templateEntity = ref<any>({
                id:'',
                name:'',
                specIds:[],
                brandIds:[],
                customAttributeItems:[]
            });
            const pagination = ref({
                current: 1,
                pageSize: 4,
                total: 0,
            });

            const columns = [
                {
                    title: '模板id',
                    dataIndex: 'id',
                    key: 'id',
                    width: 300,
                },
                {
                    title: '分类模板名称',
                    dataIndex: 'name',
                    key: 'name',
                    width: 300,
                },
                {
                    title: '关联品牌',
                    dataIndex: 'brandIds',
                    key: 'brandIds',
                    width: 300,
                    slots: {customRender: 'specIds'},
                },
                {
                    title: '关联规格',
                    dataIndex: 'specIds',
                    key: 'specIds',
                    width: 300,
                    slots: {customRender: 'brandIds'},
                },
                {
                    title: '扩展属性',
                    dataIndex: 'customAttributeItems',
                    key: 'customAttributeItems',
                    width: 300,
                    slots: {customRender: 'customAttributeItems'},
                },
                {
                    title: '操作',
                    dataIndex: 'action',
                    key: 'action',
                    width: 300,
                    slots: {customRender: 'action'},
                },
            ];

            const jsonToString = (jsonString:any, key:any) => {
                let json = JSON.parse(jsonString);
                let value = '';
                for (let i = 0; i < json.length; i++) {
                    if (i > 0) {
                        value += ',';
                    }
                    value += json[i][key];
                }
                return value;
            };

            const handleTableChange = (pagination:any) => {
                TemplateQueryList({
                    page: pagination.current,
                    size: pagination.pageSize,
                });
            };

            const handleQuery = (params:any) => {
                TemplateQueryList({
                    page: params.page,
                    size: params.size,
                });
            };

            const add = () => {
                modelVisible.value = true;
                templateEntity.value = {
                    customAttributeItems: []
                }
            };

            const edit = (record:any) => {
                console.log("rd"+record.customAttributeItems);
                // findTemplateById(record.id);
                let jsonBrand =JSON.parse(record.brandIds);
                let jsonSpec =JSON.parse(record.specIds);
                let jsonItems =JSON.parse(record.customAttributeItems);
                console.log(111+jsonItems)
                let jsonBrandList: Array<any>=[];
                let jsonSpecList:Array<any>=[];
                jsonBrand.forEach((item: { id: any; text: any; })=>{
                    jsonBrandList.push({
                        value:item.id,
                        key:item.id,
                        option:{
                            value:item.id,
                            label:item.text,
                        }
                    })
                });

                jsonSpec.forEach((item: { id: any; text: any; })=>{
                    jsonSpecList.push({
                        value:item.id,
                        key:item.id,
                        option:{
                            value:item.id,
                            label:item.text,
                        }
                    })
                })
                templateEntity.value.brandIds=jsonBrandList;
                templateEntity.value.specIds=jsonSpecList;
                templateEntity.value.customAttributeItems=jsonItems;
                console.log("at"+templateEntity.value.customAttributeItems)
                templateEntity.value.name=record.name;
                templateEntity.value.id=record.id;
                console.log(jsonSpec)
                console.log(templateEntity.value.specIds)
                // console.log(templateEntity.value.brandIds)
                // console.log(jsonBrandList)
                modelVisible.value = true;
            };

            const del = (id:number) => {
                axios.delete('/mall-manager/template-Ms/deleteTemplate/' + id).then(response => {
                    if (response.data.code == 200) {
                        message.info('删除成功');
                        //加载最新新闻列表
                        TemplateQueryList({
                            page: pagination.value.current,
                            size: pagination.value.pageSize
                        });
                    } else {
                        message.error(response.data.message);
                    }
                })
            };

            const addTableRow = () => {
                templateEntity.value.customAttributeItems.push([]);
            }

            const deleTableRow = (index:number) => {
                templateEntity.value.customAttributeItems.splice(index, 1);
            }

            const handleModelOk = () => {
                modalloading.value=false;

                let templateList:Array<any>=[];
                (templateEntity.value.brandIds as Array<any>).forEach(item=>{
                    templateList.push({
                        id:item.option.value,
                        text:item.option.label
                    })
                });

                //转json对象（集合） 为字符串
                templateEntity.value.brandIds=JSON.stringify(templateList);
                // console.log(111+templateEntity.value.brandIds)
                let specList:Array<any>=[];
                (templateEntity.value.specIds as Array<any>).forEach(item=>{
                    console.log("sss"+templateEntity.value.specIds)
                    specList.push({
                        id:item.option.value,
                        text:item.option.label
                    })
                });
                let optionList:Array<any>=[];
                (templateEntity.value.customAttributeItems as Array<any>).forEach(item=>{
                    console.log(item+"OOOO")
                    optionList.push({
                        // id:item.option.value,
                        text:item.text
                    })
                });
                //转json对象（集合） 为字符串
                templateEntity.value.brandIds=JSON.stringify(templateList);
                //转json对象（集合） 为字符串
                templateEntity.value.brandIds=JSON.stringify(templateList);
                // console.log(11111+templateEntity.value.brandIds)
                // console.log(111+templateEntity.value)
                templateEntity.value.specIds=JSON.stringify(specList);

                console.log("aa"+templateEntity.value.specIds)
                templateEntity.value.customAttributeItems=JSON.stringify(optionList);
                console.log("v"+templateEntity.value.customAttributeItems)
                // console.log(111+templateEntity.value.brandIds)
                axios.post('/mall-manager/template-Ms/saveTemplate',templateEntity.value).then(response=>{
                    modalloading.value=false;
                    if(response.data.code==200){
                        modelVisible.value=false;
                        handleQuery({
                            page:pagination.value.current,
                            size:pagination.value.pageSize
                        });
                        message.info("操作成功");
                    }else {
                        message.error(response.data.message);
                    }
                })
            };

            const TemplateQueryList = (params:any) => {
                modalloading.value = true;
                axios
                    .get('http://localhost:8899/mall-manager/TemplateByPage', {
                        params: {
                            page: params.page,
                            size: params.size,
                        },
                    })
                    .then((response) => {
                        modalloading.value = false;
                        TemplateList.value = response.data.list;
                        console.log(response.data.list[0].specIds)
                        pagination.value.current = params.page;
                        pagination.value.total = response.data.total;
                    })
                    .catch((error) => {
                        console.error(error);
                        modalloading.value = false;
                    });
            };

            const findBrandList = () => {
                axios.get('http://localhost:8899/mall-manager/queryBrand').then(response => {
                    brandList.value = response.data
                })
            }

            const findSpecList = () => {
                axios.get('http://localhost:8899/mall-manager/querySpecification').then(response => {
                    specificationList.value = response.data
                })
            }

            const findTemplateById = (id:number) => {
                axios.get('http://localhost:8899/mall-manager/findTemplateById/'+id).then(response=>{
                    if(response.data.code==200){
                        console.log(response.data.data);
                        templateEntity.value=response.data.data;
                    }else {
                        message.error(response.data.message);
                    }
                })
            }

            onMounted(() => {
                TemplateQueryList({
                    page: 1,
                    size: pagination.value.pageSize,
                });
                findBrandList();
                findSpecList();
                // findTemplateList();
            });

            return {
                handleModelOk,
                modalloading,
                modelVisible,
                handleQuery,
                columns,
                templateEntity,
                TemplateList,
                jsonToString,
                pagination,
                handleTableChange,
                del,
                add,
                edit,
                maxTagTextLength,
                brandList,
                brandOptions,
                specificationList,
                findBrandList,
                findSpecList,
                // findTemplateList,
                addTableRow,
                deleTableRow,
                findTemplateById
            };
        },
    });
</script>

<style scoped></style>

<template>
    <a-layout >
        <a-layout-content
                :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
        >
            <a-button type="primary" @click="add" style="margin-bottom: 20px">添加</a-button>
            <a-table :dataSource="contentList"
                     :columns="columns"
                     :pagination="pagination"
                     :loading="Loading"
                     @change="handleTableChange"
            >
                <template #pic="{text}">
                    <a-image :src="text" style="height: 100px"/>
                </template>

                <template #status="{text}">
                    {{text==1?"有效":"无效"}}
                </template>

                <template v-slot:action="{text,record}">
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
             title="品牌编辑表单"
             cancel-text="取消"
             @cancel="cancel"
             ok-text="确定"
             @ok="handleModelOk">
        <a-form
                :model="content"
                :label-col="{ span: 6 }"
                :wrapper-col="{ span: 16 }"
        >
            <a-form-item label="内容类目ID">
                <a-select
                        v-model:value="content.categoryId"
                        style="width: 100%;"
                        :label-in-value="true"
                >
                    <a-select-option v-for="item in contentCategoryList"
                                     :key="item.id"
                                     :value="item.id"
                                     :label="item.name"
                    >
                        {{ item.name }}
                    </a-select-option>
                </a-select>
            </a-form-item>

            <a-form-item label="内容标题">
                <a-input v-model:value="content.title" />
            </a-form-item>

            <a-form-item label="链接">
                <a-input v-model:value="content.url" />
            </a-form-item>

            <a-form-item label="图片">

                <a-upload
                        v-model:file-list="fileList"
                        name="file"
                        list-type="picture-card"
                        class="avatar-uploader"
                        :show-upload-list="true"
                        :action="actionImg"
                        :before-upload="beforeUpload"
                        @change="handleChange"
                >
                    <img v-if="imageUrl" :src="imageUrl" alt="avatar" width="200px" height="200px"/>
                    <div v-else>
                        <loading-outlined v-if="loading"></loading-outlined>
                        <plus-outlined v-else></plus-outlined>
                        <div class="ant-upload-text">文件上传</div>
                    </div>

                </a-upload>

            </a-form-item>

            <a-form-item label="状态">
                <a-checkbox v-model:value="checked" v-model:checked="checked"></a-checkbox>
            </a-form-item>

            <a-form-item label="排序">
                <a-input v-model:value="content.sortOrder" />
            </a-form-item>


        </a-form>
    </a-modal>
</template>

<script lang="ts">
    import{ref,onMounted} from "vue";
    import axios from "axios";
    import {Tool} from "@/util/tool";
    import {message} from "ant-design-vue";
    export default {
        name: "admin-content",

        setup: function () {
            const contentList = ref();
            const Loading = ref<boolean>(false);
            const modelVisible = ref<boolean>(false);
            const checked = ref<boolean>(false);
            const content = ref();
            const contentCategoryList = ref();
            const fileList = ref([]);
            const imageUrl = ref<string>('');
            const actionImg = ref('http://localhost:8899/mall-content/uploadFile');

            const pagination = ref({
                current: 1,
                pageSize: 5,
                total: 0
            });


            const columns = [
                {
                    title: '内容ID',
                    dataIndex: 'id',
                    key: 'id',

                },
                {
                    title: '内容类目ID',
                    dataIndex: 'categoryId',
                    key: 'categoryId',

                },
                {
                    title: '内容标题',
                    dataIndex: 'title',
                    key: 'title',

                },
                {
                    title: '链接',
                    dataIndex: 'url',
                    key: 'url',

                },
                {
                    title: '图片',
                    key: 'pic',
                    dataIndex: 'pic',
                    slots: {customRender: 'pic'},

                },
                {
                    title: '状态',
                    key: 'status',
                    dataIndex: 'status',
                    slots: {customRender: 'status'},
                },
                {
                    title: '排序',
                    dataIndex: 'sortOrder',
                    key: 'sortOrder',

                },
                {
                    title: '操作',
                    key: 'action',
                    dataIndex: 'action',
                    slots: {customRender: 'action'},
                }
            ];

            const handleQueryContentList = (params: any) => {
                Loading.value = true;
                axios.get('http://localhost:8899/mall-content/contentlistByPage', {
                    params: {
                        page: params.page,
                        size: params.size
                    }
                }).then((response) => {
                    Loading.value = false;
                    contentList.value = response.data.list;
                    console.log(contentList.value[2].pic)

                    //重置分页按钮
                    pagination.value.current = params.page
                    pagination.value.total = response.data.total
                })
            }

            const handleTableChange = (pagination: any) => {
                handleQueryContentList({
                    page: pagination.current,
                    size: pagination.pageSize
                })
            }

            const cancel = () => {
                imageUrl.value ='';
                modelVisible.value = false;
            }

            const handleModelOk = () => {
                console.log(imageUrl.value+"@@@@@@@@@@@@@")
                content.value.pic=imageUrl.value;
                content.value.status=(checked.value?'1':'0');
                axios.post('http://localhost:8899/mall-content/saveContent',content.value).then(response=>{
                        modelVisible.value=false;//关闭窗口
                        message.info('操作成功');
                        //加载最新品牌列表
                        handleQueryContentList({
                            page:pagination.value.current,
                            size:pagination.value.pageSize
                        });
                })
            }
            const handleChange = async (info: any) => {
                Loading.value = false;
                console.log(info);
                const response = await info.file.response;
                content.value.pic = response.httpUrl;
                console.log(response.httpUrl+"@@@@@@@@@@@@");
                if (info.file.status === 'uploading') {
                    Loading.value = true;
                    console.log(info.fileList[0].name);
                    return;
                }
                if (info.file.status === 'done') {
                    Loading.value = false;
                    const response = await info.file.response;
                    content.value.pic = response.httpUrl;
                    console.log(response.httpUrl+"@@@@@@@@@@@@");
                }
                if (info.file.status === 'error') {
                    Loading.value = false;
                    console.log('ERROR');
                    message.error('upload error');
                }
            };

            const beforeUpload = (file: File) => {
                const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png';
                if (!isJpgOrPng) {
                    message.error('You can only upload JPG file!');
                }
                const isLt2M = file.size / 1024 / 1024 < 10;
                if (!isLt2M) {
                    message.error('Image must smaller than 10MB!');
                }
                return isJpgOrPng && isLt2M;
            };

            const findAllContentCategory = () => {
                axios.get('http://localhost:8899/mall-content/findAllContentCategory').then(response => {
                    contentCategoryList.value = response.data
                })
            }

            const del = (id: number) => {
                axios.delete('/mall-content/content-Ms/deleteContent/' + id).then(response => {
                    if (response.data.code == 200) {
                        message.success('删除成功');
                        //加载最新新闻列表
                        handleQueryContentList({
                            page: pagination.value.current,
                            size: pagination.value.pageSize
                        });
                    } else {
                        message.error(response.data.message);
                    }
                })
            };

            const edit = (record: any) => {
                modelVisible.value = true;
                console.log(record.pic)
                // console.log("111" + record.id)
                // console.log("ss" + fileList.value)
                // console.log("222"+content.value.status)
                // console.log(fileList)
                // imageUrl.value=record.pic
                // record.pic=fileList.value
                // imageUrl.value = record.pic;
                if (record.id != null) {
                    checked.value = true;
                }
                content.value = Tool.copy(record); //复制对象到编辑模态框
                console.log(content.value)
                handleQueryContentList({
                    page: 1,
                    size: pagination.value.pageSize
                })
            }

            const add = () => {
                modelVisible.value = true;
                //初始化品牌对象
                fileList.value = [];
                content.value ={};
                // content.value.pic=''
            }


            onMounted(() => {
                handleQueryContentList({
                    page: 1,
                    size: pagination.value.pageSize
                })
                findAllContentCategory();
            });

            return {
                contentList,
                columns,
                pagination,
                Loading,
                handleTableChange,
                del,
                add,
                modelVisible,
                handleModelOk,
                content,
                edit,
                contentCategoryList,
                findAllContentCategory,
                fileList,
                imageUrl,
                actionImg,
                handleChange,
                beforeUpload,
                checked,
                cancel,
            }

        }
    }

</script>

<style scoped>
    /* you can make up upload button and sample style by using stylesheets */
    .ant-upload-select-picture-card i {
        font-size: 32px;
        color: #999;
    }

    .ant-upload-select-picture-card .ant-upload-text {
        margin-top: 8px;
        color: #666;
    }
</style>
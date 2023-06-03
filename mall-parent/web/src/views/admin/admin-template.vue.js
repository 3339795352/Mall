import { defineComponent, onMounted, ref } from 'vue';
import axios from 'axios';
import { message } from "ant-design-vue";
export default defineComponent({
    name: 'admin-template',
    setup: function () {
        var TemplateList = ref([]);
        var maxTagTextLength = ref(10);
        var brandList = ref();
        var brandOptions = ref([]);
        var specificationList = ref();
        var modelVisible = ref(false);
        var modalloading = ref(false);
        var templateEntity = ref({
            id: '',
            name: '',
            specIds: [],
            brandIds: [],
            customAttributeItems: []
        });
        var pagination = ref({
            current: 1,
            pageSize: 4,
            total: 0,
        });
        var columns = [
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
                slots: { customRender: 'specIds' },
            },
            {
                title: '关联规格',
                dataIndex: 'specIds',
                key: 'specIds',
                width: 300,
                slots: { customRender: 'brandIds' },
            },
            {
                title: '扩展属性',
                dataIndex: 'customAttributeItems',
                key: 'customAttributeItems',
                width: 300,
                slots: { customRender: 'customAttributeItems' },
            },
            {
                title: '操作',
                dataIndex: 'action',
                key: 'action',
                width: 300,
                slots: { customRender: 'action' },
            },
        ];
        var jsonToString = function (jsonString, key) {
            var json = JSON.parse(jsonString);
            var value = '';
            for (var i = 0; i < json.length; i++) {
                if (i > 0) {
                    value += ',';
                }
                value += json[i][key];
            }
            return value;
        };
        var handleTableChange = function (pagination) {
            TemplateQueryList({
                page: pagination.current,
                size: pagination.pageSize,
            });
        };
        var handleQuery = function (params) {
            TemplateQueryList({
                page: params.page,
                size: params.size,
            });
        };
        var add = function () {
            modelVisible.value = true;
            templateEntity.value = {
                customAttributeItems: []
            };
        };
        var edit = function (record) {
            console.log("rd" + record.customAttributeItems);
            // findTemplateById(record.id);
            var jsonBrand = JSON.parse(record.brandIds);
            var jsonSpec = JSON.parse(record.specIds);
            var jsonItems = JSON.parse(record.customAttributeItems);
            console.log(111 + jsonItems);
            var jsonBrandList = [];
            var jsonSpecList = [];
            jsonBrand.forEach(function (item) {
                jsonBrandList.push({
                    value: item.id,
                    key: item.id,
                    option: {
                        value: item.id,
                        label: item.text,
                    }
                });
            });
            jsonSpec.forEach(function (item) {
                jsonSpecList.push({
                    value: item.id,
                    key: item.id,
                    option: {
                        value: item.id,
                        label: item.text,
                    }
                });
            });
            templateEntity.value.brandIds = jsonBrandList;
            templateEntity.value.specIds = jsonSpecList;
            templateEntity.value.customAttributeItems = jsonItems;
            console.log("at" + templateEntity.value.customAttributeItems);
            templateEntity.value.name = record.name;
            templateEntity.value.id = record.id;
            console.log(jsonSpec);
            console.log(templateEntity.value.specIds);
            // console.log(templateEntity.value.brandIds)
            // console.log(jsonBrandList)
            modelVisible.value = true;
        };
        var del = function (id) {
            axios.delete('/mall-manager/template-Ms/deleteTemplate/' + id).then(function (response) {
                if (response.data.code == 200) {
                    message.info('删除成功');
                    //加载最新新闻列表
                    TemplateQueryList({
                        page: pagination.value.current,
                        size: pagination.value.pageSize
                    });
                }
                else {
                    message.error(response.data.message);
                }
            });
        };
        var addTableRow = function () {
            templateEntity.value.customAttributeItems.push([]);
        };
        var deleTableRow = function (index) {
            templateEntity.value.customAttributeItems.splice(index, 1);
        };
        var handleModelOk = function () {
            modalloading.value = false;
            var templateList = [];
            templateEntity.value.brandIds.forEach(function (item) {
                templateList.push({
                    id: item.option.value,
                    text: item.option.label
                });
            });
            //转json对象（集合） 为字符串
            templateEntity.value.brandIds = JSON.stringify(templateList);
            // console.log(111+templateEntity.value.brandIds)
            var specList = [];
            templateEntity.value.specIds.forEach(function (item) {
                console.log("sss" + templateEntity.value.specIds);
                specList.push({
                    id: item.option.value,
                    text: item.option.label
                });
            });
            var optionList = [];
            templateEntity.value.customAttributeItems.forEach(function (item) {
                console.log(item + "OOOO");
                optionList.push({
                    // id:item.option.value,
                    text: item.text
                });
            });
            //转json对象（集合） 为字符串
            templateEntity.value.brandIds = JSON.stringify(templateList);
            //转json对象（集合） 为字符串
            templateEntity.value.brandIds = JSON.stringify(templateList);
            // console.log(11111+templateEntity.value.brandIds)
            // console.log(111+templateEntity.value)
            templateEntity.value.specIds = JSON.stringify(specList);
            console.log("aa" + templateEntity.value.specIds);
            templateEntity.value.customAttributeItems = JSON.stringify(optionList);
            console.log("v" + templateEntity.value.customAttributeItems);
            // console.log(111+templateEntity.value.brandIds)
            axios.post('/mall-manager/template-Ms/saveTemplate', templateEntity.value).then(function (response) {
                modalloading.value = false;
                if (response.data.code == 200) {
                    modelVisible.value = false;
                    handleQuery({
                        page: pagination.value.current,
                        size: pagination.value.pageSize
                    });
                    message.info("操作成功");
                }
                else {
                    message.error(response.data.message);
                }
            });
        };
        var TemplateQueryList = function (params) {
            modalloading.value = true;
            axios
                .get('http://localhost:8899/mall-manager/TemplateByPage', {
                params: {
                    page: params.page,
                    size: params.size,
                },
            })
                .then(function (response) {
                modalloading.value = false;
                TemplateList.value = response.data.list;
                console.log(response.data.list[0].specIds);
                pagination.value.current = params.page;
                pagination.value.total = response.data.total;
            })
                .catch(function (error) {
                console.error(error);
                modalloading.value = false;
            });
        };
        var findBrandList = function () {
            axios.get('http://localhost:8899/mall-manager/queryBrand').then(function (response) {
                brandList.value = response.data;
            });
        };
        var findSpecList = function () {
            axios.get('http://localhost:8899/mall-manager/querySpecification').then(function (response) {
                specificationList.value = response.data;
            });
        };
        var findTemplateById = function (id) {
            axios.get('http://localhost:8899/mall-manager/findTemplateById/' + id).then(function (response) {
                if (response.data.code == 200) {
                    console.log(response.data.data);
                    templateEntity.value = response.data.data;
                }
                else {
                    message.error(response.data.message);
                }
            });
        };
        onMounted(function () {
            TemplateQueryList({
                page: 1,
                size: pagination.value.pageSize,
            });
            findBrandList();
            findSpecList();
            // findTemplateList();
        });
        return {
            handleModelOk: handleModelOk,
            modalloading: modalloading,
            modelVisible: modelVisible,
            handleQuery: handleQuery,
            columns: columns,
            templateEntity: templateEntity,
            TemplateList: TemplateList,
            jsonToString: jsonToString,
            pagination: pagination,
            handleTableChange: handleTableChange,
            del: del,
            add: add,
            edit: edit,
            maxTagTextLength: maxTagTextLength,
            brandList: brandList,
            brandOptions: brandOptions,
            specificationList: specificationList,
            findBrandList: findBrandList,
            findSpecList: findSpecList,
            // findTemplateList,
            addTableRow: addTableRow,
            deleTableRow: deleTableRow,
            findTemplateById: findTemplateById
        };
    },
});
//# sourceMappingURL=admin-template.vue.js.map
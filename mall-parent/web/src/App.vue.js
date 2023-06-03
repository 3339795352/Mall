-layout;
id = "components-layout-demo-top-side-2" >
    -header > /the-header>
    < router - view > /router-view>
    < the - footer > /the-footer>
    < /a-layout>
    < /template>
    < script;
lang = "ts" >
;
import { defineComponent } from 'vue';
import TheHeader from '@/components/the-header.vue';
import TheFooter from '@/components/the-footer.vue';
export default defineComponent({
    name: "app",
    components: {
        TheHeader: TheHeader,
        TheFooter: TheFooter
    }
});
/script>
    < style >
     - layout - demo - top - side - 2..logo;
{
    float: left;
    width: 120;
    px;
    height: 31;
    px;
    margin: 16;
    px;
    24;
    px;
    16;
    px;
    0;
    background: rgba(255, 255, 255, 0.3);
}
ant - row - rtl;
 - layout - demo - top - side - 2..logo;
{
    float: right;
    margin: 16;
    px;
    0;
    16;
    px;
    24;
    px;
}
site - layout - background;
{
    background: #fff;
}
/style>;
//# sourceMappingURL=App.vue.js.map
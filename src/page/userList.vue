<template>
    <div class="fillcontain">
        <head-top>
        </head-top>
        <el-button type="primary" @click="perform" style="text-align: left;margin-top: 10px; margin-left: 20px;" >下载</el-button>
        <div class="table_container">
            <el-table
                :data="tableData"
                highlight-current-row
                style="width: 100%">
                <el-table-column
                  type="index"
                  label="序号"
                  width="100">
                </el-table-column>
                <el-table-column
                  property="registe_time"
                  label="注册日期"
                  width="220">
                </el-table-column>
                <el-table-column
                  property="username"
                  label="用户姓名"
                  width="220">
                </el-table-column>
                <el-table-column
                    property="phone"
                    label="手机号"
                >
                </el-table-column>

            </el-table>
            <div class="Pagination" style="text-align: left;margin-top: 10px;">
                <el-pagination
                  @size-change="handleSizeChange"
                  @current-change="handleCurrentChange"
                  :current-page="currentPage"
                  :page-size="10"
                  layout=" total,prev,pager, next"
                  :total="count">
                </el-pagination>
            </div>
        </div>
    </div>
</template>

<script>
    import headTop from '../components/headTop'
    import { getUserList, getUserCount} from '@/api/getData'
    export default {
        data(){
            return {
                tableTitle:[],
                tableData: [
                 //假数据
                //  {
                //     registe_time: '2016-05-02',
                //     username: '王小虎',
                //     city: '上海市普陀区金沙江路 1518 弄'
                // }, {
                //     registe_time: '2016-05-04',
                //     username: '王小虎',
                //     city: '上海市普陀区金沙江路 1517 弄'
                // }, {
                //     registe_time: '2016-05-01',
                //     username: '王小虎',
                //     city: '上海市普陀区金沙江路 1519 弄'
                // }, {
                //     registe_time: '2016-05-03',
                //     username: '王小虎',
                //     city: '上海市普陀区金沙江路 1516 弄'
                // }
                ],
                currentRow: null,
                offset: 0,
                limit: 10,
                count: 0,
                currentPage: 1,
            };
        },
    	components: {
    		headTop,
    	},
        created(){
            this.initData();
        },
        methods: {

            async initData() {
                try {
                    //获取用户数量
                    const respObject = await getUserCount();
                    if(respObject.responseModal.code == "1") {
                        this.count = respObject.data;
                    } else {
                        throw new Error(respObject.responseModal.msg);
                    }
                    this.getUsers();
                } catch (err) {
                    console.log('获取数据失败', err);
                }
            },
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
            },

            //前端插件自动帮你实现分页的计算
            handleCurrentChange(val) {
                this.currentPage = val;
                // this.offset = (val - 1) * this.limit;   // offset : 之前的条数
                this.getUsers()
            },

            //用户列表
            async getUsers() {
                const userData = await getUserList({ currPage: this.currentPage, limit: this.limit, count: this.count });
                const rows = userData.data.page.rows;
                // alert(rows);
                this.tableData = [];
                rows.forEach(item => {
                    const tableData = {};
                    tableData.username = item.username;
                    tableData.registe_time = item.create_time;
                    tableData.phone = item.phone;
                    this.tableData.push(tableData);
                });
                console.log(this.tableData.valueOf());

                const columns = userData.data.columns;
                this.tableTitle = [];
                columns.forEach(item => {n
                    this.tableTitle.push(item);
                })
            },

            /**
             * 下载本页数据
             */
            async perform() {
                window.open("/download/userList?limit="+ this.limit + "&currPage=" + this.currentPage + "&count=" + this.count);
            },


        }
    }
</script>

<style lang="less">
	@import '../style/mixin';
    .table_container{
        padding: 20px;
    }
</style>

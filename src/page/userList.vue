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
                  :label="this.tableTitle[0]"
                  width="220">
                </el-table-column>
                <el-table-column
                  property="username"
                  :label="this.tableTitle[1]"
                  width="220">
                </el-table-column>
                <el-table-column
                    property="phone"
                    :label="this.tableTitle[2]"
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
    import {download, getUserList, getUserCount} from '@/api/getData'
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
                    const countData = await getUserCount();
                    if (typeof countData == 'string') {
                        data = JSON.parse(data);
                        alert(data.data);
                        console.log(data.data);
                    }

                    // alert(countData.valueOf());
                    // alert(countData.getResponseModal().valueOf());
                    if (countData.code == 'OK') {
                        this.count = countData.data;
                    } else {
                        throw new Error(countData.msg);
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
                this.offset = (val - 1) * this.limit;
                this.getUsers()
            },

            //用户列表
            async getUsers() {
                const userData = await getUserList({ offset: this.offset, limit: this.limit });

                const rows = userData.data.page.rows;
                this.tableData = [];
                rows.forEach(item => {
                    const tableData = {};
                    tableData.username = item.username;
                    tableData.registe_time = item.create_time;
                    tableData.phone = item.phone;
                    this.tableData.push(tableData);
                })
                console.log(this.tableData.valueOf());

                const columns = userData.data.columns;
                this.tableTitle = [];
                columns.forEach(item => {
                    this.tableTitle.push(item);
                })
            },

            //下载
            async perform() {
                window.open(download("userList"))
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

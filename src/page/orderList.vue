<template>
    <div class="fillcontain">
        <head-top></head-top>
        <div class="table_container">
            <el-table
                :data="tableData"
                @expand='expand'
                :expand-row-keys='expendRow'
                :row-key="row => row.index"
                style="width: 100%">
                <el-table-column type="expand">
                    <template scope="props">
                        <el-form label-position="left" inline class="demo-table-expand">
                            <el-form-item label="订单Id">
                                <span>{{ props.row.id }}</span>
                            </el-form-item>
                            <el-form-item label="接收人">
                                <span>{{ props.row.receiver_name }}</span>
                            </el-form-item>
                            <el-form-item label="总价格">
                                <span>{{ props.row.payment }}</span>
                            </el-form-item>
                            <el-form-item label="联系方式">
                                <span>{{ props.row.receiver_mobile }}</span>
                            </el-form-item>
                            <el-form-item label="订单状态">
                                <span>{{ props.row.status}}</span>
                            </el-form-item>
                            <el-form-item label="支付方式">
                                <span>{{ props.row.payment_type }}</span>
                            </el-form-item>
                            <el-form-item label="创建时间">
                                <span>{{ props.row.create_time }}</span>
                            </el-form-item>
                            <el-form-item label="订购商品">
                                <div  v-for="orderItem in props.row.orderItems" >
                                    <span>
                                        {{orderItem.title}}
                                    *
                                    {{ orderItem.num }}
                                    </span>
                                </div>
                            </el-form-item>

                        </el-form>
                    </template>
                </el-table-column>
                <el-table-column
                    label="订单 ID"
                    prop="id">
                </el-table-column>
                <el-table-column
                    label="总价格"
                    prop="payment">
                </el-table-column>
                <el-table-column
                    label="创建时间"
                    prop="create_time">
                </el-table-column>
            </el-table>
            <div class="Pagination" style="text-align: left;margin-top: 10px;">
                <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="currentPage"
                    :page-size="20"
                    layout="total, prev, pager, next"
                    :total="count">
                </el-pagination>
            </div>
        </div>
    </div>
</template>

<script>
    import headTop from '../components/headTop'
    import {getOrderList, getOrderCount} from '@/api/getData'

    export default {
        data() {
            return {
                tableData: [],
                orderItems: [],
                currentRow: null,
                offset: 0,
                limit: 20,
                count: 0,
                currentPage: 1,
                restaurant_id: null,
                expendRow: [],
            }
        },
        components: {
            headTop,
        },
        created() {
            this.restaurant_id = this.$route.query.restaurant_id;
            this.initData();
        },
        mounted() {

        },
        methods: {
            async initData() {
                try {
                    const respObject = await getOrderCount();
                    if (respObject.responseModal.code == "1") {
                        this.count = respObject.data;
                    } else {
                        throw new Error(respObject.responseModal.msg);
                    }
                    this.getOrders();
                } catch (err) {
                    console.log('获取数据失败', err);
                }
            },
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
            },
            handleCurrentChange(val) {
                this.currentPage = val;
                // this.offset = (val - 1)*this.limit;
                this.getOrders()
            },
            async getOrders() {
                const respObject = await getOrderList({
                    currPage: this.currentPage,
                    limit: this.limit,
                    count: this.count
                });

                if (respObject.responseModal.code == "1") {
                    const orders = respObject.data.page.rows;
                    this.tableData = [];
                    orders.forEach((order, index) => {
                        const tableData = {};
                        // alert(JSON.stringify(order.orderShipping.receiver_name));

                        tableData.id = order.order_id;
                        tableData.payment = order.payment;

                        if(order.payment_type == 1) {
                            tableData.payment_type =  "在线支付"
                        } else if(order.payment_type == 2) {
                            tableData.payment_type =  "货到付款"
                        } else {
                            tableData.payment_type =  "货到付款"
                        }
                        // tableData.payment_type = order.payment_type;
                        tableData.status = order.status;
                        tableData.create_time = order.create_time;
                        tableData.orderItems = order.orderItems;

                        // tableData.num = order.orderItems;
                        // tableData.title = order.orderItems;
                        // tableData.total_fee = order.orderItems;
                        tableData.receiver_name = order.orderShipping.receiver_name;
                        tableData.receiver_mobile = order.orderShipping.receiver_mobile;
                        tableData.index = index;
                        this.tableData.push(tableData);
                    })
                } else {
                    throw new Error(respObject.responseModal.msg);
                }
            },
            async expand(row, status) {
                if (status) {
                    const restaurant = await getResturantDetail(row.restaurant_id);
                    const userInfo = await getUserInfo(row.user_id);
                    const addressInfo = await getAddressById(row.address_id);

                    this.tableData.splice(row.index, 1, {
                        ...row, ...{
                            restaurant_name: restaurant.name,
                            restaurant_address: restaurant.address,
                            address: addressInfo.address,
                            user_name: userInfo.username
                        }
                    });
                    this.$nextTick(() => {
                        this.expendRow.push(row.index);
                    })
                } else {
                    const index = this.expendRow.indexOf(row.index);
                    this.expendRow.splice(index, 1)
                }
            },
        },
    }
</script>

<style lang="less">
    @import '../style/mixin';

    .table_container {
        padding: 20px;
    }

    .demo-table-expand {
        font-size: 0;
    }

    .demo-table-expand label {
        width: 90px;
        color: #99a9bf;
    }

    .demo-table-expand .el-form-item {
        margin-right: 0;
        margin-bottom: 0;
        width: 50%;
    }
</style>

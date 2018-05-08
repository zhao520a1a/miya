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
                <el-table-column type="expand" >
                    <template scope="props"  >
                        <el-form label-position="left" inline class="demo-table-expand">
                            <el-form-item :label="151611">
                                <span>{{ props.row.item_id }} </span>
                            </el-form-item>
                            <el-form-item :label="props.row.value">
                                <span>{{ props.row.item_id }} </span>
                            </el-form-item>
                            <el-form-item :label="this.tableTitle[0]">
                                <span>{{ props.row.item_id }} </span>
                            </el-form-item>
                            <el-form-item :label="this.tableTitle[1]">
                                <span>{{ props.row.status }}</span>
                            </el-form-item>
                            <el-form-item :label="this.tableTitle[2]">
                                <span>{{ props.row.cname }}</span>
                            </el-form-item>
                            <el-form-item :label="this.tableTitle[3]">
                                <span>{{ props.row.sell_point }}</span>
                            </el-form-item>
                            <el-form-item :label="this.tableTitle[4]" >
                                <span>{{ props.row.num }}</span>
                            </el-form-item>
                            <el-form-item :label="this.tableTitle[5]">
                                <span>{{ props.row.price }}</span>
                            </el-form-item>
                            <el-form-item :label="this.tableTitle[6]">
                                <span>{{ props.row.month_sales }}</span>
                            </el-form-item>
                            <el-form-item :label="this.tableTitle[7]">
                                <span>{{ props.row.barcode }}</span>
                            </el-form-item>
                            <el-form-item :label="this.tableTitle[8]">
                                <span>{{ props.row.create_time }}</span>
                            </el-form-item>
                            <el-form-item :label="this.tableTitle[9]">
                                <span>{{ props.row.update_time }}</span>
                            </el-form-item>
                            <el-form-item :label="this.tableTitle[10]">
                                <span>{{ props.row.title }}</span>
                            </el-form-item>
                            <el-form-item :label="this.tableTitle[11]">
                                <span>{{ props.row.image_path }}</span>
                            </el-form-item>
                            <el-form-item :label="this.tableTitle[12]">
                                <span>{{ props.row.desc }}</span>
                            </el-form-item>
                        </el-form>
                    </template>
                </el-table-column>

                <el-table-column
                    :label="this.tableTitle[10]"
                    prop="title">
                </el-table-column>
                <el-table-column
                    :label="this.tableTitle[4]"
                    prop="num">
                </el-table-column>
                <el-table-column
                    :label="this.tableTitle[5]"
                    prop="price">
                </el-table-column>
                <el-table-column label="操作" width="160">
                    <template scope="scope">
                        <el-button
                            size="small"
                            @click="handleEdit(scope.row)">编辑
                        </el-button>
                        <el-button
                            size="small"
                            type="danger"
                            @click="handleDelete(scope.$index, scope.row)">删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="Pagination">
                <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="currentPage"
                    :page-size="20"
                    layout="total, prev, pager, next"
                    :total="count">
                </el-pagination>
            </div>


            <el-dialog title="修改商品信息" v-model="dialogFormVisible">
                <el-form :model="selectTable">
                    <el-form-item label="商品名称" label-width="100px">
                        <el-input v-model="selectTable.price" auto-complete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="商品介绍" label-width="100px">
                        <el-input v-model="selectTable.description"></el-input>
                    </el-form-item>
                    <el-form-item label="商品类别" label-width="100px">
                        <el-select v-model="selectIndex" :placeholder="selectCat.label" @change="handleSelect">
                            <el-option
                                v-for="item in CatOptions"
                                :key="item.value"
                                :label="item.label"
                                :value="item.index">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="商品图片" label-width="100px">
                        <el-upload
                            class="avatar-uploader"
                            :action="baseUrl + '/pic/upload'"
                            :show-file-list="false"
                            :on-success="handleServiceAvatarScucess"
                            :before-upload="beforeAvatarUpload">
                            <img v-if="selectTable.image_path" :src="baseImgPath + selectTable.image_path"
                                 class="avatar">
                            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                        </el-upload>
                    </el-form-item>
                </el-form>
                <el-row style="overflow: auto; text-align: center;">
                    <el-table
                        :data="specs"
                        style="margin-bottom: 20px;"
                        :row-class-name="tableRowClassName">
                        <el-table-column
                            prop="specs"
                            label="规格">
                        </el-table-column>
                        <el-table-column
                            prop="packing_fee"
                            label="包装费">
                        </el-table-column>
                        <el-table-column
                            prop="price"
                            label="价格">
                        </el-table-column>
                        <el-table-column label="操作">
                            <template scope="scope">
                                <el-button
                                    size="small"
                                    type="danger"
                                    @click="deleteSpecs(scope.$index)">删除
                                </el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <el-button type="primary" @click="specsFormVisible = true" style="margin-bottom: 10px;">添加规格
                    </el-button>
                </el-row>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogFormVisible = false">取 消</el-button>
                    <el-button type="primary" @click="updateItem">确 定</el-button>
                </div>
            </el-dialog>


            <el-dialog title="添加规格" v-model="specsFormVisible">
                <el-form :rules="specsFormrules" :model="specsForm">
                    <el-form-item label="规格" label-width="100px" prop="specs">
                        <el-input v-model="specsForm.specs" auto-complete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="包装费" label-width="100px">
                        <el-input-number v-model="specsForm.packing_fee" :min="0" :max="100"></el-input-number>
                    </el-form-item>
                    <el-form-item label="价格" label-width="100px">
                        <el-input-number v-model="specsForm.price" :min="0" :max="10000"></el-input-number>
                    </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="specsFormVisible = false">取 消</el-button>
                    <el-button type="primary" @click="addspecs">确 定</el-button>
                </div>
            </el-dialog>
        </div>
    </div>
</template>

<script>
    import headTop from '../components/headTop'
    import {baseUrl, baseImgPath} from '@/config/env'
    import {
        getItemList,
        getItemCount,
        getItemDescInfo,

        getCat,
        updateItem,
        deleteItem,
        getCatById
    } from '@/api/getData'

    export default {
        data() {
            return {
                tableTitle:[],
                baseUrl,
                baseImgPath,
                city: {},
                c_id: {},
                offset: 0,
                limit: 20,
                count: 0,
                tableData: [],
                currentPage: 1,
                selectTable: {},
                dialogFormVisible: false,
                CatOptions: [],
                selectCat: {},
                selectIndex: null,
                specsForm: {
                    specs: '',
                    packing_fee: 0,
                    price: 20,
                },
                specsFormrules: {
                    specs: [
                        {required: true, message: '请输入规格', trigger: 'blur'},
                    ],
                },
                specsFormVisible: false,
                expendRow: [],
            }
        },
        created() {
            this.c_id = this.$route.query.c_id;
            this.initData();
        },
        computed: {
            specs: function () {
                let specs = [];
                if (this.selectTable.specItems) {
                    this.selectTable.specItems.forEach(item => {
                        specs.push({
                            specs: item.specs_name,
                            express_fee: item.express_fee,
                            price: item.price,
                        })
                    })
                }
                return specs
            }
        },
        components: {
            headTop,
        },
        methods: {
            async initData() {
                try {
                    const countData = await getItemCount();
                    if (countData.code == 'OK') {
                        this.count = countData.data;
                    } else {
                        throw new Error(countData.msg);
                    }
                    this.getItems();
                } catch (err) {
                    console.log('获取数据失败', err);
                }
            },
            //商品列表
            async getItems() {
                const Items = await getItemList({offset: this.offset, limit: this.limit});

                const rows = Items.data.page.rows;
                this.tableData = [];
                rows.forEach(item => {
                    const tableData = {};
                    tableData.item_id = item.id;
                    tableData.title = item.title;
                    tableData.sell_point = item.sell_point;
                    tableData.price = item.price;
                    tableData.num = item.num;
                    tableData.barcode = item.barcode;
                    tableData.image_path = item.image;
                    tableData.cid = item.cid;
                    tableData.status = item.status;
                    tableData.create_time = item.create_time;
                    tableData.update_time = item.update_time;
                    tableData.month_sales = item.month_sales;  //Todo 月销量
                    this.tableData.push(tableData);
                });
                console.log(this.tableData.valueOf());

                const columns = Items.data.columns;  //表头
                this.tableTitle = [];
                columns.forEach(item => {
                    this.tableTitle.push(item);
                });
                console.log(this.tableTitle.valueOf());

            },

            //商品种类
            async getCat() {
                this.CatOptions = [];
                try {
                    const Cat = await getItemCat({itemCatIds: this.selectTable.c_id, allCat: true});
                    Cat.forEach((item, index) => {
                        this.CatOptions.push({
                            label: item.name,
                            value: item.id,
                            index,
                        })
                    })
                } catch (err) {
                    console.log('获取商品种类失败', err);
                }
            },
            tableRowClassName(row, index) {
                if (index === 1) {
                    return 'info-row';
                } else if (index === 3) {
                    return 'positive-row';
                }
                return '';
            },
            addspecs() {
                this.specs.push({...this.specsForm});
                this.specsForm.specs = '';
                this.specsForm.packing_fee = 0;
                this.specsForm.price = 20;
                this.specsFormVisible = false;
            },
            deleteSpecs(index) {
                this.specs.splice(index, 1);
            },
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
            },
            handleCurrentChange(val) {
                this.currentPage = val;
                this.offset = (val - 1) * this.limit;
                this.getItems()
            },
            expand(row, status) {
                alert(row.value() + status);
                console.log(row.valueOf());
                // alert(row.index.valueOf());
                alert(row.length);
                alert(row.cid);
                alert(row.category_id);
                if (status) {
                    this.getSelectItemData(row)
                } else {
                    const index = this.expendRow.indexOf(row.index);
                    this.expendRow.splice(index, 1)
                }
            },
            handleEdit(row) {
                this.getSelectItemData(row, 'edit')
                this.dialogFormVisible = true;
            },
            async getSelectItemData(row, type) {
                //TOdo 获得商家信息***
                const desc = await getItemDescInfo(row.category_id); // 获得商品描述信息
                const category = await getCatById(row.category_id); // 获得商品类别信息
                this.selectTable = {   //摘要字段集合——————
                    ...row, ...{
                        //Todo 注入商家信息
                        category_name: category.data.name
                    }
                };

                this.selectCat = {label: category.data.name, value: row.category_id}
                this.tableData.splice(row.index, 1, {...this.selectTable});
                this.$nextTick(() => {
                    this.expendRow.push(row.index);
                })
                if (type == 'edit' && this.c_id != row.c_id) {
                    this.getCat();
                }
            },
            handleSelect(index) {
                this.selectIndex = index;
                this.selectCat = this.CatOptions[index];
            },
            async handleDelete(index, row) {
                try {
                    const res = await deleteItem(row.item_id);
                    if (res.status == 1) {
                        this.$message({
                            type: 'success',
                            message: '删除商品成功'
                        });
                        this.tableData.splice(index, 1);
                    } else {
                        throw new Error(res.message)
                    }
                } catch (err) {
                    this.$message({
                        type: 'error',
                        message: err.message
                    });
                    console.log('删除商品失败')
                }
            },
            handleServiceAvatarScucess(res, file) {
                alert(this.selectTable.image_path)
                if (res.status == 1) {
                    this.selectTable.image_path = res.image_path;
                } else {
                    this.$message.error('上传图片失败！');
                }
            },
            beforeAvatarUpload(file) {
                console.log(file.valueOf());
                alert(this.selectTable.image_path)

                const isRightType = (file.type === 'image/jpeg') || (file.type === 'image/png');
                const isLt2M = file.size / 1024 / 1024 < 2;

                if (!isRightType) {
                    this.$message.error('上传头像图片只能是 JPG 格式!');
                }
                if (!isLt2M) {
                    this.$message.error('上传头像图片大小不能超过 2MB!');
                }
                return isRightType && isLt2M;
            },
            async updateItem() {
                this.dialogFormVisible = false;
                try {
                    const subData = {new_category_id: this.selectCat.value, specs: this.specs};
                    const postData = {...this.selectTable, ...subData};
                    const res = await updateItem(postData)
                    if (res.status == 1) {
                        this.$message({
                            type: 'success',
                            message: '更新商品信息成功'
                        });
                        this.getItems();
                    } else {
                        this.$message({
                            type: 'error',
                            message: res.message
                        });
                    }
                } catch (err) {
                    console.log('更新餐馆信息失败', err);
                }
            },
        },
    }
</script>

<style lang="less">
    @import '../style/mixin';

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

    .table_container {
        padding: 20px;
    }

    .Pagination {
        display: flex;
        justify-content: flex-start;
        margin-top: 8px;
    }

    .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }

    .avatar-uploader .el-upload:hover {
        border-color: #20a0ff;
    }

    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 120px;
        height: 120px;
        line-height: 120px;
        text-align: center;
    }

    .avatar {
        width: 120px;
        height: 120px;
        display: block;
    }
</style>

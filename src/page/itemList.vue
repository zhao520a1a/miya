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
                    <!--"商品编号", "类别名称",  "商品名称", "商品卖点", "库存数量",  "月销量",
                    "商品价格", "好评率", "商品状态");-->
                    <template scope="props" :data="tableTitle">
                        <el-form label-position="left" inline class="demo-table-expand">
                            <el-form-item :label="tableTitle[0]">
                                <span>{{ props.row.view_id }} </span>
                            </el-form-item>
                            <el-form-item :label="tableTitle[1]">
                                <span>{{ props.row.cname }}</span>
                            </el-form-item>
                            <el-form-item :label="tableTitle[2]">
                                <span>{{ props.row.title }}</span>
                            </el-form-item>
                            <el-form-item :label="tableTitle[3]">
                                <span>{{ props.row.sell_point }}</span>
                            </el-form-item>
                            <el-form-item :label="tableTitle[4]">
                                <span>{{ props.row.stock }}</span>
                            </el-form-item>
                            <el-form-item :label="tableTitle[5]">
                                <span>{{ props.row.month_sales }}</span>
                            </el-form-item>
                            <el-form-item :label="tableTitle[6]">
                                <span>{{ props.row.price }}</span>
                            </el-form-item>
                            <el-form-item :label="tableTitle[7]">
                                <span>{{ props.row.satisfy_rate }}</span>
                            </el-form-item>
                            <el-form-item :label="tableTitle[8]">
                                <span>{{ props.row.status }}</span>
                            </el-form-item>
                            <el-form-item :label="tableTitle[9]">
                                <span>{{ props.row.update_time }}</span>
                            </el-form-item>
                        </el-form>
                    </template>
                </el-table-column>

                <el-table-column
                    :label="this.tableTitle[0]"
                    prop="view_id">
                </el-table-column>
                <el-table-column
                    :label="this.tableTitle[2]"
                    prop="title">
                </el-table-column>
                <el-table-column
                    :label="this.tableTitle[7]"
                    prop="satisfy_rate">
                </el-table-column>
                <el-table-column label="操作" width="160">
                    <template scope="scope">
                        <el-button
                            size="small"
                            @click="handleEdit(scope.row)">编辑
                        </el-button>
                        <!--直接删除-->
                        <!--<el-button-->
                            <!--size="small"-->
                            <!--type="danger"-->
                            <!--@click="handleDelete(scope.$index, scope.row)">删除-->
                        <!--</el-button> -->
                        <!--有弹出框-->
                        <el-button
                            size="small"
                            type="danger"
                            @click="beforeItemDelete(scope.$index, scope.row)">删除
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
                    <el-form-item label="标题" label-width="100px">
                        <el-input v-model="selectTable.title" auto-complete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="卖点" label-width="100px">
                        <el-input v-model="selectTable.sell_point"></el-input>
                    </el-form-item>
                    <!--商品类别-->
                    <el-form-item label="类别" label-width="100px">
                        <el-select v-model="selectCatIndex" :placeholder="selectCat.label" @change="handleSelectCat">
                            <el-option
                                v-for="item in CatOptions"
                                :key="item.value"
                                :label="item.label"
                                :value="item.index">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="价格" label-width="100px">
                        <el-input-number v-model="selectTable.price" :min="0" :max="10000"></el-input-number>
                    </el-form-item>
                    <!--商品状态-->
                    <el-form-item label="状态" label-width="100px">
                        <el-select v-model="selectStatusIndex" :placeholder="selectStatus.label"
                                   @change="handleSelectStatus">
                            <el-option
                                v-for="item in StatusOptions"
                                :key="item.value"
                                :label="item.label"
                                :value="item.index">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="图片" label-width="100px">
                        <el-upload ref="upload"
                                   class="avatar-uploader"
                                   :action="baseUrl + '/item/pic/upload'"
                                   :multiple="true"
                                   :auto-upload='true'
                                   :on-remove="handleRemove"
                                   :file-list="imageFileList"
                                   :show-file-list="true"
                                   :on-success="uploadImg"
                                   :before-upload="beforeImgUpload">
                            <img v-if="selectTable.image_path" :src="baseImgPath + selectTable.image_path"
                                 class="avatar">
                            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                        </el-upload>
                    </el-form-item>
                    <el-form-item label-width="30px">
                        <div style=""> 描述</div>
                        <vue-edit :content.sync="desc" @on-content-change="onContentChange"></vue-edit>
                    </el-form-item>
                </el-form>


                <el-row style="overflow: auto;  margin-top:60px">
                    <el-table
                        :data="specs"
                        style="margin-bottom: 20px;"
                        :row-class-name="tableRowClassName">
                        <el-table-column
                        prop="group"
                        label="组名">
                        </el-table-column>
                        <el-table-column
                            prop="key"
                            label="参数">
                        </el-table-column>
                        <el-table-column
                            prop="value"
                            label="信息">
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
                    <el-form-item label="组名" label-width="100px" prop="group">
                        <el-input v-model="specsForm.group" auto-complete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="参数" label-width="100px">
                        <el-input v-model="specsForm.key" auto-complete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="信息" label-width="100px">
                        <el-input v-model="specsForm.value" auto-complete="off"></el-input>
                    </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="specsFormVisible = false">取 消</el-button>
                    <el-button type="primary" @click="addspecs">确 定</el-button>
                </div>
            </el-dialog>

            <el-dialog title="删除商品" v-model="deleteFormVisible">
                <el-form :model="selectTable">
                    <el-form-item>
                        <span>   确定删除名为<b>{{selectTable.title}}  </b>的商品吗？
                      </span>
                    </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="deleteFormVisible = false">取 消</el-button>
                    <el-button type="primary" @click="handleDelete(selectTable.index, selectTable)">确 定</el-button>
                </div>
            </el-dialog>


        </div>
    </div>
</template>

<script>
    import headTop from '../components/headTop'
    import vueEdit from '../components/vueEdit'
    import {baseUrl, baseImgPath} from '@/config/env'
    import {
        getItemList,
        getItemCount,
        getItemCat,
        getItemDescInfo,
        getItemParam,
        deleteItem,
        updateItem,
        deleteItemPic,
        getItemPicInfo
    } from '@/api/getData'

    export default {
        data() {
            return {
                imageFileList: [
                    // {
                    //     "status": "success",
                    //     "name": "2.jpg",
                    //     "size": "",
                    //     "percentage": 100,
                    //     "uid": 1524216693810,
                    //     "raw": {
                    //         "uid": 1524216693810
                    //     },
                    //     "url": "blob:http://localhost:8002/6505ba02-699b-d042-8bcc-924146fefa0a",
                    //     "response": {
                    //         "responseModal": {
                    //             "code": 1,
                    //             "msg": "图片上传成功"
                    //         },
                    //         "data": "http://120.78.222.191/group1/M00/00/01/wKgBMFrZs3aACEbNAAGOlWJD7Sc656.jpg"
                    //     }
                    // }

                ],
                tableTitle: [],
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
                StatusOptions: [],
                desc: '文本编辑',
                selectCat: {},
                selectStatus: {},
                selectCatIndex: null,
                selectStatusIndex: null,
                specsForm: {},
                specs: [],
                specsStr: "",
                specsFormrules: {
                    group: [
                        {required: true, message: '请输入规格', trigger: 'blur'},
                    ],
                },
                specsFormVisible: false,
                deleteFormVisible: false,
                expendRow: [],
            };
        },
        created() {
            this.initData();
        },
        computed: {},
        components: {
            headTop,
            vueEdit,
        },
        methods: {

            /*—————————— 初始化数据 ——————————*/
            async initData() {
                try {
                    const respObject = await getItemCount();
                    // alert(JSON.stringify(respObject));
                    if (respObject.responseModal.code == "1") {
                        this.count = respObject.data;
                        this.getItems();
                    } else {
                        throw new Error(respObject.responseModal.msg);
                    }
                } catch (err) {
                    console.log('获取数据失败', err);
                }
            },
            //商品列表
            async getItems() {
                const itemData = await getItemList({currPage: this.currentPage, limit: this.limit, count: this.count});

                const rows = itemData.data.page.rows;
                this.tableData = [];
                rows.forEach(item => {
                    const tableData = {};
                    tableData.id = item.id;
                    tableData.view_id = item.view_id;
                    tableData.title = item.title;
                    tableData.sell_point = item.sell_point;
                    tableData.price = item.price / 100;
                    tableData.stock = item.stock;
                    tableData.barcode = item.barcode;
                    tableData.image_path = item.image.split(",")[0];   //只显示第一张图片
                    tableData.imageArr = item.image.split(",");  //数组格式
                    tableData.image = item.image;  //字符串格式
                    tableData.cid = item.cid;
                    tableData.cname = item.cname;
                    tableData.status = item.status;
                    tableData.satisfy_rate = item.satisfy_rate;
                    tableData.express_fee = item.express_fee;
                    tableData.month_sales = item.month_sales;
                    tableData.sold_cat = item.sold_cat;
                    tableData.create_time = item.create_time;
                    tableData.update_time = item.update_time;
                    this.tableData.push(tableData);
                });

                const columns = itemData.data.columns;
                this.tableTitle = [];
                columns.forEach(item => {
                    this.tableTitle.push(item);
                });
                console.log("商品表头：" + this.tableTitle.valueOf());

            },
            tableRowClassName(row, index) {
                if (index === 1) {
                    return 'info-row';
                } else if (index === 3) {
                    return 'positive-row';
                }
                return '';
            },
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
            },
            handleCurrentChange(val) {
                this.currentPage = val;
                // this.offset = (val - 1) * this.limit;
                this.getItems()
            },
            /*—————————— 初始化数据 ——————————*/

            expand(row, status) {
                console.log("展开——关闭")
                // if (status) {
                //     this.getSelectItemData(row)
                // } else {
                //     const index = this.expendRow.indexOf(row.index);
                //     this.expendRow.splice(index, 1)
                // }
            },


            /* ——————————  编辑商品 —————————— */
            handleEdit(row) {
                this.specs = [];
                this.getSelectItemData(row, 'edit')
                this.dialogFormVisible = true;
            },
            async getSelectItemData(row, type) {
                // alert("ROW: " + JSON.stringify(row));

                this.selectTable = {   //摘要字段集合——————
                    ...row
                };

                this.selectCat = {label: row.cname, value: row.cid}
                this.selectStatus = {label: row.status, value: row.id}

                this.tableData.splice(row.index, 1, {...this.selectTable});
                this.$nextTick(() => {
                    this.expendRow.push(row.index);
                });

                if (type == 'edit') {
                    this.getCat();
                    this.getStatus();
                    this.getDesc(row);
                    this.getSpecs(row);
                    //坑：后台返回的数据为''，因此数组的第一元素为'' 导致数组莫名的长度为1，这里需手动置空数组[]
                    if (this.selectTable.imageArr.length == 1 && this.selectTable.imageArr[0] == '') {
                        // alert("数组置空");
                        this.selectTable.imageArr = [];
                    }
                    this.getImageInfo(this.selectTable.imageArr)
                }
            },
            //获得商品描述信息
            async getDesc(row) {
                try {
                    const respObject = await getItemDescInfo({id: row.id}); // 获得商品描述信息
                    if (respObject.responseModal.code == 1) {
                        this.desc = respObject.data.item_desc
                    } else {
                        throw new Error(respObject.responseModal.msg);
                    }
                } catch (err) {
                    console.log('获取商品描述信息失败', err);
                }
            },
            //获得商品规格信息
            async getSpecs(row) {
                try {
                    const result = await getItemParam({id: row.id});
                    // alert(JSON.stringify(result));
                    if (result.responseModal.code === 1) {
                        var param = result.data;
                        var paramArr = JSON.parse(param);

                        paramArr.forEach((paramData, index) => {
                            // let paramMaps = paramData.params;
                            // let paramMaps = paramData.param_data;
                            // alert(JSON.stringify(paramData));

                            // paramMaps.forEach((paramMap, index) => {
                            let param = new Object();
                            param.group = paramData.group;
                            param.key = paramData.key;
                            param.value = paramData.value;
                            // param.key = paramMap.k;
                            // param.value = paramMap.v;
                            this.specs.push({...param});
                            // })
                        });
                        // alert(JSON.stringify(this.specs));

                    } else {
                        throw new Error(result.responseModal.msg);
                    }
                } catch (err) {
                    console.log('获取商品规格信息失败', err);
                }
            },
            //获得商品种类列表
            async getCat() {
                this.CatOptions = [];
                try {
                    const respList = await getItemCat({itemCatIds: this.selectTable.cid, allCat: true});
                    if (respList.responseModal.code === 1) {
                        respList.data.forEach((item, index) => {
                            this.CatOptions.push({
                                label: item.name,
                                value: item.id,
                                index,
                            })
                        });
                    } else {
                        throw new Error(respList.responseModal.msg);
                    }
                } catch (err) {
                    console.log('获取商品种类失败', err);
                }
            },
            //获得商品状态
            async getStatus() {
                this.StatusOptions = [];
                try {
                    var status1 = new Object();
                    status1.label = "正常";
                    status1.value = 1;
                    status1.index = 0;
                    var status2 = new Object();
                    status2.label = "下架";
                    status2.value = 2;
                    status2.index = 1;
                    this.StatusOptions.push(status1);
                    this.StatusOptions.push(status2);
                } catch (err) {
                    console.log('获取商品状态失败', err);
                }
            },
            //获得图片信息列表
            async getImageInfo(imageUrlList) {
                this.imageFileList = [];
                try {

                    // fileList 中元素的格式如下
                    // {
                    //     "status": "success",
                    //     "name": "2.jpg",
                    //     "size": "",
                    //     "percentage": 100,
                    //     "uid": 1524216693810,
                    //     "raw": {
                    //     "uid": 1524216693810
                    // },
                    //     "url": "blob:http://localhost:8002/6505ba02-699b-d042-8bcc-924146fefa0a",
                    //     "response": {
                    //     "responseModal": {
                    //         "code": 1,
                    //             "msg": "图片上传成功"
                    //     },
                    //     "data": "http://120.78.222.191/group1/M00/00/01/wKgBMFrZs3aACEbNAAGOlWJD7Sc656.jpg"
                    // }
                    // }
                    //
                    var _this = this;
                    // imageUrlList.forEach(function(value,i){  -- 一个坑，此种便利方式不能向后台发送请求
                    for (var i = 0; i < imageUrlList.length; i++) {
                        // alert(i + "====" + imageUrlList[i]);

                        const result = await getItemPicInfo({fileId: imageUrlList[i]});
                        if (result.responseModal.code === 1) {
                            var data = result.data;
                            var o = new Object();

                            //创建FileList中的元素结构
                            o.status = "success";
                            o.name = i + 1;
                            o.percentage = 100;

                            o.size = data.fileSize;
                            o.uid = data.createTimestamp;
                            var raw = new Object();
                            raw.uid = data.createTimestamp;
                            o.raw = raw;
                            o.url = imageUrlList[i];
                            o.response = result;
                            o.response.data = imageUrlList[i];

                            this.imageFileList.push(o);
                        } else {
                            throw new Error(result.responseModal.msg);
                        }

                    }
                } catch (err) {
                    console.log('获取图片信息失败', err);
                }
            },


            /* ———————————— 选择框 ——————————*/
            handleSelectCat(index) {
                this.selectCatIndex = index;
                this.selectCat = this.CatOptions[index];
            },
            handleSelectStatus(index) {
                this.selectStatusIndex = index;
                this.selectStatus = this.StatusOptions[index];
            },

            /* ——————————  删除商品 —————————— */
            beforeItemDelete(index,row) {
                row.index = index;
                this.getSelectItemData(row, 'delete');
                this.deleteFormVisible = true;
            },
            async handleDelete(index, row) {
                this.deleteFormVisible = false;

                try {
                    const respModel = await deleteItem(row.id);
                    if (respModel.code == 1) {
                        this.$message({
                            type: 'success',
                            message: '删除商品成功'
                        });
                        this.tableData.splice(index, 1);
                    } else {
                        throw new Error(respModel.msg)
                    }
                } catch (err) {
                    this.$message({
                        type: 'error',
                        message: '删除商品失败'
                    });
                    console.log(err);
                }
            },

            /*———————— 上传图片 ————————*/
            async deleteImg(fileId) {
                try {
                    this.selectTable.imageArr.splice(this.selectTable.imageArr.indexOf(fileId), 1);
                    this.selectTable.image_path = this.selectTable.imageArr[this.selectTable.imageArr.length - 1];

                    //修改时并没有实际删除图片; 图片服务器没有处理
                    // const result = await deleteItemPic({fileId: fileId});
                    // if (result.code === 1) {
                    // alert("删除成功");
                    // } else {
                    //     throw new Error(result.msg);
                    // }

                } catch (err) {
                    console.log('删除图片失败', err);
                }
            },
            handleRemove(file, fileList) {
                this.deleteImg(file.response.data);
            },
            uploadImg(res, file) {
                if (res.responseModal.code == '1') {
                    this.selectTable.image_path = res.data;
                    this.selectTable.imageArr.push(res.data);
                } else {
                    this.$message.error('上传图片失败！');
                    return false
                }
            },
            beforeImgUpload(file) {
                console.log(file.valueOf());
                // alert(this.selectTable.image_path)

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


            onContentChange(val) {
                // alert(val);
                this.desc = val;
            },


            /*---- 提交修改商品请求 —————— */
            async updateItem() {
                this.dialogFormVisible = false;
                try {
                    this.specsStr = JSON.stringify(this.specs);
                    // const subData = {cid: this.selectCat.value, specsStr: this.specsStr, description: this.desc};
                    // const postData = {...this.selectTable, ...subData};

                    this.selectTable.cid = this.selectCat.value;
                    this.selectTable.specsStr = this.specsStr;
                    this.selectTable.description = this.desc;
                    this.selectTable.price = this.selectTable.price * 100;
                    this.selectTable.image = this.selectTable.imageArr.toString();
                    // alert(this.selectTable.image)
                    const postData = {...this.selectTable};
                    const res = await updateItem(postData)
                    if (res.code == 1) {
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


            //添加规格
            addspecs() {
                this.specs.push({...this.specsForm});
                this.specsForm.group = '';
                this.specsForm.key = "";
                this.specsForm.value = "";
                this.specsFormVisible = false;
            },
            deleteSpecs(index) {
                this.specs.splice(index, 1);
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

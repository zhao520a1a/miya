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
                            <el-form-item label="内容Id">
                                <span>{{ props.row.id }} </span>
                            </el-form-item>
                            <el-form-item label="类别">
                                <span>{{ props.row.cname }}</span>
                            </el-form-item>
                            <el-form-item label="标题">
                                <span>{{ props.row.title }}</span>
                            </el-form-item>
                            <el-form-item label="子标题">
                                <span>{{ props.row.sub_title }}</span>
                            </el-form-item>
                            <el-form-item label="标题描述">
                                <span>{{ props.row.title_desc }}</span>
                            </el-form-item>
                            <el-form-item label="链接">
                                <span>{{ props.row.url }}</span>
                            </el-form-item>
                            <el-form-item label="创建时间">
                                <span>{{ props.row.create_time }}</span>
                            </el-form-item>
                            <el-form-item label="修改时间">
                                <span>{{ props.row.update_time }}</span>
                            </el-form-item>
                            <el-form-item label="内容">
                                <span>{{ props.row.content }}</span>
                            </el-form-item>
                            <el-form-item v-if="props.row.pic1_path" label="图片1">
                                <span>{{ props.row.pic1_path }}</span>
                                <img :src="props.row.pic1_path" class="avatar">
                            </el-form-item>
                            <el-form-item v-if="props.row.pic2_path" label="图片2">
                                <img :src="props.row.pic2_path" class="avatar">
                                <span>{{ props.row.pic2_path }}</span>
                            </el-form-item>
                        </el-form>
                    </template>
                </el-table-column>

                <el-table-column
                    label="内容ID"
                    prop="id">
                </el-table-column>
                <el-table-column
                    label="标题"
                    prop="title">
                </el-table-column>
                <el-table-column
                    label="链接"
                    prop="url">
                </el-table-column>
                <el-table-column label="操作" width="160">
                    <template scope="scope">
                        <el-button
                            size="small"
                            @click="handleEdit(scope.row)">编辑
                        </el-button>
                        <!--<el-button-->
                            <!--size="small"-->
                            <!--type="danger"-->
                            <!--@click="handleDelete(scope.$index, scope.row)">删除-->
                        <!--</el-button>-->
                        <el-button
                            size="small"
                            type="danger"
                            @click="beforeContentDelete(scope.$index, scope.row)">删除
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


            <el-dialog title="修改内容信息" v-model="dialogFormVisible">
                <el-form :model="selectTable">
                    <el-form-item label="标题" label-width="100px">
                        <el-input v-model="selectTable.title" auto-complete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="子标题" label-width="100px">
                        <el-input v-model="selectTable.sub_title"></el-input>
                    </el-form-item>
                    <!--内容类别-->
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
                    <el-form-item label="链接" label-width="100px">
                        <el-input v-model="selectTable.url"></el-input>
                    </el-form-item>
                    <el-form-item label="标题描述" label-width="100px">
                        <el-input v-model="selectTable.title_desc"></el-input>
                    </el-form-item>

                    <el-form-item label="图片1" label-width="100px">
                        <el-upload
                            class="avatar-uploader"
                            :action="baseUrl + '/content/pic/upload'"
                            :on-success="uploadImg1"
                            :show-file-list="false"
                            :before-upload="beforeImgUpload1">
                            <img v-if="selectTable.pic1_path" :src="baseImgPath + selectTable.pic1_path" class="avatar">
                            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                        </el-upload>
                    </el-form-item>
                    <el-form-item label="图片2" label-width="100px">
                        <el-upload
                            class="avatar-uploader"
                            :action="baseUrl + '/content/pic/upload'"
                            :show-file-list="false"
                            :on-success="uploadImg2"
                            :before-upload="beforeImgUpload2">
                            <img v-if="selectTable.pic2_path" :src="baseImgPath + selectTable.pic2_path" class="avatar">
                            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                        </el-upload>
                    </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogFormVisible = false">取 消</el-button>
                    <el-button type="primary" @click="updateContent">确 定</el-button>
                </div>
            </el-dialog>


            <el-dialog title="删除内容" v-model="deleteFormVisible">
                <el-form :model="selectTable">
                    <el-form-item>
                        <span>   确定删除名为<b>{{selectTable.title}}  </b>的内容吗？
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
        getContentList,
        getContentCount,
        getContentCat,
        deleteContent,
        updateContent,
        deleteContentPic,
        getContentPicInfo
    } from '@/api/getData'

    export default {
        data() {
            return {
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
                deleteFormVisible: false,
                CatOptions: [],
                desc: {},
                selectCat: {},
                selectCatIndex: null,
                expendRow: [],
            }
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
                    const respObject = await getContentCount();
                    if (respObject.responseModal.code == "1") {
                        this.count = respObject.data;
                    } else {
                        throw new Error(respObject.responseModal.msg);
                    }
                    this.getContents();
                } catch (err) {
                    console.log('获取数据失败', err);
                }
            },
            //内容列表
            async getContents() {
                const itemData = await getContentList({
                    currPage: this.currentPage,
                    limit: this.limit,
                    count: this.count
                });

                if (itemData.responseModal.code == "1") {
                    const rows = itemData.data.page.rows;
                    this.tableData = [];
                    rows.forEach(item => {
                        const tableData = {};
                        tableData.id = item.id;
                        tableData.title = item.title;
                        tableData.sub_title = item.sub_title;
                        tableData.title_desc = item.title_desc;
                        tableData.url = item.url;
                        tableData.pic1_path = item.pic1_path;
                        tableData.pic2_path = item.pic2_path;
                        tableData.cid = item.category_id;
                        tableData.cname = item.category_name;
                        tableData.content = item.content;
                        tableData.create_time = item.create_time;
                        tableData.update_time = item.update_time;
                        this.tableData.push(tableData);
                    });

                } else {
                    throw new Error(respObject.responseModal.msg);
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
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
            },
            handleCurrentChange(val) {
                this.currentPage = val;
                // this.offset = (val - 1) * this.limit;
                this.getContents()
            },
            /*—————————— 初始化数据 ——————————*/

            expand(row, status) {
                console.log("展开——关闭")
                // if (status) {
                //     this.getSelectContentData(row)
                // } else {
                //     const index = this.expendRow.indexOf(row.index);
                //     this.expendRow.splice(index, 1)
                // }
            },


            /* ——————————  编辑内容 —————————— */
            handleEdit(row) {
                this.specs = [];
                this.getSelectContentData(row, 'edit');
                this.dialogFormVisible = true;
            },
            async getSelectContentData(row, type) {
                // alert("ROW: " + JSON.stringify(row));
                this.selectTable = {   //摘要字段集合——————
                    ...row
                };
                this.selectCat = {label: row.cname, value: row.cid};
                this.selectStatus = {label: row.status, value: row.id};

                this.tableData.splice(row.index, 1, {...this.selectTable});
                this.$nextTick(() => {
                    this.expendRow.push(row.index);
                });
                if (type == 'edit') {
                    this.getCat();
                    this.getImageInfo1(this.selectTable.pic1_path );
                    this.getImageInfo2(this.selectTable.pic2_path);
                }
            },


            //获得内容种类列表
            async getCat() {
                this.CatOptions = [];
                try {
                    const respList = await getContentCat({contentCatIds: this.selectTable.cid, allCat: true});
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
                    console.log('获取内容种类失败', err);
                }
            },




            /* ———————————— 选择框 ——————————*/
            handleSelectCat(index) {
                this.selectCatIndex = index;
                this.selectCat = this.CatOptions[index];
            },


            /*———————— 删除内容 ————————*/
            beforeContentDelete(index,row) {
                row.index = index;
                this.getSelectContentData(row, 'delete');
                this.deleteFormVisible = true;
            },

            async handleDelete(index, row) {
                this.deleteFormVisible = false;

                try {
                    const respModel = await deleteContent(row.id);
                    if (respModel.code == 1) {
                        this.$message({
                            type: 'success',
                            message: '删除内容成功'
                        });
                        this.tableData.splice(index, 1);
                    } else {
                        throw new Error(respModel.msg)
                    }
                } catch (err) {
                    this.$message({
                        type: 'error',
                        message:  '删除内容失败'
                    });
                    console.log('删除内容失败')
                }
            },

            /*———————— 上传图片 ————————*/

            beforeImgUpload1(file) {
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
                this.deleteImg(this.selectTable.pic1_path);
                return isRightType && isLt2M;
            },

            beforeImgUpload2(file) {
                console.log(file.valueOf());
                // alert(this.selectTable.image_path)

                // const isRightType = (file.type === 'image/jpeg') || (file.type === 'image/png');
                const isLt2M = file.size / 1024 / 1024 < 2;

                // if (!isRightType) {
                //     this.$message.error('上传头像图片只能是 JPG 格式!');
                // }
                if (!isLt2M) {
                    this.$message.error('上传头像图片大小不能超过 2MB!');
                }
                this.deleteImg(this.selectTable.pic2_path);
                // return isRightType && isLt2M;
                return  isLt2M;
            },

            uploadImg1(res, file) {
                if (res.responseModal.code == '1') {
                    // alert(res.data);
                    this.selectTable.pic1_path = res.data;
                } else {
                    this.$message.error('上传图片失败！');
                    return false
                }
            },
            uploadImg2(res, file) {
                if (res.responseModal.code == '1') {
                    // alert(res.data);
                    this.selectTable.pic2_path = res.data;
                } else {
                    this.$message.error('上传图片失败！');
                }
            },
            async deleteImg(fileId) {
                try {
                    const result = await deleteContentPic({fileId: fileId});
                    if (result.code === 1) {
                        // alert("删除成功");
                    } else {
                        throw new Error(result.msg);
                    }
                } catch (err) {
                    console.log('获取内容种类失败', err);
                }
            },


            /*---- 修改内容请求 —————— */
            async updateContent() {
                this.dialogFormVisible = false;
                try {
                    const subData = {category_id: this.selectCat.value};
                    const postData = {...this.selectTable, ...subData};
                    // alert(JSON.stringify(postData));

                    const res = await updateContent(postData)
                    if (res.code == 1) {
                        this.$message({
                            type: 'success',
                            message: '更新内容信息成功'
                        });
                        this.getContents();
                    } else {
                        this.$message({
                            type: 'error',
                            message: res.message
                        });
                    }
                } catch (err) {
                    console.log('更新内容信息失败', err);
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

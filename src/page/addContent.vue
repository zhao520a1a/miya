<template>
    <div>
        <head-top></head-top>
        <el-row style="margin-top: 20px;">
            <el-col :span="14" :offset="4">
                <header class="form_header">选择内容种类</header>
                <el-form :model="categoryForm" :rules="categoryrules" ref="categoryForm" label-width="110px"
                         class="form">
                    <el-row class="category_select">
                        <el-form-item label="内容种类">
                            <el-select v-model="categoryForm.categorySelect" :placeholder="selectValue.label"
                                       style="width:100%;">
                                <el-option
                                    v-for="item in categoryForm.categoryList"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.index">
                                </el-option>
                            </el-select>
                        </el-form-item>
                    </el-row>
                    <el-row class="add_category_row" :class="showAddCategory? 'showEdit': ''">
                        <div class="add_category">
                            <el-form-item label="内容种类" prop="cname">
                                <el-input v-model="categoryForm.cname"></el-input>
                            </el-form-item>
                            <el-form-item label="种类描述" prop="description">
                                <el-input v-model="categoryForm.description"></el-input>
                            </el-form-item>
                            <el-form-item>
                                <el-button type="primary" @click="submitCategoryForm('categoryForm')">提交</el-button>
                            </el-form-item>
                        </div>
                    </el-row>
                    <div class="add_category_button" @click="addCategoryFun">
                        <i class="el-icon-caret-top edit_icon" v-if="showAddCategory"></i>
                        <i class="el-icon-caret-bottom edit_icon" v-else slot="icon"></i>
                        <span>添加内容种类</span>
                    </div>
                </el-form>
                <header class="form_header">添加内容</header>
                <el-form :model="contentForm" :rules="contentrules" ref="contentForm" label-width="110px"
                         class="form content_form">
                    <el-form-item label="标题" prop="title">
                        <el-input v-model="contentForm.title"></el-input>
                    </el-form-item>
                    <el-form-item label="子标题" prop="sub_title">
                        <el-input v-model="contentForm.sub_title"></el-input>
                    </el-form-item>
                    <el-form-item label="标题描述" prop="title_desc">
                        <el-input v-model="contentForm.title_desc"></el-input>
                    </el-form-item>
                    <el-form-item label="链接" prop="url">
                        <el-input v-model="contentForm.url"></el-input>
                    </el-form-item>
                    <el-form-item label="内容详情" prop="content">
                        <el-input v-model="contentForm.content"></el-input>
                    </el-form-item>

                    <el-form-item label="图片1">
                        <el-upload
                            class="avatar-uploader"
                            :action="baseUrl + '/content/pic/upload'"
                            :multiple="true"
                            :auto-upload='true'
                            :on-remove="handleRemove1"
                            :show-file-list="false"
                            :on-success="uploadImg1"
                            :before-upload="beforeImgUpload1">
                            <img v-if="contentForm.pic1_path" :src="baseImgPath + contentForm.pic1_path" class="avatar">
                            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                        </el-upload>
                    </el-form-item>
                    <el-form-item label="图片2">
                        <el-upload
                            class="avatar-uploader"
                            :action="baseUrl + '/content/pic/upload'"
                            :multiple="true"
                            :auto-upload='true'
                            :on-remove="handleRemove2"
                            :show-file-list="false"
                            :on-success="uploadImg2"
                            :before-upload="beforeImgUpload2">
                            <img v-if="contentForm.pic2_path" :src="baseImgPath + contentForm.pic2_path" class="avatar">
                            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                        </el-upload>
                    </el-form-item>

                    <el-form-item class="button_submit">
                        <el-button style="" type="primary" @click="addContent('contentForm')">确认添加内容</el-button>
                    </el-form-item>
                </el-form>


            </el-col>
        </el-row>
    </div>
</template>

<script>
    import headTop from '@/components/headTop'
    import vueEdit from '../components/vueEdit'
    import {getContentCat, addCategory, addContent, deleteContentPic} from '@/api/getData';

    import {baseUrl, baseImgPath,} from '@/config/env'

    export default {
        data() {
            return {
                baseUrl,
                baseImgPath,

                restaurant_id: 1,
                categoryForm: {
                    categoryList: [],
                    categorySelect: '',
                    cname: '',
                    cid: '',
                    description: '',
                },
                contentForm: {
                    category_id: '',
                    title: '',
                    sub_title: '',
                    title_desc: '',
                    url: '',
                    content: '',
                    pic1_path: "",
                    pic2_path: "",
                    attributes: [],
                },
                contentrules: {
                    title: [
                        {required: true, message: '请输入内容名称', trigger: 'blur'},
                    ],
                },
                categoryrules: {
                    cname: [
                        {required: true, message: '请输入内容种类名称', trigger: 'blur'},
                    ],
                },
                showAddCategory: false,
                dialogFormVisible: false,

            }
        },
        components: {
            headTop,
            vueEdit,
        },
        created() {
            // if (this.$route.query.restaurant_id) {
            //    this.restaurant_id = this.$route.query.restaurant_id;
            // }else{
            // 	this.restaurant_id = Math.ceil(Math.random()*10);
            // 	this.$msgbox({
            //       title: '提示',
            //       message: '添加内容需要选择一个商铺，先去就去选择商铺吗？',
            //       showCancelButton: true,
            //       confirmButtonText: '确定',
            //       cancelButtonText: '取消',
            //       beforeClose: (action, instance, done) => {
            //         if (action === 'confirm') {
            //           this.$router.push('/shopList');
            //           done();
            //         } else {
            //         	this.$message({
            //             type: 'info',
            //             message: '取消'
            //         });
            //           	done();
            //         }
            //       }
            //     })
            // }
            this.initData();
        },
        computed: {
            selectValue: function () {
                // alert("---" +this.categoryForm.categorySelect);
                // alert("---" + JSON.stringify(this.categoryForm.categoryList[this.categoryForm.categorySelect]));
                return this.categoryForm.categoryList[this.categoryForm.categorySelect] || {}
            }
        },
        methods: {
            async initData() {
                this.getCat();
            },

            //获得内容种类列表
            async getCat() {
                try {
                    const respList = await getContentCat({contentCatIds: -1, allCat: true});
                    if (respList.responseModal.code === 1) {
                        respList.data.forEach((item, index) => {
                            this.categoryForm.categoryList.push({
                                label: item.name,  // contentCatName
                                value: item.id,   //contentCatId
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

            addCategoryFun() {
                this.showAddCategory = !this.showAddCategory;
            },
            submitCategoryForm(categoryForm) {
                this.$refs[categoryForm].validate(async (valid) => {
                    if (valid) {
                        const params = {
                            cname: this.categoryForm.cname,
                            description: this.categoryForm.description,
                        };
                        try {
                            // const result = await addCategory(params);
                            // if (result.code == 1) {
                            //     this.initData();
                            this.categoryForm.cname = '';
                            this.categoryForm.description = '';
                            this.showAddCategory = false;
                            this.$message({
                                type: 'success',
                                message: '添加成功'
                            });
                            // }
                        } catch (err) {
                            console.log(err)
                        }
                    } else {
                        this.$notify.error({
                            title: '错误',
                            message: '请检查输入是否正确',
                            offset: 100
                        });
                        return false;
                    }
                });
            },

            uploadImg1(res, file) {
                if (res.responseModal.code == '1') {
                    // alert(res.data);
                    this.contentForm.pic1_path = res.data;
                } else {
                    this.$message.error('上传图片失败！');
                    return false
                }
            },
            uploadImg2(res, file) {
                if (res.responseModal.code == '1') {
                    // alert(res.data);
                    this.contentForm.pic2_path = res.data;
                } else {
                    this.$message.error('上传图片失败！');
                }
            },
            async deleteImg(fileId) {
                try {
                    const result = await deleteContentPic({fileId: fileId});
                    if (result.code === 1) {
                    } else {
                        throw new Error(result.msg);
                    }
                } catch (err) {
                    console.log('获取内容种类失败', err);
                }
            },
            handleRemove1(file, fileList) {
                if(!file.response.data) {
                    this.deleteImg(file.response.data);
                }
                this.contentForm.pic1_path = "";
            },

            handleRemove2(file, fileList) {
                if(file.response.data != "") {
                    this.deleteImg(file.response.data);
                }
                this.contentForm.pic2_path = "";
            },
            beforeImgUpload1(file) {
                console.log("文件信息： " + file.size, file.type)
                // const isRightType = (file.type === 'image/jpeg') || (file.type === 'image/png');
                const isLt2M = file.size / 1024 / 1024 < 2;

                // if (!isRightType) {
                //     this.$message.error('上传头像图片只能是 JPG 格式!');
                // }
                if (!isLt2M) {
                    this.$message.error('上传头像图片大小不能超过 2MB!');
                }
                this.deleteImg(this.contentForm.pic1_path);
                // return isRightType && isLt2M;
                return isLt2M;
            },
            beforeImgUpload2(file) {
                console.log("文件信息： " + file.size, file.type)
                const isRightType = (file.type === 'image/jpeg') || (file.type === 'image/png');
                const isLt2M = file.size / 1024 / 1024 < 2;

                if (!isRightType) {
                    this.$message.error('上传头像图片只能是 JPG 格式!');
                }
                if (!isLt2M) {
                    this.$message.error('上传头像图片大小不能超过 2MB!');
                }
                this.deleteImg(this.contentForm.pic2_path);
                return isRightType && isLt2M;
            },

            tableRowClassName(row, index) {
                if (index === 1) {
                    return 'info-row';
                } else if (index === 3) {
                    return 'positive-row';
                }
                return '';
            },
            onContentChange(val) {
                this.contentForm.description = val;
            },
            addContent(contentForm) {
                this.$refs[contentForm].validate(async (valid) => {
                    if (valid) {
                        // alert("selectValue:" + JSON.stringify(this.selectValue.value)  );
                        if (!this.selectValue.value) {
                            this.$message({
                                type: 'error',
                                message: "请选择类别"
                            });
                            return false;
                        } else {
                            this.contentForm.category_id = this.selectValue.value;
                        }

                        const params = {
                            ...this.contentForm,
                        };
                        try {
                            const result = await addContent(params);
                            if (result.code == 1) {
                                console.log(result)
                                this.$message({
                                    type: 'success',
                                    message: '添加成功'
                                });
                                this.contentForm = {
                                    category_id: '',
                                    title: '',
                                    sub_title: '',
                                    title_desc: '',
                                    url: '',
                                    content: '',
                                    pic1_path: "",
                                    pic2_path: "",
                                    attributes: [],
                                }
                            } else {
                                this.$message({
                                    type: 'error',
                                    message: result.msg
                                });
                            }
                        } catch (err) {
                            console.log(err)
                        }
                    } else {
                        this.$notify.error({
                            title: '错误',
                            message: '请检查输入是否正确',
                            offset: 100
                        });
                        return false;
                    }
                });
            }
        }
    }
</script>

<style lang="less">
    @import '../style/mixin';

    .form {
        min-width: 400px;
        margin-bottom: 30px;
        &:hover {
            box-shadow: 0 0 8px 0 rgba(232, 237, 250, .6), 0 2px 4px 0 rgba(232, 237, 250, .5);
            border-radius: 6px;
            transition: all 400ms;
        }
    }

    .content_form {
        border: 1px solid #eaeefb;
        padding: 10px 10px 0;
    }

    .form_header {
        text-align: center;
        margin-bottom: 10px;
    }

    .category_select {
        border: 1px solid #eaeefb;
        padding: 10px 30px 10px 10px;
        border-top-right-radius: 6px;
        border-top-left-radius: 6px;
    }

    .add_category_row {
        height: 0;
        overflow: hidden;
        transition: all 400ms;
        background: #f9fafc;
    }

    .showEdit {
        height: 185px;
    }

    .add_category {
        background: #f9fafc;
        padding: 10px 30px 0px 10px;
        border: 1px solid #eaeefb;
        border-top: none;
    }

    .add_category_button {
        text-align: center;
        line-height: 40px;
        border-bottom-right-radius: 6px;
        border-bottom-left-radius: 6px;
        border: 1px solid #eaeefb;
        border-top: none;
        transition: all 400ms;
        &:hover {
            background: #f9fafc;
            span, .edit_icon {
                color: #20a0ff;
            }
        }
        span {
            .sc(14px, #999);
            transition: all 400ms;
        }
        .edit_icon {
            color: #ccc;
            transition: all 400ms;
        }
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

    .cell {
        text-align: center;
    }

    .button_submit {
        text-align: right;
    }
</style>

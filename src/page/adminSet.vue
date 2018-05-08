<template>
    <div class="fillcontain">
        <head-top></head-top>
        <header class="admin_title">管理员信息</header>
        <div class="admin_set">
            <ul>
                <li>
                    <span>姓名：</span><span>{{adminInfo.username}}</span>
                </li>
                <li>
                    <span>注册时间：</span><span>{{adminInfo.create_time}}</span>
                </li>
                <li>
                    <span>管理员权限：</span><span>{{adminInfo.admin}}</span>
                </li>
                <li>
                    <span>管理员 ID：</span><span>{{adminInfo.id}}</span>
                </li>
                <li>

                    <span>管理员头像：</span>
                      <img v-if="adminInfo.avatar" :src="baseImgPath + adminInfo.avatar" class="avatar">

                    <!--                  -->
                                        <!--<span>更换头像：</span>-->
                                        <!--<el-upload-->
                                          <!--class="avatar-uploader"-->
                                          <!--:action="baseUrl + '/admin/pic/upload'"-->
                                          <!--:multiple="true"-->
                                          <!--:auto-upload='true'-->
                                          <!--:on-remove="handleRemove"-->
                                          <!--:show-file-list="true"-->
                                          <!--:on-success="uploadImg"-->
                                          <!--:before-upload="beforeImgUpload">-->
                                          <!--<img v-if="adminInfo.avatar" :src="baseImgPath + adminInfo.avatar" class="avatar">-->
                                          <!--<i v-else class="el-icon-plus avatar-uploader-icon"></i>-->
                                            <!--&lt;!&ndash;<el-button slot="trigger" size="small" type="primary">选取文件</el-button>&ndash;&gt;-->
                        <!--&lt;!&ndash;<el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button>&ndash;&gt;-->
                        <!--&lt;!&ndash;<div slot="tip" class="el-upload__tip">只能上传xls/xlsx文件</div>&ndash;&gt;-->
                    <!--</el-upload>    -->

                    <!--
                                      自定义的图片上传弹窗
                                        <div class="upload-container">
                                            <el-button icon='upload' :style="{background:color,borderColor:color}" @click=" dialogVisible=true" type="primary">上传图片
                                            </el-button>
                                            <el-dialog v-model="dialogVisible" :modal="false">
                                                <el-upload
                                                    class="editor-slide-upload"
                                                    action="api/productLine/saveImgInfo.do"
                                                    :data="dataObj"
                                                    :multiple="true"
                                                    :file-list="fileList"
                                                    :show-file-list="true"
                                                    list-type="picture-card"
                                                    :on-remove="handleRemove"
                                                    :on-success="handleImageScucess">
                                                    <el-button size="small" type="primary">点击上传</el-button>
                                                </el-upload>
                                                <el-button @click="dialogVisible = false">取 消</el-button>
                                                <el-button type="primary" @click="handleSubmit">确 定</el-button>
                                            </el-dialog>
                                        </div>
                    -->
                </li>

            </ul>
        </div>
    </div>
</template>

<script>
	import headTop from '../components/headTop'
    import {mapState,mapActions} from 'vuex'
    import {baseUrl, baseImgPath} from '@/config/env'
    import {deleteAdminPic} from '@/api/getData';

    export default {
        data() {
            return {
                baseUrl,
                baseImgPath,
                uploadFile: []
            }
        },
        components: {
            headTop,
        },
        computed: {
            // ...mapState(['adminInfo']),  这样在刷新js后，用户信息会丢失；
            ...mapState(
                {
                    adminInfo: function (state) {//箭头函数会有this的问题
                        let adminInfo = JSON.parse(window.localStorage.getItem('adminInfo'));
                        console.log("Session Admin: " + JSON.stringify(adminInfo));
                        if (adminInfo == null) {
                            this.$router.push('login');
                        }
                        this.$store.dispatch('setUserData', adminInfo);  //设置管理员信息

                        return adminInfo;
                    }
                }
            ),


        },
        methods: {
            ...mapActions(['getAdminData']),


            async deleteImg(fileId) {
                try {
                    const result = await deleteAdminPic({fileId: fileId});
                    if (result.code === 1) {
                        this.adminInfo.avatar = "";
                        // alert("删除成功");
                    } else {
                        throw new Error(result.msg);
                    }
                } catch (err) {
                    console.log('获取内容种类失败', err);
                }
            },
            handleRemove(file, fileList) {
                this.deleteImg(file.response.data);
            },
            uploadImg(res, file) {
                if (res.responseModal.code == '1') {
                    // alert(res.data);
                    this.adminInfo.avatar = res.data;
                } else {
                    this.$message.error('上传图片失败！');
                    return false
                }
            },
            beforeImgUpload(file) {
                console.log("文件信息： " + file.size, file.type)

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
        },
    };
</script>

<style lang="less">
	@import '../style/mixin';
	.explain_text{
		margin-top: 20px;
		text-align: center;
		font-size: 20px;
		color: #333;
	}
    .admin_set{
        width: 60%;
        background-color: #F9FAFC;
        min-height: 400px;
        margin: 20px auto 0;
        border-radius: 10px;
        ul > li{
            padding: 20px;
            span{
                color: #666;
            }
        }
    }
    .admin_title{
        margin-top: 20px;
        .sc(24px, #666);
        text-align: center;
    }
    .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        margin-top: 10px;
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

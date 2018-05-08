<template>
    <div>
     	<head-top></head-top>
        <el-row style="margin-top: 20px;">
  			<el-col :span="14" :offset="4">
  				<header class="form_header">选择商品种类</header>
	  			<el-form :model="categoryForm" :rules="categoryrules" ref="categoryForm" label-width="110px" class="form">
		  			<el-row class="category_select">
		  				<el-form-item label="商品种类">
			  				<el-select v-model="categoryForm.categorySelect" :placeholder="selectValue.label" style="width:100%;">
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
							<el-form-item label="商品种类" prop="cname">
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
						<span>添加商品种类</span>
					</div>
	  			</el-form>
	  			<header class="form_header">添加商品</header>
	  			<el-form :model="goodsForm" :rules="goodsrules" ref="goodsForm" label-width="110px" class="form goods_form">
	  				<el-form-item label="商品名称" prop="title">
						<el-input v-model="goodsForm.title"></el-input>
					</el-form-item>
					<el-form-item label="商品卖点" prop="sell_point">
						<el-input v-model="goodsForm.sell_point"></el-input>
					</el-form-item>
					<el-form-item label="商品价格" prop="price">
                        <el-input-number v-model="goodsForm.price" :min="0" :max="10000"></el-input-number>
					</el-form-item>
                    <el-form-item label="商品入库数量" prop="stock">
                        <el-input-number v-model="goodsForm.stock" :min="0" :max="10000"></el-input-number>
					</el-form-item>
					<el-form-item label="商品图片">
						<el-upload  ref="upload"
						  class="avatar-uploader"
                          :action="baseUrl + '/item/pic/upload'"
                          :multiple="true"
                          :auto-upload='true'
                          :on-remove="handleRemove"
                          :show-file-list="true"
                          :on-success="uploadImg"
                          :before-upload="beforeImgUpload">
						  <img v-if="goodsForm.image_path" :src="baseImgPath + goodsForm.image_path" class="avatar">
						  <i v-else class="el-icon-plus avatar-uploader-icon"></i>
						</el-upload>
					</el-form-item>

                    <el-form-item  label-width="30px" >
                        <div style="" >    商品描述   </div>
                        <vue-edit :content.sync="goodsForm.description"  @on-content-change="onContentChange" ></vue-edit>
                    </el-form-item>
					<!--<el-form-item label="商品特点">-->
						<!--<el-select v-model="goodsForm.attributes" multiple placeholder="请选择">-->
						    <!--<el-option-->
						      	<!--v-for="item in attributes"-->
						      	<!--:key="item.value"-->
						      	<!--:label="item.label"-->
						      	<!--:value="item.value">-->
						    <!--</el-option>-->
					 	<!--</el-select>-->
					<!--</el-form-item>-->
					<el-form-item label="商品规格" style="margin-top:120px">
                        <el-button type="primary" @click="dialogFormVisible = true" style="margin-bottom: 10px;">添加规格</el-button>
                    </el-form-item>

					<el-row style="overflow: auto; text-align: center;">
						<el-table
					    :data="goodsForm.specs"
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
						    <el-table-column label="操作" >
						    <template scope="scope">
						        <el-button
						          size="small"
						          type="danger"
						          @click="handleDelete(scope.$index)">删除</el-button>
						    </template>
						    </el-table-column>
						</el-table>
					</el-row>
					<el-form-item class="button_submit">
						<el-button style="" type="primary" @click="addgoods('goodsForm')">确认添加商品</el-button>
					</el-form-item>
	  			</el-form>

                <el-dialog title="添加规格" v-model="dialogFormVisible">
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
                        <el-button @click="dialogFormVisible = false">取 消</el-button>
                        <el-button type="primary" @click="addspecs">确 定</el-button>
                    </div>
                </el-dialog>
  			</el-col>
  		</el-row>
    </div>
</template>

<script>
 	import headTop from '../components/headTop'
    import vueEdit from '../components/vueEdit'
    import {getItemCat, addCategory, addItem,deleteItemPic} from '@/api/getData'
    import {baseUrl,baseImgPath,} from '@/config/env'
    export default {
    	data(){
    		return {
                baseImgPath,
    			baseUrl,
    			restaurant_id: 1,
    			categoryForm: {
    				categoryList: [],
    				categorySelect: '',
    				cname: '',
    				cid: '',
    				description: '',
    			},
                imageList:[],
    			goodsForm: {
    				cid: '',
                    title: '',
                    sell_point: '',
                    price:"0",
                    description: '',
    				image: '',
    				image_path: '',
    				attributes: [],
    				specs: [],
                    specsStr:""
    			},
    			goodsrules: {
    				title: [
						{ required: true, message: '请输入商品名称', trigger: 'blur' },
					],
    			},
                categoryrules: {
    				cname: [
						{ required: true, message: '请输入商品种类名称', trigger: 'blur' },
					],
    			},
    			attributes: [{
		          	value: '新',
		          	label: '新品'
		        }, {
		          	value: '招牌',
		          	label: '招牌'
		        },],
    			showAddCategory: false,
    			dialogFormVisible: false,
		        specsForm: {},
		        specsFormrules: {
                    group: [
						{ required: true, message: '请输入规格', trigger: 'blur' },
					],
		        }
    		}
    	},
    	components: {
    		headTop,
            vueEdit,
    	},
    	created(){
    		// if (this.$route.query.restaurant_id) {
             //    this.restaurant_id = this.$route.query.restaurant_id;
    		// }else{
    		// 	this.restaurant_id = Math.ceil(Math.random()*10);
    		// 	this.$msgbox({
		    //       title: '提示',
		    //       message: '添加商品需要选择一个商铺，先去就去选择商铺吗？',
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
    		selectValue: function (){
    			return this.categoryForm.categoryList[this.categoryForm.categorySelect]||{}
    		}
    	},
    	methods: {
    		async initData(){
                this.getCat();
    		},

            //获得商品种类列表
            async getCat() {
                try {
                    const respList = await getItemCat({itemCatIds:-1, allCat: true});
                    if(respList.responseModal.code === 1) {
                        respList.data.forEach((item, index) => {
                            this.categoryForm.categoryList.push({
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

		    addCategoryFun(){
		    	this.showAddCategory = !this.showAddCategory;
		    },
		    submitCategoryForm(categoryForm) {
				this.$refs[categoryForm].validate(async (valid) => {
					if (valid) {

                        const params = {
                            cname: this.categoryForm.cname,
                            description: this.categoryForm.description,
                        };
						try{
							// const result = await addCategory(params);
							// if (result.code == 1) {
							// 	this.initData();
                                this.categoryForm.cname = '';
								this.categoryForm.description = '';
								this.showAddCategory = false;
								this.$message({
					            	type: 'success',
					            	message: '添加成功'
					          	});
							// }
						}catch(err){
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



            async deleteImg(fileId) {
                try {
                    const result = await deleteItemPic({fileId: fileId});
                    if (result.code == 1) {
                        // alert("删除成功");
                    } else {
                        throw new Error(result.msg);
                    }

                    this.imageList.splice(this.imageList.indexOf(fileId), 1);
                    // alert(this.imageList[this.imageList.length - 1]);
                    this.goodsForm.image_path = this.imageList[this.imageList.length-1];

                } catch (err) {
                    console.log('删除图片失败', err);
                }
            },
            handleRemove(file, fileList) {
                // alert("FileList: " + JSON.stringify(fileList));
                this.deleteImg(file.response.data);
            },
            uploadImg(res, file) {
                if (res.responseModal.code == '1') {
                    // alert(res.data);
                    this.goodsForm.image_path = res.data;
                    this.imageList.push(res.data);
                    // alert(this.imageList.toString())
                } else {
                    this.$message.error('上传图片失败！');
                }
            },
			beforeImgUpload(file) {
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



			addspecs(){
				this.goodsForm.specs.push({...this.specsForm});
				this.dialogFormVisible = false;
                this.specsForm = {};
			},
			handleDelete(index){
				this.goodsForm.specs.splice(index, 1);
			},
			tableRowClassName(row, index) {
		        if (index === 1) {
		        	return 'info-row';
		        } else if (index === 3) {
		        	return 'positive-row';
		        }
		        return '';
		    },
            onContentChange(val){
                // alert(val);
                this.goodsForm.description = val;
            },
		    addgoods(goodsForm){
		    	this.$refs[goodsForm].validate(async (valid) => {
					if (valid) {
                        // alert("selectValue:" + JSON.stringify(this.selectValue.value)  );
                        if(!this.selectValue.value) {
                            this.$message({
                                type: 'error',
                                message: "请选择类别"
                            });
                            return false;
                        } else {
                            this.goodsForm.cid = this.selectValue.value;
                        }

                        this.goodsForm.specsStr = JSON.stringify(this.goodsForm.specs);
                        this.goodsForm.image = this.imageList.toString();
                        // alert("  Desc:" + this.goodsForm.image);
                        this.goodsForm.price = this.goodsForm.price * 100;

                        const params = {
                            ...this.goodsForm,
                        };
						try{
							const result = await addItem(params);
							if (result.code == 1) {
								console.log(result)
								this.$message({
					            	type: 'success',
					            	message: '添加成功'
					          	});

								//清空内容
                                this.goodsForm = {
                                    name: '',
                                    description: '',
                                    image: '',
                                    image_path: '',
                                    activity: '',
                                    attributes: [],
                                    specs: [],
                                };
                                this.imageList = [];
                                this.$refs.upload.clearFiles();

                            }else{
								this.$message({
					            	type: 'error',
					            	message: result.msg
					          	});
							}
						}catch(err){
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
	.form{
		min-width: 400px;
		margin-bottom: 30px;
		&:hover{
			box-shadow: 0 0 8px 0 rgba(232,237,250,.6), 0 2px 4px 0 rgba(232,237,250,.5);
			border-radius: 6px;
			transition: all 400ms;
		}
	}
	.goods_form{
		border: 1px solid #eaeefb;
		padding: 10px 10px 0;
	}
	.form_header{
		text-align: center;
		margin-bottom: 10px;
	}
	.category_select{
		border: 1px solid #eaeefb;
		padding: 10px 30px 10px 10px;
		border-top-right-radius: 6px;
		border-top-left-radius: 6px;
	}
	.add_category_row{
		height: 0;
		overflow: hidden;
		transition: all 400ms;
		background: #f9fafc;
	}
	.showEdit{
		height: 185px;
	}
	.add_category{
		background: #f9fafc;
		padding: 10px 30px 0px 10px;
		border: 1px solid #eaeefb;
		border-top: none;
	}
	.add_category_button{
		text-align: center;
		line-height: 40px;
		border-bottom-right-radius: 6px;
		border-bottom-left-radius: 6px;
		border: 1px solid #eaeefb;
		border-top: none;
		transition: all 400ms;
		&:hover{
			background: #f9fafc;
			span, .edit_icon{
				color: #20a0ff;
			}
		}
		span{
			.sc(14px, #999);
			transition: all 400ms;
		}
		.edit_icon{
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
	.cell{
		text-align: center;
	}
    .button_submit{
        text-align: right;
    }
</style>

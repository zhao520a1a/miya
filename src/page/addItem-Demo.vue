<template>
    <div>
     	<head-top></head-top>
        <el-row style="margin-top: 20px;">
  			<el-col :span="14" :offset="4">
  				<header class="form_header">选择商品种类</header>
	  			<el-form :model="categoryForm" ref="categoryForm" label-width="110px" class="form">
		  			<el-row class="category_select">
		  				<el-form-item label="商品种类">
			  				<el-select v-model="categoryForm.categorySelect" :placeholder="selectValue.label" style="width:100%;">
							    <el-option
							      	v-for="item in categoryForm.categoryList"
							      	:key="item.value"
							      	:label="item.label"
							      	:value="item.value">
							    </el-option>
						  	</el-select>
						</el-form-item>
					</el-row>
					<el-row class="add_category_row" :class="showAddCategory? 'showEdit': ''">
						<div class="add_category">
							<el-form-item label="商品种类" prop="name">
								<el-input v-model="categoryForm.name"></el-input>
							</el-form-item>
							<el-form-item label="种类描述" prop="description">
								<el-input v-model="categoryForm.description"></el-input>
							</el-form-item>
							<el-form-item>
								<el-button type="primary" @click="submitcategoryForm('categoryForm')">提交</el-button>
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
	  				<el-form-item label="商品名称" prop="name">
						<el-input v-model="goodsForm.name"></el-input>
					</el-form-item>
					<el-form-item label="商品活动" prop="activity">
						<el-input v-model="goodsForm.activity"></el-input>
					</el-form-item>
					<el-form-item label="商品详情" prop="description">
						<el-input v-model="goodsForm.description"></el-input>
					</el-form-item>
					<el-form-item label="上传商品图片">
						<el-upload
						  class="avatar-uploader"
						  :action="baseUrl + '/v1/adding/goods'"
						  :show-file-list="false"
						  :on-success="uploadImg"
						  :before-upload="beforeImgUpload">
						  <img v-if="goodsForm.image_path" :src="baseImgPath + goodsForm.image_path" class="avatar">
						  <i v-else class="el-icon-plus avatar-uploader-icon"></i>
						</el-upload>
					</el-form-item>
					<el-form-item label="商品特点">
						<el-select v-model="goodsForm.attributes" multiple placeholder="请选择">
						    <el-option
						      	v-for="item in attributes"
						      	:key="item.value"
						      	:label="item.label"
						      	:value="item.value">
						    </el-option>
					 	</el-select>
					</el-form-item>
					<el-form-item label="商品规格">
						<el-radio class="radio" v-model="goodsSpecs" label="one">单规格</el-radio>
  						<el-radio class="radio" v-model="goodsSpecs" label="more">多规格</el-radio>
					</el-form-item>
					<el-row v-if="goodsSpecs == 'one'">
						<el-form-item label="包装费">
							<el-input-number v-model="goodsForm.specs[0].packing_fee" :min="0" :max="100"></el-input-number>
						</el-form-item>
						<el-form-item label="价格">
							<el-input-number v-model="goodsForm.specs[0].price" :min="0" :max="10000"></el-input-number>
						</el-form-item>
					</el-row>
					<el-row v-else style="overflow: auto; text-align: center;">
						<el-button type="primary" @click="dialogFormVisible = true" style="margin-bottom: 10px;">添加规格</el-button>
						<el-table
					    :data="goodsForm.specs"
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
					<el-form-item>
						<el-button type="primary" @click="addgoods('goodsForm')">确认添加商品</el-button>
					</el-form-item>
	  			</el-form>
	  			<el-dialog title="添加规格" v-model="dialogFormVisible">
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
				    <el-button @click="dialogFormVisible = false">取 消</el-button>
				    <el-button type="primary" @click="addspecs">确 定</el-button>
				  </div>
				</el-dialog>
  			</el-col>
  		</el-row>
    </div>
</template>

<script>
 	import headTop from '@/components/headTop'
    import {getCategory, addCategory, addgoods} from '@/api/getData'
    import {baseUrl, baseImgPath} from '@/config/env'
    export default {
    	data(){
    		return {
    			baseUrl,
    			baseImgPath,
    			restaurant_id: 1,
    			categoryForm: {
    				categoryList: [],
    				categorySelect: '',
    				name: '',
    				description: '',
    			},
    			goodsForm: {
    				name: '',
    				description: '',
    				image_path: '',
    				activity: '',
    				attributes: [],
    				specs: [{
    					specs: '默认',
			          	packing_fee: 0,
			          	price: 20,
    				}],
    			},
    			goodsrules: {
    				name: [
						{ required: true, message: '请输入商品名称', trigger: 'blur' },
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
    			goodsSpecs: 'one',
    			dialogFormVisible: false,
		        specsForm: {
		          	specs: '',
		          	packing_fee: 0,
		          	price: 20,
		        },
		        specsFormrules: {
		        	specs: [
						{ required: true, message: '请输入规格', trigger: 'blur' },
					],
		        }
    		}
    	},
    	components: {
    		headTop,
    	},
    	created(){
    		if (this.$route.query.restaurant_id) {
    			this.restaurant_id = this.$route.query.restaurant_id;
    		}else{
    			this.restaurant_id = Math.ceil(Math.random()*10);
    			this.$msgbox({
		          title: '提示',
		          message: '添加商品需要选择一个商铺，先去就去选择商铺吗？',
		          showCancelButton: true,
		          confirmButtonText: '确定',
		          cancelButtonText: '取消',
		          beforeClose: (action, instance, done) => {
		            if (action === 'confirm') {
		              this.$router.push('/shopList');
		              done();
		            } else {
		            	this.$message({
				            type: 'info',
				            message: '取消'
				        });
		              	done();
		            }
		          }
		        })
    		}
    		this.initData();
    	},
    	computed: {
    		selectValue: function (){
    			return this.categoryForm.categoryList[this.categoryForm.categorySelect]||{}
    		}
    	},
    	methods: {
    		async initData(){
    			try{
    				const result = await getCategory(this.restaurant_id);
	    			if (result.status == 1) {
	    				result.category_list.map((item, index) => {
	    					item.value = index;
	    					item.label = item.name;
	    				})
	    				this.categoryForm.categoryList = result.category_list;
	    			}else{
	    				console.log(result)
	    			}
    			}catch(err){
    				console.log(err)
    			}
    		},
		    addCategoryFun(){
		    	this.showAddCategory = !this.showAddCategory;
		    },
		    submitcategoryForm(categoryForm) {
				this.$refs[categoryForm].validate(async (valid) => {
					if (valid) {
						const params = {
							name: this.categoryForm.name,
							description: this.categoryForm.description,
							restaurant_id: this.restaurant_id,
						}
						try{
							const result = await addCategory(params);
							if (result.status == 1) {
								this.initData();
								this.categoryForm.name = '';
								this.categoryForm.description = '';
								this.showAddCategory = false;
								this.$message({
					            	type: 'success',
					            	message: '添加成功'
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
			},
			uploadImg(res, file) {
				if (res.code == 'OK') {
					this.goodsForm.image_path = res.image_path;
				}else{
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
				this.specsForm.specs = '';
				this.specsForm.packing_fee = 0;
				this.specsForm.price = 20;
				this.dialogFormVisible = false;
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
		    addgoods(goodsForm){
		    	this.$refs[goodsForm].validate(async (valid) => {
					if (valid) {
						const params = {
							...this.goodsForm,
							category_id: this.selectValue.id,
							restaurant_id: this.restaurant_id,
						}
						try{
							const result = await addgoods(params);
							if (result.code == 'OK') {
								console.log(result)
								this.$message({
					            	type: 'success',
					            	message: '添加成功'
					          	});
					          	this.goodsForm = {
				    				name: '',
				    				description: '',
				    				image_path: '',
				    				activity: '',
				    				attributes: [],
				    				specs: [{
				    					specs: '默认',
							          	packing_fee: 0,
							          	price: 20,
				    				}],
				    			}
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
</style>

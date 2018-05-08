<template>
	<div class="manage_page fillcontain">
		<el-row style="height: 100%;">
	  		<el-col :span="4"  style="min-height: 100%; background-color: #324057;">
				<el-menu :default-active="defaultActive" style="min-height: 100%;" theme="dark" router>
					<el-menu-item index="manage"><i class="el-icon-menu"></i>首页</el-menu-item>
					<el-submenu index="2">
						<template slot="title"><i class="el-icon-document"></i>数据管理</template>
						<el-menu-item index="userList">用户列表</el-menu-item>
						<!--<el-menu-item index="contentCatList">广告分类列表</el-menu-item>-->
						<el-menu-item index="contentList">广告信息列表</el-menu-item>
						<!--<el-menu-item index="itemCatList">商品分类列表</el-menu-item>-->
                        <el-menu-item index="itemList">商品信息列表</el-menu-item>
                        <el-menu-item index="orderList">订单列表</el-menu-item>
                        <!--<el-menu-item index="adminList">管理员列表</el-menu-item>-->
                        <!--<el-menu-item index="shopList">Demo&#45;&#45;商家列表</el-menu-item>-->
                    </el-submenu>
					<el-submenu index="3">
						<template slot="title"><i class="el-icon-plus"></i>添加数据</template>
						<!--<el-menu-item index="addShop">Demo添加商铺</el-menu-item>-->
						<el-menu-item index="addContent">添加广告信息</el-menu-item>
						<el-menu-item index="addItem">添加商品信息</el-menu-item>
					</el-submenu>
					<!--<el-submenu index="4">-->
						<!--<template slot="title"><i class="el-icon-star-on"></i>图表</template>-->
						<!--<el-menu-item index="visitor">用户分布</el-menu-item>-->
						<!-- <el-menu-item index="newMember">用户数据</el-menu-item> -->
					<!--</el-submenu>-->
					<!--<el-submenu index="5">-->
						<!--<template slot="title"><i class="el-icon-edit"></i>编辑</template>-->
						<!-- <el-menu-item index="uploadImg">上传图片</el-menu-item> -->
						<!--<el-menu-item index="vueEdit">文本编辑</el-menu-item>-->
					<!--</el-submenu>-->
					<el-submenu index="6">
						<template slot="title"><i class="el-icon-setting"></i>设置</template>
						<el-menu-item index="adminSet">管理员设置</el-menu-item>
						<!-- <el-menu-item index="sendMessage">发送通知</el-menu-item> -->
					</el-submenu>
					<el-submenu index="7">
						<template slot="title"><i class="el-icon-warning"></i>说明</template>
						<el-menu-item index="explain">说明</el-menu-item>
					</el-submenu>
				</el-menu>
			</el-col>
			<el-col :span="20" style="height: 100%;overflow: auto;">
                <!--禁止缓存-->
                <!--<keep-alive>-->
				    <router-view></router-view>
				<!--</keep-alive>-->
			</el-col>
		</el-row>
  	</div>
</template>

<script>
    export default {
		computed: {
			defaultActive: function(){
				return this.$route.path.replace('/', '');
			}
		},
    }
</script>


<style lang="less" scoped>
	@import '../style/mixin';
	.manage_page{

	}
</style>

<template>
  <div class="app-container">
    <!--查询表单-->
    <div class="search-div">
      <el-form label-width="70px" size="small">
        <el-row>
          <el-col :span="12">
            <el-form-item label="角色名称">
              <el-input style="width: 50%" v-model="params.roleName" placeholder="角色名称"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-button
              type="primary"
              icon="el-icon-search"
              size="mini"
              :loading="loading"
              @click="getSysRoleList()"
            >搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetSysRoleList()">重置</el-button>
          </el-col>
        </el-row>
        <!-- <el-row style="display:flex">
          
        </el-row>-->
      </el-form>
    </div>

    <!-- 工具条 -->
    <div class="tools-div">
      <el-button type="success" icon="el-icon-plus" size="mini" :disabled="$hasBP('bnt.sysRole.add')  === false" @click="handleAdd()">添 加</el-button>
      <el-button type="danger" size="mini" :disabled="multiple || $hasBP('bnt.sysRole.remove') === false"  @click="batchRemove()">删除</el-button>
    </div>

    <div class="app-container">
      <!-- 表格 -->
      <el-table
        v-loading="loading"
        :data="sysRoleList"
        stripe
        border
        style="width: 100%;margin-top: 10px;"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" />

        <el-table-column label="序号" width="70" align="center">
          <template
            slot-scope="scope"
          >{{ (params.pageNum - 1) * params.pageSize + scope.$index + 1 }}</template>
        </el-table-column>

        <el-table-column prop="roleName" label="角色名称" />
        <el-table-column prop="roleCode" label="角色编码" />
        <el-table-column prop="createTime" label="创建时间" width="160" />
        <el-table-column label="操作" width="200" align="center">
          <template slot-scope="scope">
            <el-button
              type="primary"
              icon="el-icon-edit"
              size="mini"
              :disabled="$hasBP('bnt.sysRole.update') === false"
              @click="edit(scope.row.id)"
              title="修改"
            />
            <el-button
              type="danger"
              icon="el-icon-delete"
              size="mini"
              :disabled="$hasBP('bnt.sysRole.remove') === false"
              @click="batchRemove(scope.row.id)"
              title="删除"
            />
            <el-button
              type="warning"
              icon="el-icon-baseball"
              size="mini"
              :disabled="$hasBP('bnt.sysRole.assignAuth') === false"
              @click="showAssignAuth(scope.row)"
              title="分配权限"
            />
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 分页组件 -->
    <!-- @current-change="getSysRoleList" -->
    <!-- layout="prev, pager, next" -->
    <el-pagination
      background
      layout="total, prev, pager, next, jumper"
      :current-page.sync="params.pageNum"
      :page-size.sync="params.pageSize"
      :total="total"
      @current-change="getSysRoleList"
    ></el-pagination>

    <el-dialog title="添加/修改" :visible.sync="open" width="40%">
      <el-form
        ref="form"
        :model="form"
        label-width="150px"
        size="small"
        style="padding-right: 40px;"
      >
        <el-form-item label="角色名称">
          <el-input v-model="form.roleName" />
        </el-form-item>
        <el-form-item label="角色编码">
          <el-input v-model="form.roleCode" />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="open = false" size="small" icon="el-icon-refresh-right">取 消</el-button>
        <el-button type="primary" icon="el-icon-check" size="small" @click="submitForm">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {
  getList,
  addSysRole,
  findSysRoleById,
  updateSysRole,
  batchDelSysRole
} from "@/api/system/sysRole";

export default {
  data() {
    return {
      multiple: true,
      ids: [],
      open: false,
      loading: false,
      sysRoleList: [],
      role: {},
      total: 0,
      //   listLoading: false,
      params: {
        pageNum: 1,
        pageSize: 10,
        roleName: undefined
      },
      //表单
      form: {}
    };
  },

  created() {
    this.getSysRoleList();
  },

  methods: {
    getSysRoleList() {
      this.loading = true;
      console.log(this.params);
      getList(this.params).then(response => {
        console.log(response);
        this.sysRoleList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },

    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id);
      console.log(this.ids);
      //   this.single = selection.length!=1
      //没有选择仅用按钮
      this.multiple = !selection.length;
    },

    //打开添加对话框
    handleAdd() {
      this.reset();
      this.open = true;
    },

    // batchDelSysRole(ids).then(res => {
    //     console.log("测试");
    //     console.log(res);
    // })

    edit(id) {
      this.$modal.loading("正在加载中...");
      this.reset();
      findSysRoleById(id).then(response => {
        console.log(response);
        this.form = response.data;
        this.open = true;
        this.$modal.closeLoading();
      });
    },

    submitForm() {
      let _this = this;
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateSysRole(_this.form).then(response => {
              console.log(response);
              _this.$message.success("更新成功");
              _this.open = false;
              _this.getSysRoleList();
            });
          } else {
            addSysRole(_this.form).then(response => {
              console.log(response);
              _this.$message.success("添加成功");
              _this.open = false;
              _this.getSysRoleList();
            });
          }
        }
      });
    },

    testClick(msg1) {
      alert("子组件想父组件传递值" + msg1);
    },

    //重置表单
    reset() {
      this.form = {
        id: undefined,
        roleName: undefined,
        roleCode: undefined
      };
    },

    resetSysRoleList() {
      this.params = {
        pageNum: 1,
        pageSize: 10,
        roleName: undefined
      };
      this.getSysRoleList();
    },

    /**分配菜单权限 */
    showAssignAuth(row) {
      this.$router.push(
        "/system/assignAuth?id=" + row.id + "&roleName=" + row.roleName
      );
    },

    //删除
    batchRemove(id) {
      let roleIds = id || this.ids;
      let _this = this;
      // if (id != undefined) {
      //   roleIds.push(id);
      //   // console.log(this.ids);
      // }else{
      //   roleIds = _this.ids;
      // }
      console.log(this.ids);

      this.$modal
        .confirm("是否确定删除" + roleIds + "的数据")
        .then(function() {
          return batchDelSysRole(roleIds);
        })
        .then(response => {
          console.log(response);
          this.getSysRoleList();
          this.$modal.msgSuccess("删除成功");
          // this.ids = [];
        })
        .catch(() => {
          // this.ids = [];
        });
    }
  }
};
</script>

<style>
</style>


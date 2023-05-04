<template>
  <div class="app-container">
    <div class="search-div">
      <el-form label-width="70px" size="small">
        <el-row>
          <el-col :span="8">
            <el-form-item label="关 键 字">
              <el-input style="width: 95%" v-model="params.keyword" placeholder="用户名/姓名/手机号码"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="操作时间">
              <el-date-picker
                v-model="createTimes"
                type="datetimerange"
                range-separator="至"
                start-placeholder="开始时间"
                end-placeholder="结束时间"
                value-format="yyyy-MM-dd HH:mm:ss"
                style="margin-right: 10px;width: 100%;"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row style="display:flex">
          <el-button type="primary" icon="el-icon-search" size="mini" @click="getSysUserList">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini">重置</el-button>
        </el-row>
      </el-form>
    </div>

    <!-- 工具条 -->
    <div class="tools-div">
      <el-button type="success" icon="el-icon-plus" size="mini" @click="handleAdd">添 加</el-button>
    </div>

    <!-- 列表 -->
    <el-table
      v-loading="listLoading"
      :data="list"
      stripe
      border
      style="width: 100%;margin-top: 10px;"
    >
      <el-table-column label="序号" width="70" align="center">
        <template slot-scope="scope">{{ (params.pageNum - 1) * params.pageSize + scope.$index + 1 }}</template>
      </el-table-column>

      <el-table-column prop="username" label="用户名" width="100" />
      <el-table-column prop="name" label="姓名" width="70" />
      <el-table-column prop="phone" label="手机" width="120" />
      <el-table-column prop="postName" label="岗位" width="100" />
      <el-table-column prop="deptName" label="部门" width="100" />
      <el-table-column label="所属角色" width="130">
        <template slot-scope="scope">
          <span
            v-for="item in scope.row.roleList"
            :key="item.id"
            style="margin-right: 10px;"
          >{{ item.roleName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" width="80">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.status === 1" @change="switchStatus(scope.row)"></el-switch>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="160" />

      <el-table-column label="操作" width="180" align="center" fixed="right">
        <template slot-scope="scope">
          <el-button
            type="primary"
            icon="el-icon-edit"
            size="mini"
            @click="handleUpdate(scope.row.id)"
            title="修改"
          />
          <el-button
            type="warning"
            icon="el-icon-baseball"
            size="mini"
            @click="showAssignRole(scope.row)"
            title="分配角色"
          />
          <el-button
            type="danger"
            icon="el-icon-delete"
            size="mini"
            @click="batchRemove(scope.row.id)"
            title="删除"
          />
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页组件 -->
    <el-pagination
      background
      layout="total, prev, pager, next, jumper"
      :current-page.sync="params.pageNum"
      :page-size.sync="params.pageSize"
      :total="total"
      @current-change="getSysUserList"
    ></el-pagination>

    <el-dialog title="添加/修改" :visible.sync="open" width="40%">
      <el-form
        ref="form"
        :model="form"
        label-width="100px"
        size="small"
        style="padding-right: 40px;"
      >
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" />
        </el-form-item>
        <el-form-item v-if="!form.id" label="密码" prop="password">
          <el-input v-model="form.password" type="password" />
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="手机" prop="phone">
          <el-input v-model="form.phone" />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="open = false" size="small" icon="el-icon-refresh-right">取 消</el-button>
        <el-button type="primary" icon="el-icon-check" size="small" @click="submitForm">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog title="分配角色" :visible.sync="dialogRoleVisible">
      <el-form label-width="80px">
        <el-form-item label="用户名">
          <el-input disabled :value="form.username"></el-input>
        </el-form-item>

        <el-form-item label="角色列表">
          <el-checkbox
            :indeterminate="isIndeterminate"
            v-model="checkAll"
            @change="handleCheckAllChange"
          >全选</el-checkbox>
          <div style="margin: 15px 0;"></div>
          <el-checkbox-group v-model="userRoleIds" @change="handleCheckedChange">
            <el-checkbox v-for="role in allRoles" :key="role.id" :label="role.id">{{role.roleName}}</el-checkbox>
          </el-checkbox-group>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button type="primary" @click="assignRole" size="small">保存</el-button>
        <el-button @click="dialogRoleVisible = false" size="small">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>


<script>
import {
  getList,
  addSysUser,
  updateSysUser,
  getSysUserById,
  batchRemoveSysUser,
  updateStatus
} from "@/api/system/sysUser";

import { findAll, getRolesById,assignRoles } from "@/api/system/sysRole";

export default {
  data() {
    return {
      listLoading: true,
      list: [],
      params: {
        pageNum: 1,
        pageSize: 5,
        keyword: undefined,
        createTimeBegin: undefined,
        createTimeEnd: undefined
      },
      form: {},
      createTimes: "",
      total: 0,
      open: false,
      ids: [],

      dialogRoleVisible: false,
      allRoles: [], // 所有角色列表
      userRoleIds: [], // 用户的角色ID的列表
      isIndeterminate: false, // 是否是不确定的
      checkAll: false, // 是否全选
      roleList:[],
    };
  },

  created() {
    this.getSysUserList();

    findAll().then(response => {
      this.roleList = response.rows;
      console.log(response);
    });
  },

  //   watch : {
  //     "createTimes"(newVal,oldVal){
  //         console.log(newVal,oldVal);
  //     }
  //   },

  methods: {
    getSysUserList() {
      this.listLoading = true;
      if (this.createTimes && this.createTimes.length == 2) {
        this.params.createTimeBegin = this.createTimes[0];
        this.params.createTimeEnd = this.createTimes[1];
      }

      getList(this.params).then(res => {
        this.list = res.rows;
        console.log(res);
        this.total = res.total;
        this.listLoading = false;
      });
    },
    reset() {
      this.form = {
        id: "",
        username: "",
        password: "",
        name: "",
        phone: "",
        status: 1
      };
    },
    handleAdd() {
      this.reset();
      this.open = true;
    },
    handleUpdate(id) {
      this.reset();
      this.open = true;
      this.$modal.loading("正在加载中...");
      getSysUserById(id).then(res => {
        console.log(res);
        this.form = res.data;
        this.$modal.closeLoading();
      });
    },

    submitForm() {
      let _this = this;
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (_this.form.id != undefined) {
            updateSysUser(_this.form).then(res => {
              console.log(res);
              _this.$modal.msgSuccess("更新成功");
              _this.getSysUserList();
              _this.open = false;
            });
          } else {
            addSysUser(_this.form).then(res => {
              console.log(res);
              _this.$modal.msgSuccess("添加成功");
              _this.getSysUserList();
              _this.open = false;
            });
          }
        }
      });
    },

    //删除
    batchRemove(id) {
      let _this = this;

      let userIds = id || _this.ids;
      //   if (id != undefined) {
      //     this.ids.push(id);
      //     console.log(this.ids);
      //   }
      //   console.log(this.ids);

      this.$modal
        .confirm("是否确定删除" + userIds + "的数据")
        .then(function() {
          return batchRemoveSysUser(userIds);
        })
        .then(response => {
          console.log(response);
          this.getSysUserList();
          this.$modal.msgSuccess("删除成功");
          //   this.ids = [];
        })
        .catch(() => {
          //    this.ids = [];
        });
    },

    showAssignRole(row) {
      this.form = row;
      this.dialogRoleVisible = true;
      this.getRoles();
    },

    getRoles() {
      console.log(this.form.id);
      getRolesById(this.form.id).then(response => {
        console.log(response);
        const { allRolesList, assginRoleList } = response.data;
        this.allRoles = allRolesList;
        this.userRoleIds = assginRoleList.map(item => item.id);
        this.checkAll = allRolesList.length === assginRoleList.length;
        this.isIndeterminate =
          assginRoleList.length > 0 &&
          assginRoleList.length < allRolesList.length;
      });
    },

    /*
    全选勾选状态发生改变的监听
    */
    handleCheckAllChange(value) {
      // value 当前勾选状态true/false
      // 如果当前全选, userRoleIds就是所有角色id的数组, 否则是空数组
      this.userRoleIds = value ? this.allRoles.map(item => item.id) : [];
      // 如果当前不是全选也不全不选时, 指定为false
      this.isIndeterminate = false;
    },

    /*
    角色列表选中项发生改变的监听
    */
    handleCheckedChange(value) {
      const { userRoleIds, allRoles } = this;
      this.checkAll =
        userRoleIds.length === allRoles.length && allRoles.length > 0;
      this.isIndeterminate =
        userRoleIds.length > 0 && userRoleIds.length < allRoles.length;
    },

    assignRole() {
      let assginRoleVo = {
        userId: this.form.id,
        roleIdList: this.userRoleIds
      };
      assignRoles(assginRoleVo).then(response => {
        this.$message.success(response.message || "分配角色成功");
        this.dialogRoleVisible = false;
        this.getSysUserList();
      });
    },

    switchStatus(row) {
      row.status = row.status === 1 ? 0 : 1;
      updateStatus(row.id, row.status).then(response => {
        if (response.code) {
          this.$message.success(response.message || "操作成功");
          this.dialogVisible = false;
          this.getSysUserList();
        }
      });
    }
  }
};
</script>

<style>
</style>
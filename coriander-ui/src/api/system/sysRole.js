import request from '@/utils/request'


  //查询角色列表
export function getList(params){

    return request({
        url: '/admin/system/sysRole/findAll',
        method: 'get',
        params:params
    })
}


export function addSysRole(data){

    return request({
        url: '/admin/system/sysRole/addSysRole',
        method: 'post',
        data:data
    })
}


export function updateSysRole(data){

    return request({
        url: '/admin/system/sysRole/updateSysRole',
        method: 'post',
        data:data
    })
}



  //查询角色列表
  export function findSysRoleById(id){

    return request({
        url: '/admin/system/sysRole/findSysRoleById/'+id,
        method: 'get',
    })
}


  export function batchDelSysRole(ids){

    return request({
        url: '/admin/system/sysRole/batchDelSysRole',
        method: 'post',
        data:ids
    })
}











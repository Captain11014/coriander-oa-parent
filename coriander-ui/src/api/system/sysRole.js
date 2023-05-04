import request from '@/utils/request'

const api_name = '/admin/system/sysRole';

//查询角色列表
export function getList(params) {

    return request({
        url: '/admin/system/sysRole/findAll',
        method: 'get',
        params: params
    })
}


//查询角色列表
export function findAll(params) {

    return request({
        url: '/admin/system/sysRole/findAll',
        method: 'get',
        params: params
    })
}



export function addSysRole(data) {

    return request({
        url: '/admin/system/sysRole/addSysRole',
        method: 'post',
        data: data
    })
}


export function updateSysRole(data) {

    return request({
        url: '/admin/system/sysRole/updateSysRole',
        method: 'post',
        data: data
    })
}



//查询角色列表
export function findSysRoleById(id) {

    return request({
        url: '/admin/system/sysRole/findSysRoleById/' + id,
        method: 'get',
    })
}


export function batchDelSysRole(ids) {

    return request({
        url: '/admin/system/sysRole/batchDelSysRole/' + ids,
        method: 'post',
    })
}




export function getRolesById(adminId) {
    return request({
        url: `${api_name}/toAssign/${adminId}`,
        method: 'get'
    })
}

export function assignRoles(assginRoleVo) {
    return request({
        url: `${api_name}/doAssign`,
        method: 'post',
        data: assginRoleVo
    })
}










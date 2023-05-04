import request from '@/utils/request'

const api_path = '/admin/system/sysUser';


export function getList(params) {
    return request({
        url: `${api_path}/list`,
        method: 'get',
        params: params
    })
}


export function addSysUser(data) {

    return request({
        url: `${api_path}/addSysUser`,
        method: 'post',
        data: data
    })
}


export function updateSysUser(data) {

    return request({
        url: `${api_path}/updateSysUser`,
        method: 'post',
        data: data
    })
}



//查询角色
export function getSysUserById(id) {

    return request({
        url: `${api_path}/getSysUserById/${id}`,
        method: 'get',
    })
}


export function batchRemoveSysUser(ids) {

    return request({
        url: `${api_path}/batchRemoveSysUser/${ids}`,
        method: 'post',
    })
}


export function updateStatus(id, status) {
    return request({
      url: `${api_path}/updateStatus/${id}/${status}`,
      method: 'get'
    })
}


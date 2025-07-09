import request from '@/utils/request'

// 获取设备类型数量
export function getCount() {
    return request({
        url: '/deviceType/getCount',
        method: 'get'
    })
}

// 查询所有设备类型
export function queryDeviceTypes() {
    return request({
        url: '/deviceType/queryDeviceTypes',
        method: 'get'
    })
}

// 分页查询设备类型
export function queryDeviceTypesByPage(params) {
    return request({
        url: '/deviceType/queryDeviceTypesByPage',
        method: 'get',
        params
    })
}

// 添加设备类型
export function addDeviceType(data) {
    return request({
        url: '/deviceType/addDeviceType',
        method: 'post',
        data
    })
}

// 删除设备类型
export function deleteDeviceType(data) {
    return request({
        url: '/deviceType/deleteDeviceType',
        method: 'delete',
        data
    })
}

//  删除一些设备类型
export function deleteDeviceTypes(data) {
    return request({
        url: '/deviceType/deleteDeviceTypes',
        method: 'delete',
        data
    })
}

//  更新设备类型
export function updateDeviceType(data) {
    return request({
        url: '/deviceType/updateDeviceType',
        method: 'put',
        data
    })
}

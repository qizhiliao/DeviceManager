import request from '@/utils/request'

// 获取设备数量
export function getCount() {
    return request({
        url: '/deviceInfo/getCount',
        method: 'get'
    })
}

// 查询所有设备信息
export function queryDeviceInfos() {
    return request({
        url: '/deviceInfo/queryDeviceInfos',
        method: 'get'
    })
}

// 分页查询设备信息
export function queryDeviceInfosByPage(params) {
    return request({
        url: '/deviceInfo/queryDeviceInfosByPage',
        method: 'get',
        params
    })
}

// 添加设备信息
export function addDeviceInfo(data) {
    return request({
    url: '/deviceInfo/addDeviceInfo',
        method: 'post',
        data
    })
}

// 删除设备信息
export function deleteDeviceInfo(data) {
    return request({
        url: '/deviceInfo/deleteDeviceInfo',
        method: 'delete',
        data
    })
}

//  删除一些设备信息
export function deleteDeviceInfos(data) {
    return request({
        url: '/deviceInfo/deleteDeviceInfos',
        method: 'delete',
        data
    })
}

//  更新设备信息
export function updateDeviceInfo(data) {
    return request({
        url: '/deviceInfo/updateDeviceInfo',
        method: 'put',
        data
    })
}

import request from '@/utils/request'

// 获取借用数量
export function getCount() {
    return request({
        url: '/borrow/getCount',
        method: 'get'
    })
}

// 查询所有借用信息
export function queryBorrows() {
    return request({
        url: '/borrow/queryBorrows',
        method: 'get'
    })
}

// 分页查询借用信息
export function queryBorrowsByPage(params) {
    return request({
        url: '/borrow/queryBorrowsByPage',
        method: 'get',
        params
    })
}

// 添加借用信息
export function addBorrow(data) {
    return request({
        url: '/borrow/addBorrow',
        method: 'post',
        data
    })
}

// 删除借用信息
export function deleteBorrow(data) {
    return request({
        url: '/borrow/deleteBorrow',
        method: 'delete',
        data
    })
}

//  删除一些借用信息
export function deleteBorrows(data) {
    return request({
        url: '/borrow/deleteBorrows',
        method: 'delete',
        data
    })
}

//  更新借用信息
export function updateBorrow(data) {
    return request({
        url: '/borrow/updateBorrow',
        method: 'put',
        data
    })
}

// 借设备
export function borrowDevice(userId, deviceId) {
    return request({
        url: '/borrow/borrowDevice',
        method: 'post',
        params: {
            userId,
            deviceId
        }
    })
}

// 还设备
export function returnDevice(borrowid, deviceId) {
    return request({
        url: '/borrow/returnDevice',
        method: 'post',
        params: {
            borrowid,
            deviceId
        }
    })
}

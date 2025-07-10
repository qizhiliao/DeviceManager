import request from "@/utils/request";

export function getCount() {
    return request({
        url: '/comment/getCount',
        method: 'get'
    })
}
export function selectAll() {
    return request({
        url: '/comment/selectAll',
        method: 'get'
    })
}
export function queryCommentsByPage(params){
    return request({
        url: '/comment/queryCommentsByPage',
        method: 'get',
        params
    })
}
// 删除评论信息
export function deleteComment(data) {
  return request({
    url: '/comment/deleteComment',
    method: 'delete',
    data
  })
}
export function deleteComments(data) {
  return request({
    url: '/comment/deleteComments',
    method: 'delete',
    data
  })
}

package com.rabbiter.bms.controller;

import com.rabbiter.bms.pojo.Borrow;
import com.rabbiter.bms.pojo.Comment;
import com.rabbiter.bms.service.CommentService;
import com.rabbiter.bms.utils.MyResult;
import com.rabbiter.bms.utils.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    CommentService commentService;
    @RequestMapping("/getCount")
    public Integer getCount() {
        return commentService.getCount();
    }
    @RequestMapping("/selectAll")
    public List<Comment> selectAll() {
        return commentService.selectAll();
    }
    @RequestMapping("/queryCommentsByPage")
    public Map<String, Object> queryCommentsByPage(@RequestParam Map<String, Object> params){
        MyUtils.parsePageParams(params);
        int count = commentService.getSearchCount(params);
        List<Comment> comments = commentService.searchCommentsByPage(params);
        return MyResult.getListResultMap(0, "success", count, comments);
    }

    //删除一个评论
    @RequestMapping(value = "/deleteComment")
    public Integer deleteComment(@RequestBody Comment comment){
        return commentService.deleteComment(comment);
    }
    //删除多个评论
    @RequestMapping(value = "/deleteComments")
    public Integer deleteComments(@RequestBody List<Comment> comments){
        return commentService.deleteComments(comments);
    }
    //添加评论
    @RequestMapping(value = "/addComment")
    public Integer addComment(@RequestBody Comment comment){
        return commentService.addComment(comment);
    }
}

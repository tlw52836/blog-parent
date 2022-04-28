package com.tlw.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tlw.blog.mapper.CommentMapper;
import com.tlw.blog.mapper.pojo.Comment;
import com.tlw.blog.mapper.pojo.SysUser;
import com.tlw.blog.service.CommentsService;
import com.tlw.blog.service.SysUserService;
import com.tlw.blog.vo.CommentVo;
import com.tlw.blog.vo.Result;
import com.tlw.blog.vo.UserVo;
import org.apache.catalina.User;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentsServiceImpl implements CommentsService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private SysUserService sysUserService;

    @Override
    public Result commentsByArticleId(Long articleId) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId,articleId);
        queryWrapper.eq(Comment::getLevel,1);
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        return Result.success(copyList(comments));
    }


    public List<CommentVo> copyList(List<Comment> commentList){
        List<CommentVo> commentVoList = new ArrayList<>();
        for (Comment comment : commentList) {
            commentVoList.add(copy(comment));
        }
        return commentVoList;
    }

    public CommentVo copy(Comment comment){
        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(comment,commentVo);

        //时间格式化
        commentVo.setCreateDate(new DateTime(comment.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        Long authorId = comment.getAuthorId();
        SysUser sysUser = sysUserService.findUserById(authorId);
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(sysUser, userVo);
        commentVo.setAuthor(userVo);

        //评论的评论
        List<CommentVo> commentVoList = findCommentsByParentId(comment.getId());
        commentVo.setChildrens(commentVoList);
        if (comment.getLevel() > 1) {
            Long toUid = comment.getToUid();
            SysUser toSysUser = sysUserService.findUserById(toUid);
            UserVo toUserVo = new UserVo();
            BeanUtils.copyProperties(toSysUser, toUserVo);
            commentVo.setToUser(toUserVo);
        }
        return commentVo;
    }

    private List<CommentVo> findCommentsByParentId(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getParentId,id);
        queryWrapper.eq(Comment::getLevel,2);
        List<Comment> comments = this.commentMapper.selectList(queryWrapper);
        return copyList(comments);
    }


}

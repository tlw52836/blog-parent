package com.tlw.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tlw.blog.dao.CommentMapper;
import com.tlw.blog.dao.pojo.Comment;
import com.tlw.blog.dao.pojo.SysUser;
import com.tlw.blog.service.CommentsService;
import com.tlw.blog.service.SysUserService;
import com.tlw.blog.utils.UserThreadLocal;
import com.tlw.blog.vo.CommentVo;
import com.tlw.blog.vo.Result;
import com.tlw.blog.vo.UserVo;
import com.tlw.blog.vo.params.CommentParam;
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

    @Override
    public Result comment(CommentParam commentParam) {
        SysUser sysUser = UserThreadLocal.get();
        Comment comment = new Comment();
        comment.setArticleId(commentParam.getArticleId());
        comment.setAuthorId(sysUser.getId());
        comment.setContent(commentParam.getContent());
        comment.setCreateDate(System.currentTimeMillis());
        Long parent = commentParam.getParent();
        if (parent == null || parent == 0) {
            comment.setLevel(1);
        }else{
            comment.setLevel(2);
        }
        comment.setParentId(parent == null ? 0 : parent);
        Long toUserId = commentParam.getToUserId();
        comment.setToUid(toUserId == null ? 0 : toUserId);
        this.commentMapper.insert(comment);

        Result result = commentsByArticleId(commentParam.getArticleId());

        return result;
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

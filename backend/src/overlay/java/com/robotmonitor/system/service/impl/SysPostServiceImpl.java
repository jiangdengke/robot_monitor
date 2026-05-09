package com.robotmonitor.system.service.impl;

import com.robotmonitor.common.exception.ServiceException;
import com.robotmonitor.common.utils.StringUtils;
import com.robotmonitor.system.domain.SysPost;
import com.robotmonitor.system.mapper.SysPostMapper;
import com.robotmonitor.system.mapper.SysUserPostMapper;
import com.robotmonitor.system.repository.jooq.GeneratedJooqPostRepository;
import com.robotmonitor.system.service.ISysPostService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysPostServiceImpl implements ISysPostService {
    @Autowired
    private SysPostMapper postMapper;
    @Autowired
    private SysUserPostMapper userPostMapper;
    @Autowired
    private GeneratedJooqPostRepository generatedJooqPostRepository;

    @Override
    public List<SysPost> selectPostList(SysPost post) {
        return this.postMapper.selectPostList(post);
    }

    @Override
    public List<SysPost> selectPostAll() {
        return this.generatedJooqPostRepository.selectPostAll();
    }

    @Override
    public SysPost selectPostById(Long postId) {
        return this.postMapper.selectPostById(postId);
    }

    @Override
    public List<Long> selectPostListByUserId(Long userId) {
        return this.generatedJooqPostRepository.selectPostListByUserId(userId);
    }

    @Override
    public String checkPostNameUnique(SysPost post) {
        Long postId = StringUtils.isNull(post.getPostId()) ? -1L : post.getPostId();
        SysPost info = this.postMapper.checkPostNameUnique(post.getPostName());
        if (StringUtils.isNotNull(info) && info.getPostId().longValue() != postId.longValue()) {
            return "1";
        }
        return "0";
    }

    @Override
    public String checkPostCodeUnique(SysPost post) {
        Long postId = StringUtils.isNull(post.getPostId()) ? -1L : post.getPostId();
        SysPost info = this.postMapper.checkPostCodeUnique(post.getPostCode());
        if (StringUtils.isNotNull(info) && info.getPostId().longValue() != postId.longValue()) {
            return "1";
        }
        return "0";
    }

    @Override
    public int countUserPostById(Long postId) {
        return this.userPostMapper.countUserPostById(postId);
    }

    @Override
    public int deletePostById(Long postId) {
        return this.postMapper.deletePostById(postId);
    }

    @Override
    public int deletePostByIds(Long[] postIds) {
        for (Long postId : postIds) {
            SysPost post = this.selectPostById(postId);
            if (this.countUserPostById(postId) > 0) {
                throw new ServiceException(String.format("%1$s已分配,不能删除", post.getPostName()));
            }
        }
        return this.postMapper.deletePostByIds(postIds);
    }

    @Override
    public int insertPost(SysPost post) {
        return this.postMapper.insertPost(post);
    }

    @Override
    public int updatePost(SysPost post) {
        return this.postMapper.updatePost(post);
    }
}

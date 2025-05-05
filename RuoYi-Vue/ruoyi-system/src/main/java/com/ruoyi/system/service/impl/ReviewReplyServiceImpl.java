package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.ReviewReplyMapper;
import com.ruoyi.system.domain.ReviewReply;
import com.ruoyi.system.service.IReviewReplyService;

/**
 * 商家回复用户评论Service业务层处理
 * 
 * @author hxx
 * @date 2025-05-05
 */
@Service
public class ReviewReplyServiceImpl implements IReviewReplyService 
{
    @Autowired
    private ReviewReplyMapper reviewReplyMapper;

    /**
     * 查询商家回复用户评论
     * 
     * @param replyId 商家回复用户评论主键
     * @return 商家回复用户评论
     */
    @Override
    public ReviewReply selectReviewReplyByReplyId(Long replyId)
    {
        return reviewReplyMapper.selectReviewReplyByReplyId(replyId);
    }

    /**
     * 查询商家回复用户评论列表
     * 
     * @param reviewReply 商家回复用户评论
     * @return 商家回复用户评论
     */
    @Override
    public List<ReviewReply> selectReviewReplyList(ReviewReply reviewReply)
    {
        return reviewReplyMapper.selectReviewReplyList(reviewReply);
    }

    /**
     * 新增商家回复用户评论
     * 
     * @param reviewReply 商家回复用户评论
     * @return 结果
     */
    @Override
    public int insertReviewReply(ReviewReply reviewReply)
    {
        return reviewReplyMapper.insertReviewReply(reviewReply);
    }

    /**
     * 修改商家回复用户评论
     * 
     * @param reviewReply 商家回复用户评论
     * @return 结果
     */
    @Override
    public int updateReviewReply(ReviewReply reviewReply)
    {
        return reviewReplyMapper.updateReviewReply(reviewReply);
    }

    /**
     * 批量删除商家回复用户评论
     * 
     * @param replyIds 需要删除的商家回复用户评论主键
     * @return 结果
     */
    @Override
    public int deleteReviewReplyByReplyIds(Long[] replyIds)
    {
        return reviewReplyMapper.deleteReviewReplyByReplyIds(replyIds);
    }

    /**
     * 删除商家回复用户评论信息
     * 
     * @param replyId 商家回复用户评论主键
     * @return 结果
     */
    @Override
    public int deleteReviewReplyByReplyId(Long replyId)
    {
        return reviewReplyMapper.deleteReviewReplyByReplyId(replyId);
    }
}

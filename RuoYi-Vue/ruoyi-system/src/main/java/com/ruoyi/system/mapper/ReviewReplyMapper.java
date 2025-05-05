package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.ReviewReply;

/**
 * 商家回复用户评论Mapper接口
 * 
 * @author hxx
 * @date 2025-05-05
 */
public interface ReviewReplyMapper 
{
    /**
     * 查询商家回复用户评论
     * 
     * @param replyId 商家回复用户评论主键
     * @return 商家回复用户评论
     */
    public ReviewReply selectReviewReplyByReplyId(Long replyId);

    /**
     * 查询商家回复用户评论列表
     * 
     * @param reviewReply 商家回复用户评论
     * @return 商家回复用户评论集合
     */
    public List<ReviewReply> selectReviewReplyList(ReviewReply reviewReply);

    /**
     * 新增商家回复用户评论
     * 
     * @param reviewReply 商家回复用户评论
     * @return 结果
     */
    public int insertReviewReply(ReviewReply reviewReply);

    /**
     * 修改商家回复用户评论
     * 
     * @param reviewReply 商家回复用户评论
     * @return 结果
     */
    public int updateReviewReply(ReviewReply reviewReply);

    /**
     * 删除商家回复用户评论
     * 
     * @param replyId 商家回复用户评论主键
     * @return 结果
     */
    public int deleteReviewReplyByReplyId(Long replyId);

    /**
     * 批量删除商家回复用户评论
     * 
     * @param replyIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteReviewReplyByReplyIds(Long[] replyIds);
}

package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.Review;

/**
 * 评论Mapper接口
 * 
 * @author hxx
 * @date 2025-04-13
 */
public interface ReviewMapper 
{
    /**
     * 查询评论
     * 
     * @param reviewId 评论主键
     * @return 评论
     */
    public Review selectReviewByReviewId(Long reviewId);

    /**
     * 查询评论列表
     * 
     * @param review 评论
     * @return 评论集合
     */
    public List<Review> selectReviewList(Review review);

    /**
     * 新增评论
     * 
     * @param review 评论
     * @return 结果
     */
    public int insertReview(Review review);

    /**
     * 修改评论
     * 
     * @param review 评论
     * @return 结果
     */
    public int updateReview(Review review);

    /**
     * 删除评论
     * 
     * @param reviewId 评论主键
     * @return 结果
     */
    public int deleteReviewByReviewId(Long reviewId);

    /**
     * 批量删除评论
     * 
     * @param reviewIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteReviewByReviewIds(Long[] reviewIds);
}

package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.ViewProductReviewsMapper;
import com.ruoyi.system.domain.ViewProductReviews;
import com.ruoyi.system.service.IViewProductReviewsService;

/**
 * 评论详情Service业务层处理
 * 
 * @author hxx
 * @date 2025-04-13
 */
@Service
public class ViewProductReviewsServiceImpl implements IViewProductReviewsService 
{
    @Autowired
    private ViewProductReviewsMapper viewProductReviewsMapper;

    /**
     * 查询评论详情
     * 
     * @param reviewId 评论详情主键
     * @return 评论详情
     */
    @Override
    public ViewProductReviews selectViewProductReviewsByReviewId(Long reviewId)
    {
        return viewProductReviewsMapper.selectViewProductReviewsByReviewId(reviewId);
    }

    /**
     * 查询评论详情列表
     * 
     * @param viewProductReviews 评论详情
     * @return 评论详情
     */
    @Override
    public List<ViewProductReviews> selectViewProductReviewsList(ViewProductReviews viewProductReviews)
    {
        return viewProductReviewsMapper.selectViewProductReviewsList(viewProductReviews);
    }

    /**
     * 新增评论详情
     * 
     * @param viewProductReviews 评论详情
     * @return 结果
     */
    @Override
    public int insertViewProductReviews(ViewProductReviews viewProductReviews)
    {
        return viewProductReviewsMapper.insertViewProductReviews(viewProductReviews);
    }

    /**
     * 修改评论详情
     * 
     * @param viewProductReviews 评论详情
     * @return 结果
     */
    @Override
    public int updateViewProductReviews(ViewProductReviews viewProductReviews)
    {
        return viewProductReviewsMapper.updateViewProductReviews(viewProductReviews);
    }

    /**
     * 批量删除评论详情
     * 
     * @param reviewIds 需要删除的评论详情主键
     * @return 结果
     */
    @Override
    public int deleteViewProductReviewsByReviewIds(Long[] reviewIds)
    {
        return viewProductReviewsMapper.deleteViewProductReviewsByReviewIds(reviewIds);
    }

    /**
     * 删除评论详情信息
     * 
     * @param reviewId 评论详情主键
     * @return 结果
     */
    @Override
    public int deleteViewProductReviewsByReviewId(Long reviewId)
    {
        return viewProductReviewsMapper.deleteViewProductReviewsByReviewId(reviewId);
    }
}

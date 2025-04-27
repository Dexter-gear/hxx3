<template>
  <div class="app-container">
    <!-- 数据概览 -->
    <el-row :gutter="20" class="mb20">
      <el-col :span="6">
        <el-card shadow="hover" class="statistics-card">
          <div class="statistics-item">
            <div class="statistics-icon">
              <i class="el-icon-goods"></i>
            </div>
            <div class="statistics-info">
              <div class="statistics-title">商品总数</div>
              <div class="statistics-value">{{ statistics.totalProducts }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="statistics-card">
          <div class="statistics-item">
            <div class="statistics-icon">
              <i class="el-icon-s-order"></i>
            </div>
            <div class="statistics-info">
              <div class="statistics-title">订单总数</div>
              <div class="statistics-value">{{ statistics.totalOrders }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="statistics-card">
          <div class="statistics-item">
            <div class="statistics-icon">
              <i class="el-icon-user"></i>
            </div>
            <div class="statistics-info">
              <div class="statistics-title">用户总数</div>
              <div class="statistics-value">{{ statistics.totalUsers }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="statistics-card">
          <div class="statistics-item">
            <div class="statistics-icon">
              <i class="el-icon-chat-dot-round"></i>
            </div>
            <div class="statistics-info">
              <div class="statistics-title">评论总数</div>
              <div class="statistics-value">{{ statistics.totalComments }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 商品统计 -->
    <el-card class="mb20">
      <div slot="header" class="clearfix">
        <span>商品统计</span>
        <el-button style="float: right; padding: 3px 0" type="text" @click="refreshProductData">刷新</el-button>
      </div>
      <el-row :gutter="20">
        <el-col :span="12">
          <div ref="productCategoryChart" class="chart-container"></div>
        </el-col>
        <el-col :span="12">
          <div ref="productSalesChart" class="chart-container"></div>
        </el-col>
      </el-row>
      <el-table :data="productList" style="width: 100%; margin-top: 20px">
        <el-table-column prop="productName" label="商品名称" />
        <el-table-column prop="categoryName" label="分类" />
        <el-table-column prop="sales" label="销量" />
        <el-table-column prop="stock" label="库存" />
        <el-table-column prop="status" label="状态">
          <template slot-scope="scope">
            <el-tag :type="scope.row.status === '0' ? 'success' : 'info'">
              {{ scope.row.status === '0' ? '上架' : '下架' }}
            </el-tag>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 订单统计 -->
    <el-card class="mb20">
      <div slot="header" class="clearfix">
        <span>订单统计</span>
        <el-button style="float: right; padding: 3px 0" type="text" @click="refreshOrderData">刷新</el-button>
      </div>
      <el-row :gutter="20">
        <el-col :span="12">
          <div ref="orderStatusChart" class="chart-container"></div>
        </el-col>
        <el-col :span="12">
          <div ref="orderTrendChart" class="chart-container"></div>
        </el-col>
      </el-row>
      <el-table :data="orderList" style="width: 100%; margin-top: 20px">
        <el-table-column prop="orderId" label="订单编号" />
        <el-table-column prop="userName" label="用户" />
        <el-table-column prop="totalAmount" label="金额" />
        <el-table-column prop="status" label="状态">
          <template slot-scope="scope">
            <el-tag :type="getOrderStatusType(scope.row.status)">
              {{ getOrderStatusText(scope.row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" />
      </el-table>
    </el-card>

    <!-- 评论统计 -->
    <el-card>
      <div slot="header" class="clearfix">
        <span>评论统计</span>
        <el-button style="float: right; padding: 3px 0" type="text" @click="refreshCommentData">刷新</el-button>
      </div>
      <el-row :gutter="20">
        <el-col :span="12">
          <div ref="commentRatingChart" class="chart-container"></div>
        </el-col>
        <el-col :span="12">
          <div ref="commentTrendChart" class="chart-container"></div>
        </el-col>
      </el-row>
      <el-table :data="commentList" style="width: 100%; margin-top: 20px">
        <el-table-column prop="productName" label="商品" />
        <el-table-column prop="userName" label="用户" />
        <el-table-column prop="rating" label="评分">
          <template slot-scope="scope">
            <el-rate v-model="scope.row.rating" disabled show-score />
          </template>
        </el-table-column>
        <el-table-column prop="content" label="评论内容" show-overflow-tooltip />
        <el-table-column prop="createTime" label="评论时间" />
      </el-table>
    </el-card>
  </div>
</template>

<script>
import * as echarts from 'echarts'
import { getStatistics, getProductList, getOrderList, getCommentList } from '@/api/monitor/statistics'

export default {
  name: 'Statistics',
  data() {
    return {
      // 统计数据
      statistics: {
        totalProducts: 0,
        totalOrders: 0,
        totalUsers: 0,
        totalComments: 0
      },
      // 商品数据
      productList: [],
      // 订单数据
      orderList: [],
      // 评论数据
      commentList: [],
      // 图表实例
      charts: {
        productCategory: null,
        productSales: null,
        orderStatus: null,
        orderTrend: null,
        commentRating: null,
        commentTrend: null
      }
    }
  },
  mounted() {
    this.initData()
    this.initCharts()
    // 监听窗口大小变化，重绘图表
    window.addEventListener('resize', this.resizeCharts)
  },
  beforeDestroy() {
    window.removeEventListener('resize', this.resizeCharts)
    // 销毁图表实例
    Object.values(this.charts).forEach(chart => {
      chart && chart.dispose()
    })
  },
  methods: {
    // 初始化数据
    async initData() {
      try {
        // 获取统计数据
        const statisticsRes = await getStatistics()
        this.statistics = statisticsRes.data

        // 获取商品数据
        const productRes = await getProductList()
        this.productList = productRes.rows

        // 获取订单数据
        const orderRes = await getOrderList()
        this.orderList = orderRes.rows

        // 获取评论数据
        const commentRes = await getCommentList()
        this.commentList = commentRes.rows

        // 更新图表
        this.updateCharts()
      } catch (error) {
        console.error('获取数据失败:', error)
        this.$message.error('获取数据失败')
      }
    },
    // 初始化图表
    initCharts() {
      // 商品分类图表
      this.charts.productCategory = echarts.init(this.$refs.productCategoryChart)
      // 商品销量图表
      this.charts.productSales = echarts.init(this.$refs.productSalesChart)
      // 订单状态图表
      this.charts.orderStatus = echarts.init(this.$refs.orderStatusChart)
      // 订单趋势图表
      this.charts.orderTrend = echarts.init(this.$refs.orderTrendChart)
      // 评论评分图表
      this.charts.commentRating = echarts.init(this.$refs.commentRatingChart)
      // 评论趋势图表
      this.charts.commentTrend = echarts.init(this.$refs.commentTrendChart)
    },
    // 更新图表数据
    updateCharts() {
      // 更新商品分类图表
      this.updateProductCategoryChart()
      // 更新商品销量图表
      this.updateProductSalesChart()
      // 更新订单状态图表
      this.updateOrderStatusChart()
      // 更新订单趋势图表
      this.updateOrderTrendChart()
      // 更新评论评分图表
      this.updateCommentRatingChart()
      // 更新评论趋势图表
      this.updateCommentTrendChart()
    },
    // 更新商品分类图表
    updateProductCategoryChart() {
      const categoryData = this.getCategoryData()
      const option = {
        title: {
          text: '商品分类分布',
          left: 'center'
        },
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b}: {c} ({d}%)'
        },
        legend: {
          orient: 'vertical',
          left: 'left',
          data: categoryData.map(item => item.name)
        },
        series: [
          {
            name: '商品分类',
            type: 'pie',
            radius: ['50%', '70%'],
            avoidLabelOverlap: false,
            label: {
              show: false,
              position: 'center'
            },
            emphasis: {
              label: {
                show: true,
                fontSize: '18',
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: false
            },
            data: categoryData
          }
        ]
      }
      this.charts.productCategory.setOption(option)
    },
    // 更新商品销量图表
    updateProductSalesChart() {
      const salesData = this.getSalesData()
      const option = {
        title: {
          text: '商品销量排行',
          left: 'center'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        xAxis: {
          type: 'value'
        },
        yAxis: {
          type: 'category',
          data: salesData.map(item => item.name)
        },
        series: [
          {
            name: '销量',
            type: 'bar',
            data: salesData.map(item => item.value)
          }
        ]
      }
      this.charts.productSales.setOption(option)
    },
    // 更新订单状态图表
    updateOrderStatusChart() {
      const statusData = this.getOrderStatusData()
      const option = {
        title: {
          text: '订单状态分布',
          left: 'center'
        },
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b}: {c} ({d}%)'
        },
        legend: {
          orient: 'vertical',
          left: 'left',
          data: statusData.map(item => item.name)
        },
        series: [
          {
            name: '订单状态',
            type: 'pie',
            radius: '50%',
            data: statusData,
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      }
      this.charts.orderStatus.setOption(option)
    },
    // 更新订单趋势图表
    updateOrderTrendChart() {
      const trendData = this.getOrderTrendData()
      const option = {
        title: {
          text: '订单趋势',
          left: 'center'
        },
        tooltip: {
          trigger: 'axis'
        },
        xAxis: {
          type: 'category',
          data: trendData.map(item => item.date)
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: '订单数',
            type: 'line',
            data: trendData.map(item => item.value),
            smooth: true
          }
        ]
      }
      this.charts.orderTrend.setOption(option)
    },
    // 更新评论评分图表
    updateCommentRatingChart() {
      const ratingData = this.getCommentRatingData()
      const option = {
        title: {
          text: '评论评分分布',
          left: 'center'
        },
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b}: {c} ({d}%)'
        },
        legend: {
          orient: 'vertical',
          left: 'left',
          data: ratingData.map(item => item.name)
        },
        series: [
          {
            name: '评分',
            type: 'pie',
            radius: '50%',
            data: ratingData,
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      }
      this.charts.commentRating.setOption(option)
    },
    // 更新评论趋势图表
    updateCommentTrendChart() {
      const trendData = this.getCommentTrendData()
      const option = {
        title: {
          text: '评论趋势',
          left: 'center'
        },
        tooltip: {
          trigger: 'axis'
        },
        xAxis: {
          type: 'category',
          data: trendData.map(item => item.date)
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: '评论数',
            type: 'line',
            data: trendData.map(item => item.value),
            smooth: true
          }
        ]
      }
      this.charts.commentTrend.setOption(option)
    },
    // 获取商品分类数据
    getCategoryData() {
      const categoryMap = new Map()
      this.productList.forEach(product => {
        const count = categoryMap.get(product.categoryName) || 0
        categoryMap.set(product.categoryName, count + 1)
      })
      return Array.from(categoryMap.entries()).map(([name, value]) => ({ name, value }))
    },
    // 获取商品销量数据
    getSalesData() {
      return this.productList
        .sort((a, b) => b.sales - a.sales)
        .slice(0, 10)
        .map(product => ({
          name: product.productName,
          value: product.sales
        }))
    },
    // 获取订单状态数据
    getOrderStatusData() {
      const statusMap = new Map()
      this.orderList.forEach(order => {
        const count = statusMap.get(this.getOrderStatusText(order.status)) || 0
        statusMap.set(this.getOrderStatusText(order.status), count + 1)
      })
      return Array.from(statusMap.entries()).map(([name, value]) => ({ name, value }))
    },
    // 获取订单趋势数据
    getOrderTrendData() {
      const dateMap = new Map()
      this.orderList.forEach(order => {
        const date = order.createTime.split(' ')[0]
        const count = dateMap.get(date) || 0
        dateMap.set(date, count + 1)
      })
      return Array.from(dateMap.entries())
        .sort((a, b) => a[0].localeCompare(b[0]))
        .map(([date, value]) => ({ date, value }))
    },
    // 获取评论评分数据
    getCommentRatingData() {
      const ratingMap = new Map()
      this.commentList.forEach(comment => {
        const count = ratingMap.get(comment.rating) || 0
        ratingMap.set(comment.rating, count + 1)
      })
      return Array.from(ratingMap.entries())
        .sort((a, b) => a[0] - b[0])
        .map(([rating, value]) => ({
          name: `${rating}星`,
          value
        }))
    },
    // 获取评论趋势数据
    getCommentTrendData() {
      const dateMap = new Map()
      this.commentList.forEach(comment => {
        const date = comment.createTime.split(' ')[0]
        const count = dateMap.get(date) || 0
        dateMap.set(date, count + 1)
      })
      return Array.from(dateMap.entries())
        .sort((a, b) => a[0].localeCompare(b[0]))
        .map(([date, value]) => ({ date, value }))
    },
    // 获取订单状态类型
    getOrderStatusType(status) {
      const statusMap = {
        '0': 'info',    // 待付款
        '1': 'warning', // 待发货
        '2': 'success', // 已发货
        '3': 'danger'   // 已取消
      }
      return statusMap[status] || 'info'
    },
    // 获取订单状态文本
    getOrderStatusText(status) {
      const statusMap = {
        '0': '待付款',
        '1': '待发货',
        '2': '已发货',
        '3': '已取消'
      }
      return statusMap[status] || '未知'
    },
    // 刷新商品数据
    async refreshProductData() {
      try {
        const res = await getProductList()
        this.productList = res.rows
        this.updateProductCategoryChart()
        this.updateProductSalesChart()
        this.$message.success('刷新成功')
      } catch (error) {
        console.error('刷新商品数据失败:', error)
        this.$message.error('刷新失败')
      }
    },
    // 刷新订单数据
    async refreshOrderData() {
      try {
        const res = await getOrderList()
        this.orderList = res.rows
        this.updateOrderStatusChart()
        this.updateOrderTrendChart()
        this.$message.success('刷新成功')
      } catch (error) {
        console.error('刷新订单数据失败:', error)
        this.$message.error('刷新失败')
      }
    },
    // 刷新评论数据
    async refreshCommentData() {
      try {
        const res = await getCommentList()
        this.commentList = res.rows
        this.updateCommentRatingChart()
        this.updateCommentTrendChart()
        this.$message.success('刷新成功')
      } catch (error) {
        console.error('刷新评论数据失败:', error)
        this.$message.error('刷新失败')
      }
    },
    // 重绘图表
    resizeCharts() {
      Object.values(this.charts).forEach(chart => {
        chart && chart.resize()
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.statistics-card {
  .statistics-item {
    display: flex;
    align-items: center;
    
    .statistics-icon {
      font-size: 48px;
      margin-right: 20px;
      color: #409EFF;
    }
    
    .statistics-info {
      .statistics-title {
        font-size: 14px;
        color: #909399;
        margin-bottom: 5px;
      }
      
      .statistics-value {
        font-size: 24px;
        font-weight: 500;
        color: #303133;
      }
    }
  }
}

.chart-container {
  height: 400px;
  margin-bottom: 20px;
}

.mb20 {
  margin-bottom: 20px;
}
</style>

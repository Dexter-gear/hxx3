<template>
  <div class="app-container">
    <!-- 数据概览 -->
    <el-row :gutter="20" class="mb20">
      <el-col :span="6" v-for="(item, index) in overviewData" :key="index">
        <el-card shadow="hover" class="statistics-card">
          <div class="statistics-item">
            <div class="statistics-icon">
              <i :class="item.icon"></i>
            </div>
            <div class="statistics-info">
              <div class="statistics-title">{{ item.title }}</div>
              <div class="statistics-value">{{ item.value }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 图表展示 -->
    <el-row :gutter="20" class="mb20">
      <el-col :span="12">
        <el-card>
          <div slot="header" class="clearfix">
            <span>商品统计</span>
          </div>
          <div ref="productChart" class="chart-container"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <div slot="header" class="clearfix">
            <span>订单统计</span>
          </div>
          <div ref="orderChart" class="chart-container"></div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20">
      <el-col :span="12">
        <el-card>
          <div slot="header" class="clearfix">
            <span>商家统计</span>
          </div>
          <div ref="merchantChart" class="chart-container"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <div slot="header" class="clearfix">
            <span>评论统计</span>
          </div>
          <div ref="commentChart" class="chart-container"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import * as echarts from 'echarts'
import { getStatistics } from '@/api/monitor/statistics'

export default {
  name: 'Statistics',
  data() {
    return {
      // 商品统计数据
      productStats: {
        avg_price: 0,
        total_stock: 0,
        total_products: 0
      },
      // 订单统计数据
      orderStats: {
        total_amount: 0,
        total_orders: 0,
        avg_order_amount: 0
      },
      // 评论统计数据
      commentStats: {
        total_comments: 0,
        comments_last_30d: 0
      },
      // 商家统计数据
      merchantStats: {
        total_merchants: 0,
        total_sales_records: 0,
        avg_products_per_merchant: 0
      },
      // 图表实例
      charts: {
        product: null,
        order: null,
        merchant: null,
        comment: null
      }
    }
  },
  computed: {
    // 概览数据
    overviewData() {
      return [
        {
          title: '商品总数',
          value: this.productStats.total_products,
          icon: 'el-icon-goods'
        },
        {
          title: '订单总数',
          value: this.orderStats.total_orders,
          icon: 'el-icon-s-order'
        },
        {
          title: '评论总数',
          value: this.commentStats.total_comments,
          icon: 'el-icon-chat-dot-round'
        },
        {
          title: '商家总数',
          value: this.merchantStats.total_merchants,
          icon: 'el-icon-user'
        }
      ]
    }
  },
  mounted() {
    this.initCharts()
    this.initData()
    // 监听窗口大小变化，重绘图表
    window.addEventListener('resize', this.resizeCharts)
  },
  beforeDestroy() {
    // 移除事件监听
    window.removeEventListener('resize', this.resizeCharts)
    // 销毁图表实例
    Object.values(this.charts).forEach(chart => {
      chart && chart.dispose()
    })
  },
  methods: {
    // 初始化图表实例
    initCharts() {
      this.charts.product = echarts.init(this.$refs.productChart)
      this.charts.order = echarts.init(this.$refs.orderChart)
      this.charts.merchant = echarts.init(this.$refs.merchantChart)
      this.charts.comment = echarts.init(this.$refs.commentChart)
    },
    // 更新所有图表
    updateCharts() {
      this.updateProductChart()
      this.updateOrderChart()
      this.updateMerchantChart()
      this.updateCommentChart()
    },
    // 更新商品统计图表
    updateProductChart() {
      const option = {
        title: {
          text: '商品统计数据',
          left: 'center'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          },
          formatter: (params) => {
            if (params[0].name === '平均价格') {
              return `${params[0].name}: ¥${params[0].value.toFixed(2)}`
            }
            return `${params[0].name}: ${params[0].value}`
          }
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          data: ['平均价格', '总库存', '商品数量']
        },
        yAxis: [
          {
            type: 'value',
            name: '数量',
            position: 'left',
            axisLabel: {
              formatter: '{value}'
            }
          }
        ],
        series: [
          {
            name: '商品统计',
            type: 'bar',
            data: [
              this.productStats.avg_price,
              this.productStats.total_stock,
              this.productStats.total_products
            ],
            itemStyle: {
              color: function(params) {
                const colors = ['#FF9F7F', '#67C23A', '#409EFF']
                return colors[params.dataIndex]
              }
            },
            label: {
              show: true,
              position: 'top',
              formatter: (params) => {
                if (params.name === '平均价格') {
                  return `¥${params.value.toFixed(2)}`
                }
                return params.value
              }
            }
          }
        ]
      }
      this.charts.product.setOption(option)
    },
    // 更新订单统计图表
    updateOrderChart() {
      const option = {
        title: {
          text: '订单统计数据',
          left: 'center'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross'
          },
          formatter: (params) => {
            let result = `${params[0].name}<br/>`
            params.forEach(param => {
              const value = param.seriesName === '平均订单金额' ? 
                `¥${param.value.toFixed(2)}` : 
                param.seriesName === '总销售额' ? 
                `¥${param.value.toFixed(2)}` : 
                param.value
              result += `${param.marker}${param.seriesName}: ${value}<br/>`
            })
            return result
          }
        },
        legend: {
          data: ['总销售额', '订单数量', '平均订单金额'],
          bottom: 0
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '10%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          data: ['当前统计'],
          axisLabel: { show: false }
        },
        yAxis: [
          {
            type: 'value',
            name: '金额',
            position: 'left',
            axisLabel: {
              formatter: '¥{value}'
            }
          },
          {
            type: 'value',
            name: '数量',
            position: 'right'
          }
        ],
        series: [
          {
            name: '总销售额',
            type: 'bar',
            yAxisIndex: 0,
            data: [this.orderStats.total_amount],
            itemStyle: { color: '#409EFF' },
            label: {
              show: true,
              position: 'top',
              formatter: (params) => `¥${params.value.toFixed(2)}`
            }
          },
          {
            name: '订单数量',
            type: 'bar',
            yAxisIndex: 1,
            data: [this.orderStats.total_orders],
            itemStyle: { color: '#67C23A' },
            label: {
              show: true,
              position: 'top'
            }
          },
          {
            name: '平均订单金额',
            type: 'line',
            yAxisIndex: 0,
            data: [this.orderStats.avg_order_amount],
            itemStyle: { color: '#E6A23C' },
            label: {
              show: true,
              position: 'top',
              formatter: (params) => `¥${params.value.toFixed(2)}`
            }
          }
        ]
      }
      this.charts.order.setOption(option)
    },
    // 更新商家统计图表
    updateMerchantChart() {
      const avgProducts = this.merchantStats.avg_products_per_merchant
      const option = {
        title: {
          text: '商家统计数据',
          left: 'center'
        },
        tooltip: {
          trigger: 'item',
          formatter: (params) => {
            if (params.name === '平均商品数') {
              return `${params.name}: ${params.value.toFixed(2)}`
            }
            return `${params.name}: ${params.value}`
          }
        },
        legend: {
          orient: 'vertical',
          left: 'left'
        },
        series: [
          {
            type: 'pie',
            radius: ['50%', '70%'],
            avoidLabelOverlap: false,
            label: {
              show: true,
              position: 'outside',
              formatter: (params) => {
                if (params.name === '平均商品数') {
                  return `${params.name}\n${params.value.toFixed(2)}`
                }
                return `${params.name}\n${params.value}`
              }
            },
            emphasis: {
              label: {
                show: true,
                fontSize: '20',
                fontWeight: 'bold'
              }
            },
            data: [
              { 
                value: this.merchantStats.total_merchants, 
                name: '商家数量',
                itemStyle: { color: '#409EFF' }
              },
              { 
                value: this.merchantStats.total_sales_records, 
                name: '销售记录',
                itemStyle: { color: '#67C23A' }
              },
              { 
                value: avgProducts, 
                name: '平均商品数',
                itemStyle: { color: '#E6A23C' }
              }
            ]
          }
        ]
      }
      this.charts.merchant.setOption(option)
    },
    // 更新评论统计图表
    updateCommentChart() {
      const historicalComments = this.commentStats.total_comments - this.commentStats.comments_last_30d
      const option = {
        title: {
          text: '评论统计趋势',
          left: 'center'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          bottom: '5%',
          left: 'center'
        },
        color: ['#91CC75', '#FAC858'],
        series: [
          {
            name: '评论分布',
            type: 'pie',
            radius: ['40%', '70%'],
            center: ['50%', '45%'],
            itemStyle: {
              borderRadius: 10,
              borderColor: '#fff',
              borderWidth: 2
            },
            label: {
              show: true,
              formatter: '{b}: {c} ({d}%)'
            },
            data: [
              { 
                value: this.commentStats.comments_last_30d, 
                name: '近30天评论',
                itemStyle: { color: '#67C23A' }
              },
              { 
                value: historicalComments, 
                name: '历史评论',
                itemStyle: { color: '#E6A23C' }
              }
            ]
          }
        ]
      }
      this.charts.comment.setOption(option)
    },
    // 重绘图表
    resizeCharts() {
      Object.values(this.charts).forEach(chart => {
        chart && chart.resize()
      })
    },
    // 初始化数据
    async initData() {
      try {
        const res = await getStatistics()
        console.log('API返回数据:', res)
        if (res.code === 200 && res.rows) {
          // 处理返回的数组数据
          res.rows.forEach(item => {
            try {
              // 解析stats字段中的JSON字符串
              const statsData = JSON.parse(item.stats || '{}')
              console.log('解析后的统计数据:', item.module, statsData)

              // 根据module字段判断数据类型
              switch(item.module) {
                case '商品信息统计':
                  this.productStats = {
                    avg_price: statsData.avg_price || 0,
                    total_stock: statsData.total_stock || 0,
                    total_products: statsData.total_products || 0
                  }
                  break
                case '订单信息统计':
                  this.orderStats = {
                    total_amount: statsData.total_amount || 0,
                    total_orders: statsData.total_orders || 0,
                    avg_order_amount: statsData.avg_order_amount || 0
                  }
                  break
                case '评论信息统计':
                  this.commentStats = {
                    total_comments: statsData.total_comments || 0,
                    comments_last_30d: statsData.comments_last_30d || 0
                  }
                  break
                case '商家信息统计':
                  this.merchantStats = {
                    total_merchants: statsData.total_merchants || 0,
                    total_sales_records: statsData.total_sales_records || 0,
                    avg_products_per_merchant: statsData.avg_products_per_merchant || 0
                  }
                  break
              }
            } catch (parseError) {
              console.error('解析统计数据失败:', parseError)
            }
          })
          // 更新图表
          this.updateCharts()
        } else {
          this.$message.error(res.msg || '获取数据失败')
        }
      } catch (error) {
        console.error('获取统计数据失败:', error)
        this.$message.error('获取数据失败')
      }
    },
    // 刷新数据
    refreshData() {
      this.initData()
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

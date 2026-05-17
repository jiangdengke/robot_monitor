import { Comment, Fragment, defineComponent, h, ref } from 'vue'
import {
  App,
  Avatar,
  Breadcrumb,
  BreadcrumbItem,
  Button,
  Card,
  Col,
  DatePicker,
  Descriptions,
  DescriptionsItem,
  Divider,
  Drawer,
  Dropdown,
  DropdownButton,
  Form,
  FormItem,
  Input,
  InputNumber,
  Layout,
  Menu,
  Modal,
  Pagination,
  Progress,
  Radio,
  Row,
  Select,
  Space,
  Spin,
  Statistic,
  Table,
  Tabs,
  Tag,
  Timeline,
  TreeSelect,
  Upload
} from 'ant-design-vue'

export const ElButton = Button
export const ElCard = Card
export const ElRow = Row
export const ElCol = Col
export const ElSpace = Space
export const ElText = 'span'
export const ElInput = Input
export const ElInputNumber = InputNumber
export const ElForm = Form
export const ElFormItem = FormItem
export const ElSelect = Select
export const ElOption = Select.Option
export const ElDatePicker = DatePicker
function flattenVNodes(nodes = []) {
  return nodes.flatMap((node) => {
    if (!node || node.type === Comment) {
      return []
    }
    if (Array.isArray(node)) {
      return flattenVNodes(node)
    }
    if (node.type === Fragment) {
      return flattenVNodes(node.children || [])
    }
    return [node]
  })
}

function getByPath(target, path = '') {
  return String(path)
    .split('.')
    .filter(Boolean)
    .reduce((current, key) => current?.[key], target)
}

function resolveRowKey(rowKey, record, index) {
  if (typeof rowKey === 'function') {
    return rowKey(record)
  }
  return record?.[rowKey] ?? record?.id ?? index
}

function buildColumns(nodes) {
  const columns = []
  let hasSelection = false

  flattenVNodes(nodes).forEach((node) => {
    const props = node.props || {}
    if (props.type === 'selection') {
      hasSelection = true
      return
    }

    const dataIndex = props.prop || props.dataIndex
    const key = props.key || dataIndex || props.label || columns.length
    const column = {
      key,
      title: props.label || props.title || '',
      dataIndex,
      width: props.width || props.minWidth,
      fixed: props.fixed,
      align: props.align,
      customRender: ({ text, record, index }) => {
        if (props.type === 'index') {
          return index + 1
        }
        if (node.children?.default) {
          return node.children.default({
            row: record,
            column: props,
            $index: index,
            text,
            record,
            index
          })
        }
        if (typeof props.formatter === 'function') {
          return props.formatter(record, props, text, index)
        }
        const value = dataIndex ? getByPath(record, dataIndex) : text
        return value === undefined || value === null || value === '' ? '-' : value
      }
    }

    columns.push(column)
  })

  return { columns, hasSelection }
}

export const ElTable = defineComponent({
  name: 'ElTable',
  inheritAttrs: false,
  props: {
    data: { type: Array, default: () => [] },
    rowKey: { type: [String, Function], default: 'id' },
    border: { type: Boolean, default: false },
    stripe: { type: Boolean, default: false },
    size: { type: String, default: 'middle' },
    height: { type: [String, Number], default: null },
    maxHeight: { type: [String, Number], default: null }
  },
  emits: ['selection-change', 'row-click'],
  setup(props, { attrs, emit, slots }) {
    const selectedRowKeys = ref([])

    return () => {
      const { columns, hasSelection } = buildColumns(slots.default?.() || [])
      const scrollY = props.height || props.maxHeight || attrs.height || attrs.maxHeight
      const rowSelection = hasSelection
        ? {
            selectedRowKeys: selectedRowKeys.value,
            onChange: (keys, rows) => {
              selectedRowKeys.value = keys
              emit('selection-change', rows)
            }
          }
        : undefined

      return h(Table, {
        ...attrs,
        columns,
        dataSource: props.data,
        rowKey: (record, index) => resolveRowKey(props.rowKey, record, index),
        bordered: props.border || attrs.bordered,
        pagination: false,
        rowSelection,
        size: props.size === 'small' ? 'small' : 'middle',
        scroll: scrollY ? { ...(attrs.scroll || {}), y: scrollY } : attrs.scroll,
        rowClassName: props.stripe
          ? (_record, index) => (index % 2 === 1 ? 'el-table__row--striped' : '')
          : attrs.rowClassName,
        customRow: (record, index) => ({
          onClick: (event) => emit('row-click', record, undefined, event, index)
        })
      })
    }
  }
})

export const ElTableColumn = defineComponent({
  name: 'ElTableColumn',
  setup() {
    return () => null
  }
})

export const ElPagination = defineComponent({
  name: 'ElPagination',
  inheritAttrs: false,
  props: {
    currentPage: { type: Number, default: 1 },
    pageSize: { type: Number, default: 10 },
    pageSizes: { type: Array, default: () => [10, 20, 50, 100] },
    total: { type: Number, default: 0 },
    layout: { type: String, default: '' },
    background: { type: Boolean, default: false }
  },
  emits: ['current-change', 'size-change'],
  setup(props, { attrs, emit }) {
    return () => h(Pagination, {
      ...attrs,
      current: props.currentPage,
      pageSize: props.pageSize,
      total: props.total,
      pageSizeOptions: props.pageSizes.map(String),
      showSizeChanger: props.layout.includes('sizes'),
      showQuickJumper: props.layout.includes('jumper'),
      showTotal: props.layout.includes('total') ? (total) => `共 ${total} 条` : undefined,
      onChange: (page) => emit('current-change', page),
      onShowSizeChange: (_current, size) => emit('size-change', size)
    })
  }
})
export const ElTag = Tag
export const ElAvatar = Avatar
export const ElDivider = Divider
export const ElBreadcrumb = Breadcrumb
export const ElBreadcrumbItem = BreadcrumbItem
export const ElDescriptions = Descriptions
export const ElDescriptionsItem = DescriptionsItem
export const ElTimeline = Timeline
export const ElTimelineItem = Timeline.Item
export const ElTabs = Tabs
export const ElTabPane = Tabs.TabPane
export const ElStatistic = Statistic
export const ElProgress = Progress
export const ElTreeSelect = TreeSelect
export const ElUpload = Upload
export const ElDropdown = Dropdown
export const ElDropdownItem = Menu.Item
export const ElDropdownMenu = Menu
export const ElRadioGroup = Radio.Group
export const ElRadio = Radio
export const ElRadioButton = Radio.Button
export const ElDialog = Modal
export const ElDrawer = Drawer
export const ElScrollbar = 'div'
export const ElMenu = Menu
export const ElMenuItem = Menu.Item
export const ElSubMenu = Menu.SubMenu
export const ElContainer = Layout
export const ElMain = Layout.Content
export const ElAside = Layout.Sider
export const ElHeader = Layout.Header
export const ElFooter = Layout.Footer
export const ElIcon = 'span'

export const ElMessage = {
  success: App.useApp ? undefined : null
}

export { Modal as ElMessageBox, Spin as ElLoading }

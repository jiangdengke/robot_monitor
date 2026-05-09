<template>
  <section class="map-panel">
    <div class="map-toolbar">
      <div>
        <strong>{{ roomName }}</strong>
        <span>{{ selectedRegion?.regionName || selectedRegion?.areaName || '选择区域查看联动数据' }}</span>
      </div>
      <el-tag>{{ robots.length }} 台机器人</el-tag>
    </div>
    <div class="map-canvas">
      <button
        v-for="region in regions"
        :key="region.id"
        class="region-node"
        :class="{ active: selectedRegion?.id === region.id }"
        :style="nodeStyle(region)"
        @click="$emit('select-region', region)"
      >
        <span>{{ region.regionName || region.areaName || `区域${region.id}` }}</span>
        <small>{{ region.maxCapacity || 0 }} 人</small>
      </button>
      <button
        v-for="robot in robots"
        :key="robot.id || robot.robotId"
        class="robot-node"
        :style="robotStyle(robot)"
        @click="$emit('select-robot', robot)"
      >
        {{ robot.robotName || robot.robotId || 'R' }}
      </button>
      <button
        v-for="passenger in passengers"
        :key="passenger.id"
        class="passenger-node"
        :style="passengerStyle(passenger)"
        @click="$emit('select-passenger', passenger)"
      >
        {{ passenger.userName || '旅客' }}
      </button>
    </div>
  </section>
</template>

<script setup>
const props = defineProps({
  roomName: { type: String, default: '贵宾室地图' },
  regions: { type: Array, default: () => [] },
  robots: { type: Array, default: () => [] },
  passengers: { type: Array, default: () => [] },
  selectedRegion: { type: Object, default: null }
})

defineEmits(['select-region', 'select-robot', 'select-passenger'])

function parsePoint(text, fallbackX, fallbackY) {
  const match = String(text || '').match(/(-?\d+(?:\.\d+)?)[,，\s]+(-?\d+(?:\.\d+)?)/)
  if (!match) return { x: fallbackX, y: fallbackY }
  return {
    x: Math.max(5, Math.min(92, Number(match[1]) % 100)),
    y: Math.max(7, Math.min(88, Number(match[2]) % 100))
  }
}

function nodeStyle(region) {
  const index = props.regions.findIndex((item) => item.id === region.id)
  const point = parsePoint(region.coordinate || region.cameraCoordinates || region.coordinates, 14 + (index % 4) * 22, 18 + Math.floor(index / 4) * 24)
  return { left: `${point.x}%`, top: `${point.y}%` }
}

function robotStyle(robot) {
  const index = props.robots.findIndex((item) => (item.id || item.robotId) === (robot.id || robot.robotId))
  const point = parsePoint(robot.coordinate || robot.cameraCoordinates || robot.oriCoordinate || robot.position, 18 + (index % 5) * 17, 62 + (index % 2) * 13)
  return { left: `${point.x}%`, top: `${point.y}%` }
}

function passengerStyle(passenger) {
  const index = props.passengers.findIndex((item) => item.id === passenger.id)
  const point = parsePoint(passenger.coordinate || passenger.cameraCoordinates || passenger.position, 10 + (index % 6) * 14, 42 + (index % 3) * 12)
  return { left: `${point.x}%`, top: `${point.y}%` }
}
</script>

<style scoped>
.map-panel { padding: 18px; border: 1px solid var(--line); border-radius: 16px; background: var(--panel-alt); }
.map-toolbar { display: flex; align-items: center; justify-content: space-between; gap: 12px; margin-bottom: 12px; }
.map-toolbar div { display: grid; gap: 4px; }
.map-toolbar span { color: var(--text-soft); font-size: 13px; }
.map-canvas {
  position: relative;
  min-height: 430px;
  overflow: hidden;
  border-radius: 16px;
  border: 1px solid rgba(13, 92, 171, 0.18);
  background:
    radial-gradient(circle at 20% 20%, rgba(13, 92, 171, .18), transparent 20%),
    radial-gradient(circle at 80% 70%, rgba(21, 128, 61, .13), transparent 22%),
    linear-gradient(90deg, rgba(13, 92, 171, 0.08) 1px, transparent 1px),
    linear-gradient(rgba(13, 92, 171, 0.08) 1px, transparent 1px),
    #f8fbff;
  background-size: auto, auto, 34px 34px, 34px 34px, auto;
}
.region-node,
.robot-node,
.passenger-node {
  position: absolute;
  transform: translate(-50%, -50%);
  border: 0;
  cursor: pointer;
  box-shadow: 0 12px 28px rgba(15, 42, 71, .16);
}
.region-node {
  display: grid;
  gap: 4px;
  min-width: 116px;
  padding: 12px 14px;
  border-radius: 18px;
  color: #0f375f;
  background: rgba(255, 255, 255, .92);
}
.region-node.active { outline: 3px solid rgba(13, 92, 171, .28); background: #e5f1ff; }
.region-node small { color: var(--text-soft); }
.robot-node {
  width: 52px;
  height: 52px;
  border-radius: 16px;
  color: #fff;
  background: linear-gradient(135deg, #0d5cab, #27a0d8);
  font-weight: 700;
}
.passenger-node {
  padding: 7px 10px;
  border-radius: 999px;
  color: #14532d;
  background: #dcfce7;
  font-size: 12px;
}
</style>

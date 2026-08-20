// src/api/roadSegmentApi.js
import api from './axiosConfig'

// GET /api/public/road-segments — used to populate dropdown in submit form
export const getRoadSegments = () => {
  return api.get('/api/public/road-segments')
}










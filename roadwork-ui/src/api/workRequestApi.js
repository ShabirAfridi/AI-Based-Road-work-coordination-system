// All work-request related API calls
import api from './axiosConfig'

// POST /api/work-requests — submit new request
export const submitWorkRequest = (data) => {
  return api.post('/api/work-requests', data)
}

// GET /api/work-requests — get all (filtered by role on backend)
export const getWorkRequests = () => {
  return api.get('/api/work-requests')
}

// GET /api/work-requests/{id}
export const getWorkRequestById = (id) => {
  return api.get(`/api/work-requests/${id}`)
}

// PUT /api/work-requests/{id}
export const updateWorkRequest = (id, data) => {
  return api.put(`/api/work-requests/${id}`, data)
}

// DELETE /api/work-requests/{id} — cancel
export const cancelWorkRequest = (id) => {
  return api.delete(`/api/work-requests/${id}`)
}

// GET /api/work-requests/active — for map
export const getActiveWorkRequests = () => {
  return api.get('/api/work-requests/active')
}
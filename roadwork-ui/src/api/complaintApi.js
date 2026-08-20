import api from './axiosConfig'

// POST /api/public/complaints — works with or without login
export const submitComplaint = (data) => {
  return api.post('/api/public/complaints', data)
}

// GET /api/complaints/mine — requires login
export const getMyComplaints = () => {
  return api.get('/api/complaints/mine')
}

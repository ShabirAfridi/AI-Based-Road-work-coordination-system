import axios from 'axios'
// api call go to spring boot on port 9090
const api = axios.create({
baseURL: 'http://localhost:9090',
headers: {
    'Content-Type': 'application/json',
  },
})
// Handle 403 globally — redirect to login
api.interceptors.response.use(
(response) => response,
(error) => {
if(error.response?.status === 403 ){

sessionStorage.removeItem('token')
sessionStorage.removeItem('user')
window.location.href = '/login'
}
return Promise.reject(error)
})


api.interceptors.request.use(
(config)=> {
const token =sessionStorage.getItem('token')
if (token && token !== 'null' && token !== 'undefined' && token.split('.').length === 3){
config.headers.Authorization = `Bearer ${token}`;
}
return config
},
(error)=> Promise.reject(error)
);
export default api

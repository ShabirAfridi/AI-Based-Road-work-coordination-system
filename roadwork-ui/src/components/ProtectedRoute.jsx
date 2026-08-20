// Wraps routes that require login
// If not logged in → redirects to /login
// If wrong role → redirects to /unauthorized

import { Navigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'

function ProtectedRoute({ children, allowedRoles }) {
  const { isLoggedIn, user } = useAuth()

  // Not logged in → go to login page
  if (!isLoggedIn()) {
    return <Navigate to="/login" replace />
  }

  // Logged in but wrong role → go to unauthorized page
  if (allowedRoles && !allowedRoles.includes(user?.role)) {
    return <Navigate to="/unauthorized" replace />
  }

  // All good → render the page
  return children
}

export default ProtectedRoute

import { useState } from 'react'
import { rejectRequest } from '../../api/adminApi'

function RejectModal({ requestId, onClose, onSuccess }) {
  const [remarks, setRemarks] = useState('')
  const [error, setError]     = useState('')
  const [loading, setLoading] = useState(false)

  const handleSubmit = async (e) => {
    e.preventDefault()
    if (!remarks.trim()) {
      setError('Remarks are required to reject a request')
      return
    }
    setLoading(true)
    try {
      await rejectRequest(requestId, remarks)
      onSuccess()
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to reject request')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div style={styles.overlay} onClick={onClose}>
      <div style={styles.modal} onClick={(e) => e.stopPropagation()}>
        <h3 style={styles.title}>Reject Request</h3>

        {error && <div style={styles.error}>{error}</div>}

        <form onSubmit={handleSubmit}>
          <textarea
            style={styles.textarea}
            value={remarks}
            onChange={(e) => setRemarks(e.target.value)}
            placeholder="Reason for rejection..."
            required
          />
          <div style={styles.actions}>
            <button type="button" style={styles.cancelBtn} onClick={onClose}>
              Cancel
            </button>
            <button
              type="submit"
              style={loading ? styles.rejectBtnDisabled : styles.rejectBtn}
              disabled={loading}
            >
              {loading ? 'Rejecting...' : 'Reject'}
            </button>
          </div>
        </form>
      </div>
    </div>
  )
}

const styles = {
  overlay: {
    position: 'fixed', inset: 0, background: 'rgba(0,0,0,0.5)',
    display: 'flex', alignItems: 'center', justifyContent: 'center', zIndex: 1000,
  },
  modal: {
    background: '#fff', borderRadius: '12px', padding: '1.5rem',
    width: '100%', maxWidth: '400px',
  },
  title: { fontSize: '1.1rem', fontWeight: '700', color: '#1a1a2e', marginBottom: '1rem' },
  error: {
    background: '#fee2e2', color: '#991b1b', padding: '0.6rem 0.9rem',
    borderRadius: '8px', marginBottom: '0.9rem', fontSize: '0.85rem',
  },
  textarea: {
    width: '100%', minHeight: '90px', padding: '0.6rem 0.8rem',
    border: '1px solid #d1d5db', borderRadius: '8px',
    fontSize: '0.9rem', outline: 'none', boxSizing: 'border-box',
    resize: 'vertical', fontFamily: 'inherit',
  },
  actions: { display: 'flex', gap: '0.65rem', marginTop: '1rem' },
  cancelBtn: {
    flex: 1, padding: '0.65rem', background: '#f3f4f6', color: '#374151',
    border: 'none', borderRadius: '8px', fontWeight: '600', cursor: 'pointer',
  },
  rejectBtn: {
    flex: 1, padding: '0.65rem', background: '#dc2626', color: '#fff',
    border: 'none', borderRadius: '8px', fontWeight: '600', cursor: 'pointer',
  },
  rejectBtnDisabled: {
    flex: 1, padding: '0.65rem', background: '#fca5a5', color: '#fff',
    border: 'none', borderRadius: '8px', fontWeight: '600', cursor: 'not-allowed',
  },
}

export default RejectModal
 import { useState } from 'react'
import { resolveComplaintAdmin } from '../../api/adminApi'

function ResolveComplaintModal({ complaint, onClose, onSuccess }) {
  const [adminResponse, setAdminResponse] = useState('')
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)

  const submit = async (dismiss) => {
    if (!adminResponse.trim()) {
      setError('Please provide a response before continuing')
      return
    }
    setError('')
    setLoading(true)
    try {
      await resolveComplaintAdmin(complaint.id, adminResponse, dismiss)
      onSuccess()
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to update complaint')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div style={styles.overlay} onClick={onClose}>
      <div style={styles.modal} onClick={(e) => e.stopPropagation()}>
        <h3 style={styles.title}>Complaint #{complaint.id}</h3>

        <div style={styles.infoBox}>
          <div style={styles.infoRow}><strong>Reported by:</strong> {complaint.reportedByName}</div>
          <div style={styles.infoRow}><strong>Road:</strong> {complaint.roadSegmentName}</div>
          <div style={styles.infoRow}><strong>Category:</strong> {complaint.category?.replace(/_/g, ' ')}</div>
          <div style={styles.infoRow}><strong>Description:</strong> {complaint.description}</div>
        </div>

        {error && <div style={styles.error}>{error}</div>}

        <label style={styles.label}>Admin Response</label>
        <textarea
          style={styles.textarea}
          value={adminResponse}
          onChange={(e) => setAdminResponse(e.target.value)}
          placeholder="Explain what action was taken..."
        />

        <div style={styles.actions}>
          <button type="button" style={styles.cancelBtn} onClick={onClose}>Cancel</button>
          <button
            type="button"
            style={loading ? styles.dismissBtnDisabled : styles.dismissBtn}
            disabled={loading}
            onClick={() => submit(true)}
          >
            Dismiss
          </button>
          <button
            type="button"
            style={loading ? styles.resolveBtnDisabled : styles.resolveBtn}
            disabled={loading}
            onClick={() => submit(false)}
          >
            Mark Resolved
          </button>
        </div>
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
    width: '100%', maxWidth: '440px',
  },
  title: { fontSize: '1.1rem', fontWeight: '700', color: '#1a1a2e', marginBottom: '1rem' },
  infoBox: {
    background: '#f9fafb', border: '1px solid #e5e7eb', borderRadius: '8px',
    padding: '0.85rem', marginBottom: '1rem',
  },
  infoRow: { fontSize: '0.82rem', color: '#374151', marginBottom: '0.35rem' },
  error: {
    background: '#fee2e2', color: '#991b1b', padding: '0.6rem 0.9rem',
    borderRadius: '8px', marginBottom: '0.9rem', fontSize: '0.85rem',
  },
  label: {
    display: 'block', marginBottom: '0.35rem',
    fontSize: '0.85rem', fontWeight: '500', color: '#374151',
  },
  textarea: {
    width: '100%', minHeight: '90px', padding: '0.6rem 0.8rem',
    border: '1px solid #d1d5db', borderRadius: '8px',
    fontSize: '0.9rem', outline: 'none', boxSizing: 'border-box',
    resize: 'vertical', fontFamily: 'inherit',
  },
  actions: { display: 'flex', gap: '0.6rem', marginTop: '1rem' },
  cancelBtn: {
    flex: 1, padding: '0.6rem', background: '#f3f4f6', color: '#374151',
    border: 'none', borderRadius: '8px', fontWeight: '600', cursor: 'pointer', fontSize: '0.85rem',
  },
  dismissBtn: {
    flex: 1, padding: '0.6rem', background: '#6b7280', color: '#fff',
    border: 'none', borderRadius: '8px', fontWeight: '600', cursor: 'pointer', fontSize: '0.85rem',
  },
  dismissBtnDisabled: {
    flex: 1, padding: '0.6rem', background: '#d1d5db', color: '#fff',
    border: 'none', borderRadius: '8px', fontWeight: '600', cursor: 'not-allowed', fontSize: '0.85rem',
  },
  resolveBtn: {
    flex: 1, padding: '0.6rem', background: '#16a34a', color: '#fff',
    border: 'none', borderRadius: '8px', fontWeight: '600', cursor: 'pointer', fontSize: '0.85rem',
  },
  resolveBtnDisabled: {
    flex: 1, padding: '0.6rem', background: '#86efac', color: '#fff',
    border: 'none', borderRadius: '8px', fontWeight: '600', cursor: 'not-allowed', fontSize: '0.85rem',
  },
}

export default ResolveComplaintModal

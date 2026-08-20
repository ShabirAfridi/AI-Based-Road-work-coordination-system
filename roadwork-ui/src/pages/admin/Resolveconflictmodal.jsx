
import { useState } from 'react'
import { resolveConflict } from '../../api/adminApi'

function ResolveConflictModal({ conflict, onClose, onSuccess }) {
  const [resolution, setResolution]   = useState('MERGED')
  const [rejectId, setRejectId]       = useState(conflict.requestAId)
  const [remarks, setRemarks]         = useState('')
  const [error, setError]             = useState('')
  const [loading, setLoading]         = useState(false)

  const handleSubmit = async (e) => {
    e.preventDefault()
    setError('')

    if (!remarks.trim()) {
      setError('Admin remarks are required')
      return
    }

    setLoading(true)
    try {
      await resolveConflict(
        conflict.id,
        resolution,
        remarks,
        resolution === 'ONE_REJECTED' ? rejectId : null
      )
      onSuccess()
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to resolve conflict')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div style={styles.overlay} onClick={onClose}>
      <div style={styles.modal} onClick={(e) => e.stopPropagation()}>

        <div style={styles.header}>
          <h3 style={styles.title}>Resolve Conflict #{conflict.id}</h3>
          <button style={styles.closeBtn} onClick={onClose}>✕</button>
        </div>

        {/* Side-by-side comparison of both conflicting requests */}
        <div style={styles.compareGrid}>
          <div style={styles.compareCard}>
            <div style={styles.compareLabel}>Request A</div>
            <div style={styles.compareDept}>{conflict.requestADepartment}</div>
            <div style={styles.compareDetail}>{conflict.requestARoadSegment}</div>
            <div style={styles.compareDetail}>
              {conflict.requestAStartDate} → {conflict.requestAEndDate}
            </div>
          </div>
          <div style={styles.compareCard}>
            <div style={styles.compareLabel}>Request B</div>
            <div style={styles.compareDept}>{conflict.requestBDepartment}</div>
            <div style={styles.compareDetail}>{conflict.requestBRoadSegment}</div>
            <div style={styles.compareDetail}>
              {conflict.requestBStartDate} → {conflict.requestBEndDate}
            </div>
          </div>
        </div>

        {error && <div style={styles.error}>{error}</div>}

        <form onSubmit={handleSubmit}>

          <div style={styles.field}>
            <label style={styles.label}>Resolution</label>
            <select
              style={styles.input}
              value={resolution}
              onChange={(e) => setResolution(e.target.value)}
            >
              <option value="MERGED">Merged — both departments dig together</option>
              <option value="ONE_REJECTED">One Rejected — reject one request</option>
              <option value="RESCHEDULED">Rescheduled — both must resubmit</option>
            </select>
          </div>

          {/* Only show "which request to reject" dropdown for ONE_REJECTED */}
          {resolution === 'ONE_REJECTED' && (
            <div style={styles.field}>
              <label style={styles.label}>Which request to reject?</label>
              <select
                style={styles.input}
                value={rejectId}
                onChange={(e) => setRejectId(parseInt(e.target.value))}
              >
                <option value={conflict.requestAId}>
                  Request A — {conflict.requestADepartment}
                </option>
                <option value={conflict.requestBId}>
                  Request B — {conflict.requestBDepartment}
                </option>
              </select>
            </div>
          )}

          <div style={styles.field}>
            <label style={styles.label}>Admin Remarks (required)</label>
            <textarea
              style={{ ...styles.input, minHeight: '80px', resize: 'vertical' }}
              value={remarks}
              onChange={(e) => setRemarks(e.target.value)}
              placeholder="Explain your decision..."
              required
            />
          </div>

          <div style={styles.actions}>
            <button type="button" style={styles.cancelBtn} onClick={onClose}>
              Cancel
            </button>
            <button
              type="submit"
              style={loading ? styles.submitBtnDisabled : styles.submitBtn}
              disabled={loading}
            >
              {loading ? 'Resolving...' : 'Resolve Conflict'}
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
    background: '#fff', borderRadius: '12px', padding: '1.75rem',
    width: '100%', maxWidth: '540px', maxHeight: '90vh', overflowY: 'auto',
  },
  header: {
    display: 'flex', justifyContent: 'space-between', alignItems: 'center',
    marginBottom: '1.25rem',
  },
  title: { fontSize: '1.2rem', fontWeight: '700', color: '#1a1a2e' },
  closeBtn: {
    background: 'none', border: 'none', fontSize: '1.2rem',
    cursor: 'pointer', color: '#6b7280',
  },
  compareGrid: {
    display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '0.75rem',
    marginBottom: '1.25rem',
  },
  compareCard: {
    background: '#f9fafb', border: '1px solid #e5e7eb',
    borderRadius: '8px', padding: '0.85rem',
  },
  compareLabel: {
    fontSize: '0.7rem', fontWeight: '700', color: '#9ca3af',
    textTransform: 'uppercase', marginBottom: '0.4rem',
  },
  compareDept: { fontWeight: '700', fontSize: '0.9rem', color: '#1a1a2e' },
  compareDetail: { fontSize: '0.8rem', color: '#6b7280', marginTop: '0.2rem' },
  error: {
    background: '#fee2e2', color: '#991b1b', padding: '0.65rem 1rem',
    borderRadius: '8px', marginBottom: '1rem', fontSize: '0.85rem',
  },
  field: { marginBottom: '1rem' },
  label: {
    display: 'block', marginBottom: '0.35rem',
    fontSize: '0.85rem', fontWeight: '500', color: '#374151',
  },
  input: {
    width: '100%', padding: '0.6rem 0.8rem',
    border: '1px solid #d1d5db', borderRadius: '8px',
    fontSize: '0.9rem', outline: 'none', boxSizing: 'border-box',
    background: '#fff', fontFamily: 'inherit',
  },
  actions: { display: 'flex', gap: '0.75rem', marginTop: '1.5rem' },
  cancelBtn: {
    flex: 1, padding: '0.7rem', background: '#f3f4f6', color: '#374151',
    border: 'none', borderRadius: '8px', fontWeight: '600', cursor: 'pointer',
  },
  submitBtn: {
    flex: 1, padding: '0.7rem', background: '#2563eb', color: '#fff',
    border: 'none', borderRadius: '8px', fontWeight: '600', cursor: 'pointer',
  },
  submitBtnDisabled: {
    flex: 1, padding: '0.7rem', background: '#93c5fd', color: '#fff',
    border: 'none', borderRadius: '8px', fontWeight: '600', cursor: 'not-allowed',
  },
}

export default ResolveConflictModal
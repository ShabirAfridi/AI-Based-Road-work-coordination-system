// src/components/StatusBadge.jsx
// Reusable colored badge for work request status
// Used in department dashboard, admin dashboard, citizen portal

const STATUS_STYLES = {
  PENDING:          { bg: '#fef3c7', color: '#92400e', label: 'Pending' },
  CONFLICT_FLAGGED: { bg: '#fee2e2', color: '#991b1b', label: 'Conflict' },
  APPROVED:         { bg: '#d1fae5', color: '#065f46', label: 'Approved' },
  REJECTED:         { bg: '#fee2e2', color: '#7f1d1d', label: 'Rejected' },
  IN_PROGRESS:      { bg: '#dbeafe', color: '#1e40af', label: 'In Progress' },
  COMPLETED:        { bg: '#e5e7eb', color: '#374151', label: 'Completed' },
  CANCELLED:        { bg: '#f3f4f6', color: '#6b7280', label: 'Cancelled' },
}

function StatusBadge({ status }) {
  const style = STATUS_STYLES[status] || STATUS_STYLES.PENDING

  return (
    <span style={{
      display: 'inline-block',
      padding: '0.25rem 0.7rem',
      borderRadius: '999px',
      fontSize: '0.75rem',
      fontWeight: '600',
      background: style.bg,
      color: style.color,
    }}>
      {style.label}
    </span>
  )
}

export default StatusBadge
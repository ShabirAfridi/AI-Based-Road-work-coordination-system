// Small legend showing what each marker color means

const LEGEND_ITEMS = [
  { status: 'PENDING',          label: 'Pending',    color: '#f59e0b' },
  { status: 'CONFLICT_FLAGGED', label: 'Conflict',   color: '#dc2626' },
  { status: 'APPROVED',         label: 'Approved',   color: '#16a34a' },
  { status: 'IN_PROGRESS',      label: 'In Progress',color: '#2563eb' },
  { status: 'COMPLETED',        label: 'Completed',  color: '#6b7280' },
]

function MapLegend() {
  return (
    <div style={styles.legend}>
      {LEGEND_ITEMS.map(item => (
        <div key={item.status} style={styles.item}>
          <span style={{ ...styles.dot, backgroundColor: item.color }} />
          <span style={styles.label}>{item.label}</span>
        </div>
      ))}
    </div>
  )
}

const styles = {
  legend: {
    display: 'flex', flexWrap: 'wrap', gap: '1rem',
    background: '#fff', border: '1px solid #e5e7eb',
    borderRadius: '10px', padding: '0.75rem 1rem',
    marginBottom: '1rem',
  },
  item: { display: 'flex', alignItems: 'center', gap: '0.4rem' },
  dot: {
    width: '10px', height: '10px', borderRadius: '50%',
    display: 'inline-block',
  },
  label: { fontSize: '0.8rem', color: '#374151' },
}

export default MapLegend
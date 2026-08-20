// Reusable Leaflet map — shows work requests as colored markers
// Used by: Citizen Map page, Department Dashboard, Admin Dashboard

import { MapContainer, TileLayer, Marker, Popup } from 'react-leaflet'
import L from 'leaflet'
import 'leaflet/dist/leaflet.css'
import StatusBadge from './StatusBadge'

// ── Fix default marker icon issue in Leaflet + Vite ─────────────
// Leaflet's default marker images don't load correctly with Vite's
// bundler because of how it resolves image paths. This fixes it.
delete L.Icon.Default.prototype._getIconUrl
L.Icon.Default.mergeOptions({
  iconRetinaUrl: 'https://unpkg.com/leaflet@1.9.4/dist/images/marker-icon-2x.png',
  iconUrl:       'https://unpkg.com/leaflet@1.9.4/dist/images/marker-icon.png',
  shadowUrl:     'https://unpkg.com/leaflet@1.9.4/dist/images/marker-shadow.png',
})

// ── Custom colored icons per status ──────────────────────────────
// Different colored pins so status is visible at a glance on the map
const createColoredIcon = (color) => {
  return L.divIcon({
    className: 'custom-marker',
    html: `
      <div style="
        background-color: ${color};
        width: 22px;
        height: 22px;
        border-radius: 50% 50% 50% 0;
        transform: rotate(-45deg);
        border: 2px solid #fff;
        box-shadow: 0 2px 5px rgba(0,0,0,0.3);
      "></div>
    `,
    iconSize: [22, 22],
    iconAnchor: [11, 22],
    popupAnchor: [0, -22],
  })
}

const STATUS_COLORS = {
  PENDING:          '#f59e0b',
  CONFLICT_FLAGGED: '#dc2626',
  APPROVED:         '#16a34a',
  IN_PROGRESS:      '#2563eb',
  COMPLETED:        '#6b7280',
  REJECTED:         '#991b1b',
  CANCELLED:        '#9ca3af',
}

const STATUS_ICONS = Object.fromEntries(
  Object.entries(STATUS_COLORS).map(([status, color]) => [status, createColoredIcon(color)])
)

// Default center — Islamabad
const DEFAULT_CENTER = [33.6844, 73.0479]
const DEFAULT_ZOOM   = 12

function WorkRequestMap({ requests, height = '500px' }) {

  // Filter out requests with no coordinates — can't place them on map
  const mappable = requests.filter(r => r.latitude && r.longitude)

  return (
    <div style={{ ...styles.mapWrapper, height }}>
      <MapContainer
        center={DEFAULT_CENTER}
        zoom={DEFAULT_ZOOM}
        style={{ height: '100%', width: '100%', borderRadius: '12px' }}
        scrollWheelZoom={true}
      >
        {/* OpenStreetMap tiles — completely free, no API key needed */}
        <TileLayer
          attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
          url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        />

        {mappable.map(req => (
          <Marker
            key={req.id}
            position={[req.latitude, req.longitude]}
            icon={STATUS_ICONS[req.status] || STATUS_ICONS.PENDING}
          >
            <Popup>
              <div style={styles.popup}>
                <div style={styles.popupTitle}>{req.roadSegmentName}</div>
                <div style={styles.popupRow}>
                  <strong>Department:</strong> {req.departmentName}
                </div>
                <div style={styles.popupRow}>
                  <strong>Work Type:</strong> {req.workType?.replace(/_/g, ' ')}
                </div>
                <div style={styles.popupRow}>
                  <strong>Dates:</strong> {req.startDate} → {req.endDate}
                </div>
                <div style={styles.popupStatus}>
                  <StatusBadge status={req.status} />
                </div>
              </div>
            </Popup>
          </Marker>
        ))}
      </MapContainer>
    </div>
  )
}

const styles = {
  mapWrapper: {
    width: '100%',
    borderRadius: '12px',
    overflow: 'hidden',
    border: '1px solid #e5e7eb',
  },
  popup: { minWidth: '180px' },
  popupTitle: { fontWeight: '700', fontSize: '0.95rem', marginBottom: '0.4rem' },
  popupRow: { fontSize: '0.8rem', color: '#374151', marginBottom: '0.2rem' },
  popupStatus: { marginTop: '0.5rem' },
}

export default WorkRequestMap
# Frontend — Road Work Coordination UI

React.js client for the Excavation Management System.

## Requirements

- Node.js 18+

## Setup

```bash
npm install
npm run dev
```

Runs at `http://localhost:5173`. Requires the backend running at `http://localhost:9090`.

## Structure

```
src/
├── api/           Axios calls to the backend
├── components/    Shared UI components
├── context/        Auth state (React Context)
└── pages/
    ├── auth/       Login, register
    ├── department/ Department dashboard, submit request
    ├── admin/      Admin dashboard, conflict resolution
    └── citizen/    Public map, complaint form
```

## Build for production

```bash
npm run build
```
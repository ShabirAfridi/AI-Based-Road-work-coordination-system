import {
  FiHome,
  FiFileText,
  FiAlertTriangle,
  FiBarChart2,
  FiSettings,
} from "react-icons/fi";

const menuItems = [
  {
    title: "Dashboard",
    icon: FiHome,
    active: true,
  },
  {
    title: "My Requests",
    icon: FiFileText,
  },
  {
    title: "Conflict Alerts",
    icon: FiAlertTriangle,
    badge: 3,
  },
  {
    title: "Reports",
    icon: FiBarChart2,
  },
];

export default function Sidebar() {
  return (
    <aside className="fixed left-0 top-0 flex h-screen w-[280px] flex-col border-r border-slate-200 bg-white shadow-sm">

    {/* Logo */}
    <div className="border-b border-slate-200 px-6 py-5">
      <div className="flex items-center gap-4">

        <div className="flex h-12 w-12 items-center justify-center rounded-xl bg-gradient-to-br from-blue-600 to-cyan-500 text-lg font-bold text-white shadow-lg">
          RC
        </div>

        <div>
          <h2 className="text-lg font-bold tracking-tight text-slate-900">
            CivicPath
          </h2>

          <p className="text-xs text-slate-500">
            Infrastructure Suite
          </p>
        </div>

      </div>
    </div>

      {/* Navigation */}

      <nav className="flex-1 px-5 py-6 space-y-2">

        {menuItems.map((item) => {

          const Icon = item.icon;

          return (

            <button
              key={item.title}
            className={`group flex w-full items-center justify-between rounded-xl px-4 py-3 text-sm font-medium transition-all duration-200 ${
              item.active
                ? "bg-blue-600 text-white shadow-md"
                : "text-slate-600 hover:bg-slate-100 hover:text-slate-900"
            }`}
            >

              <div className="flex items-center gap-3">

                <Icon size={20} />

                <span className="font-medium">
                  {item.title}
                </span>

              </div>

              {item.badge && (
               <span className="flex h-6 min-w-6 items-center justify-center rounded-full bg-red-500 px-2 text-xs font-bold text-white">
                  {item.badge}
                </span>
              )}

            </button>

          );

        })}

      </nav>

      {/* Bottom */}

      <div className="border-t border-slate-200 p-5">

        <button className="flex w-full items-center gap-3 rounded-xl px-4 py-3 text-slate-600 transition hover:bg-slate-100 hover:text-slate-900">

          <FiSettings size={20} />

          <span className="font-medium">
            Settings
          </span>

        </button>

      </div>

    </aside>
  );
}
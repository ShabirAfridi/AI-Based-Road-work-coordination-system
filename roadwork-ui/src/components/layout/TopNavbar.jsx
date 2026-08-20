import { FiBell, FiSearch, FiUser } from "react-icons/fi";

export default function TopNavbar() {
  return (
    <header className="sticky top-0 z-30 flex h-20 items-center justify-between border-b border-slate-200 bg-white px-8">

      {/* Left Side */}
      <div>
        <h1 className="text-2xl font-bold text-slate-900">
          Department Dashboard
        </h1>

        <p className="mt-1 text-sm text-slate-500">
          Home / Dashboard
        </p>
      </div>

      {/* Right Side */}
      <div className="flex items-center gap-5">

        {/* Search */}
        <div className="relative">
          <FiSearch
            className="absolute left-4 top-1/2 -translate-y-1/2 text-slate-400"
            size={18}
          />

          <input
            type="text"
            placeholder="Search requests..."
            className="h-11 w-72 rounded-full border border-slate-200 bg-slate-50 pl-11 pr-4 outline-none transition-all focus:border-blue-500 focus:bg-white"
          />
        </div>

        {/* Notification */}
        <button className="relative rounded-full p-2 hover:bg-slate-100 transition">
          <FiBell size={21} />

          <span className="absolute right-2 top-2 h-2.5 w-2.5 rounded-full bg-red-500"></span>
        </button>

        {/* Profile */}
        <div className="flex items-center gap-3">
          <div className="flex h-11 w-11 items-center justify-center rounded-full bg-blue-100">
            <FiUser className="text-blue-600" size={20} />
          </div>

          <div className="hidden lg:block">
            <p className="text-sm font-semibold text-slate-900">
              Department User
            </p>

            <p className="text-xs text-slate-500">
              Infrastructure Dept.
            </p>
          </div>
        </div>

      </div>

    </header>
  );
}
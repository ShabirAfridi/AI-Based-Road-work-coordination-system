import Sidebar from "./Sidebar";
import TopNavbar from "./TopNavbar";

export default function DashboardLayout({ children }) {
  return (
    <div className="min-h-screen bg-[#F8FAFC]">
      <Sidebar />
<div className="ml-[280px] flex min-h-screen flex-col"  >
        <TopNavbar />

        <main className="flex-1 bg-[#F8FAFC] px-8 py-8">
          {children}
        </main>
      </div>
    </div>
  );
}
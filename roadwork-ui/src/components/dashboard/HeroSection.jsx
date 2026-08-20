import { FiPlus } from "react-icons/fi";

export default function HeroSection({ onNewRequest }) {
  return (
    <section className="mb-8 flex items-center justify-between rounded-3xl bg-gradient-to-r from-blue-600 via-blue-500 to-cyan-500 p-8 text-white shadow-lg">

      <div>

        <p className="text-sm uppercase tracking-widest text-blue-100">
          Department Workspace
        </p>

        <h1 className="mt-2 text-4xl font-bold">
          Good Morning 👋
        </h1>

        <p className="mt-3 max-w-xl text-blue-100">
          Manage road construction requests, monitor conflicts and keep your
          department's projects moving efficiently.
        </p>

      </div>

      <button
        onClick={onNewRequest}
        className="flex items-center gap-2 rounded-xl bg-white px-6 py-3 font-semibold text-blue-600 shadow transition hover:scale-105"
      >
        <FiPlus size={20} />

        New Request
      </button>

    </section>
  );
}
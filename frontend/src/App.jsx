import { useEffect, useMemo, useState } from "react";
import { runsApi } from "./api.js";

const emptyForm = { name: "", targetUrl: "", framework: "PLAYWRIGHT" };

export default function App() {
  const [runs, setRuns] = useState([]);
  const [form, setForm] = useState(emptyForm);
  const [search, setSearch] = useState("");
  const [error, setError] = useState("");

  const load = async () => {
    try {
      setRuns(await runsApi.list());
      setError("");
    } catch (requestError) {
      setError(requestError.message);
    }
  };

  useEffect(() => { load(); }, []);

  const visibleRuns = useMemo(() => {
    const query = search.trim().toLowerCase();
    return runs.filter((run) =>
      !query ||
      run.name.toLowerCase().includes(query) ||
      run.framework.toLowerCase().includes(query) ||
      run.status.toLowerCase().includes(query)
    );
  }, [runs, search]);

  const createRun = async (event) => {
    event.preventDefault();
    try {
      const created = await runsApi.create(form);
      setRuns((current) => [created, ...current]);
      setForm(emptyForm);
      setError("");
    } catch (requestError) {
      setError(requestError.message);
    }
  };

  const setStatus = async (id, status) => {
    const updated = await runsApi.setStatus(id, status);
    setRuns((current) => current.map((run) => run.id === id ? updated : run));
  };

  const removeRun = async (id) => {
    await runsApi.remove(id);
    setRuns((current) => current.filter((run) => run.id !== id));
  };

  return (
    <main className="shell">
      <header className="hero">
        <div>
          <span className="eyebrow">AUTOMATION OPERATIONS</span>
          <h1>QualityBoard</h1>
          <p>Register test runs, review their state and keep automation work visible.</p>
        </div>
        <div className="metric">
          <strong>{runs.length}</strong>
          <span>registered runs</span>
        </div>
      </header>

      <section className="panel">
        <h2>Register a run</h2>
        <form className="form" onSubmit={createRun}>
          <input aria-label="Run name" placeholder="Run name" value={form.name}
                 onChange={(event) => setForm({ ...form, name: event.target.value })} required />
          <input aria-label="Target URL" placeholder="https://target.example" value={form.targetUrl}
                 onChange={(event) => setForm({ ...form, targetUrl: event.target.value })} required />
          <select aria-label="Framework" value={form.framework}
                  onChange={(event) => setForm({ ...form, framework: event.target.value })}>
            <option>PLAYWRIGHT</option>
            <option>SELENIUM</option>
            <option>APPIUM</option>
          </select>
          <button type="submit">Add run</button>
        </form>
        {error && <p className="error">{error}</p>}
      </section>

      <section className="panel">
        <div className="section-heading">
          <h2>Runs</h2>
          <input aria-label="Search runs" placeholder="Search by name, framework or status"
                 value={search} onChange={(event) => setSearch(event.target.value)} />
        </div>
        <div className="grid">
          {visibleRuns.map((run) => (
            <article className="card" key={run.id}>
              <div className="card-topline">
                <span>{run.framework}</span>
                <span className={"status status-" + run.status.toLowerCase()}>{run.status}</span>
              </div>
              <h3>{run.name}</h3>
              <a href={run.targetUrl} target="_blank" rel="noreferrer">{run.targetUrl}</a>
              <div className="actions">
                <button onClick={() => setStatus(run.id, "PASSED")}>Pass</button>
                <button onClick={() => setStatus(run.id, "FAILED")}>Fail</button>
                <button className="danger" onClick={() => removeRun(run.id)}>Delete</button>
              </div>
            </article>
          ))}
        </div>
      </section>
    </main>
  );
}

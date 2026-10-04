import { Navigate, NavLink, Route, Routes } from 'react-router-dom';
import AssetList from './pages/AssetList.jsx';
import EmployeeList from './pages/EmployeeList.jsx';

export default function App() {
  return (
      <>
        <header className="topbar">
          <span className="brand">Corporate Asset Manager</span>
          <nav>
            <NavLink to="/assets">Eszközök</NavLink>
            <NavLink to="/employees">Dolgozók</NavLink>
          </nav>
        </header>

        <main className="content">
          <Routes>
            <Route path="/" element={<Navigate to="/assets" replace />} />
            <Route path="/assets" element={<AssetList />} />
            <Route path="/employees" element={<EmployeeList />} />
          </Routes>
        </main>
      </>
  );
}
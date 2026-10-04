import { Navigate, NavLink, Route, Routes } from 'react-router-dom';
import AssetList from './pages/AssetList.jsx';
import EmployeeList from './pages/EmployeeList.jsx';
import AssetForm from './pages/AssetForm.jsx';
import EmployeeForm from './pages/EmployeeForm.jsx';

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
              <Route path="/assets/new" element={<AssetForm />} />
              <Route path="/assets/:serialNumber" element={<AssetForm />} />
            <Route path="/employees" element={<EmployeeList />} />
              <Route path="/employees/new" element={<EmployeeForm />} />
              <Route path="/employees/:employeeId" element={<EmployeeForm />} />
          </Routes>
        </main>
      </>
  );
}
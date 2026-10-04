import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { api } from '../api.js';

export default function EmployeeList() {
    const [employees, setEmployees] = useState([]);
    const [assetCounts, setAssetCounts] = useState({});
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        Promise.all([api.getEmployees(), api.getAssets()])
            .then(([employeeData, assetData]) => {
                setEmployees([...employeeData].sort((a, b) => a.name.localeCompare(b.name)));

                const counts = {};
                assetData.forEach((asset) => {
                    if (asset.assignedEmployeeId) {
                        counts[asset.assignedEmployeeId] = (counts[asset.assignedEmployeeId] ?? 0) + 1;
                    }
                });
                setAssetCounts(counts);
            })
            .catch((err) => setError(err.message))
            .finally(() => setLoading(false));
    }, []);

    function handleDelete(employee) {
        const count = assetCounts[employee.employeeId] ?? 0;
        const warning = count > 0
            ? `\nA nála lévő ${count} eszköz visszakerül a raktárba.`
            : '';
        if (!window.confirm(`Biztosan törlöd? ${employee.name} (${employee.employeeId})${warning}`)) return;

        api.deleteEmployee(employee.employeeId)
            .then(() => setEmployees((prev) => prev.filter((e) => e.employeeId !== employee.employeeId)))
            .catch((err) => alert(`A törlés nem sikerült: ${err.message}`));
    }

    if (loading) return <p>Betöltés...</p>;
    if (error) return <p className="error">Hiba történt: {error}</p>;

    return (
        <>
            <div className="page-header">
                <h1>Dolgozók ({employees.length})</h1>
                <Link to="/employees/new" className="btn primary">+ Új dolgozó</Link>
            </div>
            <table>
                <thead>
                <tr>
                    <th>Azonosító</th>
                    <th>Név</th>
                    <th>E-mail</th>
                    <th>Osztály</th>
                    <th>Eszközök</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                {employees.map((employee) => (
                    <tr key={employee.employeeId}>
                        <td className="mono">{employee.employeeId}</td>
                        <td>{employee.name}</td>
                        <td>{employee.email}</td>
                        <td>{employee.department}</td>
                        <td>{assetCounts[employee.employeeId] ?? 0}</td>
                        <td className="actions">
                            <Link to={`/employees/${employee.employeeId}`} className="btn small">Szerkesztés</Link>
                            <button type="button" className="btn small danger" onClick={() => handleDelete(employee)}>
                                Törlés
                            </button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </>
    );
}
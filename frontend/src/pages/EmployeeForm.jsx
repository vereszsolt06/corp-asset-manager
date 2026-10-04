import { useEffect, useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { api } from '../api.js';
import { ASSET_STATUSES, ASSET_TYPES } from '../labels.js';

const EMPTY_EMPLOYEE = {
    name: '',
    email: '',
    department: '',
};

export default function EmployeeForm() {
    const { employeeId } = useParams();
    const isEdit = employeeId !== undefined;
    const navigate = useNavigate();

    const [employee, setEmployee] = useState(EMPTY_EMPLOYEE);
    const [heldAssets, setHeldAssets] = useState([]);
    const [loading, setLoading] = useState(isEdit);
    const [saving, setSaving] = useState(false);
    const [error, setError] = useState(null);

    useEffect(() => {
        if (!isEdit) return;

        Promise.all([api.getEmployee(employeeId), api.getAssets()])
            .then(([employeeData, assetData]) => {
                setEmployee(employeeData);
                setHeldAssets(assetData.filter((a) => a.assignedEmployeeId === employeeId));
            })
            .catch((err) => setError(err.message))
            .finally(() => setLoading(false));
    }, [isEdit, employeeId]);

    function handleChange(event) {
        const { name, value } = event.target;
        setEmployee((prev) => ({ ...prev, [name]: value }));
    }

    function handleSubmit(event) {
        event.preventDefault();
        setSaving(true);
        setError(null);

        const save = isEdit ? api.updateEmployee(employee) : api.createEmployee(employee);

        save
            .then(() => navigate('/employees'))
            .catch((err) => {
                setError(err.message);
                setSaving(false);
            });
    }

    if (loading) return <p>Betöltés...</p>;

    return (
        <>
            <h1>{isEdit ? `Dolgozó szerkesztése: ${employeeId}` : 'Új dolgozó'}</h1>
            {error && <p className="error">Hiba történt: {error}</p>}

            <form className="form" onSubmit={handleSubmit}>
                <label>
                    Név
                    <input name="name" value={employee.name ?? ''} onChange={handleChange} required />
                </label>

                <label>
                    E-mail
                    <input type="email" name="email" value={employee.email ?? ''} onChange={handleChange} required />
                </label>

                <label>
                    Osztály
                    <input name="department" value={employee.department ?? ''} onChange={handleChange} required />
                </label>

                <div className="form-actions">
                    <button type="submit" className="btn primary" disabled={saving}>
                        {saving ? 'Mentés...' : 'Mentés'}
                    </button>
                    <Link to="/employees" className="btn">Mégse</Link>
                </div>
            </form>

            {isEdit && (
                <section className="related">
                    <h2>Nála lévő eszközök ({heldAssets.length})</h2>
                    {heldAssets.length === 0 ? (
                        <p className="muted">Jelenleg nincs nála eszköz.</p>
                    ) : (
                        <table>
                            <thead>
                            <tr>
                                <th>Sorozatszám</th>
                                <th>Megnevezés</th>
                                <th>Típus</th>
                                <th>Státusz</th>
                            </tr>
                            </thead>
                            <tbody>
                            {heldAssets.map((asset) => (
                                <tr key={asset.serialNumber}>
                                    <td className="mono">
                                        <Link to={`/assets/${asset.serialNumber}`}>{asset.serialNumber}</Link>
                                    </td>
                                    <td>{asset.name}</td>
                                    <td>{ASSET_TYPES[asset.type] ?? asset.type}</td>
                                    <td>
                                        <span className={`badge ${asset.status}`}>
                                            {ASSET_STATUSES[asset.status] ?? asset.status}
                                        </span>
                                    </td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    )}
                </section>
            )}
        </>
    );
}
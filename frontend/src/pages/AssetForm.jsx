import { useEffect, useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { api } from '../api.js';
import { ASSET_STATUSES, ASSET_TYPES } from '../labels.js';

const EMPTY_ASSET = {
    name: '',
    type: 'LAPTOP',
    status: 'IN_STOCK',
    assignedEmployeeId: '',
};

export default function AssetForm() {
    const { serialNumber } = useParams();
    const isEdit = serialNumber !== undefined;
    const navigate = useNavigate();

    const [asset, setAsset] = useState(EMPTY_ASSET);
    const [employees, setEmployees] = useState([]);
    const [loading, setLoading] = useState(true);
    const [saving, setSaving] = useState(false);
    const [error, setError] = useState(null);

    useEffect(() => {
        Promise.all([
            api.getEmployees(),
            isEdit ? api.getAsset(serialNumber) : Promise.resolve(EMPTY_ASSET),
        ])
            .then(([employeeData, assetData]) => {
                setEmployees([...employeeData].sort((a, b) => a.name.localeCompare(b.name)));
                setAsset({ ...assetData, assignedEmployeeId: assetData.assignedEmployeeId ?? '' });
            })
            .catch((err) => setError(err.message))
            .finally(() => setLoading(false));
    }, [isEdit, serialNumber]);

    function handleChange(event) {
        const { name, value } = event.target;
        setAsset((prev) => ({ ...prev, [name]: value }));
    }

    function handleSubmit(event) {
        event.preventDefault();
        setSaving(true);
        setError(null);

        const payload = { ...asset, assignedEmployeeId: asset.assignedEmployeeId || null };
        const save = isEdit ? api.updateAsset(payload) : api.createAsset(payload);

        save
            .then(() => navigate('/assets'))
            .catch((err) => {
                setError(err.message);
                setSaving(false);
            });
    }

    if (loading) return <p>Betöltés...</p>;

    return (
        <>
            <h1>{isEdit ? `Eszköz szerkesztése: ${serialNumber}` : 'Új eszköz'}</h1>
            {error && <p className="error">Hiba történt: {error}</p>}

            <form className="form" onSubmit={handleSubmit}>
                <label>
                    Megnevezés
                    <input name="name" value={asset.name ?? ''} onChange={handleChange} required />
                </label>

                <label>
                    Típus
                    <select name="type" value={asset.type} onChange={handleChange}>
                        {Object.entries(ASSET_TYPES).map(([value, label]) => (
                            <option key={value} value={value}>{label}</option>
                        ))}
                    </select>
                </label>

                <label>
                    Státusz
                    <select name="status" value={asset.status} onChange={handleChange}>
                        {Object.entries(ASSET_STATUSES).map(([value, label]) => (
                            <option key={value} value={value}>{label}</option>
                        ))}
                    </select>
                </label>

                <label>
                    Kinél van
                    <select name="assignedEmployeeId" value={asset.assignedEmployeeId} onChange={handleChange}>
                        <option value="">— senkinél —</option>
                        {employees.map((e) => (
                            <option key={e.employeeId} value={e.employeeId}>
                                {e.name} ({e.employeeId})
                            </option>
                        ))}
                    </select>
                </label>

                <div className="form-actions">
                    <button type="submit" className="btn primary" disabled={saving}>
                        {saving ? 'Mentés...' : 'Mentés'}
                    </button>
                    <Link to="/assets" className="btn">Mégse</Link>
                </div>
            </form>
        </>
    );
}
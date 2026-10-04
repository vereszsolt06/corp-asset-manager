import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { api } from '../api.js';
import { ASSET_STATUSES, ASSET_TYPES } from '../labels.js';

export default function AssetList() {
    const [assets, setAssets] = useState([]);
    const [employeeNames, setEmployeeNames] = useState({});
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        Promise.all([api.getAssets(), api.getEmployees()])
            .then(([assetData, employeeData]) => {
                setAssets(assetData);
                setEmployeeNames(
                    Object.fromEntries(employeeData.map((e) => [e.employeeId, e.name]))
                );
            })
            .catch((err) => setError(err.message))
            .finally(() => setLoading(false));
    }, []);

    function handleDelete(asset) {
        if (!window.confirm(`Biztosan törlöd? ${asset.name} (${asset.serialNumber})`)) return;

        api.deleteAsset(asset.serialNumber)
            .then(() => setAssets((prev) => prev.filter((a) => a.serialNumber !== asset.serialNumber)))
            .catch((err) => alert(`A törlés nem sikerült: ${err.message}`));
    }

    if (loading) return <p>Betöltés...</p>;
    if (error) return <p className="error">Hiba történt: {error}</p>;

    return (
        <>
            <div className="page-header">
                <h1>Eszközök ({assets.length})</h1>
                <Link to="/assets/new" className="btn primary">+ Új eszköz</Link>
            </div>
            <table>
                <thead>
                <tr>
                    <th>Sorozatszám</th>
                    <th>Megnevezés</th>
                    <th>Típus</th>
                    <th>Státusz</th>
                    <th>Kinél van</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                {assets.map((asset) => (
                    <tr key={asset.serialNumber}>
                        <td className="mono">{asset.serialNumber}</td>
                        <td>{asset.name}</td>
                        <td>{ASSET_TYPES[asset.type] ?? asset.type}</td>
                        <td>
                <span className={`badge ${asset.status}`}>
                  {ASSET_STATUSES[asset.status] ?? asset.status}
                </span>
                        </td>
                        <td>
                            {asset.assignedEmployeeId
                                ? `${employeeNames[asset.assignedEmployeeId] ?? 'Ismeretlen'} (${asset.assignedEmployeeId})`
                                : '—'}
                        </td>
                        <td className="actions">
                            <Link to={`/assets/${asset.serialNumber}`} className="btn small">Szerkesztés</Link>
                            <button type="button" className="btn small danger" onClick={() => handleDelete(asset)}>
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
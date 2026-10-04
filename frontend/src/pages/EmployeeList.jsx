import { useEffect, useState } from 'react';
import { api } from '../api.js';

export default function EmployeeList() {
    const [employees, setEmployees] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        api.getEmployees()
            .then((data) => setEmployees([...data].sort((a, b) => a.name.localeCompare(b.name))))
            .catch((err) => setError(err.message))
            .finally(() => setLoading(false));
    }, []);

    if (loading) return <p>Betöltés...</p>;
    if (error) return <p className="error">Hiba történt: {error}</p>;

    return (
        <>
            <h1>Dolgozók ({employees.length})</h1>
            <table>
                <thead>
                <tr>
                    <th>Azonosító</th>
                    <th>Név</th>
                    <th>E-mail</th>
                    <th>Osztály</th>
                </tr>
                </thead>
                <tbody>
                {employees.map((employee) => (
                    <tr key={employee.employeeId}>
                        <td className="mono">{employee.employeeId}</td>
                        <td>{employee.name}</td>
                        <td>{employee.email}</td>
                        <td>{employee.department}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </>
    );
}
const BASE_URL = '/api';

async function request(path, options = {}) {
    const response = await fetch(BASE_URL + path, {
        headers: { 'Content-Type': 'application/json' },
        ...options,
    });

    if (!response.ok) {
        let message = `${response.status} ${response.statusText}`;
        try {
            const body = await response.json();
            if (body.message) message = body.message;
        } catch {
            ///
        }
        throw new Error(message);
    }

    const text = await response.text();
    return text ? JSON.parse(text) : null;
}

export const api = {
    getAssets: () => request('/asset'),
    getAsset: (serialNumber) => request(`/asset/${serialNumber}`),
    createAsset: (asset) => request('/asset', { method: 'POST', body: JSON.stringify(asset) }),
    updateAsset: (asset) => request('/asset', { method: 'PUT', body: JSON.stringify(asset) }),
    deleteAsset: (serialNumber) => request(`/asset/${serialNumber}`, { method: 'DELETE' }),

    getEmployees: () => request('/employee'),
    getEmployee: (employeeId) => request(`/employee/${employeeId}`),
    createEmployee: (employee) => request('/employee', { method: 'POST', body: JSON.stringify(employee) }),
    updateEmployee: (employee) => request('/employee', { method: 'PUT', body: JSON.stringify(employee) }),
    deleteEmployee: (employeeId) => request(`/employee/${employeeId}`, { method: 'DELETE' }),
};
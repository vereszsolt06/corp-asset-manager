const BASE_URL = '/api';
const AUTH_KEY = 'auth';
const USER_KEY = 'user';

function logout() {
    sessionStorage.removeItem(AUTH_KEY);
    sessionStorage.removeItem(USER_KEY);
}

async function request(path, options = {}) {
    const headers = { 'Content-Type': 'application/json' };
    const auth = sessionStorage.getItem(AUTH_KEY);
    if (auth) headers.Authorization = auth;

    const response = await fetch(BASE_URL + path, { ...options, headers });

    if (response.status === 401) {
        logout();
        window.location.reload();
        throw new Error('A bejelentkezés lejárt vagy érvénytelen.');
    }

    if (!response.ok) {
        let message = `${response.status} ${response.statusText}`;
        const body = await response.json().catch(() => null);
        if (body?.message) message = body.message;
        throw new Error(message);
    }

    const text = await response.text();
    return text ? JSON.parse(text) : null;
}

async function login(username, password) {
    const auth = 'Basic ' + btoa(`${username}:${password}`);
    const response = await fetch(`${BASE_URL}/auth/me`, { headers: { Authorization: auth } });

    if (response.status === 401) throw new Error('Hibás felhasználónév vagy jelszó.');
    if (!response.ok) throw new Error(`${response.status} ${response.statusText}`);

    const user = await response.json();
    sessionStorage.setItem(AUTH_KEY, auth);
    sessionStorage.setItem(USER_KEY, JSON.stringify(user));
    return user;
}

function getCurrentUser() {
    const stored = sessionStorage.getItem(USER_KEY);
    return stored ? JSON.parse(stored) : null;
}

export const api = {
    login,
    logout,
    getCurrentUser,

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
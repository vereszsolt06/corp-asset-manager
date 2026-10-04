const BASE_URL = '/api';

async function request(path, options = {}) {
    const response = await fetch(BASE_URL + path, {
        headers: { 'Content-Type': 'application/json' },
        ...options,
    });

    if (!response.ok) {
        throw new Error(`${response.status} ${response.statusText}`);
    }

    const text = await response.text();
    return text ? JSON.parse(text) : null;
}

export const api = {
    getAssets: () => request('/asset'),
    getEmployees: () => request('/employee'),
};
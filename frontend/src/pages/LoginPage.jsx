import { useState } from 'react';
import { api } from '../api.js';

export default function LoginPage({ onLogin }) {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    function handleSubmit(event) {
        event.preventDefault();
        setLoading(true);
        setError(null);

        api.login(username, password)
            .then((user) => onLogin(user))
            .catch((err) => {
                setError(err.message);
                setLoading(false);
            });
    }

    return (
        <div className="login-page">
            <form className="form login-card" onSubmit={handleSubmit}>
                <h1>Corporate Asset Manager</h1>
                <p className="muted">Jelentkezz be a folytatáshoz.</p>
                {error && <p className="error">{error}</p>}

                <label>
                    Felhasználónév
                    <input
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        autoComplete="username"
                        autoFocus
                        required
                    />
                </label>

                <label>
                    Jelszó
                    <input
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        autoComplete="current-password"
                        required
                    />
                </label>

                <button type="submit" className="btn primary" disabled={loading}>
                    {loading ? 'Belépés...' : 'Belépés'}
                </button>
            </form>
        </div>
    );
}
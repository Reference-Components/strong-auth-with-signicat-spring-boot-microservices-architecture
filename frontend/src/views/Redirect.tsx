import { useContext } from 'react'
import { useNavigate } from 'react-router-dom'
import { AuthContext } from '../context/AuthContextProvider'
import useAuthorization from '../hooks/useAuthorization'

const Redirect = () => {
    const authContext = useContext(AuthContext)
    const [fetching, error] = useAuthorization()
    const navigate = useNavigate()

    if (fetching) {
        return <div>Fetching...</div>
    }

    if (!fetching || !error) {
        return (
            <div>
                <h1>Exhanged code for token & identity</h1>
                <p>Raw identity data:</p>
                <pre>{authContext.userData?.identityRawData}</pre>

                <small>
                    <p>Id token: {authContext.userData?.idToken}</p>
                </small>

                <button onClick={() => navigate('/')}>Go home</button>
            </div>
        )
    }

    return (
        <div>
            <p>Authorization failed: {error}</p>
            <button onClick={() => navigate('/')}>Go home</button>
        </div>
    )
}

export default Redirect

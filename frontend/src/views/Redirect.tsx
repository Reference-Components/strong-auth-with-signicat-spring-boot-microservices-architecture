import { useContext, useMemo } from 'react'
import { useNavigate } from 'react-router-dom'
import { AuthContext } from '../context/AuthContextProvider'
import useAuthorization from '../hooks/useAuthorization'

const Redirect = () => {
    const authContext = useContext(AuthContext)
    const [fetching, error] = useAuthorization()
    const navigate = useNavigate()

    const expiryTimeStr = useMemo(() => {
        if (authContext.userData) {
            return new Date(authContext.userData?.exp).toString()
        }
    }, [authContext.userData])

    if (fetching) {
        return <div>Fetching...</div>
    }

    if (authContext.userData) {
        return (
            <div>
                <h1>Exhanged code for token & identity</h1>
                <p>Raw identity data:</p>
                <pre>{authContext.userData.identityRawData}</pre>

                <small>
                    <p>Id token: {authContext.userData.idToken}</p>
                    <p>Expires: {expiryTimeStr}</p>
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

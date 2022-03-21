import { useContext } from 'react'
import { AuthContext } from '../context/AuthContextProvider'
import useAuthEndpoint from '../hooks/useAuthEndpoint'

const Home = (): JSX.Element => {
    const authContext = useContext(AuthContext)
    const authUrl = useAuthEndpoint()

    if (!authContext.isAuthenticated) {
        return (
            <div>
                <p>You are not logged in</p>
                <button onClick={() => window.location.replace(authUrl)}>Log in</button>
            </div>
        )
    }

    return (
        <div>
            <p>You are logged in as: {authContext.userData?.name}</p>
            <p>Raw identity data:</p>
            <pre>{authContext.userData?.identityRawData}</pre>
        </div>
    )
}

export default Home

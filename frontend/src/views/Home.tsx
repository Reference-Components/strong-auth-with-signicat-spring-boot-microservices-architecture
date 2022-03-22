import { useContext } from 'react'
import { AuthContext } from '../context/AuthContextProvider'
import useAuthEndpoint from '../hooks/useAuthEndpoint'

const Home = (): JSX.Element => {
    const authContext = useContext(AuthContext)
    const [authEndpoint, error] = useAuthEndpoint()

    if (error) {
        return <div>Failed to retrieve authorization endpoint: {error}</div>
    }

    if (!authContext.isAuthenticated) {
        return (
            <div>
                <p>You are not logged in</p>
                <button onClick={() => window.location.replace(authEndpoint || '/')}>Log in</button>
            </div>
        )
    }

    return (
        <div>
            <p>You are logged in as: {authContext.userData?.name}</p>
        </div>
    )
}

export default Home

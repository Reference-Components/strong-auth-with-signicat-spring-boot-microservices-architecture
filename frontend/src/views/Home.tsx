import { useContext } from 'react'
import { Link } from 'react-router-dom'
import { AuthContext } from '../context/AuthContextProvider'
import useAuthEndpoint from '../hooks/useAuthEndpoint'
import useLogoutStatus from '../hooks/useLogoutStatus'

const Home = (): JSX.Element => {
    const authContext = useContext(AuthContext)
    const [authEndpoint, authEndpointError] = useAuthEndpoint()
    const isLoggedOut = useLogoutStatus()

    if (authEndpointError) {
        return (
            <div>
                <h1>Home</h1>
                <p>Failed to retrieve authorization endpoint: {authEndpointError}</p>
            </div>
        )
    }

    if (authContext.isAuthenticated) {
        return (
            <div>
                <h1>Home</h1>
                <p>You are logged in as: {authContext.userData?.name}</p>
                <Link to={'/protected'}>Protected view</Link>
            </div>
        )
    }

    return (
        <div>
            <h1>Home</h1>
            {isLoggedOut && <p>You have been logged out</p>}
            <a href={authEndpoint}>Log in</a>
        </div>
    )
}

export default Home

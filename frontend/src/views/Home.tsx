import { useContext } from 'react'
import { AuthContext } from '../context/AuthContextProvider'
import useAuthEndpoint from '../hooks/useAuthEndpoint'
import { getInfoHelloMessage } from '../services/infoService'

const Home = (): JSX.Element => {
    const authContext = useContext(AuthContext)
    const [authEndpoint, error] = useAuthEndpoint()

    const getInfoMessage = async () => {
        const message = await getInfoHelloMessage()
        console.log('Home message', message)
    }

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
            <button onClick={getInfoMessage}>Get message from info service</button>
        </div>
    )
}

export default Home

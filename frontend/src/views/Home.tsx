import { useContext } from 'react'
import { AuthContext } from '../context/AuthContextProvider'
import * as authservice from '../services/authService'

const Home = (): JSX.Element => {
    const authContext = useContext(AuthContext)

    const login = async () => {
        const authUrl = await authservice.getAuthUrl()
        if (authUrl) {
            const { endpointUrl } = authUrl
            window.location.replace(endpointUrl)
        }
    }

    if (!authContext.isAuthenticated) {
        return (
            <div>
                Not logged in
                <button onClick={login}>Log in</button>
            </div>
        )
    }

    return <div>logged in</div>
}

export default Home

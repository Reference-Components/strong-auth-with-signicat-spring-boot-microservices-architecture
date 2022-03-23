import { useContext } from 'react'
import { Link } from 'react-router-dom'
import Greeting from '../components/Greeting'
import { AuthContext } from '../context/AuthContextProvider'
import useLogoutStatus from '../hooks/useLogoutStatus'

const Home = (): JSX.Element => {
    const authContext = useContext(AuthContext)
    const isLoggedOut = useLogoutStatus()

    if (authContext.isAuthenticated) {
        return (
            <div>
                <h1>Home</h1>
                <Greeting />
                <Link to={'/protected'}>Protected view</Link>
            </div>
        )
    }

    return (
        <div>
            <h1>Home</h1>
            {isLoggedOut && <p>You have been logged out</p>}
            <Link to={'/login'}>Log in</Link>
        </div>
    )
}

export default Home

import { useContext } from 'react'
import { Link, Navigate } from 'react-router-dom'
import { AuthContext } from '../context/AuthContextProvider'
import useAuthorization from '../hooks/useAuthorization'

const Redirect = () => {
    const authContext = useContext(AuthContext)
    const [fetching, error] = useAuthorization()

    if (fetching) {
        return <div>Fetching...</div>
    }

    if (error) {
        return (
            <div>
                <p>Authorization failed: {error}</p>
                <Link to={'/'}>Go home</Link>
            </div>
        )
    }

    if (authContext.isAuthenticated && !fetching) {
        return <Navigate to="/protected" />
    }

    return <></>
}

export default Redirect

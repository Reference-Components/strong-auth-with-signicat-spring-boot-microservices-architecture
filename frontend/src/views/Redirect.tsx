import { useContext } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { AuthContext } from '../context/AuthContextProvider'
import useAuthorization from '../hooks/useAuthorization'

const Redirect = () => {
    const authContext = useContext(AuthContext)
    const [fetching, error] = useAuthorization()
    const navigate = useNavigate()

    if (fetching) {
        return <div>Fetching...</div>
    }

    if (authContext.isAuthenticated) {
        navigate('/protected')
    }

    return (
        <div>
            <p>Authorization failed: {error}</p>
            <Link to={'/'}>Go home</Link>
        </div>
    )
}

export default Redirect

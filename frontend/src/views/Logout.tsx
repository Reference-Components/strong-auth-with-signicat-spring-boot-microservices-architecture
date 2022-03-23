import { Navigate } from 'react-router-dom'
import useLogout from '../hooks/useLogout'

const Logout = () => {
    const isLoggedOut = useLogout()

    if (isLoggedOut) {
        return <Navigate to="/?logout=true" />
    }

    return (
        <div>
            <p>Logging out...</p>
        </div>
    )
}

export default Logout

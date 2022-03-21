import { useContext } from 'react'
import { useNavigate } from 'react-router-dom'
import { AuthContext } from '../context/AuthContextProvider'
import useUserData from '../hooks/useUserData'

const Redirect = () => {
    const authContext = useContext(AuthContext)
    const [fetching, error] = useUserData()
    const navigate = useNavigate()

    if (fetching || !error) {
        return (
            <div>
                <p>Exhanged code for token: {authContext.userData?.idToken}</p>
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

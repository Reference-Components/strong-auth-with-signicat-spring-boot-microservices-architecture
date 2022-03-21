import { useContext, useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import { AuthContext } from '../context/AuthContextProvider'
import useUserData from '../hooks/useUserData'

const Redirect = () => {
    const authContext = useContext(AuthContext)
    const userData = useUserData()
    const navigate = useNavigate()

    useEffect(() => {
        if (!authContext.isAuthenticated && userData.idToken) {
            authContext.setUserData({
                name: userData.name,
                idToken: userData.idToken,
                identityRawData: userData.identityRawData,
            })
            authContext.setAuthenticated(true)
        }
    }, [userData, authContext])

    return (
        <div>
            <p>Exhanged code for token: {userData.idToken}</p>
            <button onClick={() => navigate('/')}>Go home</button>
        </div>
    )
}

export default Redirect

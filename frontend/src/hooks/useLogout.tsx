import { useContext, useEffect, useState } from 'react'
import { AuthContext } from '../context/AuthContextProvider'
import TokenManager from '../utils/TokenManager'

const useLogout = () => {
    const authContext = useContext(AuthContext)
    const tokenManager = TokenManager.getInstance()
    const [success, setSuccess] = useState<boolean>(false)

    useEffect(() => {
        tokenManager.setIdToken(undefined)
        tokenManager.setExp(undefined)
        authContext.setUserData(undefined)
        authContext.setAuthenticated(false)
        setSuccess(true)
    }, [authContext, tokenManager])

    return success
}

export default useLogout

import { useCallback, useEffect, useState } from 'react'
import { getAuthUrl } from '../services/authService'

const useAuthEndpoint = () => {
    const [authEndpoint, setAuthEndpoint] = useState<string>('')

    const getAuthEndPoint = useCallback(async () => {
        const authEndpointData = await getAuthUrl()
        if (authEndpointData) {
            const { endpointUrl } = authEndpointData
            setAuthEndpoint(endpointUrl)
        } else {
            setAuthEndpoint('/')
        }
    }, [])

    useEffect(() => {
        if (!authEndpoint) {
            getAuthEndPoint()
        }
    }, [authEndpoint, getAuthEndPoint])

    return authEndpoint
}

export default useAuthEndpoint

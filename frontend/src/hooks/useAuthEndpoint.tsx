import { useCallback, useEffect, useState } from 'react'
import { getAuthUrl } from '../services/authService'

type ReturnType = [authEndpoint: string | undefined, error: string | undefined, fetching: boolean]

const useAuthEndpoint = (): ReturnType => {
    const [authEndpoint, setAuthEndpoint] = useState<string>('')
    const [error, setError] = useState<string>()
    const [fetching, setFetching] = useState<boolean>(true)

    const getAuthEndPoint = useCallback(async () => {
        try {
            const authEndpointData = await getAuthUrl()
            const { endpointUrl } = authEndpointData
            setFetching(false)
            setAuthEndpoint(endpointUrl)
            setError(undefined)
        } catch (err: unknown) {
            console.error(err)
            const e = err as Error
            setFetching(false)
            setError(e.message)
            setAuthEndpoint('')
        }
    }, [])

    useEffect(() => {
        if (!authEndpoint && fetching) {
            getAuthEndPoint()
        }
    }, [authEndpoint, fetching, getAuthEndPoint])

    return [authEndpoint, error, fetching]
}

export default useAuthEndpoint

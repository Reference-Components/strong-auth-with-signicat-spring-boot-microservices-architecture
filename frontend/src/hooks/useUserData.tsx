import { useCallback, useContext, useEffect, useState } from 'react'
import { exhangeCodeForUserData } from '../services/authService'
import { useSearchParams } from 'react-router-dom'
import { AuthContext } from '../context/AuthContextProvider'

const useUserData = () => {
    const authContext = useContext(AuthContext)
    const [searchParams] = useSearchParams()
    const [fetching, setFetching] = useState<boolean>(true)
    const [initialized, setInitialized] = useState<boolean>(false)
    const [error, setError] = useState<string>()

    const updateUserData = useCallback(
        async (code: string | null, state: string | null) => {
            try {
                const data = await exhangeCodeForUserData(code, state)
                if (data.idToken && data.name && data.identityRawData) {
                    authContext.setUserData({
                        name: data.name,
                        idToken: data.idToken,
                        identityRawData: data.identityRawData,
                    })
                    authContext.setAuthenticated(true)
                    setFetching(false)
                }
            } catch (err: unknown) {
                console.error(err)
                const e = err as Error
                setFetching(false)
                setError(e.message)
            }
        },
        [authContext],
    )

    useEffect(() => {
        if (!initialized) {
            const code = searchParams.get('code')
            const state = searchParams.get('state')
            setInitialized(true)
            updateUserData(code, state)
        }
    }, [searchParams, updateUserData, authContext, error, fetching, initialized])

    return [fetching, error, setInitialized]
}

export default useUserData

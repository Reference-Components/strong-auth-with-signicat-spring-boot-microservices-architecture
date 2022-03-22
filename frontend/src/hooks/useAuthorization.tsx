import { useCallback, useContext, useEffect, useState } from 'react'
import { exhangeCodeForUserData } from '../services/authService'
import { useSearchParams } from 'react-router-dom'
import { AuthContext } from '../context/AuthContextProvider'

type ReturnType = [fetching: boolean, error: string | undefined, reset: () => void]

const useAuthorization = (): ReturnType => {
    const authContext = useContext(AuthContext)
    const [searchParams] = useSearchParams()
    const [fetching, setFetching] = useState<boolean>(true)
    const [initialized, setInitialized] = useState<boolean>(false)
    const [error, setError] = useState<string>()

    const reset = () => {
        setInitialized(false)
        setFetching(true)
    }

    const getUserData = useCallback(
        async (code: string | null, state: string | null) => {
            try {
                const data = await exhangeCodeForUserData(code, state)
                if (data.idToken && data.name && data.identityRawData) {
                    setFetching(false)
                    authContext.setUserData({
                        name: data.name,
                        idToken: data.idToken,
                        identityRawData: data.identityRawData,
                    })
                    authContext.setAuthenticated(true)
                    setError(undefined)
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
            getUserData(code, state)
        }
    }, [searchParams, getUserData, authContext, error, fetching, initialized])

    return [fetching, error, reset]
}

export default useAuthorization

import { useCallback, useEffect, useState } from 'react'
import { exhangeCodeForToken } from '../services/authService'
import { useSearchParams } from 'react-router-dom'

const useAuthToken = () => {
    const [searchParams] = useSearchParams()

    const [token, setToken] = useState({})

    const fetchToken = useCallback(async (code: string | null, state: string | null) => {
        const data = await exhangeCodeForToken(code, state)
        console.log('executed')
        console.log(data)
    }, [])

    useEffect(() => {
        const code = searchParams.get('code')
        const state = searchParams.get('state')
        fetchToken(code, state)
    }, [searchParams, fetchToken])
}

export default useAuthToken

import { useCallback, useEffect, useState } from 'react'
import { exhangeCodeForUserData } from '../services/authService'
import { useSearchParams } from 'react-router-dom'
import { UserData } from '../types'

const useUserData = () => {
    const [searchParams] = useSearchParams()
    const [userData, setUserData] = useState<UserData>({})

    const fetchUserData = useCallback(async (code: string | null, state: string | null) => {
        const data = await exhangeCodeForUserData(code, state)
        if (data.idToken && data.name && data.identityRawData) {
            setUserData({
                name: data.name,
                idToken: data.idToken,
                identityRawData: data.identityRawData,
            })
        }
    }, [])

    useEffect(() => {
        if (!userData.idToken) {
            const code = searchParams.get('code')
            const state = searchParams.get('state')
            fetchUserData(code, state)
        }
    }, [userData, searchParams, fetchUserData])

    return userData
}

export default useUserData

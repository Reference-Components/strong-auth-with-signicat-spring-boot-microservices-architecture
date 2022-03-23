import { useEffect, useState } from 'react'
import { useSearchParams } from 'react-router-dom'

const useLogoutStatus = () => {
    const [searchParams] = useSearchParams()
    const [isLoggedOut, setIsLoggedOut] = useState<boolean>(false)

    useEffect(() => {
        if (searchParams.get('logout')) {
            setIsLoggedOut(true)
        }
    }, [searchParams])

    return isLoggedOut
}

export default useLogoutStatus

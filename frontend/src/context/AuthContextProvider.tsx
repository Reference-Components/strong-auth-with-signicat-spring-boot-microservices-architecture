import { createContext, useEffect, useState } from 'react'
import { UserData } from '../types'

interface AuthContextValues {
    isAuthenticated: boolean
    userData?: UserData
    setAuthenticated: (a: boolean) => void
    setUserData: (data: UserData) => void
}

const defaultAuthContextValues: AuthContextValues = {
    isAuthenticated: false,
    userData: undefined,
    setAuthenticated: () => console.warn('setAuthenticated not implemented'),
    setUserData: () => console.warn('setUserData not implemented'),
}

export const AuthContext = createContext<AuthContextValues>(defaultAuthContextValues)

interface AuthContextProviderProps {
    children: JSX.Element
}

const AuthContextProvider = (props: AuthContextProviderProps) => {
    const [isAuthenticated, setAuthenticated] = useState<boolean>(false)
    const [userData, setUserData] = useState<UserData>({})

    useEffect(() => {
        if (!isAuthenticated) {
            console.log('user is not yet authenticated.')
        } else {
            console.log('user is authenticated')
        }
    }, [isAuthenticated])

    return (
        <AuthContext.Provider value={{ isAuthenticated, setAuthenticated, userData, setUserData }}>
            {props.children}
        </AuthContext.Provider>
    )
}

export default AuthContextProvider

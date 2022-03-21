import { apiBaseUrl } from '../config/config'
import { AuthUrlResponse, UserDataResponse } from '../types'

type ErrorBody = {
    status: string
    message: string
}

export const getAuthUrl = async (): Promise<AuthUrlResponse> => {
    try {
        const response = await fetch(`${apiBaseUrl}/identity/authorize`)
        if (response.status !== 200) {
            return handleErrors(response)
        }
        storeSessionIfPresent(response)
        return await response.json()
    } catch (error) {
        console.error(error)
        return Promise.reject(error)
    }
}

export const exhangeCodeForUserData = async (code: string | null, state: string | null): Promise<UserDataResponse> => {
    try {
        const session = retrieveSessionAndFlush()
        const response = await fetch(`${apiBaseUrl}/identity/token/?code=${code}&state=${state}`, {
            headers: {
                'X-Auth-Token': session,
            },
        })

        if (response.status !== 200) {
            return handleErrors(response)
        }

        return await response.json()
    } catch (error) {
        console.error(error)
        return Promise.reject(error)
    }
}

const handleErrors = async (response: Response) => {
    console.error(response)
    const body = (await response.json()) as ErrorBody
    return Promise.reject(new Error(`${response.statusText} - ${body.message}`))
}

const storeSessionIfPresent = (response: Response) => {
    const session = response.headers.get('x-auth-token')
    if (session) {
        localStorage.setItem('session', session)
    }
}

const retrieveSessionAndFlush = (): string => {
    const session = localStorage.getItem('session')
    if (session) {
        localStorage.removeItem('session')
    }
    return session || ''
}

import { ApiErrorBody } from '../types'
import TokenManager from './TokenManager'

const tokenManager = TokenManager.getInstance()

export const handleErrors = async (response: Response) => {
    console.error(response)
    const body = (await response.json()) as ApiErrorBody
    return Promise.reject(new Error(`${response.statusText} - ${body.message}`))
}

export const getRequestOptions = (): RequestInit => {
    const authHeader = getAuthorizationHeader()
    const options = {
        method: 'GET',
        headers: {
            ...authHeader,
            Accept: 'application/json',
            'Content-Type': 'application/json',
        },
    }
    return options
}

const getAuthorizationHeader = () => {
    if (tokenManager.getIdToken()) {
        return { Authorization: `Bearer ${tokenManager.getIdToken()}` }
    }
}

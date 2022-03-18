import { apiBaseUrl } from '../config/config'

export const getAuthUrl = async () => {
    try {
        const response = await fetch(`${apiBaseUrl}/identity/authorize`)
        if (response.status !== 200) {
            handleErrors(response)
        }
        return await response.json()
    } catch (error) {
        console.error(error)
        return Promise.reject(error)
    }
}

export const exhangeCodeForToken = async (code: string | null, state: string | null) => {
    try {
        const response = await fetch(`${apiBaseUrl}/identity/token/?code=${code}&state=${state}`)
        console.log(response)
        if (response.status !== 200) {
            handleErrors(response)
        }
        return await response.json()
    } catch (error) {
        console.error(error)
        return Promise.reject(error)
    }
}

const handleErrors = async (response: Response) => {
    console.error(response)
    return Promise.reject(new Error(response.statusText))
}

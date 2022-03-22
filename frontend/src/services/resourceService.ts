import { apiBaseUrl } from '../config/config'
import { ResourceMessageResponse } from '../types'
import { getRequestOptions, handleErrors } from './utils'

export const getResourceHelloMessage = async (): Promise<ResourceMessageResponse> => {
    try {
        const postOptions = getRequestOptions()
        const response = await fetch(`${apiBaseUrl}/resource/hello`, postOptions)
        if (response.status !== 200) {
            return handleErrors(response)
        }

        return await response.json()
    } catch (error) {
        console.error(error)
        return Promise.reject(error)
    }
}

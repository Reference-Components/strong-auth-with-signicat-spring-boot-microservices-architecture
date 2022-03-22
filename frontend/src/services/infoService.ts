import { apiBaseUrl } from '../config/config'
import { InfoMessageResponse } from '../types'
import { getRequestOptions, handleErrors } from './utils'

export const getInfoHelloMessage = async (): Promise<InfoMessageResponse> => {
    try {
        const postOptions = getRequestOptions()
        const response = await fetch(`${apiBaseUrl}/info/hello`, postOptions)
        if (response.status !== 200) {
            return handleErrors(response)
        }

        return await response.json()
    } catch (error) {
        console.error(error)
        return Promise.reject(error)
    }
}

export const getResourceMessageFromInfoService = async (): Promise<InfoMessageResponse> => {
    try {
        const postOptions = getRequestOptions()
        const response = await fetch(`${apiBaseUrl}/info/resource-message`, postOptions)
        if (response.status !== 200) {
            return handleErrors(response)
        }

        return await response.json()
    } catch (error) {
        console.error(error)
        return Promise.reject(error)
    }
}

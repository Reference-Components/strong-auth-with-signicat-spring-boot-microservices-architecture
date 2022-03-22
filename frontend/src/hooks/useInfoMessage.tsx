import { useCallback, useEffect, useState } from 'react'
import { getInfoHelloMessage } from '../services/infoService'
import { InfoMessageResponse } from '../types'

type ReturnType = [message: InfoMessageResponse | undefined, error: string | undefined, fetching: boolean]

const useInfoMessage = (): ReturnType => {
    const [message, setMessage] = useState<InfoMessageResponse>()
    const [error, setError] = useState<string>()
    const [fetching, setFetching] = useState<boolean>(true)

    const getInfoMessage = useCallback(async () => {
        try {
            const message = await getInfoHelloMessage()
            setFetching(false)
            setMessage(message)
            setError(undefined)
        } catch (err: unknown) {
            console.error(err)
            const e = err as Error
            setFetching(false)
            setError(e.message)
            setMessage(undefined)
        }
    }, [])

    useEffect(() => {
        if (!message && fetching) {
            getInfoMessage()
        }
    }, [fetching, getInfoMessage, message])

    return [message, error, fetching]
}

export default useInfoMessage

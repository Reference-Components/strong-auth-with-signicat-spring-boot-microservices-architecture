import { useContext, useState } from 'react'
import { Link } from 'react-router-dom'
import { AuthContext } from '../context/AuthContextProvider'

import { getInfoHelloMessage, getResourceMessageFromInfoService } from '../services/infoService'
import { getResourceHelloMessage } from '../services/resourceService'
import { InfoMessageResponse, ResourceMessageResponse } from '../types'
import Greeting from '../components/Greeting'
import UserInfo from '../components/UserInfo'

const Protected = () => {
    const authContext = useContext(AuthContext)

    const [infoMessage, setInfoMessage] = useState<InfoMessageResponse>()
    const [infoResourceMessage, setInfoResourceMessage] = useState<InfoMessageResponse>()
    const [resourceMessage, setResourceMessage] = useState<ResourceMessageResponse>()
    const [error, setError] = useState<string>()

    const getInfoMessage = async () => {
        try {
            const message = await getInfoHelloMessage()
            setInfoMessage(message)
        } catch (error: unknown) {
            const err = error as Error
            setError(err.message)
        }
    }

    const getInfoResourceMessage = async () => {
        try {
            const message = await getResourceMessageFromInfoService()
            setInfoResourceMessage(message)
        } catch (error: unknown) {
            const err = error as Error
            setError(err.message)
        }
    }

    const getResourceMessage = async () => {
        try {
            const message = await getResourceHelloMessage()
            setResourceMessage(message)
        } catch (error: unknown) {
            const err = error as Error
            setError(err.message)
        }
    }

    if (authContext.isAuthenticated && authContext.userData) {
        return (
            <div>
                <h1>Protected view</h1>
                <Greeting />
                <UserInfo />

                <div>
                    <p>
                        <button onClick={getInfoMessage}>Get message from info service</button>
                        {infoMessage && <span>The message: {infoMessage.message}</span>}
                    </p>

                    <p>
                        <button onClick={getInfoResourceMessage}>Get resource service message via info service</button>
                        {infoResourceMessage && <span>The message: {infoResourceMessage.message}</span>}
                    </p>

                    <p>
                        <button onClick={getResourceMessage}>Get message from resource service</button>
                        {resourceMessage && <span>The message: {resourceMessage.message}</span>}
                    </p>

                    {error && <p>Error: {error}</p>}
                </div>

                <Link to={'/'}>Go home</Link>
            </div>
        )
    }

    return (
        <div>
            <h1>Protected view</h1>
            <Link to={'/'}>Go home</Link>
        </div>
    )
}

export default Protected

import { useContext, useState } from 'react'
import { AuthContext } from '../context/AuthContextProvider'
import useAuthEndpoint from '../hooks/useAuthEndpoint'
import { getInfoHelloMessage, getResourceMessageFromInfoService } from '../services/infoService'
import { getResourceHelloMessage } from '../services/resourceService'
import { InfoMessageResponse, ResourceMessageResponse } from '../types'

const Home = (): JSX.Element => {
    const authContext = useContext(AuthContext)
    const [authEndpoint, authEndpointError] = useAuthEndpoint()
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

    if (authEndpointError) {
        return <div>Failed to retrieve authorization endpoint: {authEndpointError}</div>
    }

    if (!authContext.isAuthenticated) {
        return (
            <div>
                <p>You are not logged in</p>
                <button onClick={() => window.location.replace(authEndpoint || '/')}>Log in</button>
            </div>
        )
    }

    return (
        <div>
            {authEndpointError && <p>Failed to retrieve auth endpoint: {authEndpointError}</p>}
            <p>You are logged in as: {authContext.userData?.name}</p>
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
    )
}

export default Home

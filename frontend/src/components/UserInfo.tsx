import { useContext } from 'react'
import { AuthContext } from '../context/AuthContextProvider'
import { millisToTimeStr } from '../utils/dateTimeUtils'
import TokenManager from '../utils/TokenManager'

const UserInfo = () => {
    const authContext = useContext(AuthContext)
    const tokenManager = TokenManager.getInstance()

    if (!authContext.userData) {
        return <></>
    }

    return (
        <div>
            <p>Raw identity data of authenticated user:</p>
            <pre>{authContext.userData.identityRawData}</pre>

            <p>
                <small>Id token: {tokenManager.getIdToken()}</small>
            </p>
            <p>
                <small>Expires: {millisToTimeStr(tokenManager.getExp())}</small>
            </p>
        </div>
    )
}

export default UserInfo

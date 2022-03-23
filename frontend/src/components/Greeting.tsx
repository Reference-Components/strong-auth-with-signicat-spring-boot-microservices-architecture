import { useContext } from 'react'
import { Link } from 'react-router-dom'
import { AuthContext } from '../context/AuthContextProvider'

const Greeting = () => {
    const authContext = useContext(AuthContext)

    if (!authContext.isAuthenticated) {
        return <></>
    }

    return (
        <p>
            You are logged in as: {authContext.userData?.name}. (<Link to={'/logout'}>Log out</Link>)
        </p>
    )
}

export default Greeting

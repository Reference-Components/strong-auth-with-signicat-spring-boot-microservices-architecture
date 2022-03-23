import useAuthEndpoint from '../hooks/useAuthEndpoint'

const Login = (): JSX.Element => {
    const [authEndpoint, authEndpointError] = useAuthEndpoint()

    if (authEndpointError) {
        return (
            <div>
                <h1>Login</h1>
                <p>Failed to retrieve authorization endpoint: {authEndpointError}</p>
            </div>
        )
    }

    if (authEndpoint) {
        window.location.replace(authEndpoint)
    }

    return <></>
}

export default Login

import useAuthToken from '../hooks/useAuthToken'
import { useQueryParams } from '../hooks/useQueryParams'

const Redirect = () => {
    const token = useAuthToken()
    return <div>REdirect</div>
}

export default Redirect

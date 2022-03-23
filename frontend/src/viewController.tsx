import { Route, Routes } from 'react-router-dom'
import Home from './views/Home'
import Logout from './views/Logout'
import Protected from './views/Protected'
import Redirect from './views/Redirect'

export const ViewController = () => {
    return (
        <div>
            <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/redirect" element={<Redirect />} />
                <Route path="/protected" element={<Protected />} />
                <Route path="/logout" element={<Logout />} />
            </Routes>
        </div>
    )
}

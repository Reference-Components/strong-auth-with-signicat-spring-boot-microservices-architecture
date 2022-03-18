import { Route, Routes } from 'react-router-dom'
import Home from './views/Home'
import Redirect from './views/Redirect'

export const ViewController = () => {
    return (
        <div>
            <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/redirect" element={<Redirect />} />
            </Routes>
        </div>
    )
}

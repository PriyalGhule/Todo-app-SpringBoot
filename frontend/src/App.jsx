import {
    BrowserRouter,
    Routes,
    Route
} from "react-router-dom";

import Login from "./pages/Login";
import Register from "./pages/Register";
import Todos from "./pages/Todos";
import AddTodo from "./pages/AddTodos";
import EditTodo from "./pages/EditTodos";
import ProtectedRoute from "./components/ProtectedRoute";

function App() {

    return (
        <BrowserRouter>

            <Routes>

                <Route path="/" element={<Todos />} />
<Route path="/add" element={<AddTodo />} />
<Route path="/edit/:id" element={<EditTodo />} />
<Route path="/login" element={<Login />} />
<Route path="/register" element={<Register />} />

            </Routes>

        </BrowserRouter>
    );
}

export default App;
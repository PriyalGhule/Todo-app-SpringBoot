import {
    useContext,
    useEffect,
    useState
} from "react";

import {
    Link,
    useNavigate
} from "react-router-dom";

import API from "../services/api";
import { AuthContext } from "../context/AuthContext";

export default function Todos() {

    const [todos, setTodos] = useState([]);
    const [keyword, setKeyword] = useState("");

    const { logout } = useContext(AuthContext);

    const navigate = useNavigate();

    const handleLogout = () => {

        logout();

        navigate("/login");
    };

    const fetchTodos = async () => {

        try {

            const response = await API.get(
                `/api/todos?keyword=${keyword}`
            );

            setTodos(response.data);

        } catch (error) {

            console.error(error);
        }
    };

    useEffect(() => {

        fetchTodos();

    }, []);

    const handleDelete = async (id) => {

        await API.delete(`/api/todos/${id}`);

        fetchTodos();
    };

    const handleToggle = async (id) => {

        await API.put(`/api/todos/toggle/${id}`);

        fetchTodos();
    };

    const handleDeleteAll = async () => {

        await API.delete("/api/todos");

        fetchTodos();
    };

    const handleDeleteCompleted = async () => {

        await API.delete("/api/todos/completed");

        fetchTodos();
    };

    return (

        <div className="min-h-screen bg-zinc-950 p-8">

            <div className="max-w-5xl mx-auto">

                <div className="bg-zinc-900 border border-zinc-800 rounded-3xl shadow-2xl p-8">

                    {/* Header */}

                    <div className="flex justify-between items-center mb-8">

                        <div>

                            <h1 className="text-4xl font-bold text-white">
                                My Todo List
                            </h1>

                            <p className="text-zinc-400 mt-2">
                                Organize your tasks efficiently
                            </p>

                        </div>

                        <div className="flex gap-3">

                            <Link to="/add">

                                <button
                                    className="
                                        bg-indigo-600
                                        hover:bg-indigo-700
                                        text-white
                                        px-5
                                        py-2
                                        rounded-xl
                                        transition
                                    "
                                >
                                    Add Todo
                                </button>

                            </Link>

                            <button
                                onClick={handleLogout}
                                className="
                                    bg-zinc-700
                                    hover:bg-zinc-600
                                    text-white
                                    px-5
                                    py-2
                                    rounded-xl
                                    transition
                                "
                            >
                                Logout
                            </button>

                        </div>

                    </div>

                    {/* Search */}

                    <div className="flex gap-3 mb-6">

                        <input
                            type="text"
                            placeholder="Search todos..."
                            value={keyword}
                            onChange={(e) =>
                                setKeyword(e.target.value)
                            }
                            className="
                                flex-1
                                bg-zinc-800
                                border
                                border-zinc-700
                                text-white
                                rounded-xl
                                p-3
                                outline-none
                                focus:border-indigo-500
                            "
                        />

                        <button
                            onClick={fetchTodos}
                            className="
                                bg-indigo-600
                                hover:bg-indigo-700
                                text-white
                                px-5
                                rounded-xl
                                transition
                            "
                        >
                            Search
                        </button>

                        <button
                            onClick={() => {

                                setKeyword("");

                                setTimeout(
                                    fetchTodos,
                                    0
                                );
                            }}
                            className="
                                bg-zinc-700
                                hover:bg-zinc-600
                                text-white
                                px-5
                                rounded-xl
                                transition
                            "
                        >
                            Clear
                        </button>

                    </div>

                    {/* Action Buttons */}

                    <div className="flex gap-3 mb-8">

                        <button
                            onClick={handleDeleteAll}
                            className="
                                bg-red-600
                                hover:bg-red-700
                                text-white
                                px-5
                                py-2
                                rounded-xl
                                transition
                            "
                        >
                            Delete All
                        </button>

                        <button
                            onClick={handleDeleteCompleted}
                            className="
                                bg-zinc-700
                                hover:bg-zinc-600
                                text-white
                                px-5
                                py-2
                                rounded-xl
                                transition
                            "
                        >
                            Delete Completed
                        </button>

                        <a
                            href="http://localhost:8080/api/todos/download"
                            target="_blank"
                            rel="noreferrer"
                        >

                            <button
                                className="
                                    bg-indigo-600
                                    hover:bg-indigo-700
                                    text-white
                                    px-5
                                    py-2
                                    rounded-xl
                                    transition
                                "
                            >
                                Download
                            </button>

                        </a>

                    </div>

                    {/* Todos */}

                    {todos.length === 0 ? (

                        <div className="text-center py-16">

                            <p className="text-zinc-400 text-lg">
                                No todos found
                            </p>

                        </div>

                    ) : (

                        <div className="grid gap-4">

                            {todos.map((todo) => (

                                <div
                                    key={todo.id}
                                    className="
                                        bg-zinc-800
                                        border
                                        border-zinc-700
                                        rounded-2xl
                                        p-5
                                        hover:border-zinc-600
                                        transition
                                    "
                                >

                                    <div className="flex justify-between items-start">

                                        <div className="flex gap-4">

                                            <input
                                                type="checkbox"
                                                checked={todo.completed}
                                                onChange={() =>
                                                    handleToggle(todo.id)
                                                }
                                                className="mt-1 h-5 w-5"
                                            />

                                            <div>

                                                <h3
                                                    className={`text-xl font-semibold ${
                                                        todo.completed
                                                            ? "line-through text-zinc-500"
                                                            : "text-white"
                                                    }`}
                                                >
                                                    {todo.title}
                                                </h3>

                                                <div className="flex gap-3 mt-3 flex-wrap">

                                                    <span
                                                        className={
                                                            todo.completed
                                                                ? `
                                                                    px-3 py-1
                                                                    rounded-full
                                                                    text-sm
                                                                    bg-zinc-700
                                                                    text-zinc-300
                                                                  `
                                                                : `
                                                                    px-3 py-1
                                                                    rounded-full
                                                                    text-sm
                                                                    bg-indigo-900
                                                                    text-indigo-300
                                                                  `
                                                        }
                                                    >
                                                        {
                                                            todo.completed
                                                                ? "Completed"
                                                                : "Pending"
                                                        }
                                                    </span>

                                                    <span
                                                        className="
                                                            px-3 py-1
                                                            rounded-full
                                                            text-sm
                                                            bg-zinc-700
                                                            text-zinc-300
                                                        "
                                                    >
                                                        {
                                                            todo.dueDate
                                                                ? todo.dueDate
                                                                : "No Due Date"
                                                        }
                                                    </span>

                                                </div>

                                            </div>

                                        </div>

                                        <div className="flex gap-2">

                                            <Link
                                                to={`/edit/${todo.id}`}
                                            >

                                                <button
                                                    className="
                                                        bg-zinc-700
                                                        hover:bg-zinc-600
                                                        text-white
                                                        px-4
                                                        py-2
                                                        rounded-xl
                                                        transition
                                                    "
                                                >
                                                    Edit
                                                </button>

                                            </Link>

                                            <button
                                                onClick={() =>
                                                    handleDelete(todo.id)
                                                }
                                                className="
                                                    bg-red-600
                                                    hover:bg-red-700
                                                    text-white
                                                    px-4
                                                    py-2
                                                    rounded-xl
                                                    transition
                                                "
                                            >
                                                Delete
                                            </button>

                                        </div>

                                    </div>

                                </div>

                            ))}

                        </div>

                    )}

                </div>

            </div>

        </div>
    );
}
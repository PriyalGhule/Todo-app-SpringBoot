import {
    useEffect,
    useState
} from "react";

import {
    useNavigate,
    useParams
} from "react-router-dom";

import API from "../services/api";

export default function EditTodo() {

    const { id } = useParams();

    const navigate = useNavigate();

    const [todo, setTodo] = useState({
        title: "",
        dueDate: ""
    });

    useEffect(() => {

        fetchTodo();

    }, []);

    const fetchTodo = async () => {

        const response =
            await API.get(`/api/todos/${id}`);

        setTodo(response.data);
    };

    const handleChange = (e) => {

        setTodo({
            ...todo,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = async (e) => {

        e.preventDefault();

        await API.put(
            `/api/todos/${id}`,
            todo
        );

        navigate("/");
    };

    return (

        <div className="min-h-screen flex items-center justify-center p-8">

            <div className="w-full max-w-lg bg-zinc-900 rounded-3xl shadow-2xl border border-zinc-800 p-8">

                <div className="mb-8">

                    <h2 className="text-4xl font-bold text-white">
                        Edit Todo
                    </h2>

                    <p className="text-zinc-400 mt-2">
                        Update your task details.
                    </p>

                </div>

                <form
                    onSubmit={handleSubmit}
                    className="space-y-6"
                >

                    <div>

                        <label className="block text-zinc-300 mb-2">
                            Title
                        </label>

                        <input
                            type="text"
                            name="title"
                            value={todo.title}
                            onChange={handleChange}
                            className="
                                w-full
                                bg-zinc-800
                                text-white
                                border
                                border-zinc-700
                                rounded-xl
                                p-4
                                outline-none
                                focus:border-indigo-500
                            "
                        />

                    </div>

                    <div>

                        <label className="block text-zinc-300 mb-2">
                            Due Date
                        </label>

                        <input
                            type="date"
                            name="dueDate"
                            value={todo.dueDate || ""}
                            onChange={handleChange}
                            className="
                                w-full
                                bg-zinc-800
                                text-white
                                border
                                border-zinc-700
                                rounded-xl
                                p-4
                                outline-none
                                focus:border-indigo-500
                            "
                        />

                    </div>

                    <div className="flex gap-4 pt-2">

                        <button
                            type="submit"
                            className="
                                flex-1
                                bg-indigo-600
                                hover:bg-indigo-700
                                text-white
                                py-3
                                rounded-xl
                                font-semibold
                                transition
                            "
                        >
                            Update Todo
                        </button>

                        <button
                            type="button"
                            onClick={() => navigate("/")}
                            className="
                                flex-1
                                bg-zinc-700
                                hover:bg-zinc-600
                                text-white
                                py-3
                                rounded-xl
                                font-semibold
                                transition
                            "
                        >
                            Cancel
                        </button>

                    </div>

                </form>

            </div>

        </div>
    );
}
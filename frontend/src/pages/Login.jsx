import { useContext, useState } from "react";
import { useNavigate, Link } from "react-router-dom";

import { AuthContext } from "../context/AuthContext";
import { loginUser } from "../services/authService";

const Login = () => {

    const navigate = useNavigate();

    const { login } = useContext(AuthContext);

    const [formData, setFormData] = useState({
        username: "",
        password: ""
    });

    const handleChange = (e) => {

        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = async (e) => {

        e.preventDefault();

        try {

            const response = await loginUser(formData);

            login(response.data.token);

            navigate("/");

        } catch (error) {

            console.error(error);

            alert("Login failed");
        }
    };

    return (

        <div className="min-h-screen flex items-center justify-center p-8">

            <div className="w-full max-w-md bg-zinc-900 rounded-3xl shadow-2xl border border-zinc-800 p-8">

                <div className="text-center mb-8">

                    <h1 className="text-4xl font-bold text-white">
                        Welcome Back
                    </h1>

                    <p className="text-zinc-400 mt-2">
                        Sign in to manage your tasks
                    </p>

                </div>

                <form
                    onSubmit={handleSubmit}
                    className="space-y-5"
                >

                    <div>

                        <label className="block text-zinc-300 mb-2">
                            Username
                        </label>

                        <input
                            type="text"
                            name="username"
                            placeholder="Enter username"
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
                            Password
                        </label>

                        <input
                            type="password"
                            name="password"
                            placeholder="Enter password"
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

                    <button
                        type="submit"
                        className="
                            w-full
                            bg-indigo-600
                            hover:bg-indigo-700
                            text-white
                            py-3
                            rounded-xl
                            font-semibold
                            transition
                        "
                    >
                        Login
                    </button>

                </form>

                <div className="mt-6 text-center">

                    <p className="text-zinc-400">
                        Don't have an account?
                    </p>

                    <Link
                        to="/register"
                        className="text-indigo-400 hover:text-indigo-300 font-medium"
                    >
                        Register here
                    </Link>

                </div>

            </div>

        </div>
    );
};

export default Login;
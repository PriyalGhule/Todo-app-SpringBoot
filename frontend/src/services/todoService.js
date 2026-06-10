import API from "./api";

export const getTodos = () => {
    return API.get("/api/todos");
};

export const addTodo = (todo) => {
    return API.post("/api/todos", todo);
};

export const toggleTodo = (id) => {
    return API.put(`/api/todos/toggle/${id}`);
};

export const deleteTodo = (id) => {
    return API.delete(`/api/todos/${id}`);
};
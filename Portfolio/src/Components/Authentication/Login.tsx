import React from "react";
import { UserDetail } from "../types/types";
import axios from "axios";
import { useCookies } from "react-cookie";

axios.defaults.withCredentials=true
function Login(): React.JSX.Element{
    const [cookie,setCookie]=useCookies(["backend"])
    const handleLogin=(e:React.FormEvent<HTMLFormElement>)=>{
        e.preventDefault()
        
        const detail:UserDetail={
            username:e.target[0].value,
            password:e.target[1].value
        }
        const credentials=btoa(`${detail.username}:${detail.password}`)
        const headers = {
            'Authorization': `Basic ${credentials}`,
           // 'Content-Type': 'application/json'
        };
        // axios.get("http://localhost:8080/prefs/xo",
        //     {
        //         headers
        //     }
        // ).then(res=>res.data)
        // .then(data=>console.log(data))
        axios.get(
            "http://localhost:8080/prefs/shlm",
            {
                headers
            }
        )
        .then(c=>console.log(c))
        
    }
    
    return <form className="flex justify-center bg-white 
    flex-col py-24 px-8 rounded-2xl border-l border-b shadow-md
    gap-8 
    " 
    onSubmit={handleLogin}
    >
        <p className=" font-semibold text-2xl font-san self-center">
            Login
            </p> 
        <label htmlFor="username">
            Username <input className="p-1 mx-2 border rounded-lg" 
            name="username" type="text" required/>
        </label>
        <label  htmlFor="password">
            Password <input className="p-1 mx-2 border rounded-lg"
            name="password" type="password"  required/>
        </label>
        <button type="submit" 
        className=" font-bold text-white bg-blue-500 w-min py-2 px-6 rounded-xl
        self-end">
            Login
        </button>
    </form>
}
export default Login;
import React from "react";
import { formState, UserDetail } from "../types/types";
import axios from "axios";
import { useCookies } from "react-cookie";

axios.defaults.withCredentials=true
function Login({
    setSignIn
}:formState):React.JSX.Element{
    
    
    const [cookie,setCookie]=useCookies(["backend","token"])
    const handleLogin=(e:React.FormEvent<HTMLFormElement>)=>{
        e.preventDefault()
        //TODO make a way to handle requests with bearer token
        const detail:UserDetail={
            username:(e.target as HTMLFormElement)[0].value,
            password:(e.target as HTMLFormElement)[1].value
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
        axios.post(
            "http://localhost:8080/login",
            detail
        )
        .then(c=>c.status)
        .then(d=>{
            if(d==204)
                setSignIn("signed-in")
        })
        .catch(err=>console.log("err",err)
        )
        
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
        <span className="flex justify-around">

            <button onClick={e=>setSignIn("sign-up")} 
            className=" font-normal text-blue-500 bg-transparent w-min py-2 px-6 rounded-xl
            self-start text-nowrap">
                Sign-up
            </button>
            <button type="submit" 
            className=" font-bold text-white bg-blue-500 w-min py-2 px-6 rounded-xl
            self-end">
                Login
            </button>
        </span>

    </form>
}
export default Login;
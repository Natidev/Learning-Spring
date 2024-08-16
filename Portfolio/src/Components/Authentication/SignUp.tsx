import React, { useRef, useState } from "react";
import axios from "axios";
axios.defaults.withCredentials=true
function SignUp():React.JSX.Element{
    const passRef=useRef<HTMLInputElement>(null)
    const [notAMatch,setNotAMatch]=useState<boolean>(false)
    const confirmPassword=(e:React.ChangeEvent<HTMLInputElement>)=>{
        
        
        setNotAMatch(!(e.target.value===passRef.current?.value))
    }
    const handleSignUp=(e:React.FormEvent<HTMLFormElement>)=>{
        e.preventDefault()
        
        const detail:UserDetail={
            username:e.target[0].value,
            password:e.target[1].value
        }
        
        // axios.get("http://localhost:8080/prefs/xo",
        //     {
        //         headers
        //     }
        // ).then(res=>res.data)
        // .then(data=>console.log(data))
        axios.post(
            "http://localhost:8080/register",
            detail
            
        )
        .then(c=>console.log(c))
        
    }
    
    return <form onSubmit={handleSignUp}
    className="flex justify-center bg-white 
    flex-col py-24 px-8 rounded-2xl border-l border-b shadow-md
    gap-8 
    ">
        <p className=" font-semibold text-2xl font-san self-center">
            SignUp
            </p> 
        <label htmlFor="username">
            Username <input className="p-1 mx-2 border rounded-lg" 
            name="username" type="text" required/>
        </label>
        <label  htmlFor="password">
            Password <input className="p-1 mx-2 border rounded-lg"
            name="password" type="password"  required ref={passRef} />

        </label>
        <label  htmlFor="cpassword" >
            Confirm<input className="p-1 mx-2 border rounded-lg  border-b-2"
            name="cpassword" type="password"  required onChange={e=>{
                confirmPassword(e)
                console.log(notAMatch)
            
            }}/>
            {notAMatch && <p className="text-red-500">Password does not match </p>}
        </label>
        
        <button type="submit" 
        className=" font-bold text-white bg-blue-500 w-min py-2 px-6 rounded-xl
        self-end">
            SignUp
        </button>
        </form>
}
export default SignUp;
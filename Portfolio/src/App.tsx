import { useEffect, useState } from "react"
import Login from "./Components/Authentication/Login"
import SignUp from "./Components/Authentication/SignUp"
import Home from "./Components/Home"
import { useCookies } from "react-cookie"
import axios from "axios"
axios.defaults.withCredentials=true
function App() {
  const [signIn,setSignIn]=useState<"sign-in"|"signed-in"|"sign-up">("sign-in")
  return <div className="flex h-lvh w-full justify-center items-center bg-zinc-100">
    {signIn==="sign-in"?<Login setSignIn={setSignIn}/>:""}
    {signIn==="sign-up"?<SignUp setSignIn={setSignIn}/>:""}
    {signIn==="signed-in"?<Home/>:""}
  </div>
  
}

export default App

import { useState } from "react"
import Login from "./Components/Authentication/Login"
import SignUp from "./Components/Authentication/SignUp"

function App() {
  const [signIn,setSignIn]=useState<"sign-in"|"signed-in"|"sign-up">("sign-in")
  
  return <div className="flex h-lvh w-full justify-center items-center bg-zinc-100">
    {signIn==="sign-in"?<Login setSignIn={setSignIn}/>:""}
    {signIn==="sign-up"?<SignUp setSignIn={setSignIn}/>:""}
    {signIn==="signed-in"?"Succesfully signedIn":""}
  </div>
  
}

export default App

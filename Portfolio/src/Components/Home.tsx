import { useState } from "react";
import { useCookies } from "react-cookie";

function Home():React.JSX.Element{
    const [cookie,setCookie]=useCookies(["token"])
    const [dsply,setDsply]=useState<string>("no cookie")
    return <div>
        is the bearer working <br/>
        {dsply}
        <button >
            Test
        </button>
    </div>
}
export default Home;
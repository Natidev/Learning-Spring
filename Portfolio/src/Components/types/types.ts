export type UserDetail={
    username:string;
    password:string;
}
export type formState={
    setSignIn:React.Dispatch<React.SetStateAction<"sign-in" | "signed-in" | "sign-up">>
}
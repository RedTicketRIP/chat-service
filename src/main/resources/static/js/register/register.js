import {sendAxios} from "../axios.js";
import { parseAndShowError} from "../errHandler.js";

const formRegister = document.querySelector("#registerForm")
const registerMessage = document.querySelector("#registerMessage")

formRegister.addEventListener("submit", e=> {
    e.preventDefault()

    const username = formRegister.username.value;
    const password = formRegister.password.value;

    const data = {
        "id":username,
        "password":password
    }

    sendAxios("POST", "/public/user/", data).then(data=> {
        console.log(data)
        location.href='/'
    }).catch(e => {
        parseAndShowError(e.response.data, registerMessage);
    })
})

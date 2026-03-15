import {getMyInfoAPI} from "../api.js";
import {setUsername} from "../storage.js";

document.addEventListener('DOMContentLoaded', async () => {
    const usernameEl = document.querySelector('#username');
    const loginBtn = document.querySelector('#loginBtn')
    const logoutBtn = document.querySelector('#logoutBtn')

    try {
        const myInfo = await getMyInfoAPI();

        setUsername(myInfo.id)
        usernameEl.textContent = myInfo.id;

        loginBtn.hidden = true;
        logoutBtn.hidden = false;
    } catch (e) {
        loginBtn.hidden = false;
        logoutBtn.hidden = true;
        throw e;
    }
})
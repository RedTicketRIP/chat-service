
import { loadCaptchaModal} from "./captcha.js";
import {verifyLoginAPI} from "../api.js";
import {isRequireCaptcha, parseAndShowError} from "../errHandler.js";

const headers = {
    'Content-Type': 'application/x-www-form-urlencoded',
}

document.addEventListener("DOMContentLoaded", () => {
    const loginForm = document.querySelector("#loginForm");
    const errField = document.querySelector("#loginError")

    loginForm.addEventListener('submit', (e) => login(e));


    async function login(e) {
        if (e && e.preventDefault) e.preventDefault();

        const username = loginForm.username.value;
        const password = loginForm.password.value;

        try {
            const res = await verifyLoginAPI(username, password);

            location.href = "/";
        } catch (e) {
            if (isRequireCaptcha(e)) {
                loadCaptchaModal(username);
            } else {
                parseAndShowError(e, errField);
            }
        }
    }

});

export function goLogin() {
    location.href = "/login";
}

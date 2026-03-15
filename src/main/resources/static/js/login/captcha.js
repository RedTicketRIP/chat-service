import {closeModal,  showModal} from "../util.js";
import {parseAndShowError} from "../errHandler.js";
import {verifyCaptchaAPI} from "../api.js";

const headers = {
    'Content-Type': 'application/x-www-form-urlencoded',
}

let captchaModal = null;
let errField = null;
let captchaImageEl = null;
let captchaTargetUsername = null;

document.addEventListener('DOMContentLoaded', async () => {
    captchaModal = document.querySelector("#captcha_modal");
    errField = captchaModal.querySelector(".error-message")
    captchaImageEl = captchaModal.querySelector('#captchaImage')
    captchaTargetUsername = captchaModal.querySelector('#captcha_target_username');

    const captchaInput = captchaModal.querySelector("#captcha_input");
    const captchaSubmit = captchaModal.querySelector("#captcha_submit_btn");

    captchaSubmit.addEventListener('click', async (e) => {
        const captchaUser = captchaTargetUsername.value;
        const captchaString = captchaInput.value;

        try {
            await verifyCaptchaAPI(captchaUser, captchaString);

            closeModal(captchaModal);
        } catch (e) {
            parseAndShowError(e, errField);
        }
    })



})

export function loadCaptchaModal(username) {
    captchaImageEl.src = "/captcha?username="+username;
    captchaTargetUsername.value = username;
    showModal(captchaModal)

}


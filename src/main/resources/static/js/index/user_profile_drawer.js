import {showDrawer} from "../util.js";
import {getUsername} from "../storage.js";
import {goLogout} from "../login/logout.js";
import {getMyInfoAPI} from "../api.js";

document.addEventListener('DOMContentLoaded', async () => {
    const userProfileBtn = document.querySelector('#user_info')
    const userProfileModal = document.querySelector('#user_profile_drawer')
    const usernameEl = userProfileModal.querySelector('#username')
    const footer = userProfileModal.querySelector('#profile_footer');
    const logoutBtn = userProfileModal.querySelector('#logout')

    const loginUsername = await getMyInfoAPI();

    userProfileBtn.addEventListener('click', e => {
        showDrawer(userProfileModal);
    })

    usernameEl.textContent = loginUsername.id;

    footer.hidden = false;

    logoutBtn.addEventListener('click', e => {
        goLogout();
    })

})
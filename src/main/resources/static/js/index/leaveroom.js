import {getCurrentRoomId} from "../storage.js";
import {leaveRoomAPI} from "../api.js";
import {hideError, showModal} from "../util.js";
import {parseAndShowError} from "../errHandler.js";


document.addEventListener('DOMContentLoaded',  () => {
    const leaveBtn = document.querySelector('#drawer_leave_btn')
    const leaveModal = document.querySelector('#chatroom_leave_modal')
    const realLeaveBtn = leaveModal.querySelector('#real_leave_btn')
    const errField = leaveModal.querySelector('.error-message');

    leaveBtn.addEventListener('click',  (e) => {
        hideError(errField);
        showModal(leaveModal);
    })

    realLeaveBtn.addEventListener('click', async (e) => {
        hideError(errField)

        const rid = getCurrentRoomId();
        if (!rid) {
            console.log("join room first");
            return;
        }

        try {
            await leaveRoomAPI(rid);
        } catch (e) {
            parseAndShowError(e, errField);
            return;
        }

        window.location.reload();
    })

})
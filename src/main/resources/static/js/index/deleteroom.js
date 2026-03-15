import {getCurrentRoomId} from "../storage.js";
import {deleteRoomAPI} from "../api.js";
import {hideError,  showModal} from "../util.js";
import {parseAndShowError} from "../errHandler.js";


document.addEventListener('DOMContentLoaded',  () => {
    const deleteBtn = document.querySelector('#drawer_delete_btn')
    const deleteModal = document.querySelector('#chatroom_delete_modal')
    const realDeleteBtn = deleteModal.querySelector('#real_delete_btn')
    const errField = deleteModal.querySelector('.error-message');

    deleteBtn.addEventListener('click',  (e) => {
        hideError(errField)
        showModal(deleteModal);
    })

    realDeleteBtn.addEventListener('click', async (e) => {
        hideError(errField)

        const rid = getCurrentRoomId();
        if (!rid) {
            console.log("join room first");
            return;
        }

        try {
            await deleteRoomAPI(rid);
        } catch (e) {
            parseAndShowError(e, errField)
            return;
        }

        window.location.reload();
    })

})
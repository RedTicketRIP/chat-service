import {showModal, closeModal} from "../util.js";
import {createChatroomAPI} from "../api.js";
import {loadJoiningChatroomList} from "./chatroom_list.js";
import {parseAndShowError} from "../errHandler.js";


document.addEventListener('DOMContentLoaded', () => {
    const newChatroomBtn = document.getElementById('btn_add_chatroom');
    const modal = document.getElementById('chatroom_create_modal')
    const createRoomBtn = modal.querySelector("#createroom_btn");
    const chatroomNameInput =  modal.querySelector('#chatroom_name_input');
    const errField = modal.querySelector('.error-message');

    newChatroomBtn.addEventListener('click', (e) => {
        showModal(modal);
    })

    createRoomBtn.addEventListener('click', async (e) => {
        const name = chatroomNameInput.value.trim();
        if (name) {
            try {
                await createChatroomAPI(name)
                await loadJoiningChatroomList();

            } catch (e) {
                parseAndShowError(e, errField);
                return;
            }

            closeModal(modal);
        } else {
            chatroomNameInput.focus();
            setTimeout(() => chatroomNameInput.style.borderColor = "", 2000);
        }
    })
})


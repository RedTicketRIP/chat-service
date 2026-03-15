
import {showModal, closeModal} from "../util.js";
import {loadJoiningChatroomList} from "./chatroom_list.js";
import {getAllChatroomAPI, sendJoinChatAPI} from "../api.js";
import {subscribeRoom} from "../stomp.js";
import {getUsername} from "../storage.js";
import {parseAndShowError} from "../errHandler.js";

document.addEventListener('DOMContentLoaded', () => {
    const joinChatroomBtn = document.getElementById('btn_join_chatroom');
    const joinRoomModal = document.getElementById('chatroom_join_modal')
    const roomList = joinRoomModal.querySelector('#room_list');
    const errField = joinRoomModal.querySelector(".error-message");

    joinChatroomBtn.addEventListener('click',  async (e) => {
        clearRoomList();

        let chatrooms = null;
        try {
            chatrooms = await getAllChatroomAPI();

        } catch (e) {
            parseAndShowError(e, errField);
            throw e;
        }

        chatrooms.forEach(room => {
            roomList.append(createRoomTag(room, joinRoomModal));
        })

        bindEventInRoomTag(joinRoomModal);


        showModal(joinRoomModal);
    });

    function clearRoomList() {
        roomList.innerHTML = "";
    }

    function bindEventInRoomTag(modal) {
        const items = document.querySelectorAll('.join-room-item');

        items.forEach(item => {
            const roomName = item.querySelector('.join-room-name').textContent
            const joinBtn = item.querySelector('.join-room-action-btn')

            joinBtn.addEventListener("click", e => {
                sendJoinChatAPI(roomName).then(data => {
                    closeModal(modal);
                    loadJoiningChatroomList();
                    subscribeRoom(roomName, getUsername());
                })
            })
        })
    }


    function createRoomTag(chatroom, modal) {
        const item = document.createElement('div');
        item.className = 'join-room-item';

        const roomInfo = document.createElement('join-room-info');
        roomInfo.className = "join-room-info";

        const roomName =  document.createElement('span');
        roomName.className = "join-room-name"
        roomName.textContent = chatroom.id;

        const roomCreated =  document.createElement('span');
        roomCreated.className = "join-room-meta"
        roomCreated.textContent = chatroom.createdTime;

        const joinBtn =  document.createElement('button');
        joinBtn.className = "join-room-action-btn"
        joinBtn.textContent = "Join"

        roomInfo.append(roomName);
        roomInfo.append(roomCreated);

        item.append(roomInfo)
        item.append(joinBtn)


        return item;
    }


});

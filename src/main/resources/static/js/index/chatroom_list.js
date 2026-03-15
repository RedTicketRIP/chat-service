import { getJoiningRoomAPI, sendJoinChatAPI } from "../api.js";
import { addChatHistoryListInContainer, enableMessageContainer, setRoomNameInChatContainer } from "./chat_container.js";
import { subscribeRoom } from "../stomp.js";
import { getUsername, setCurrentRoomId } from "../storage.js";

const chatroomList = document.querySelector('.chat-list')

document.addEventListener("DOMContentLoaded", () => {
    loadJoiningChatroomList();

});

// 현재 참가중인 모든 방 구독.
export function subscribeAllJoiningRoom() {
    const username = getUsername();
    const chatrooms = chatroomList.querySelectorAll(".chat-item");
    for (const chatroom of chatrooms) {
        const chatId = chatroom.querySelector(".chat-id").textContent;
        subscribeRoom(chatId,username);
    }
}



export function clearChatroomListContainer() {
    const items = chatroomList.querySelectorAll(".chat-item");
    for (let i = 0; i < items.length; i++) {
        items[i].remove();
    }
}


export function addUnreadCount(rid) {
    const chatrooms = chatroomList.querySelectorAll(".chat-item");
    for (const chatroom of chatrooms) {
        const chatId = chatroom.querySelector(".chat-id").textContent;
        if (chatId === rid) {
            const badge = chatroom.querySelector(".unread-badge");
            if (badge) {
                const currentCount = parseInt(badge.textContent) || 0;
                badge.textContent = (currentCount + 1).toString();
                badge.style.display = "flex";
            }
        }
    }



    return null;
}

// 컨테이너에 현재 참여중인 채팅룸 보여주기.
export async function loadJoiningChatroomList() {
    clearChatroomListContainer()

    let chatrooms = null;
    try {
        chatrooms = await getJoiningRoomAPI();
    } catch (e) {
        console.log("loadJoiningChatroomList err: ", e);
        throw e;
    }

    console.log(chatrooms);
    loadChatRoomInContainer(chatrooms);
}

export function loadChatRoomInContainer(chatrooms) {
    chatrooms.forEach(chatroom => {
        const chatroomTag = createChatRoomTag(chatroom);

        bindEventOnRoomTag(chatroomTag)

        chatroomList.append(chatroomTag)
    });
}

function bindEventOnRoomTag(chatroomTag) {
    chatroomTag.addEventListener("click", async e => {
        const target = e.currentTarget;

        // Clear unread badge
        const badge = target.querySelector(".unread-badge");
        if (badge) {
            badge.textContent = "0";
            badge.style.display = "none";
        }

        const chatId = target.querySelector(".chat-id").textContent;

        const chatHistory = await sendJoinChatAPI(chatId); // join 을 서버에 알림.
        enableMessageContainer();                                       // 채팅창 활성화.
        addChatHistoryListInContainer(chatHistory);                     // 채팅창에 이전 채팅기록 로드.
        setCurrentRoomId(chatId);                                       // 현재 room id 설정.
        setRoomNameInChatContainer(chatId);                             // 채팅창 상단 room name 설정.
        subscribeRoom(chatId, getUsername()) // 통신을 위한 subscribe
    });
}

// 채팅방에 대한 골격 반환.
function createChatRoomTag(chatRoomInfo) {
    const item = document.createElement("div")
    item.className = "chat-item active";

    const info = document.createElement("div")
    info.className = "chat-info";

    const header = document.createElement("div")
    header.className = "chat-header";

    // 채팅방ID
    const chatIdSpan = document.createElement("span");
    chatIdSpan.className = "chat-id"
    chatIdSpan.textContent = chatRoomInfo.id;


    // 채팅방생성 일자.
    const chatTime = document.createElement("span");
    chatTime.className = "chat-time"
    chatTime.textContent = chatRoomInfo.createdTime;

    header.append(chatIdSpan);
    header.append(chatTime);

    info.append(header)

    // 안 읽은 메시지 배지
    const unreadBadge = document.createElement("span");
    unreadBadge.className = "unread-badge";
    unreadBadge.textContent = "0";
    unreadBadge.style.display = "none"; // 처음에는 숨김

    item.append(info);
    item.append(unreadBadge);


    return item;
}
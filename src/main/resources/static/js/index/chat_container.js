import {getCurrentRoomId, getSubscribeInMap, getUsername} from "../storage.js";
import {stompSendMessage} from "../stomp.js";

const container = document.querySelector(".messages-container");
const sendBtn = document.querySelector(".sendBtn")
const messageInput = document.querySelector(".message-input")
const headerName = document.querySelector(".header-name");


// 페이지 로드 시 message container 초기화, 비활성화.
document.addEventListener('DOMContentLoaded', () => {
    clearMessageContainer();
    disableMessageInput();
})


function clearMessageContainer() {
    container.innerHTML = ""
}

function enableMessageInput() {
    messageInput.disabled = false
}

function disableMessageInput() {
    messageInput.disabled = true
}

// 채팅기록을 message container 에 로드.
export function addChatHistoryListInContainer(chatHistoryList) {
    chatHistoryList.forEach(data => { // 이전 채팅 목록.
        let comment = null;

        if (!data.notify)
            comment = createComment(data);
        else
            comment = createNotify(data);

        addChatInContainer(comment)
    })
}


export function addChatInContainer(message) {
    container.append(message);
    container.scrollTop = container.scrollHeight;
}

export function enableMessageContainer() {
    clearMessageContainer();
    enableMessageInput();

    sendBtn.addEventListener("click", sendMsg);
}

export function disableMessageContainer() {
    clearMessageContainer();
    disableMessageInput();

    sendBtn.removeEventListener("click", sendMsg);
}

export function setRoomNameInChatContainer(rid) {
    headerName.textContent = rid;
}



// 메시지 전송.
export function sendMsg(e) {
    const message = messageInput.value;
    if (message.length > 300) {
        alert('300자 이하.')
        return;
    }

    const rid = getCurrentRoomId();
    const random = getSubscribeInMap(rid);

    const headers = {
        "id": `msg-subline.${rid}.${random}`
    }

    const data = { // UserDTO, // ChatDTO
        "content": message
    }

    stompSendMessage(getCurrentRoomId(), headers, JSON.stringify(data));
}


export function createComment(data) {
    const isOutgoing = (data.user_id === getUsername());

    const wrapper = document.createElement("div");
    wrapper.className = isOutgoing ? "message-wrapper outgoing" : "message-wrapper incoming";

    if (!isOutgoing) {
        const avatar = document.createElement("div");
        avatar.className = "message-avatar";
        avatar.style.backgroundImage = `url('https://ui-avatars.com/api/?name=${data.user_id}&background=random')`;
        wrapper.append(avatar);

        const contentWrapper = document.createElement("div");
        contentWrapper.className = "message-content-wrapper";

        const username = document.createElement("div");
        username.className = "message-username";
        username.textContent = data.user_id;
        contentWrapper.append(username);

        const bubble = document.createElement("div");
        bubble.className = "message incoming";

        const content = document.createElement("div");
        content.className = "message-content";
        content.textContent = data.content;
        bubble.append(content);

        const msgTime = document.createElement("span");
        msgTime.className = "message-time";
        msgTime.textContent = data.sendTime;
        bubble.append(msgTime);

        contentWrapper.append(bubble);
        wrapper.append(contentWrapper);
    } else {
        const bubble = document.createElement("div");
        bubble.className = "message outgoing";

        const content = document.createElement("div");
        content.className = "message-content";
        content.textContent = data.content;
        bubble.append(content);

        const msgTime = document.createElement("span");
        msgTime.className = "message-time";
        msgTime.textContent = data.sendTime;
        bubble.append(msgTime);

        wrapper.append(bubble);
    }

    return wrapper;
}



export function createNotify(data) {
    const div = document.createElement("div");
    div.textContent = data.content;

    Object.assign(div.style, {
        backgroundColor: "#e0e0e0", // 회색 배경
        color: "#3C1E1E",
        padding: "10px 14px",
        borderRadius: "12px",
        boxShadow: "0 2px 4px rgba(0,0,0,0.1)",
        fontFamily: "Arial, sans-serif",
        fontSize: "14px",
        maxWidth: "70%",
        wordBreak: "break-word",
        alignSelf: "center", // 메시지 div 자체를 중앙으로
        textAlign: "center", // 텍스트 중앙
    });

    return div;
}


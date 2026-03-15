// 구독 라인으로 수신한 메시지에 대한 핸들러.
// 채팅방 입장 시 기본적으로 아래 3라인을 구독한다.
// /topic/msg/room/{rid}       메시지 수신용 라인.
// /topic/notify/room/{rid}    이벤트 수신용 라인. ( 사용자 참가, 탈퇴 등 )
// /topic/error/user/{uid}     에러 수신용 라인. ( 왜 만들었지? )
import { addChatInContainer, createComment, createNotify } from "./index/chat_container.js";
import { getCurrentRoomId } from "./storage.js";
import {addUnreadCount } from "./index/chatroom_list.js";

// 메시지 수신 라인 receive 핸들러.
// 사용자가 등록하는 모든 subscribe 의 수신핸들러는 얘 하나로 등록되어있다.
export function onReceiveMsg(msg) {
    const json = JSON.parse(msg.body)

    if (json.room_id === getCurrentRoomId()) { // 현재 내가 보고있는 방에서 온 메시지라면.
        const comment = createComment(json);

        addChatInContainer(comment)
    } else { // 현재 내가 보고있는 방이 아닌 곳에서 온 메시지라면.. 옆에 표시.
        addUnreadCount(json.room_id);
    }

}


// 이벤트 수신 라인 receive 핸들러.
export function onReceiveNotify(msg) {
    const json = JSON.parse(msg.body)

    const comment = createNotify(json);

    addChatInContainer(comment)
}


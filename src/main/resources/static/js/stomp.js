import {
    addSubscribeInMap,
    getSubscribeInMap,
    isAlreadySubscribeInMap,
    removeAllSubscribeInMap,
    removeSubscribeInMap
} from "./storage.js";
import {onReceiveMsg, onReceiveNotify} from "./message_receive_handler.js";
import {subscribeAllJoiningRoom} from "./index/chatroom_list.js";

let socket = null;
let clientStomp = null;

document.addEventListener("DOMContentLoaded", () => {
    socket = new SockJS("/ws");
    clientStomp = Stomp.over(socket);

    clientStomp.connect({}, onConnected, onError);

});

export function stompSendMessage(rid, header, data) {
    clientStomp.send(`/app/msg/room/${rid}`, header, data);
}


function onConnected() {
    console.log("connected")
    subscribeAllJoiningRoom();
}


function onError() {
    removeAllSubscribeInMap();
}



export function subscribeRoom(rid, username) {
    const headers = {
        "id": "msg-subline"
    }

    if (isAlreadySubscribeInMap(rid))
        return;

    const random = crypto.randomUUID();

    addSubscribeInMap(rid, random);

    headers.id = `msg-subline.${rid}.${random}`
    clientStomp.subscribe(`/topic/msg/room/${rid}`, onReceiveMsg, headers);

    headers.id = `notify-subline.${rid}.${random}`
    clientStomp.subscribe(`/topic/notify/room/${rid}`, onReceiveNotify, headers);
}

export function unsubscribeRoom(rid) {
    if (!rid) {
        throw new Error("unsubscribeRoom: rid must not be null");
    }

    const random = getSubscribeInMap(rid);
    if (!random) {
        throw new Error("unsubscribeRoom: rid not found, subList");
    }

    removeSubscribeInMap(rid)

    clientStomp.unsubscribe(`msg-subline.${rid}.${random}`);
    clientStomp.unsubscribe(`notify-subline.${rid}.${random}`);
    clientStomp.unsubscribe(`error-subline.${rid}.${random}`);
}



import {sendAxios} from "./axios.js";


// 내 정보 반환.
export async function getMyInfoAPI() {
    try {
        return await sendAxios('get', `/api/user/`);
    } catch (e) {
        const errMsg =  e.response.data;
        console.log("getMyInfoAPI Failed: "+ errMsg);
        throw errMsg;
    }
}

// 특정 채팅방 정보 반환.
export async function getRoomInfoAPI(rid) {
    try {
        return await sendAxios('get', `/api/chatroom/${rid}`);
    } catch (e) {
        const errMsg =  e.response.data;
        console.log("getRoomInfoAPI Failed: "+ errMsg);
        throw errMsg;
    }
}

// 현재 참여중인 채팅룸 반환.
export async function getJoiningRoomAPI() {
    try {
        return await sendAxios("get", "/api/user/chatroom");

    } catch (e) {
        const errMsg =  e.response.data;
        console.log("getJoiningRoomAPI Failed: "+ errMsg);
        throw errMsg;
    }
}

// 모든 채팅룸 얻어오기.
export async function getAllChatroomAPI() {
    try {
        return await sendAxios("get", '/public/chatroom/all');
    } catch (e) {
        const errMsg =  e.response.data;
        console.log("getAllChatroomAPI err: ", errMsg);
        throw errMsg
    }
}

export async function verifyCaptchaAPI(username, captchaString) {
    const data = {
        "username":captchaTargetUsername.value,
        "captcha": captchaString
    };

    try {
        return await sendAxios("POST", '/captcha', data);
    } catch (e) {
        const errMsg =  e.response.data;
        console.log("verifyCaptchaAPI err: ", errMsg);
        throw errMsg
    }
}



export async function verifyLoginAPI(username, password) {
    const headers = {
        'Content-Type': 'application/x-www-form-urlencoded',
    }

    const data = {
        'username': username,
        'password': password
    }

    try {
        return await sendAxios("POST", '/login', data, headers);
    } catch (e) {
        const errMsg =  e.response.data;
        console.log(typeof(errMsg))

        console.log("verifyCaptchaAPI err: ", errMsg);
        throw errMsg
    }
}


// 채팅방 생성 요청.
export async function createChatroomAPI(rid) {
    const data = {
        "rid": rid
    };

    try {
        await sendAxios("post", `/api/chatroom/${rid}`, data)

    } catch(e) {
        const errMsg = e.response.data
        console.log("createChatRoom err: " + errMsg);
        throw errMsg;
    }
}



// 채팅방 클릭 시 join.
export async function sendJoinChatAPI(rid) {
    try {
        // return: chat History.
        return await sendAxios("post", `/api/chatroom/${rid}/member`);
    } catch (e) {
        const errMsg = e.response.data
        console.log("sendJoinChatAPI err: " + errMsg);
        throw errMsg;
    }
}


export async function deleteRoomAPI(rid) {
    try {
        await sendAxios("delete", `/api/chatroom/${rid}`)
    }  catch (e) {
        const errMsg = e.response.data
        console.log("deleteRoomAPI err: " + errMsg);
        throw errMsg;
    }
}

export async function leaveRoomAPI(rid) {
    try {
        await sendAxios("delete", `/api/chatroom/${rid}/member`)
    }  catch (e) {
        const errMsg = e.response.data
        console.log("leaveRoomAPI err: " + errMsg);
        throw errMsg;
    }
}



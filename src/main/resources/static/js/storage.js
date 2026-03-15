let curRid = "";
const subMap = new Map();

export function addSubscribeInMap(rid, random) {
    subMap.set(rid, random);
}

export function removeSubscribeInMap(rid) {
    subMap.delete(rid);
}

export function removeAllSubscribeInMap() {
    subMap.clear();
}

export function getSubscribeInMap(rid) {
    return subMap.get(rid);
}

export function isAlreadySubscribeInMap(rid) {
    return subMap.has(rid);
}

export function getCurrentRoomId() {
    return curRid;
}

export function setCurrentRoomId(rid) {
    curRid = rid;
}

export function setUsername(id) {
    localStorage.setItem('username', id);
}

export function getUsername() {
    const username =  localStorage.getItem('username');
    if (!username || username === "") {
        throw new Error("login first");
    }

    return username;
}

export function removeFromLocalStorage(data) {
    localStorage.removeItem(data);
}
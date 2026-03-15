import {getCurrentRoomId} from "../storage.js";
import {getRoomInfoAPI} from "../api.js";
import {closeDrawer, showDrawer} from "../util.js";

let roomDetailDrawer = null;

document.addEventListener('DOMContentLoaded', () => {
    const menuBtn = document.querySelector('#menuBtn');
    roomDetailDrawer = document.querySelector('#chatroom_detail_drawer')
    const joinerContainer = roomDetailDrawer.querySelector('#drawer_joiner_list')

    menuBtn.addEventListener('click', async (e) => {
        clearJoinerContainer();

        const rid = getCurrentRoomId();
        if (!rid) {
            throw new Error("rid null");
        }


        const roomInfo = await getRoomInfoAPI(rid);

        roomInfo.joinerList.forEach(joinerInfo => {
            const joinerTag = createJoinerTag(joinerInfo);

            appendJoinerInContainer(joinerTag);
        })

        showDrawer(roomDetailDrawer);
    })

    function appendJoinerInContainer(joinerTag) {
        joinerContainer.append(joinerTag);
    }

    function clearJoinerContainer() {
        joinerContainer.innerHTML = '';
    }

    function createJoinerTag(joinerInfo) {
        const div = document.createElement('div');
        div.className = 'drawer-joiner-item';

        const divJoinerImg = document.createElement('div');
        divJoinerImg.className = 'drawer-joiner-img';

        const joinerNameSpan = document.createElement('span');
        joinerNameSpan.className = 'drawer-joiner-name';
        joinerNameSpan.textContent = joinerInfo.id;


        div.append(divJoinerImg);
        div.append(joinerNameSpan);

        return div;
    }

})

export function closeRoomDetailDrawer() {
    closeDrawer(roomDetailDrawer);
}
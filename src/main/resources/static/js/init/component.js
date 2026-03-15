// modal, drawer 같은 기본적인 동작에 대한 이벤트를 추가한다.
import {closeDrawer, closeModal} from "../util.js";

document.addEventListener('DOMContentLoaded', () => {
    addModalEvent();
    addDrawerEvent();
})




/*
    class: drawer
    require. {
        .drawer_close: 닫기버튼.
    }
*/
function addDrawerEvent() {
    const drawers = document.querySelectorAll('.drawer')
    const backdrop = document.querySelector('.backdrop');

    drawers.forEach(drawer => {
        const closeBtn= drawer.querySelector('.drawer_close')

        if (closeBtn) {
            closeBtn.addEventListener('click', () => {
                closeDrawer(drawer);
            })
        }

        backdrop.addEventListener('click', e => {
            closeDrawer(drawer)
        })
    })
}



/*
    class: modal
    require. {
        .modal_close: 닫기버튼.
    }
*/
function addModalEvent() {
    const modals = document.querySelectorAll('.modal')

    modals.forEach(modal => {
        const closeBtn = modal.querySelector('.modal_close');

        if (closeBtn) {
            closeBtn.addEventListener('click', () => {
                closeModal(modal)
            });
        }

        modal.addEventListener('click', (e) => {
            if (e.target === modal) {
                closeModal(modal);
            }
        })
    })

}
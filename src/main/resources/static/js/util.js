const backdrop = document.querySelector('.backdrop');

export function closeModal(modal) {
    modal.classList.remove('show');
}

export function showModal(modal) {
    modal.classList.add('show');
}

export function showError(errField, errMsg) {
    errField.textContent = errMsg;
    errField.hidden = false;
}

export function hideError(errField) {
    errField.hidden = true;
    errField.textContent = ""
}

export function showDrawer(drawer) {
    drawer.classList.add('show');
    backdrop.classList.add('show');
}

export function closeDrawer(drawer) {
    drawer.classList.remove('show');
    backdrop.classList.remove('show');
}
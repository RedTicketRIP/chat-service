
export function parseAndShowError(err, errField) {
    if (!err) {
        throw Error("parseAndShowError::err null");
    }

    const errCode = err.errCode

    errField.textContent = err.detail.join(' ');
    errField.hidden = false;
}

export function isRequireCaptcha(err) {
    if (!err) {
        throw Error("isRequireCaptcha::err null");
    }

    return (err.errCode === 'CAPTCHA_REQUIRED');
}
const axiosInstance = axios.create()

export async function sendAxios(method, url, data, headers) {
    const response = await axiosInstance({
        method: method,
        url: url,
        data: data,
        headers: headers
    });

    return response.data;
}

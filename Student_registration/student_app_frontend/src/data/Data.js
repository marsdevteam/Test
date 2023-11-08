
export function universities() {
    const res = fetch('http://localhost:8080/api/universities', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': JSON.parse(localStorage.getItem('currentUser')).access_token
        }
    })
    return res;
}

export function courses() {
    const res = fetch('http://localhost:8080/api/courses', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': JSON.parse(localStorage.getItem('currentUser')).access_token
        }
    })
    return res
}
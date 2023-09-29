// Cookie
const cookieArr = document.cookie.split("=")
const userId = cookieArr[1];

// DOM Elements
const submitForm = document.getElementById("loadout-form")
const loadoutContainer = document.getElementById("loadout-container")

// Modal Elements
let loadoutBody = document.getElementById(`loadout-body`)
let updateLoadoutBtn = document.getElementById('update-loadout-button')

const headers = {
    'Content-Type': 'application/json'
}

const baseUrl = "http://localhost:8080/api/v1/loadouts"

function handleLogout(){
    let c = document.cookie.split(";");
    for(let i in c){
        document.cookie = /^[^=]+/.exec(c[i])[0]+"=;expires=Thu, 01 Jan 1970 00:00:00 GMT"
    }
}

const handleSubmit = async (e) => {
    e.preventDefault()
    let bodyObj = {
        body: document.getElementById("loadout-input").value
    }
    await addLoadout(bodyObj);
    document.getElementById("loadout-input").value = ''
}

async function addLoadout(obj) {
    const response = await fetch(`${baseUrl}user/${userId}`, {
        method: "POST",
        body: JSON.stringify(obj),
        headers: headers
    })
        .catch(err => console.error(err.message))
    if (response.status == 200) {
        return getLoadouts(userId);
    }
}

async function getLoadouts(userId) {
    await fetch(`${baseUrl}user/${userId}`, {
        method: "GET",
        headers: headers
    })
        .then(response => response.json())
        .then(data => createLoadoutCards(data))
        .catch(err => console.error(err))
}

async function getLoadoutById(loadoutId){
    await fetch(baseUrl + loadoutId, {
        method: "GET",
        headers: headers
    })
        .then(res => res.json())
        .then(data => populateModal(data))
        .catch(err => console.error(err.message))
}

async function handleLoadoutEdit(loadoutId) {
    let bodyObj = {
        id: loadoutId,
        body: loadoutBody.value
    }

    await fetch(baseUrl, {
        method: "PUT",
        body: JSON.stringify(bodyObj),
        headers: headers
    })
        .catch(err => console.error(err))

    return getLoadouts(userId);
}

async function handleDelete(loadoutId){
    await fetch(baseUrl + loadoutId, {
        method: "DELETE",
        headers: headers
    })
        .catch(err => console.error(err))

    return getLoadouts(userId);
}

const createLoadoutCards = (array) => {
    loadoutContainer.innerHTML = ''
    array.forEach(obj => {
        let loadoutCard = document.createElement("div")
        loadoutCard.classList.add("m-2")
        loadoutCard.innerHTML = `
            <div class="card d-flex" style="width: 18rem; height: 18 rem;">
                <div class="card-body d-flex flex-column justify-content-between" style="height: available">
                    <p class="card-text">${obj.summary}</p>
                    <div class="d-flex justify-content-between">
                        <button class="btn btn-danger" onclick="handleDelete(${obj.id})">Delete</button>
                        <button onclick="getLoadoutById(${obj.id})" type="button" class="btn btn-primary"
                        data-bs-toggle="modal" data-bs-target="#loadout-edit-modal">
                        Edit
                        </button>
                    </div>
                </div>
            </div>
        `
        loadoutContainer.append(loadoutCard);
    })
}

const populateModal = (obj) => {
    loadoutBody.innerText = ''
    loadoutBody.innerText = obj.summary
    updateLoadoutBtn.setAttribute('data-loadout-id', obj.id)
}

getLoadouts(userId);

submitForm.addEventListener("submit", handleSubmit)

updateLoadoutBtn.addEventListener("click", (e) => {
    let loadoutId = e.target.getAttribute('data-loadout-id')
    handleLoadoutEdit(loadoutId);
})

<a class="btn btn-danger navbar-btn" href="./login.html" onclick="handleLogout()">Logout</a>
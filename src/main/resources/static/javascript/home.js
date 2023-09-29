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

// Function to fetch and display loadouts
// Fetch and display loadouts
fetch('/api/v1/loadouts')
    .then(response => response.json())
    .then(data => {
        const loadoutsContainer = document.getElementById('loadouts-container');

        data.forEach(loadout => {
            const loadoutDiv = document.createElement('div');
            loadoutDiv.classList.add('loadout');

            // Display loadout name
//            const loadoutName = `<h2>${loadout.name}</h2>`;

            // Add editable fields for buildType and summary
            const buildTypeInput = `<input type="text" value="${loadout.buildType || ''}" placeholder="Build Type">`;
            const summaryTextarea = `<textarea rows="4" placeholder="Summary">${loadout.summary || ''}</textarea>`;

            // Create a container for the perk icons
            const perkIconsContainer = document.createElement('div');

            // Display perk icons (1 to 4 perks)
            loadout.perksIds.forEach(perk => {
                const perkIcon = document.createElement('img');
                perkIcon.src = perk.image;
                perkIcon.alt = perk.name;
                perkIconsContainer.appendChild(perkIcon);
            });

            // Append all elements to the loadout container
            loadoutDiv.innerHTML = buildTypeInput + summaryTextarea;
            loadoutDiv.appendChild(perkIconsContainer);
            loadoutsContainer.appendChild(loadoutDiv);
        });
    })
    .catch(error => {
        console.error('Error:', error);
    });


// Function to send a PUT request to update a loadout
function updateLoadout(loadoutId, buildType, summary) {
    // Implement your PUT request logic here
    // You can use fetch or another library (e.g., axios) to send the request
    // Include the loadoutId, buildType, and summary in the request body
    // Send the request to your backend API endpoint for updating loadouts
}

// Fetch and display loadouts when the page loads
//window.addEventListener('load', fetchLoadouts);




async function handleDelete(loadoutId){
    await fetch(baseUrl + loadoutId, {
        method: "DELETE",
        headers: headers
    })
        .catch(err => console.error(err))

    return getLoadouts(userId);
}
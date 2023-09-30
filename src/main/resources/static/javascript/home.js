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
function fetchLoadouts() {
    fetch('/api/v1/loadouts')
    .then(response => response.json())
    .then(data => {
        const loadoutsContainer = document.getElementById('loadouts-container');

        data.forEach(loadout => {
            const loadoutDiv = document.createElement('div');
            loadoutDiv.classList.add('loadout');

            // Set the loadout ID as a data attribute to grab later for updating
            loadoutDiv.setAttribute('data-loadout-id', loadout.id);

            // Create the header for each loadout
            // Header includes the Build Type (aka loadout name) on far left and Edit button far right)
            // Edit button toggles off to have Save or Cancel appear during Edit mode
            const headerDiv = document.createElement('div');
            headerDiv.classList.add('loadout-header');

            // Header Part 1: Build Type (aka the Loadout Name)
            const buildTypeInput = `<input type="text" value="${loadout.buildType || ''}" placeholder="Build Type">`;
            const buildTypeInputEl = document.createElement('div');
            buildTypeInputEl.innerHTML = buildTypeInput;
            buildTypeInputEl.querySelector('input').classList.add('build-type-input');
            buildTypeInputEl.querySelector('input').disabled = true;

            // Header Part 2: Edit and Save buttons
            const editButton = document.createElement('button');
            editButton.textContent = 'Edit';
            editButton.classList.add('edit-button');
            editButton.addEventListener('click', () => toggleEditMode(loadoutDiv));

            const saveButton = document.createElement('button');
            saveButton.textContent = 'Save';
            saveButton.classList.add('save-button');
            saveButton.style.display = 'none'; // Initially hidden
            saveButton.addEventListener('click', () => saveChanges(loadoutDiv));

            // Append the loadout name and Edit/Save buttons to the header
            headerDiv.appendChild(buildTypeInputEl);
            headerDiv.appendChild(editButton);
            headerDiv.appendChild(saveButton);

            // Create the loadout content container
            const contentDiv = document.createElement('div');
            contentDiv.classList.add('loadout-content');

            // Add editable field for summary
            const summaryTextarea = `<textarea rows="4" placeholder="Summary">${loadout.summary || ''}</textarea>`;
            const summaryTextareaEl = document.createElement('div');
            summaryTextareaEl.innerHTML = summaryTextarea;
            summaryTextareaEl.querySelector('textarea').classList.add('summary-textarea');
            summaryTextareaEl.querySelector('textarea').disabled = true;

            // Create a container for the perk icons
            const perkIconsContainer = document.createElement('div');
            perkIconsContainer.classList.add('perk-icons-container');

            // Display perk icons
            loadout.perksIds.forEach(perkId => {
                // Fetches the perk object by ID using the getPerkById function
                fetch(`/api/v1/perks/${perkId}`)
                .then(response => response.json())
                .then(perk => {
                    const perkFigure = document.createElement('figure');
                    perkFigure.classList.add('perk-figure');

                    const perkIcon = document.createElement('img');
                    perkIcon.src = perk.image;
                    perkIcon.alt = perk.name;
                    perkFigure.appendChild(perkIcon);

                    // To add a the perk name as a caption to the perk Figure
                    const perkCaption = document.createElement('figcaption');
                    perkCaption.textContent = perk.name;
                    perkFigure.appendChild(perkCaption);

                    perkIconsContainer.appendChild(perkFigure);
                })
                .catch(error => {
                    console.error('Error fetching perk:', error);
                });
            });

            // Append perk icons container and loadout details to loadout content
            contentDiv.appendChild(perkIconsContainer);
            contentDiv.appendChild(summaryTextareaEl);

            // Append the header and content to construct a single complete loadout
            loadoutDiv.appendChild(headerDiv);
            loadoutDiv.appendChild(contentDiv);

            // Now actually add all the generated loadouts to the main loadouts container
            // So they actually appear
            loadoutsContainer.appendChild(loadoutDiv);
        });
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

// Function to toggle edit mode for a specific loadout
function toggleEditMode(loadoutDiv) {
    const buildTypeInput = loadoutDiv.querySelector('.build-type-input');
    const summaryTextarea = loadoutDiv.querySelector('.summary-textarea');
    const editButton = loadoutDiv.querySelector('.edit-button');
    const saveButton = loadoutDiv.querySelector('.save-button');

    buildTypeInput.disabled = !buildTypeInput.disabled;
    summaryTextarea.disabled = !summaryTextarea.disabled;

    // Toggle the text on the edit button based on edit mode
    if (buildTypeInput.disabled) {
        editButton.textContent = 'Edit';
    } else {
        editButton.textContent = 'Cancel'; // You can change this to "Cancel" if needed
    }

    // Show/hide the save button
    saveButton.style.display = buildTypeInput.disabled ? 'none' : 'block';
}

// Function to save changes made in edit mode
function saveChanges(loadoutDiv) {
    // Get the modified buildType and summary values
    const loadoutId = loadoutDiv.getAttribute('data-loadout-id');
    const buildTypeInput = loadoutDiv.querySelector('.build-type-input');
    const summaryTextarea = loadoutDiv.querySelector('.summary-textarea');
    const newBuildType = buildTypeInput.value;
    const newSummary = summaryTextarea.value;

    // Send PUT request that updates loadout with new data
    updateLoadout(loadoutId, newBuildType, newSummary);

    // After saving, toggle back to view mode
    toggleEditMode(loadoutDiv);
}


// Function to send a PUT request to update a loadout
function updateLoadout(loadoutId, buildType, summary) {
    // Create payload with the updated data
    const updatedData = {
        id: loadoutId,
        buildType: buildType,
        summary: summary,
        user: userId,
    };

    // PUT request to update the loadout
    fetch(`/api/v1/loadouts/${loadoutId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(updatedData),
    })
    .then(response => {
        if (response.ok) {
            alert('Loadout saved successfully.');
            // Update UI to reflect changes (e.g., update the display)
            // May need to reload loadouts or update specific loadout element
        } else {
            console.error('Update failed.');
        }
    })
    .catch(error => {
        console.error('Error', error);
    });
}

// Fetch and display loadouts when the page loads
window.addEventListener('load', fetchLoadouts);

async function handleDelete(loadoutId){
    await fetch(baseUrl + loadoutId, {
        method: "DELETE",
        headers: headers
    })
        .catch(err => console.error(err))

    return getLoadouts(userId);
}
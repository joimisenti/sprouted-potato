// Maintain cookie tracking of user and user-related perk submissions
// Cookie
const cookieArr = document.cookie.split("=")
const userId = cookieArr[1];

// An array to store selected perks
const selectedPerks = [];

// Function to fetch perks from Spring Boot API
function fetchPerks() {
    fetch('/api/v1/perks')
    .then(response => response.json())
    .then(data => {
        console.log(data);
        // Loop through the perks to create HTML elements
        data.forEach(perk => {
            const perkItem = document.createElement('div');
            perkItem.classList.add('perk-item');

            const perkIcon = document.createElement('img');
            perkIcon.src = perk.image;
            perkItem.appendChild(perkIcon);

            const perkName = document.createElement('p');
            perkName.textContent = perk.name;
            perkItem.appendChild(perkName);

            const selectButton = document.createElement('button');
            selectButton.classList.add('btn', 'btn-outline-warning');
            selectButton.textContent = 'Select';

            // Event listeners to handle perk selection
                selectButton.addEventListener('click', () => {
                    if (selectedPerks.length < 4) {
                        // Check if the perk is not already in the selectedPerks list
                        if (!selectedPerks.some(selectedPerk => selectedPerk.id === perk.id)) {
                            selectedPerks.push(perk);
                            updateSelectedPerks();
                        } else {
                            alert('This perk is already selected.');
                        }
                    } else {
                        // Handle case where maximum perks are selected
                        alert('You can select up to 4 perks.')
                    }
                });

            perkItem.appendChild(selectButton);

            document.querySelector('.perk-grid').appendChild(perkItem);
        });
    });
}

// Function to toggle the selection of a perk
function updateSelectedPerks() {
    // Implement logic allowing selection of up to 4 perks
    // Add or remove the perk from the "Selected Perks" list
    const selectedPerksList = document.getElementById('selected-perks-list');
    selectedPerksList.innerHTML = ''; // Clear the previous selections

    // Loop through selected perks and create list items
    selectedPerks.forEach(perk => {
        const listItem = document.createElement('li');
        listItem.classList.add('list-group-item', 'd-flex', 'justify-content-between', 'align-items-center')
        listItem.textContent = perk.name;

        const listIcon = document.createElement('img');
        listIcon.src = perk.image;
        listItem.appendChild(listIcon);

        // Add a button to remove the perk from the selection
        const removeButton = document.createElement('button');
        removeButton.textContent = 'Remove';
        removeButton.classList.add('btn', 'btn-outline-secondary', 'btn-sm');
        removeButton.addEventListener('click', () => {
            selectedPerks.splice(selectedPerks.indexOf(perk), 1);
            updateSelectedPerks();
        });

        listItem.appendChild(removeButton);
        selectedPerksList.appendChild(listItem);
        console.log('Selected Perks: ', selectedPerks);
        const perkIds = selectedPerks.map(perk => perk.id);
        console.log('Extracted Perk IDs:', perkIds);
    });
}

// Function to submit perks to Loadouts table
function handleSubmitPerksToLoadout() {

    // Check if no perks are selected
    if (selectedPerks.length === 0) {
        alert('You must select at least 1 perk.');
        return; // Prevents further execution
    }
    // Create an array of selected perk IDs
    const selectedPerkIds = selectedPerks.map(perk => perk.id);
    console.log(selectedPerkIds)

    // Create a Loadout DTO to send to the backend
    const loadoutDto = {
        id: userId,
        buildType: "",
        perksIds: selectedPerkIds
    };

    const temp = JSON.stringify(loadoutDto);
    console.log(temp);
    // Send the data to the backend
    fetch('/api/v1/loadouts/create', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(loadoutDto)
    })
    .then(response => {
        if(response.ok) {
            alert('Loadout created successfully.');
            clearSelectedPerks();
        } else {
            alert('Error creating loadout.');
        }
    })
    .catch(error => {
        alert('An error occurred: ' + error.message);
    });
}

// Function to clear the selected perks list
function clearSelectedPerks() {
    selectedPerks.length = 0; // Empty the array of selection
    const selectedPerksList = document.getElementById('selected-perks-list');
    selectedPerksList.innerHTML = ''; // Clear the HTML content from the display
}

// Call fetchPerks function when the page loads
window.addEventListener('load', fetchPerks);

// Add a submit event listener to the form
const perksSubmit = document.getElementById('perks-submit-button');
perksSubmit.addEventListener('click', handleSubmitPerksToLoadout);
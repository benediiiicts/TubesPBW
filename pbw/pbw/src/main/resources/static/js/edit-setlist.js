let removedSongs = [];
let addedSongs = [];
const artistId = /*[[${setlist.artist.id}]]*/ null;

// Handle song removal
document.querySelectorAll('.remove-song').forEach(button => {
    button.addEventListener('click', function() {
        const songId = this.getAttribute('data-song-id');
        removedSongs.push(songId);
        this.closest('.song-item').remove();
        updateHiddenFields();
    });
});

// Search existing songs
const searchInput = document.getElementById('songSearch');
let searchTimeout;

searchInput.addEventListener('input', function() {
    clearTimeout(searchTimeout);
    const query = this.value;
    
    if (query.length < 2) {
        document.getElementById('searchResults').innerHTML = '';
        return;
    }
    document.getElementById('searchResults').style = 'display = block';
    searchTimeout = setTimeout(async () => {
        try {
            const response = await fetch(`/api/setlist/songs/search?query=${encodeURIComponent(query.toLowerCase())}`);
            if (!response.ok) throw new Error('Network response was not ok');
            
            const songs = await response.json();
            console.log('Received songs:', songs); // Debug log
            
            const resultsDiv = document.getElementById('searchResults');
            resultsDiv.innerHTML = '';
            
            songs.forEach(song => {
                console.log('Processing song:', song); // Debug log
                const div = document.createElement('div');
                div.className = 'search-result';
                div.textContent = song.title;
                div.onclick = () => addExistingSong({
                    id_song: song.id_song || song.idSongs, // Handle both possible property names
                    title: song.title
                });
                resultsDiv.appendChild(div);
            });
        } catch (error) {
            console.error('Error searching songs:', error);
        }
    }, 300);
});

function addExistingSong(song) {
    console.log('Adding song:', song); // Debug log
    
    if (!song || !song.id_song) {
        console.error('Invalid song data:', song);
        return;
    }

    // Check if song is already in the list
    const isAlreadyAdded = document.querySelector(`.song-item span[data-song-id="${song.id_song}"]`);
    if (isAlreadyAdded) {
        alert('This song is already in the setlist');
        return;
    }

    addedSongs.push({
        id: song.id_song,
        isNew: false
    });
    
    // Add visual feedback
    const currentSongs = document.querySelector('.current-songs');
    const newSongDiv = document.createElement('div');
    newSongDiv.className = 'song-item';
    newSongDiv.innerHTML = `
        <span class="song-title" data-song-id="${song.id_song}">${song.title}</span>
        <button type="button" class="remove-song">Remove</button>
    `;
    
    // Add remove functionality
    const removeButton = newSongDiv.querySelector('.remove-song');
    removeButton.addEventListener('click', function() {
        const index = addedSongs.findIndex(s => s.id === song.id_song);
        if (index > -1) {
            addedSongs.splice(index, 1);
        }
        newSongDiv.remove();
        updateHiddenFields();
    });
    
    currentSongs.appendChild(newSongDiv);
    updateHiddenFields();
    document.getElementById('searchResults').innerHTML = '';
    document.getElementById('songSearch').value = '';
}

function updateHiddenFields() {
    document.getElementById('removedSongs').value = JSON.stringify(removedSongs);
    document.getElementById('addedSongs').value = JSON.stringify(addedSongs);
    console.log('Updated removedSongs:', removedSongs); // Debug log
    console.log('Updated addedSongs:', addedSongs); // Debug log
}

document.getElementById('addNewSong').addEventListener('click', function() {
    const title = document.getElementById('newSongTitle').value;
    if (title) {
        addedSongs.push({ title: title, isNew: true });
        
        // Add visual feedback
        const currentSongs = document.querySelector('.current-songs');
        const newSongDiv = document.createElement('div');
        newSongDiv.className = 'song-item';
        newSongDiv.innerHTML = `
            <span>${title}</span>
            <button type="button" class="remove-song">Remove</button>
        `;
        currentSongs.appendChild(newSongDiv);
        
        // Add remove functionality to new song
        const removeButton = newSongDiv.querySelector('.remove-song');
        removeButton.addEventListener('click', function() {
            const index = addedSongs.findIndex(song => song.title === title);
            if (index > -1) {
                addedSongs.splice(index, 1);
            }
            newSongDiv.remove();
            updateHiddenFields();
        });
        
        updateHiddenFields();
        document.getElementById('newSongTitle').value = '';
    }
});

// Initialize hidden fields with empty arrays
updateHiddenFields();

// Sidebar
function toggleMenu() {
    const sidebar = document.getElementById('sidebar');
    sidebar.classList.toggle('active');
}
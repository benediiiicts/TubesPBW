function searchArtist() {
    const query = document.getElementById('artist-search').value;

    if (query.trim() === '') {
        document.getElementById('artist-dropdown').style.display = 'none';
        return;
    }

    fetch(`/api/setlist/artists/search?query=${encodeURIComponent(query)}`)
        .then(response => response.json())
        .then(data => {
            console.log(data); // For debugging
            const dropdown = document.getElementById('artist-dropdown');
            dropdown.innerHTML = ''; // Clear previous results

            if (data.length === 0) {
                dropdown.innerHTML = '<div>No artists found</div>';
            } else {
                data.forEach(artist => {
                    const item = document.createElement('div');
                    item.textContent = `${artist.name} (${artist.genre})`;
                    item.onclick = () => selectArtist(artist);
                    dropdown.appendChild(item);
                });
            }

            dropdown.style.display = 'block';
        })
        .catch(error => console.error('Error fetching artists:', error));
}

function selectArtist(artist) {
    document.getElementById('artist-search').value = artist.name;
    document.getElementById('selected-artist-id').value = artist.id;
    document.getElementById('artist-dropdown').style.display = 'none';

    console.log(document.getElementById('selected-artist-id').value);
}

function searchSong() {
    const query = document.getElementById('song-search').value;

    if (query.trim() === '') {
        document.getElementById('song-dropdown').style.display = 'none';
        return;
    }

    fetch(`/api/setlist/songs/search?query=${encodeURIComponent(query)}`)
        .then(response => response.json())
        .then(data => {
            console.log(data); // For debugging
            const dropdown = document.getElementById('song-dropdown');
            dropdown.innerHTML = ''; // Clear previous results

            if (data.length === 0) {
                dropdown.innerHTML = '<div>No Song found</div>';
            } else {
                data.forEach(song => {
                    const item = document.createElement('div');
                    // item.textContent = `${song.title} (${song.genre})`;
                    item.textContent = `${song.songTitle} - ${song.artistName}`;
                    item.onclick = () => selectSong(song);
                    dropdown.appendChild(item);
                });
            }

            dropdown.style.display = 'block';
        })
        .catch(error => console.error('Error fetching songs:', error));
}

function selectSong(song) {
    document.getElementById('song-search').value = song.songTitle + ' - ' + song.artistName;
    document.getElementById('selected-song-id').value = song.songId;
    document.getElementById('song-dropdown').style.display = 'none';

    console.log(document.getElementById('selected-song-id').value);
}

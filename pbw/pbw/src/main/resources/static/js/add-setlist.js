function searchArtist() {
    const query = document.getElementById('artist-search').value;

    if (query.trim() === '') {
        document.getElementById('artist-dropdown').style.display = 'none';
        return;
    }

    fetch(`/api/artists/search?query=${encodeURIComponent(query)}`)
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
    document.getElementById('selected-artist-id').value = artist.idArtist;
    document.getElementById('artist-dropdown').style.display = 'none';
}

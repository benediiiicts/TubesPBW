function toggleMenu() {
    const sidebar = document.getElementById('sidebar');
    sidebar.classList.toggle('active');
}

function applyFilters() {
    const country = document.getElementById('countryFilter').value;
    const genre = document.getElementById('genreFilter').value;
    const searchQuery = document.getElementById('searchInput').value;

    const url = `/artists?page=1&size=10&search=${encodeURIComponent(searchQuery)}&country=${encodeURIComponent(country)}&genre=${encodeURIComponent(genre)}`;
    window.location.href = url;
}


document.addEventListener("DOMContentLoaded", () => {
    const countryFilter = document.getElementById('countryFilter');
    const genreFilter = document.getElementById('genreFilter');
    const searchInput = document.getElementById('searchInput');

    // Fungsi untuk mengupdate URL berdasarkan filter
    function updateURL() {
        const country = countryFilter.value;
        const genre = genreFilter.value;
        const search = searchInput.value;

        const params = new URLSearchParams({ country, genre, search });
        window.location.href = `/artists?${params.toString()}`;
    }

    // Event listener untuk filter country dan genre
    countryFilter.addEventListener('change', updateURL);
    genreFilter.addEventListener('change', updateURL);

    // Event listener untuk search ketika menekan tombol enter
    searchInput.addEventListener('keypress', (e) => {
        if (e.key === 'Enter') {
            updateURL();
        }
    });
});
// Sidebar
function toggleMenu() {
    const sidebar = document.getElementById('sidebar');
    sidebar.classList.toggle('active');
}

// Slideshow
let slideIndex = new Map();
function showSlides(slide_id) {
    let slides = document.querySelectorAll(`#${slide_id} .slides`);
    if (!slideIndex.has(slide_id)) {
        slideIndex.set(slide_id, 0);
    }
    for (let i = 0; i < slides.length; i++) {
        slides[i].style.display = "none";  
        slides[i].style.opacity = "0";
        slides[i].getElementsByClassName("caption")[0].style.opacity = "0"; 
    }

    let index = slideIndex.get(slide_id);

    slides[index].style.display = "block";  
    setTimeout(() => {
        slides[index].style.opacity = "1";
        slides[index].getElementsByClassName("caption")[0].style.opacity = "1";
    }, 10);
}

let slideIntervals = new Map();

function resetAutoSlide(slide_id, interval) {
    if (slideIntervals.has(slide_id)) {
        clearInterval(slideIntervals.get(slide_id));
    }
    const newInterval = setInterval(() => {
        plusSlides(1, slide_id);
    }, interval);
    slideIntervals.set(slide_id, newInterval);
}

function plusSlides(n, slide_id) {
    let slides = document.querySelectorAll(`#${slide_id} .slides`);
    if (!slideIndex.has(slide_id)) {
        slideIndex.set(slide_id, 0);
    }
    let index = slideIndex.get(slide_id);
    index += n;
    if (index < 0) index = slides.length - 1;
    if (index >= slides.length) { index = 0; }

    slideIndex.set(slide_id, index);
    showSlides(slide_id);
    resetAutoSlide(slide_id, 3000);
}

showSlides('slide-otp');

resetAutoSlide('slide-otp', 3000);

function toggleMenu() {
    const sidebar = document.getElementById('sidebar');
    sidebar.classList.toggle('active');
}

function goToShowDetail(idShow) {
    window.location.href = `/show/detail/${idShow}`;
}

function goToSetlistDetail(idSetlist) {
    window.location.href = `/setlist/detail/${idSetlist}`;
}

function applyFilters() {
    const query = document.getElementById('searchInput').value.trim();
    const resultContainer = document.getElementById('searchResults');

    if (query === "") {
        resultContainer.style.display = "none";
        return;
    }

    fetch(`/api/shows/search?query=${encodeURIComponent(query)}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to fetch search results.");
            }
            return response.json();
        })
        .then(shows => {
            displaySearchResults(shows);
            resultContainer.style.display = "block";
        })
        .catch(error => {
            console.error("Error:", error);
            resultContainer.style.display = "none";
        });
}

function displaySearchResults(shows) {
    const resultContainer = document.getElementById('searchResults');
    resultContainer.innerHTML = ""; // Clear previous results

    if (shows.length === 0) {
        resultContainer.innerHTML = "<p style='justify-self: center;'>No shows found.</p>";
        return;
    }

    const showContainer = document.createElement("div");
    showContainer.className = "show-container";

    const header = document.createElement("h2");
    header.className = "container-header";
    header.textContent = "Search Results";
    showContainer.appendChild(header);

    const gridContainer = document.createElement("div");

    shows.forEach(show => {
        const showGrid = document.createElement("div");
        showGrid.className = "show-grid-res";

        // Set data-id dynamically
        showGrid.setAttribute("data-id", show.idShow);

        showGrid.innerHTML = `
            <p class="show-title">${show.showName}</p>
            <p class="show-date">${show.date}</p>
            <p class="show-description">${show.description}</p>
        `;

        // Add click event listener for navigation
        showGrid.addEventListener("click", () => {
            const showId = showGrid.getAttribute("data-id");
            if (showId) {
                window.location.href = `/show/detail/${showId}`;
            }
        });

        gridContainer.appendChild(showGrid);
    });

    showContainer.appendChild(gridContainer);
    resultContainer.appendChild(showContainer);
}

document.addEventListener('DOMContentLoaded', () => {
    const showGrids = document.querySelectorAll('.show-grid');

    showGrids.forEach(grid => {
        grid.addEventListener('click', () => {
            const showId = grid.getAttribute('data-id');
            if (showId) {
                window.location.href = `/show/detail/${showId}`;
            }
        });
    });
});


//sidebar
function toggleMenu() {
    const sidebar = document.getElementById('sidebar');
    sidebar.classList.toggle('active');
}

//slide show
let slideIndex = 0;

function plusSlides(n, slideshowId) {
    const slides = document.querySelectorAll(`#${slideshowId} .slides`);
    slideIndex += n;
    if (slideIndex >= slides.length) {
        slideIndex = 0;
    } else if (slideIndex < 0) {
        slideIndex = slides.length - 1;
    }
    slides.forEach((slide) => {
        slide.style.display = "none";
    });
    slides[slideIndex].style.display = "block";
}

function showSlides(slideshowId) {
    const slides = document.querySelectorAll(`#${slideshowId} .slides`);
    const delay = 5000; // Waktu tunggu antara slide (dalam milidetik)

    slides[0].style.display = "block";

    setInterval(() => {
        plusSlides(1, slideshowId);
    }, delay);
}

showSlides('slide-rec');
showSlides('slide-otp');
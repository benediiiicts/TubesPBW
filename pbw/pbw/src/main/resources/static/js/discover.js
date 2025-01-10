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

showSlides('slide-rec');
showSlides('slide-otp');

resetAutoSlide('slide-rec', 3000);
resetAutoSlide('slide-otp', 3000);

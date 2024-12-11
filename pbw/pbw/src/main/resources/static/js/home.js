//sidebar
function toggleMenu() {
    const sidebar = document.getElementById('sidebar');
    sidebar.classList.toggle('active');
}

//slideshow
let slideIndex = new Map();
function showSlides(slide_id) {
    let slides = document.querySelectorAll(`#${slide_id} .slides`);
    if(!slideIndex.has(slide_id)){
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
function plusSlides(n, slide_id) {
    let slides = document.querySelectorAll(`#${slide_id} .slides`);
    if(!slideIndex.has(slide_id)){
        slideIndex.set(slide_id, 0);
    }
    let index = slideIndex.get(slide_id);
    index += n;
    if(index < 0) index = slides.length-1;
    if (index >= slides.length) { index = 0; }

    slideIndex.set(slide_id, index);
    showSlides(slide_id);
}
showSlides('slide-rec');
showSlides('slide-otp');
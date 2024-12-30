function validateForm() {
    const fileInput = document.getElementById('artist-picture');
    if (!fileInput.files || fileInput.files.length === 0) {
        alert('Please select an image file');
        return false;
    }
    return true;
}

function previewImage(event) {
    const file = event.target.files[0];
    const preview = document.getElementById('image-preview');
    
    if (file) {
        const reader = new FileReader();
        reader.onload = function(e) {
            preview.src = e.target.result;
            preview.style.display = 'block';
        }
        reader.readAsDataURL(file);
    } else {
        preview.src = '';
        preview.style.display = 'none';
    }
}
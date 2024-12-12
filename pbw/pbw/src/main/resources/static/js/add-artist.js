function previewImage(event) {
    const imagePreview = document.getElementById('image-preview');
    const file = event.target.files[0];
    const button = document.getElementsByClassName('file-upload-btn');

    if (file) {

        const reader = new FileReader();
        reader.onload = function(e) {
            imagePreview.src = e.target.result; // Set the preview image source
            imagePreview.style.display = 'block'; // Show the preview image
        }
        reader.readAsDataURL(file); // Convert the file to a data URL
    } else {
        imagePreview.src = ''; // Clear the preview if no file is selected
        imagePreview.style.display = 'none'; // Hide the preview image
    }
}

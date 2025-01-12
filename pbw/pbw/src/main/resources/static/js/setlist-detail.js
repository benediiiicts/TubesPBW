document.addEventListener('DOMContentLoaded', function() {
    const commentForm = document.querySelector('.comment-input-container form');
    const commentInput = document.getElementById('comment-input');
    const commentsContainer = document.getElementById('comments-container');
    const setlistId = new URLSearchParams(window.location.search).get('setlistId') || 
                      window.location.pathname.split('/').pop();

    // Handle posting new comments
    commentForm.addEventListener('submit', async function(e) {
        e.preventDefault();
        
        const commentText = commentInput.value.trim();
        if (!commentText) return;

        try {
            const response = await fetch('/comments/post', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    comment: commentText,
                    setlistId: parseInt(setlistId)
                })
            });

            const data = await response.json();
            
            if (data.success) {
                // Add new comment to the list
                const commentElement = createCommentElement({
                    id: data.comment.id,
                    commentar: data.comment.commentar,
                    date: data.comment.date,
                    user: {
                        username: data.username
                    }
                });
                commentsContainer.insertBefore(commentElement, commentsContainer.firstChild);
                commentInput.value = ''; // Clear input
            } else {
                alert(data.message || 'Error posting comment');
            }
        } catch (error) {
            console.error('Error:', error);
            alert('Error posting comment');
        }
    });

    // Handle deleting comments
    commentsContainer.addEventListener('click', async function(e) {
        if (e.target.id === 'delete-comment') {
            e.preventDefault();
            
            if (!confirm('Are you sure you want to delete this comment?')) {
                return;
            }

            const commentDiv = e.target.closest('.comment');
            const commentId = commentDiv.dataset.commentId;

            try {
                const response = await fetch(`/comments/delete/${commentId}`, {
                    method: 'POST'
                });

                const data = await response.json();
                
                if (data.success) {
                    commentDiv.remove();
                } else {
                    alert(data.message || 'Error deleting comment');
                }
            } catch (error) {
                console.error('Error:', error);
                alert('Error deleting comment');
            }
        }
    });

    // Helper function to create comment HTML element
    function createCommentElement(comment) {
        const div = document.createElement('div');
        div.className = 'comment';
        div.dataset.commentId = comment.id;
        
        const currentUserId = document.querySelector('meta[name="user-id"]')?.content;
        const showDeleteButton = currentUserId && currentUserId === String(comment.user.id);
        
        div.innerHTML = `
            <div class="comment-header">
                <span class="comment-author">${comment.user.username}</span>
                <span class="comment-date">${comment.date}</span>
            </div>
            <p class="comment-text">${comment.commentar}</p>
            ${showDeleteButton ? `
                <form id="delete-btn-form">
                    <button id="delete-comment" type="button">Delete</button>
                </form>
            ` : ''}
        `;
        
        return div;
    }
});
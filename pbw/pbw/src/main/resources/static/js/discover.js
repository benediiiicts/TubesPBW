function toggleMenu() {
    const sidebar = document.getElementById('sidebar');
    sidebar.classList.toggle('active');
}

// Hapus parameter query dari URL saat halaman di-refresh
if (window.location.search.includes("query=")) {
    const url = new URL(window.location.href);
    url.searchParams.delete("query");
    window.history.replaceState(null, "", url.toString());
}

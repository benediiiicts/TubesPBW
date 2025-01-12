--CREATE TABEL UNTUK MENYIMPAN history perubahan
DROP TABLE IF EXISTS setlist_changes CASCADE;

CREATE TABLE setlist_changes (
    id SERIAL PRIMARY KEY,
    setlist_id INT NOT NULL,
    editor_email VARCHAR(255) NOT NULL,
    change_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    activity TEXT NOT NULL,
    FOREIGN KEY (setlist_id) REFERENCES setlist(idSetlist) ON DELETE CASCADE
);
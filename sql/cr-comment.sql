--CREATE TABEL UNTUK MENYIMPAN KOMENTAR SETLIST
DROP TABLE IF EXISTS comment CASCADE;

CREATE TABLE comment (
    id SERIAL PRIMARY KEY,
    commentar TEXT,
    id_setlist INT NOT NULL,
    id_user INT NOT NULL,
    FOREIGN KEY (id_user) REFERENCES users(id),
    FOREIGN KEY (id_setlist) REFERENCES setlist(idSetlist)
)
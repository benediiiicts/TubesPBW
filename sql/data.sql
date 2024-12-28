DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS artist_show CASCADE;
DROP TABLE IF EXISTS artist CASCADE;
DROP TABLE IF EXISTS countries CASCADE;
DROP TABLE IF EXISTS "show" CASCADE;
DROP TABLE IF EXISTS album CASCADE;
DROP TABLE IF EXISTS songs CASCADE;
DROP TABLE IF EXISTS song_artist CASCADE;
DROP TABLE IF EXISTS setlist CASCADE;

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(60) NOT NULL,
    role VARCHAR(100) DEFAULT 'member'
);

CREATE TABLE countries (
    country_code VARCHAR(3) NOT NULL UNIQUE PRIMARY KEY,
    country_name VARCHAR(100) NOT NULL
);

CREATE TABLE artist (
    idArtist SERIAL PRIMARY KEY,
    PhotosURL TEXT,
    name VARCHAR(255),
    Description TEXT,
    genre VARCHAR(255),
    Year VARCHAR(255),
    Country VARCHAR(255),
    FOREIGN KEY (Country) REFERENCES countries(country_code)
);

CREATE TABLE "show" (
    idShow SERIAL PRIMARY KEY,
    showName VARCHAR(255),
    date DATE,
    venue VARCHAR(255),
    description TEXT
);

CREATE TABLE artist_show (
    idArtist INT NOT NULL,
    idShow INT NOT NULL,
    PRIMARY KEY (idArtist, idShow), -- memastikan tidak ada 2 artist sama dalam 1 show
    FOREIGN KEY (idArtist) REFERENCES artist(idArtist) ON DELETE CASCADE,
    FOREIGN KEY (idShow) REFERENCES "show"(idShow) ON DELETE CASCADE
);

CREATE TABLE album (
    idAlbum SERIAL PRIMARY KEY,
    IdArtist INT NOT NULL,
    FOREIGN KEY (IdArtist) REFERENCES artist(idArtist),
    title VARCHAR(255)
);

CREATE TABLE songs (
    idSongs SERIAL PRIMARY KEY,
    listener BIGINT,
    title VARCHAR(255),
    url TEXT,
    idAlbum INT NOT NULL,
    FOREIGN KEY (idAlbum) REFERENCES album(idAlbum)
);

CREATE TABLE setlist (
    idSetlist SERIAL PRIMARY KEY,
    title VARCHAR(100),
    idShow INT NOT NULL,
    FOREIGN KEY (idShow) REFERENCES "show" (idShow)
);

CREATE TABLE song_artist (
    idSongs INT NOT NULL,
    idArtist INT NOT NULL,
    FOREIGN KEY (idArtist) REFERENCES artist(idArtist),
    FOREIGN KEY (idSongs) REFERENCES songs(idSongs)
);
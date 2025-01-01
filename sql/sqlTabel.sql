DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS artist_show CASCADE;
DROP TABLE IF EXISTS artist CASCADE;
DROP TABLE IF EXISTS countries CASCADE;
DROP TABLE IF EXISTS "show" CASCADE;
DROP TABLE IF EXISTS album CASCADE;
DROP TABLE IF EXISTS songs CASCADE;
DROP TABLE IF EXISTS song_artist CASCADE;
DROP TABLE IF EXISTS setlist CASCADE;
DROP TABLE IF EXISTS "venue" CASCADE;
DROP TABLE IF EXISTS song_setlist CASCADE;


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

CREATE TABLE venue (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255),
    address2 VARCHAR(255),
    city VARCHAR(100) NOT NULL,
    state VARCHAR(100),
    zip VARCHAR(20) NOT NULL,
    geocodable BOOLEAN NOT NULL,
    latitude DECIMAL(9,6) NOT NULL,
    longitude DECIMAL(9,6) NOT NULL,
    website VARCHAR(255),
    phone VARCHAR(20),
    email VARCHAR(100)
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
    description TEXT,
	idVenue int not null,
	foreign key (idVenue) references venue(id)
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

CREATE TABLE song_setlist (
    id_song INT NOT NULL,
    id_setlist INT NOT NULL,
    PRIMARY KEY (id_song, id_setlist), -- Menghindari duplikasi data antara lagu dan setlist
    FOREIGN KEY (id_song) REFERENCES songs(idSongs) ON DELETE CASCADE,
    FOREIGN KEY (id_setlist) REFERENCES setlist(idSetlist) ON DELETE CASCADE
);

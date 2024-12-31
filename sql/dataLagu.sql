-- ALBUM TABLE DATA
INSERT INTO album (IdArtist, title)
VALUES
-- Taylor Swift (idArtist = 56)
(56, '1989'),
(56, 'Red'),
(56, 'Lover'),

-- Beyoncé (idArtist = 57)
(57, 'Dangerously in Love'),
(57, 'B’Day'),
(57, 'Lemonade'),

-- Eminem (idArtist = 58)
(58, 'The Marshall Mathers LP'),
(58, 'Recovery'),
(58, 'The Eminem Show'),

-- Bruce Springsteen (idArtist = 59)
(59, 'Born to Run'),
(59, 'The River'),
(59, 'Born in the U.S.A.'),

-- Lady Gaga (idArtist = 60)
(60, 'The Fame'),
(60, 'Born This Way'),
(60, 'Artpop'),

-- Billie Eilish (idArtist = 61)
(61, 'When We All Fall Asleep, Where Do We Go?'),
(61, 'Happier Than Ever'),

-- Frank Sinatra (idArtist = 62)
(62, 'My Way'),
(62, 'Sinatra at the Sands'),
(62, 'Songs for Swingin’ Lovers!'),

-- Michael Jackson (idArtist = 63)
(63, 'Thriller'),
(63, 'Bad'),
(63, 'Dangerous'),

-- Madonna (idArtist = 64)
(64, 'Like a Virgin'),
(64, 'True Blue'),
(64, 'Like a Prayer'),

-- Prince (idArtist = 65)
(65, 'Purple Rain'),
(65, '1999'),
(65, 'Sign o’ the Times'),

-- Adele (idArtist = 66)
(66, '19'),
(66, '21'),
(66, '25'),

-- Ed Sheeran (idArtist = 67)
(67, 'Plus'),
(67, 'Multiply'),
(67, 'Divide'),

-- Elton John (idArtist = 68)
(68, 'Goodbye Yellow Brick Road'),
(68, 'Rocket Man'),
(68, 'The Diving Board'),

-- Amy Winehouse (idArtist = 69)
(69, 'Back to Black'),
(69, 'Frank'),

-- Dua Lipa (idArtist = 70)
(70, 'Dua Lipa'),
(70, 'Future Nostalgia'),

-- Freddie Mercury (idArtist = 71)
(71, 'Mr. Bad Guy'),
(71, 'Barcelona'),

-- David Bowie (idArtist = 72)
(72, 'The Rise and Fall of Ziggy Stardust'),
(72, 'Hunky Dory'),
(72, 'Let’s Dance'),

-- Paul McCartney (idArtist = 73)
(73, 'McCartney'),
(73, 'Band on the Run'),
(73, 'Ram'),

-- Harry Styles (idArtist = 74)
(74, 'Harry Styles'),
(74, 'Fine Line'),

-- George Michael (idArtist = 75)
(75, 'Faith'),
(75, 'Listen Without Prejudice Vol. 1'),
(75, 'Older'),

-- Andrea Bocelli
(76, 'Romanza'),
(76, 'Sacred Arias'),
(76, 'Sì'),

-- David Guetta
(77, 'One Love'),
(77, 'Nothing But the Beat'),
(77, 'Listen'),

-- Enrique Iglesias
(78, 'Enrique'),
(78, 'Insomniac'),
(78, 'Escape'),

-- ABBA
(79, 'Arrival'),
(79, 'Super Trouper'),
(79, 'The Visitors'),

-- Roxette
(80, 'Look Sharp!'),
(80, 'Joyride'),
(80, 'Crash! Boom! Bang!'),



-- SONG TABLE DATA
INSERT INTO songs (listener, title, url, idAlbum)
VALUES
-- Taylor Swift
(1500000000, 'Shake It Off', 'https://www.youtube.com/watch?v=nfWlot6h_JM', 1),
(1000000000, 'Blank Space', 'https://www.youtube.com/watch?v=e-ORhEE9VVg', 1),
(1200000000, 'All Too Well', 'https://www.youtube.com/watch?v=5j1RCys4R0g', 2),
(1700000000,'I Forgot That You Existed', 'https://www.youtube.com/watch?v=0W8g1g1g1g1', 3),
(1900000000,'Cruel Summer', 'https://www.youtube.com/watch?v=0W8g1g1g1g1', 3),
(2100000000,'Lover', 'https://www.youtube.com/watch?v=0W8g1g1g1g1', 3),

-- Beyoncé
(2000000000, 'Halo', 'https://www.youtube.com/watch?v=bnVUHWCynig', 4),
(1500000000, 'Single Ladies', 'https://www.youtube.com/watch?v=4m1EFMoRFvY', 5),
(1800000000, 'Formation', 'https://www.youtube.com/watch?v=WDzjJkyDd0Q', 6),

-- Eminem
(2500000000, 'Lose Yourself', 'https://www.youtube.com/watch?v=_Yhyp-_hX2s', 7),
(2200000000, 'Love the Way You Lie', 'https://www.youtube.com/watch?v=uelHwf8o7_U', 8),
(2100000000, 'Stan', 'https://www.youtube.com/watch?v=iywaKY5qQ6g', 9),

-- Bruce Springsteen
(1000000000, 'Dancing in the Dark', 'https://www.youtube.com/watch?v=129kuDCQtHs', 10),
(900000000, 'Born to Run', 'https://www.youtube.com/watch?v=IxuThNgl3YA', 11),
(850000000, 'Born in the U.S.A.', 'https://www.youtube.com/watch?v=EPhWR4d3FJQ', 12),

-- Lady Gaga
(1500000000, 'Bad Romance', 'https://www.youtube.com/watch?v=qrO4YZeyl0I', 13),
(1300000000, 'Poker Face', 'https://www.youtube.com/watch?v=LO2uM_O9B0s', 14),
(1200000000, 'Shallow', 'https://www.youtube.com/watch?v=boX2a6kAPvY', 15),

-- Billie Eilish
(2000000000, 'Bad Guy', 'https://www.youtube.com/watch?v=DyDfgMOUjCI', 16),
(1800000000, 'Everything I Wanted', 'https://www.youtube.com/watch?v=XtL2nV9RZZM', 17),

-- Frank Sinatra
(800000000, 'My Way', 'https://www.youtube.com/watch?v=qQkBeOisTIs', 18),
(750000000, 'Fly Me to the Moon', 'https://www.youtube.com/watch?v=ZEcq70t9QJo', 19),
(700000000, 'New York, New York', 'https://www.youtube.com/watch?v=Prt8Xv_qCxc', 20),

-- Michael Jackson
(4000000000, 'Billie Jean', 'https://www.youtube.com/watch?v=Zi_XLOBDo_Y', 21),
(3500000000, 'Thriller', 'https://www.youtube.com/watch?v=sOnqjkJTMaA', 22),
(3300000000, 'Beat It', 'https://www.youtube.com/watch?v=oRdxUFDoQe0', 23),

-- Madonna
(3000000000, 'Like a Prayer', 'https://www.youtube.com/watch?v=79fzeNUqQbQ', 24),
(2500000000, 'Like a Virgin', 'https://www.youtube.com/watch?v=s__rX_WL100', 25),
(2200000000, 'Vogue', 'https://www.youtube.com/watch?v=GuJQSAfrJt8', 26),

-- Prince
(2000000000, 'Purple Rain', 'https://www.youtube.com/watch?v=1P4x_Hj9PG8', 27),
(1900000000, 'Kiss', 'https://www.youtube.com/watch?v=H9tEvf8p4O4', 28),
(1700000000, 'When Doves Cry', 'https://www.youtube.com/watch?v=UMwvZy8b7r8', 29),

-- Adele
(1500000000, 'Someone Like You', 'https://www.youtube.com/watch?v=hLQl3WQQoQ0', 30),
(1300000000, 'Rolling in the Deep', 'https://www.youtube.com/watch?v=rYEDA3JcQqw', 31),
(1200000000, 'Hello', 'https://www.youtube.com/watch?v=YQHsXMglC9A', 32),

-- Ed Sheeran
(2500000000, 'Shape of You', 'https://www.youtube.com/watch?v=JGwWNGJdvx8', 33),
(2200000000, 'Thinking Out Loud', 'https://www.youtube.com/watch?v=lp-EO5I60KA', 34),
(2100000000, 'Perfect', 'https://www.youtube.com/watch?v=2Vv-BfVoq4g', 35),

-- Elton John
(3000000000, 'Rocket Man', 'https://www.youtube.com/watch?v=DtVBCG6ThDk', 36),
(2500000000, 'Your Song', 'https://www.youtube.com/watch?v=GlZh0_wXhAA', 37),
(2300000000, 'Tiny Dancer', 'https://www.youtube.com/watch?v=0zI4zU5_HXY', 38),

-- Amy Winehouse
(2000000000, 'Back to Black', 'https://www.youtube.com/watch?v=TJAfLE39ZZ8', 39),
(1800000000, 'Rehab', 'https://www.youtube.com/watch?v=KUmZ2lXj24g', 40),

-- Dua Lipa
(1800000000, 'New Rules', 'https://www.youtube.com/watch?v=k2qgadPqW7A', 41),
(1600000000, 'Don’t Start Now', 'https://www.youtube.com/watch?v=oygrmJFKYZY', 42),

-- Freddie Mercury
(1600000000, 'Living On My Own', 'https://www.youtube.com/watch?v=RbRDt90D3QU', 43),
(1500000000, 'Barcelona', 'https://www.youtube.com/watch?v=ko9XQnN73Xw', 44),

-- David Bowie
(2500000000, 'Heroes', 'https://www.youtube.com/watch?v=bsFObk4GfHA', 45),
(2200000000, 'Space Oddity', 'https://www.youtube.com/watch?v=iYYRH4apXDo', 46),
(2100000000, 'Let’s Dance', 'https://www.youtube.com/watch?v=5w0lBy7yK4w', 47),

-- Paul McCartney
(2000000000, 'Maybe I’m Amazed', 'https://www.youtube.com/watch?v=nPZZxTZX3CQ', 48),
(1800000000, 'Band on the Run', 'https://www.youtube.com/watch?v=ytKQgg2ybAw', 49),
(1500000000, 'Too Many People', 'https://www.youtube.com/watch?v=vnqYxG_5FhI', 50),
(1400000000, 'Heart of the Country', 'https://www.youtube.com/watch?v=wTTme_oBl7Y', 50),

-- Harry Styles
(1800000000, 'Sign of the Times', 'https://www.youtube.com/watch?v=Q1gAghz3h2Y', 51),
(1600000000, 'Watermelon Sugar', 'https://www.youtube.com/watch?v=E07s5ZYygMg', 52),

-- George Michael
(2200000000, 'Careless Whisper', 'https://www.youtube.com/watch?v=izGwDsrQ1eQ', 53),
(2000000000, 'Faith', 'https://www.youtube.com/watch?v=60ItHLz5WEA', 54),
(1900000000, 'Last Christmas', 'https://www.youtube.com/watch?v=E8gmARGvPlI', 55),

-- Andrea Bocelli
(1000000000, 'Time to Say Goodbye', 'https://www.youtube.com/watch?v=1R3JMIHFVJ0', 56),
(900000000, 'Con Te Partirò', 'https://www.youtube.com/watch?v=QXY7i2wL5XY', 57),
(850000000, 'Vivo per Lei', 'https://www.youtube.com/watch?v=EBOdvvKQUW4', 58),


-- David Guetta
(2000000000, 'When Love Takes Over', 'https://www.youtube.com/watch?v=PSmZbA2jfHk', 59),
(1500000000, 'Titanium', 'https://www.youtube.com/watch?v=JRfuAukYTKg', 60),
(1700000000, 'Without You', 'https://www.youtube.com/watch?v=71pWxpxhzgw', 61),

-- Enrique Iglesias
(2200000000, 'Bailando', 'https://www.youtube.com/watch?v=NUsoVlDFqZg', 62),
(1800000000, 'Hero', 'https://www.youtube.com/watch?v=6f4b4r4cJ5Q', 63),
(2000000000, 'Escape', 'https://www.youtube.com/watch?v=FBChzHk5_nA', 64),

-- ABBA
(3500000000, 'Dancing Queen', 'https://www.youtube.com/watch?v=xFrGuyw1V8s', 65),
(3000000000, 'Mamma Mia', 'https://www.youtube.com/watch?v=unfzfe8f9NI', 66),
(2800000000, 'The Winner Takes It All', 'https://www.youtube.com/watch?v=92CWwzM8q6k', 67),

-- Roxette
(2200000000, 'It Must Have Been Love', 'https://www.youtube.com/watch?v=k2C5TjS2pds', 68),
(2100000000, 'Listen to Your Heart', 'https://www.youtube.com/watch?v=5jz3P_v_UbM', 69),
(2000000000, 'Joyride', 'https://www.youtube.com/watch?v=cMTA1gB4HhA', 70);

-- RELATIONSHIPS
-- artist_show TABLE DATA
INSERT INTO artist_show (idArtist, idShow)
VALUES
(56, 1), -- Taylor Switft tampil di show 1
(57, 2), -- Beyoncé tampil di show 2
(58, 3), -- Eminem tampil di show 3
(59, 4), -- Bruce Springsteen tampil di show 4
(60, 5), -- Lady Gaga tampil di show 5
(61, 6), -- Billie Eilish tampil di show 6
(62, 7), -- Frank Sinatra tampil di show 7
(63, 8), -- Michael Jackson tampil di show 8
(64, 9), -- Madonna tampil di show 9
(65, 10), -- Prince tampil di show 10
(66, 11), -- Adele tampil di show 11
(67, 12), -- Ed Sheeran tampil di show 12
(68, 13), -- Elton John tampil di show 13
(69, 14), -- Amy Winehouse tampil di show 14
(70, 15), -- Dua Lipa tampil di show 15
(71, 16), -- Freddie Mercury tampil di show 16
(72, 17), -- David Bowie tampil di show 17
(73, 18), -- Paul McCartney tampil di show 18
(74, 19), -- Harry Styles tampil di show 19
(75, 20), -- George Michael tampil di show 20
(76, 21), -- Andrea Bocelli tampil di show 21
(77, 22), -- David Guetta tampil di show 22
(78, 23), -- Enrique Iglesias tampil di show 23
(79, 24), -- ABBA tampil di show 24
(80, 25); -- Roxette tampil di show 25


-- setlist TABLE DATA
INSERT INTO setlist (title, idShow)
VALUES
('Taylor Swift Hits', 1),
('Beyoncé: The Concert', 2),
('Eminem Live', 3),
('Bruce Springsteen: The Boss Live', 4),
('Lady Gaga: The Fame Ball', 5),
('Billie Eilish World Tour', 6),
('Frank Sinatra’s Swinging Night', 7),
('Michael Jackson: The King of Pop', 8),
('Madonna: Rebel Heart Tour', 9),
('Prince: Purple Rain Experience', 10),
('Adele: Live in Concert', 11),
('Ed Sheeran: World Tour', 12),
('Elton John: Farewell Tour', 13),
('Amy Winehouse: Back to Black Live', 14),
('Dua Lipa: Future Nostalgia Tour', 15),
('Freddie Mercury: The Solo Years', 16),
('David Bowie: A Legacy Remembered', 17),
('Paul McCartney: The Beatle Years', 18),
('Harry Styles: Love on Tour', 19),
('George Michael: A Tribute to the Legend', 20),
('Andrea Bocelli: The Opera Concert', 21),
('David Guetta: The Electronic Dance', 22),
('Enrique Iglesias: Latin Love', 23),
('ABBA: The Musical Journey', 24),
('Roxette: The Pop-Rock Experience', 25);



INSERT INTO song_artist (idSongs, idArtist)
VALUES
-- Taylor Swift
(1, 56), (2, 56), (3, 56), (4, 56), (5, 56), (6, 56),

-- Beyoncé
(7, 57), (8, 57), (9, 57),

-- Eminem
(10, 58), (11, 58), (12, 58),

-- Bruce Springsteen
(13, 59), (14, 59), (15, 59),

-- Lady Gaga
(16, 60), (17, 60), (18, 60),

-- Billie Eilish
(19, 61), (20, 61),

-- Frank Sinatra
(21, 62), (22, 62), (23, 62),

-- Michael Jackson
(24, 63), (25, 63), (26, 63),

-- Madonna
(27, 64), (28, 64), (29, 64),

-- Prince
(30, 65), (31, 65), (32, 65),

-- Adele
(33, 66), (34, 66), (35, 66),

-- Ed Sheeran
(36, 67), (37, 67), (38, 67),

-- Elton John
(39, 68), (40, 68), (41, 68),

-- Amy Winehouse
(42, 69), (43, 69),

-- Dua Lipa
(44, 70), (45, 70),

-- Freddie Mercury
(46, 71), (47, 71),

-- David Bowie
(48, 72), (49, 72), (50, 72),

-- Paul McCartney
(51, 73), (52, 73), (53, 73), (54, 73),

-- Harry Styles
(55, 74), (56, 74),

-- George Michael
(57, 75), (58, 75), (59, 75),

-- Andrea Bocelli
(60, 76), (61, 76), (62, 76),

-- David Guetta
(63, 77), (64, 77), (65, 77),

-- Enrique Iglesias
(66, 78), (67, 78), (68, 78),

-- ABBA
(69, 79), (70, 79), (71, 79),

-- Roxette
(72, 80), (73, 80), (74, 80);


INSERT INTO song_setlist (id_song, id_setlist)
VALUES
-- Taylor Swift Hits
(1, 1), -- Shake It Off
(2, 1), -- Blank Space
(3, 1), -- All Too Well
(4, 1), -- I Forgot That You Existed
(5, 1), -- Cruel Summer
(6, 1), -- Lover

-- Beyoncé: The Concert
(7, 2), -- Halo
(8, 2), -- Single Ladies
(9, 2), -- Formation

-- Eminem Live
(10, 3), -- Lose Yourself
(11, 3), -- Love the Way You Lie
(12, 3), -- Stan

-- Bruce Springsteen: The Boss Live
(13, 4), -- Dancing in the Dark
(14, 4), -- Born to Run
(15, 4), -- Born in the U.S.A.

-- Lady Gaga: The Fame Ball
(16, 5), -- Bad Romance
(17, 5), -- Poker Face
(18, 5), -- Shallow

-- Billie Eilish World Tour
(19, 6), -- Bad Guy
(20, 6), -- Everything I Wanted

-- Frank Sinatra’s Swinging Night
(21, 7), -- My Way
(22, 7), -- Fly Me to the Moon
(23, 7), -- New York, New York

-- Michael Jackson: The King of Pop
(24, 8), -- Billie Jean
(25, 8), -- Thriller
(26, 8), -- Beat It

-- Madonna: Rebel Heart Tour
(27, 9), -- Like a Prayer
(28, 9), -- Like a Virgin
(29, 9), -- Vogue

-- Prince: Purple Rain Experience
(30, 10), -- Purple Rain
(31, 10), -- Kiss
(32, 10), -- When Doves Cry

-- Adele: Live in Concert
(33, 11), -- Someone Like You
(34, 11), -- Rolling in the Deep
(35, 11), -- Hello

-- Ed Sheeran: World Tour
(36, 12), -- Shape of You
(37, 12), -- Thinking Out Loud
(38, 12), -- Perfect

-- Elton John: Farewell Tour
(39, 13), -- Rocket Man
(40, 13), -- Your Song
(41, 13), -- Tiny Dancer

-- Amy Winehouse: Back to Black Live
(42, 14), -- Back to Black
(43, 14), -- Rehab

-- Dua Lipa: Future Nostalgia Tour
(44, 15), -- New Rules
(45, 15), -- Don’t Start Now

-- Freddie Mercury: The Solo Years
(46, 16), -- Living On My Own
(47, 16), -- Barcelona

-- David Bowie: A Legacy Remembered
(48, 17), -- Heroes
(49, 17), -- Space Oddity
(50, 17), -- Let’s Dance

-- Paul McCartney: The Beatle Years
(51, 18), -- Maybe I’m Amazed
(52, 18), -- Band on the Run
(53, 18), -- Too Many People

-- Harry Styles: Love on Tour
(54, 19), -- Sign of the Times
(55, 19), -- Watermelon Sugar

-- George Michael: A Tribute to the Legend
(56, 20), -- Careless Whisper
(57, 20), -- Faith
(58, 20), -- Last Christmas

-- Andrea Bocelli: The Opera Concert
(59, 21), -- Time to Say Goodbye
(60, 21), -- Con Te Partirò
(61, 21), -- Vivo per Lei

-- David Guetta: The Electronic Dance
(62, 22), -- When Love Takes Over
(63, 22), -- Titanium
(64, 22), -- Without You

-- Enrique Iglesias: Latin Love
(65, 23), -- Bailando
(66, 23), -- Hero
(67, 23), -- Escape

-- ABBA: The Musical Journey
(68, 24), -- Dancing Queen
(69, 24), -- Mamma Mia
(70, 24), -- The Winner Takes It All

-- Roxette: The Pop-Rock Experience
(71, 25), -- It Must Have Been Love
(72, 25), -- Listen to Your Heart
(73, 25); -- Joyride


--Run psql tool buat import csv atau bisa di import dari klik kanan tabel di pgadmin
\copy public.venue (id, name, address, address2, city, state, zip, geocodable, latitude, longitude, website, phone, email)
FROM 'C:\Users\Lenovo\OneDrive - Universitas Katolik Parahyangan\Drive Kuliah\Pemrograman Berbasis Web\Tubes\TubesPBW\sql\venues.csv'
DELIMITER ','
CSV HEADER
NULL AS '';

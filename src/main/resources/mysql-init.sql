DROP DATABASE IF EXISTS nowplaying;

CREATE DATABASE nowplaying;
USE nowplaying;

CREATE TABLE Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_username (username),
    INDEX idx_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE Subscriptions (
    subscription_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    plan_type ENUM('BASIC', 'PREMIUM', 'UNLIMITED') NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    status ENUM('ACTIVE', 'EXPIRED', 'CANCELLED') NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id),
    INDEX idx_end_date (end_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE Artists (
    artist_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    genre VARCHAR(100),
    bio TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_name (name),
    INDEX idx_genre (genre)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE Songs (
    song_id INT AUTO_INCREMENT PRIMARY KEY,
    artist_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    duration_seconds INT,
    released_at DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (artist_id) REFERENCES Artists(artist_id) ON DELETE CASCADE,
    INDEX idx_artist_id (artist_id),
    INDEX idx_title (title)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE Playlists (
    playlist_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE PlaylistSongs (
    playlist_song_id INT AUTO_INCREMENT PRIMARY KEY,
    playlist_id INT NOT NULL,
    song_id INT NOT NULL,
    position INT,
    added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (playlist_id) REFERENCES Playlists(playlist_id) ON DELETE CASCADE,
    FOREIGN KEY (song_id) REFERENCES Songs(song_id) ON DELETE CASCADE,
    UNIQUE KEY unique_playlist_song (playlist_id, song_id),
    INDEX idx_playlist_id (playlist_id),
    INDEX idx_song_id (song_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


DELIMITER //
CREATE PROCEDURE Get_Top_Playlists(IN limit_count INT)
BEGIN
    SELECT 
        p.playlist_id,
        p.user_id,
        p.name,
        p.description,
        p.created_at,
        u.username,
        COUNT(ps.song_id) AS song_count
    FROM Playlists p
    LEFT JOIN PlaylistSongs ps ON p.playlist_id = ps.playlist_id
    LEFT JOIN Users u ON p.user_id = u.user_id
    GROUP BY p.playlist_id, p.user_id, p.name, p.description, p.created_at, u.username
    ORDER BY song_count DESC
    LIMIT limit_count;
END //
DELIMITER ;


DELIMITER //
CREATE TRIGGER trg_prevent_user_delete
BEFORE DELETE ON Users
FOR EACH ROW
BEGIN
    DECLARE active_subscription_count INT;
    
    
    SELECT COUNT(*) INTO active_subscription_count
    FROM Subscriptions
    WHERE user_id = OLD.user_id
    AND end_date >= CURDATE();
    
    
    IF active_subscription_count > 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Cannot delete user: Active subscription exists. Please cancel subscription before deleting user.';
    END IF;
END //
DELIMITER ;


INSERT INTO Artists (name, genre, bio) VALUES
('The Weeknd', 'Synthwave/Pop', 'Canadian singer-songwriter'),
('Taylor Swift', 'Pop/Country', 'American pop icon'),
('Billie Eilish', 'Alternative Hip-Hop', 'Electronica and alternative artist');


INSERT INTO Songs (artist_id, title, duration_seconds, released_at) VALUES
(1, 'Blinding Lights', 200, '2019-11-29'),
(1, 'Starboy', 230, '2016-09-22'),
(2, 'Shake It Off', 242, '2014-08-18'),
(2, 'Anti-Hero', 228, '2022-10-21'),
(3, 'Bad Guy', 194, '2018-03-30');


INSERT INTO Users (username, email, password_hash, first_name, last_name) VALUES
('musicfan99', 'musicfan99@example.com', 'hashed_password_1', 'John', 'Doe'),
('beatslover', 'beatslover@example.com', 'hashed_password_2', 'Jane', 'Smith'),
('pop_culture', 'popcult@example.com', 'hashed_password_3', 'Sarah', 'Williams');


INSERT INTO Subscriptions (user_id, plan_type, start_date, end_date, status) VALUES
(1, 'PREMIUM', '2026-01-15', '2026-12-15', 'ACTIVE'),
(2, 'UNLIMITED', '2026-03-01', '2027-03-01', 'ACTIVE'),
(3, 'BASIC', '2025-06-01', '2026-06-01', 'EXPIRED');


INSERT INTO Playlists (user_id, name, description) VALUES
(1, 'My Favorites', 'Collection of my all-time favorite songs'),
(2, 'Workout Hits', 'High-energy songs for gym sessions');


INSERT INTO PlaylistSongs (playlist_id, song_id, position) VALUES
(1, 1, 1),
(1, 3, 2),
(1, 5, 3),
(2, 2, 1),
(2, 4, 2);
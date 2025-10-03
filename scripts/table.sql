CREATE DATABASE codingtest CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE codingtest;
-- asked above statement AI because create table were not working
CREATE TABLE users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) NOT NULL UNIQUE,
  password_hash VARBINARY(256) NOT NULL,
  salt VARBINARY(32) NOT NULL,
  full_name VARCHAR(100) NOT NULL
);

CREATE TABLE interview_details (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  interviewed_at DATETIME NOT NULL,
  interviewer_id INT NULL,
  comments TEXT NULL,
  rating TINYINT UNSIGNED NULL,
  interview_type ENUM('phone','virtual','onsite','takehome','other') NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

  CONSTRAINT fk_interview_user
    FOREIGN KEY (user_id) REFERENCES users(id)
    ON DELETE CASCADE,

  CONSTRAINT fk_interviewer_user
    FOREIGN KEY (interviewer_id) REFERENCES users(id)
    ON DELETE SET NULL,

  INDEX idx_interview_user (user_id),
  INDEX idx_interviewed_at (interviewed_at),
  INDEX idx_interviewer (interviewer_id),

  CHECK (rating IS NULL OR (rating BETWEEN 1 AND 10))
);
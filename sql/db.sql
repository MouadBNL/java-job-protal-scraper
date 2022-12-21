CREATE DATABASE IF NOT EXISTS `jobportalscraper` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `jobportalscraper`;

DROP TABLE IF EXISTS `jobs`;

CREATE TABLE `jobs` (
    id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    site_name VARCHAR(255),
    url VARCHAR(255) NOT NULL UNIQUE,
    publish_date VARCHAR(255) NULL,
    apply_date VARCHAR(255) NULL,
    company_name VARCHAR(255) NULL,
    company_address VARCHAR(255) NULL,
    company_website VARCHAR(255) NULL,
    company_description TEXT NULL,
    description TEXT NULL,
    title VARCHAR(255) NULL,
    diploma VARCHAR(255) NULL,
    experience   VARCHAR(511) NULL,
    profile_searched TEXT NULL,
    personality_traits TEXT NULL,
    hard_skills TEXT NULL,
    soft_skills TEXT NULL,
    recommended_skills TEXT NULL,
    lang VARCHAR(255) NULL,
    lang_level VARCHAR(255) NULL,
    salary VARCHAR(255) NULL,
    social_advantages TEXT NULL,
    remote VARCHAR(255) NULL
);
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema tutorsearcher
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `tutorsearchertest`;
-- -----------------------------------------------------
-- Schema tutorsearcher
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `tutorsearchertest` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `tutorsearchertest` ;

-- -----------------------------------------------------
-- Table `tutorsearcher`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tutorsearchertest`.`users`;

CREATE TABLE IF NOT EXISTS `tutorsearchertest`.`users` (
  `user_id` INT(10) NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(45) NOT NULL,
  `password_hash` VARCHAR(200) NOT NULL,
  `tutor` TINYINT(4) NOT NULL,
  `phone_number` VARCHAR(45) NOT NULL,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `availability` VARCHAR(200) NULL DEFAULT NULL,
  `tutee_search_class` VARCHAR(45) NULL DEFAULT NULL,
  `tutee_search_times` VARCHAR(200) NULL DEFAULT NULL,
  `rating` DOUBLE NULL DEFAULT NULL,
  `num_ratings` INT(10) NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tutorsearcher`.`classes`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tutorsearchertest`.`classes`;

CREATE TABLE IF NOT EXISTS `tutorsearchertest`.`classes` (
  `classes_id` INT(11) NOT NULL AUTO_INCREMENT,
  `class_name` VARCHAR(45) NOT NULL,
  `tutor_id` INT(10) NOT NULL,
  PRIMARY KEY (`classes_id`),
  UNIQUE INDEX `availability_id_UNIQUE` (`classes_id` ASC) VISIBLE,
  INDEX `tutor_id_fk_idx_test` (`tutor_id` ASC) VISIBLE,
  CONSTRAINT `classes_ibfk_1_test`
    FOREIGN KEY (`tutor_id`)
    REFERENCES `tutorsearchertest`.`users` (`user_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tutorsearcher`.`requests`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tutorsearchertest`.`requests`;

CREATE TABLE IF NOT EXISTS `tutorsearchertest`.`requests` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `tutee_id` INT(10) NOT NULL,
  `tutor_id` INT(10) NOT NULL,
  `class` VARCHAR(45) NOT NULL,
  `time` VARCHAR(45) NOT NULL,
  `status` INT(11) NOT NULL,
  `time_created` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `tutor_id_fk_idx_test` (`tutor_id` ASC) VISIBLE,
  INDEX `tutee_id_fk_test` (`tutee_id` ASC) VISIBLE,
  CONSTRAINT `tutee_id_fk_test`
    FOREIGN KEY (`tutee_id`)
    REFERENCES `tutorsearchertest`.`users` (`user_id`),
  CONSTRAINT `tutor_id_fk_test`
    FOREIGN KEY (`tutor_id`)
    REFERENCES `tutorsearchertest`.`users` (`user_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

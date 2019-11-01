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

-- -----------------------------------------------------
-- Schema tutorsearcher
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `tutorsearcher` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `tutorsearcher` ;

-- -----------------------------------------------------
-- Table `tutorsearcher`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tutorsearcher`.`users` (
  `user_id` INT(10) NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(45) NOT NULL,
  `password_hash` VARCHAR(45) NOT NULL,
  `tutor` TINYINT(4) NOT NULL,
  `phone_number` VARCHAR(45) NOT NULL,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `availability` VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tutorsearcher`.`classes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tutorsearcher`.`classes` (
  `classes_id` INT(11) NOT NULL AUTO_INCREMENT,
  `class_name` VARCHAR(45) NOT NULL,
  `tutor_id` INT(10) NOT NULL,
  PRIMARY KEY (`classes_id`),
  UNIQUE INDEX `availability_id_UNIQUE` (`classes_id` ASC) VISIBLE,
  INDEX `tutor_id_fk_idx` (`tutor_id` ASC) VISIBLE,
  CONSTRAINT `classes_ibfk_1`
    FOREIGN KEY (`tutor_id`)
    REFERENCES `tutorsearcher`.`users` (`user_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tutorsearcher`.`requests`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tutorsearcher`.`requests` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `tutee_id` INT(10) NOT NULL,
  `tutor_id` INT(10) NOT NULL,
  `class` VARCHAR(45) NOT NULL,
  `time` VARCHAR(45) NOT NULL,
  `status` INT(11) NOT NULL,
  `time_created` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `tutor_id_fk_idx` (`tutor_id` ASC) VISIBLE,
  INDEX `tutee_id_fk` (`tutee_id` ASC) VISIBLE,
  CONSTRAINT `tutee_id_fk`
    FOREIGN KEY (`tutee_id`)
    REFERENCES `tutorsearcher`.`users` (`user_id`),
  CONSTRAINT `tutor_id_fk`
    FOREIGN KEY (`tutor_id`)
    REFERENCES `tutorsearcher`.`users` (`user_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

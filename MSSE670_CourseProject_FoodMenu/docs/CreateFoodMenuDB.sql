--
-- File generated with SQLiteStudio v3.3.3 on Sat Nov 6 21:52:33 2021
--
-- Text encoding used: System
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: daymeallist
DROP TABLE IF EXISTS daymeallist;
CREATE TABLE daymeallist (daymenuid INTEGER REFERENCES daymenu (daymenuid) ON DELETE CASCADE NOT NULL, menuitemid INTEGER REFERENCES menuitems (menuitemid) ON DELETE CASCADE NOT NULL);
INSERT INTO daymeallist (daymenuid, menuitemid) VALUES (1, 3);
INSERT INTO daymeallist (daymenuid, menuitemid) VALUES (1, 2);
INSERT INTO daymeallist (daymenuid, menuitemid) VALUES (1, 1);

-- Table: daymenu
DROP TABLE IF EXISTS daymenu;
CREATE TABLE daymenu (daymenuid INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, date DATE NOT NULL, complexityvalue INTEGER NOT NULL, healthvalue INTEGER NOT NULL);
INSERT INTO daymenu (daymenuid, date, complexityvalue, healthvalue) VALUES (1, '2021-10-29', 3, 2.89);

-- Table: fooditems
DROP TABLE IF EXISTS fooditems;
CREATE TABLE fooditems (fooditemid INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, foodname TEXT NOT NULL UNIQUE, category TEXT NOT NULL, healthvalue INTEGER NOT NULL, preptime INTEGER NOT NULL);
INSERT INTO fooditems (fooditemid, foodname, category, healthvalue, preptime) VALUES (1, 'Cherrios Cereal', 'Other', 3, 5);
INSERT INTO fooditems (fooditemid, foodname, category, healthvalue, preptime) VALUES (2, 'McDonalds Cheeseburger', 'American', 1, 0);
INSERT INTO fooditems (fooditemid, foodname, category, healthvalue, preptime) VALUES (3, 'McDonalds Fries', 'American', 1, 0);
INSERT INTO fooditems (fooditemid, foodname, category, healthvalue, preptime) VALUES (4, 'Pot Roast', 'American', 4, 180);
INSERT INTO fooditems (fooditemid, foodname, category, healthvalue, preptime) VALUES (5, 'House Style Salad', 'Other', 8, 15);
INSERT INTO fooditems (fooditemid, foodname, category, healthvalue, preptime) VALUES (6, 'Ice Cream with Hot Fudge', 'Other', 2, 5);

-- Table: info
DROP TABLE IF EXISTS info;
CREATE TABLE info (infoid INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, infotext TEXT NOT NULL);
INSERT INTO info (infoid, infotext) VALUES (1, '22b5f5c6b963d8b1be83b0501a00528512fd3de080a3a63b1215e4d1f08ab26dc3612b3c8ba1f9b2177310c56bb7b78f9d76a25ff146476d8083575341e7edc7');
INSERT INTO info (infoid, infotext) VALUES (2, 'a468165530a795dcdf220c8103315219ccf0e52782752b7fab1f9a3eeda63d98cc0131147101f2a210866da0b9029b5e48b545ed788eff71d666b4d2f308ef04');

-- Table: salt
DROP TABLE IF EXISTS salt;
CREATE TABLE salt (saltid INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, salttext TEXT NOT NULL);
INSERT INTO salt (saltid, salttext) VALUES (1, 'mgrYSUDSoQ3G4Z6lGv4fisCJa76Yt5Fv');
INSERT INTO salt (saltid, salttext) VALUES (2, 'GZGogBWbOvZ375qj5lBg8wx4Eyv1LxeU');

-- Table: ingredients
DROP TABLE IF EXISTS ingredients;
CREATE TABLE ingredients (ingredientid INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, fooditemid INTEGER REFERENCES fooditems (fooditemid) ON DELETE CASCADE NOT NULL, ingredient TEXT NOT NULL);
INSERT INTO ingredients (ingredientid, fooditemid, ingredient) VALUES (1, 1, 'Milk');
INSERT INTO ingredients (ingredientid, fooditemid, ingredient) VALUES (2, 1, 'Cereal');
INSERT INTO ingredients (ingredientid, fooditemid, ingredient) VALUES (3, 2, 'Car+gas');
INSERT INTO ingredients (ingredientid, fooditemid, ingredient) VALUES (4, 2, 'Money');
INSERT INTO ingredients (ingredientid, fooditemid, ingredient) VALUES (5, 3, 'Car+gas');
INSERT INTO ingredients (ingredientid, fooditemid, ingredient) VALUES (6, 3, 'Money');
INSERT INTO ingredients (ingredientid, fooditemid, ingredient) VALUES (7, 4, 'Potatoes');
INSERT INTO ingredients (ingredientid, fooditemid, ingredient) VALUES (8, 4, 'Carrots');
INSERT INTO ingredients (ingredientid, fooditemid, ingredient) VALUES (9, 4, 'Black Pepper');
INSERT INTO ingredients (ingredientid, fooditemid, ingredient) VALUES (10, 4, '3 lbs chuck roast');
INSERT INTO ingredients (ingredientid, fooditemid, ingredient) VALUES (11, 4, 'Onions');
INSERT INTO ingredients (ingredientid, fooditemid, ingredient) VALUES (12, 5, 'Lettuce');
INSERT INTO ingredients (ingredientid, fooditemid, ingredient) VALUES (13, 5, 'Tomatoes');
INSERT INTO ingredients (ingredientid, fooditemid, ingredient) VALUES (14, 5, 'Onions');
INSERT INTO ingredients (ingredientid, fooditemid, ingredient) VALUES (15, 5, 'Carrots');
INSERT INTO ingredients (ingredientid, fooditemid, ingredient) VALUES (16, 5, 'Salad Dressing (Ranch)');
INSERT INTO ingredients (ingredientid, fooditemid, ingredient) VALUES (17, 6, 'Vanilla Ice Cream');
INSERT INTO ingredients (ingredientid, fooditemid, ingredient) VALUES (18, 6, 'Hot Fudge');

-- Table: mealfoodlist
DROP TABLE IF EXISTS mealfoodlist;
CREATE TABLE mealfoodlist (fooditemid INTEGER REFERENCES fooditems (fooditemid) ON DELETE CASCADE NOT NULL, menuitemid INTEGER REFERENCES menuitems (menuitemid) ON DELETE CASCADE NOT NULL);
INSERT INTO mealfoodlist (fooditemid, menuitemid) VALUES (1, 1);
INSERT INTO mealfoodlist (fooditemid, menuitemid) VALUES (3, 2);
INSERT INTO mealfoodlist (fooditemid, menuitemid) VALUES (2, 2);
INSERT INTO mealfoodlist (fooditemid, menuitemid) VALUES (4, 3);
INSERT INTO mealfoodlist (fooditemid, menuitemid) VALUES (5, 3);
INSERT INTO mealfoodlist (fooditemid, menuitemid) VALUES (6, 3);

-- Table: menuitems
DROP TABLE IF EXISTS menuitems;
CREATE TABLE menuitems (menuitemid INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, mealname TEXT NOT NULL UNIQUE, complexityvalue INTEGER NOT NULL, healthvalue DOUBLE NOT NULL);
INSERT INTO menuitems (menuitemid, mealname, complexityvalue, healthvalue) VALUES (1, 'Cherrios Cereal', 2, 3.0);
INSERT INTO menuitems (menuitemid, mealname, complexityvalue, healthvalue) VALUES (2, 'McDonalds Burger & Fries', 1, 2.89);
INSERT INTO menuitems (menuitemid, mealname, complexityvalue, healthvalue) VALUES (3, 'Pot Roast Dinner with Salad and Ice Cream', 6, 4.67);

-- Table: recipe
DROP TABLE IF EXISTS recipe;
CREATE TABLE recipe (recipeid INTEGER PRIMARY KEY AUTOINCREMENT, fooditemid INTEGER REFERENCES fooditems (fooditemid) ON DELETE CASCADE, steptext TEXT NOT NULL);
INSERT INTO recipe (recipeid, fooditemid, steptext) VALUES (1, 1, 'Pour Cereal into Bowl.');
INSERT INTO recipe (recipeid, fooditemid, steptext) VALUES (2, 1, 'Pour Milk into Bowl.');
INSERT INTO recipe (recipeid, fooditemid, steptext) VALUES (3, 2, 'Collect food at second window');
INSERT INTO recipe (recipeid, fooditemid, steptext) VALUES (4, 2, 'Pay at 1st Window');
INSERT INTO recipe (recipeid, fooditemid, steptext) VALUES (5, 3, 'Collect food at second window');
INSERT INTO recipe (recipeid, fooditemid, steptext) VALUES (6, 3, 'Pay at 1st Window');
INSERT INTO recipe (recipeid, fooditemid, steptext) VALUES (7, 3, 'Order Fries');
INSERT INTO recipe (recipeid, fooditemid, steptext) VALUES (8, 3, 'Drive to McDonalds');
INSERT INTO recipe (recipeid, fooditemid, steptext) VALUES (9, 2, 'Order Cheeseburger');
INSERT INTO recipe (recipeid, fooditemid, steptext) VALUES (10, 2, 'Drive to McDonalds');
INSERT INTO recipe (recipeid, fooditemid, steptext) VALUES (11, 4, 'Preheat the oven to 275 degrees F.');
INSERT INTO recipe (recipeid, fooditemid, steptext) VALUES (12, 4, 'Generously salt and pepper the chuck roast.');
INSERT INTO recipe (recipeid, fooditemid, steptext) VALUES (13, 4, 'Heat the olive oil in large pot or Dutch oven over medium-high heat. Add the halved onions to the pot, browning them on both sides. Remove the onions to a plate.');
INSERT INTO recipe (recipeid, fooditemid, steptext) VALUES (14, 4, 'Throw the carrots into the same very hot pot and toss them around a bit until slightly browned, about a minute or so. Reserve the carrots with the onions.');
INSERT INTO recipe (recipeid, fooditemid, steptext) VALUES (15, 4, 'If needed, add a bit more olive oil to the very hot pot. Place the meat in the pot and sear it for about a minute on all sides until it is nice and brown all over. Remove the roast to a plate.');
INSERT INTO recipe (recipeid, fooditemid, steptext) VALUES (16, 4, 'With the burner still on high, use either red wine or beef broth (about 1 cup) to deglaze the pot, scraping the bottom with a whisk. Place the roast back into the pot and add enough beef stock to cover the meat halfway.');
INSERT INTO recipe (recipeid, fooditemid, steptext) VALUES (17, 4, 'Add in the onions and the carrots, along with the fresh herbs.');
INSERT INTO recipe (recipeid, fooditemid, steptext) VALUES (18, 4, 'Put the lid on, then roast for 3 hours for a 3-pound roast. For a 4 to 5-pound roast, plan on 4 hours. The roast is ready when it''s fall-apart tender.');
INSERT INTO recipe (recipeid, fooditemid, steptext) VALUES (19, 5, 'Wash Vegitables with water.');
INSERT INTO recipe (recipeid, fooditemid, steptext) VALUES (20, 5, 'Air dry / Spin dry Vegitables.');
INSERT INTO recipe (recipeid, fooditemid, steptext) VALUES (21, 5, 'Cut Tomatoes and Onions. Shave Carrots into large bowl.');
INSERT INTO recipe (recipeid, fooditemid, steptext) VALUES (22, 5, 'Mix all vegitables in a large bowl.');
INSERT INTO recipe (recipeid, fooditemid, steptext) VALUES (23, 5, 'Add Salad Dressing.');
INSERT INTO recipe (recipeid, fooditemid, steptext) VALUES (24, 6, 'Scoop Ice Cream into individual bowl.');
INSERT INTO recipe (recipeid, fooditemid, steptext) VALUES (25, 6, 'Add Hot Fudge.');

-- Table: rolesa
DROP TABLE IF EXISTS roles;
CREATE TABLE roles (roleid INTEGER PRIMARY KEY AUTOINCREMENT, rolename TEXT NOT NULL);
INSERT INTO roles (roleid, rolename) VALUES (1, 'admin');
INSERT INTO roles (roleid, rolename) VALUES (2, 'user');

-- Table: users
DROP TABLE IF EXISTS users;
CREATE TABLE users (userid INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, firstname TEXT NOT NULL, lastname TEXT NOT NULL, email TEXT NOT NULL UNIQUE ON CONFLICT FAIL, recoveryphrase TEXT NOT NULL, age INTEGER NOT NULL, role INTEGER REFERENCES roles (roleid) NOT NULL, infoid INTEGER REFERENCES info (infoid), saltid INTEGER REFERENCES salt (saltid));
INSERT INTO users (userid, firstname, lastname, email, recoveryphrase, age, role, infoid, saltid) VALUES (1, 'admin', 'istrator', 'admin@not.areal.email', 'I Am Groot!', 29, 1, 1, 1);
INSERT INTO users (userid, firstname, lastname, email, recoveryphrase, age, role, infoid, saltid) VALUES (2, 'justa', 'user', 'user@not.areal.email', 'Left Alone', 28, 2, 2, 2);

COMMIT TRANSACTION;
PRAGMA foreign_keys = on;

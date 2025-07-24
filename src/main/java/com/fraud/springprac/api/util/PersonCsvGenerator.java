package com.fraud.springprac.api.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class PersonCsvGenerator {

    static String[] firstNames = {"Liam", "Noah", "Oliver", "Elijah", "William", "James", "Benjamin", "Lucas", "Henry", "Alexander",
            "Mason", "Michael", "Ethan", "Daniel", "Logan", "Jackson", "Sebastian", "Jack", "Aiden", "Owen", "Olivia", "Emma", "Ava",
            "Charlotte", "Sophia", "Amelia", "Isabella", "Mia", "Evelyn", "Harper", "Camila", "Gianna", "Elizabeth", "Luna", "Ella",
            "Avery", "Scarlett", "Emily", "Aria", "Layla", "Liam", "Noah", "Oliver", "Elijah", "William", "James", "Benjamin", "Lucas",
            "Henry", "Alexander", "Mason", "Michael", "Ethan", "Daniel", "Logan", "Jackson", "Sebastian", "Jack", "Aiden", "Owen", "Olivia",
            "Emma", "Ava", "Charlotte", "Sophia", "Amelia", "Isabella", "Mia", "Evelyn", "Harper", "Camila", "Gianna", "Elizabeth", "Luna", "Ella",
            "Avery", "Scarlett", "Emily", "Aria", "Layla", "Liam", "Noah", "Oliver", "Elijah", "William", "James", "Benjamin", "Lucas", "Henry", "Alexander",
            "Mason", "Michael", "Ethan", "Daniel", "Logan", "Jackson", "Sebastian", "Jack", "Aiden", "Owen", "Olivia", "Emma", "Ava", "Charlotte", "Sophia",
            "Amelia", "Isabella", "Mia", "Evelyn", "Harper", "Camila", "Gianna", "Elizabeth", "Luna", "Ella", "Avery", "Scarlett", "Emily", "Aria", "Layla",
            "Liam", "Noah", "Oliver", "Elijah", "William", "James", "Benjamin", "Lucas", "Henry", "Alexander", "Mason", "Michael", "Ethan", "Daniel", "Logan",
            "Jackson", "Sebastian", "Jack", "Aiden", "Owen", "Olivia", "Emma", "Ava", "Charlotte", "Sophia", "Amelia", "Isabella", "Mia", "Evelyn", "Harper", "Camila",
            "Gianna", "Elizabeth", "Luna", "Ella", "Avery", "Scarlett", "Emily", "Aria", "Layla", "Liam", "Noah", "Oliver", "Elijah", "William", "James", "Benjamin",
            "Lucas", "Henry", "Alexander", "Mason", "Michael", "Ethan", "Daniel", "Logan", "Jackson", "Sebastian", "Jack", "Aiden",
            "Owen", "Olivia", "Emma", "Ava", "Charlotte", "Sophia", "Amelia", "Isabella", "Mia", "Evelyn", "Harper", "Camila", "Gianna",
            "Elizabeth", "Luna", "Ella", "Avery", "Scarlett", "Emily", "Aria", "Layla", "Liam", "Noah", "Oliver", "Elijah", "William", "James",
            "Benjamin", "Lucas", "Henry", "Alexander", "Mason", "Michael", "Ethan", "Daniel", "Logan", "Jackson", "Sebastian", "Jack", "Aiden", "Owen",
            "Olivia", "Emma", "Ava", "Charlotte", "Sophia", "Amelia", "Isabella", "Mia", "Evelyn", "Harper", "Camila", "Gianna", "Elizabeth", "Luna", "Ella",
            "Avery", "Scarlett", "Emily", "Aria", "Layla", "Liam", "Noah", "Oliver", "Elijah", "William", "James", "Benjamin", "Lucas", "Henry", "Alexander",
            "Mason", "Michael", "Ethan", "Daniel", "Logan", "Jackson", "Sebastian", "Jack", "Aiden", "Owen", "Olivia", "Emma", "Ava", "Charlotte", "Sophia",
            "Amelia", "Isabella", "Mia", "Evelyn", "Harper", "Camila", "Gianna", "Elizabeth", "Luna", "Ella", "Avery", "Scarlett", "Emily", "Aria", "Layla",
            "Liam", "Noah", "Oliver", "Elijah", "William", "James", "Benjamin", "Lucas", "Henry", "Alexander", "Mason", "Michael", "Ethan", "Daniel", "Logan",
            "Jackson", "Sebastian", "Jack", "Aiden", "Owen", "Olivia", "Emma", "Ava", "Charlotte", "Sophia", "Amelia", "Isabella", "Mia", "Evelyn", "Harper",
            "Camila", "Gianna", "Elizabeth", "Luna", "Ella", "Avery", "Scarlett", "Emily", "Aria", "Layla", "Liam", "Noah", "Oliver", "Elijah", "William",
            "James", "Benjamin", "Lucas", "Henry", "Alexander", "Mason", "Michael", "Ethan", "Daniel", "Logan", "Jackson", "Sebastian", "Jack", "Aiden",
            "Owen", "Olivia", "Emma", "Ava", "Charlotte", "Sophia", "Amelia", "Isabella", "Mia", "Evelyn", "Harper", "Camila", "Gianna", "Elizabeth",
            "Luna", "Ella", "Avery", "Scarlett", "Emily", "Aria", "Layla", "Liam", "Noah", "Oliver", "Elijah", "William", "James", "Benjamin", "Lucas",
            "Henry", "Alexander", "Mason", "Michael", "Ethan", "Daniel", "Logan", "Jackson", "Sebastian", "Jack", "Aiden", "Owen", "Olivia", "Emma", "Ava",
            "Charlotte", "Sophia", "Amelia", "Isabella", "Mia", "Evelyn", "Harper", "Camila", "Gianna", "Elizabeth", "Luna", "Ella", "Avery", "Scarlett",
            "Emily", "Aria", "Layla", "Liam", "Noah", "Oliver", "Elijah", "William", "James", "Benjamin", "Lucas", "Henry", "Alexander", "Mason", "Michael",
            "Ethan", "Daniel", "Logan", "Jackson", "Sebastian", "Jack", "Aiden", "Owen", "Olivia", "Emma", "Ava", "Charlotte", "Sophia", "Amelia", "Isabella",
            "Mia", "Evelyn", "Harper", "Camila", "Gianna", "Elizabeth", "Luna", "Ella", "Avery", "Scarlett", "Emily", "Aria", "Layla", "Liam", "Noah", "Oliver",
            "Elijah", "William", "James", "Benjamin", "Lucas", "Henry", "Alexander", "Mason", "Michael", "Ethan", "Daniel", "Logan", "Jackson", "Sebastian", "Jack",
            "Aiden", "Owen", "Olivia", "Emma", "Ava", "Charlotte", "Sophia", "Amelia", "Isabella", "Mia", "Evelyn", "Harper", "Camila", "Gianna", "Elizabeth", "Luna",
            "Ella", "Avery", "Scarlett", "Emily", "Aria", "Layla", "Liam", "Noah", "Oliver", "Elijah", "William", "James", "Benjamin", "Lucas", "Henry", "Alexander",
            "Mason", "Michael", "Ethan", "Daniel", "Logan", "Jackson", "Sebastian", "Jack", "Aiden", "Owen", "Olivia", "Emma", "Ava", "Charlotte", "Sophia", "Amelia",
            "Isabella", "Mia", "Evelyn", "Harper", "Camila", "Gianna", "Elizabeth", "Luna", "Ella", "Avery", "Scarlett", "Emily", "Aria", "Layla", "Liam", "Noah",
            "Oliver", "Elijah", "William", "James", "Benjamin", "Lucas", "Henry", "Alexander", "Mason", "Michael", "Ethan", "Daniel", "Logan", "Jackson",
            "Sebastian", "Jack", "Aiden", "Owen", "Olivia", "Emma", "Ava", "Charlotte", "Sophia", "Amelia", "Isabella", "Mia", "Evelyn", "Harper", "Camila",
            "Gianna", "Elizabeth", "Luna", "Ella", "Avery", "Scarlett", "Emily", "Aria", "Layla", "Liam", "Noah", "Oliver", "Elijah", "William", "James",
            "Benjamin", "Lucas", "Henry", "Alexander", "Mason", "Michael", "Ethan", "Daniel", "Logan", "Jackson", "Sebastian", "Jack", "Aiden", "Owen", "Olivia",
            "Emma", "Ava", "Charlotte", "Sophia", "Amelia", "Isabella", "Mia", "Evelyn", "Harper", "Camila", "Gianna", "Elizabeth", "Luna", "Ella", "Avery", "Scarlett",
            "Emily", "Aria", "Layla", "Liam", "Noah", "Oliver", "Elijah", "William", "James", "Benjamin", "Lucas", "Henry", "Alexander", "Mason", "Michael", "Ethan",
            "Daniel", "Logan", "Jackson", "Sebastian", "Jack", "Aiden", "Owen", "Olivia", "Emma", "Ava", "Charlotte", "Sophia", "Amelia", "Isabella", "Mia", "Evelyn",
            "Harper", "Camila", "Gianna", "Elizabeth", "Luna", "Ella", "Avery", "Scarlett", "Emily", "Aria", "Layla", "Liam", "Noah", "Oliver", "Elijah", "William",
            "James", "Benjamin", "Lucas", "Henry", "Alexander", "Mason", "Michael", "Ethan", "Daniel", "Logan", "Jackson", "Sebastian", "Jack", "Aiden", "Owen",
            "Olivia", "Emma", "Ava", "Charlotte", "Sophia", "Amelia", "Isabella", "Mia", "Evelyn", "Harper", "Camila", "Gianna", "Elizabeth", "Luna", "Ella",
            "Avery", "Scarlett", "Emily", "Aria", "Layla", "Liam", "Noah", "Oliver", "Elijah", "William", "James", "Benjamin", "Lucas", "Henry", "Alexander",
            "Mason", "Michael", "Ethan", "Daniel", "Logan", "Jackson", "Sebastian", "Jack", "Aiden", "Owen", "Olivia", "Emma", "Ava", "Charlotte", "Sophia",
            "Amelia", "Isabella", "Mia", "Evelyn", "Harper", "Camila", "Gianna", "Elizabeth", "Luna", "Ella", "Avery", "Scarlett", "Emily", "Aria", "Layla",
            "Liam", "Noah", "Oliver", "Elijah", "William", "James", "Benjamin", "Lucas", "Henry", "Alexander", "Mason", "Michael", "Ethan", "Daniel", "Logan",
            "Jackson", "Sebastian", "Jack", "Aiden", "Owen", "Olivia", "Emma", "Ava", "Charlotte", "Sophia", "Amelia", "Isabella", "Mia", "Evelyn", "Harper",
            "Camila", "Gianna", "Elizabeth", "Luna", "Ella", "Avery", "Scarlett", "Emily", "Aria", "Layla", "Liam", "Noah", "Oliver", "Elijah", "William", "James",
            "Benjamin", "Lucas", "Henry", "Alexander", "Mason", "Michael", "Ethan", "Daniel", "Logan", "Jackson", "Sebastian", "Jack", "Aiden", "Owen", "Olivia",
            "Emma", "Ava", "Charlotte", "Sophia", "Amelia", "Isabella", "Mia", "Evelyn", "Harper", "Camila", "Gianna", "Elizabeth", "Luna", "Ella", "Avery",
            "Scarlett", "Emily", "Aria", "Layla", "Liam", "Noah", "Oliver", "Elijah", "William", "James", "Benjamin", "Lucas", "Henry", "Alexander", "Mason",
            "Michael", "Ethan", "Daniel", "Logan", "Jackson", "Sebastian", "Jack", "Aiden", "Owen", "Olivia", "Emma", "Ava", "Charlotte", "Sophia", "Amelia",
            "Isabella", "Mia", "Evelyn", "Harper", "Camila", "Gianna", "Elizabeth", "Luna", "Ella", "Avery", "Scarlett", "Emily", "Aria", "Layla", "Liam", "Noah",
            "Oliver", "Elijah", "William", "James", "Benjamin", "Lucas", "Henry", "Alexander", "Mason", "Michael", "Ethan", "Daniel", "Logan", "Jackson", "Sebastian",
            "Jack", "Aiden", "Owen", "Olivia", "Emma", "Ava", "Charlotte", "Sophia", "Amelia", "Isabella", "Mia", "Evelyn", "Harper", "Camila", "Gianna", "Elizabeth", "Luna",
            "Ella", "Avery", "Scarlett", "Emily", "Aria", "Layla", "Liam", "Noah", "Oliver", "Elijah", "William", "James", "Benjamin", "Lucas", "Henry", "Alexander", "Mason",
            "Michael", "Ethan", "Daniel", "Logan", "Jackson", "Sebastian", "Jack", "Aiden", "Owen", "Olivia", "Emma", "Ava", "Charlotte", "Sophia", "Amelia", "Isabella", "Mia",
            "Evelyn", "Harper", "Camila", "Gianna", "Elizabeth", "Luna", "Ella", "Avery", "Scarlett", "Emily", "Aria", "Layla", "Liam", "Noah", "Oliver", "Elijah", "William",
            "James", "Benjamin", "Lucas", "Henry", "Alexander", "Mason", "Michael", "Ethan", "Daniel", "Logan", "Jackson", "Sebastian", "Jack", "Aiden", "Owen", "Olivia", "Emma", "Ava", "Charlotte",
            "Sophia", "Amelia", "Isabella", "Mia", "Evelyn", "Harper", "Camila", "Gianna", "Elizabeth", "Luna", "Ella", "Avery", "Scarlett", "Emily", "Aria", "Layla", "Liam", "Noah", "Oliver", "Elijah",
            "William", "James", "Benjamin", "Lucas", "Henry", "Alexander", "Mason", "Michael", "Ethan", "Daniel", "Logan", "Jackson", "Sebastian", "Jack", "Aiden", "Owen", "Olivia", "Emma", "Ava", "Charlotte",
            "Sophia", "Amelia", "Isabella", "Mia", "Evelyn", "Harper", "Camila", "Gianna", "Elizabeth", "Luna", "Ella", "Avery", "Scarlett", "Emily", "Aria", "Layla"
    };
    static String[] lastNames = {
            "Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis", "Rodriguez", "Martinez", "Hernandez", "Lopez", "Gonzalez", "Wilson", "Anderson", "Thomas", "Taylor", "Moore", "Jackson",
            "Martin", "Lee", "Perez", "Thompson", "White", "Harris", "Sanchez", "Clark", "Ramirez", "Lewis", "Robinson", "Walker", "Young", "Allen", "King", "Wright", "Scott", "Torres", "Nguyen", "Hill", "Flores",
            "Green", "Adams", "Nelson", "Baker", "Hall", "Rivera", "Campbell", "Mitchell", "Carter", "Roberts", "Gomez", "Phillips", "Evans", "Turner", "Diaz", "Parker", "Cruz", "Edwards", "Collins", "Reyes", "Stewart",
            "Morris", "Morales", "Murphy", "Cook", "Rogers", "Gutierrez", "Ortiz", "Morgan", "Cooper", "Peterson", "Bailey", "Reed", "Kelly", "Howard", "Ramos", "Kim", "Cox", "Ward", "Richardson", "Watson", "Brooks",
            "Chavez", "Wood", "James", "Bennett", "Gray", "Mendoza", "Ruiz", "Hughes", "Price", "Alvarez", "Castillo", "Sanders", "Patel", "Myers", "Long", "Ross", "Foster", "Jimenez", "Powell", "Jenkins", "Perry",
            "Russell", "Sullivan", "Bell", "Coleman", "Butler", "Henderson", "Barnes", "Gonzales", "Fisher", "Vasquez", "Simmons", "Romero", "Jordan", "Patterson", "Alexander", "Hamilton", "Graham", "Reynolds",
            "Griffin", "Wallace", "Moreno", "West", "Cole", "Hayes", "Bryant", "Herrera", "Gibson", "Ellis", "Tran", "Medina", "Aguilar", "Stevens", "Murray", "Ford", "Castro", "Marshall", "Owens", "Harrison",
            "Fernandez", "Mcdonald", "Woods", "Washington", "Kennedy", "Wells", "Vargas", "Henry", "Chen", "Freeman", "Webb", "Tucker", "Guzman", "Burns", "Crawford", "Olson", "Simpson", "Porter", "Hunter",
            "Gordon", "Mendez", "Silva", "Shaw", "Snyder", "Mason", "Dixon", "Munoz", "Hunt", "Hicks", "Holmes", "Palmer", "Wagner", "Black", "Robertson", "Boyd", "Rose", "Stone", "Salazar", "Fox", "Warren",
            "Mills", "Meyer", "Rice", "Schmidt", "Garza", "Daniels", "Ferguson", "Nichols", "Stephens", "Soto", "Weaver", "Ryan", "Gardner", "Payne", "Grant", "Dunn", "Kelley", "Spencer", "Hawkins", "Arnold",
            "Pierce", "Vazquez", "Hansen", "Peters", "Santos", "Hart", "Bradley", "Knight", "Elliott", "Cunningham", "Duncan", "Armstrong", "Hudson", "Carroll", "Lane", "Riley", "Andrews", "Alvarado", "Ray",
            "Delgado", "Berry", "Perkins", "Hoffman", "Johnston", "Matthews", "Pena", "Richards", "Contreras", "Willis", "Carpenter", "Lawrence", "Sandoval", "Guerrero", "George", "Chapman", "Rios", "Estrada",
            "Ortega", "Watkins", "Greene", "Nunez", "Wheeler", "Valdez", "Harper", "Burke", "Larson", "Santiago", "Maldonado", "Morrison", "Franklin", "Carlson", "Austin", "Dominguez", "Carr", "Lawson", "Jacobs",
            "Obrien", "Lynch", "Singh", "Vega", "Bishop", "Montgomery", "Oliver", "Jensen", "Harvey", "Williamson", "Gilbert", "Dean", "Sims", "Espinoza", "Howell", "Li", "Wong", "Reid", "Hanson", "Le", "Mccoy",
            "Garrett", "Burton", "Fuller", "Wang", "Weber", "Welch", "Rojas", "Lucas", "Marquez", "Fields", "Park", "Yang", "Little", "Banks", "Padilla", "Day", "Walsh", "Bowman", "Schultz", "Luna", "Fowler", "Mejia",
            "Davidson", "Acosta", "Brewer", "May", "Holland", "Juarez", "Newman", "Pearson", "Curtis", "Cortez", "Douglas", "Schneider", "Joseph", "Barrett", "Navarro", "Figueroa", "Keller", "Avila", "Wade", "Molina",
            "Stanley", "Hopkins", "Campos", "Barnett", "Bates", "Chambers", "Caldwell", "Beck", "Lambert", "Miranda", "Byrd", "Craig", "Ayala", "Lowe", "Frazier", "Powers", "Neal", "Leonard", "Gregory", "Carrillo",
            "Sutton", "Fleming", "Rhodes", "Shelton", "Schwartz", "Norris", "Jennings", "Watts", "Duran", "Walters", "Cohen", "Mcdaniel", "Moran", "Parks", "Steele", "Vaughn", "Becker", "Holt", "Deleon", "Barker",
            "Terry", "Hale", "Leon", "Hail", "Benson", "Haynes", "Horton", "Miles", "Lyons", "Pham", "Graves", "Bush", "Thornton", "Wolfe", "Warner", "Cabrera", "Mckinney", "Mann", "Zimmerman", "Dawson", "Lara",
            "Fletcher", "Page", "Mccarthy", "Love", "Robles", "Cervantes", "Solis", "Erickson", "Reeves", "Chang", "Klein", "Salinas", "Fuentes", "Baldwin", "Daniel", "Simon", "Velasquez", "Hardy", "Higgins",
            "Aguirre", "Lin", "Cummings", "Chandler", "Sharp", "Barber", "Bowen", "Ochoa", "Dennis", "Robbins", "Liu", "Ramsey", "Francis", "Griffith", "Paul", "Blair", "Oconnor", "Cardenas", "Pacheco", "Cross",
            "Calderon", "Quinn", "Moss", "Swanson", "Chan", "Rivas", "Khan", "Rodgers", "Serrano", "Fitzgerald", "Rosales", "Stevenson", "Christensen", "Manning", "Gill", "Curry", "Mclaughlin", "Harmon", "Mcgee",
            "Gross", "Doyle", "Garner", "Newton", "Burgess", "Reese", "Walton", "Blake", "Trujillo", "Adkins", "Brady", "Goodman", "Roman", "Webster", "Goodwin", "Fischer", "Huang", "Potter", "Delacruz", "Montoya",
            "Todd", "Wu", "Hines", "Mullins", "Castaneda", "Malone", "Cannon", "Tate", "Mack", "Sherman", "Hubbard", "Hodges", "Zhang", "Guerra", "Wolf", "Valencia", "Franco", "Saunders", "Rowe", "Gallagher", "Farmer",
            "Hammond", "Hampton", "Townsend", "Ingram", "Wise", "Gallegos", "Clarke", "Barton", "Schroeder", "Maxwell", "Waters", "Logan", "Camacho", "Strickland", "Norman", "Person", "Colon", "Parsons", "Frank",
            "Harrington", "Glover", "Osborne", "Buchanan", "Casey", "Floyd", "Patton", "Ibarra", "Ball", "Tyler", "Suarez", "Bowers", "Orozco", "Salas", "Cobb", "Gibbs", "Andrade", "Bauer", "Conner", "Moody", "Escobar",
            "Mcguire", "Lloyd", "Mueller", "Hartman", "French", "Kramer", "Mcbride", "Pope", "Lindsey", "Velazquez", "Norton", "Mccormick", "Sparks", "Flynn", "Yates", "Hogan", "Marsh", "Macias", "Villanueva", "Zamora",
            "Pratt", "Stokes", "Owen", "Ballard", "Lang", "Brock", "Villarreal", "Charles", "Drake", "Barrera", "Cain", "Patrick", "Pineda", "Burnett", "Mercado", "Santana", "Shepherd", "Bautista", "Ali", "Shaffer", "Lamb",
            "Trevino", "Mckenzie", "Hess", "Beil", "Olsen", "Cochran", "Morton", "Nash", "Wilkins", "Petersen", "Briggs", "Shah", "Roth", "Nicholson", "Holloway", "Lozano", "Flowers", "Rangel", "Hoover", "Arias", "Short",
            "Mora", "Valenzuela", "Bryan", "Meyers", "Weiss", "Underwood", "Bass", "Greer", "Summers", "Houston", "Carson", "Morrow", "Clayton", "Whitaker", "Decker", "Yoder", "Collier", "Zuniga", "Carey", "Wilcox", "Melendez",
            "Poole", "Roberson", "Larsen", "Conley", "Davenport", "Copeland", "Massey", "Lam", "Huff", "Rocha", "Cameron", "Jefferson", "Hood", "Monroe", "Anthony", "Pittman", "Huynh", "Randall", "Singleton", "Kirk", "Combs",
            "Mathis", "Christian", "Skinner", "Bradford", "Richard", "Galvan", "Wall", "Boone", "Kirby", "Wilkinson", "Bridges", "Bruce", "Atkinson", "Velez", "Meza", "Roy", "Vincent", "York", "Hodge", "Villa", "Abbott", "Allison",
            "Tapia", "Gates", "Chase", "Sosa", "Sweeney", "Farrell", "Wyatt", "Dalton", "Horn", "Barron", "Phelps", "Yu", "Dickerson", "Heath", "Foley", "Atkins", "Mathews", "Bonilla", "Acevedo", "Benitez", "Zavala", "Hensley",
            "Glenn", "Cisneros", "Harrell", "Shields", "Rubio", "Choi", "Huffman", "Boyer", "Garrison", "Arroyo", "Bond", "Kane", "Hancock", "Callahan", "Dillon", "Cline", "Wiggins", "Grimes", "Arellano", "Melton", "Oneill", "Savage",
            "Ho", "Beltran", "Pitts", "Parrish", "Ponce", "Rich", "Booth", "Koch", "Golden", "Ware", "Brennan", "Mcdowell", "Marks", "Cantu", "Humphrey", "Baxter", "Sawyer", "Clay", "Tanner", "Hutchinson", "Kaur", "Berg", "Wiley",
            "Gilmore", "Russo", "Villegas", "Hobbs", "Keith", "Wilkerson", "Ahmed", "Beard", "Mcclain", "Montes", "Mata", "Rosario", "Vang", "S", "S", "Walter", "Henson", "Oneal", "Mosley", "Mcclure", "Beasley", "Stephenson", "Snow",
            "Huerta", "Preston", "Vance", "Barry", "Johns", "Eaton", "Blackwell", "Dyer", "Prince", "Macdonald", "Solomon", "Guevara", "Stafford", "English", "Hurst", "Woodard", "Cortes", "Shannon", "Kemp", "Nolan", "Mccullough",
            "Merritt", "Murillo", "Moon", "Salgado", "Strong", "Kline", "Cordova", "Barajas", "Roach", "Rosas", "Winters", "Jacobson", "Lester", "Knox", "Bullock", "Kerr", "Leach", "Meadows", "Davila", "Orr", "Whitehead", "Pruitt",
            "Kent", "Conway", "Mckee", "Barr", "David", "Dejesus", "Marin", "Berger", "Mcintyre", "Blankenship", "Gaines", "Palacios", "Cuevas", "Bartlett", "Durham", "Dorsey", "Mccall", "Odonnell", "Stein", "Browning", "Stout",
            "Lowery", "Sloan", "Mclean", "Hendricks", "Calhoun", "Sexton", "Chung", "Gentry", "Hull", "Duarte", "Ellison", "Nielsen", "Gillespie", "Buck", "Middleton", "Sellers", "Leblanc", "Esparza", "Hardin", "Bradshaw",
            "Mcintosh", "Howe", "Livingston", "Frost", "Glass", "Morse", "Knapp", "Herman", "Stark", "Bravo", "Noble", "Spears", "Weeks", "Corona", "Frederick", "Buckley", "Mcfarland", "Hebert", "Enriquez", "Hickman", "Quintero",
            "Randolph", "Schaefer", "Walls", "Trejo", "House", "Reilly", "Pennington", "Michael", "Conrad", "Giles", "Benjamin", "Crosby", "Fitzpatrick", "Donovan", "Mays", "Mahoney", "Valentine", "Raymond", "Medrano", "Hahn", "Mcmillan",
            "Small", "Bentley", "Felix", "Peck", "Lucero", "Boyle", "Hanna", "Pace", "Rush", "Hurley", "Harding", "Mcconnell", "Bernal", "Nava", "Ayers", "Everett", "Ventura", "Avery", "Pugh", "Mayer", "Bender", "Shepard", "Mcmahon",
            "Landry", "Case", "Sampson", "Moses", "Magana", "Blackburn", "Dunlap", "Gould", "Duffy", "Vaughan", "Herring", "Mckay", "Espinosa", "Rivers", "Farley", "Bernard", "Ashley", "Friedman", "Potts", "Truong", "Costa", "Correa",
            "Blevins", "Nixon", "Clements", "Fry", "Delarosa", "Best", "Benton", "Lugo", "Portillo", "Dougherty", "Crane", "Haley", "Phan", "Villalobos", "Blanchard", "Horne", "Finley", "Quintana", "Lynn", "Esquivel", "Bean", "Dodson",
            "Mullen", "Xiong", "Hayden", "Cano", "Levy", "Huber", "Richmond", "Moyer", "Lim", "Frye", "Sheppard", "Mccarty", "Avalos", "Booker", "Waller", "Parra", "Woodward", "Jaramillo", "Krueger", "Rasmussen", "Brandt", "Peralta",
            "Donaldson", "Stuart", "Faulkner", "Maynard", "Galindo", "Coffey", "Estes", "Sanford", "Burch", "Maddox", "Vo", "Oconnell", "Vu", "S", "S", "Andersen", "Spence", "Mcpherson", "Church", "Schmitt", "Stanton", "Leal", "Cherry",
            "Compton", "Dudley", "Sierra", "Pollard", "Alfaro", "Hester", "Proctor", "Lu", "Hinton", "Novak", "Good", "Madden", "Mccann", "Terrell", "Jarvis", "Dickson", "Reyna", "Cantrell", "Mayo", "Branch", "Hendrix", "Rollins", "Rowland",
            "Whitney", "Duke", "Odom", "Daugherty", "Travis", "Tang"
    };

    static Map<String, List<String>> attributePool = new LinkedHashMap<>();

    static Map<String, String> motherTongueMap = Map.of(
            "en", "English",
            "tr", "Turkish",
            "de", "German",
            "ar", "Arabic",
            "fr", "French",
            "es", "Spanish",
            "ru", "Russian",
            "ja", "Japanese",
            "it", "Italian",
            "pt", "Portuguese"
    );

    static {
        // Physical Attributes
        attributePool.put("hairColor", List.of("black", "blonde", "brown", "red", "auburn", "gray", "white"));
        attributePool.put("hairLength", List.of("short", "medium", "long"));
        attributePool.put("hairType", List.of("straight", "wavy", "curly"));
        attributePool.put("eyeColor", List.of("blue", "green", "brown", "hazel", "gray", "amber"));
        attributePool.put("skinTone", List.of("fair", "medium", "dark"));
        attributePool.put("isWearingGlasses", List.of("yes", "no"));

        // Medical & Legal
        attributePool.put("bloodType", List.of("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"));
        attributePool.put("isSmoker", List.of("yes", "no", "former smoker"));

        // Social & Background
        attributePool.put("isMarried", List.of("yes", "no"));
        attributePool.put("hasChildren", List.of("0", "1", "3"));
        attributePool.put("educationLevel", List.of("high school","associate", "bachelor", "master", "PhD"));
        attributePool.put("jobType", List.of("unemployed","office", "manual", "remote", "freelance", "self-employed"));
        attributePool.put("ownsCar", List.of("yes", "no"));
        attributePool.put("hasDriversLicense", List.of("yes", "no"));
        attributePool.put("motherTongue", new ArrayList<>(motherTongueMap.keySet())); // store keys for later replacement
    }

    private static String generateRandomAttributesJson() {
        Random random = new Random();
        Map<String, String> attributes = new LinkedHashMap<>();

        // Fixed attributes (height/weight always included)
        attributes.put("height", String.valueOf(getRandomNumber(150, 200)));
        attributes.put("weight", String.valueOf(getRandomNumber(45, 120)));

        // Randomly add 5-8 other attributes
        List<String> keys = new ArrayList<>(attributePool.keySet());
        Collections.shuffle(keys);
        int attributesToAdd = getRandomNumber(5, 8);

        for (int i = 0; i < attributesToAdd; i++) {
            String key = keys.get(i);
            String value = attributePool.get(key).get(random.nextInt(attributePool.get(key).size()));

            // Handle motherTongue mapping
            if (key.equals("motherTongue")) {
                value = motherTongueMap.get(value);
            }
            attributes.put(key, value);
        }

        // Convert to JSON with proper escaping
        StringBuilder json = new StringBuilder("{");
        int i = 0;
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            json.append("\"").append(entry.getKey()).append("\": \"").append(entry.getValue()).append("\"");
            if (i++ < attributes.size() - 1) json.append(", ");
        }
        json.append("}");

        return json.toString();
    }

    public static void main(String[] args) {
        String fileName = "persons.csv";
        int maxRecords = 1_000_000;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("firstName,lastName,email,age,attributes\n");

            for (int count = 0; count < maxRecords; count++) {
                String firstName = getRandom(firstNames);
                String lastName = getRandom(lastNames);
                String email = (firstName + lastName + count + "@mail.com").toLowerCase();
                int age = getRandomNumber(18, 70);
                String jsonAttributes = generateRandomAttributesJson();

                // Format CSV row with escaped JSON
                String row = String.format(
                        "%s,%s,%s,%d,\"%s\"\n",
                        firstName,
                        lastName,
                        email,
                        age,
                        jsonAttributes.replace("\"", "\"\"")  // Escape quotes for CSV
                );

                writer.write(row);

                // Progress tracking
                if (count % 100_000 == 0) {
                    System.out.println("Processed: " + count);
                    writer.flush();
                }
            }

            System.out.println("✅ CSV created successfully. Records: " + maxRecords);
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }

    private static String getRandom(String[] array) {
        return array[new Random().nextInt(array.length)];
    }

    private static int getRandomNumber(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }

//    private static String generateRandomAttributesJson() {
//        Random random = new Random();
//        List<String> keys = new ArrayList<>(attributePool.keySet());
//        Collections.shuffle(keys);
//
//        int attributeCount = getRandomNumber(5, 10); // 5 to 10 attributes per person
//
//        Map<String, String> selectedAttributes = new LinkedHashMap<>();
//
//        // Always include randomized height & weight
//        selectedAttributes.put("height", String.valueOf(getRandomNumber(150, 200)));
//        selectedAttributes.put("weight", String.valueOf(getRandomNumber(45, 120)));
//
//        int used = 0;
//        for (String key : keys) {
//            if (used >= attributeCount - 2) break; // already included height+weight
//            List<String> values = attributePool.get(key);
//            String value = values.get(random.nextInt(values.size()));
//
//            // convert motherTongue short code to full language name
//            if (key.equals("motherTongue")) {
//                value = motherTongueMap.get(value);
//            }
//
//            selectedAttributes.put(key, value);
//            used++;
//        }
//
//        // Convert to JSON manually
//        StringBuilder json = new StringBuilder("{");
//        int i = 0;
//        for (Map.Entry<String, String> entry : selectedAttributes.entrySet()) {
//            json.append("\"").append(entry.getKey()).append("\":\"").append(entry.getValue()).append("\"");
//            if (i++ < selectedAttributes.size() - 1) json.append(",");
//        }
//        json.append("}");
//        return json.toString();
//    }
}

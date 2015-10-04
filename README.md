#Destination Unknown
A text adventure creation engine.
Now you can make a text adventure **without knowing a programming language!**

###How to use it
The engine reads information through a json file titled `gameconfig.json` which needs to be in the same directory as the core jar file.

It would help to learn the [json format](http://www.w3schools.com/json/), else you can look at the default config file and see get the jist of it.

#####Things you can customize
* Player
 * Max health
 * Starting health
 * Starting Room
 * Inventory
* Non Player Characters
 * Name
 * Unique or not (Mario vs A Mario)
 * Health
 * Gender
 * inventory
 * Items that drop when killed
* Items
 * Name
 * Damage
 * Returned Health on consumption
 * Unique or not
* Rooms
 * Name
 * Description (Printed on first visit of room and when the player 'look's)
 * Items in room
 * NPCs in room 

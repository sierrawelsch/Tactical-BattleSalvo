## Changes I made from PA03 to PA04
- Initialized a ThisBoard and OtherBoard object in the BattleSalvoController class so that I have access to both board objects within
both player classes. This allowed us to change a former implementation in which we passed a UserPlayer object into a ComputerPlayer object.
- added a setFleet method to the Board class to be utilized within the 
setup method since the boards are now initialized in the constructor rather than the player
- changed the constructor of the UserPlayer and the ComputerPlayer class to take in a 2 Board objects because I initialize 
my Boards in the constructor and want to have access to them in the Player objects I create
- removed throwing an io exception in takeShots 
UTEID: kdh2289; jhm2464;
FIRSTNAME: Kaelin; Jeremiah;
LASTNAME: Hooper; Martinez;
CSACCOUNT: kaelin; jeremiah;
EMAIL: kaelin@cs.utexas.edu; Jeremiah.H.Martinez@utexas.edu;

[Program 1]
[Description]
This program aims to bypass the firewall sought after in the last project by (in which we created and abstracted the ability to read and write to objects from the main class, SystemSecurity) via a cover channel. SystemSecurity uses the ReferenceMonitor class, which in turn manages the Subject class and uses the ObjectManager class to manage the Object_ class, to do all of the reading and writing, based on the command that was read in. A LinkedHashMap serves as the internal datastructure that the Subjects and Object_’s are stored in. Furthermore, if a Subject attempts to read an object that does not exist, a zero will be returned and stored as its new internal TEMP value. The program will continue executing commands until the end of the file is reached. Through this infrastructure, we found and created a covert channel between two subjects, Hal and Lyle. To do this depends on whether or not Hal creates an object and the feedback Lyle gets from this when trying to create an object of the same name. For instance, if Hal wants to send a 1, Hal creates an object (CREATE HAL OBJ), then Lyle attempts to create the same object (CREATE LYLE OBJ), write to it (WRITE LYLE OBJ 1), then read it (READ LYLE OBJ). In this case, Lyle will read a 0 because the creation of OBJ at Lyle's level failed (because OBJ already existed, created by Hal and at Hal's level). Thus, Hal successfully transmits a 0. Conversely, if Hal wants to transmit a 1, he simply doesn't create OBJ. When Lyle follows this by creating (successfully), writing a 1 to, and reading OBJ, the read will result in a 1. Thus, Hal will have transmitted a 1.

[Finish]
Finished all requirements.

[Test Cases]
[Input of test 1]
There are 10 types of people in the world: those who understand binary, and those who don't.
[Output of test 1]
There are 10 types of people in the world: those who understand binary, and those who don't.

[Input of test 2]
Text file (test2-ArtameneouleGrandCyrus.txt) was too big to paste here.

[Output of test 2]
Text file (test2-ArtameneouleGrandCyrus.txt.out) was too big to paste here.
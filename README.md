# StaticTypeCheckerJolie
A type checking system for Jolie programming langauge. The typing rules are based on Nielsen's thesis

## How to setup the Project
1. Get Intellij IDEA up and running
2. Open project with "jolie-root" as main directory
3. Go to File > Project Structure
4. Remove all existing libraries or modules if present
5. Add jolie\src and libjolie\src as source Folders in the Modules section

![alt How it should look like](https://github.com/Danielatonge/StaticTypeCheckerJolie/blob/master/images/jolie_setup_1.PNG)

6. Make sure you have similar "Edit Configurations"

![alt location of edit config](https://github.com/Danielatonge/StaticTypeCheckerJolie/blob/master/images/jolie_setup_3.PNG)
![alt edit configurations](https://github.com/Danielatonge/StaticTypeCheckerJolie/blob/master/images/jolie_setup2.PNG)

7. Navigate to the "StaticTypeChecker" class found in ```jolie-root > jolie > src > jolie > StaticTypeChecker``` to compile and run the project
6. When project is setup (i.e after compiling), be sure to check "jolie-root\checker.z3", This is where the output will be printed
7. You can then validate the output using https://rise4fun.com/Z3/tutorial
8. The jolie file where you can re-write your tests and re-compile is found at "test\try.ol"

## Enjoy the beauty of Jolie


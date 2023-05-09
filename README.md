To compile the program, we need 2 steps:
- Step 1: 
javac -d compile src/foo/bar/*.java
  - **Note: This will compile all files with .java extension and put it in a folder name "compile"
- Step 2:
java -cp ./compile Main
  - **Note: This will run the compiled Main java file located in the "compile" folder

The test files for the Customer.java and ValidationHelper.java files are located in the unitTest folder. 
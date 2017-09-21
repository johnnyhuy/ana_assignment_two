make:
	javac -cp .:mazeSolver/SampleSolver.jar *.java

run:
	java -cp .:mazeSolver/SampleSolver.jar MazeTester 

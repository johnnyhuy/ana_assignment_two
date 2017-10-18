GENDIR = mazeGenerator
REC_BACK = 	RecursiveBacktrackerGenerator.class
PRIMS = 	ModifiedPrimsGenerator.class
GROW_TREE =	GrowingTreeGenerator.class

make:
	@echo "compiling..."
	javac -cp .:mazeSolver/SampleSolver.jar *.java

clean:
	@echo "cleaning..."
	rm -f $(GENDIR)/$(REC_BACK)
	rm -f $(GENDIR)/$(PRIMS)
	rm -f $(GENDIR)/$(GROW_TREE)

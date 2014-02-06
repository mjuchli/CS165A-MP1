JFLAGS = -g
JC = javac
JVM= java
FILE=
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java
	
CLASSES = \
    NPuzzle/Main.java \
    NPuzzle/Node.java \
    NPuzzle/Search.java \
    NPuzzle/AStarSearch.java
    
MAIN = Main
default: classes
classes: $(CLASSES:.java=.class)
run: $(MAIN).class
	$(JVM) $(MAIN)
clean:
	$(RM) *.class

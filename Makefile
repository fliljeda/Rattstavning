main: ClosestWords.java Main.java
	javac *.java
	time cat ordlista.txt testfall/testord2.indata | java Main > /dev/null

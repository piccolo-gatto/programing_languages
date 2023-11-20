MAIN_FILE = src/main/kotlin/Main.kt
OUT_FILE = ./target/main.jar

test:
	mvn test

clean:
	mvn clean

kotlin:
	kotlinc $(MAIN_FILE) -include-runtime -d $(OUT_FILE)

java:
	java -jar $(OUT_FILE)

run: kotlin java clean
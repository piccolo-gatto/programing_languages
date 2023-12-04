KOTLINC = kotlinc
MAIN_FILE = src/main/kotlin/Main.kt
TEST_FILE = src/test/kotlin/TestSpecialHashMap.kt

ifeq ($(OS), Windows_NT)
	RM = del
else
	RM = rm
endif

test:
	gradle $(TEST_FILE)

clean:
	gradle clean

kotlin:
	$(KOTLINC) $(MAIN_FILE)


run: kotlin clean
# ===========================
# BUILD TARGET
# ===========================
# Do not remove or rename this
.PHONY: java21-cli-build
java21-cli-build:
	./mvnw install -DtestFailureIgnore=true

# ===========================
# RUN TARGET
# ===========================
# Do not remove or rename this
.PHONY: java21-cli-run
java21-cli-run: ;

# ===========================
# RUN COMMAND (IDE RUN BUTTON)
# ===========================
# Starts the Spring Boot application safely
.PHONY: lab-run
lab-run:
	# ensure tmp directory exists
	mkdir -p ./tmp
	# compile code
	./mvnw compile
	# stop any previous Spring Boot instance
	./mvnw spring-boot:stop || true
	# start Spring Boot app
	./mvnw spring-boot:start -Dspring-boot.run.jvmArguments="-Djava.io.tmpdir=./tmp"

# ===========================
# TEST COMMAND (IDE TEST BUTTON)
# ===========================
# Runs the currently open test file only
.PHONY: lab-test
lab-test:
ifeq ($(FILE),)
	@echo "No file open, running all tests..."
	./mvnw test
else
	@echo "Running test for file: $(FILE)"
	./mvnw -Dtest=$(subst src/test/kotlin/,,$(subst src/test/java/,,$(basename $(FILE)))) test
endif

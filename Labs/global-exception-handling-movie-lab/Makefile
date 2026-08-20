MVN := $(shell which mvn 2>/dev/null || echo ./mvnw)

# ===========================
# BUILD TARGET
# ===========================
# Do not remove or rename this
.PHONY: java21-cli-build
java21-cli-build:
	$(MVN) install -DtestFailureIgnore=true

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
	$(MVN) compile
	# stop any previous Spring Boot instance
	$(MVN) spring-boot:stop || true
	# start Spring Boot app
	$(MVN) spring-boot:start -Dspring-boot.run.jvmArguments="-Djava.io.tmpdir=./tmp"

# ===========================
# TEST COMMAND (IDE TEST BUTTON)
# ===========================
# Runs the currently open test file only
.PHONY: lab-test
lab-test:
ifeq ($(FILE),)
	@echo "No file open, running all tests..."
	$(MVN) test
else
	@echo "Running test for file: $(FILE)"
	$(MVN) -Dtest=$(subst src/test/kotlin/,,$(subst src/test/java/,,$(basename $(FILE)))) test
endif


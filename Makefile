.PHONY: help
help: ## Show help
	@echo Please specify a build target. The choices are:
	@grep -E '^[0-9a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'

.PHONY: format
format: ## Format code
	@echo "Applying coding standards formatting"
	@./gradlew goJF

.PHONY: lint
lint: ## Lint Open API spec
	@echo "Linting Open API spec"
	@npm run lint

.PHONY: check
check: lint ## Run code quality checks
	@echo "Running test suite..."
	@./gradlew check
	@echo "Running mutation test suite..."
	@./gradlew pitest --info

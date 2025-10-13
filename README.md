# Flask Hello World - CI/CD Demo

This repository contains a simple Python Flask application designed to demonstrate CI/CD workflows. The project showcases how a clean and organized codebase, combined with tools like Makefiles, can simplify the migration between different CI/CD solutions.

## Project Structure

- **`src/`**: Contains the source code for the Flask application.
- **`tests/`**: Includes unit tests for the application.
- **`charts/`**: Helm chart for deploying the application to Kubernetes.
- **`ci-cd/`**: Contains CI/CD pipeline configurations (e.g., Jenkins pipelines).
- **`infra/`**: (Planned) Will contain Proof-of-Concept (PoC) infrastructure configurations for quick instance setups.
- **`Makefile`**: Centralized management for building, testing, and deploying the application and Helm chart.

## Features

- **Flask Application**: A simple "Hello World" app to demonstrate CI/CD workflows.
- **Helm Chart**: Kubernetes deployment templates for the application.
- **Makefile**: Simplifies common tasks such as building Docker images, running tests, and managing Helm charts.
- **CI/CD Pipelines**: Jenkins pipelines for building, testing, and deploying the application.

## Makefile Commands

The `Makefile` provides a set of commands to manage the application lifecycle. Below are some of the key commands:

- **Install Dependencies**: `make install`
- **Run Application**: `make run`
- **Build Docker Image**: `make build`
- **Run Docker Container**: `make run-docker`
- **Lint Code**: `make lint`
- **Run Tests**: `make test`
- **Helm Commands**:
  - Lint Chart: `make helm-lint`
  - Render Templates: `make helm-template`
  - Install Chart: `make helm-install`
  - Upgrade Release: `make helm-upgrade`

For a full list of commands, refer to the `Makefile`.

## CI/CD Pipelines

The `ci-cd/` folder contains Jenkins pipeline configurations:

- **`Jenkinsfile.ci`**: CI pipeline for building, testing, and linting the application.
- **`Jenkinsfile.helm-ci`**: CI pipeline for managing Helm charts.

Future updates will include additional CI/CD solutions to demonstrate the ease of migration between tools.

## Key Takeaway

This project highlights the importance of maintaining a clean and organized codebase. By centralizing common tasks in a `Makefile`, the project ensures:

- **Ease of Migration**: Switching between CI/CD solutions becomes seamless.
- **Consistency**: Developers can use the same commands locally and in CI/CD pipelines.
- **Simplicity**: Reduces the learning curve for new contributors.

## Getting Started

0. Create a python virtual environment (optional but recommended)

1. Clone the repository:
   ```bash
   git clone https://github.com/brzezinskilukasz/flask-hello-world.git
   cd flask-hello-world
   ```

2. Install dependencies:
   ```bash
   make install
   ```

3. Run the application:
   ```bash
   make run
   ```

4. Run tests:
   ```bash
   make test
   ```

## Future Plans

- Add more CI/CD solutions to demonstrate flexibility.
- Introduce the `infra/` folder for PoC infrastructure setups.
- Expand the Helm chart for advanced Kubernetes deployments.

## Contributing

Contributions are welcome! Feel free to open issues or submit pull requests to improve the project.

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.
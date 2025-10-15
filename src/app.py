"""
A simple Flask web application that returns "Hello, World!" at the root URL.

Used to demonstrate CI/CD pipelines with linting and testing.

Endpoints:
- `/` : Returns a friendly HTTP greeting.
- `/ready` : Readiness check endpoint, returns `OK` with status 200.
- `/health` : Health check endpoint, returns `OK` with status 200 if healthy, otherwise `Service Unhealthy` with status 500.
- `/kill` : Simulates service failure by marking the service as unhealthy.
- `/revive` : Revives the service by marking it as healthy again.

Global Variables:
- `healthy` : A boolean flag to simulate the health status of the service.
"""

from flask import Flask

app = Flask(__name__)

healthy = True


@app.route('/')
def hello_world():
    """Return a friendly HTTP greeting."""
    return 'Hello, World!'


@app.route('/ready')
def readiness_check():
    """Return a simple readiness check response."""
    return 'OK', 200


@app.route('/health')
def health_check():
    """Return a simple health check response."""
    if not healthy:
        return 'Service Unhealthy', 500
    return 'OK', 200


@app.route('/kill')
def kill_service():
    """Kill the service."""
    global healthy

    if not healthy:
        return 'I am already dead, please check my status via /health', 200

    healthy = False
    return 'Ouch', 200


@app.route('/revive')
def revive_service():
    """Revive the service."""
    global healthy

    if healthy:
        return 'I am already alive, please check my status via /health', 200

    healthy = True
    return 'I am alive again!', 200

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)

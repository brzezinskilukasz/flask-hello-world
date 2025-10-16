"""
Smoke tests for the Flask Hello World application.
Used in CI/CD pipelines to verify application functionality.
"""

import requests

BASE_URL = "http://localhost:5000"

def test_hello_world():
    """Test the root endpoint."""
    response = requests.get(f"{BASE_URL}/", timeout=5)
    assert response.status_code == 200, (
        f"Expected status code 200 but got {response.status_code}")
    assert response.text == "Hello, World!", (
        f"Expected response text 'Hello, World!' but got {response.text}")
    print("Root endpoint test passed.")

def test_readiness_check():
    """Test the readiness endpoint."""
    response = requests.get(f"{BASE_URL}/ready", timeout=5)
    assert response.status_code == 200, f"Expected status code 200 but got {response.status_code}"
    assert response.text == "OK", f"Expected response text 'OK' but got {response.text}"
    print("Readiness check test passed.")

def test_health_check():
    """Test the health endpoint when healthy."""
    response = requests.get(f"{BASE_URL}/health", timeout=5)
    assert response.status_code == 200, f"Expected status code 200 but got {response.status_code}"
    assert response.text == "OK", f"Expected response text 'OK' but got {response.text}"
    print("Health check test passed.")

def test_kill_service():
    """Test the kill endpoint."""
    response = requests.get(f"{BASE_URL}/kill", timeout=5)
    assert response.status_code == 200, f"Expected status code 200 but got {response.status_code}"
    assert response.text == "Ouch", f"Expected response text 'Ouch' but got {response.text}"
    print("Kill service test passed.")

def test_kill_service_again():
    """Test the kill endpoint when already dead."""
    response = requests.get(f"{BASE_URL}/kill", timeout=5)
    assert response.status_code == 200, f"Expected status code 200 but got {response.status_code}"
    assert response.text == "I am already dead, please check my status via /health", (
        f"Expected response text 'I am already dead, please check my status via /health'"
        f" but got {response.text}")
    print("Kill service while it's already dead test passed.")

def test_health_check_unhealthy():
    """Test the health endpoint after killing the service."""
    response = requests.get(f"{BASE_URL}/health", timeout=5)
    assert response.status_code == 500, f"Expected status code 500 but got {response.status_code}"
    assert response.text == "Service Unhealthy", (
        f"Expected response text 'Service Unhealthy' but got {response.text}")
    print("Health check (unhealthy) test passed.")

def test_revive_service():
    """Test the revive endpoint."""
    response = requests.get(f"{BASE_URL}/revive", timeout=5)
    assert response.status_code == 200, f"Expected status code 200 but got {response.status_code}"
    assert response.text == "I am alive again!", (
        f"Expected response text 'I am alive again!' but got {response.text}")
    print("Revive service test passed.")

def test_revive_service_again():
    """Test the revive endpoint when already alive."""
    response = requests.get(f"{BASE_URL}/revive", timeout=5)
    assert response.status_code == 200, f"Expected status code 200 but got {response.status_code}"
    assert response.text == "I am already alive, please check my status via /health", (
        f"Expected response text 'I am already alive, please check my status via /health'"
        f" but got {response.text}")
    print("Revive service while it's already alive test passed.")

def test_health_check_healthy():
    """Test the health endpoint after reviving the service."""
    response = requests.get(f"{BASE_URL}/health", timeout=5)
    assert response.status_code == 200, f"Expected status code 200 but got {response.status_code}"
    assert response.text == "OK", f"Expected response text 'OK' but got {response.text}"
    print("Health check (healthy) test passed.")

if __name__ == "__main__":
    test_hello_world()
    test_readiness_check()
    test_health_check()
    test_kill_service()
    test_kill_service_again()
    test_health_check_unhealthy()
    test_revive_service()
    test_revive_service_again()
    test_health_check_healthy()
    print("All tests passed!")

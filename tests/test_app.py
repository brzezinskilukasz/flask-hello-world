"""Unit tests for the Flask application."""

import unittest
from app import app

class AppTestCase(unittest.TestCase):
    """Test case for the Flask application."""
    def setUp(self):
        self.app = app.test_client()
        self.app.testing = True

    def test_hello_world(self):
        """Test the root endpoint."""
        response = self.app.get('/')
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data.decode('utf-8'), 'Hello, World!')

    def test_readiness_check(self):
        """Test the readiness check endpoint."""
        response = self.app.get('/ready')
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data.decode('utf-8'), 'OK')

    def test_health_check(self):
        """Test the health check endpoint."""
        response = self.app.get('/health')
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data.decode('utf-8'), 'OK')

    def test_kill_service(self):
        """Test killing the service."""
        response = self.app.get('/kill')
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data.decode('utf-8'), 'Ouch')
        # Ensure the service is unhealthy now
        response = self.app.get('/health')
        self.assertEqual(response.status_code, 500)
        self.assertEqual(response.data.decode('utf-8'), 'Service Unhealthy')

    def test_kill_service_when_already_dead(self):
        """Test killing the service when it's already dead."""
        self.app.get('/kill')  # First kill
        response = self.app.get('/kill')  # Second kill
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data.decode('utf-8'),
                         'I am already dead, please check my status via /health')

    def test_revive_service(self):
        """Test reviving the service."""
        response = self.app.get('/revive')
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data.decode('utf-8'), 'I am alive again!')
        # Ensure the service is healthy again
        response = self.app.get('/health')
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data.decode('utf-8'), 'OK')

    def test_revive_service_when_already_alive(self):
        """Test reviving the service when it's already alive."""
        response = self.app.get('/revive')  # First revive
        response = self.app.get('/revive')  # Second revive
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data.decode('utf-8'),
                         'I am already alive, please check my status via /health')

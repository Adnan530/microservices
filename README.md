Example Scenario for Circuit Breaker and Retry:
A downstream microservice becomes temporarily unavailable due to a network issue. A retry mechanism is triggered (for example, 3 retries with exponential backoff).
If the retries fail, the circuit breaker opens, and all further requests to that service are rejected immediately to avoid overloading the failing service.
After some time, the circuit breaker enters the half-open state and allows a limited number of requests to pass through to check if the service has recovered.
If the service recovers, the circuit breaker closes, and normal traffic resumes.

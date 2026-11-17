#!/bin/bash
echo "--- Direct backend"; curl -s http://localhost:8081/api/v1/internal/accounts/ping; echo
echo "--- Via gateway /accounts/ping"; curl -s http://localhost:8080/accounts/ping; echo
echo "--- Via gateway /accounts"; curl -s http://localhost:8080/accounts; echo
echo "--- Via gateway /accounts/1001"; curl -s http://localhost:8080/accounts/1001; echo

#!/usr/bin/env bash
set -euo pipefail

java ${JAVA_OPTS:-} -jar /app/jiangdk.jar &
java_pid=$!

nginx -g "daemon off;" &
nginx_pid=$!

shutdown() {
  kill -TERM "$java_pid" "$nginx_pid" 2>/dev/null || true
  wait "$java_pid" 2>/dev/null || true
  wait "$nginx_pid" 2>/dev/null || true
}

trap shutdown TERM INT

set +e
wait -n "$java_pid" "$nginx_pid"
exit_code=$?
set -e

shutdown
exit "$exit_code"

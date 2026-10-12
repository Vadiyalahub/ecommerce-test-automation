#!/usr/bin/env bash
# Shortcut for the common test runs.
#
# Usage: ./run-tests.sh [unit|smoke|regression|all] [--headed]
#   --headed  opens a visible browser window so you can watch the tests

set -euo pipefail

SUITE="${1:-smoke}"
HEADLESS=true
if [[ "${2:-}" == "--headed" ]]; then
  HEADLESS=false
fi

UNIT_TESTS="PriceUtilsTest,SortUtilsTest,TestUserRepositoryTest"

run_ui() {
  mvn -B test -Dtest=RunCucumberTest -Dheadless="$HEADLESS" -Dcucumber.filter.tags="$1"
}

case "$SUITE" in
  unit)       mvn -B test -Dtest="$UNIT_TESTS" ;;
  smoke)      run_ui "@smoke" ;;
  regression) run_ui "@smoke or @regression" ;;
  all)        mvn -B test -Dheadless="$HEADLESS" ;;
  *)
    echo "Unknown suite: $SUITE"
    echo "Usage: ./run-tests.sh [unit|smoke|regression|all] [--headed]"
    exit 1
    ;;
esac

echo
echo "HTML report: target/cucumber-reports/cucumber.html"

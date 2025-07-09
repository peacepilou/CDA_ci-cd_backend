source "$(dirname "$0")/../utils.sh"

echo
echo_separator_general
echo_yellow "⚡ Running unit tests..."
echo_separator_general

if mvn test -Punit-tests; then
  echo
  echo_green "✅ Unit tests passed"
else
  echo_separator
  echo_red "❌ Unit tests failed. Fix them before running integration tests."
  echo_separator
  exit 1
fi

echo
echo_separator_general
echo_yellow "⚡ Running integration tests..."
echo_separator_general

if mvn test -Pintegration-tests; then
  echo
  echo_green "✅ Integration tests passed"
else
  echo_separator
  echo_red "❌ Integration tests failed. Please fix them."
  echo_separator
  exit 1
fi

echo
echo_separator_general
echo_green "🎉 All tests passed successfully!"
echo_separator_general
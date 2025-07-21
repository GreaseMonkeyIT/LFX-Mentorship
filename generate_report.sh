#!/bin/bash

# Configuration
FORK_URL="https://github.com/GreaseMonkeyIT/LFX-Mentorship"
SRC_FILE="src/main/scala/stack/StackModule.scala"
TB_FILE="stack_tb.py"
REPORT_FILE="report.md"
RESULTS_FILE="results.xml"

# Check files exist
if [[ ! -f "$SRC_FILE" || ! -f "$TB_FILE" || ! -f "$RESULTS_FILE" ]]; then
    echo "Missing one or more required files: $SRC_FILE, $TB_FILE, or $RESULTS_FILE"
    exit 1
fi

# Write report
cat > $REPORT_FILE <<EOF
# LFX Mentorship Fall 2025 — StackModule Submission

**Fork URL:** $FORK_URL

---

## 1. Source Code

### \`$SRC_FILE\`

\`\`\`scala
$(cat "$SRC_FILE")
\`\`\`

### \`$TB_FILE\`

\`\`\`python
$(cat "$TB_FILE")
\`\`\`

---

## 2. Test Results

\`\`\`
$(if grep -q "<failure" "$RESULTS_FILE"; then echo "stack_tb FAILED"; else echo "stack_tb PASSED"; fi)
\`\`\`

*Generated on: $(date +"%Y-%m-%d %H:%M")*
EOF

echo "Report generated: $REPORT_FILE"

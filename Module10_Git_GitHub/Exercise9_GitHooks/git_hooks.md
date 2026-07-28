# Exercise 9: Git Hooks

## Objective
Automate tasks with Git hooks — scripts that run automatically at specific points in the Git workflow.

---

## 1. What are Git Hooks?

Git hooks are scripts in `.git/hooks/` that execute automatically before or after Git events like commit, push, and merge.

### Hook Types

| Hook | Trigger | Use Case |
|------|---------|----------|
| **pre-commit** | Before commit is created | Lint, format, run tests |
| **prepare-commit-msg** | After default message, before editor | Add ticket numbers |
| **commit-msg** | After commit message is entered | Validate message format |
| **post-commit** | After commit is completed | Notifications |
| **pre-push** | Before push to remote | Run full test suite |
| **pre-rebase** | Before rebase starts | Prevent rebase on main |
| **post-merge** | After merge completes | Install dependencies |
| **post-checkout** | After checkout/switch | Environment setup |

---

## 2. Creating Hooks

### Pre-Commit Hook — Code Quality

```bash
#!/bin/bash
# .git/hooks/pre-commit
# Runs before every commit

echo "🔍 Running pre-commit checks..."

# Check for debugging statements
if git diff --cached --name-only | xargs grep -l "System.out.println" 2>/dev/null; then
    echo "❌ ERROR: Found System.out.println statements!"
    echo "   Please use a proper logger (SLF4J/Logback)."
    exit 1
fi

# Check for TODO/FIXME in staged files
TODOS=$(git diff --cached --diff-filter=ACM | grep -c "TODO\|FIXME" || true)
if [ "$TODOS" -gt 0 ]; then
    echo "⚠️  WARNING: Found $TODOS TODO/FIXME comments in staged changes."
    echo "   Consider addressing them before committing."
fi

# Run checkstyle or spotless (if using Maven)
if [ -f "pom.xml" ]; then
    echo "🧹 Running code formatter check..."
    mvn spotless:check -q 2>/dev/null
    if [ $? -ne 0 ]; then
        echo "❌ ERROR: Code formatting issues found!"
        echo "   Run 'mvn spotless:apply' to fix."
        exit 1
    fi
fi

echo "✅ Pre-commit checks passed!"
exit 0
```

### Commit-Msg Hook — Conventional Commits

```bash
#!/bin/bash
# .git/hooks/commit-msg
# Validates commit message format

COMMIT_MSG_FILE=$1
COMMIT_MSG=$(cat "$COMMIT_MSG_FILE")

# Conventional Commits pattern: type(scope): description
PATTERN="^(feat|fix|docs|style|refactor|perf|test|build|ci|chore|revert)(\(.+\))?: .{1,72}$"

if ! echo "$COMMIT_MSG" | head -1 | grep -qE "$PATTERN"; then
    echo "❌ ERROR: Invalid commit message format!"
    echo ""
    echo "Expected format: type(scope): description"
    echo ""
    echo "Valid types:"
    echo "  feat     — New feature"
    echo "  fix      — Bug fix"
    echo "  docs     — Documentation only"
    echo "  style    — Code style (formatting, semicolons)"
    echo "  refactor — Code refactoring"
    echo "  perf     — Performance improvement"
    echo "  test     — Adding or updating tests"
    echo "  build    — Build system or dependencies"
    echo "  ci       — CI/CD configuration"
    echo "  chore    — Maintenance tasks"
    echo "  revert   — Revert a previous commit"
    echo ""
    echo "Examples:"
    echo "  feat(auth): Add JWT token refresh"
    echo "  fix: Resolve null pointer in OrderService"
    echo "  docs: Update API documentation"
    echo ""
    echo "Your message: $COMMIT_MSG"
    exit 1
fi

echo "✅ Commit message format is valid."
exit 0
```

### Pre-Push Hook — Run Tests

```bash
#!/bin/bash
# .git/hooks/pre-push
# Runs tests before pushing

BRANCH=$(git rev-parse --abbrev-ref HEAD)

echo "🚀 Running pre-push checks on branch: $BRANCH"

# Prevent direct push to main
if [ "$BRANCH" = "main" ]; then
    echo "❌ ERROR: Direct push to 'main' is not allowed!"
    echo "   Please create a Pull Request instead."
    exit 1
fi

# Run tests
if [ -f "pom.xml" ]; then
    echo "🧪 Running Maven tests..."
    mvn test -q -B
    if [ $? -ne 0 ]; then
        echo "❌ ERROR: Tests failed! Push aborted."
        exit 1
    fi
fi

echo "✅ All pre-push checks passed!"
exit 0
```

---

## 3. Setting Up Hooks

```bash
# Hooks are stored in .git/hooks/ (not tracked by Git)
ls .git/hooks/
# pre-commit.sample, commit-msg.sample, etc.

# Enable a sample hook by removing .sample
cp .git/hooks/pre-commit.sample .git/hooks/pre-commit
chmod +x .git/hooks/pre-commit

# Create a custom hook
cat > .git/hooks/pre-commit << 'HOOK'
#!/bin/bash
echo "Running pre-commit hook..."
# Your checks here
HOOK
chmod +x .git/hooks/pre-commit
```

---

## 4. Sharing Hooks with the Team

Since `.git/hooks/` is not tracked, use one of these approaches:

### Approach A: Custom hooks directory

```bash
# Create a tracked hooks directory
mkdir -p .githooks

# Move hooks there
cp .git/hooks/pre-commit .githooks/

# Configure Git to use this directory
git config core.hooksPath .githooks

# Commit the hooks
git add .githooks/
git commit -m "chore: Add shared Git hooks"
```

### Approach B: Setup script

```bash
# setup-hooks.sh
#!/bin/bash
echo "Setting up Git hooks..."
cp .githooks/* .git/hooks/
chmod +x .git/hooks/*
echo "✅ Git hooks installed!"
```

### Approach C: Maven plugin (for Java projects)

```xml
<!-- pom.xml -->
<plugin>
    <groupId>com.rudikershaw.gitbuildhook</groupId>
    <artifactId>git-build-hook-maven-plugin</artifactId>
    <version>3.5.0</version>
    <configuration>
        <installHooks>
            <pre-commit>.githooks/pre-commit</pre-commit>
            <commit-msg>.githooks/commit-msg</commit-msg>
        </installHooks>
    </configuration>
    <executions>
        <execution>
            <goals><goal>install</goal></goals>
        </execution>
    </executions>
</plugin>
```

---

## 5. Bypassing Hooks (When Needed)

```bash
# Skip pre-commit and commit-msg hooks
git commit --no-verify -m "WIP: Quick save"
# OR
git commit -n -m "WIP: Quick save"

# Skip pre-push hook
git push --no-verify
```

> **Note:** Use `--no-verify` sparingly. If hooks are failing, fix the root cause.

---

## 6. Practical Exercise

```bash
# Step 1: Create a project
mkdir hook-demo && cd hook-demo
git init

# Step 2: Set up conventional commit hook
mkdir .githooks
cat > .githooks/commit-msg << 'HOOK'
#!/bin/bash
PATTERN="^(feat|fix|docs|style|refactor|test|chore)(\(.+\))?: .{1,72}$"
MSG=$(head -1 "$1")
if ! echo "$MSG" | grep -qE "$PATTERN"; then
    echo "❌ Bad commit message: $MSG"
    echo "   Use: type(scope): description"
    exit 1
fi
HOOK
chmod +x .githooks/commit-msg
git config core.hooksPath .githooks

# Step 3: Test the hook
echo "Hello" > file.txt
git add .
git commit -m "bad message"          # ❌ Rejected!
git commit -m "feat: Add greeting"   # ✅ Accepted!

# Step 4: Share with team
git add .githooks/
git commit -m "chore: Add commit-msg hook for conventional commits"
```

---

## Key Takeaways

1. Git hooks **automate quality checks** at key points in the workflow
2. **pre-commit**: Lint, format, prevent debug code
3. **commit-msg**: Enforce conventional commit messages
4. **pre-push**: Run tests, prevent direct push to main
5. Share hooks via a `.githooks/` directory with `core.hooksPath`
6. Use `--no-verify` to bypass hooks only when absolutely necessary

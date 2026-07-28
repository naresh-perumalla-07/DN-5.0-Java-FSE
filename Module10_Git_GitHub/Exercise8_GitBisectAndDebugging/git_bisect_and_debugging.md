# Exercise 8: Git Bisect and Debugging

## Objective
Use `git bisect` to perform binary search through commit history to find the exact commit that introduced a bug. Learn additional debugging tools like `git blame` and `git log -S`.

---

## 1. Git Bisect

### What is Bisect?
`git bisect` uses **binary search** to find the commit that introduced a bug. Instead of checking every commit, it halves the search space each time.

```
100 commits → 7 steps to find the bug (log₂ 100 ≈ 7)
1000 commits → 10 steps
```

### Basic Bisect Workflow

```bash
# Step 1: Start bisect
git bisect start

# Step 2: Mark the current commit as bad (has the bug)
git bisect bad

# Step 3: Mark a known good commit (before the bug existed)
git bisect good v1.0.0
# OR
git bisect good abc1234

# Git checks out the midpoint commit
# Step 4: Test the code at this commit
mvn test
# OR manually test the feature

# Step 5: Mark this commit as good or bad
git bisect good  # Bug is NOT present here
# OR
git bisect bad   # Bug IS present here

# Git checks out the next midpoint
# Repeat steps 4-5 until Git finds the exact commit:
# "abc1234 is the first bad commit"

# Step 6: End bisect and return to original branch
git bisect reset
```

### Automated Bisect with a Test Script

```bash
# Create a test script that exits 0 (good) or 1 (bad)
cat > test_bug.sh << 'EOF'
#!/bin/bash
# Compile and run the test
mvn test -pl module-name -Dtest=UserServiceTest#testLogin -q
EOF
chmod +x test_bug.sh

# Run automated bisect
git bisect start
git bisect bad HEAD
git bisect good v1.0.0
git bisect run ./test_bug.sh

# Git automatically tests each midpoint and reports the first bad commit
# Result: "abc1234 is the first bad commit"

git bisect reset
```

### Bisect Log and Replay

```bash
# View bisect history
git bisect log

# Save bisect log to replay later
git bisect log > bisect_log.txt

# Replay a saved bisect session
git bisect replay bisect_log.txt

# Skip a commit (can't test — e.g., build is broken)
git bisect skip
```

---

## 2. Git Blame

### Find Who Changed Each Line

```bash
# Show author and commit for each line
git blame src/main/java/com/app/UserService.java

# Output:
# abc1234 (Naresh  2026-07-15  1) public class UserService {
# def5678 (Naresh  2026-07-20  2)     public User findById(Long id) {
# ghi9012 (Naresh  2026-07-25  3)         return repo.findById(id).orElse(null);
# abc1234 (Naresh  2026-07-15  4)     }
# abc1234 (Naresh  2026-07-15  5) }

# Blame specific line range
git blame -L 10,20 UserService.java

# Blame ignoring whitespace changes
git blame -w UserService.java

# Blame showing the commit that moved/copied lines
git blame -M UserService.java

# Blame showing the original file (before rename)
git blame -C UserService.java
```

---

## 3. Searching with Git Log

### Search for Changes to a String

```bash
# Find commits that changed the string "findById" (pickaxe search)
git log -S "findById" --oneline

# Find commits where a regex pattern was changed
git log -G "return.*null" --oneline

# Show the actual diff for matching commits
git log -S "findById" -p

# Search within a specific file
git log -S "findById" -- src/main/java/com/app/UserService.java
```

### Search Commit Messages

```bash
# Search for commits mentioning "login"
git log --grep="login" --oneline

# Case-insensitive search
git log --grep="LOGIN" -i --oneline

# Multiple search terms (OR)
git log --grep="fix" --grep="bug" --oneline

# Multiple search terms (AND)
git log --grep="fix" --grep="bug" --all-match --oneline
```

---

## 4. Git Reflog — Recovering Lost Commits

```bash
# View the reflog (reference log)
git reflog
# Output:
# abc1234 HEAD@{0}: commit: Add feature
# def5678 HEAD@{1}: checkout: moving from main to feature
# ghi9012 HEAD@{2}: reset: moving to HEAD~2
# jkl3456 HEAD@{3}: commit: Important work (now "lost")

# Recover a "lost" commit after reset
git checkout jkl3456
# OR create a branch from it
git branch recovered-work jkl3456

# Reflog entries expire after 90 days (default)
# For unreachable commits, it's 30 days
```

---

## 5. Practical Scenario — Finding a Regression

```bash
# Step 1: A test that was passing is now failing
mvn test -Dtest=OrderServiceTest#testCalculateTotal
# FAIL!

# Step 2: Find the last known good state
git log --oneline -20
# We know v2.0.0 (2 weeks ago) was working

# Step 3: Start bisect
git bisect start
git bisect bad HEAD
git bisect good v2.0.0

# Step 4: Automate with the failing test
git bisect run mvn test -Dtest=OrderServiceTest#testCalculateTotal -q -B

# Result:
# "a1b2c3d is the first bad commit"
# commit a1b2c3d
# Author: Dev <dev@example.com>
# Date: Thu Jul 24 14:30:00 2026
#     refactor: Optimize discount calculation
#
# Modified: src/main/java/com/app/OrderService.java

# Step 5: Examine the breaking commit
git show a1b2c3d

# Step 6: Fix the issue
git bisect reset
git revert a1b2c3d
# OR fix the logic and commit
```

---

## 6. Quick Reference

| Command | Description |
|---------|-------------|
| `git bisect start` | Begin binary search |
| `git bisect bad` | Current commit has the bug |
| `git bisect good <ref>` | This commit is bug-free |
| `git bisect run <script>` | Automate bisect with a test |
| `git bisect reset` | End bisect session |
| `git bisect skip` | Skip untestable commit |
| `git blame <file>` | Show per-line authorship |
| `git log -S "text"` | Find commits changing a string |
| `git log -G "regex"` | Find commits matching a regex diff |
| `git reflog` | View local reference history |

---

## Key Takeaways

1. **`git bisect`** finds the exact bug-introducing commit via binary search
2. **Automate bisect** with `git bisect run` and a test script for efficiency
3. **`git blame`** shows who last modified each line — useful for tracking ownership
4. **`git log -S`** (pickaxe) finds when a specific string was added or removed
5. **`git reflog`** is your safety net — it tracks every HEAD movement locally
6. These debugging tools together make it possible to trace any regression to its source

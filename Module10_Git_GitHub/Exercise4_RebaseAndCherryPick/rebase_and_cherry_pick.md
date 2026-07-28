# Exercise 4: Rebase and Cherry-Pick

## Objective
Understand `git rebase` for linear history and `git cherry-pick` for selectively applying commits.

---

## 1. Git Rebase

### What is Rebase?
Rebase re-applies your commits on top of another branch's latest commit, creating a **linear history**.

```
Before rebase:
main    ── C1 ── C2 ── C4
                  \
feature ────────── C3 ── C5

After rebase (git rebase main from feature):
main    ── C1 ── C2 ── C4
                         \
feature ──────────────── C3' ── C5'
```

### Basic Rebase

```bash
# You're on feature branch, main has moved ahead
git switch feature/user-profile

# Rebase your feature onto the latest main
git rebase main

# If conflicts occur during rebase:
# 1. Resolve conflicts in the files
# 2. Stage resolved files
git add resolved-file.java
# 3. Continue the rebase
git rebase --continue

# Abort the rebase entirely
git rebase --abort

# Skip a conflicting commit
git rebase --skip
```

### Interactive Rebase

Interactive rebase lets you **edit, squash, reorder, or drop commits** before they are applied.

```bash
# Rebase the last 4 commits interactively
git rebase -i HEAD~4
```

An editor opens with:
```
pick abc1234 Add user model
pick def5678 Fix typo in user model
pick ghi9012 Add user service
pick jkl3456 Add user controller

# Commands:
# p, pick   = use commit
# r, reword = use commit, but edit the commit message
# e, edit   = use commit, but stop for amending
# s, squash = use commit, but meld into previous commit
# f, fixup  = like squash, but discard this commit's log message
# d, drop   = remove commit
# x, exec   = run command
```

### Squash Commits Example

```bash
# Squash the typo fix into the previous commit
pick abc1234 Add user model
squash def5678 Fix typo in user model
pick ghi9012 Add user service
pick jkl3456 Add user controller

# Save and close — Git will prompt you to edit the combined commit message
```

Result:
```
Before: C1 ── C2 ── C3 ── C4  (4 commits)
After:  C1+C2 ── C3 ── C4     (3 commits, first two squashed)
```

### Reorder Commits

```bash
# Change the order of lines in the interactive editor
pick ghi9012 Add user service
pick abc1234 Add user model
pick jkl3456 Add user controller
```

---

## 2. Merge vs Rebase

| Aspect | Merge | Rebase |
|--------|-------|--------|
| History | Non-linear (preserves branch) | Linear (clean history) |
| Merge commits | Yes | No |
| Safety | Safe for shared branches | **Never rebase shared/public branches** |
| Conflict resolution | Once | Per replayed commit |
| Use case | Collaborative branches | Local feature cleanup |

### The Golden Rule of Rebase
> **Never rebase commits that have been pushed to a shared/public repository.**

```bash
# SAFE: Rebase your local feature before pushing
git switch feature/my-work
git rebase main
git switch main
git merge feature/my-work  # Fast-forward!

# DANGEROUS: Never do this
git switch main
git rebase feature/my-work  # Rewrites shared history!
```

---

## 3. Git Cherry-Pick

### What is Cherry-Pick?
Cherry-pick applies a **specific commit** from one branch to another without merging the entire branch.

```
Before:
main    ── C1 ── C2
                  \
feature ────────── C3 ── C4 ── C5

After cherry-pick C4 onto main:
main    ── C1 ── C2 ── C4'
                  \
feature ────────── C3 ── C4 ── C5
```

### Basic Cherry-Pick

```bash
# Find the commit hash you want
git log --oneline feature/payments
# Output:
# def5678 Add payment validation
# abc1234 Add payment gateway integration
# 789abcd WIP: payment UI

# Cherry-pick a specific commit onto current branch
git switch main
git cherry-pick abc1234

# Cherry-pick multiple commits
git cherry-pick abc1234 def5678

# Cherry-pick a range of commits
git cherry-pick abc1234..def5678   # Exclusive of abc1234
git cherry-pick abc1234^..def5678  # Inclusive of abc1234
```

### Cherry-Pick Options

```bash
# Cherry-pick without auto-committing (stage only)
git cherry-pick --no-commit abc1234
# Useful to combine multiple cherry-picks into one commit

# Cherry-pick and edit the commit message
git cherry-pick --edit abc1234

# If conflicts occur during cherry-pick:
git cherry-pick abc1234
# CONFLICT!
# Resolve conflicts, then:
git add resolved-file.java
git cherry-pick --continue

# Abort the cherry-pick
git cherry-pick --abort
```

---

## 4. Practical Scenarios

### Scenario A: Clean Up Feature Branch Before PR

```bash
# Your feature has messy commits
git log --oneline feature/search
# Output:
# e5f6g7h Fix search again
# c3d4e5f Oops, fix search bug
# a1b2c3d Add search feature
# 9z8y7x6 WIP search

# Interactive rebase to clean up
git switch feature/search
git rebase -i HEAD~4

# In editor:
pick 9z8y7x6 WIP search
squash a1b2c3d Add search feature
squash c3d4e5f Oops, fix search bug
squash e5f6g7h Fix search again

# Result: One clean commit "Add search feature"
```

### Scenario B: Hotfix Needs to Go to Multiple Branches

```bash
# Fix a critical bug on main
git switch main
echo "// Critical security fix" >> Auth.java
git add . && git commit -m "fix: Patch authentication vulnerability"
# Commit hash: abc1234

# Cherry-pick the fix to the release branch
git switch release/v2.0
git cherry-pick abc1234

# Cherry-pick the fix to develop
git switch develop
git cherry-pick abc1234
```

### Scenario C: Rebase Workflow for Clean Merges

```bash
# Daily workflow
git switch feature/notifications
# ... do work, make commits ...

# Before creating PR, update and clean up:
git fetch origin
git rebase origin/main           # Get latest changes
git rebase -i origin/main        # Squash/clean up commits

# Push (force push needed after rebase)
git push --force-with-lease origin feature/notifications

# Create PR → Reviewer merges with fast-forward
```

---

## 5. Quick Reference

| Command | Description |
|---------|-------------|
| `git rebase main` | Rebase current branch onto main |
| `git rebase -i HEAD~N` | Interactive rebase last N commits |
| `git rebase --continue` | Continue after resolving conflicts |
| `git rebase --abort` | Cancel the rebase |
| `git cherry-pick <hash>` | Apply specific commit to current branch |
| `git cherry-pick --no-commit` | Cherry-pick without committing |
| `git cherry-pick --abort` | Cancel the cherry-pick |

---

## Key Takeaways

1. **Rebase** creates linear history by replaying commits on a new base
2. **Interactive rebase** (`-i`) lets you squash, reorder, edit, or drop commits
3. **Never rebase shared/public branches** — it rewrites history
4. **Cherry-pick** applies individual commits to any branch
5. Use `--force-with-lease` (not `--force`) when pushing after rebase
6. Clean up feature branches with interactive rebase **before** creating PRs

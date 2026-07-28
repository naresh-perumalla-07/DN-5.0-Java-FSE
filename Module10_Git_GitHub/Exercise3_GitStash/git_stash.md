# Exercise 3: Git Stash

## Objective
Learn to temporarily shelve changes using `git stash` to switch contexts without committing incomplete work.

---

## 1. What is Git Stash?

`git stash` takes your uncommitted changes (both staged and unstaged), saves them on a stack, and reverts the working directory to match the HEAD commit.

```
Working Directory ──(git stash)──▶ Stash Stack
                                      │
                  ◀──(git stash pop)───┘
```

---

## 2. Basic Stash Operations

```bash
# Stash current changes
git stash

# Stash with a descriptive message
git stash save "WIP: halfway through login feature"
# OR (modern syntax)
git stash push -m "WIP: halfway through login feature"

# List all stashes
git stash list
# Output:
# stash@{0}: On feature/login: WIP: halfway through login feature
# stash@{1}: WIP on main: abc123 Previous stash

# Apply the most recent stash (keep it in stash list)
git stash apply

# Apply and remove the most recent stash
git stash pop

# Apply a specific stash
git stash apply stash@{1}

# Drop a specific stash
git stash drop stash@{0}

# Clear all stashes
git stash clear
```

---

## 3. Advanced Stash Options

```bash
# Stash including untracked files
git stash push -u -m "Include untracked files"
# OR
git stash push --include-untracked

# Stash including ignored files too
git stash push -a -m "Include everything"
# OR
git stash push --all

# Stash only specific files
git stash push -m "Stash only config" src/config.java src/settings.xml

# Stash interactively (choose hunks)
git stash push -p -m "Partial stash"

# Show stash contents (diff)
git stash show
git stash show -p          # Full diff
git stash show stash@{1}   # Specific stash
```

---

## 4. Creating a Branch from Stash

```bash
# Create a new branch and apply stash to it
git stash branch feature/recovered-work stash@{0}
# This:
# 1. Creates the new branch from the commit where stash was created
# 2. Applies the stash
# 3. Drops the stash if applied successfully
```

---

## 5. Practical Scenario

### Scenario: Urgent Bug Fix While Working on a Feature

```bash
# You're working on a new feature
git switch -c feature/dashboard
echo "class Dashboard { /* WIP */ }" > Dashboard.java
git add Dashboard.java

echo "class Widget { /* WIP */ }" > Widget.java
# Widget.java is untracked

# URGENT: Bug reported on main!

# Step 1: Stash your work (include untracked files)
git stash push -u -m "WIP: Dashboard feature — incomplete"

# Step 2: Switch to main and fix the bug
git switch main
git switch -c hotfix/null-pointer
echo "// Fixed null pointer check" >> App.java
git add . && git commit -m "fix: Add null check in App.java"

# Step 3: Merge the hotfix
git switch main
git merge --no-ff hotfix/null-pointer -m "Merge hotfix/null-pointer"
git branch -d hotfix/null-pointer

# Step 4: Return to your feature and restore work
git switch feature/dashboard
git stash pop
# Your Dashboard.java (staged) and Widget.java (untracked) are restored!

# Verify
git status
git stash list  # Stash is gone after pop
```

---

## 6. Stash Conflicts

```bash
# If applying a stash causes conflicts
git stash pop
# CONFLICT in file.txt

# Resolve the conflict manually, then:
git add file.txt
git commit -m "Resolve stash conflict"

# Note: On conflict, `git stash pop` does NOT drop the stash.
# You must manually drop it after resolving:
git stash drop stash@{0}
```

---

## 7. Quick Reference

| Command | Description |
|---------|-------------|
| `git stash` | Stash tracked changes |
| `git stash push -u` | Stash including untracked files |
| `git stash push -m "msg"` | Stash with message |
| `git stash push -p` | Interactive/partial stash |
| `git stash list` | List all stashes |
| `git stash show -p` | Show stash diff |
| `git stash apply` | Apply stash (keep in list) |
| `git stash pop` | Apply stash (remove from list) |
| `git stash drop stash@{N}` | Delete specific stash |
| `git stash branch <name>` | Create branch from stash |
| `git stash clear` | Delete all stashes |

---

## Key Takeaways

1. Use `git stash` to save incomplete work without committing
2. Always use `-m` for descriptive stash messages
3. Use `-u` to include untracked files in the stash
4. `pop` = `apply` + `drop` (but only drops on clean apply)
5. Stash is a **LIFO stack** — most recent stash is `stash@{0}`
6. Create a branch from stash when changes are more complex than expected

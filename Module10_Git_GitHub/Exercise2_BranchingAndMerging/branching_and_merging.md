# Exercise 2: Branching and Merging

## Objective
Master Git branching strategies, merging techniques, and understand how branches enable parallel development.

---

## 1. Understanding Branches

A branch is simply a lightweight movable pointer to a commit. The default branch is `main` (or `master`).

```
        main
         ▼
C1 ◄── C2 ◄── C3
                ▲
              feature
```

---

## 2. Branch Operations

### Creating Branches

```bash
# List all branches
git branch

# List all branches (including remote)
git branch -a

# Create a new branch
git branch feature/login

# Create and switch to a new branch
git checkout -b feature/login
# OR (modern syntax)
git switch -c feature/login

# Switch to an existing branch
git checkout main
# OR
git switch main

# Rename a branch
git branch -m old-name new-name

# Delete a branch (merged)
git branch -d feature/login

# Force delete a branch (unmerged)
git branch -D feature/login
```

---

## 3. Branching Strategy — Git Flow

```
main ──────●──────────────●──────────────●──────── (production releases)
            \            /                \
develop ─────●──●──●──●──●──●──●──●──●────●─────── (integration branch)
              \      /    \         /
feature/A ─────●──●──      \       /
                            \     /
feature/B ──────────────────●──●──
```

### Branch Types
| Branch | Purpose | Created From | Merges Into |
|--------|---------|-------------|-------------|
| `main` | Production-ready code | — | — |
| `develop` | Integration branch | `main` | `main` |
| `feature/*` | New features | `develop` | `develop` |
| `release/*` | Release preparation | `develop` | `main` & `develop` |
| `hotfix/*` | Emergency production fixes | `main` | `main` & `develop` |

---

## 4. Merging Branches

### Fast-Forward Merge
When the target branch has no new commits since the feature branched off.

```bash
# Start on main
git switch main

# Create and work on feature branch
git switch -c feature/add-user
echo "class User {}" > User.java
git add . && git commit -m "Add User class"

# Merge back (fast-forward)
git switch main
git merge feature/add-user
```

```
Before:  main ── C1 ── C2
                         \
         feature ──────── C3

After:   main ── C1 ── C2 ── C3  (fast-forward, no merge commit)
```

### Three-Way Merge
When both branches have diverged with new commits.

```bash
# Main has progressed
git switch main
echo "# Updated README" > README.md
git add . && git commit -m "Update README on main"

# Feature also has commits
git switch feature/add-user
echo "class Admin extends User {}" > Admin.java
git add . && git commit -m "Add Admin class"

# Merge creates a merge commit
git switch main
git merge feature/add-user -m "Merge feature/add-user into main"
```

```
Before:  main ── C1 ── C2 ── C4
                         \
         feature ──────── C3

After:   main ── C1 ── C2 ── C4 ── M  (merge commit)
                         \        /
         feature ──────── C3 ────
```

### No-Fast-Forward Merge
Force a merge commit even when fast-forward is possible (preserves branch history).

```bash
git merge --no-ff feature/add-user -m "Merge feature/add-user"
```

---

## 5. Handling Merge Conflicts

### When Conflicts Occur
Conflicts happen when the same lines are modified differently in two branches.

```bash
# Simulate a conflict
# On main
git switch main
echo "Hello from main" > greeting.txt
git add . && git commit -m "Add greeting on main"

# On feature
git switch -c feature/greeting
echo "Hello from feature" > greeting.txt
git add . && git commit -m "Add greeting on feature"

# Attempt merge
git switch main
git merge feature/greeting
# CONFLICT! Git cannot auto-merge
```

### Conflict Markers
```
<<<<<<< HEAD
Hello from main
=======
Hello from feature
>>>>>>> feature/greeting
```

### Resolving Conflicts

```bash
# Option 1: Manually edit the file and remove conflict markers
# Edit greeting.txt to desired content

# Option 2: Accept current branch version
git checkout --ours greeting.txt

# Option 3: Accept incoming branch version
git checkout --theirs greeting.txt

# After resolving, stage and commit
git add greeting.txt
git commit -m "Resolve merge conflict in greeting.txt"

# Abort the merge entirely
git merge --abort
```

---

## 6. Practical Exercise — Feature Branch Workflow

### Scenario: Build a Calculator App

```bash
# Step 1: Initialize repository
mkdir calculator-app && cd calculator-app
git init
echo "# Calculator App" > README.md
git add . && git commit -m "Initial commit"

# Step 2: Create develop branch
git switch -c develop

# Step 3: Feature — Addition
git switch -c feature/addition
cat > Calculator.java << 'EOF'
public class Calculator {
    public int add(int a, int b) {
        return a + b;
    }
}
EOF
git add . && git commit -m "feat: Add addition operation"

# Step 4: Merge addition feature
git switch develop
git merge --no-ff feature/addition -m "Merge feature/addition"
git branch -d feature/addition

# Step 5: Feature — Subtraction (parallel development)
git switch -c feature/subtraction
# Add subtract method to Calculator.java
git add . && git commit -m "feat: Add subtraction operation"

# Step 6: Merge subtraction feature
git switch develop
git merge --no-ff feature/subtraction -m "Merge feature/subtraction"
git branch -d feature/subtraction

# Step 7: Release
git switch main
git merge --no-ff develop -m "Release v1.0.0"
git tag -a v1.0.0 -m "Version 1.0.0 — Addition and Subtraction"

# Step 8: View the history
git log --oneline --graph --all --decorate
```

---

## 7. Useful Branch Commands

```bash
# See branches merged into current branch
git branch --merged

# See branches NOT merged into current branch
git branch --no-merged

# See the last commit on each branch
git branch -v

# Compare two branches
git diff main..develop

# Show commits in develop that are not in main
git log main..develop --oneline
```

---

## Key Takeaways

1. Branches are lightweight pointers — create them freely
2. **Fast-forward merges** produce a linear history
3. **Three-way merges** create merge commits that preserve branch topology
4. Use `--no-ff` to always preserve branch history in the log
5. Resolve conflicts manually, then `git add` + `git commit`
6. Follow a **branching strategy** (Git Flow / GitHub Flow) for team projects
7. Delete branches after merging to keep the repository clean

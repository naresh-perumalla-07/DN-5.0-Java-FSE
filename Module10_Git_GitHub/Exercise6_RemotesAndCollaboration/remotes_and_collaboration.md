# Exercise 6: Remotes and Collaboration

## Objective
Learn to work with remote repositories, collaborate using GitHub, and manage upstream/downstream workflows.

---

## 1. Remote Repository Basics

```bash
# Clone a remote repository
git clone https://github.com/username/repo.git
git clone https://github.com/username/repo.git my-local-name

# View remotes
git remote
git remote -v
# Output:
# origin  https://github.com/username/repo.git (fetch)
# origin  https://github.com/username/repo.git (push)

# Add a remote
git remote add origin https://github.com/username/repo.git
git remote add upstream https://github.com/original-author/repo.git

# Rename a remote
git remote rename origin old-origin

# Remove a remote
git remote remove upstream

# Show detailed info about a remote
git remote show origin
```

---

## 2. Fetch, Pull, and Push

### Fetch
Downloads objects and refs from remote but does **not** merge.

```bash
# Fetch from default remote (origin)
git fetch

# Fetch from a specific remote
git fetch upstream

# Fetch a specific branch
git fetch origin main

# Fetch and prune deleted remote branches
git fetch --prune
```

### Pull
Fetches and **merges** remote changes into the current branch.

```bash
# Pull from tracking branch
git pull

# Pull with rebase instead of merge
git pull --rebase

# Pull from specific remote/branch
git pull origin main

# Set pull to rebase by default
git config --global pull.rebase true
```

### Push
Uploads local commits to the remote repository.

```bash
# Push current branch to tracking remote
git push

# Push to a specific remote/branch
git push origin main

# Push and set upstream tracking
git push -u origin feature/login
# After this, `git push` and `git pull` work without specifying remote

# Force push (CAUTION — use after rebase)
git push --force-with-lease origin feature/login

# Push all branches
git push --all origin

# Push tags
git push origin --tags

# Delete a remote branch
git push origin --delete feature/old-branch
```

---

## 3. Tracking Branches

```bash
# List tracking relationships
git branch -vv
# Output:
# * main          abc1234 [origin/main] Latest commit
#   feature/login def5678 [origin/feature/login: ahead 2] WIP

# Set tracking branch
git branch --set-upstream-to=origin/main main
# OR
git branch -u origin/main

# Create a local branch tracking a remote branch
git switch --track origin/feature/auth
# OR
git checkout -b feature/auth origin/feature/auth
```

---

## 4. GitHub Collaboration Workflow

### Fork and Pull Request Model

```
Original Repository (upstream)
        │
        ▼ Fork
Your Fork on GitHub (origin)
        │
        ▼ Clone
Your Local Repository
```

```bash
# Step 1: Fork the repository on GitHub (via UI)

# Step 2: Clone your fork
git clone https://github.com/your-username/repo.git
cd repo

# Step 3: Add upstream remote
git remote add upstream https://github.com/original-owner/repo.git

# Step 4: Keep your fork updated
git fetch upstream
git switch main
git merge upstream/main
git push origin main

# Step 5: Create a feature branch
git switch -c feature/improve-docs

# Step 6: Make changes, commit, push
echo "Improved documentation" >> CONTRIBUTING.md
git add . && git commit -m "docs: Improve contributing guidelines"
git push -u origin feature/improve-docs

# Step 7: Create Pull Request on GitHub (via UI)
# Compare: original-owner/repo main ← your-username/repo feature/improve-docs
```

### Pull Request Best Practices
1. **Title**: Clear and descriptive (`feat: Add user authentication`)
2. **Description**: What, Why, and How — include screenshots for UI changes
3. **Size**: Keep PRs small and focused (< 400 lines ideally)
4. **Tests**: Include test results or evidence
5. **Reviewers**: Assign appropriate reviewers
6. **Labels**: Add relevant labels (bug, enhancement, etc.)

---

## 5. Code Review Process

### As a Reviewer
```bash
# Fetch the PR branch locally
git fetch origin pull/42/head:pr-42
git switch pr-42

# Review the code
git log main..pr-42 --oneline
git diff main..pr-42

# Test the changes locally
mvn test
```

### Review Feedback Types
| Type | Usage |
|------|-------|
| **Approve** | Code is ready to merge |
| **Request Changes** | Issues must be fixed before merging |
| **Comment** | General feedback, no blocking |

---

## 6. Handling Upstream Changes During PR

```bash
# Your PR branch has conflicts with main
git switch feature/my-pr
git fetch origin

# Option A: Merge main into your branch
git merge origin/main
# Resolve conflicts, commit

# Option B: Rebase onto main (cleaner history)
git rebase origin/main
# Resolve conflicts per commit, continue
git push --force-with-lease origin feature/my-pr
```

---

## 7. SSH vs HTTPS Authentication

### HTTPS
```bash
git clone https://github.com/username/repo.git
# Uses username + Personal Access Token (PAT)
# Cache credentials:
git config --global credential.helper store
```

### SSH
```bash
# Generate SSH key
ssh-keygen -t ed25519 -C "naresh.xplores777@gmail.com"

# Start SSH agent
eval "$(ssh-agent -s)"
ssh-add ~/.ssh/id_ed25519

# Copy public key to GitHub (Settings → SSH Keys)
cat ~/.ssh/id_ed25519.pub

# Clone with SSH
git clone git@github.com:username/repo.git

# Test connection
ssh -T git@github.com
```

---

## 8. Useful Collaboration Commands

```bash
# See who contributed to a file
git shortlog -sn

# See all contributors
git log --format='%aN' | sort -u

# Check if your branch is up to date
git fetch origin
git status  # Shows "behind by N commits"

# Clean up merged remote branches
git remote prune origin

# List remote branches
git branch -r
```

---

## Key Takeaways

1. `origin` is your fork/clone; `upstream` is the original repository
2. Use `git fetch` + merge/rebase for controlled updates (vs `git pull`)
3. Always work on **feature branches**, never directly on `main`
4. Use **Pull Requests** for code review before merging
5. Keep your fork synced with upstream regularly
6. Use `--force-with-lease` instead of `--force` for safer force pushes
7. Set up **SSH keys** for seamless authentication

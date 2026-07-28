# Exercise 10: Git Best Practices and Cheat Sheet

## Objective
Consolidate Git knowledge with industry best practices, workflows, and a comprehensive command reference.

---

## 1. Commit Best Practices

### Conventional Commits
```
<type>(<scope>): <description>

[optional body]

[optional footer(s)]
```

| Type | Purpose | Example |
|------|---------|---------|
| `feat` | New feature | `feat(auth): Add OAuth2 Google login` |
| `fix` | Bug fix | `fix(cart): Resolve incorrect total calculation` |
| `docs` | Documentation | `docs: Update API endpoint documentation` |
| `style` | Formatting (no logic change) | `style: Fix indentation in UserService` |
| `refactor` | Code restructuring | `refactor(db): Extract repository pattern` |
| `perf` | Performance improvement | `perf: Add caching to product queries` |
| `test` | Adding/updating tests | `test: Add unit tests for OrderService` |
| `build` | Build system/dependencies | `build: Upgrade Spring Boot to 3.3.0` |
| `ci` | CI/CD pipeline | `ci: Add SonarQube analysis step` |
| `chore` | Maintenance | `chore: Update .gitignore` |

### Atomic Commits
Each commit should represent **one logical change**:

```bash
# ❌ Bad: Too many changes in one commit
git commit -m "Fix login, add dashboard, update styles, refactor DB"

# ✅ Good: One concern per commit
git commit -m "fix(auth): Handle expired token gracefully"
git commit -m "feat(dashboard): Add revenue chart widget"
git commit -m "style: Apply consistent button styling"
git commit -m "refactor(db): Use connection pooling"
```

---

## 2. Branching Best Practices

### Naming Conventions
```bash
# Feature branches
feature/user-authentication
feature/JIRA-123-payment-gateway

# Bug fixes
fix/login-timeout
bugfix/JIRA-456-null-pointer

# Hotfixes (production emergencies)
hotfix/security-patch
hotfix/v1.2.1

# Releases
release/v1.0.0
release/2026-Q3

# Chores/Docs
chore/update-dependencies
docs/api-documentation
```

### Branch Lifecycle
```bash
# 1. Create from latest develop/main
git switch develop
git pull origin develop
git switch -c feature/JIRA-123-new-feature

# 2. Make small, frequent commits
git commit -m "feat(order): Add order creation endpoint"
git commit -m "test(order): Add integration tests for order API"

# 3. Keep branch updated
git fetch origin
git rebase origin/develop

# 4. Clean up before PR
git rebase -i origin/develop  # Squash WIP commits

# 5. Push and create PR
git push -u origin feature/JIRA-123-new-feature

# 6. After merge, clean up
git switch develop
git pull origin develop
git branch -d feature/JIRA-123-new-feature
```

---

## 3. Collaboration Best Practices

### Pull Request Checklist
- [ ] Branch is up to date with target branch
- [ ] All tests pass
- [ ] Code follows project style guidelines
- [ ] No debug/console statements left
- [ ] Documentation updated if needed
- [ ] Self-review completed
- [ ] PR description explains what, why, and how
- [ ] Screenshots attached for UI changes

### Code Review Guidelines
- **Be constructive**: "Consider using X for better performance" vs "This is wrong"
- **Ask questions**: "What happens if this is null?" vs "Handle null case"
- **Praise good code**: "Great abstraction here! 👍"
- **Focus on**: Logic, security, performance, readability, test coverage

---

## 4. .gitignore Best Practices

```gitignore
### Java ###
*.class
*.jar
*.war
*.ear
*.log
hs_err_pid*

### Maven ###
target/
pom.xml.tag
pom.xml.releaseBackup

### Gradle ###
.gradle/
build/

### IDE ###
.idea/
*.iml
.vscode/
*.swp
*.swo
.settings/
.project
.classpath
*.code-workspace

### OS ###
.DS_Store
Thumbs.db
desktop.ini

### Environment ###
.env
.env.local
*.properties.local
application-local.yml

### Dependencies ###
node_modules/

### Coverage & Reports ###
coverage/
*.lcov
jacoco/
```

---

## 5. Git Aliases (Productivity Boosters)

```bash
# Add to ~/.gitconfig or run git config commands

# Short status
git config --global alias.st "status -sb"

# Pretty log
git config --global alias.lg "log --oneline --graph --all --decorate"

# Amend without editing message
git config --global alias.oops "commit --amend --no-edit"

# Unstage all
git config --global alias.unstage "restore --staged ."

# Last commit
git config --global alias.last "log -1 HEAD --stat"

# List branches by last commit date
git config --global alias.recent "branch --sort=-committerdate --format='%(committerdate:short) %(refname:short)'"

# Interactive rebase shortcut
git config --global alias.ri "rebase -i"

# Diff of staged files
git config --global alias.staged "diff --staged"

# Undo last commit (keep changes)
git config --global alias.undo "reset --soft HEAD~1"

# Usage
git st            # git status -sb
git lg            # Pretty log graph
git oops          # Amend last commit
git undo          # Undo last commit
git recent        # Recent branches
```

---

## 6. Security Best Practices

```bash
# NEVER commit secrets
# ❌ Dangerous
echo "DB_PASSWORD=secret123" >> application.properties
git add . && git commit -m "Add config"  # Secret is now in history forever!

# ✅ Use environment variables or secret managers
# application.yml
# spring:
#   datasource:
#     password: ${DB_PASSWORD}

# If you accidentally commit a secret:
# 1. Rotate the secret immediately
# 2. Remove from history using BFG Repo-Cleaner
java -jar bfg.jar --replace-text passwords.txt repo.git
git reflog expire --expire=now --all
git gc --prune=now --aggressive

# Use git-secrets to prevent committing secrets
# https://github.com/awslabs/git-secrets
git secrets --install
git secrets --register-aws
```

---

## 7. Comprehensive Command Cheat Sheet

### Setup
| Command | Description |
|---------|-------------|
| `git init` | Initialize repository |
| `git clone <url>` | Clone remote repository |
| `git config --global user.name "Name"` | Set username |
| `git config --global user.email "email"` | Set email |

### Daily Workflow
| Command | Description |
|---------|-------------|
| `git status` | Show working tree status |
| `git add .` | Stage all changes |
| `git commit -m "msg"` | Commit staged changes |
| `git push` | Push to remote |
| `git pull --rebase` | Pull with rebase |
| `git fetch` | Download remote changes |

### Branching
| Command | Description |
|---------|-------------|
| `git branch` | List branches |
| `git switch -c <name>` | Create and switch branch |
| `git switch <name>` | Switch branch |
| `git merge <branch>` | Merge branch into current |
| `git branch -d <name>` | Delete merged branch |
| `git branch -D <name>` | Force delete branch |

### History & Inspection
| Command | Description |
|---------|-------------|
| `git log --oneline --graph` | Compact graph log |
| `git diff` | Show unstaged changes |
| `git diff --staged` | Show staged changes |
| `git show <commit>` | Show commit details |
| `git blame <file>` | Show line-by-line authorship |
| `git reflog` | Show HEAD movement history |

### Undoing
| Command | Description |
|---------|-------------|
| `git restore <file>` | Discard working changes |
| `git restore --staged <file>` | Unstage file |
| `git revert <commit>` | Create undo commit |
| `git reset --soft HEAD~1` | Undo commit, keep staged |
| `git reset --hard HEAD~1` | Undo commit, discard all |
| `git stash` | Shelve changes |
| `git stash pop` | Restore shelved changes |

### Advanced
| Command | Description |
|---------|-------------|
| `git rebase <branch>` | Rebase onto branch |
| `git rebase -i HEAD~N` | Interactive rebase |
| `git cherry-pick <hash>` | Apply specific commit |
| `git bisect start` | Begin binary search |
| `git tag -a v1.0 -m "msg"` | Create annotated tag |

---

## Key Takeaways

1. **Write atomic commits** — one logical change per commit
2. **Use conventional commits** — standardized, parseable messages
3. **Follow branch naming conventions** — `feature/`, `fix/`, `hotfix/`
4. **Never rebase public branches** — rebase local, merge shared
5. **Never commit secrets** — use environment variables or vaults
6. **Clean up before PRs** — squash WIP commits with interactive rebase
7. **Use aliases** — save time on repetitive commands
8. **Protect `main`** — require PRs, reviews, and passing CI

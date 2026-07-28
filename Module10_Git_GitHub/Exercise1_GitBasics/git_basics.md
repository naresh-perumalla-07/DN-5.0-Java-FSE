# Exercise 1: Git Basics

## Objective
Learn fundamental Git commands for version control — initializing repositories, staging, committing, and viewing history.

---

## 1. Git Configuration

```bash
# Set your identity
git config --global user.name "Naresh Perumalla"
git config --global user.email "naresh.xplores777@gmail.com"

# Set default branch name
git config --global init.defaultBranch main

# Set default editor
git config --global core.editor "code --wait"

# View all configuration
git config --list

# View specific config
git config user.name
```

### Configuration Levels
| Level | Flag | Scope | File Location |
|-------|------|-------|---------------|
| System | `--system` | All users on machine | `/etc/gitconfig` |
| Global | `--global` | Current user | `~/.gitconfig` |
| Local | `--local` | Current repository | `.git/config` |

> **Note:** Local overrides Global, which overrides System.

---

## 2. Initializing a Repository

```bash
# Create a new project directory
mkdir my-git-project
cd my-git-project

# Initialize a new Git repository
git init

# Verify the .git directory was created
ls -la .git/
```

### What `git init` creates:
```
.git/
├── HEAD            # Points to the current branch
├── config          # Repository-specific configuration
├── description     # Used by GitWeb
├── hooks/          # Client/server-side hook scripts
├── info/           # Global exclude patterns
├── objects/        # All content (blobs, trees, commits)
└── refs/           # Pointers to commit objects (branches, tags)
```

---

## 3. The Three States of Git

```
Working Directory  ──(git add)──▶  Staging Area  ──(git commit)──▶  Repository
   (Modified)                       (Staged)                        (Committed)
```

### Tracking Files

```bash
# Create sample files
echo "# My Project" > README.md
echo "public class Main {}" > Main.java
echo "*.class" > .gitignore

# Check the status of files
git status

# Stage a single file
git add README.md

# Stage multiple files
git add Main.java .gitignore

# Stage all changes
git add .

# Unstage a file (keep changes in working directory)
git restore --staged Main.java
```

---

## 4. Committing Changes

```bash
# Commit with a message
git commit -m "Initial commit: Add README, Main.java, and .gitignore"

# Commit with multi-line message
git commit -m "Add project structure" -m "- Created README.md with project description
- Added Main.java entry point
- Configured .gitignore for Java"

# Stage and commit tracked files in one step
git commit -am "Update README with description"

# Amend the last commit (change message or add files)
git add forgotten-file.txt
git commit --amend -m "Updated commit message"
```

### Commit Message Best Practices
- Use **imperative mood**: "Add feature" not "Added feature"
- First line: **50 chars max** (summary)
- Blank line, then body: **72 chars per line** (details)
- Reference issue numbers: `Fix #42`

---

## 5. Viewing History

```bash
# View commit history
git log

# Compact one-line log
git log --oneline

# Show last N commits
git log -n 5

# Graphical log with branches
git log --oneline --graph --all --decorate

# Log with file changes
git log --stat

# Log with full diff
git log -p

# Filter by author
git log --author="Naresh"

# Filter by date
git log --since="2026-01-01" --until="2026-07-28"

# Search commit messages
git log --grep="fix"
```

---

## 6. Inspecting Changes

```bash
# Show changes in working directory (unstaged)
git diff

# Show changes in staging area
git diff --staged

# Show changes between two commits
git diff abc123 def456

# Show details of a specific commit
git show HEAD
git show abc123

# Show who changed each line of a file
git blame README.md
```

---

## 7. Undoing Changes

```bash
# Discard changes in working directory
git restore README.md

# Unstage a file
git restore --staged README.md

# Revert a commit (creates a new commit that undoes changes)
git revert HEAD

# Reset to a previous commit (USE WITH CAUTION)
git reset --soft HEAD~1   # Keep changes staged
git reset --mixed HEAD~1  # Keep changes unstaged (default)
git reset --hard HEAD~1   # Discard all changes
```

### Reset vs Revert
| Command | History | Use Case |
|---------|---------|----------|
| `git revert` | Preserves history | Safe for shared branches |
| `git reset` | Rewrites history | Only for local/private branches |

---

## 8. .gitignore Patterns

```gitignore
# Compiled class files
*.class

# Build output
/target/
/build/

# IDE files
.idea/
*.iml
.vscode/
.settings/
.project
.classpath

# OS files
.DS_Store
Thumbs.db

# Environment files
.env
*.log

# Dependency directories
node_modules/
```

### Pattern Rules
| Pattern | Meaning |
|---------|---------|
| `*.class` | Ignore all `.class` files |
| `/build/` | Ignore `build` directory in root only |
| `build/` | Ignore all `build` directories |
| `!important.log` | Negate — do NOT ignore this file |
| `doc/**/*.pdf` | Ignore PDFs in `doc/` and subdirectories |

---

## Key Takeaways

1. `git init` creates a new repository
2. Files move through three states: **Modified → Staged → Committed**
3. `git add` stages changes, `git commit` saves them permanently
4. `git log` and `git diff` are essential for inspecting history
5. `git revert` is safer than `git reset` for undoing changes
6. `.gitignore` prevents unnecessary files from being tracked

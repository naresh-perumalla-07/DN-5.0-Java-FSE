# Exercise 7: GitHub Features

## Objective
Explore key GitHub features — Issues, Projects, Actions (CI/CD), GitHub Pages, and repository management.

---

## 1. GitHub Issues

### Creating Issues
Issues track bugs, feature requests, and tasks.

```markdown
# Issue Title: Fix login timeout on mobile devices

## Description
Users on mobile devices are experiencing timeouts when attempting to log in.

## Steps to Reproduce
1. Open the app on a mobile browser
2. Enter valid credentials
3. Click "Login"
4. Wait 30+ seconds → timeout error

## Expected Behavior
Login should complete within 5 seconds.

## Environment
- Browser: Chrome Mobile 120
- OS: Android 14
- Device: Pixel 8

## Labels: bug, priority-high, mobile
## Assignee: @naresh
## Milestone: v1.1.0
```

### Issue Templates
Create `.github/ISSUE_TEMPLATE/bug_report.md`:
```markdown
---
name: Bug Report
about: Report a bug to help us improve
title: '[BUG] '
labels: bug
assignees: ''
---

**Describe the bug**
A clear description of the bug.

**To Reproduce**
Steps to reproduce:
1. Go to '...'
2. Click on '...'
3. See error

**Expected behavior**
What you expected to happen.

**Screenshots**
If applicable, add screenshots.

**Environment:**
 - OS: [e.g., Windows 11]
 - Browser: [e.g., Chrome 120]
 - Version: [e.g., v1.0.0]
```

### Linking Issues and PRs
```markdown
# In a commit message:
git commit -m "Fix login timeout — closes #42"

# In a PR description:
Fixes #42
Resolves #42
Closes #42
```

---

## 2. GitHub Projects (Kanban Boards)

### Project Board Columns
```
┌─────────────┐  ┌─────────────┐  ┌─────────────┐  ┌──────────┐
│  📋 Backlog  │  │ 🔨 In Progress│  │ 👀 In Review │  │ ✅ Done  │
├─────────────┤  ├─────────────┤  ├─────────────┤  ├──────────┤
│ #45 Add API  │  │ #42 Fix login│  │ #40 Update UI│  │ #38 Auth │
│ #46 Docs     │  │ #43 Tests   │  │              │  │ #39 DB   │
│ #47 Refactor │  │             │  │              │  │          │
└─────────────┘  └─────────────┘  └─────────────┘  └──────────┘
```

### Automation Rules
- When an issue is **opened** → Move to **Backlog**
- When a PR is **linked** → Move to **In Progress**
- When a PR gets **review** → Move to **In Review**
- When a PR is **merged** → Move to **Done**

---

## 3. GitHub Actions (CI/CD)

### Basic Workflow
Create `.github/workflows/ci.yml`:

```yaml
name: Java CI with Maven

on:
  push:
    branches: [ main, develop ]
  pull_request:
    branches: [ main ]

jobs:
  build:
    runs-on: ubuntu-latest

    strategy:
      matrix:
        java-version: [ 17, 21 ]

    steps:
      - name: Checkout code
        uses: actions/checkout@v4

      - name: Set up JDK ${{ matrix.java-version }}
        uses: actions/setup-java@v4
        with:
          java-version: ${{ matrix.java-version }}
          distribution: 'temurin'
          cache: 'maven'

      - name: Build with Maven
        run: mvn clean install -B

      - name: Run tests
        run: mvn test -B

      - name: Generate test report
        if: always()
        uses: dorny/test-reporter@v1
        with:
          name: Maven Tests (Java ${{ matrix.java-version }})
          path: target/surefire-reports/*.xml
          reporter: java-junit
```

### Workflow Triggers
| Trigger | Description |
|---------|-------------|
| `push` | On push to specified branches |
| `pull_request` | On PR to specified branches |
| `schedule` | Cron-based schedule |
| `workflow_dispatch` | Manual trigger |
| `release` | On release creation |

### Environment Secrets
```yaml
steps:
  - name: Deploy
    env:
      DB_PASSWORD: ${{ secrets.DB_PASSWORD }}
      API_KEY: ${{ secrets.API_KEY }}
    run: ./deploy.sh
```

---

## 4. GitHub Pages

### Deploying a Static Site
```yaml
# .github/workflows/deploy-pages.yml
name: Deploy to GitHub Pages

on:
  push:
    branches: [ main ]

permissions:
  contents: read
  pages: write
  id-token: write

jobs:
  deploy:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4

      - name: Setup Pages
        uses: actions/configure-pages@v4

      - name: Upload artifact
        uses: actions/upload-pages-artifact@v3
        with:
          path: './docs'

      - name: Deploy to Pages
        uses: actions/deploy-pages@v4
```

---

## 5. Branch Protection Rules

### Recommended Settings for `main`
- ✅ Require pull request reviews before merging
- ✅ Require at least 1 approving review
- ✅ Dismiss stale pull request approvals on new commits
- ✅ Require status checks to pass (CI/CD)
- ✅ Require branches to be up to date before merging
- ✅ Require signed commits
- ✅ Include administrators
- ❌ Allow force pushes
- ❌ Allow deletions

---

## 6. Repository Best Practices

### Essential Files
| File | Purpose |
|------|---------|
| `README.md` | Project overview, setup, usage |
| `LICENSE` | Legal terms (MIT, Apache 2.0, etc.) |
| `CONTRIBUTING.md` | How to contribute |
| `CODE_OF_CONDUCT.md` | Community standards |
| `CHANGELOG.md` | Version history |
| `.gitignore` | Files to exclude |
| `.github/CODEOWNERS` | Auto-assign reviewers |
| `.github/PULL_REQUEST_TEMPLATE.md` | PR description template |

### CODEOWNERS File
```
# .github/CODEOWNERS
# Default owner for everything
*                   @naresh

# Backend specific
/src/main/java/     @naresh @backend-team

# Frontend specific
/src/main/webapp/   @frontend-team

# DevOps
/.github/workflows/ @devops-team
Dockerfile          @devops-team
```

---

## 7. GitHub CLI (`gh`)

```bash
# Install GitHub CLI
# Windows: winget install --id GitHub.cli
# Mac: brew install gh

# Authenticate
gh auth login

# Clone a repo
gh repo clone username/repo

# Create a repo
gh repo create my-project --public --clone

# Create an issue
gh issue create --title "Fix bug" --body "Description" --label "bug"

# List issues
gh issue list

# Create a pull request
gh pr create --title "Add feature" --body "Description" --base main

# List pull requests
gh pr list

# Check out a PR locally
gh pr checkout 42

# View PR status
gh pr status

# Merge a PR
gh pr merge 42 --squash --delete-branch

# View Actions workflow runs
gh run list
gh run view 12345
```

---

## Key Takeaways

1. **Issues** track work; link them to PRs with `Fixes #N`
2. **Projects** visualize workflow with Kanban boards
3. **GitHub Actions** automate CI/CD — test on every push/PR
4. **Branch protection** enforces code quality on `main`
5. **CODEOWNERS** auto-assigns reviewers by file path
6. **GitHub CLI** (`gh`) streamlines common operations from the terminal

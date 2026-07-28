# Exercise 5: Tags and Releases

## Objective
Learn to create tags for marking release points and understand semantic versioning.

---

## 1. Types of Tags

### Lightweight Tags
Simple pointers to a commit (like a branch that doesn't move).

```bash
# Create a lightweight tag
git tag v1.0.0

# Tag a specific commit
git tag v0.9.0 abc1234
```

### Annotated Tags (Recommended)
Full objects stored in Git — contain tagger name, email, date, and message.

```bash
# Create an annotated tag
git tag -a v1.0.0 -m "Release version 1.0.0 — Initial stable release"

# Tag a specific commit
git tag -a v0.9.0 -m "Beta release" abc1234
```

---

## 2. Managing Tags

```bash
# List all tags
git tag

# List tags matching a pattern
git tag -l "v1.*"

# Show tag details
git show v1.0.0

# Delete a local tag
git tag -d v1.0.0

# Delete a remote tag
git push origin --delete v1.0.0

# Push a specific tag to remote
git push origin v1.0.0

# Push all tags to remote
git push origin --tags

# Checkout a tag (detached HEAD)
git checkout v1.0.0

# Create a branch from a tag
git checkout -b hotfix/v1.0.1 v1.0.0
```

---

## 3. Semantic Versioning (SemVer)

Format: `MAJOR.MINOR.PATCH`

| Component | When to Increment | Example |
|-----------|-------------------|---------|
| **MAJOR** | Breaking/incompatible API changes | `1.0.0 → 2.0.0` |
| **MINOR** | New backward-compatible features | `1.0.0 → 1.1.0` |
| **PATCH** | Backward-compatible bug fixes | `1.0.0 → 1.0.1` |

### Pre-release Labels
```
v1.0.0-alpha.1    # Early testing
v1.0.0-beta.1     # Feature complete, testing
v1.0.0-rc.1       # Release candidate
v1.0.0            # Stable release
```

---

## 4. Practical Exercise — Release Workflow

```bash
# Step 1: Development complete, create release branch
git switch develop
git switch -c release/v1.0.0

# Step 2: Final fixes on release branch
echo "version=1.0.0" > version.properties
git add . && git commit -m "chore: Bump version to 1.0.0"

# Step 3: Merge to main and tag
git switch main
git merge --no-ff release/v1.0.0 -m "Release v1.0.0"
git tag -a v1.0.0 -m "Release v1.0.0

Features:
- User authentication
- Product catalog
- Order management

Bug Fixes:
- Fixed null pointer in checkout
- Fixed date parsing in reports"

# Step 4: Merge back to develop
git switch develop
git merge --no-ff release/v1.0.0 -m "Merge release/v1.0.0 back to develop"

# Step 5: Clean up
git branch -d release/v1.0.0

# Step 6: Push with tags
git push origin main develop --tags

# Step 7: View release history
git tag -l --sort=-v:refname
git log --oneline --decorate main
```

---

## 5. Comparing Tags

```bash
# Show commits between two tags
git log v1.0.0..v1.1.0 --oneline

# Show diff between two tags
git diff v1.0.0 v1.1.0

# Generate a changelog between tags
git log v1.0.0..v1.1.0 --pretty=format:"- %s (%an)" --no-merges
```

---

## Key Takeaways

1. Use **annotated tags** (`-a`) for releases — they store metadata
2. Follow **Semantic Versioning** (MAJOR.MINOR.PATCH)
3. Tags are **not pushed automatically** — use `git push --tags`
4. Tags mark **immutable release points** in history
5. Create branches from tags for hotfixes on past releases

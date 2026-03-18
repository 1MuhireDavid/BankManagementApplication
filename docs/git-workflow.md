# Git Workflow and Contribution Guidelines

Welcome to the Bank Account Management App! Whether you're a core developer or a new contributor, this guide outlines the standard Git workflow and conventions we follow to maintain a clean history and streamline collaboration.

## 1. Branching Strategy

We use a standard feature-branch workflow. Please avoid committing directly to the `main` branch. 

### Branch Naming Conventions
Always use descriptive, hyphen-separated branch names prefixed with the type of work you are doing:

- **Features**: `feature/<short-description>` (e.g., `feature/add-savings-account`)
- **Bug Fixes**: `bugfix/<short-description>` (e.g., `bugfix/fix-withdrawal-validation`)
- **Refactoring**: `refactor/<short-description>` (e.g., `refactor/solid-principles`)
- **Documentation**: `docs/<short-description>` (e.g., `docs/update-readme`)

## 2. Commit Message Guidelines

We enforce a structured format for commit messages using conventional commits to automatically generate changelogs and maintain consistency.

### Format
```
<type>(<scope>): <subject>

<body>
```

- **Type**: Defines the intent of the change.
  - `feat`: A new feature
  - `fix`: A bug fix
  - `docs`: Documentation only changes
  - `style`: Changes that do not affect the meaning of the code (white-space, formatting, etc.)
  - `refactor`: A code change that neither fixes a bug nor adds a feature
  - `test`: Adding missing tests or correcting existing tests
  - `chore`: Changes to the build process or auxiliary tools and libraries
- **Scope**: (Optional) The module or component affected (e.g., `ui`, `account`, `customer`).
- **Subject**: A short summary of the change, written in the imperative mood (e.g., "add", not "added" or "adds"). Do not capitalize the first letter and do not place a period at the end.

**Example:**
```text
feat(account): add overdraft limit to checking accounts

- Adds an overdraft property to the CheckingAccount class.
- Prevents withdrawals if the resulting balance drops below the overdraft limit.
```

## 3. Workflow Steps

### Step 1: Sync with Main
Before starting any new work, make sure your local `main` branch is up to date.

```bash
git checkout main
git pull origin main
```

### Step 2: Create a Branch
Branch off from `main` to start working on your new feature or fix.

```bash
git checkout -b feature/my-awesome-feature
```

### Step 3: Commit Your Changes
Make meaningful, atomic commits. Include the proper prefix as defined in our commit guidelines.

```bash
git add .
git commit -m "feat(ui): add new account creation flow"
```

### Step 4: Push the Branch
Push your newly created branch to the remote repository.

```bash
git push -u origin feature/my-awesome-feature
```

### Step 5: Open a Pull Request (PR)
1. Go to the GitHub repository and click on **Compare & pull request** next to your pushed branch.
2. Provide a clear title and description.
3. Reference any relevant issue numbers (e.g., "Fixes #42").
4. Wait for code review. Resolve any requested changes by making new commits on your branch and pushing them.

## 4. Resolving Merge Conflicts

If `main` has advanced while you were working on your branch, you may run into merge conflicts.
To resolve, rebase your branch onto `main`:

```bash
git fetch origin
git rebase origin/main
```

If conflicts occur during the rebase, Git will pause. Open the conflicted files, manually resolve the conflicts, and then run:

```bash
git add <resolved-file>
git rebase --continue
```

Once rebased, you will need to force-push your remote branch:

```bash
git push -f origin feature/my-awesome-feature
```

> **Warning:** NEVER force push to the `main` branch. Only force push to your personal feature branches.

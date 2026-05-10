# ASS 3 – Java MPI Array Sum: Execution Commands

---

## ✅ Check If It Will Work on Your College PC

Run these checks **first** in the terminal:

```bash
# Check 1: Is MPJ_HOME set?
echo %MPJ_HOME%
```
- If it shows a path like `C:\mpj` or `/usr/local/mpj` → **Will work ✅**
- If it shows `%MPJ_HOME%` or is blank → **MPJ Express not installed ❌**

```bash
# Check 2: Is mpjrun available?
mpjrun.bat
```
- If it shows usage → **Will work ✅**
- If "not recognized" → **Not in PATH ❌**

```bash
# Check 3: Can you find mpj.jar?
dir %MPJ_HOME%\lib\mpj.jar
```
- If file found → **Will work ✅**

---

## Prerequisites
- Java JDK installed
- **MPJ Express** installed and `MPJ_HOME` environment variable set
- File: `ArrSum.java`

---

## Step-by-Step Execution

### Terminal 1 – Compile with MPJ Classpath

```bash
# Compile (include mpj.jar so import mpi.* resolves)
javac -cp %MPJ_HOME%\lib\mpj.jar ArrSum.java
```

**On Linux/Mac college lab:**
```bash
javac -cp $MPJ_HOME/lib/mpj.jar ArrSum.java
```

---

### Run with 4 MPI Processes

```bash
# Run with 4 processes (shared memory / multicore mode)
mpjrun.bat -np 4 ArrSum
```

**On Linux/Mac college lab:**
```bash
mpjrun.sh -np 4 ArrSum
```

> ⚠️ The number after `-np` = number of processes. Must match what the code expects.
> With `unitsize=5` in the code, use `-np 4` → total 20 elements (4 × 5).

---

## Expected Output (4 processes, unitsize=5)

```
Enter 20 elements
Element 0    = 0
Element 1    = 1
Element 2    = 2
...
Element 19   = 19
Intermediate sum at process 0 is 10
Intermediate sum at process 1 is 35
Intermediate sum at process 2 is 60
Intermediate sum at process 3 is 85
Final sum : 190
```

> **Verification:** Sum of 0 to 19 = 19 × 20 / 2 = **190** ✓

---

## How the Data Gets Split

```
4 processes, unitsize=5, total=20 elements:

  P0 gets [0, 1, 2, 3, 4]          → sum = 10
  P1 gets [5, 6, 7, 8, 9]          → sum = 35
  P2 gets [10, 11, 12, 13, 14]     → sum = 60
  P3 gets [15, 16, 17, 18, 19]     → sum = 85

  Gather at P0 → 10+35+60+85 = 190
```

---

## Common Errors & Fixes

| Error | Fix |
|-------|-----|
| `package mpi does not exist` | Add `-cp %MPJ_HOME%\lib\mpj.jar` to javac |
| `mpjrun.bat: not found` | MPJ not in PATH — open terminal from MPJ bin folder |
| `%MPJ_HOME%` shows blank | Environment variable not set — ask lab admin |
| Process count mismatch | Change `-np N` to match your `unitsize` setting |
| Output lines in random order | Normal for MPI — processes run in parallel |

---

## If MPJ Express Is NOT Installed (Home/Exam)

If MPJ is not available, you can **simulate the output manually** for viva:

```
With 4 processes, unitsize=5:
  process 0 handles elements 0-4, sum=10
  process 1 handles elements 5-9, sum=35
  process 2 handles elements 10-14, sum=60
  process 3 handles elements 15-19, sum=85
  Final sum = 190
```

---

## Changing Number of Processes

In `ArrSum.java`, line:
```java
int unitsize = 5;
```
Change `unitsize` and run with matching `-np`:
| `unitsize` | `-np` | Total elements | Final sum |
|-----------|------|---------------|-----------|
| 5 | 4 | 20 | 190 |
| 4 | 3 | 12 | 66 |
| 5 | 2 | 10 | 45 |

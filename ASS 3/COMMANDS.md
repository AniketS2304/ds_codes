# ASS 3 – Java MPI Array Sum: Execution Commands

## ⚠️ IMPORTANT NOTE
This program uses the `mpi.*` package from **MPJ Express**.
MPJ Express must be downloaded and configured separately.

---

## Prerequisites
1. Java JDK installed
2. **MPJ Express** downloaded from: https://sourceforge.net/projects/mpjexpress/
3. `MPJ_HOME` environment variable set

---

## Installation (One-Time Setup)

### Windows
```powershell
# 1. Download and extract MPJ Express to C:\mpj
# 2. Set environment variable
[Environment]::SetEnvironmentVariable("MPJ_HOME", "C:\mpj", "User")
$env:PATH += ";C:\mpj\bin"

# 3. Restart terminal
```

---

## Step-by-Step Execution

### Terminal 1 – Compile with MPJ Classpath
```bash
# Compile (include mpj.jar in classpath)
javac -cp %MPJ_HOME%\lib\mpj.jar ArrSum.java
```

### Run with Multiple Processes
```bash
# Run with 4 MPI processes (multicore/shared memory mode)
mpjrun.bat -np 4 ArrSum

# OR for cluster/network mode:
mpjrun.bat -np 4 -dev niodev ArrSum
```

**Expected output (4 processes, unitsize=5, total=20 elements):**
```
Enter 20 elements
Element 0   = 0
Element 1   = 1
...
Element 19  = 19
Intermediate sum at process 0 is 10
Intermediate sum at process 1 is 35
Intermediate sum at process 2 is 60
Intermediate sum at process 3 is 85
Final sum : 190
```

---

## How the Parallelism Works

```
Root (P0) distributes array [0..19] to 4 processes:
  P0 gets [0,1,2,3,4]     → sum = 10
  P1 gets [5,6,7,8,9]     → sum = 35
  P2 gets [10,11,12,13,14]→ sum = 60
  P3 gets [15,16,17,18,19]→ sum = 85

P0 gathers all sums → Total = 10+35+60+85 = 190
```

---

## Changing Parameters
In `ArrSum.java`:
- `int unitsize = 5;` → elements per process (change this)
- Run with different `-np` values to match process count

# ASS 4 – Berkeley Clock Synchronization: Execution Commands

## Prerequisites
- Java JDK installed (`javac` + `java` in PATH)
- File: `BerkeleyAlgorithm.java`

---

## Step-by-Step Execution (Single Terminal)

```bash
# Step 1: Compile
javac BerkeleyAlgorithm.java

# Step 2: Run
java BerkeleyAlgorithm
```

---

## Sample Run (Interactive Input)

```
Enter number of clients: 3
Enter coordinator (server) time: 10
Enter time for client 1: 15
Enter time for client 2: 5
Enter time for client 3: 20

--- Before Synchronization ---
Coordinator Time: 10
Client 1 Time: 15
Client 2 Time: 5
Client 3 Time: 20

--- After Synchronization ---
Coordinator Time: 12
Client 1 Time: 12
Client 2 Time: 12
Client 3 Time: 12
```

---

## Manual Calculation Verification

Given: Coordinator = 10, Clients = [15, 5, 20]

```
Differences from coordinator:
  Client 1: 15 - 10 = +5
  Client 2:  5 - 10 = -5
  Client 3: 20 - 10 = +10
  Sum of differences = 5 + (-5) + 10 = 10

Total devices = 3 clients + 1 coordinator = 4
Average difference = 10 / 4 = 2  (integer division)

New coordinator time = 10 + 2 = 12
New client times     = coordinator time + avg = 12 each
```

---

## One-liner with Piped Input (PowerShell)
```powershell
"3`n10`n15`n5`n20`n" | java BerkeleyAlgorithm
```

---

## Quick Compile + Run with JDK 21 Full Path
```powershell
$jdk = "C:\Program Files\Microsoft\jdk-21.0.11.10-hotspot\bin"
& "$jdk\javac.exe" BerkeleyAlgorithm.java
"3`n10`n15`n5`n20`n" | & "$jdk\java.exe" BerkeleyAlgorithm
```

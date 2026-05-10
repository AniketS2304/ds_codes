# ASS 6 – Bully & Ring Election Algorithms: Execution Commands

## Prerequisites
- Java JDK installed (`javac` + `java` in PATH)
- Files: `Bully.java`, `Ring.java`

---

## Compile Both Programs
```bash
javac Bully.java Ring.java
```

---

## Running Bully Algorithm

```bash
java Bully
```

### Sample Bully Session
```
Bully Algorithm
1. Create processes
2. Display processes
3. Up a process
4. Down a process
5. Run election algorithm
6. Exit Program
Enter your choice: 1
Enter the number of processes: 5
Creating processes..
P1 created ... P5 created
Process P5 is the coordinator

Enter your choice: 2         ← Display all (all up, P5 coordinator)

Enter your choice: 4         ← Down a process
Enter the process number to down: 5
Process 5 is down.

Enter your choice: 5         ← Run election
Enter the process number which will perform election: 2
Election message sent from process 2 to process 3
Election message sent from process 3 to process 4
Election message sent from process 4 to process 5
P4 is the coordinator       ← P4 wins (P5 is down)

Enter your choice: 6         ← Exit
```

### Bully One-liner (PowerShell)
```powershell
# Create(5), Display, Down P5, Election from P2, Display, Exit
"1`n5`n2`n4`n5`n5`n2`n2`n6`n" | java Bully
```

---

## Running Ring Algorithm

```bash
java Ring
```

### Sample Ring Session
```
Ring Algorithm
1. Create processes
...
Enter your choice: 1
Enter the total number of processes: 5
P1 created. ... P5 created.
P5 is the coordinator

Enter your choice: 4         ← Down a process
Enter the process to down: 5
Process P5 is down.

Enter your choice: 5         ← Run election
Enter the process which will initiate election: 1
Process P1 sending the following list:- [ 1  ]
Process P2 sending the following list:- [ 1 2  ]
Process P3 sending the following list:- [ 1 2 3  ]
Process P4 sending the following list:- [ 1 2 3 4  ]
Process P1 has declared P4 as the coordinator

Enter your choice: 6         ← Exit
```

### Ring One-liner (PowerShell)
```powershell
# Create(5), Display, Down P5, Election from P1, Display, Exit
"1`n5`n2`n4`n5`n5`n1`n2`n6`n" | java Ring
```

---

## Quick Compile + Run with JDK 21 Full Path
```powershell
$jdk = "C:\Program Files\Microsoft\jdk-21.0.11.10-hotspot\bin"
& "$jdk\javac.exe" Bully.java Ring.java

# Run Bully
"1`n5`n2`n4`n5`n5`n2`n2`n6`n" | & "$jdk\java.exe" Bully

# Run Ring
"1`n5`n2`n4`n5`n5`n1`n2`n6`n" | & "$jdk\java.exe" Ring
```

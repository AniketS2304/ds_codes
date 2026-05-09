# 📚 Distributed Systems (DS) — Assignment Codes

This folder contains Java programs for all Distributed Systems lab assignments.

---

## 📁 Folder Structure

```
DS CODE/
├── ASS 1/          → RMI — Calculator (math + string ops)
├── ASS 2/          → CORBA — String Reverse
├── ASS 3/          → MPI — Array Sum (parallel)
├── ASS 4/          → Clock Sync — Berkeley Algorithm
├── ASS 5/          → Mutual Exclusion — Token Ring
├── ASS 6/          → Leader Election — Bully & Ring Algorithms
├── DEMO ASS 1/     → RMI — String Join (demo/practice)
├── DEMO ASS 2/     → CORBA — String Reverse (demo/practice)
└── command for ass1.txt / ass2.txt  → Quick command references
```

---

## ✅ Requirements

- **Java JDK** (version 8 or higher recommended for CORBA support)
  - Check: `java -version` and `javac -version`
- **Open MPI with Java bindings** (for ASS 3 only)
- **Multiple terminal windows** for ASS 1 and ASS 2

---

---

## 🔵 ASS 1 — RMI Calculator

**Topic:** Remote Method Invocation (RMI)  
**What it does:** Client connects to a remote server and performs math operations (add, subtract, multiply, divide, square, square root) and string operations (palindrome, string compare).

### Steps to Run:

**Terminal 1 — Compile:**
```bash
cd "ASS 1"
javac *.java
```

**Terminal 2 — Start RMI Registry + Server:**
```bash
cd "ASS 1"
start rmiregistry
java Server
```

**Terminal 3 — Run Client:**
```bash
cd "ASS 1"
java Client
```
> Enter two numbers when prompted. Results will be printed.

---

---

## 🔵 ASS 2 — CORBA String Reverse

**Topic:** Common Object Request Broker Architecture (CORBA)  
**What it does:** Client sends a string to the server via CORBA, the server reverses it and sends back the result.

### Steps to Run:

**Terminal 1 — Compile & Start ORB Daemon:**
```bash
cd "ASS 2"
javac *.java ReverseModule\*.java
orbd -ORBInitialPort 1050
```
> ⚠️ One warning may appear during compile — that is expected.

**Terminal 2 — Start Server:**
```bash
cd "ASS 2"
java ReverseServer -ORBInitialPort 1050 -ORBInitialHost localhost
```

**Terminal 3 — Run Client:**
```bash
cd "ASS 2"
java ReverseClient -ORBInitialPort 1050 -ORBInitialHost localhost
```
> Enter a string when prompted. The reversed string will be printed.

---

---

## 🔵 ASS 3 — MPI Array Sum (Parallel)

**Topic:** Message Passing Interface (MPI) — Parallel Computing  
**What it does:** Distributes an array across multiple processes. Each process sums its chunk, then all partial sums are gathered at the root to compute the final total.

> ⚠️ **Requires:** OpenMPI with Java bindings installed. This does NOT run with plain `javac/java`.

### Steps to Run:

```bash
cd "ASS 3"
javac -cp /path/to/mpi.jar ArrSum.java
mpirun -np 4 java ArrSum
```
> Replace `/path/to/mpi.jar` with your actual MPJ Express or OpenMPI Java jar path.  
> `-np 4` means 4 processes (adjust as needed; array size = 5 × number of processes).

---

---

## 🔵 ASS 4 — Berkeley Clock Synchronization

**Topic:** Distributed Clock Synchronization  
**What it does:** Simulates the Berkeley Algorithm — a coordinator collects time from all clients, calculates the average difference, and sends correction values back so all clocks sync.

### Steps to Run:

```bash
cd "ASS 4"
javac BerkeleyAlgorithm.java
java BerkeleyAlgorithm
```
> Enter the number of clients, coordinator time, and each client's time when prompted.  
> Output will show clocks before and after synchronization.

---

---

## 🔵 ASS 5 — Token Ring Mutual Exclusion

**Topic:** Token Ring — Distributed Mutual Exclusion  
**What it does:** Simulates a ring of nodes. A token is passed around, and only the node holding the token can send data. Shows how data moves from sender to receiver through intermediate nodes.

### Steps to Run:

```bash
cd "ASS 5"
javac TokenRing.java
java TokenRing
```
> Enter number of nodes, then sender/receiver/data for each transfer.  
> Press `1` to send again or `0` to exit.

---

---

## 🔵 ASS 6 — Leader Election (Bully & Ring)

**Topic:** Distributed Leader Election  
**What it does:** Two separate programs simulating leader election algorithms.

### 6a — Bully Algorithm:
```bash
cd "ASS 6"
javac Bully.java
java Bully
```
> Menu-driven: Create processes → Down a process → Run election → See new coordinator.

### 6b — Ring Election Algorithm:
```bash
cd "ASS 6"
javac Ring.java
java Ring
```
> Menu-driven: Same as Bully but uses ring-based election.

---

---

## 🟡 DEMO ASS 1 — RMI String Join (Demo/Practice)

**Topic:** RMI (simpler demo version)  
**What it does:** Client sends two strings to the server via RMI, server joins them and returns the result.

### Steps to Run:

**Terminal 1 — Compile:**
```bash
cd "DEMO ASS 1"
javac *.java
```

**Terminal 2 — Start RMI Registry + Server:**
```bash
cd "DEMO ASS 1"
start rmiregistry
java Server
```

**Terminal 3 — Run Client:**
```bash
cd "DEMO ASS 1"
java Client
```
> Enter two strings when prompted.

---

---

## 🟡 DEMO ASS 2 — CORBA String Reverse (Demo/Practice)

**Topic:** CORBA (simpler demo version)  
**What it does:** Same as ASS 2 but cleaner/simpler code structure. Client sends a string, server reverses it.

### Steps to Run:

**Terminal 1 — Compile & Start ORB Daemon:**
```bash
cd "DEMO ASS 2"
idlj -fall Reverse.idl
javac *.java ReverseModule\*.java
orbd -ORBInitialPort 1050
```

**Terminal 2 — Start Server:**
```bash
cd "DEMO ASS 2"
java ReverseServer -ORBInitialPort 1050 -ORBInitialHost localhost
```

**Terminal 3 — Run Client:**
```bash
cd "DEMO ASS 2"
java ReverseClient -ORBInitialPort 1050 -ORBInitialHost localhost
```

---

## ⚠️ Common Issues & Fixes

| Issue | Fix |
|-------|-----|
| `rmiregistry` not found | Make sure `JAVA_HOME/bin` is in PATH |
| `Connection refused` in RMI | Start `rmiregistry` BEFORE running `java Server` |
| CORBA compile warning | Normal for Java 8+; still works |
| CORBA `NameService` not found | Make sure `orbd` is running first in Terminal 1 |
| MPI class not found | Need MPJ Express or OpenMPI Java bindings installed |
| `java.lang.ClassNotFoundException` | Compile with `javac *.java` from correct folder |

---

> 📝 **Note:** ASS 1 and DEMO ASS 1 use **RMI** (Java-native). ASS 2 and DEMO ASS 2 use **CORBA** (middleware-based). ASS 3 needs **MPI** runtime. ASS 4, 5, 6 are **standalone simulations** — simplest to run.

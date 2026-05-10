# ASS 3 – Java MPI Array Sum: Technical Explanation & Theory

---

## 1. What is MPI?

**MPI (Message Passing Interface)** is a standardized library specification for **parallel computing**. It allows multiple processes (possibly on different machines) to communicate by **sending and receiving messages**.

> MPI is the backbone of high-performance computing (HPC). Programs like weather simulations, molecular dynamics, and deep learning training use MPI.

---

## 2. What is MPJ Express?

**MPJ Express** is a Java implementation of the MPI standard. It provides the `mpi.*` package that this program uses, enabling Java programs to:
- Spawn multiple processes
- Pass data between processes (scatter, gather, send, receive)

---

## 3. Core Concepts

### 3.1 Process Rank and Size
```java
int rank = MPI.COMM_WORLD.Rank(); // This process's ID (0 to N-1)
int size = MPI.COMM_WORLD.Size(); // Total number of processes
```
- **Rank**: Unique identifier of each process (like a process ID)
- **Size**: Total processes launched (set by `-np N` flag)
- **COMM_WORLD**: The default communicator (group containing all processes)

### 3.2 Root Process
- The **root** process (rank = 0) acts as the master
- It distributes data to others and collects results

---

## 4. Two Key Operations in This Program

### 4.1 Scatter – Distributing Data
```
Root has: [0, 1, 2, 3, 4, | 5, 6, 7, 8, 9, | 10,11,12,13,14, | 15,16,17,18,19]
After Scatter (unitsize=5):
  P0 gets: [0, 1, 2, 3, 4]
  P1 gets: [5, 6, 7, 8, 9]
  P2 gets: [10, 11, 12, 13, 14]
  P3 gets: [15, 16, 17, 18, 19]
```

```java
MPI.COMM_WORLD.Scatter(
    send_buffer, 0, unitsize, MPI.INT,   // from root: send unitsize ints
    recieve_buffer, 0, unitsize, MPI.INT, // to each: receive unitsize ints
    root
);
```

### 4.2 Gather – Collecting Results
```
Each process computes local sum → stores in recieve_buffer[0]:
  P0: sum = 0+1+2+3+4   = 10
  P1: sum = 5+6+7+8+9   = 35
  P2: sum = 10+11+12+13+14 = 60
  P3: sum = 15+16+17+18+19 = 85

After Gather at root:
  new_recieve_buffer = [10, 35, 60, 85]
  Final sum = 10+35+60+85 = 190
```

---

## 5. Program Flow

```
MPI.Init(args)
       │
       ▼
Each process gets rank and size
       │
       ▼ (rank == 0 only)
Root creates array [0,1,2,...,19]
prints "Enter 20 elements"
       │
       ▼
MPI.COMM_WORLD.Scatter()
  → P0 gets [0..4], P1 gets [5..9], P2 gets [10..14], P3 gets [15..19]
       │
       ▼ (all processes)
Each computes local sum → stores in recieve_buffer[0]
prints "Intermediate sum at process X is Y"
       │
       ▼
MPI.COMM_WORLD.Gather()
  → Root collects partial sums
       │
       ▼ (rank == 0 only)
Root totals all partial sums → prints "Final sum: 190"
       │
       ▼
MPI.Finalize()
```

---

## 6. Parallel Speedup

With N processes and an array of N×M elements:
- **Sequential**: O(N×M) operations on 1 CPU
- **Parallel (MPI)**: O(M) operations per process + communication overhead
- **Speedup** ≈ N (ideal, ignoring communication)

For 4 processes summing 20 elements:
- Sequential: 20 additions
- Parallel: 5 additions per process (4× faster in theory)

---

## 7. MPI vs Threads

| Feature | MPI | Java Threads |
|---------|-----|--------------|
| Memory | Separate memory per process | Shared memory |
| Scalability | Scales to thousands of nodes | Limited to 1 machine |
| Communication | Explicit messages (send/recv) | Shared variables |
| Fault tolerance | Better (process isolation) | Harder |
| Use case | HPC clusters | Multi-core single machine |

---

## 8. Math: Expected Results

With `unitsize = 5` and 4 processes (elements 0 to 19):
```
P0 sum = 0+1+2+3+4 = 10
P1 sum = 5+6+7+8+9 = 35
P2 sum = 10+11+12+13+14 = 60
P3 sum = 15+16+17+18+19 = 85
Total  = 10+35+60+85 = 190

(Verification: sum of 0..19 = 19×20/2 = 190 ✓)
```

# ASS 3 – Java MPI Array Sum: Viva Preparation

---

## 🔴 Must-Know Questions

### Q1. What is MPI? What does it stand for?
**A:** MPI stands for **Message Passing Interface**. It is a standardized library specification for **parallel and distributed computing**. MPI allows multiple processes — running on one machine or across a cluster — to communicate by explicitly sending and receiving messages. It is the de-facto standard for HPC (High Performance Computing).

---

### Q2. What is MPJ Express?
**A:** **MPJ Express** is a Java implementation of the MPI standard. Since standard MPI libraries are written in C/Fortran, MPJ Express provides the `mpi.*` Java package that this assignment uses. It can run in:
- **Multicore mode** (multiple processes on one machine)
- **Cluster mode** (processes across multiple networked machines)

---

### Q3. What is `MPI.COMM_WORLD`?
**A:** `COMM_WORLD` is the **default communicator** in MPI — a group containing **all processes** launched by `mpjrun`. It provides collective operations like Scatter and Gather that operate across all processes. You can create sub-communicators for groups of processes.

---

### Q4. What is Rank? What is Size?
**A:**
- **Rank**: A unique integer ID assigned to each process, from 0 to (Size-1). Used to identify processes and control which process does what.
- **Size**: Total number of processes launched (set by `-np N` flag)
- The process with rank 0 is typically called the **root** or **master**

---

### Q5. What is MPI Scatter? Explain with example.
**A:** `Scatter` distributes a large array from the **root** process equally among all processes:

```
Root has: [0,1,2,3,4, | 5,6,7,8,9, | 10,11,12,13,14, | 15,16,17,18,19]
After Scatter to 4 processes (unitsize=5):
  P0 gets [0,1,2,3,4]
  P1 gets [5,6,7,8,9]
  P2 gets [10,11,12,13,14]
  P3 gets [15,16,17,18,19]
```
Only the **root** needs the full send_buffer. All processes provide a receive buffer.

---

### Q6. What is MPI Gather? Explain with example.
**A:** `Gather` is the **reverse of Scatter** — it collects one element from each process into an array at the root:

```
Before Gather:
  P0: partial_sum = 10
  P1: partial_sum = 35
  P2: partial_sum = 60
  P3: partial_sum = 85

After Gather at root:
  new_recieve_buffer = [10, 35, 60, 85]
```

---

### Q7. What is the difference between Scatter/Gather and Send/Receive?
| | Scatter/Gather | Send/Receive |
|-|----------------|--------------|
| Communication | One-to-many / Many-to-one | Point-to-point |
| Who participates | All processes (collective) | Two processes only |
| Use case | Distributing/collecting data | Direct messaging |
| Called by | All processes simultaneously | Sender and receiver separately |

---

### Q8. What is the expected final sum for this program with 4 processes?
**A:** With `unitsize = 5` and 4 processes, elements are 0 to 19:
```
Sum = 0+1+2+...+19 = 19×20/2 = 190
Verification:
  P0: 0+1+2+3+4 = 10
  P1: 5+6+7+8+9 = 35
  P2: 10+11+12+13+14 = 60
  P3: 15+16+17+18+19 = 85
  Total = 10+35+60+85 = 190 ✓
```

---

### Q9. Why do only root process operations run when `rank == root`?
**A:** All processes execute the **same code** (SPMD — Single Program Multiple Data model). The `if(rank == root)` check ensures that only the root process (rank 0) performs master tasks like array initialization and final aggregation, while all other processes perform worker tasks.

---

### Q10. What is the SPMD programming model?
**A:** **SPMD (Single Program, Multiple Data)** is the dominant MPI programming model where:
- All processes run the **same program**
- Each process operates on **different data** (based on its rank)
- Conditional logic (`if rank == 0`) is used for master-specific code
- This is different from MPMD (Multiple Program Multiple Data) where different executables run

---

### Q11. What happens if the number of elements is not divisible by the number of processes?
**A:** The basic `Scatter` assumes equal distribution. If `total_elements % num_processes != 0`, extra elements (remainder) won't be distributed. Advanced MPI programs handle this with `MPI_Scatterv` (variable-length scatter) or manual padding.

---

### Q12. What is `MPI.Init()` and `MPI.Finalize()`?
**A:**
- `MPI.Init(args)` — **Must be called first**: initializes the MPI environment, sets up communication infrastructure, assigns ranks
- `MPI.Finalize()` — **Must be called last**: cleans up all MPI resources, terminates communication. No MPI calls can be made after this.

---

### Q13. What is the speedup achieved by this parallel approach?
**A:** With N processes:
- **Theoretical speedup** = N (linear)
- **Actual speedup** < N due to:
  - Communication overhead (Scatter/Gather time)
  - Synchronization barriers
  - Memory bottleneck at root
- **Amdahl's Law**: Speedup is limited by the sequential fraction of the code

---

### Q14. What is the difference between MPI and multi-threading?
| Feature | MPI | Java Threads |
|---------|-----|--------------|
| Memory model | Distributed (separate) | Shared |
| Scalability | Thousands of nodes | One machine |
| Communication | Explicit messages | Shared variables |
| Synchronization | Barriers, collective ops | Locks, monitors |
| Language dependency | No (C, Fortran, Java, Python) | JVM-specific |

---

## 🟡 Quick-Fire Answers

| Question | Answer |
|----------|--------|
| Full form of MPI | Message Passing Interface |
| Java MPI library | MPJ Express |
| Root process rank | 0 |
| Rank range for N processes | 0 to N-1 |
| Command to run with 4 processes | `mpjrun.bat -np 4 ArrSum` |
| Scatter = ? | Root distributes array to all |
| Gather = ? | All send result to root |
| MPI programming model | SPMD (Single Program Multiple Data) |
| Sum of 0 to 19 | 190 |
| `unitsize` in code | 5 (elements per process) |

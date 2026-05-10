# ASS 6 – Bully & Ring Election: Viva Preparation

---

## 🔴 Must-Know Questions

### Q1. What is Leader Election? Why is it needed?
**A:** Leader Election is the process by which alive processes in a distributed system **agree on a single coordinator/leader** after the previous coordinator crashes. It's needed because many distributed algorithms require a coordinator to manage shared resources, order messages, or make decisions.

### Q2. What is the Bully Algorithm? Who proposed it?
**A:** The Bully Algorithm was proposed by **Garcia-Molina (1982)**. The process with the **highest process ID always wins** the election. When a process detects coordinator failure, it "bullies" all lower-ID processes out of the election by sending ELECTION messages to higher-ID processes only.

### Q3. What are the 3 message types in Bully Algorithm?
| Message | Meaning |
|---------|---------|
| **ELECTION** | "I'm starting an election" |
| **OK (Answer)** | "I'm alive, I'll take over" |
| **COORDINATOR** | "I've won, I'm the new leader" |

### Q4. Walk through Bully with P5 down, P2 initiates (5 processes):
**A:**
```
P2 → ELECTION to P3: "Are you there?"
P3 is UP → P3 responds OK → P3 takes over
P3 → ELECTION to P4: "Are you there?"
P4 is UP → P4 responds OK → P4 takes over
P4 → ELECTION to P5: "Are you there?"
P5 is DOWN → no response
P4 wins → P4 broadcasts COORDINATOR
```
**New coordinator = P4** ✓

### Q5. What is the Ring Election Algorithm?
**A:** In Ring Election, processes are in a logical ring. The initiating process sends its ID around the ring. Each alive process **adds its ID** to the list. When the list returns to the initiator, the process with the **maximum ID** in the list is declared coordinator.

### Q6. Walk through Ring with P5 down, P1 initiates:
**A:**
```
P1 sends list: [1]
P2 adds → [1, 2] → passes to P3
P3 adds → [1, 2, 3] → passes to P4
P4 adds → [1, 2, 3, 4] → passes to P5
P5 DOWN → skipped
List returns to P1: [1, 2, 3, 4]
max([1,2,3,4]) = 4 → P4 is coordinator
```

### Q7. Bully vs Ring Algorithm Comparison:

| Feature | Bully | Ring |
|---------|-------|------|
| Messages | O(n²) worst case | O(n) |
| Topology needed | Any | Ring |
| Winner | Highest ID | Highest ID |
| How winner found | Higher processes respond | Max in collected list |
| Announcement | COORDINATOR broadcast | Initiator announces |

### Q8. Why does the process with the highest ID win in Bully?
**A:** In Bully, higher-ID processes always respond to lower-ID processes with "OK" (I'll take over). Lower-ID processes then step down. So only the highest alive process gets no "OK" response and declares itself coordinator.

### Q9. What is the complexity of Bully Algorithm?
**A:**
- **Best case**: O(1) — if the second-highest process initiates (it gets no OK, immediately wins)
- **Worst case**: O(n²) — when the lowest-ID process initiates (sends to n-1 processes, each round generates more messages)

### Q10. What are Election, Safety, and Liveness in leader election?
**A:**
| Property | Meaning | Both Satisfy? |
|----------|---------|---------------|
| **Safety** | At most one leader at a time | ✅ Yes |
| **Liveness** | Eventually a leader is elected | ✅ Yes |
| **Validity** | Leader is among alive processes | ✅ Yes |

### Q11. What happens if the new coordinator also crashes during election?
**A:** A new election is started by any process that detects the crash. The algorithms are **re-entrant** — elections can be started multiple times until a stable coordinator is found.

### Q12. What real-world systems use leader election?
| System | Algorithm |
|--------|-----------|
| ZooKeeper | ZAB protocol |
| Kubernetes/etcd | Raft |
| Cassandra | Virtual ring + gossip |
| MongoDB replica sets | Raft-based voting |

### Q13. What is the `runElection` recursive call in Bully code?
**A:**
```java
if(processes[i]) {
    keepGoing = false;
    runElection(i + 1);  // higher process takes over recursively
}
```
When a higher alive process is found, it recursively calls `runElection` with its own ID, effectively simulating that process taking over the election — until no higher alive process responds.

### Q14. What is the difference between Bully and Raft?
| | Bully | Raft |
|-|-------|------|
| Leader criteria | Highest ID | Majority vote (quorum) |
| Log replication | No | Yes |
| Fault tolerance | Handles crashes | Handles up to N/2 failures |
| Used in | Academic/classic | Modern production |

---

## 🟡 Quick-Fire Answers

| Question | Answer |
|----------|--------|
| Bully proposed by | Garcia-Molina, 1982 |
| Winner in both algorithms | Highest process ID |
| Bully message types | ELECTION, OK, COORDINATOR |
| Bully complexity | O(n²) worst case |
| Ring complexity | O(n) |
| Ring uses | `Collections.max(pid)` |
| What triggers election? | Coordinator crash detection |
| P5 down (5 procs) → winner | P4 (highest alive) |
| ZooKeeper uses | ZAB protocol |
| Kubernetes uses | Raft |

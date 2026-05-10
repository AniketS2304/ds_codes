# ASS 6 – Bully & Ring Election Algorithms: Technical Explanation & Theory

---

## 1. What is Leader Election?

In a distributed system, processes often need a **coordinator** (leader) to manage shared resources, distribute tasks, or make decisions. When the current coordinator **crashes**, an **election algorithm** is needed to choose a new one.

> Leader Election = The process of all remaining alive processes agreeing on a single new coordinator.

---

## 2. Requirements of a Good Election Algorithm

| Property | Meaning |
|----------|---------|
| **Safety** | At most one process declares itself elected |
| **Liveness** | Some process eventually becomes the leader |
| **Correctness** | The process with the highest ID (or priority) wins |

---

## 3. Bully Algorithm

### 3.1 Concept
The process with the **highest process ID** always wins the election. When a process detects the coordinator is down, it **bullies** lower-ID processes out of the election.

### 3.2 Three Types of Messages
| Message | Meaning |
|---------|---------|
| **Election** | "I'm starting an election" |
| **OK (Answer)** | "I'm alive and will take over" |
| **Coordinator** | "I've won, I'm the new coordinator" |

### 3.3 Algorithm Steps

```
Process P detects coordinator is down:

1. P sends ELECTION message to all processes with higher IDs

2. If no response → P wins → broadcasts COORDINATOR to all

3. If any higher-ID process responds with OK:
   → That higher process takes over the election
   → P steps out

4. The process that gets no OK response declares itself COORDINATOR
```

### 3.4 Code Trace (Your Implementation)

```java
void runElection(int process_id) {
    coordinator = process_id;
    boolean keepGoing = true;

    // Send election messages to all higher-ID processes
    for(int i = process_id; i < max_processes && keepGoing; i++) {
        System.out.println("Election message sent from " + process_id + " to " + (i+1));

        if(processes[i]) {        // If that process is alive
            keepGoing = false;    // Stop here
            runElection(i + 1);  // That process takes over (recursive)
        }
    }
    // If loop completes without a response → this process wins
}
```

**Example (5 processes, P5 down, P2 initiates):**
```
P2 → Election msg to P3
P3 is alive → P3 takes over
P3 → Election msg to P4
P4 is alive → P4 takes over
P4 → Election msg to P5
P5 is DOWN → no response
P4 wins → P4 is the new coordinator
```

### 3.5 Complexity
- **Messages**: O(n²) in worst case
- **Time**: O(n) rounds

---

## 4. Ring Election Algorithm

### 4.1 Concept
Processes are arranged in a **logical ring**. The initiating process sends its ID around the ring, collecting all active process IDs. The **maximum ID** in the list becomes the new coordinator.

### 4.2 Algorithm Steps

```
1. Process P detects coordinator failure

2. P creates a list: [P's own ID]

3. P passes the list to its neighbor (clockwise)

4. Each active process ADDS its own ID to the list and passes it on

5. When the list returns to P (full circle), P picks the MAX ID as coordinator

6. P broadcasts the new coordinator ID to all processes
```

### 4.3 Code Trace (Your Implementation)

```java
void initElection(int process_id) {
    pid.add(process_id);        // Start with initiator's ID
    int temp = process_id;

    while(temp != process_id - 1) {    // Loop until full circle
        if(processes[temp]) {           // If process is alive
            pid.add(temp + 1);          // Add to list
            displayArrayList(pid);
        }
        temp = (temp + 1) % max_processes;  // Move clockwise
    }
    
    coordinator = Collections.max(pid);   // Winner = max ID
}
```

**Example (5 processes, P5 down, P1 initiates):**
```
P1 sends: [1]
P2 adds: [1, 2]
P3 adds: [1, 2, 3]
P4 adds: [1, 2, 3, 4]
P5 is DOWN → skipped
List returns to P1: [1, 2, 3, 4]
Max = 4 → P4 is the new coordinator
```

### 4.4 Complexity
- **Messages**: O(n) — one pass around the ring
- **Time**: O(n)

---

## 5. Bully vs Ring: Comparison

| Feature | Bully Algorithm | Ring Algorithm |
|---------|----------------|----------------|
| **Winner** | Highest process ID | Highest process ID |
| **Topology** | Any (point-to-point) | Ring |
| **Messages** | O(n²) worst case | O(n) |
| **Detection** | Immediate (times out) | Passes around ring |
| **Failure tolerance** | Handles multiple failures | Handles multiple failures |
| **Coordinator announcement** | Broadcasts COORDINATOR msg | Initiator announces |
| **Complexity** | Higher (more messages) | Lower (efficient) |

---

## 6. When Does Election Happen?

An election is triggered when:
1. A process doesn't receive a **heartbeat** from the coordinator within timeout
2. A process explicitly detects the coordinator is down
3. A new process joins with a higher ID than the current coordinator

---

## 7. Real-World Examples

| System | Election Algorithm |
|--------|-------------------|
| **Apache ZooKeeper** | ZAB (ZooKeeper Atomic Broadcast) |
| **etcd / Kubernetes** | Raft Algorithm |
| **Cassandra** | Gossip + virtual ring |
| **Hadoop HDFS** | Manual failover + ZooKeeper |
| **MongoDB** | Raft-based replica set elections |

---

## 8. Raft vs Bully (Modern Context)

| | Bully | Raft |
|-|-------|------|
| Leader requirement | Highest ID | Majority vote (quorum) |
| Fault tolerance | Handles crashes | Handles up to N/2 failures |
| Log replication | No | Yes |
| Used in | Academic examples | Production systems |

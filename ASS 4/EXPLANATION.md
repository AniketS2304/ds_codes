# ASS 4 – Berkeley Clock Synchronization: Technical Explanation & Theory

---

## 1. The Problem: Clock Skew in Distributed Systems

In a distributed system, every machine has its own hardware clock. These clocks:
- **Drift** over time at different rates
- Are **never perfectly synchronized**
- Can differ by seconds, minutes, or more

This causes problems: if Process A and Process B disagree on the time, events can appear out of order, transactions can conflict, and logs become inconsistent.

---

## 2. Types of Clock Synchronization

| Algorithm | Who initiates? | External time reference? |
|-----------|----------------|--------------------------|
| **Cristian's Algorithm** | Client asks server | Yes (time server) |
| **Berkeley Algorithm** | Server polls clients | No (internal average) |
| **NTP (Network Time Protocol)** | Hierarchical | Yes (GPS/atomic clock) |

---

## 3. Berkeley Algorithm Overview

Proposed by **Gusella and Zatti** at UC Berkeley (1989).

**Key idea:** The coordinator collects all clocks, computes an **average**, and tells everyone how much to **adjust** their clock — no external time reference needed.

### Who uses it?
- Isolated networks with no internet access
- Situations where internal consistency matters more than absolute accuracy

---

## 4. Algorithm Steps

```
Step 1: Coordinator sends a time request to all clients
         (asks "what time do you think it is?")

Step 2: Each client replies with its current clock value

Step 3: Coordinator calculates the average of:
         - Its own time
         - All client times

Step 4: Coordinator sends each node a CORRECTION VALUE
         (not the absolute time, just "add X seconds")

Step 5: Each node adjusts its clock by the correction
```

---

## 5. Mathematical Walkthrough

**Given:**
- Coordinator time = 10
- Client 1 = 15, Client 2 = 5, Client 3 = 20

**Step 1: Compute differences**
```
diff(C1) = 15 - 10 = +5
diff(C2) =  5 - 10 = -5
diff(C3) = 20 - 10 = +10
Coordinator's own difference = 0
Sum of all differences = 5 + (-5) + 10 + 0 = 10
```

**Step 2: Compute average**
```
Total devices = 3 clients + 1 coordinator = 4
Average difference = 10 / 4 = 2
```

**Step 3: Adjust all clocks**
```
New Coordinator = 10 + 2 = 12
New Client 1 = 15 - 5 + 2 = 12  (undo diff, apply average)
New Client 2 =  5 + 5 + 2 = 12
New Client 3 = 20 - 10 + 2 = 12
```

All clocks synchronized to **12** ✓

---

## 6. Code Walkthrough

```java
// Calculate each client's difference from coordinator
timeDifferences[i] = clientTimes[i] - coordinatorTime;
sumDifferences += timeDifferences[i];

// Average includes coordinator (its diff is 0)
int totalDevices = clientTimes.length + 1;
int avgDifference = sumDifferences / totalDevices;

// Adjust coordinator
coordinatorTime += avgDifference;

// Adjust each client
clientTimes[i] = clientTimes[i] - timeDifferences[i] + avgDifference;
//                original time   undo deviation      add avg correction
```

---

## 7. Important Properties

### ✅ Advantages
- Does not require an external time source (GPS, atomic clock)
- Works in isolated/closed networks
- Self-correcting — multiple rounds can converge clocks

### ❌ Limitations
- Coordinator is a **single point of failure**
- Does not give absolute time (only relative synchronization)
- Simple integer division introduces rounding error
- Network delay to clients is not accounted for (unlike Cristian's)

---

## 8. Real-World Relevance

| System | Clock Sync Used |
|--------|----------------|
| Internet (general) | NTP (Network Time Protocol) |
| Data centers | PTP (Precision Time Protocol) |
| Google Spanner | TrueTime API (GPS + atomic clocks) |
| Isolated LANs | Berkeley Algorithm |

---

## 9. Diagram

```
     COORDINATOR (time=10)
          │
    ┌─────┼─────┐
    ▼     ▼     ▼
  C1=15  C2=5  C3=20
    │     │     │
    └──ask time ──►
    ◄─collect times──
    │
  avg = 12
    │
  send adjustment to all:
    C1: -3 (15→12)
    C2: +7 (5→12)
    C3: -8 (20→12)
    self: +2 (10→12)
    │
    ▼
  All clocks = 12 ✓
```

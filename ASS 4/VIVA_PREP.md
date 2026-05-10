# ASS 4 – Berkeley Clock Synchronization: Viva Preparation

---

## 🔴 Must-Know Questions

### Q1. Why is clock synchronization needed in distributed systems?
**A:** In a distributed system, each machine has its own hardware clock that **drifts** over time at different rates. Without synchronization:
- Events may appear in the wrong order
- Database transactions can conflict
- File timestamps become inconsistent
- Logs from different machines cannot be correlated
- Distributed algorithms (like 2PC) can break

---

### Q2. What is the Berkeley Algorithm? Who proposed it?
**A:** The Berkeley Algorithm is a **clock synchronization algorithm** proposed by **Gusella and Zatti** at the University of California, Berkeley in **1989**. It synchronizes clocks in a distributed system **without an external time reference** by computing an average time across all participating nodes.

---

### Q3. What is the key difference between Berkeley and Cristian's Algorithm?
| Feature | Berkeley | Cristian's |
|---------|----------|-----------|
| Initiator | Server polls clients | Client asks server |
| External time source | ❌ No (internal avg) | ✅ Yes (time server) |
| Accuracy | Relative (consistent) | Absolute (UTC-based) |
| Fault tolerance | Coordinator failure = problem | Time server failure = problem |

---

### Q4. Walk me through the Berkeley Algorithm steps.

**A:**
1. **Coordinator** sends a time request to all clients
2. Each **client** replies with its current clock value
3. Coordinator computes **differences** of each client clock from its own
4. Coordinator averages all differences (including its own difference = 0)
5. Coordinator sends each node its **correction value** (not absolute time)
6. Each node **adjusts** its clock by the correction value

---

### Q5. Explain with numbers: coordinator=10, clients=[15, 5, 20]

**A:**
```
Differences from coordinator:
  Client 1: 15-10 = +5
  Client 2:  5-10 = -5
  Client 3: 20-10 = +10
  Coordinator own diff = 0
  Sum = 5+(-5)+10+0 = 10

Total devices = 4
Average diff = 10/4 = 2

Adjustments:
  Coordinator: 10 + 2 = 12
  Client 1: 15 - 5 + 2 = 12
  Client 2:  5 + 5 + 2 = 12
  Client 3: 20 - 10 + 2 = 12
```
All clocks → **12** ✅

---

### Q6. Why does the coordinator send a correction (delta) instead of the absolute time?
**A:** Sending a correction value (e.g., "add 2 seconds") is **better** than sending absolute time because:
- It **accounts for network delay** — by the time the message arrives, the absolute time will be wrong
- The correction is applied immediately upon receipt
- Reduces the **discontinuity** in the clock (especially important — you don't want clocks to jump backward)

---

### Q7. What is clock drift? What causes it?
**A:** **Clock drift** is the rate at which a clock deviates from the true time. Caused by:
- Crystal oscillator manufacturing imperfections
- Temperature variations
- Aging of hardware components
Typical drift: 1-2 microseconds per second (or ~1 second per day for a cheap clock).

---

### Q8. What is clock skew?
**A:** **Clock skew** is the instantaneous difference between two clocks at a given point in time. It is the result of accumulated clock drift. Berkeley Algorithm reduces clock skew by bringing all clocks to a common value.

---

### Q9. What are the limitations of this implementation?
**A:**
1. **Network delay ignored** — time to send/receive messages is not accounted for
2. **Integer division** — causes rounding error (e.g., 10/4 = 2, not 2.5)
3. **Single coordinator** — if coordinator crashes, synchronization stops
4. **No outlier rejection** — a faulty clock with extreme value skews the average
5. **Single round** — real-world implementations run continuously

---

### Q10. How does NTP differ from Berkeley?

| Feature | Berkeley | NTP |
|---------|----------|-----|
| External reference | No | Yes (GPS/atomic clocks) |
| Hierarchy | Flat (1 coordinator) | Stratum 0→1→2→... |
| Accuracy | Milliseconds | Microseconds to nanoseconds |
| Use case | Isolated LAN | Internet-scale |
| Protocol | Custom | UDP port 123 |

---

### Q11. What is the formula used to adjust client times in this code?
**A:**
```java
clientTimes[i] = clientTimes[i] - timeDifferences[i] + avgDifference;
```
Breaking it down:
- `timeDifferences[i]` = `clientTimes[i] - coordinatorTime` (deviation)
- `clientTimes[i] - timeDifferences[i]` = `coordinatorTime` (set to coordinator's time)
- `+ avgDifference` = apply the average correction

This effectively sets all clocks to `coordinatorTime + avgDifference`.

---

### Q12. What is Lamport's Logical Clock? How is it different from Berkeley?

**A:**
- **Lamport's Clock**: Doesn't synchronize physical time at all — assigns **logical timestamps** to events to establish ordering (happens-before relationship)
- **Berkeley**: Synchronizes **physical wall-clock time** by adjusting hardware clocks

Berkeley: *"What time is it?"* → Lamport: *"Which event happened first?"*

---

## 🟡 Quick-Fire Answers

| Question | Answer |
|----------|--------|
| Proposed by | Gusella & Zatti, UC Berkeley, 1989 |
| External time source needed? | No |
| Who initiates? | Coordinator (server) |
| What is sent? | Correction delta (not absolute time) |
| Formula for average | (Sum of all diffs) / (clients + 1) |
| Coordinator's own difference | 0 |
| Result in example | All clocks → 12 |
| Alternative algorithm | Cristian's Algorithm, NTP |
| NTP port | UDP 123 |
| Clock skew = ? | Difference between two clocks at same instant |

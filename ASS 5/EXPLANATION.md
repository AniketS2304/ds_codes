# ASS 5 – Token Ring: Technical Explanation & Theory

---

## 1. What is Token Ring?

**Token Ring** is a network/mutual exclusion algorithm where processes (nodes) are arranged in a **logical ring**. A special message called a **token** circulates around the ring, and only the process that **holds the token** can send data or enter the critical section.

> Think of it like a "talking stick" — only whoever holds it can speak.

---

## 2. Two Uses of Token Ring

| Context | Purpose |
|---------|---------|
| **Computer Networks** | Medium access control (like IEEE 802.5 LAN standard) |
| **Distributed Systems** | Mutual exclusion — preventing two processes from accessing the same resource simultaneously |

This assignment simulates the **network data transfer** use case.

---

## 3. How Token Ring Works

### 3.1 Ring Structure
Processes are connected in a circle:
```
  0 → 1 → 2 → 3 → 4 → 0 (wraps around)
```

### 3.2 Token Passing
- The token starts at process 0
- The token passes from process to process: 0→1→1→2→3→...
- When the **sender** gets the token, it sends the data
- Data travels **hop by hop** through intermediate nodes until it reaches the **receiver**
- After sending, the token moves to the sender's position for next use

---

## 4. Algorithm Steps

```
1. Token is at position 'token' (initially 0)

2. User inputs: sender, receiver, data

3. Token travels from current position to sender:
   token → token+1 → ... → sender

4. Sender holds token and transmits data:
   data travels: sender → sender+1 → ... → receiver

5. Receiver gets the data

6. Token position updates to sender (for next round)
```

---

## 5. Code Walkthrough

```java
// Pass token from current position to sender
for(int i = token; i < sender; i++) {
    System.out.print(" " + i + "->");
}

// If receiver is immediately next to sender
if(receiver == sender + 1) {
    // Direct delivery
    System.out.println("Sender: " + sender + " Sending The Data: " + data);
    System.out.println("Receiver: " + receiver + " Received The Data: " + data);
} else {
    // Data hops through intermediate nodes
    System.out.println(sender);
    System.out.println("Sender:" + sender + " Sending Data: " + data);
    
    for(int i = sender; i != receiver; i = (i+1) % n) {
        System.out.println("Data: " + data + " Forwarded By: " + i);
    }
    
    System.out.println("Receiver: " + receiver + " Received The Data: " + data);
}

// Update token position
token = sender;
```

---

## 6. Example Trace

**Setup:** 5 nodes (0→1→2→3→4→0), token at 0
**Action:** Sender=1, Receiver=3, Data=42

```
Token passes: 0→1 (token reaches sender)
Sender 1 transmits data=42:
  Data: 42 Forwarded By: 1
  Data: 42 Forwarded By: 2
  Receiver: 3 Received The Data: 42
Token now at: 1
```

---

## 7. Properties of Token Ring

### ✅ Advantages
- **Fair** — every process gets a turn in round-robin order
- **No collisions** — only token holder transmits
- **Deterministic** — maximum wait time is bounded (N-1 hops at most)
- **No starvation** — token circulates continuously

### ❌ Disadvantages
- **Token loss** problem — if the token holder crashes, the whole ring stops
- **Overhead** — even when no data to send, token must keep circulating
- **Single point of failure** — token loss requires election of new token generator
- **Latency** — data must traverse intermediate nodes hop by hop

---

## 8. Token Ring vs Bus (CSMA/CD)

| Feature | Token Ring | CSMA/CD (Ethernet) |
|---------|-----------|-------------------|
| Access control | Token-based | Collision detection |
| Fairness | Guaranteed | Not guaranteed |
| Collision | None | Possible |
| Performance under load | Better | Degrades with collisions |
| Complexity | Higher | Lower |
| Still used? | Rare (legacy) | Yes (Ethernet dominates) |

---

## 9. Mutual Exclusion with Token Ring

In distributed systems, Token Ring ensures **mutual exclusion**:
- Token = permission to enter critical section
- Process holds token → enters critical section
- After exiting critical section → passes token to next process
- Guarantees only 1 process in critical section at a time

### Properties satisfied:
| Property | Satisfied? |
|----------|------------|
| **Mutual Exclusion** | ✅ Yes — only token holder acts |
| **Progress** | ✅ Yes — token always circulates |
| **Bounded Waiting** | ✅ Yes — wait at most N-1 token passes |

---

## 10. IEEE 802.5 Token Ring Standard

The real Token Ring LAN standard (IBM, 1980s):
- Ring speed: 4 Mbps or 16 Mbps
- Uses **free token** and **busy token** frames
- If token holder fails: **ring recovery** protocol kicks in (Neighbor Notification)
- Largely replaced by Ethernet (IEEE 802.3) by the 2000s

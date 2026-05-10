# ASS 5 – Token Ring: Viva Preparation

---

## 🔴 Must-Know Questions

### Q1. What is Token Ring?
**A:** Token Ring is a network access method where processes are in a logical ring. A special **token** circulates around the ring — only the token holder can transmit data or enter the critical section.

### Q2. What is a Token?
**A:** A token is a special control frame/message that acts as a "permission slip." Its properties:
- Only **one** token exists in the ring at any time
- Only the **holder** can transmit
- After transmitting, it's passed to the next node

### Q3. Trace: 5 nodes, Sender=1, Receiver=3, Data=42, Token at 0

**A:**
```
Token passes: 0 → 1 (reaches sender)
Sender 1 transmits data=42:
  Data Forwarded By: 1
  Data Forwarded By: 2
  Receiver: 3 Received The Data: 42
Token now at: 1
```

### Q4. Mutual Exclusion properties satisfied by Token Ring?

| Property | How |
|----------|-----|
| **Mutual Exclusion** | Only token holder acts → one in critical section |
| **Progress** | Token always circulates |
| **Bounded Waiting** | Max wait = N-1 token passes |

### Q5. What does `(i+1) % n` do in the code?
**A:** Creates the circular ring. When `i = n-1` (last node), `(n-1+1) % n = 0` wraps back to start — simulating a ring topology.

### Q6. What if the token is lost?
**A:** Token loss stops the ring. Solutions:
1. **Timeout + Regeneration** — monitor detects no token, generates new one
2. **IEEE 802.5 Monitor Station** — dedicated hardware monitor

### Q7. Token Ring vs Ethernet (CSMA/CD)?

| Feature | Token Ring | Ethernet |
|---------|-----------|----------|
| Collisions | None | Possible |
| Fairness | Guaranteed | Not guaranteed |
| Under heavy load | Better | Degrades |
| IEEE Standard | 802.5 | 802.3 |

### Q8. Why did Ethernet replace Token Ring?
**A:** Token Ring hardware was more expensive, Ethernet evolved to higher speeds faster (10→100→1000 Mbps), and switched Ethernet eliminated most collisions.

### Q9. What is the condition `if(receiver == sender + 1)` checking?
**A:** Checks if the receiver is the **immediately adjacent** next node. If yes, direct delivery without intermediate forwarding.

### Q10. Maximum waiting time in Token Ring?
**A:** With N nodes, maximum wait = **(N-1) token passes** — bounded, no starvation possible.

---

## 🟡 Quick-Fire Answers

| Question | Answer |
|----------|--------|
| IEEE standard for Token Ring | IEEE 802.5 |
| Classic Token Ring speed | 4 Mbps or 16 Mbps |
| Token Ring replaced by | Ethernet |
| Collision possible? | No |
| Fairness guaranteed? | Yes |
| Ring formula | `(i+1) % n` |
| Max wait (N nodes) | N-1 token passes |
| Token loss fix | Timeout + regenerate |

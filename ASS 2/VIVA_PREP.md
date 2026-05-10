# ASS 2 – Java CORBA: Viva Preparation

---

## 🔴 Must-Know Questions

### Q1. What is CORBA? What does it stand for?
**A:** CORBA stands for **Common Object Request Broker Architecture**. It is a middleware standard defined by the **OMG (Object Management Group)** that enables objects in different programming languages and on different platforms to communicate transparently over a network.

---

### Q2. What is an ORB? What is its role?
**A:** **ORB (Object Request Broker)** is the middleware that:
- Acts as the communication bus between client and server
- Locates the server object on behalf of the client
- Marshals/unmarshals (serializes/deserializes) parameters
- Handles low-level network communication (IIOP protocol)
- In this assignment, `orbd` is the ORB daemon running on port 1050

---

### Q3. What is IDL? Why is it used?
**A:** **IDL (Interface Definition Language)** is a language-neutral way to define the interface (methods and data types) for remote objects. It is:
- Not Java-specific — can generate stubs in C++, Python, etc.
- Compiled by `idlj` tool to generate Java binding files
- The "contract" between client and server

---

### Q4. What does `idlj -fall` do?
**A:** `idlj -fall ReverseModule.idl` generates **all** Java files needed for both client AND server:
- `-fall` = generate both client-side stubs and server-side skeletons
- `-fclient` would generate only client files
- `-fserver` would generate only server files

---

### Q5. What is POA? What does it stand for?
**A:** **POA (Portable Object Adapter)** is the server-side framework in CORBA that:
- Manages the lifecycle of server objects (called *servants*)
- Dispatches incoming requests to the right servant
- Activates/deactivates objects on demand
- Replaced the older **BOA (Basic Object Adapter)**

---

### Q6. What is the difference between CORBA and RMI?
| Feature | CORBA | RMI |
|---------|-------|-----|
| Language support | Multi-language (Java, C++, etc.) | Java only |
| Interface definition | IDL | Java interface |
| Transport protocol | IIOP | JRMP |
| Complexity | Higher | Lower |
| Removed from Java | Java 11+ | Still available |

---

### Q7. What is IIOP?
**A:** **IIOP (Internet Inter-ORB Protocol)** is the standard TCP/IP-based communication protocol used by CORBA. It ensures that ORBs from different vendors can communicate. It runs on top of TCP/IP.

---

### Q8. What files does `idlj` generate and what are their purposes?
**A:**
| File | Purpose |
|------|---------|
| `Reverse.java` | Java remote interface |
| `ReverseHelper.java` | Utility: narrows (casts) object references |
| `ReverseHolder.java` | For `inout`/`out` IDL parameters |
| `ReverseOperations.java` | Interface to implement on server |
| `ReversePOA.java` | Server skeleton (extends this class) |
| `_ReverseStub.java` | Client-side proxy |

---

### Q9. What is `ReverseHelper.narrow()`?
**A:** CORBA uses generic `CORBA.Object` references. `narrow()` is like a **type-safe cast** that converts a generic CORBA object reference into a specific interface type (e.g., `Reverse`). It verifies the type at runtime.

---

### Q10. What is the Naming Service in CORBA?
**A:** The **CORBA Naming Service** (part of `orbd`) is like a directory service (similar to RMI Registry). The server **registers** its object with a name; the client **looks up** that name to get a reference. This assignment uses `NamingContextExt` for this purpose.

---

### Q11. Why was CORBA removed from Java 11+?
**A:**
1. Rarely used in modern development (replaced by REST, gRPC)
2. Heavy, complex setup
3. Security vulnerabilities in older ORB implementations
4. Part of Java's module cleanup effort (JEP 320)

---

### Q12. What is marshalling in CORBA?
**A:** Marshalling (also called **serialization**) is the process of converting method parameters and return values into a byte stream for transmission over the network via IIOP. The reverse process (byte stream → Java objects) is called **unmarshalling**.

---

### Q13. What port does CORBA use in this assignment?
**A:** Port **1050** (passed via `-ORBInitialPort 1050`). The default CORBA port is 900, but 1050 is used here to avoid needing root/admin privileges.

---

### Q14. Explain the execution sequence of this assignment.
**A:**
1. `orbd` starts on port 1050 (acts as ORB + Naming Service)
2. `ReverseServer` initializes ORB → creates `ReverseImpl` instance → registers with Naming Service as "Reverse"
3. `ReverseClient` initializes ORB → looks up "Reverse" in Naming Service → gets `_ReverseStub`
4. Client calls `obj.reverseString("hello")` → stub marshals → IIOP sends to server
5. Server's POA dispatches to `ReverseImpl.reverseString()` → returns "olleh"
6. Result marshaled back → client receives and prints "olleh"

---

## 🟡 Quick-Fire Answers

| Question | Answer |
|----------|--------|
| Full form of CORBA | Common Object Request Broker Architecture |
| Full form of ORB | Object Request Broker |
| Full form of IDL | Interface Definition Language |
| Full form of POA | Portable Object Adapter |
| Full form of IIOP | Internet Inter-ORB Protocol |
| ORB port in this assignment | 1050 |
| Tool to compile IDL | `idlj` |
| Standard defining CORBA | OMG (Object Management Group) |
| CORBA removed from Java version | Java 11 (JEP 320) |
| What does `orbd` stand for? | Object Request Broker Daemon |

# ASS 1 – Java RMI: Viva Preparation

---

## 🔴 Must-Know Questions

### Q1. What is RMI? What does it stand for?
**A:** RMI stands for **Remote Method Invocation**. It is a Java API that allows a method in one JVM to invoke a method on an object residing in another JVM, even on a different physical machine. It enables distributed computing in pure Java.

---

### Q2. What is the role of the RMI Registry?
**A:** The RMI Registry is a **naming service** (like a phone directory). The server **registers** its remote object with a name using `Naming.rebind()`. The client **looks up** that name using `Naming.lookup()` to get a reference (stub) to the remote object. It runs on port **1099** by default.

---

### Q3. What is a Stub in RMI?
**A:** A **Stub** is a client-side proxy object that represents the remote object. When the client calls a method on the stub, it:
1. Serializes (marshals) the method name and arguments
2. Sends them over the network to the server
3. Waits for the result
4. Deserializes and returns the result to the client

---

### Q4. What is a Skeleton in RMI?
**A:** A **Skeleton** is the server-side counterpart of the stub. It:
1. Receives the call from the stub
2. Deserializes the method arguments
3. Calls the actual method on the server object
4. Serializes and sends the result back
> **Note:** In Java 2+, skeletons are no longer needed — the JVM handles dispatching internally.

---

### Q5. Why does the Remote interface need to extend `java.rmi.Remote`?
**A:** Extending `Remote` marks the interface as one whose methods can be called from a different JVM. It's a **marker interface** — it has no methods but tells the RMI runtime that this interface is remotely accessible.

---

### Q6. Why does every remote method throw `RemoteException`?
**A:** Remote method calls involve network communication which can fail due to:
- Network failures
- Server crashes
- Serialization errors
`RemoteException` is a checked exception that forces the client to handle these network-related failures explicitly.

---

### Q7. What is Marshalling and Unmarshalling?
**A:**
- **Marshalling**: Converting method parameters into a byte stream for transmission over the network (serialization)
- **Unmarshalling**: Converting the received byte stream back into Java objects (deserialization)

---

### Q8. What is `UnicastRemoteObject`?
**A:** `UnicastRemoteObject` is the base class for RMI server objects. Extending it:
- Exports the object to make it available for remote calls
- Uses **point-to-point (unicast)** TCP communication
- Automatically handles the creation of stub/skeleton infrastructure

---

### Q9. What is the difference between RMI and RPC?
| RMI | RPC |
|-----|-----|
| Java-specific | Language-independent |
| Object-oriented (passes objects) | Procedure-oriented (passes data) |
| Uses Java serialization | Uses platform-neutral encoding (XDR) |
| Methods on remote objects | Functions on remote servers |

---

### Q10. What port does RMI use?
**A:** The RMI Registry uses **port 1099** by default. Actual remote object communication uses dynamically assigned ports (or you can specify them).

---

### Q11. What happens if the rmiregistry is not running when the server tries to bind?
**A:** The server throws a `java.rmi.ConnectException: Connection refused to host` or `MalformedURLException`. The registry must be started **before** the server.

---

### Q12. What is the difference between `rebind()` and `bind()` in Naming?
**A:**
- `Naming.bind(name, obj)` — Registers the object; throws `AlreadyBoundException` if name already exists
- `Naming.rebind(name, obj)` — Registers the object; **overwrites** any existing binding with the same name (safer for restarting servers)

---

### Q13. Can RMI work across different machines?
**A:** Yes. Change the URL from `"rmi://localhost/Server"` to `"rmi://192.168.x.x/Server"` (server's IP). Both machines must have the same class files or use dynamic class loading.

---

### Q14. What is the sequence of execution in your program?
**A:**
1. `rmiregistry` starts (port 1099)
2. `Server.java` creates `ServerImpl` instance → calls `Naming.rebind("Server", serverimpl)` → prints "server started!"
3. `Client.java` calls `Naming.lookup("rmi://localhost/Server")` → gets stub
4. Client calls remote methods (addition, subtraction, etc.) via stub
5. Stub sends requests over TCP → Server executes → returns results
6. Client prints results

---

### Q15. What is the advantage of RMI over socket programming?
**A:**
- RMI provides a **higher-level abstraction** — you call methods like local functions
- No manual serialization/deserialization of data
- No manual protocol design
- Supports passing of complex Java objects (not just raw bytes)
- Built-in threading and connection management

---

## 🟡 Quick-Fire Answers

| Question | Answer |
|----------|--------|
| Default RMI port | 1099 |
| RMI package | `java.rmi` |
| Base class for server | `UnicastRemoteObject` |
| Binding utility | `Naming` class |
| Communication protocol | TCP/IP |
| Serialization mechanism | Java Object Serialization |
| Is CORBA related to RMI? | No, CORBA is language-neutral; RMI is Java-only |
| What does `Naming.lookup()` return? | A `Remote` object (cast to interface) |

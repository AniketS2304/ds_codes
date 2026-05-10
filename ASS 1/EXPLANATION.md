# ASS 1 – Java RMI: Technical Explanation & Theory

---

## 1. What is RMI?

**Remote Method Invocation (RMI)** is a Java API that allows a program running on one JVM to invoke methods on an object running in another JVM — possibly on a different machine.

> In simple terms: Call a function that runs on a different computer, as if it were local.

---

## 2. Core Concepts

### 2.1 Architecture Layers

```
CLIENT JVM                          SERVER JVM
┌─────────────┐   Network (TCP)    ┌──────────────────┐
│  Client.java│◄──────────────────►│ ServerImpl.java  │
│             │                    │ (actual logic)   │
│  Stub       │                    │ Skeleton         │
│  (proxy)    │                    │ (dispatcher)     │
└─────────────┘                    └──────────────────┘
                   RMI Registry
               ┌─────────────────┐
               │  Naming.rebind  │ ← Server registers
               │  Naming.lookup  │ ← Client discovers
               └─────────────────┘
```

### 2.2 Components

| Component | File | Role |
|-----------|------|------|
| **Remote Interface** | `ServerIntf.java` | Declares methods callable remotely |
| **Implementation** | `ServerImpl.java` | Actual method logic on server |
| **Server** | `Server.java` | Registers implementation with RMI registry |
| **Client** | `Client.java` | Looks up server, calls remote methods |
| **RMI Registry** | `rmiregistry` | Name-to-object directory service |

---

## 3. How It Works – Step by Step

### Step 1: Define the Remote Interface
```java
interface ServerIntf extends Remote {
    public int addition(int a, int b) throws RemoteException;
    // ... more methods
}
```
- Must extend `java.rmi.Remote`
- Every method must throw `RemoteException`

### Step 2: Implement the Interface
```java
class ServerImpl extends UnicastRemoteObject implements ServerIntf {
    public int addition(int a, int b) { return a + b; }
}
```
- Extends `UnicastRemoteObject` to enable point-to-point RMI communication
- The JVM auto-generates a **Stub** (client-side proxy) and **Skeleton** (server-side dispatcher)

### Step 3: Server Registers the Object
```java
ServerImpl serverimpl = new ServerImpl();
Naming.rebind("Server", serverimpl);
```
- Binds the object to the RMI registry under the name `"Server"`

### Step 4: Client Looks Up and Calls
```java
ServerIntf serverIntf = (ServerIntf) Naming.lookup("rmi://localhost/Server");
int result = serverIntf.addition(10, 5); // → 15
```
- Finds the stub from registry
- Calling `addition()` → stub serializes parameters → sends over TCP → server deserializes → executes → returns result

---

## 4. Data Flow Diagram

```
Client.java                RMI Registry         ServerImpl.java
    │                           │                      │
    │── Naming.lookup("Server")─►                      │
    │◄── returns Stub ──────────│                      │
    │                                                  │
    │── stub.addition(10, 5) ──────────────────────────►
    │   [serialize args over TCP]                      │
    │                                              execute addition
    │◄─ return 15 ─────────────────────────────────────│
    │   [deserialize result]
```

---

## 5. Key Java RMI Classes

| Class/Interface | Package | Purpose |
|-----------------|---------|---------|
| `Remote` | `java.rmi` | Marker interface for remote objects |
| `RemoteException` | `java.rmi` | Exception for remote call failures |
| `UnicastRemoteObject` | `java.rmi.server` | Base class for server objects |
| `Naming` | `java.rmi` | Utility for bind/lookup in registry |

---

## 6. Advantages & Disadvantages

### Advantages ✅
- Pure Java solution — no platform dependency
- Object-oriented remote calls
- Built-in serialization
- Supports distributed garbage collection

### Disadvantages ❌
- Java-to-Java only (not cross-language)
- Network latency for every method call
- Firewall/port issues
- More complex than REST APIs

---

## 7. Real-World Use Cases
- Distributed Java EE applications (EJB uses RMI under the hood)
- Java IDEs communicating with remote JVMs for debugging
- Cluster computing with homogeneous Java nodes

---

## 8. This Assignment's Operations

| Method | Operation | Example (a=10, b=5) |
|--------|-----------|---------------------|
| `addition(a,b)` | a + b | 15 |
| `substraction(a,b)` | a - b | 5 |
| `multiplication(a,b)` | a × b | 50 |
| `division(a,b)` | a / b | 2 |
| `square(a)` | a² | 100 |
| `squareroot(b)` | √b | 2 |

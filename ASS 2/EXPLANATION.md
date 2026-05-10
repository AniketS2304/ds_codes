# ASS 2 – Java CORBA: Technical Explanation & Theory

---

## 1. What is CORBA?

**CORBA (Common Object Request Broker Architecture)** is a middleware standard defined by the **OMG (Object Management Group)** that enables objects written in different programming languages (Java, C++, Python, etc.) running on different platforms to communicate with each other.

> Unlike RMI (Java-only), CORBA is **language-neutral and platform-neutral**.

---

## 2. Key Difference: CORBA vs RMI

| Feature | CORBA | RMI |
|---------|-------|-----|
| Language | Any (Java, C++, Python...) | Java only |
| Interface Definition | **IDL** (Interface Definition Language) | Java interface |
| Middleware | **ORB** (Object Request Broker) | RMI Registry |
| Protocol | **IIOP** (Internet Inter-ORB Protocol) | JRMP (Java RMI protocol) |
| Complexity | Higher | Lower |
| Still in use? | Legacy (removed Java 11+) | Used in EJB |

---

## 3. Architecture

```
CLIENT JVM                         ORB (orbd)          SERVER JVM
┌──────────────────┐               ┌──────────┐        ┌──────────────────┐
│  ReverseClient   │               │          │        │  ReverseServer   │
│  (uses _Stub)    │◄─────IIOP────►│   ORB    │◄──────►│  (extends POA)   │
│                  │               │  daemon  │        │  ReverseImpl     │
└──────────────────┘               └──────────┘        └──────────────────┘
          ↑                                                     ↑
   _ReverseStub                                          ReversePOA
  (auto-generated)                                      (auto-generated)
```

---

## 4. IDL – Interface Definition Language

The starting point is the `.idl` file, which defines the interface in a **language-neutral** way:

```idl
// ReverseModule.idl
module ReverseModule {
    interface Reverse {
        string reverseString(in string input);
    };
};
```

- `module` = namespace (like a Java package)
- `interface` = remote interface definition
- `in` = input parameter direction

---

## 5. How `idlj` Works

Running `idlj -fall ReverseModule.idl` auto-generates 6 Java files:

| Generated File | Purpose |
|----------------|---------|
| `Reverse.java` | Java interface equivalent of the IDL |
| `ReverseHelper.java` | Utility for narrowing (casting) remote references |
| `ReverseHolder.java` | For `inout`/`out` parameters |
| `ReverseOperations.java` | Operations interface (implemented by server) |
| `ReversePOA.java` | **Portable Object Adapter** — server skeleton |
| `_ReverseStub.java` | Client-side stub (proxy) |

---

## 6. How It Works – Step by Step

### Server Side
```java
// ReverseImpl.java — implements the business logic
class ReverseImpl extends ReversePOA {
    public String reverseString(String input) {
        return new StringBuilder(input).reverse().toString();
    }
}
```

```java
// ReverseServer.java — registers with the ORB
ORB orb = ORB.init(args, null);
POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
rootpoa.the_POAManager().activate();

ReverseImpl reverseImpl = new ReverseImpl();
org.omg.CORBA.Object ref = rootpoa.servant_to_reference(reverseImpl);

// Register with the CORBA Naming Service
NamingContextExt ncRef = NamingContextExtHelper.narrow(
    orb.resolve_initial_references("NameService")
);
ncRef.rebind(ncRef.to_name("Reverse"), ref);
orb.run(); // block and serve requests
```

### Client Side
```java
// ReverseClient.java — looks up and calls the server
ORB orb = ORB.init(args, null);
NamingContextExt ncRef = NamingContextExtHelper.narrow(
    orb.resolve_initial_references("NameService")
);
Reverse obj = ReverseHelper.narrow(ncRef.resolve_str("Reverse"));
String result = obj.reverseString("hello"); // → "olleh"
```

---

## 7. POA – Portable Object Adapter

The **POA** is the server-side framework that:
- Manages remote object lifecycle
- Dispatches incoming requests to the right servant (implementation)
- Handles threading and request queuing

---

## 8. IIOP – Internet Inter-ORB Protocol

CORBA uses **IIOP** (built on TCP/IP) as the transport protocol. It is the standard wire protocol that allows different ORB vendors' implementations to communicate with each other.

---

## 9. Data Flow

```
Client calls obj.reverseString("hello")
    ↓
_ReverseStub serializes call → sends via IIOP to ORB port 1050
    ↓
orbd routes request to ReverseServer
    ↓
ReversePOA dispatches to ReverseImpl.reverseString("hello")
    ↓
Returns "olleh" → serialized back through ORB → Client receives "olleh"
```

---

## 10. Why CORBA Was Removed from Java

- Rarely used in modern development (REST/gRPC replaced it)
- Heavy and complex setup
- Security vulnerabilities in older ORB implementations
- Removed in Java 11 (JEP 320)

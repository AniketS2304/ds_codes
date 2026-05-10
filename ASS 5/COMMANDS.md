# ASS 5 – Token Ring: Execution Commands

## Prerequisites
- Java JDK installed (`javac` + `java` in PATH)
- File: `TokenRing.java`

---

## Step-by-Step Execution

```bash
# Step 1: Compile
javac TokenRing.java

# Step 2: Run
java TokenRing
```

---

## Sample Run (Interactive Input)

```
Enter Number Of Nodes You Want In The Ring : 5
Ring Formed Is As Below:
0 1 2 3 4 0

Enter Sender : 1
Enter Receiver : 3
Enter Data To Send : 42
Token Passing :  0-> 1
Sender:1 Sending Data: 42
Data: 42 Forwarded By: 1
Data: 42 Forwarded By: 2
Receiver: 3 Received The Data: 42
Do You Want To Send Data Again? If YES Enter 1, If NO Enter 0: 0
```

---

## Multiple Send Example (Send again = 1)

```
# Inputs:
# 5 nodes
# First send: Sender=0, Receiver=2, Data=100  → continue (1)
# Second send: Sender=2, Receiver=4, Data=200 → stop (0)

Enter Number Of Nodes You Want In The Ring : 5
...
Enter Sender : 0
Enter Receiver : 2
Enter Data To Send : 100
Token Passing :  0
Sender:0 Sending Data: 100
Data: 100 Forwarded By: 0
Data: 100 Forwarded By: 1
Receiver: 2 Received The Data: 100
Do You Want To Send Data Again? If YES Enter 1, If NO Enter 0: 1

Enter Sender : 2
Enter Receiver : 4
Enter Data To Send : 200
...
```

---

## One-liner with Piped Input (PowerShell)
```powershell
# 5 nodes, sender=1, receiver=3, data=42, no repeat
"5`n1`n3`n42`n0`n" | java TokenRing
```

---

## Quick Compile + Run with JDK 21 Full Path
```powershell
$jdk = "C:\Program Files\Microsoft\jdk-21.0.11.10-hotspot\bin"
& "$jdk\javac.exe" TokenRing.java
"5`n1`n3`n42`n0`n" | & "$jdk\java.exe" TokenRing
```

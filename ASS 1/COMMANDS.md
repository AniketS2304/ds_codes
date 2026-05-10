# ASS 1 – Java RMI Calculator: Execution Commands

## Prerequisites
- Java JDK installed (javac + java in PATH)
- All 4 files present: ServerIntf.java, ServerImpl.java, Server.java, Client.java

---

## Step-by-Step Execution (3 Terminals Required)

### Terminal 1 – Compile & Start RMI Registry
```bash
# Verify Java version
java -version

# Compile all Java files
javac *.java

# Start the RMI Registry (keep this running)
rmiregistry
```

### Terminal 2 – Start the Server
```bash
# Run the server (keep this running)
java Server
```
**Expected output:** `server started !`

### Terminal 3 – Run the Client
```bash
# Run the client
java Client
```
**Enter when prompted:**
- `num1` → e.g., `10`
- `num2` → e.g., `5`

**Expected output:**
```
Enter num1
Enter num2
Add is 15
sub is 5
mul is 50
div is 2
square is 100
squar root is 2
```

---

## Quick One-liner (for testing with JDK 21 path)
```powershell
# In project directory
$javac = "C:\Program Files\Microsoft\jdk-21.0.11.10-hotspot\bin\javac.exe"
$java  = "C:\Program Files\Microsoft\jdk-21.0.11.10-hotspot\bin\java.exe"
$rmi   = "C:\Program Files\Microsoft\jdk-21.0.11.10-hotspot\bin\rmiregistry.exe"

& $javac *.java
$reg = Start-Process $rmi -NoNewWindow -PassThru; Start-Sleep 2
$srvJob = Start-Job { param($j) Set-Location "ASS 1 path"; & $j Server } -Arg $java
Start-Sleep 3
"10`n5`n" | & $java Client
```

---

## Shutdown
- Press `Ctrl+C` in Terminal 1 (rmiregistry) and Terminal 2 (server)

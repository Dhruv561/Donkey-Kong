# **Shadow Donkey Kong - Game Guide & Git Log Instructions**

## **📌 Overview**
This document provides an overview of the **Shadow Donkey Kong game** and instructions on how to log **Git commit history** using a Bash script.

---

## **🎮 Shadow Donkey Kong Game Guide**
Shadow Donkey Kong is a classic platformer where players control **Mario** to navigate obstacles, climb ladders, jump on platforms, and defeat Donkey Kong using a hammer. The game features:
- **Platform mechanics**: Mario can walk, jump, and land on platforms.
- **Ladder climbing**: Mario can climb up and down ladders.
- **Hammers**: Mario can pick up a hammer to defeat Donkey Kong.
- **Time-based challenge**: The level must be completed within a time limit.

### **🔹 How to Play**
1. **Start the game**: Run the game and begin on the home screen.
2. **Move Mario**: Use the arrow keys to move left, right, and jump.
3. **Climb ladders**: Approach a ladder and press the UP or DOWN key.
4. **Use a hammer**: Pick up a hammer to defeat Donkey Kong.
5. **Win the level**: Reach Donkey Kong while holding a hammer before time runs out.

---

## **🛠️ Git Commit Log Instructions**
This section explains how students can automatically **generate a Git commit log** and save it in a file named after their **student number**.

### **1️⃣ Prerequisites**
- Git must be installed on your system.
- You must be inside a **Git repository** (a project that has been initialized with Git).
- This works on **Linux, macOS, and Windows (WSL)**.

### **2️⃣ How to Run the Bash Script**
#### **For Linux/macOS**
1. **Open a Terminal**: Navigate to the directory:
   ```sh
   cd /path/to/your/git/repo
   ```
2. **Grant Execution Permission**:
   ```sh
   chmod +x generate_git_log.sh
   ```
3. **Run the Script**:
   ```sh
   ./generate_git_log.sh
   ```
4. **Enter Your Student Number**: You will be prompted to enter it.
5. **View the Generated Log File**: A file `STUDENT_NUMBER.txt` will be created in the same directory.

#### **For Windows (WSL)**
1. **Enable WSL (If Not Installed)**:
   ```sh
   wsl --install
   ```
2. **Open WSL and Navigate to the Repository**:
   ```sh
   cd /mnt/c/path/to/your/git/repo
   ```
3. **Follow the Linux/macOS Steps Above**.

### **3️⃣ Example Output (`12345678.txt`)**
```
a1b2c3d - John Doe, 3 days ago : Initial commit
d4e5f6g - Alice Smith, 1 hour ago : Fixed a bug
```

### **4️⃣ Troubleshooting**
#### **🛑 "This is not a Git repository" Error**
If you see this error:
```sh
❌ Error: This is not a Git repository. Please navigate to your Git project directory.
```
Make sure you are inside a Git-tracked project:
```sh
git status
```
If Git is not initialized, run:
```sh
git init
```

#### **🛑 "Permission Denied" Error**
If you get a permission error, use:
```sh
chmod +x generate_git_log.sh
./generate_git_log.sh
```


# VoIP Call Simulation Android App

This Android project is a simulated VoIP calling application developed in **Java**. It mimics real call behavior using Android components, including `AlarmManager`, `BroadcastReceiver`, a full-screen `Activity`, `Service`, and a local `Room` database. The app is built with **MVVM architecture** and is fully compatible with **Android 15**.

🔗 **GitHub Repo**: [https://github.com/aryanandroiddev/VoIPCallSim](https://github.com/aryanandroiddev/VoIPCallSim)

---

## 📱 Features

### 🔔 Incoming Call Simulation
- Scheduled simulated call via `AlarmManager`
- Full-screen call UI appears even when:
  - App is in the background
  - Phone is locked
  - Device is in Doze mode (where possible)
- Compliant with Android 15's notification and permission requirements

### 📞 Call UI
- WhatsApp/Truecaller-like incoming call screen
- Includes:
  - Answer and Reject buttons
  - Caller name display (hardcoded as `"Test Caller"`)
  - Ringtone and vibration
- Auto-dismisses after 10 seconds if unanswered

### 📟 Ongoing Call
- Foreground Service displays timer and "End Call" button
- Timer continues even if the app is backgrounded

### 🧾 Call Logs
- Stored locally using **Room DB**
- Call details include:
  - Caller name
  - Call start/end time
  - Duration
  - Type: Answered or Missed

### 🔔 Missed Call Notification
- Notification includes:
  - Caller name
  - Time
  - "Call Back" quick action
- Tapping the notification opens the call log screen

### ✅ Bonus
- Schedule a test call using the main screen

---

## 🔧 Tech Stack

- Java
- MVVM Architecture (LiveData, ViewModel, Repository)
- Room DB (SQLite)
- AlarmManager
- ForegroundService
- NotificationChannel
- AndroidX Libraries
- Android SDK 34 (Android 15)

---

## ✅ Permissions Required

- `POST_NOTIFICATIONS` (Android 13+)
- `FOREGROUND_SERVICE`
- `FOREGROUND_SERVICE_MEDIA_PLAYBACK`
- `FOREGROUND_SERVICE_DATA_SYNC` (Android 14+)

Make sure to **manually allow permissions** from system settings if not prompted.

---

## ▶️ How to Run

```bash
git clone https://github.com/aryanandroiddev/VoIPCallSim.git
```

1. Open the project in **Android Studio Hedgehog or later**
2. Run on **Android 13+** emulator or real device
3. Accept all permissions
4. Click `Schedule Call` on the main screen
5. A full-screen call UI will appear after 5 seconds
---

## 📁 Folder Structure

```
voipcallsim/
├── adapter/
│   └── CallLogAdapter.java
├── data/
│   ├── model/
│   │   └── CallLog.java
│   └── db/
│       ├── AppDatabase.java
│       └── CallLogDao.java
├── domain/
│   └── repository/
│       └── CallRepository.java
├── receiver/
│   └── CallReceiver.java
├── service/
│   └── CallService.java
├── ui/
│   ├── viewmodel/
│   │   └── CallViewModel.java 
│   └── activity/
│       ├── MainActivity.java
│       ├── IncomingCallActivity.java
│       ├── OngoingCallActivity.java
│       └── CallLogActivity.java
└── utils/ (currently empty)
```
**Aryan Srivastava**  
aryaupvns@gmail.com
https://www.linkedin.com/in/aryan-srivastava-vns/
Noida, Uttar Pradesh, India

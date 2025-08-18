# ✈️ Airline Management System  

## 📌 Overview  
The **Airline Management System** is a Java-based, database-driven application designed to manage airline operations efficiently.  
It provides functionality for passenger registration, flight scheduling, ticket booking, payment processing, and reporting—aiming to reduce manual workload and improve accuracy.  

---

## 🚀 Features  
- 🔑 **User Authentication** – Secure login page for authorized staff.  
- 🏠 **Homepage Dashboard** – Centralized panel to navigate system modules.  
- 👥 **Passenger Management** – Add, update, and manage customer records.  
- ✈️ **Flight Management** – Schedule and manage flights with unique flight codes.  
- 📖 **Booking System** – Reserve seats, verify availability, and record transactions.  
- 💳 **Payment Processing** – Dynamic pricing, discount handling, and secure transactions.  
- 🎫 **Boarding Pass Generator** – Auto-generate digital/printable boarding passes after booking.  
- ❌ **Cancellation Module** – Cancel tickets with proper refund/adjustment.  
- 📊 **Revenue & Analytics** – Visualize earnings and booking trends using charts.  
- 🗄️ **Relational Database Design** – Structured schema with primary/foreign keys and constraints for data integrity.  

---

## 🛠️ Technology Stack  
- **Programming Language:** Java  
- **Database:** MySQL  
- **UI Framework:** Java Swing / JavaFX  
- **Tools & IDEs:** MySQL Workbench, NetBeans / IntelliJ IDEA  
- **Version Control:** Git  

---

## 🗂️ Database Design  

### 📋 Key Tables  
- **Login** – Admin/staff credentials  
- **Passenger** – Passenger personal details  
- **Flight** – Flight information (codes, names, schedules)  
- **Booking** – Links passengers to booked flights  
- **Payment** – Records transaction details  
- **Cancellation** – Stores canceled ticket details  
- **Revenue** – Aggregates payments and cancellations for chart visualization  

### 📌 Diagrams  
**Entity–Relationship Diagram:**  
![ER Diagram](https://github.com/user-attachments/assets/1855e321-9afd-4bd7-932f-9986edffc25b)  

**Schema Relationship Diagram:**  
![Schema Diagram](https://github.com/user-attachments/assets/ff24ae54-4cc8-4351-8bf8-392c8522081a)  

**Block Diagram:**  
![Block Diagram](https://github.com/user-attachments/assets/39c085b4-791d-4e59-8405-a84f8f9dfcc9)  

---

## 🖥️ User Interface Modules  

### 🔑 Login Page  
Secure authentication for staff/admin. Prevents unauthorized access.  
![Login page](https://github.com/user-attachments/assets/c28d9100-b7bb-4fd3-a9d3-d99d6816a9e9)  

---

### 🏠 Homepage Dashboard  
Quick access to all system functions, displaying key flight & booking stats.  
![Home Page](https://github.com/user-attachments/assets/dda7620e-9fe6-4332-8e9d-b3841d0462a7)  

---

### 👥 Customer Management (Add / Update)  
- Register new passengers  
- Edit existing passenger details  
- Delete customer records  
![Customer Management](https://github.com/user-attachments/assets/7a242585-b450-433a-b539-0d2fd0b12340)  

---

### ✈️ Book Flight  
- Search available flights  
- Assign seats and confirm booking  
- Automatically linked with payment module  
![Book Flight](https://github.com/user-attachments/assets/00521eb0-b0d3-4364-acbb-da8046037090)  

---

### 💳 Payment Page  
- Dynamic pricing and discounts  
- Secure transaction recording in **Payment** table  
![Payment](https://github.com/user-attachments/assets/7643a8ed-9800-4cf3-8f77-f39777db3b26)  

---

### 🎫 Boarding Pass  
- Generates digital/printable passes  
- Contains flight info, seat number, QR/barcode  
![BoardingPass](https://github.com/user-attachments/assets/b8a38d3a-8074-4d4a-aaa6-1d878ba92626)  

---

### ❌ Cancellation Page  
- Cancel tickets with refund adjustments  
- Updates **Booking**, **Payment**, and **Cancellation** tables  
![Cancellation](https://github.com/user-attachments/assets/ce0580fb-ac91-4e68-b5a6-7c8b7c999a62)  

---

### 📊 Revenue Chart Page  
- Displays earnings from bookings  
- Daily, weekly, and monthly trends  
- Helps in performance analysis  
![Revenue](https://github.com/user-attachments/assets/1f0bc67a-4d19-4e9c-82c5-852fbc4eea62)  

---

## 🔮 Future Enhancements  
- 🌐 Online booking portal for passengers  
- 📱 Mobile app integration  
- 🤖 AI-based demand forecasting for pricing  
- 🌍 Multi-language support  

---

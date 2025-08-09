# Pharmacy Management System : Desktop Application

## Project Overview
This project is a comprehensive desktop application developed in **Java** using **Object-Oriented Programming (OOP)** principles to manage the daily operations of a pharmacy. The application connects to a **MySQL** database for efficient and persistent data storage.

This project was developed by me and 3 of my friends, as a final project for my **Object-Oriented Programming Class** in University. 

## Tab Key Features
**Pharmacist** : The pharmacist data is used to login on the first page. This tab provides CRUDS system for those pharmacist data (name, salary, email), and an auto-generated username & password for the pharmacist that just got created.

**Customer** : CRUDS for customers data (name, age, phone number)

**Product** : CRUDS to manage products (medicines & vitamins) inventory (product's name, availability stocks, price, prescription type, how to consume, and the expired date)  

**Prescription** : CRUDS for the prescription data (doctor's name, how to use, total prescription). The Many-To-Many concept is used here to manage doctor's prescriptions, linking them to relevant customers and medicines

**Transaction** : CRUDS for the Transaction records (transaction's date, amount, total price), use Many-To-Many relationship to link the customer and the products they bought, and to handle more than 1 transaction per customers.

**Report** : Tracks both transactions and the prescriptions that are made in that pharmacy, featuring a total earnings from the transaction, amounts of transaction that occurs per months, best selling product, amounts of prescription that are being handle by each doctors, most frequently prescribed medicines, and list of customers serviced by each pharmacists

## Application's Interface
Here are some screenshots of the application's interface:
![WhatsApp Image 2025-08-09 at 18 59 39](https://github.com/user-attachments/assets/45dcb8c3-cab3-459f-8fa2-253dd6698f5f)

![WhatsApp Image 2025-08-09 at 18 59 49](https://github.com/user-attachments/assets/c0418680-c4a2-48e8-8fea-872b770ebcee)

![WhatsApp Image 2025-08-09 at 19 00 01](https://github.com/user-attachments/assets/b1fed165-9b41-4fad-914d-b599228d458c)

![WhatsApp Image 2025-08-09 at 19 00 18](https://github.com/user-attachments/assets/98102d2b-bb4d-4936-a849-0a85b7f9f412)

![WhatsApp Image 2025-08-09 at 19 01 26](https://github.com/user-attachments/assets/f5640b13-07f7-432a-95e6-c45496ed2ae6)

![WhatsApp Image 2025-08-09 at 19 01 49](https://github.com/user-attachments/assets/7f368381-ec82-4625-b11a-312db0b477c9)

![WhatsApp Image 2025-08-09 at 19 02 03](https://github.com/user-attachments/assets/c441b8e6-0820-48de-9e8a-2e28a58936ac)


## Tools
- Java Development Kit (JDK) 8 or later.

- MySQL Server.

- Apache NetBeans IDE (recommended).

- jcalendar library to access date more efficiently

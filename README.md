# University Canteen Ordering System

A simple Java application for ordering food from a university canteen. It
supports three roles — **student**, **lecturer**, and **staff** — and comes
in two forms that share the same data files:

- A **Swing GUI** (`CanteenAppGUI`) — the primary way to use the app.
- A **console (text menu) version** built from several cooperating classes
  (`MainMenu`, `LoginApp`, `CreateAccountApp`, `StudentDashboard`,
  `LecturerDashboard`, `StaffDashboard`, `StaffFeedbackDashboard`).

## Features

- **Account creation & login** for student, lecturer, and staff roles.
- **Students & Lecturers**: browse the daily menu, add items to a basket,
  view/edit the basket, check out with a simulated card payment, view their
  own order history, view notifications, and submit feedback.
- **Staff**: view all orders, search orders by email, filter orders by role,
  update an order's status (Pending → Preparing → Ready → Completed —
  moving an order to "Ready" automatically notifies the customer), delete
  an order, manage the menu (add/remove items), and manage feedback
  (view all, search by email, delete).

## Project structure

```
src/LoginPackage/
  CanteenAppGUI.java          Swing GUI entry point (main)
  MainMenu.java                Console entry point (main)
  LoginApp.java, CreateAccountApp.java
  LoginService.java, AccountCreationService.java
  StudentDashboard.java, LecturerDashboard.java
  StaffDashboard.java, StaffFeedbackDashboard.java
  User.java, MenuItem.java, BasketItem.java, Order.java,
  Feedback.java, Notification.java        (model classes)
  UserDatabase.java, MenuDatabase.java, OrderDatabase.java,
  FeedbackDatabase.java, NotificationDatabase.java   (flat-file storage)
  PaymentService.java          Simulated card payment
  Console.java                 Shared System.in Scanner for the console UI
```

Data is persisted in plain-text, comma/pipe-delimited files in the project
root: `users.txt`, `menu.txt`, `orders.txt`, `feedback.txt`,
`notifications.txt`. These are created/updated automatically as the app
runs; passwords are stored as plain text, so treat this as a learning
project rather than a production system.

## Building & running

This is a plain Eclipse Java project (no Maven/Gradle). You can either
open it in Eclipse (`File > Import > Existing Projects into Workspace`)
and run a class with a `main` method, or use the JDK directly from the
project root:

```sh
# Compile
javac -d bin src/LoginPackage/*.java

# Run the graphical version
java -cp bin LoginPackage.CanteenAppGUI

# ...or run the console version
java -cp bin LoginPackage.MainMenu
```

Run commands from the project root so the app finds/creates its data
files (`users.txt`, `menu.txt`, etc.) alongside them.

### Sample accounts

`users.txt` ships with a set of pre-registered student accounts (format
`email,password,role`). You can also create a new account from the
"Create Account" option in either UI, choosing `student`, `lecturer`, or
`staff`.

GuestBook
==============

 Vaadin single page application that save message from guests to database.

Building
========

- git clone https://github.com/ARK21/guestbook.git
- cd guestbook
- mvn install
- mvn flyway:migrate
- mvn jetty:run  and open http://localhost:8080/

Functional
==========
<h1>Table</h1>

![default](https://user-images.githubusercontent.com/18110699/36639869-d96effbc-1a47-11e8-89a0-ff5f8c798ca7.png)

 Here you can see the list of all messages which is contained in database. One page of table can contain no more then 25 row.
- You can add new message by clicking at "Leave the message" button. Form for leaving message will appear.
- You can move between pages by clicking buttons "First", "Previous", "Next", "Last"
- To see full text of long message you need point at message cell in table, and then tooltip with full text will appear.
<h1>Table with tooltip</h1>

![tooltip](https://user-images.githubusercontent.com/18110699/36639959-977bbecc-1a49-11e8-9a24-86df9d6bb825.png)

<h1>Leaving massage form </h1>

![default](https://user-images.githubusercontent.com/18110699/36639968-bf39e466-1a49-11e8-9274-eaf6d5b9caa2.png)

In this form you should enter your data and message. To upload captcha press update button.
You also can hide form. Press button "Hide" or ESC. To save message enter "Save" or press ENTER button on the keyboard.

Technologies, libraries and plugins
===================================
- Java 8
- Vaadin 8
- Hibernate 4.3.10
- MySql 5.1.8
- Vaadin add-on: GridExtensionPack. To add page turn functional to table.
- Simplecaptcha library. To create captcha.
- Tomcat 8.0.35 or Jetty
- JUnit 4
- Plugin: Flyway. To generate database from SQL script.
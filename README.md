<div align="right">

<a href="README.md"><img src="https://flagcdn.com/w40/gb.png" width="25"></a> | <a href="README-ID.md"><img src="https://flagcdn.com/w40/id.png" width="20"></a>


</div>

# Java Social Media Application

A simple social media application built using Java with a graphical interface (GUI) using JFrame Form. This application integrates AWS DynamoDB as a database to store user data and social media content.

![ss](./readme%20media/RunSocialMedia.gif)

## Main Features

### 1. Account Management
- **Registration**: New users can create an account by filling in required information
- **Login**: Authentication system for registered users

  ![](./readme%20media/register%20page.png)

### 2. Home Page
- View posts from all users
- Create new posts
- Interact with posts

  ![](./readme%20media/beranda.png)

### 3. Messaging System
- Send private messages to other users
- View conversation history

  ![](./readme%20media/pesan.png)

## Technologies Used

![](https://cdn.hashnode.com/res/hashnode/image/upload/v1690034956546/101c1694-7e87-458e-afd5-ab65c48c468e.gif?w=1600&h=840&fit=crop&crop=entropy&auto=format,compress&gif-q=60&format=webm)

- **Backend & Frontend**: Java
- **IDE**: NetBeans
- **GUI Framework**: Java Swing (JFrame Form)
- **Database**: AWS DynamoDB
- **Components**: Java Class and JFrame Form

## Database
![](https://media.licdn.com/dms/image/v2/D5612AQHmXgaaTvUgUQ/article-cover_image-shrink_720_1280/article-cover_image-shrink_720_1280/0/1683409691265?e=2147483647&v=beta&t=MI0BsgNjRzCSGldmtEY7txk0WvBcMeEEm0jx8Cn5vYU)

AWS DynamoDB is used to store:
- User data
- Posts
- Post interactions (likes, comments)
- User messages

## System Requirements

- JDK 8 or higher
- NetBeans IDE
- Internet connection for AWS DynamoDB access
- AWS SDK for Java
- AWS Configuration (Access Key and Secret Key)

## How to Run the Application

1. Clone this repository
2. Open the project using NetBeans IDE
3. Ensure all dependencies are installed
4. Configure AWS credentials in the configuration file
5. Build and run the application

## AWS DynamoDB Configuration

```java
// AWS DynamoDB Configuration Example
AWSCredentials credentials = new BasicAWSCredentials(
    "YOUR_ACCESS_KEY",
    "YOUR_SECRET_KEY"
);

AmazonDynamoDB client = AmazonDynamoDBClientBuilder
    .standard()
    .withCredentials(new AWSStaticCredentialsProvider(credentials))
    .withRegion(Regions.AP_SOUTHEAST_1)
    .build();
```

## Contribution

If you want to contribute to this project, please:
1. Fork the repository
2. Create a new branch for your feature
3. Commit changes
4. Push to the branch
5. Create a Pull Request

## Contact

If you have any questions or suggestions, please open a new issue in this repository.

[![WhatsApp](https://img.shields.io/badge/WhatsApp-25D366?style=for-the-badge&logo=whatsapp&logoColor=white)](https://wa.me/6285157517798)
[![Instagram](https://img.shields.io/badge/Instagram-E4405F?style=for-the-badge&logo=instagram&logoColor=white)](https://www.instagram.com/ryan.septiawan__)




# ShareDear - Social Media Application

ShareDear is a social media application developed using Spring Boot, Spring MVC, and MySQL. It provides a platform for users to connect, share updates, and interact with their friends.

## Features

1. **User Registration:**
   - Users can register by providing necessary details like username, email, and password.
   - Username and email should be unique across all registered users.

2. **User Login and Logout:**
   - Registered users can log in securely and log out when they're done.

3. **Friend Management:**
   - Users can send friend requests to other users.
   - Accept or decline friend requests.
   - Remove friends from the friend list.
   - Users Can see list of all registered users.

4. **Profile Viewing:**
   - View other users' profiles with details.

5. **Post Management:**
   - Users can create and upload posts.
   - View their own posts and posts of friends.

6. **If User is Friend:**
   - Users can see their friend list.
   - Users can see their Mutual friend list.
   - Users can see their posts.
  
7. **Profile Edit:**
   - User can change their profile pic.
   - User can remove their profile pic.
   - User can edit their profile details.
   - User can change their password.
     
8. **Security:**
   - Secure user authentication and authorization using Spring Security.

9. **Validation:**
   - Input validation for registration and other forms.

10. **Thymeleaf Templates:**
   - Utilizes Thymeleaf for server-side rendering of HTML templates.

11. **Data Persistence:**
    - Uses JPA for database interaction, storing user information, posts, and friend relationships.

 12. **Email Verification:**
     - Email verification through OTP for new account registraion and forgot password.     

## Technologies Used

- Java
- Spring Boot
- Spring MVC
- Thymeleaf
- Spring Security
- Spring Data JPA
- MySQL

## Getting Started

1. Clone the repository: `git clone https://github.com/shrikantbochare/ShareDear.git`
2. Configure the MySQL database settings in `application.properties`.

## Dependencies

- Spring Boot Web
- Thymeleaf
- Spring Security
- Spring Validation
- Spring Data JPA
- MySQL Connector
- Mail API
- Spring AOP

## Contributing

Feel free to contribute by opening issues or submitting pull requests.


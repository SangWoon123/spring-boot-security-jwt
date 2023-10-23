# spring-boot-security-jwt
Spring Security Jwt 로그인

기능
- 회원가입 
- 로그인(jwt 토큰 발급)

도메인 
- User

### URL

회원가입 / POST / body {
    "username": "",
    "password": ""
} 
```
http://localhost:8080/auth/signup
```

로그인 / POST / body {
    "username": "",
    "password": ""
} 

```
http://localhost:8080/auth/signin
```

[security 프로세스](https://github.com/SangWoon123/spring-boot-security-jwt/wiki ) 


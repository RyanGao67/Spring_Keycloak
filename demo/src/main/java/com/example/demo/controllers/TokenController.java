package com.example.demo.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/token")
public class TokenController {

    @GetMapping
    public Map<String, Object> getToken(@AuthenticationPrincipal Jwt jwt){
// when we send the request with an access token to a
// resource server, behind the scenes, spring framework will do
// a lot of work.

//And if all is good, the result of that work will be an authentication object
// from which we can get the details of currently authenticated principal user
// and also the jwt access token claims to get all of the claims that access token
// contains in a single object I will use a special notation called Authentication
// Principle.So what this annotation will do is it will bind and the details of
// currently authenticated principle into special jwt object, and from this jwt
// object we can get the access token itself and all the claims that the provided
// request access token contains.
        return Collections.singletonMap("principal", jwt);
    }
}

// STEP 1: go to browser:
// http://localhost:8080/auth/realms/realm1/protocol/openid-connect/auth?client_id=client1&response_type=code&scope=openid profile&redirect_uri=http://localhost:8083/callback&state=asdfasdfasdfa
// this url includes :
// client_id=client1
// response_type=code
// scope=openid profile
// redirect_uri=http://localhost:8083/callback
// state=asdfasdfasdfa

// STEP 2: get the code from redirect url
// http://localhost:8083/callback?state=asdfasdfasdfa&session_state=e06150d9-51db-45e3-9bfd-217791071152&code=3e785bfe-c45b-4d16-9c34-f3feeecdd087.e06150d9-51db-45e3-9bfd-217791071152.d2781a95-e0b8-4f07-a930-f222ee7ce374
// code=3e785bfe-c45b-4d16-9c34-f3feeecdd087.e06150d9-51db-45e3-9bfd-217791071152.d2781a95-e0b8-4f07-a930-f222ee7ce374

// STEP 3: change the code for token
//curl -XPOST localhost:8080/auth/realms/realm1/protocol/openid-connect/token \
// --header 'Content-Type:application/x-www-form-urlencoded' \
// --data-urlencode 'grant_type=authorization_code' \
// --data-urlencode 'client_id=client1' \
// --data-urlencode 'redirect_uri=http://localhost:8083/callback' \
// --data-urlencode 'scope=openid profile' \
// --data-urlencode 'client_secret=7c11a6bc-40a5-4eea-ad4d-5b28ed2ba84e' \
// --data-urlencode 'code=21918612-94b5-4693-aabe-1ad715346e7b.e06150d9-51db-45e3-9bfd-217791071152.d2781a95-e0b8-4f07-a930-f222ee7ce374'

//{"access_token":"eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJ0UXdfVjFnZ0VVa3RzYWY4RlBrUURtWjZ2ZEdSMGVGTVpHaEpaTWNHbjNRIn0.eyJleHAiOjE2MTE1MjIwNTAsImlhdCI6MTYxMTUyMTc1MCwiYXV0aF90aW1lIjoxNjExNTIwNTUzLCJqdGkiOiI4YTMxMDRiNy1hN2EzLTQ4MzItYTliNy0wNzMxYzllYzFlNmEiLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvYXV0aC9yZWFsbXMvcmVhbG0xIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6IjFiMmNlZTFmLWExMWMtNGYyZS04ZWMxLTRjODA0MzBkYzUwYiIsInR5cCI6IkJlYXJlciIsImF6cCI6ImNsaWVudDEiLCJzZXNzaW9uX3N0YXRlIjoiZTA2MTUwZDktNTFkYi00NWUzLTliZmQtMjE3NzkxMDcxMTUyIiwiYWNyIjoiMCIsInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJvZmZsaW5lX2FjY2VzcyIsIkRldmVsb3BlciIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJvcGVuaWQgcHJvZmlsZSBlbWFpbCIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwibmFtZSI6InlhbmxpbiBtbyIsInByZWZlcnJlZF91c2VybmFtZSI6ImVsZWFub3Jtb21vIiwiZ2l2ZW5fbmFtZSI6InlhbmxpbiIsImZhbWlseV9uYW1lIjoibW8iLCJlbWFpbCI6ImFzc2lkdW91c3J5YW5AaG90bWFpbC5jb20ifQ.pok7LHR7AMSt5by9pSTFnFfOFPnX2SMj3co3tWvAwcJO_42hIjrerk25mvOtZVm8bNM0uVCrLoQv9PGlY-aTcH6yqD-HisAuo1G3WZWz8RNmzZvs60smFogljmy8d_tDmln0GtbNBlKkcVlUthxAZQsmILUvzplo184GP4pZ6XD6MaCnsqoMoLuKvuRnGdscu9pOtdq9wpgEQQ1xQ7XPxHG90NQnW4AzfshKBenNmHE95CIshDTsXPD56A3RKV5ATaSOcQoRGqURAVbJN2pHzEp1PLOxqAidROgMOTHVex0RgTC0RlFu-LmzBMrlUWuNLltH9GW1OfEcN74kxfDd8Q",
// "expires_in":300,
// "refresh_expires_in":1800,
// "refresh_token":"eyJhbGciOiJIUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIxMjdiNGUwYS0yZTA1LTQ5NGItODlmMi1iZjQ1YTgxZDhmN2EifQ.eyJleHAiOjE2MTE1MjM1NTAsImlhdCI6MTYxMTUyMTc1MCwianRpIjoiMDY1NjBlNmMtNDBmMS00NDZiLTk4OWYtM2MwNzQ1MjgyMzE3IiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2F1dGgvcmVhbG1zL3JlYWxtMSIsImF1ZCI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9hdXRoL3JlYWxtcy9yZWFsbTEiLCJzdWIiOiIxYjJjZWUxZi1hMTFjLTRmMmUtOGVjMS00YzgwNDMwZGM1MGIiLCJ0eXAiOiJSZWZyZXNoIiwiYXpwIjoiY2xpZW50MSIsInNlc3Npb25fc3RhdGUiOiJlMDYxNTBkOS01MWRiLTQ1ZTMtOWJmZC0yMTc3OTEwNzExNTIiLCJzY29wZSI6Im9wZW5pZCBwcm9maWxlIGVtYWlsIn0.X4iAGvAfU-j_ZJUQZeNespKSmvta9izfz3qhK1GDEXo",
// "token_type":"Bearer",
// "id_token":"eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJ0UXdfVjFnZ0VVa3RzYWY4RlBrUURtWjZ2ZEdSMGVGTVpHaEpaTWNHbjNRIn0.eyJleHAiOjE2MTE1MjIwNTAsImlhdCI6MTYxMTUyMTc1MCwiYXV0aF90aW1lIjoxNjExNTIwNTUzLCJqdGkiOiJlNzUzMTEwOS1mNDAzLTRkMzEtOWI2OS0zNWU3ZmU0Y2Q0Y2MiLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvYXV0aC9yZWFsbXMvcmVhbG0xIiwiYXVkIjoiY2xpZW50MSIsInN1YiI6IjFiMmNlZTFmLWExMWMtNGYyZS04ZWMxLTRjODA0MzBkYzUwYiIsInR5cCI6IklEIiwiYXpwIjoiY2xpZW50MSIsInNlc3Npb25fc3RhdGUiOiJlMDYxNTBkOS01MWRiLTQ1ZTMtOWJmZC0yMTc3OTEwNzExNTIiLCJhdF9oYXNoIjoiMnVPTTVtZnZkcDl2M21JSUVQTGFFZyIsImFjciI6IjAiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsIm5hbWUiOiJ5YW5saW4gbW8iLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJlbGVhbm9ybW9tbyIsImdpdmVuX25hbWUiOiJ5YW5saW4iLCJmYW1pbHlfbmFtZSI6Im1vIiwiZW1haWwiOiJhc3NpZHVvdXNyeWFuQGhvdG1haWwuY29tIn0.CU9lUflZyUqV5knj0_XzNaiSSJFNTQZeqboeK4xObHJOEH3uYAw-jnsbo0WgR-puYCPO2y2B1iQZPuULn50WkGciNVNkXGd2At0yA1oOweqE-9UUKuJfuoSVGk_cb998gkFaW37JMdWbgIqaP4Q_5fRfjY1GfHva94CXnxGgzpFAaqDGTJStAyN9khRPQ2v6m5c6ldWeOu189wWfE3AXjJkGqJPwMRulUuPvBvU_wDghVOqqi1y65KyIbKHFyADugWNMenbFus01N26RBJXr92m4ERLbZCozt8wX0eKXOGVxsdvt5nN2Sh2gVRanBw5SX-qFGQqbxuxRxuhNX4Q6Jg",
// "not-before-policy":0,
// "session_state":"e06150d9-51db-45e3-9bfd-217791071152",
// "scope":"openid profile email"}


// Sample output of this api:
//{
//    "principal": {


//        "tokenValue": "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJ0UXdfVjFnZ0VVa3RzYWY4RlBrUURtWjZ2ZEdSMGVGTVpHaEpaTWNHbjNRIn0.eyJleHAiOjE2MTE1MjA4NzQsImlhdCI6MTYxMTUyMDU3NCwiYXV0aF90aW1lIjoxNjExNTIwNTUzLCJqdGkiOiI3NDBlNDQyMS1jN2Q0LTRhOTItYmI4Ni1kOGFmOGJiMjIwZDkiLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvYXV0aC9yZWFsbXMvcmVhbG0xIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6IjFiMmNlZTFmLWExMWMtNGYyZS04ZWMxLTRjODA0MzBkYzUwYiIsInR5cCI6IkJlYXJlciIsImF6cCI6ImNsaWVudDEiLCJzZXNzaW9uX3N0YXRlIjoiZTA2MTUwZDktNTFkYi00NWUzLTliZmQtMjE3NzkxMDcxMTUyIiwiYWNyIjoiMSIsInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJvZmZsaW5lX2FjY2VzcyIsIkRldmVsb3BlciIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJvcGVuaWQgcHJvZmlsZSBlbWFpbCIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwibmFtZSI6InlhbmxpbiBtbyIsInByZWZlcnJlZF91c2VybmFtZSI6ImVsZWFub3Jtb21vIiwiZ2l2ZW5fbmFtZSI6InlhbmxpbiIsImZhbWlseV9uYW1lIjoibW8iLCJlbWFpbCI6ImFzc2lkdW91c3J5YW5AaG90bWFpbC5jb20ifQ.qmqiCyFMM-kKkrpNZTn6NQbeR23F1ePMiEkKJCW1aBdAD2jIm05e4bOH0Nc1NkOzG1k_L88fKiNpqYs6Z7ANcIszK1FCzAMbg9Nh4WCLdX1BO-n2UtoXOjcFQrJMo0WgAoZW0Un13Z4IzCXsb-w5XGq8oHdfWnZddAE2aAp4ayOG7yvvw4wCRlDGWBfkUHtRpyFEsnqV7dc_PpNnefavqZ7HzX3ZaW27_aTblDdPA9O3kgxiN563Q4TdfXkEOXoBCeBkxvIvW0KtY9oNj707nZJfQ9gPQN457SEoHQlBd0lPFDzEuedPPfrJmg7No3AYdM2bf2O_pp1M2gIexrG1vA",
//        "issuedAt": "2021-01-24T20:36:14Z",
//        "expiresAt": "2021-01-24T20:41:14Z",
//        "headers": {
//            "kid": "tQw_V1ggEUktsaf8FPkQDmZ6vdGR0eFMZGhJZMcGn3Q",
//            "typ": "JWT",
//            "alg": "RS256"
//        },
//        "claims": {
//            "sub": "1b2cee1f-a11c-4f2e-8ec1-4c80430dc50b",
//            "resource_access": {
//                "account": {
//                    "roles": [
//                        "manage-account",
//                        "manage-account-links",
//                        "view-profile"
//                     ]
//                 }
//             },
//             "email_verified": false,
//             "iss": "http://localhost:8080/auth/realms/realm1",
//             "typ": "Bearer",
//             "preferred_username": "eleanormomo",
//             "given_name": "yanlin",
//             "aud": [
//                 "account"
//             ],
//             "acr": "1",
//             "realm_access": {
//                 "roles": [
//                      "offline_access",
//                      "Developer",
//                      "uma_authorization"
//                 ]
//              },
//              "azp": "client1",
//              "auth_time": 1611520553,
//              "scope": "openid profile email",
//              "name": "yanlin mo",
//              "exp": "2021-01-24T20:41:14Z",
//              "session_state": "e06150d9-51db-45e3-9bfd-217791071152",
//              "iat": "2021-01-24T20:36:14Z",
//              "family_name": "mo",
//              "jti": "740e4421-c7d4-4a92-bb86-d8af8bb220d9",
//              "email": "assiduousryan@hotmail.com"
//         },
//         "subject": "1b2cee1f-a11c-4f2e-8ec1-4c80430dc50b",
//         "issuer": "http://localhost:8080/auth/realms/realm1",
//         "audience": [
//             "account"
//         ],
//         "notBefore": null,
//         "id": "740e4421-c7d4-4a92-bb86-d8af8bb220d9"



//    }
//}






// More on this
// http://localhost:8080/auth/realms/realm1/protocol/openid-connect/auth?client_id=client1&response_type=code&scope=openid profile&redirect_uri=http://localhost:8083/callback&state=asdfasdfasdfa
// this url includes :
// client_id=client1
// response_type=code
// scope=openid profile
// redirect_uri=http://localhost:8083/callback
// state=asdfasdfasdfa

// openID connect is a layer on top of Oauth2 authrization framework.
// And it enables client applications to verify the identity of a user
// as well as obtain some basic information about the end user.
// So with these two particular scopes 'openid' and 'profile', I would like my
// client application to obtain user profile information.

// So with these two particular scopes `openid` and `profile`, I would like
// my client application to obtain user profile information.These are openid
// connect standard scopes.
//
// The first one, which is called openid, it informs the authorization server that
// the client application is making an openid connect request.And this will also
// make the authorization server additionally to an access token return an
// ID token.


//  So the second scope value, which is called profile, it tells the authorization
//  server the client application would like to have an access to a user profile
//  details and openid connect user profile information contains the following.
//  name, family name, given name, middle name, nickname and so on, but it does
//  not contain email address. Email is a separate scope.
//
//  So here's an example of openid connect standard scopes that can be used to
//  access information about the user:
//  &scope=openid profile email address phone offline_access
//  the first scope openid it will tell atomisation server
//  that client The application is making an openid connect request.
//
//  The second scope value is profile and with scope the client application
//  will be able to access user's first name, last name, nickname, birthdate
//  and so on.



//    The next scope is email, and the scope will allow client application to
//    get user's email address and also information if this email address has
//    been verified or not.
//
//    the next scope is address, this scope allows client application to get
//    different details about users to address.
//
//    the next value in the authentication request is phone and the scope value
//    will allow client application to get user's phone number and also
//    information if this phone number has been verified or not.
//
//    And the final scope offline access, which will inform authorization server
//    that the client Application would also like to get a refresh token and
//    this refresh token can be used to refresh and expiring access token.


//    So as you can see, Scope's, they are being used to request the server
//    to generate an access token that will allow for an application to
//    communicate with protected resource server end points and request
//    information that is defined by access scope. the above scopes are standard
//    openid scopes.
//    But the information, the return can be configured on the server side and
//    you can also define your custom scopes.
//        So once you have an access token that was generated with a support
//        for one or all of the scopes, you should be able to get user information
//        from either from either id token or by sending the request to a user
//        information Web service end point that your authorization server will
//        expose.
//
//        And to get this information from
//        authorization, server,
// curl -H "Content-Type:Application/json" \
// -H "Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJ0UXdfVjFnZ0VVa3RzYWY4RlBrUURtWjZ2ZEdSMGVGTVpHaEpaTWNHbjNRIn0.eyJleHAiOjE2MTE1MjU0MzIsImlhdCI6MTYxMTUyNTEzMiwiYXV0aF90aW1lIjoxNjExNTI1MTExLCJqdGkiOiI3MDNjN2QyZC0xZTQ3LTQwYTItYmE1Zi1jYjk2ZTkzOTYxMzUiLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvYXV0aC9yZWFsbXMvcmVhbG0xIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6IjFiMmNlZTFmLWExMWMtNGYyZS04ZWMxLTRjODA0MzBkYzUwYiIsInR5cCI6IkJlYXJlciIsImF6cCI6ImNsaWVudDEiLCJzZXNzaW9uX3N0YXRlIjoiYTIzMTRkNDYtODcyNC00YmY3LTg1OWMtOTM3MGM5MzI1YWZmIiwiYWNyIjoiMSIsInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJvZmZsaW5lX2FjY2VzcyIsIkRldmVsb3BlciIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJvcGVuaWQgcHJvZmlsZSBlbWFpbCIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwibmFtZSI6InlhbmxpbiBtbyIsInByZWZlcnJlZF91c2VybmFtZSI6ImVsZWFub3Jtb21vIiwiZ2l2ZW5fbmFtZSI6InlhbmxpbiIsImZhbWlseV9uYW1lIjoibW8iLCJlbWFpbCI6ImFzc2lkdW91c3J5YW5AaG90bWFpbC5jb20ifQ.WPOymGAyL9uG2Qnyq6FLE7hzQ1XPlHYGjQkeSmy5cPWJ4qZ1MmH5EMycrGE2hUGAMcZcENVUCgCcn26BJaygxmqZfMQPT9iEHv0RmeCQ3lsD6pfEvVZg-C8syFONp8TPydKwTDmJPnIzxncw3wp0_wWis8ArDmgmf49mvK2A2vWYq2GM8qQwclaSiR5ArCIN2goQm2ievLdnmecRs3cMFc9NVLTY_LtpTqxtKnXSe5xO50UutMwAOtWwWxClmV12ov4GWUB8sWBaW0Nm9iqODO6IvcmHYhIhrl9uthb6raqq3-54U_O9nJP52OEz6sg9gy2nb13O_vSTt4Sch26cog" \
// http://localhost:8080/auth/realms/realm1/protocol/openid-connect/userinfo
//{"sub":"1b2cee1f-a11c-4f2e-8ec1-4c80430dc50b",
// "email_verified":false,
// "name":"yanlin mo",
// "preferred_username":"eleanormomo",
// "given_name":"yanlin",
// "family_name":"mo",
// "email":"assiduousryan@hotmail.com"}
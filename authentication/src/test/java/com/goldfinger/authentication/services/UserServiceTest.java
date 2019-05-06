package com.goldfinger.authentication.services;

import com.goldfinger.authentication.models.JwtUserDetails;
import com.goldfinger.authentication.models.Role;
import com.goldfinger.authentication.repositories.UserRepository;
import com.goldfinger.authentication.security.JwtGenerator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    JwtGenerator generator;

    @InjectMocks
    private UserServiceImpl service;

    @Test(expected = EntityExistsException.class)
    public void AddUser_Should_Return_EntytyExistException_When_User_Exist_In_Database() {
        JwtUserDetails jwtUserDetails = new JwtUserDetails();
        List<JwtUserDetails> users = new ArrayList<>();
        users.add(jwtUserDetails);
        Mockito.when(repository.findByUsername(jwtUserDetails.getUsername())).thenReturn(users);

        service.addUser(jwtUserDetails);
    }

    @Test
    public void addUser_Should_Return_True_When_Add_User_InRepository() {
        JwtUserDetails jwtUserDetails = new JwtUserDetails();
        List<JwtUserDetails> users = new ArrayList<>();
        Mockito.when(repository.findByUsername(jwtUserDetails.getUsername())).thenReturn(users);
        Mockito.when(repository.save(jwtUserDetails)).thenReturn(jwtUserDetails);

        Assert.assertEquals(true, service.addUser(jwtUserDetails));
    }

    @Test
    public void getToken_Should_Return_Token_When_Method_Is_Called(){
        JwtUserDetails jwtUserDetails = new JwtUserDetails();
        String token = "token";
        List<JwtUserDetails> userDetails = new ArrayList<>();
        userDetails.add(jwtUserDetails);
        Mockito.when(generator.generate(jwtUserDetails)).thenReturn(token);
        Mockito.when(repository.findByUsernameAndPassword(jwtUserDetails.getUsername(), jwtUserDetails.getPassword())).thenReturn(userDetails);

        Assert.assertEquals(token, service.getToken(jwtUserDetails.getUsername(), jwtUserDetails.getPassword()));

    }

    @Test(expected = EntityNotFoundException.class)
    public void getToken_Should_Throw_Entity_Not_Found_Exception_When_Username_And_Password_Is_Incorrect(){
        JwtUserDetails jwtUserDetails = new JwtUserDetails();
        List<JwtUserDetails> userDetails = new ArrayList<>();
        Mockito.when(repository.findByUsernameAndPassword(jwtUserDetails.getUsername(), jwtUserDetails.getPassword())).thenReturn(userDetails);

        service.getToken(jwtUserDetails.getUsername(), jwtUserDetails.getPassword());
    }
}
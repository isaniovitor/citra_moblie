package com.example.citra_moblie;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.example.citra_moblie.model.User;

import org.junit.Test;

public class UserValidatorTest {

    private User user = new User();

    @Test
    public void testUserNull() {
        assertNotNull(user);
    }

    @Test
    public void testSetIdUser(){
        String id = "6cfklSn2j6SqK4fc4CzxP98ICGY2";
        user.setId(id);
        assertEquals("Verificando o ID do User", id, user.getId());
    }

    @Test
    public void testSetIdUserNull(){
        assertNull(user.getId());
    }

    @Test
    public void testSetName(){
        String name = "Fulano de Tal";
        user.setName(name);
        assertEquals("Verificando o Nome do User", name, user.getName());
    }

    @Test
    public void testSetNameUserNull(){
        assertNull(user.getName());
    }

    @Test
    public void testSetBirthday(){
        String birthday = "18112001";
        user.setBirthday(birthday);
        assertEquals("Verificando Aniversário do User", birthday, user.getBirthday());
    }

    @Test
    public void testSetBirthdayNull(){
        assertNull(user.getBirthday());
    }

    @Test
    public void testSetCpf(){
        String cpf = "282838373";
        user.setCpf(cpf);
        assertEquals("Verificando CPF do User", cpf, user.getCpf());
    }

    @Test
    public void testSetCpfNull(){
        assertNull(user.getCpf());
    }

    @Test
    public void testSetEmail(){
        String email = "fulano@gmail.com";
        user.setEmail(email);
        assertEquals("Verificando Email do User", email, user.getEmail());
    }

    @Test
    public void testSetEmailNull(){
        assertNull(user.getEmail());
    }

    @Test
    public void testSetPassword(){
        String password = "123456";
        user.setPassword(password);
        assertEquals("Verificando Senha do User", password, user.getPassword());
    }

    @Test
    public void testSetPasswordNull(){
        assertNull(user.getPassword());
    }
}

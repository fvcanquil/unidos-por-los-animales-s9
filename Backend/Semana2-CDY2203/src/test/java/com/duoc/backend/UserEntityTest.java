package com.duoc.backend;

import com.duoc.backend.user.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserEntityTest {

    @Test
    void testUserGettersSettersAndDetails() {
        // Arrange
        User user = new User();
        
        // Act: Probamos Setters
        user.setId(1);
        user.setUsername("arquitecto");
        user.setEmail("test@duoc.cl");
        user.setPassword("secret123");
        user.setEnabled(true);

        // Assert: Probamos Getters
        assertEquals(1, user.getId());
        assertEquals("arquitecto", user.getUsername());
        assertEquals("test@duoc.cl", user.getEmail());
        assertEquals("secret123", user.getPassword());
        
        // Assert: Probamos implementación de UserDetails
        assertTrue(user.isEnabled());
        assertTrue(user.isAccountNonExpired());
        assertTrue(user.isAccountNonLocked());
        assertTrue(user.isCredentialsNonExpired());
        assertNull(user.getAuthorities()); // Según tu implementación actual retorna null
    }

    @Test
    void testUserConstructor() {
        User user = new User();
        assertNotNull(user);
    }
}

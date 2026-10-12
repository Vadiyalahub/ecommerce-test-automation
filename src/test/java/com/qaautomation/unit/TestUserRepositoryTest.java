package com.qaautomation.unit;

import com.qaautomation.data.TestUser;
import com.qaautomation.data.TestUserRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestUserRepositoryTest {

    private final TestUserRepository repository = new TestUserRepository();

    @Test
    void findsStandardUser() {
        TestUser user = repository.findByType("standard");
        assertEquals("standard_user", user.username());
        assertEquals("secret_sauce", user.password());
    }

    @Test
    void findsLockedOutUser() {
        assertEquals("locked_out_user", repository.findByType("locked").username());
    }

    @Test
    void throwsForUnknownUserType() {
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                () -> repository.findByType("admin"));
        assertTrue(error.getMessage().contains("admin"));
    }

    @Test
    void loadsAllUserTypesFromSqlScript() {
        assertEquals(List.of("locked", "problem", "standard"), repository.findAllUserTypes());
    }
}

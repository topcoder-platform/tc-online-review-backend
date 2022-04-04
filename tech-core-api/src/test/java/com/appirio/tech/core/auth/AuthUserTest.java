package com.appirio.tech.core.auth;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Test AuthUser
 * 
 * <p>
 * Changes in the version 1.1 (Topcoder - Support Machine To Machine Token In Core API v1.0)
 * - add the tests for scope and machine field
 * </p>
 * 
 * @author TCCoder
 * @version 1.1
 *
 */

public class AuthUserTest {

	@Test
	public void testHasRole() {
		
		// testee
		AuthUser authUser = spy(new AuthUser());
		List<String> roles = new ArrayList<String>();
		roles.add("ROLE");
		authUser.setRoles(roles);

		// test
		boolean result1 = authUser.hasRole("ROLE");
		boolean result2 = authUser.hasRole("ANOTHER-ROLE");
		
		// verify
		assertTrue(result1);
		assertFalse(result2);
	}
	
	@Test
	public void testHasRole_EmptyRole() {
		
		// testee
		AuthUser authUser = spy(new AuthUser());
		authUser.setRoles(null);

		// test
		boolean result = authUser.hasRole("ROLE");
		
		// verify
		assertFalse(result);
	}
	
	/**
     * Test get/set scope
     *
     */
    @Test
    public void testScope() {
        // testee
        AuthUser authUser = spy(new AuthUser());
        List<String> scope = new ArrayList<String>();
        scope.add("read:xxx");
        scope.add("write:yyy");

        authUser.setScope(scope);

        assertEquals(authUser.getScope().size(), 2);
        assertEquals(authUser.getScope().get(0), "read:xxx");
        assertEquals(authUser.getScope().get(1), "write:yyy");
    }
    
    /**
     * Test get/set machine
     *
     */
    @Test
    public void testMachine() {
        AuthUser authUser = spy(new AuthUser());
        authUser.setMachine(true);
        assertTrue(authUser.isMachine());
    }
}

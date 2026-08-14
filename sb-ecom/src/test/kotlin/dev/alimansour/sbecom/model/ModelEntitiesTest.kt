package dev.alimansour.sbecom.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ModelEntitiesTest {

    @Test
    fun `Category should have safe default values, products collection, and working equals and hashCode`() {
        val category1 = Category(id = 1L, name = "Electronics")
        val category2 = Category(id = 1L, name = "Gadgets")
        val category3 = Category(id = 2L, name = "Electronics")
        val transient1 = Category(name = "Books")
        val transient2 = Category(name = "books")
        val transient3 = Category(name = "Clothing")

        // Collection is initialized
        assertNotNull(category1.products)
        assertEquals(0, category1.products.size)

        // Persisted equality (by ID)
        assertEquals(category1, category2)
        assertEquals(category1.hashCode(), category2.hashCode())
        assertNotEquals(category1, category3)

        // Transient equality (case-insensitive name fallback)
        assertEquals(transient1, transient2)
        assertEquals(transient1.hashCode(), transient2.hashCode())
        assertNotEquals(transient1, transient3)

        // toString
        assertEquals("Category(id=1, name='Electronics')", category1.toString())
    }

    @Test
    fun `Product should have safe default values, nullable relationships, and working equals and hashCode`() {
        val product1 = Product(id = 10L, name = "Laptop", price = 1000.0)
        val product2 = Product(id = 10L, name = "Notebook", price = 1200.0)
        val product3 = Product(id = 20L, name = "Laptop", price = 1000.0)
        val transient1 = Product(name = "Phone")
        val transient2 = Product(name = "phone")
        val transient3 = Product(name = "Tablet")

        // Relationships default to null safely without UninitializedPropertyAccessException
        assertNull(product1.category)
        assertNull(product1.user)

        // Persisted equality
        assertEquals(product1, product2)
        assertEquals(product1.hashCode(), product2.hashCode())
        assertNotEquals(product1, product3)

        // Transient equality
        assertEquals(transient1, transient2)
        assertEquals(transient1.hashCode(), transient2.hashCode())
        assertNotEquals(transient1, transient3)

        // toString format check (no typos)
        val str = product1.toString()
        assertTrue(str.startsWith("Product(id=10, name='Laptop', image='default.png'"))
    }

    @Test
    fun `User should redact password in toString and have safe equals and hashCode`() {
        val user1 = User(id = 1L, username = "alice", email = "alice@example.com", password = "secret_password")
        val user2 = User(id = 1L, username = "alice_new", email = "alice2@example.com", password = "new_password")
        val user3 = User(id = 2L, username = "alice", email = "alice@example.com", password = "secret_password")
        val transient1 = User(username = "bob", email = "bob1@example.com", password = "pass")
        val transient2 = User(username = "bob", email = "bob2@example.com", password = "pass")
        val transient3 = User(username = "charlie", email = "charlie@example.com", password = "pass")

        // Collections are initialized
        assertNotNull(user1.roles)
        assertNotNull(user1.addresses)
        assertNotNull(user1.products)

        // Persisted equality
        assertEquals(user1, user2)
        assertEquals(user1.hashCode(), user2.hashCode())
        assertNotEquals(user1, user3)

        // Transient equality (by username)
        assertEquals(transient1, transient2)
        assertEquals(transient1.hashCode(), transient2.hashCode())
        assertNotEquals(transient1, transient3)

        // Security check: password is NOT in toString
        val str = user1.toString()
        assertTrue(str.contains("username='alice'"))
        assertTrue(str.contains("email='alice@example.com'"))
        assertFalse(str.contains("secret_password"))
        assertFalse(str.contains("password"))
    }

    @Test
    fun `Role should have default name, and working equals and hashCode`() {
        val defaultRole = Role()
        assertEquals(AppRole.ROLE_USER, defaultRole.name)

        val roleAdmin = Role(id = 1, name = AppRole.ROLE_ADMIN)
        val roleAdmin2 = Role(id = 1, name = AppRole.ROLE_ADMIN)
        val roleUser = Role(id = 2, name = AppRole.ROLE_USER)

        val transientAdmin1 = Role(name = AppRole.ROLE_ADMIN)
        val transientAdmin2 = Role(name = AppRole.ROLE_ADMIN)
        val transientSeller = Role(name = AppRole.ROLE_SELLER)

        // Persisted equality
        assertEquals(roleAdmin, roleAdmin2)
        assertEquals(roleAdmin.hashCode(), roleAdmin2.hashCode())
        assertNotEquals(roleAdmin, roleUser)

        // Transient equality
        assertEquals(transientAdmin1, transientAdmin2)
        assertEquals(transientAdmin1.hashCode(), transientAdmin2.hashCode())
        assertNotEquals(transientAdmin1, transientSeller)

        // toString
        assertEquals("Role(id=1, name='ROLE_ADMIN')", roleAdmin.toString())
    }

    @Test
    fun `Address should have initialized users list and working equals and hashCode`() {
        val address1 = Address(id = 100L, street = "Main St", buildingName = "Tower A", city = "New York", state = "NY", country = "USA", pinCode = "10001")
        val address2 = Address(id = 100L, street = "Other St", buildingName = "Tower B", city = "Boston", state = "MA", country = "USA", pinCode = "02101")
        val address3 = Address(id = 200L, street = "Main St", buildingName = "Tower A", city = "New York", state = "NY", country = "USA", pinCode = "10001")

        val transient1 = Address(street = "Main St", buildingName = "Tower A", city = "New York", state = "NY", country = "USA", pinCode = "10001")
        val transient2 = Address(street = "Main St", buildingName = "Tower A", city = "New York", state = "NY", country = "USA", pinCode = "10001")
        val transient3 = Address(street = "Second St", buildingName = "Tower A", city = "New York", state = "NY", country = "USA", pinCode = "10001")

        // Collection initialized
        assertNotNull(address1.users)
        assertEquals(0, address1.users.size)

        // Persisted equality
        assertEquals(address1, address2)
        assertEquals(address1.hashCode(), address2.hashCode())
        assertNotEquals(address1, address3)

        // Transient equality
        assertEquals(transient1, transient2)
        assertEquals(transient1.hashCode(), transient2.hashCode())
        assertNotEquals(transient1, transient3)

        // toString format
        assertEquals("Address(id=100, street='Main St', buildingName='Tower A', city='New York', state='NY', country='USA', pinCode='10001')", address1.toString())
    }
}

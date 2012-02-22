import org.example.demo.*

class BootStrap {

    def init = { servletContext ->
        def adminRole = Role.findByAuthority('ROLE_ADMIN') ?: new Role(authority: 'ROLE_ADMIN').save(failOnError: true)
        def userRole = Role.findByAuthority('ROLE_USER') ?: new Role(authority: 'ROLE_USER').save(failOnError: true)

        def user1 = User.findByUsername('bobby@example.org') ?: new User(username: 'bobby@example.org', enabled: true, password: 'pass', firstName: 'Bobby', lastName: 'Warner', phone: '1111111111').save(failOnError: true)
        if (!user1.authorities.contains(userRole)) {
            UserRole.create user1, userRole, true
        }

        def user2 = User.findByUsername('admin@example.org') ?: new User(username: 'admin@example.org', enabled: true, password: 'pass', firstName: 'Admin', lastName: 'Admin', phone: '2222222222').save(failOnError: true)
        if (!user2.authorities.contains(userRole)) {
            UserRole.create user2, userRole, true
        }
        if (!user2.authorities.contains(adminRole)) {
            UserRole.create user2, adminRole, true
        }

        assert User.count() == 2
        assert Role.count() == 2
        assert UserRole.count() == 3     
    }
    
    def destroy = {
    }
}
